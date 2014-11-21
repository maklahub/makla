package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.annotation.EnumMapping;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "accounttype")
public class AccountType extends Model {

    @Id
    private String id = UUID.randomUUID().toString().replaceAll("-", "");
    private String reference;
    private String name;
    private String label;
    private Date createTime;
    public String test;
    @Version
    public long version;

    public AccountType( String name , String ref , String label){
         setName( name );
         setReference( ref );
         setLabel( label );
         setCreateTime( new Date());
    }
    private static Finder<Long, AccountType> find = new Finder<Long, AccountType>(Long.class, AccountType.class);

    public static List<AccountType> getAccountTypes() {
        List<AccountType> accountTypes = Ebean.find(AccountType.class).findList();
        //System.out.print(">>>>>>> " + accountTypes);
        return accountTypes;
    }

    public static AccountType findAccountTypeByName( String name){
        return  Ebean.find( AccountType.class).where().like( "name", name.toLowerCase()).findUnique();
    }

    public static AccountType findAccountTypeByReference( String ref){
        return  Ebean.find( AccountType.class).where().like( "reference", ref ).findUnique();
    }
    

    public Date getCreateTime() {
        return createTime;
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
}
