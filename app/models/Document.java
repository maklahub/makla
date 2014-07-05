package models;

import com.avaje.ebean.annotation.EnumMapping;
import com.fasterxml.jackson.annotation.*;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@MappedSuperclass
public class Document extends Model {
    @Id
    private String id = UUID.randomUUID().toString().replaceAll("-","");
    private String reference;
    private String title;
    private String description;
    private String fileName;
    private String url;
    private String documentType;
    private Long fileSize;
    private String keyWords;
    private String extension;
    //@JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    private Album album;
    @OneToMany ( targetEntity= Comment.class,mappedBy = "photo")
    @JsonBackReference
    private List<Comment> comments;
    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private SystemUser owner;
    private Date createTime;
    @Version
    @Column(columnDefinition = "timestamp")
    private Date updateTime;
    @Enumerated(value=EnumType.ORDINAL)
    Status status = Status.active;


    @EnumMapping(nameValuePairs="active = a, inactive = i")
    public enum Status {
        active, inactive
    }

    public Document(){

    }
    public Document( SystemUser owner, String title, String url ){
        setOwner( owner );
        setTitle( title );
        setUrl( url );
        setCreateTime( new Date());
        setStatus( Status.active );
    }

    public static Model.Finder<String, Document> find = new Model.Finder<String, Document>(String.class, Document.class);

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }


    /**
     * Return status.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Set status.
     */
    public void setStatus(Status status) {
        this.status = status;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
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


    public List<Comment> getComments() {
        List <Comment> comments = Comment.getCommentsByPhotoId(this.getId());
        return comments;
    }


    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Date getUpdateTime() {
        return updateTime;
    }
}
