package models;

import com.avaje.ebean.Ebean;
import play.db.ebean.Model;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "carts")
public class Cart extends Model {
    @Id
    private String id = UUID.randomUUID().toString().replaceAll("-","");
    private static String reference ;
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    private SystemUser requestor;
    @OneToOne(cascade = CascadeType.ALL)
    private SystemUser provider;
    private double totalAmount;
    private final double taxRate = 10;
    private double totalTaxAmount;
    @Column(columnDefinition = "TEXT")
    private String description;
    @ManyToMany( cascade = CascadeType.ALL )
    private List<CartItem> cartItems;
    private Date createTime;
    private Date closeTime;
    @Column(columnDefinition = "timestamp")
    private Date updateTime;
    @Version
    public long version;


    public Cart( String name, SystemUser requestor){
        setName( name );
        setRequestor( requestor );
        setCreateTime( new Date() );
    }

    private static Finder< String, Cart > find = new Finder<String, Cart>( String.class, Cart.class );

    public static Cart findCartBySystemUser( String requestorId ){
        Cart cart = Ebean.find(Cart.class).where().eq("requestor.id", requestorId).findUnique();
        System.out.println("Cart: ---> " + cart);
        return cart;
    }

    public static Cart findCartById( String id ){
        Cart cart = Ebean.find(Cart.class).where().eq("id", id).findUnique();
        System.out.println("Cart: ---> " + cart);
        return cart;
    }

    public double getTotalAmountWithTax(){
        return Double.valueOf(new DecimalFormat("#0.00").format( getTotalAmount() + getTotalTaxAmount() ));
    }

    public double getTotalTaxAmount() {
        totalTaxAmount = (getTaxRate()/100) * getTotalAmount();
        return Double.valueOf(new DecimalFormat("#0.00").format( totalTaxAmount ));
    }
    public double getTaxRate() {
        return taxRate;
    }

    public void setTotalTaxAmount(double totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }
    public static String getReference() {
        return reference;
    }

    public static void setReference(String reference) {
        Cart.reference = reference;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<CartItem> getCartItems() {
        cartItems = CartItem.findCartItemsByCart( this.getId() );
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public double getTotalAmount() {
        this.totalAmount = 0;
        for ( CartItem ci :  getCartItems() ){
            totalAmount += ci.getAmount();
        }
        totalAmount = Double.parseDouble(new DecimalFormat("#0.00").format( totalAmount ));
        return totalAmount;
    }
    public double updateTotalAmount() {
        this.totalAmount = 0;
        for ( CartItem ci :  getCartItems() ){
            totalAmount += ci.getPrice();
        }
        totalAmount = Double.parseDouble(new DecimalFormat("#0.00").format( totalAmount ));
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public SystemUser getProvider() {
        return provider;
    }

    public void setProvider(SystemUser provider) {
        this.provider = provider;
    }

    public SystemUser getRequestor() {
        return requestor;
    }

    public void setRequestor(SystemUser requestor) {
        this.requestor = requestor;
    }
}