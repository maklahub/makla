package models;

import com.avaje.ebean.Ebean;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "feeds")
public class Feed extends Model {

    @Id
    private String id = UUID.randomUUID().toString().replaceAll("-","");
    @OneToOne(cascade = CascadeType.ALL)
    private SystemUser systemUser;
    private String url;
    private String description;
    @Version
    @Column(columnDefinition = "timestamp")
    private Date createTime;

    public Feed( SystemUser systemUser, String url, String description){
        setUrl(url);
        setDescription(description);
        setCreateTime(new Date());
        setSystemUser(systemUser);
    }

    private static Finder<Long, Feed> find = new Finder<Long, Feed>(Long.class, Feed.class);

    public static List<Feed> getFeeds() {
        List<Feed> feeds = Ebean.find(Feed.class).findList();
        System.out.print(" feeds >>>>>>> " + feeds);
        return feeds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public SystemUser getSystemUser() {
        return systemUser;
    }

    public void setSystemUser(SystemUser systemUser) {
        this.systemUser = systemUser;
    }
}
