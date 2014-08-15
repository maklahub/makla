package models;

import com.avaje.ebean.Ebean;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="usertypes")
public class UserType extends Model {

    @Id
    private String id = UUID.randomUUID().toString().replaceAll("-", "");
    private String reference;
    private String name;
    private String label;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Version
    @Column(columnDefinition = "timestamp")
    private Date createTime;

    public UserType(String name,  String ref, String label){
        setName( name );
        setReference( ref );
        setLabel( label );
        setCreateTime( new Date());
    }
    private static Finder<Long, UserType> find = new Finder<Long, UserType>(Long.class, UserType.class);

    public static List<UserType> getUserTypes() {
        List<UserType> userTypes = Ebean.find(UserType.class).findList();
        ///System.out.print(" USer Types >>>>>>> " + userTypes);
        return userTypes;
    }

    public static UserType findUserTypeByName( String name){
        return  Ebean.find( UserType.class).where().like("name", name.toLowerCase()).findUnique();
    }

    public static UserType findUserTypeByReference( String ref){
        return  Ebean.find( UserType.class).where().like( "reference", ref ).findUnique();
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
