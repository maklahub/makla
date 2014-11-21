package models;

import com.avaje.ebean.Ebean;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "organizationcategories")
public class OrganizationCategory extends Model {

    @Id
    public String id = UUID.randomUUID().toString().replaceAll("-", "");
    private String reference;
    private String name;
    private String label;
    @Column(columnDefinition = "TEXT")
    private String description;
    @ManyToMany( mappedBy = "categories", cascade = CascadeType.ALL )
    public List< Organization > organizations;
    @Column(columnDefinition = "timestamp")
    private Date createTime;
    public static int count = 0;
    @Version
    public long version;

    public OrganizationCategory(){
         setReference( "org-cat-" + count++ );
         setCreateTime( new Date() );
    }
    private static Finder<Long, OrganizationCategory> find = new Finder<Long, OrganizationCategory>(Long.class, OrganizationCategory.class);

    public static List<OrganizationCategory> getOrganizationCategories() {
        List<OrganizationCategory> orgCategories = Ebean.find(OrganizationCategory.class).findList();
        System.out.print(" Org Categories >>>>>>> " + orgCategories);
        return orgCategories;
    }

    public static OrganizationCategory findOrgCategoryByName( String name){
        return  Ebean.find( OrganizationCategory.class).where().like("name", name.toLowerCase()).findUnique();
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String toString(){
        return "Org Category: " + getName() + " " + getLabel() + " " + getReference() ;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
