package models;

import com.avaje.ebean.Ebean;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "systemaccounts")
public class SystemAccount extends Model {

    @Id
    private String id = UUID.randomUUID().toString().replaceAll("-", "");
    @OneToOne(cascade = CascadeType.ALL)
    private SystemUser systemUser;
    private String accountEmail;
    private String accountPassword;
    @Enumerated(EnumType.STRING)
    private AccountType accountType = AccountType.free;
    private Date createTime;
    @Version
    @Column(columnDefinition = "timestamp")
    private Date updateTime;




    public enum AccountType {
        free, gold, platinum;
    }

    public SystemAccount( SystemUser systemUser, String accountEmail, String accountPassword ){
        setSystemUser(systemUser);
        setAccountEmail(accountEmail);
        setAccountPassword( accountPassword );
        setCreateTime( new Date() );

    }

    private static Finder<Long, SystemAccount> find = new Finder<Long, SystemAccount>(Long.class, SystemAccount.class);

    public static SystemAccount findSystemAccountById( String id ){
        return Ebean.find(SystemAccount.class).where().like( "id" , id).findUnique();
    }

    public static SystemAccount findSystemAccountByEmail( String email ){
        return Ebean.find(SystemAccount.class).where().like( "account_email" , email).findUnique();
    }

    public static SystemAccount findSystemAccountByEmailAndPass( String email, String password ){
        return  Ebean.find( SystemAccount.class ).where().like( "account_email", email).eq("account_password", password).findUnique();

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

    public String getId() {
        return id;
    }

    public SystemUser getSystemUser() {
        return systemUser;
    }

    public void setSystemUser(SystemUser systemUser) {
        this.systemUser = systemUser;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getAccountEmail() {
        return accountEmail;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public static SystemAccount findSystemAccountBySystemUserId( String systemUserId ) {
        return  Ebean.find( SystemAccount.class ).where().like( "system_user_id", systemUserId ).findUnique();
    }

    public String toString(){
        return " System account: user: " + getSystemUser() + " email: " + getAccountEmail() ;
    }
}
