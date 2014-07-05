package models;

import com.avaje.ebean.Ebean;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "comments")
public class Comment extends Model {

    @Id
    private String id = UUID.randomUUID().toString().replaceAll("-","");
    @OneToOne(cascade = CascadeType.ALL)
    private SystemUser commenter;
    private String description;
    @ManyToOne( cascade = CascadeType.ALL  )
    private Photo photo;
    @OneToOne(cascade = CascadeType.ALL)
    private Video video;
  //  @Version
    @Column(columnDefinition = "timestamp")
    private Date createTime;


    public Comment( Photo photo,  SystemUser commenter, String comment){
       setPhoto( photo );
       setVideo( null );
       setCommenter( commenter );
       setDescription( comment );
       setDateCreated( new Date() );
    }
    public Comment( Video video ,  SystemUser commenter, String comment){
       setVideo( video );
       setPhoto( null );
       setCommenter( commenter );
       setDescription( comment );
       setDateCreated( new Date() );
    }

    private static Finder<Long, Comment> find = new Finder<Long, Comment>(Long.class, Comment.class);

     public static List<Comment> getCommentsByMyPhoto( String myphotoId ) {
        List<Comment> comments = Ebean.find(Comment.class).where().ilike("myphoto_id", myphotoId).orderBy(" createTime asc").findList();
        return comments;
    }
     public static List<Comment> getCommentsByPhotoId( String photoId ) {
        List<Comment> comments = Ebean.find(Comment.class).where().ilike("photo_id", photoId).orderBy(" createTime asc").findList();
        return comments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SystemUser getCommenter() {
        return commenter;
    }

    public void setCommenter(SystemUser commenter) {
        this.commenter = commenter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreated() {
        return createTime;
    }

    public void setDateCreated(Date dateCreated) {
        this.createTime = dateCreated;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public String toString(){
        return "Comment: " + getDescription() + " " +  getCommenter() + ""  + getDateCreated();
    }
}
