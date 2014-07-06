package models;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "cartitems")
public class CartItem extends Model {
    @Id
    private String id = UUID.randomUUID().toString().replaceAll("-","");
    private String reference ;
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    private Photo cartItemPhoto;
    private String Description;
    private double price;
    private int quantity;
    @OneToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    private Cart cart;
    private Date createTime;
    private Date closeTime;
    @Version
    @Column(columnDefinition = "timestamp")
    private Date updateTime;
    String test;


    public CartItem( String name, Cart cart, double price ){
        setName( name );
        setCart( cart );
        setPrice( price );
        setCreateTime( new Date() );
    }

    public CartItem( Cart cart, MenuItem menuItem){
        setCart( cart );
        setName( menuItem.getName() );
        setDescription( menuItem.getDescription() );
        setPrice( menuItem.getPrice() );
        setCartItemPhoto( menuItem.getMenuItemPhoto() );
        setCreateTime( new Date() );
    }

    private static Finder< String, CartItem > find = new Finder<String, CartItem>( String.class, CartItem.class );

    public static CartItem findCartItemById( String id ){
        return Ebean.find( CartItem.class ).where().like( "id" , id).findUnique();
    }

    public static List<CartItem> findCartItemsByCart( String cartId ){
        List<CartItem> cartItems = Ebean.find(CartItem.class).where().eq("cart.id", cartId).findList();
        System.out.println("cart Items : ---> " + cartItems);
        return cartItems;
    }

    public static List<CartItem> getAllCartItems() {
        List<CartItem> cartItems = Ebean.find(CartItem.class).findList();
        System.out.print(" All cart Items>>>>>>>  " + cartItems );
        return cartItems;
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

    public Photo getCartItemPhoto() {
        return cartItemPhoto;
    }

    public void setCartItemPhoto(Photo cartItemPhoto) {
        this.cartItemPhoto = cartItemPhoto;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
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
        return "Cart Item: " + getName() +  " Cart: " + getCart() + " Create time: " + getCreateTime();
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
