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
@Table(name = "menuitems")
public class MenuItem extends Model {
    @Id
    private String id = UUID.randomUUID().toString().replaceAll("-","");
    private String reference ;
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    private Photo menuItemPhoto;
    @Column(columnDefinition = "TEXT")
    private String Description;
    private double price;
    private int quantity;
    @OneToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    private Menu menu;
    private Date createTime;
    private Date closeTime;
    @Version
    @Column(columnDefinition = "timestamp")
    private Date updateTime;

    public MenuItem( String name, Menu menu, double price ){
      setName( name );
      setMenu( menu );
      setPrice( price );
      setCreateTime( new Date() );
    }

    public MenuItem( String name, Menu menu, double price, String description ){
      setName( name );
      setMenu( menu );
      setPrice( price );
      setDescription( description );
      setCreateTime( new Date() );
    }

    private static Finder< String, MenuItem > find = new Finder<String, MenuItem>( String.class, MenuItem.class );

    public static MenuItem findMenuItemById( String id ){
        return Ebean.find( MenuItem.class ).where().like( "id" , id).findUnique();
    }

    public static List<MenuItem> findMenuItemsByMenu( String menuId ){
        List<MenuItem> menuItems = Ebean.find(MenuItem.class).where().eq("menu.id", menuId).findList();
        System.out.println("Menu Item : ---> " + menuItems);
        return menuItems;
    }

    public static List<MenuItem> getAllMenuItems() {
        List<MenuItem> menuItems = Ebean.find(MenuItem.class).findList();
        System.out.print(" All menu Items>>>>>>>  " + menuItems );
        return menuItems;
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

    public Photo getMenuItemPhoto() {
        return menuItemPhoto;
    }

    public void setMenuItemPhoto(Photo menuItemPhoto) {
        this.menuItemPhoto = menuItemPhoto;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
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
        return "Menu Item: " + getName() +  " Menu: " + getMenu() + " Create time: " + getCreateTime();
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
