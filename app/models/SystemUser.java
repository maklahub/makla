package models;

import com.avaje.ebean.Ebean;
import play.db.ebean.Model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "systemusers")
public class SystemUser extends Model {

    @Id
    private String id = UUID.randomUUID().toString().replaceAll("-","");
    private String reference ;
    @OneToOne(cascade = CascadeType.ALL)
    private Person person;
    @OneToOne(cascade = CascadeType.ALL)
    private MaklaCreditCard mainCreditCard;
    private String userName;
    @OneToOne(cascade = CascadeType.ALL)
    private Organization organization;
    @OneToOne(cascade = CascadeType.ALL)
    private UserType userType;
    private String description;
   // @OneToOne(cascade = CascadeType.ALL)
   // private Photo activeProfileImage;
    private String profileImageId;
    private String profileImageUrl;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @OneToOne(cascade = CascadeType.ALL)
    private Address billingAddress;
    @ManyToMany( cascade = CascadeType.ALL )
    private List<Address> shippingAddresses;
    private Date createTime;
    @Column(columnDefinition = "timestamp")
    private Timestamp updateTime;
    @Version
    public long version;



    public SystemUser( Person person, String password, UserType userType ){
        List<SystemUser> o = Ebean.find( SystemUser.class).orderBy().desc("createTime").findList();
        String ref = "";


        if ( o.size() == 0){
            ref = String.valueOf( 111 );
        }
        else {
            SystemUser lastInsertedUser =  o.get( 0 );
            ref = String.valueOf( Integer.valueOf(lastInsertedUser.getReference()) + 1 );
        }

          setReference( ref  );
          setOrganization(null);
          setPerson(person);
          setUserType(userType);
         // setCreateTime(new Date());
          createSystemAccount(person.getEmail(), password);
    }

    public SystemUser( Organization organization, String password, UserType userType ){
        List<SystemUser> o = Ebean.find( SystemUser.class).orderBy().desc("createTime").findList();
        String ref = "";


        if ( o.size() == 0){
            ref = String.valueOf( 111 );
        }
        else {
            SystemUser lastInsertedUser =  o.get( 0 );
            ref = String.valueOf( Integer.valueOf(lastInsertedUser.getReference()) + 1 );
            System.out.println( "last inserted user + " +  lastInsertedUser);

        }

          setReference( ref  );
          setPerson(null);
          setOrganization(organization);
          setUserType(userType);
         // setCreateTime(new Date());
          createSystemAccount(organization.getEmail(), password);
    }

    public SystemUser(){

    }

    private static Finder<Long, SystemUser> find = new Finder<Long, SystemUser>(Long.class, SystemUser.class);

    public static List<SystemUser> getSystemUsers() {
        List<SystemUser> users = Ebean.find(SystemUser.class).findList();
       // System.out.print(">>>>>>> " + artistas);
        return users;
    }

    public static SystemUser findSystemUserByUserName ( String userName){
        return  Ebean.find( SystemUser.class ).where().like( "userName", userName).findUnique();
    }

    public static SystemUser findSystemUserByReference ( String reference){
        return  Ebean.find( SystemUser.class ).where().like( "reference", reference).findUnique();
    }

    public static List<SystemUser> findAllOrganizations(){
        List<SystemUser> organizations = Ebean.find( SystemUser.class).where().eq("person", null).findList();
        System.out.println( "Organizations size: " +  organizations.size());
        return organizations;
    }

    public static List<SystemUser> findByName( String q){
        //List<SystemUser> artistas = Ebean.find(SystemUser.class).where(Expr.or(Expr.ilike("firstName", "%" + q + "%"), Expr.ilike("lastName", "%" + q+ "%"))).findList();
        List<SystemUser> users = Ebean.find(SystemUser.class).where().ilike("userName", "%" + q+ "%").findList();
        return users;
    }

    public static List<SystemUser> findByQuery( String q){
        //List<SystemUser> artistas = Ebean.find(SystemUser.class).where(Expr.or(Expr.ilike("firstName", "%" + q + "%"), Expr.ilike("lastName", "%" + q+ "%"))).findList();
        List<SystemUser> users = Ebean.find(SystemUser.class).where().ilike("userName", "%" + q+ "%").findList();
       // List<SystemUser> artistas =Ebean.find(SystemUser.class)
                     //   .where(Expr.or( Expr.or(Expr.ilike("person.firstName","%" + q + "%"),Expr.ilike("person.lastName", "%" + q+ "%")),
                     //   Expr.or(Expr.ilike("userType.label","%" + q + "%"),Expr.ilike("organization.name", "%" + q+ "%")))).where().ilike("person.address.city","%")
                     //   .findList();
        return users;
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
        return " SystemUser: " + getId() + "Reference: " + getReference() +  " Name: " + getFullName() + " " + getUserName() + " Email: " ;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public List<Address> getShippingAddresses() {
        return shippingAddresses;
    }

    public void setShippingAddresses(List<Address> shippingAddresses) {
        this.shippingAddresses = shippingAddresses;
    }


    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
