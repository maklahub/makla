package models;

import com.avaje.ebean.Ebean;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "orders")
public class Order extends Model {
    @Id
    private String id = UUID.randomUUID().toString().replaceAll("-","");
    private static String reference ;
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    private SystemUser owner;
    private String description;
    private double totalAmount;
    @ManyToMany( cascade = CascadeType.ALL )
    private List<OrderItem> orderItems;
    private Date createTime;
    private Date closeTime;
    @Version
    @Column(columnDefinition = "timestamp")
    private Date updateTime;

    public Order( SystemUser owner){
     setOwner( owner );
     setCreateTime( new Date() );
    }
    private static Finder< String, Order > find = new Finder<String, Order>( String.class, Order.class );

    public static List<Order> findOrdersBySystemUser( String ownerId ){
        List<Order> orders  = Ebean.find(Order.class).where().eq("owner.id", ownerId).findList();
        System.out.println("Orders: ---> " + orders);
        return orders;
    }

    public static String getReference() {
        return reference;
    }

    public static void setReference(String reference) {
        Order.reference = reference;
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
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<OrderItem> getOrderItems() {
        orderItems = OrderItem.findOrderItemsByOrder( this.getId() );
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
}
