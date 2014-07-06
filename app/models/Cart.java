package models;

import com.avaje.ebean.Ebean;
import play.db.ebean.Model;

import javax.persistence.*;
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
    private SystemUser owner;
    private String description;
    @ManyToMany( cascade = CascadeType.ALL )
    private List<CartItem> cartItems;
    private Date createTime;
    private Date closeTime;
    @Version
    @Column(columnDefinition = "timestamp")
    private Date updateTime;


    public Cart( String name, SystemUser owner){
        setName( name );
        setOwner( owner );
        setCreateTime( new Date() );
    }

    private static Finder< String, Cart > find = new Finder<String, Cart>( String.class, Cart.class );

    public static Cart findCartBySystemUser( String ownerId ){
        Cart cart = Ebean.find(Cart.class).where().eq("owner.id", ownerId).findUnique();
        System.out.println("Cart: ---> " + cart);
        return cart;
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

    public SystemUser getOwner() {
        return owner;
    }

    public void setOwner(SystemUser owner) {
        this.owner = owner;
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
}