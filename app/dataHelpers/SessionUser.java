package dataHelpers;

import models.Address;
import models.Photo;
import models.SystemUser;


public class SessionUser {

    private String userName ;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private Photo activeProfileImage;
    private Address location;

    public SessionUser( SystemUser systemUser){
        if ( systemUser.isItAPerson()){
            setFirstName( systemUser.getPerson().getFirstName() );
            setLastName( systemUser.getPerson().getLastName() );
            setFullName( getFirstName() + " " + getLastName());
            setEmail( systemUser.getPerson().getEmail() );
        }
        else {
            setFullName( systemUser.getOrganization().getName() );
            setEmail( systemUser.getOrganization().getEmail() );
        }

       setUserName( systemUser.getUserName());

       setActiveProfileImage( SystemUser.getActiveProfilePhoto( systemUser.getId() ) );
       setLocation( systemUser.getAddress() );
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Photo getActiveProfileImage() {
        return activeProfileImage;
    }

    public void setActiveProfileImage(Photo activeProfileImage) {
        this.activeProfileImage = activeProfileImage;
    }

    public Address getLocation() {
        return location;
    }

    public void setLocation(Address location) {
        this.location = location;
    }

    public String toString(){
        return "SessionUser: " + getFirstName() + getLastName() + getActiveProfileImage();
    }
}
