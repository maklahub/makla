package models;

import com.avaje.ebean.Ebean;
import play.db.ebean.Model;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "orders")
public class Order extends Model {
    @Id
    private String id = UUID.randomUUID().toString().replaceAll("-","");
    private String reference ;
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    private SystemUser requestor;
    @OneToOne(cascade = CascadeType.ALL)
    private SystemUser provider;
    @Column(columnDefinition = "TEXT")
    private String description;
    private double totalAmount;
    @ManyToMany( cascade = CascadeType.ALL )
    private List<OrderItem> orderItems;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.Draft;
    private final double taxRate = 10;
    private final double delivery = Double.valueOf(new DecimalFormat("#0.00").format( 4.00 ));
    private double totalTaxAmount;
    private Date deliveryTime;
    private Date createTime;
    private Date closeTime;
    @Column(columnDefinition = "timestamp")
    private Date updateTime;
    @Version
    public long version;


    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public double getTaxRate() {
        return taxRate;
    }
    public double getTotalAmountWithTax(){
        return Double.valueOf(new DecimalFormat("#0.00").format( getTotalAmount() + getTotalTaxAmount() ));
    }

    public double getTotalAmountWithTaxAndDelivery(){
        return Double.valueOf(new DecimalFormat("#0.00").format( getTotalAmount() + getTotalTaxAmount() + getDelivery() ));
    }

    public double getTotalTaxAmount() {
        totalTaxAmount = (getTaxRate()/100) * getTotalAmount();
        return Double.valueOf(new DecimalFormat("#0.00").format( totalTaxAmount ));
    }

    public void setTotalTaxAmount(double totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }

    public SystemUser getRequestor() {
        return requestor;
    }

    public void setRequestor(SystemUser requestor) {
        this.requestor = requestor;
    }

    public SystemUser getProvider() {
        return provider;
    }

    public void setProvider(SystemUser provider) {
        this.provider = provider;
    }

    public double getDelivery() {
        return delivery;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date t) {
        this.deliveryTime = t;
    }

    public enum OrderStatus {
        Draft,Precessing,Paid, Confirmed, Complete;
    }

    public Order( SystemUser requestor ){
        List<Order> o = Ebean.find( Order.class).orderBy().desc("createTime").findList();
        String ref = "";


        if ( o.size() == 0){
            ref = String.valueOf( 1 );
        }
        else {
            Order lastInsertedOrder =  o.get( 0 );
            ref = String.valueOf( Integer.valueOf(lastInsertedOrder.getReference()) + 1 );
        }

        setReference( ref  );
        setRequestor(requestor);
        setCreateTime(new Date());
    }

    public Order( Cart cart, SystemUser owner ){
        List<Order> o = Ebean.find( Order.class).orderBy().desc("createTime").findList();
        System.out.println("\n\n\n Orders *****-----> " + o.get( 0) );

        Order lastInsertedOrder =  o.get( 0 );
        String ref = "";
        if( lastInsertedOrder.getReference() == null){
            ref = String.valueOf( 1 );
        }
        ref = String.valueOf( Integer.valueOf(lastInsertedOrder.getReference()) + 1 );
        setReference( ref  );
        setRequestor(requestor);
        setCreateTime(new Date());
    }

    public  List<OrderItem> createOrderItems( Cart cart ){
        List<OrderItem> orderItems = this.getOrderItems();
        for ( CartItem cartItem : cart.getCartItems() ){
             OrderItem orderItem = new OrderItem( this , cartItem );
            // orderItem.save();
             orderItems.add( orderItem );
        }
        this.setProvider( cart.getProvider() );
        this.save();
      //  System.out.println("OrderItems: ------> " + orderItems);
      return orderItems;
    }
    private static Finder< String, Order > find = new Finder<String, Order>( String.class, Order.class );

    public static Order findOrderById( String id ){
        Order order = Ebean.find(Order.class).where().eq("id", id).findUnique();
        System.out.println("Order: ---> " + order);
        return order;
    }

    public static List<Order> findOrdersBySystemUser( String requestorId ){
        List<Order> orders  = Ebean.find(Order.class).where().eq("requestor.id", requestorId).findList();
        System.out.println("Orders: ---> " + orders);
        return orders;
    }

    public String getReference() {
        return reference;
    }

    public  void setReference(String reference) {
       this.reference = reference;
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

    public double getTotalAmount() {
        this.totalAmount = 0;
        for ( OrderItem oi :  getOrderItems() ){
            totalAmount += Double.valueOf(new DecimalFormat("#0.00").format( oi.getAmount() ));
        }
        totalAmount = Double.valueOf(new DecimalFormat("#0.00").format( totalAmount ));
        this.save();
        return totalAmount;
    }
    public double updateTotalAmount() {
        this.totalAmount = 0;
        for ( OrderItem oi :  getOrderItems() ){
            totalAmount += Double.valueOf(new DecimalFormat("#0.00").format( oi.getAmount() ));
        }
        totalAmount = Double.valueOf(new DecimalFormat("#0.00").format( totalAmount ));
        this.save();
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = Double.valueOf(new DecimalFormat("#0.00").format( totalAmount ));
    }

    public List<OrderItem> getOrderItems() {
        //orderItems = OrderItem.findOrderItemsByOrder( this.getId() );
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getFormatedCreateTime(){
        String date = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a").format( getCreateTime() );
        return date;
    }
    public String getFormatedDeliveryTime(){
        String date = "";
        if( getDeliveryTime() != null ){
            date = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a").format( getDeliveryTime( ) );
        }

        return date;
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
    public String toString(){
        return " Order: " + getId() + " amount: "  + getTotalAmount();
    }
}
