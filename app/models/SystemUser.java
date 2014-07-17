package models;

import com.avaje.ebean.Ebean;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "systemusers")
public class SystemUser extends Model {

    @Id
    private String id = UUID.randomUUID().toString().replaceAll("-","");
    @OneToOne(cascade = CascadeType.ALL)
    private Person person;
    @OneToOne(cascade = CascadeType.ALL)
    private MaklaCreditCard mainCreditCard;
    private String userName;
    @OneToOne(cascade = CascadeType.ALL)
    private Organization organization;
    @OneToOne(cascade = CascadeType.ALL)
    private UserType userType;
   // @OneToOne(cascade = CascadeType.ALL)
   // private Photo activeProfileImage;
    private String profileImageId;
    private String profileImageUrl;
    private Date createTime;
    @Version
    @Column(columnDefinition = "timestamp")
    private Date updateTime;

    public SystemUser( Person person, String password, UserType userType ){
          setOrganization( null );
          setPerson( person );
          setUserType( userType );
          setCreateTime( new Date());
          createSystemAccount( person.getEmail(), password);
    }

    public SystemUser( Organization organization, String password, UserType userType ){
          setPerson( null );
          setOrganization( organization );
          setUserType( userType );
          setCreateTime( new Date() );
          createSystemAccount( organization.getEmail(), password );
    }

    private static Finder<Long, SystemUser> find = new Finder<Long, SystemUser>(Long.class, SystemUser.class);

    public static List<SystemUser> getSystemUsers() {
        List<SystemUser> artistas = Ebean.find(SystemUser.class).findList();
       // System.out.print(">>>>>>> " + artistas);
        return artistas;
    }

    public static SystemUser findSystemUserByUserName ( String userName){
        return  Ebean.find( SystemUser.class ).where().like( "userName", userName).findUnique();
    }

    public static List<SystemUser> findByName( String q){
        //List<SystemUser> artistas = Ebean.find(SystemUser.class).where(Expr.or(Expr.ilike("firstName", "%" + q + "%"), Expr.ilike("lastName", "%" + q+ "%"))).findList();
        List<SystemUser> artistas = Ebean.find(SystemUser.class).where().ilike("userName", "%" + q+ "%").findList();
        return artistas;
    }

    public static List<SystemUser> findByQuery( String q){
        //List<SystemUser> artistas = Ebean.find(SystemUser.class).where(Expr.or(Expr.ilike("firstName", "%" + q + "%"), Expr.ilike("lastName", "%" + q+ "%"))).findList();
        List<SystemUser> artistas = Ebean.find(SystemUser.class).where().ilike("userName", "%" + q+ "%").findList();
       // List<SystemUser> artistas =Ebean.find(SystemUser.class)
                     //   .where(Expr.or( Expr.or(Expr.ilike("person.firstName","%" + q + "%"),Expr.ilike("person.lastName", "%" + q+ "%")),
                     //   Expr.or(Expr.ilike("userType.label","%" + q + "%"),Expr.ilike("organization.name", "%" + q+ "%")))).where().ilike("person.address.city","%")
                     //   .findList();
        System.out.println( "\n artista foubd : \n " + artistas);
        return artistas;
    }

    public static SystemUser findUserById( String id){
        return  Ebean.find( SystemUser.class).where().like( "id", id).findUnique();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



    public String getId() {
        return id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isItAPerson(){
        return this.getOrganization() == null && this.getPerson() != null;
    }

    public boolean isItAOrganization(){
        return this.getPerson() == null && this.getOrganization() != null;
    }

    public String getFullName(){
        if ( this.isItAPerson()){
            return this.getPerson().getFullName() ;
        }
        return this.getOrganization().getName();

    }
    public Address getLocation(){
        if ( this.isItAPerson() ){
            return this.getPerson().getAddressId() ;
        }
        return this.getOrganization().getAddress();

    }

    private SystemAccount createSystemAccount( String email, String password ){
        SystemAccount systemAccount = new SystemAccount( this, email, password );
        systemAccount.save();
        return systemAccount;
    }

    public static List<Photo> getProfilePhotos( String ownerId ){
        Album profileAlbum = Album.findProfileAlbumByOwner( ownerId );
       // System.out.println( "Profile album:  --------- " + profileAlbum);
        List<Photo> photos = profileAlbum.getPhotos();
       // System.out.println( "Photos Profile album:  --------- " + photos );
        return photos;
        }

    public static Photo getActiveProfilePhoto( String ownerId ){
        Photo profilePhoto = null;
        List<Photo> photos = getProfilePhotos( ownerId );
        for ( Photo photo : photos ){
            System.out.println( "Photo: " + photo);
            if ( photo.getStatus() == Photo.Status.active ){
                System.out.println( "profile photo has been set .............." );
                profilePhoto = photo;
            }
        }
        return profilePhoto;
    }

    public String toString(){
        return " SystemUser: " + getId() + " Name: " + getPerson() + " " + getUserName() + " Email: " ;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }


   /* public Photo getActiveProfileImage() {
        return activeProfileImage;
    }

    public void setActiveProfileImage(Photo activeProfileImage) {
        this.activeProfileImage = activeProfileImage;
    }

    */


    public String getProfileImageId() {
        return profileImageId;
    }

    public void setProfileImageId(String profileImageId) {
        this.profileImageId = profileImageId;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public MaklaCreditCard getMainCreditCard() {
        return mainCreditCard;
    }

    public void setMainCreditCard(MaklaCreditCard mainCreditCard) {
        this.mainCreditCard = mainCreditCard;
    }
}
