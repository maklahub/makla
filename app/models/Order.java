package models;

import com.avaje.ebean.Ebean;
import play.db.ebean.Model;

import javax.persistence.*;
import java.text.DecimalFormat;
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
    private SystemUser owner;
    private String description;
    private double totalAmount;
    @ManyToMany( cascade = CascadeType.ALL )
    private List<OrderItem> orderItems;
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.draft;
    private Date createTime;
    private Date closeTime;
    @Version
    @Column(columnDefinition = "timestamp")
    private Date updateTime;

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public enum OrderStatus {
        draft, paid, confirmed, complete;
    }

    public Order( SystemUser owner ){
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
        setOwner(owner);
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
        setOwner(owner);
        setCreateTime(new Date());
    }

    public  List<OrderItem> createOrderItems( Cart cart ){
        List<OrderItem> orderItems = this.getOrderItems();
        for ( CartItem cartItem : cart.getCartItems() ){
             OrderItem orderItem = new OrderItem( this , cartItem );
            // orderItem.save();
             orderItems.add( orderItem );
        }
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

    public static List<Order> findOrdersBySystemUser( String ownerId ){
        List<Order> orders  = Ebean.find(Order.class).where().eq("owner.id", ownerId).findList();
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

    public double getTotalAmount() {
        this.totalAmount = 0;
        for ( OrderItem oi :  getOrderItems() ){
            totalAmount += oi.getAmount();
        }
        totalAmount = Double.parseDouble(new DecimalFormat("#0.00").format( totalAmount ));
        this.save();
        return totalAmount;
    }
    public double updateTotalAmount() {
        this.totalAmount = 0;
        for ( OrderItem oi :  getOrderItems() ){
            totalAmount += oi.getAmount();
        }
        totalAmount = Double.parseDouble(new DecimalFormat("#0.00").format( totalAmount ));
        this.save();
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
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
