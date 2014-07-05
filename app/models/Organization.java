package models;


import com.avaje.ebean.Ebean;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "organizations")
public class Organization extends Model {

    @Id
    private String id = UUID.randomUUID().toString().replaceAll("-", "");
    private String name;
    private String email;
    private String www;
    private String phoneNumber;
    @ManyToMany( mappedBy = "organizations", cascade = CascadeType.ALL )
    private List<Person> persons;
    @OneToOne(cascade = CascadeType.ALL)
    private OrganizationCategory category;
    @ManyToMany( cascade = CascadeType.ALL )
    private List< OrganizationCategory >  categories;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @OneToOne(cascade = CascadeType.ALL)
    private Address billingAddress;
    @OneToOne(cascade = CascadeType.ALL)
    private Address shippingAddress;
    private Date createTime;
    @Version
    @Column(columnDefinition = "timestamp")
    private Date updateTime;

    private static Finder<String , Organization> find = new Finder<String, Organization>(String.class, Organization.class);

    public Organization( String name ){
        setName( name );
        setCreateTime( new Date() );
    }

    public Organization( String name, String email, String phoneNumber ){
        setName( name );
        setEmail( email );
        setPhoneNumber( phoneNumber );
        setCreateTime( new Date() );
    }
    public Organization( String name, String email ){
        setName( name );
        setEmail( email );
        setCreateTime( new Date() );
    }

    public static Organization findOrganizationById( String organizationId){
        return Ebean.find( Organization.class ).where().like( "id" , organizationId).findUnique();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWww() {
        return www;
    }

    public void setWww(String www) {
        this.www = www;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public String toString(){
        return "Organization: " + getName() + " " + getEmail() + " " + getCreateTime();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<OrganizationCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<OrganizationCategory> categories) {
        this.categories = categories;
    }

    public OrganizationCategory getCategory() {
        return category;
    }

    public void setCategory(OrganizationCategory category) {
        this.category = category;
    }
}

