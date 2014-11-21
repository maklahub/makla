package models;

import com.avaje.ebean.Ebean;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "availability")
public class Availability extends Model{
    @Id
    private String id = UUID.randomUUID().toString().replaceAll("-", "");
    private String name;
    private String Monday;
    private String Tuesday;
    private String wednesday;
    private String thursday;
    private String friday;
    private String saturday;
    private String sunday;
    @OneToOne(cascade = CascadeType.ALL)
    private SystemUser systemUser;

    public Availability( SystemUser systemUser, String mon, String tues, String wed, String thur, String fri, String sat, String sun){
        setSystemUser( systemUser );
        setMonday( mon );
        setTuesday( tues );
        setWednesday( wed );
        setThursday( thur );
        setFriday( fri );
        setSaturday( sat );
        setSunday( sun );

    }

    public static Availability findAvailabilityByUser( String systemUserId){
        return Ebean.find( Availability.class ).where().like( "systemUser.id" , systemUserId).findUnique();
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

    public String getMonday() {
        return Monday;
    }

    public void setMonday(String monday) {
        Monday = monday;
    }

    public String getTuesday() {
        return Tuesday;
    }

    public void setTuesday(String tuesday) {
        Tuesday = tuesday;
    }

    public String getWednesday() {
        return wednesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }

    public String getThursday() {
        return thursday;
    }

    public void setThursday(String thursday) {
        this.thursday = thursday;
    }

    public String getFriday() {
        return friday;
    }

    public void setFriday(String friday) {
        this.friday = friday;
    }

    public String getSaturday() {
        return saturday;
    }

    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }

    public String getSunday() {
        return sunday;
    }

    public void setSunday(String sunday) {
        this.sunday = sunday;
    }

    public SystemUser getSystemUser() {
        return systemUser;
    }

    public void setSystemUser(SystemUser systemUser) {
        this.systemUser = systemUser;
    }
    public String toString(){
        return "Availability: " + getName() + " Monday: " + getMonday();
    }
}
