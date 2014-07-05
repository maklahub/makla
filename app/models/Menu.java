package models;

import com.avaje.ebean.Ebean;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "menus")
public class Menu extends Model {
    @Id
    private String id = UUID.randomUUID().toString().replaceAll("-","");
    private static String reference ;
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    private SystemUser owner;
    private Photo menuPhoto;
    private String description;
    @ManyToMany( cascade = CascadeType.ALL )
    private List<MenuItem> menuItems;
    @Enumerated(EnumType.STRING)
    private MenuType menuType;  // lunch, dinner, breakfast
    private Date createTime;
    private Date closeTime;
    @Version
    @Column(columnDefinition = "timestamp")
    private Date updateTime;


    public enum MenuType{
        Breakfast, Lunch, Dinner, Other;
    }


    public Menu( String name, SystemUser user ){
      setName( name );
      setOwner( user );
      setCreateTime( new Date() );
    }

    private static Finder< String, Menu > find = new Finder<String, Menu>( String.class, Menu.class );

    public static List<Menu> findMenuByOrganization( String ownerId ){
        List<Menu> menus = Ebean.find(Menu.class).where().eq("owner.id", ownerId).findList();
        System.out.println("Menus: ---> " + menus);
        return menus;
    }

    public static List<Menu> getAllMenus() {
        List<Menu> menus = Ebean.find(Menu.class).findList();
        System.out.print(" All menus >>>>>>>  " + menus );
        return menus;
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

    public Photo getMenuPhoto() {
        return menuPhoto;
    }

    public void setMenuPhoto(Photo menuPhoto) {
        this.menuPhoto = menuPhoto;
    }

    public List<MenuItem> getMenuItems() {
        menuItems = MenuItem.findMenuByMenu( this.getId() );
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
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

    public MenuType getMenuType() {
        return menuType;
    }

    public void setMenuType(MenuType menuType) {
        this.menuType = menuType;
    }

    public String toString(){
        return "Menu: " + getName() +  " Owner: " +getOwner() + " created at " + getCreateTime();
    }


}