package models;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.annotation.JsonBackReference;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "creditcards")

public class MaklaCreditCard extends Model {

    @Id
    private String id = UUID.randomUUID().toString().replaceAll("-","");
    private static String reference ;
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    private SystemUser owner;
    private String payPalCreditCardId;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String creditCardNumber;
    private Date createTime;
    private Date closeTime;
    @Column(columnDefinition = "timestamp")
    private Date updateTime;
    @Version
    public long version;

    public MaklaCreditCard( SystemUser owner, String payPalCardId , String cardNumber ){
        setCreateTime( new Date()) ;
        setPayPalCreditCardId( payPalCardId );
        setCreditCardNumber( cardNumber );
        setOwner( owner );
        owner.setMainCreditCard( this );
        owner.save();
    }

    private static Finder< String, MaklaCreditCard > find = new Finder<String, MaklaCreditCard>( String.class, MaklaCreditCard.class );


    public static List<MaklaCreditCard> findCreditCardsBySystemUser( String ownerId ){
        List<MaklaCreditCard> cards = Ebean.find(MaklaCreditCard.class).where().eq("owner.id", ownerId).findList();
        System.out.println("\nMakla credit cards: ---> \n" + cards);
        return cards;
    }

    public static String getReference() {
        return reference;
    }

    public static void setReference(String reference) {
        MaklaCreditCard.reference = reference;
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

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
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

    public String getPayPalCreditCardId() {
        return payPalCreditCardId;
    }

    public void setPayPalCreditCardId(String payPalCreditCardId) {
        this.payPalCreditCardId = payPalCreditCardId;
    }
}
