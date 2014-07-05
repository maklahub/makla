package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.annotation.EnumMapping;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "personcategories")
public class PersonCategory extends Model {

    @Id
    public String id = UUID.randomUUID().toString().replaceAll("-", "");
    private String reference;
    private String name;
    private String label;
    @ManyToMany( mappedBy = "categories", cascade = CascadeType.ALL )
    public List< Person > persons;
    private Date createTime;
    @Version
    @Column(columnDefinition = "timestamp")
    private Date updateTime;
    public static int count = 0;

    public PersonCategory(){
         setReference( "person-cat-" + count++ );
         setCreateTime( new Date() );
    }


    private static Finder<Long, PersonCategory> find = new Finder<Long, PersonCategory>(Long.class, PersonCategory.class);

    public static List<PersonCategory> getPersonCategories() {
        List<PersonCategory> personCategories = Ebean.find(PersonCategory.class).findList();
        System.out.print(" Person CAtegories >>>>>>> " + personCategories);
        return personCategories;
    }

    public static PersonCategory findPersonCategoryByName( String name){
        return  Ebean.find( PersonCategory.class).where().like("name", name.toLowerCase()).findUnique();
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
        return "Person Category: " + getName() + " " + getLabel() + " " + getReference() ;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
