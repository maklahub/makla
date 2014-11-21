package models;


import com.avaje.ebean.Ebean;
import com.avaje.ebean.annotation.EnumMapping;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import play.db.ebean.Model;
import play.libs.Json;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "albums")

public class Album extends Model {
    @Id
    private String id = UUID.randomUUID().toString().replaceAll("-","");
    private String reference;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    @OneToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    private SystemUser owner;
    @OneToMany( targetEntity= Photo.class,mappedBy = "album" )
    @JsonIgnore
    private List<Photo> photos;
    private Date createTime;
    @Column(columnDefinition = "timestamp")
    private Date updateTime;
    @Enumerated(value=EnumType.ORDINAL)
    private AlbumType albumType;
    @EnumMapping(nameValuePairs="profile = p, other = o")
    public enum AlbumType {
        profile, other;
    }
    @Version
    public long version;

    public Album( SystemUser owner, String title, String description, AlbumType albumType ){
        setOwner( owner );
        setTitle( title );
        setDescription( description );
        setCreateTime( new Date() );
        setAlbumType( albumType );
    }

    /**
     * Return Album Type.
     */
    public AlbumType getAlbumType() {
        return albumType;
    }

    /**
     * Set Album Type.
     */
    public void setAlbumType(AlbumType albumType) {
        this.albumType = albumType;
    }

    private static Finder<Long, Album> find = new Finder<Long, Album>(Long.class, Album.class);

    public static Album findAlbumById( String id){
        return  Ebean.find(Album.class).where().like( "id", id).findUnique();
    }
    public static Album findProfileAlbumByOwner( String ownerId ){
        return  Ebean.find(Album.class).where().like("owner_id", ownerId).eq("album_type", AlbumType.profile).findUnique();
    }

    public static List<Album> getAlbumsByOwnerId( String id ) {
        List<Album> albums = Ebean.find(Album.class).where().ilike("owner_id", id).findList();
        System.out.print("My albums >>>>>>> " + Json.toJson( albums ).toString());
        return albums;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SystemUser getOwner() {
        return owner;
    }

    public void setOwner(SystemUser owner) {
        this.owner = owner;
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

    public List<Photo> getPhotos(  ) {
         List<Photo> photos  = Photo.getPhotosByAlbumId( this.getId() );
        // System.out.print("Album photos >>>>>>> " + Json.toJson(photos).toString());
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public String toString(){
        return "Album " + getTitle() + " " + getDescription() + " " + getClass();
    }
}
