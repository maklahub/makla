package models;


import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.annotation.JsonBackReference;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "orderitems")
public class OrderItem extends Model {
    @Id
    private String id = UUID.randomUUID().toString().replaceAll("-","");
    private String reference ;
    private String name;
    private String Description;
    private double amount;
    private int quantity;
    @OneToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    private Order order;
    @OneToOne(cascade = CascadeType.ALL)
    private Photo orderItemPhoto;
    private Date createTime;
    private Date closeTime;
    @Version
    @Column(columnDefinition = "timestamp")
    private Date updateTime;

    public OrderItem( Order order, String name, String description){
        setOrder( order );
        setName( name );
        setDescription( description );
        setCreateTime( new Date() );
    }

    public OrderItem( Order order, CartItem cartItem){
         setOrder( order );
         setName( cartItem.getName() );
         setDescription( cartItem.getDescription() );
         setOrderItemPhoto( cartItem.getCartItemPhoto() );
         setAmount( cartItem.getPrice() );
         setCreateTime( new Date() );
    }


    public static List<OrderItem> findOrderItemsByOrder( String orderId ){
        List<OrderItem> orderItems = Ebean.find(OrderItem.class).where().eq("order.id", orderId).findList();
        System.out.println("Order Items : ---> " + orderItems);
        return orderItems;
    }

    public String getId() {
        return id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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

    public Photo getOrderItemPhoto() {
        return orderItemPhoto;
    }

    public void setOrderItemPhoto(Photo orderItemPhoto) {
        this.orderItemPhoto = orderItemPhoto;
    }
}