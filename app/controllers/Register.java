package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.paypal.core.rest.PayPalRESTException;
import dataHelpers.SessionUser;
import models.*;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import play.data.DynamicForm;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import views.html.register;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static play.data.Form.form;

public class Register extends Controller {
    public static Result index() {
        //DynamicForm requestData = form().bindFromRequest();
        //String firstName = requestData.get("firstName");
        //tring lastName = requestData.get("lastName");
        //String name = requestData.get("businessName");
       // String email = requestData.get("email");
        //String userType = requestData.get("userType");
        //List<UserType> userTypes = UserType.getUserTypes();
        //List<PersonCategory> personCategories = PersonCategory.getPersonCategories();
        //List<OrganizationCategory> orgCategories = OrganizationCategory.getOrganizationCategories();
        //String userTypesAsJson = Json.toJson(userTypes).toString();
        //String personCategoriesAsJson = Json.toJson(personCategories).toString();
       // String orgCategoriesAsJson = Json.toJson(orgCategories).toString();

        UserType userType = UserType.findUserTypeByName( "customer" );
        return ok(register.render( "", userType ));
    }

    public static Result MerchantAccountSetUp() {
        //DynamicForm requestData = form().bindFromRequest();
        //String firstName = requestData.get("firstName");
        //tring lastName = requestData.get("lastName");
        //String name = requestData.get("businessName");
       // String email = requestData.get("email");
        //String userType = requestData.get("userType");
        //List<UserType> userTypes = UserType.getUserTypes();
        //List<PersonCategory> personCategories = PersonCategory.getPersonCategories();
        //List<OrganizationCategory> orgCategories = OrganizationCategory.getOrganizationCategories();
        //String userTypesAsJson = Json.toJson(userTypes).toString();
        //String personCategoriesAsJson = Json.toJson(personCategories).toString();
       // String orgCategoriesAsJson = Json.toJson(orgCategories).toString();

        UserType userType = UserType.findUserTypeByName( "merchant" );
        return ok( views.html.account.merchantAccountSetup.render( "", userType ));
    }


    public static Result completeRegistration() throws IOException, EmailException {
        String baseUrl = request().path();
        DynamicForm requestData = form().bindFromRequest();
        String name = requestData.get("businessName");
        String firstName = requestData.get("firstName");
        String lastName = requestData.get("lastName");
        String accountEmail = requestData.get("email").trim().toLowerCase();
        String password = requestData.get("password");
        String userName = requestData.get("userName");
        String phoneNumber = requestData.get("phoneNumber");
        /// String creditCardNumber = requestData.get("creditCardNumber");
        //String cvv = requestData.get("CVV");
        // String expirationDate = requestData.get("expirationDate");
        //String personCategory = requestData.get("personCategory");
        //String orgCategory = requestData.get("orgCategory");
        // String sex = requestData.get("sex");
        //System.out.println(" Gender: ---> " + sex.trim() );
        String userType = requestData.get("userType");
        String address = requestData.get("address");
        String subAddress = requestData.get("subAddress");
        String city = requestData.get("city");
        String zip = requestData.get("zipCode");
        String state = requestData.get("state");
        String country = requestData.get("country");
        UserType systemUserType = UserType.findUserTypeByName(userType);

        if ( checkAccountByEmailExist( accountEmail ) == true ){
            List<UserType> userTypes = UserType.getUserTypes();
            List<PersonCategory> personCategories = PersonCategory.getPersonCategories();
            List<OrganizationCategory> orgCategories = OrganizationCategory.getOrganizationCategories();
            String userTypesAsJson = Json.toJson(userTypes).toString();
            String personCategoriesAsJson = Json.toJson(personCategories).toString();
            String orgCategoriesAsJson = Json.toJson(orgCategories).toString();
            // return ok( " Account already exist with the email " + accountEmail );
            return ok(register.render("Account already exist with the email " + accountEmail , systemUserType));


        }


        Address systemUserAddress = new Address(address, subAddress, city, state, zip,  country);

        SystemUser u = null;
        if (name != null) {
            //OrganizationCategory systemOrgCategory = OrganizationCategory.findOrgCategoryByName(orgCategory);
            Organization o = new Organization(name, accountEmail);
            o.setPhoneNumber( phoneNumber );
            //o.setCategory(systemOrgCategory);
            u = new SystemUser(o, password, systemUserType);
        } else {
            //  PersonCategory systemPersonCategory = PersonCategory.findPersonCategoryByName( personCategory );
            Person p = new Person(firstName, lastName, accountEmail);
            p.setPhoneNumber( phoneNumber );
            /*
             p.setCategory( systemPersonCategory );

            if ( sex.trim().equalsIgnoreCase("female")){
                p.setGender( Person.Sex.Female );
            }
            else if ( sex.trim().equalsIgnoreCase("Male")){
                p.setGender( Person.Sex.Male );
            }
            else {
                p.setGender( Person.Sex.Other );
            }
            **/
            u = new SystemUser(p, password, systemUserType);
        }

        //String result = SaveCreditCard.saveCard( firstName,lastName, creditCardNumber, 11,2018);

        // System.out.println(" Result ------------> " + result );
        systemUserAddress.setSystemUser(u);
        systemUserAddress.save();
        u.setAddress(systemUserAddress);

        List<Address> shippingAddresses = u.getShippingAddresses();
        shippingAddresses.add(systemUserAddress);




        u.setUserName(userName);
        u.update();


        // Save the file in AWS
        MultipartFormData b = request().body().asMultipartFormData();
        // System.out.print(b);
        FilePart picture = b.getFile("profileImage");
        if (picture != null) {
            S3File s3File = new S3File();
            s3File.name = picture.getFilename().trim();
            s3File.file = picture.getFile();
            s3File.save();

            //ProfileImage profileImage = new ProfileImage( s3File.getUrl().toString(), s3File.name);
            Album profileAlbum = new Album(u, "Profile album ", " Profile album description ", Album.AlbumType.profile);
            Photo profilePhoto = new Photo(u, "profile photo", s3File.getUrl().toString(), profileAlbum);
            profilePhoto.setTitle(" Profile Photo");
            //  u.setActiveProfileImage(profilePhoto);
            u.setProfileImageId(profilePhoto.getId());
            u.setProfileImageUrl(profilePhoto.getUrl());
            String photoUrl = s3File.getUrl().toString();
            Feed feed = new Feed(u, photoUrl, " Text text...");
            feed.save();
            profilePhoto.save();

            SessionUser sessionUser = new SessionUser(u);
            session("sessionUser", Json.toJson(sessionUser).toString());
            session("currentUserId", u.getId());
            sendHtmlasEmail(u, photoUrl, accountEmail);

            if (u.isItAPerson()) {
                return redirect(controllers.routes.Application.index());
            } else {
                return redirect(controllers.routes.Application.home());
            }
        } else {
            System.out.print("Missing file");
            flash("error", "Missing file");
            return redirect(routes.Application.index());
        }
    }

    public static Result addVideo() {
        DynamicForm requestData = form().bindFromRequest();
        SystemUser u = SystemUser.findUserById(session("currentUserId"));
        String videoLink = requestData.get("videoLink");
        String videoTitle = requestData.get("video-title");
        String videoDescription = requestData.get("videoDescription");
        Video video = new Video(u, videoTitle, videoLink);
        // video.setOwner(u);
        video.setDescription(videoDescription);
        video.save();
        return redirect(routes.Application.myVideos());

    }

    public static Result saveBusinessHours(){
        String currentUserId = session("currentUserId");
        DynamicForm requestData = form().bindFromRequest();
        String mon = requestData.get("monday");
        String tue = requestData.get("tuesday");
        String wed = requestData.get("wednesday");
        String thur = requestData.get("thursday");
        String fri= requestData.get("friday");
        String sat = requestData.get("saturday");
        String sun = requestData.get("sunday");
        Availability availability = Availability.findAvailabilityByUser( currentUserId );
        availability.setMonday( mon );
        availability.setTuesday( tue );
        availability.setWednesday( wed );
        availability.setThursday( thur );
        availability.setFriday( fri );
        availability.setSaturday( sat );
        availability.setSunday( sun );
        availability.update();

        return redirect( routes.Application.myProfile());
    }



    public static Result saveProfileData() throws MalformedURLException {
        String currentUserId = session("currentUserId");
        SystemUser currentUser = SystemUser.findUserById(currentUserId);
        String baseUrl = request().path();
        DynamicForm requestData = form().bindFromRequest();
        String name = requestData.get("businessName");
        String firstName = requestData.get("firstName");
        String lastName = requestData.get("lastName");
        String userName = requestData.get("userName");
        String phoneNumber = requestData.get("phoneNumber");
      //  String selectedPersonCategory = requestData.get("personCategory");
        //String selectedOrgCategory = requestData.get("orgCategory");
        String sex = requestData.get("sex");
        //System.out.println(" Gender: ---> " + sex.trim() );
        // String userType = requestData.get("userType" );
        String address = requestData.get("address");
        String subAddress = requestData.get("subAddress");
        String zip = requestData.get("zipCode");
        System.out.println( "\n\n Zip:------> " + zip);
        String city = requestData.get("city");
        String state = requestData.get("state");
        String country = requestData.get("country");
        currentUser.getAddress().setAddress(address);
        currentUser.getAddress().setSubAddress(subAddress);
        currentUser.getAddress().setCity(city);
        currentUser.getAddress().setState(state);
        currentUser.getAddress().setZip(zip);
        currentUser.getAddress().setCountry(country);
        currentUser.getAddress().save();
        if (currentUser.isItAPerson()) {
           // PersonCategory personCategory = PersonCategory.findPersonCategoryByName(selectedPersonCategory);
           // currentUser.getPerson().setCategory(personCategory);
            currentUser.getPerson().setFirstName(firstName);
            currentUser.getPerson().setLastName(lastName);
            currentUser.getPerson().setPhoneNumber( phoneNumber );
           // currentUser.setUserName(userName);


        } else {
           // OrganizationCategory orgCategory = OrganizationCategory.findOrgCategoryByName(selectedOrgCategory);
           // currentUser.getOrganization().setCategory(orgCategory);
            currentUser.getOrganization().setName(name);
            currentUser.getOrganization().setPhoneNumber( phoneNumber );
           // currentUser.setUserName(userName);
        }


        // Save the file in AWS
        MultipartFormData b = request().body().asMultipartFormData();
        // System.out.print(b);
        FilePart picture = b.getFile("profileImage");
        if (picture != null) {
            S3File s3File = new S3File();
            s3File.name = picture.getFilename().trim();
            s3File.file = picture.getFile();
            s3File.save();

            //ProfileImage profileImage = new ProfileImage( s3File.getUrl().toString(), s3File.name);
            // Album profileAlbum = new Album( currentUser , "Profile album ", " Profile album description ", Album.AlbumType.profile );
            Album profileAlbum = Album.findProfileAlbumByOwner(currentUser.getId());
            Photo profilePhoto = new Photo(currentUser, " 1 New profile photo", s3File.getUrl().toString(), profileAlbum);
            profilePhoto.setTitle(" New Profile Photo");
            //  u.setActiveProfileImage(profilePhoto);
            currentUser.setProfileImageId(profilePhoto.getId());
            currentUser.setProfileImageUrl(profilePhoto.getUrl());
            String photoUrl = s3File.getUrl().toString();
            System.out.println("Photo URL: " + photoUrl);
            // Feed feed = new Feed( u, photoUrl , " Text text...") ;
            // feed.save();
            profilePhoto.save();
        }
        currentUser.save();
        session().remove("sessionUser");
        SessionUser sessionUser = new SessionUser(currentUser);
        session("sessionUser", Json.toJson(sessionUser).toString());
        session("currentUserId", currentUser.getId());

        return redirect(routes.Application.myProfile());
    }


    public static boolean checkAccountByEmailExist(String email) {
        boolean result = false;
        List<SystemAccount> systemAccounts = SystemAccount.findSystemAccountsListByEmail( email );
        return systemAccounts.size() >= 1;
    }

    public static void sendHtmlasEmail(SystemUser u, String photoUrl, String accountEmail) throws EmailException, MalformedURLException {
        // Create the email message
        HtmlEmail email = new HtmlEmail();
        email.setHostName("mail.privateemail.com");
        //email.setHostName("smtp.gmail.com");
        email.setSmtpPort(465);
        email.setDebug(true);
        email.setAuthenticator(new DefaultAuthenticator("support@discoverydelivery.com", "12344321"));

        /*
        email.getMailSession().getProperties().put("mail.smtps.auth", "true");
        email.getMailSession().getProperties().put("mail.debug", "true");
        email.getMailSession().getProperties().put("mail.smtps.port", "465");
        email.getMailSession().getProperties().put("mail.smtps.socketFactory.port", "587");
        email.getMailSession().getProperties().put("mail.smtps.socketFactory.class",   "javax.net.ssl.SSLSocketFactory");
        email.getMailSession().getProperties().put("mail.smtps.socketFactory.fallback", "false");
        email.getMailSession().getProperties().put("mail.smtp.starttls.enable", "true");

        **/

        email.setSSLOnConnect(true);
        email.setFrom("support@discoverydelivery.com");
        email.setSubject("Welcome to ArtistaOne");
        String baseUrl = request().host();


        // embed the image and get the content id
        URL url = new URL(photoUrl);
        //  String cid = email.embed(url, u.getFullName());

        // set the html message
        //email.setHtmlMsg("<html><h1>Welcome to ArtistaOne</h1>   <img src=\"cid:"+cid+"\">  <hr><hr></html>");
        email.setHtmlMsg("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title> Welcome to Discovery Delivery </title>\n" +
                "    <script type=\"text/javascript\" src=\"http://code.jquery.com/jquery-latest.js\">  </script>\n" +
                "\n" +
                "</head>\n" +
                "<body style=\"font-family: Helvetica Neue, Helvetica, Arial, sans-serif;font-size: 15px;line-height: 23px;color: #333333;font-weight: 400 !important;text-shadow: 1px 1px 2px white;\">\n" +
                "\n" +
                "<div style=\"background: #E6E4E4; min-width: 300px; width:100%; max-width: 900px; margin: auto;border-radius: 5px 5px 0px 0px;border: solid thin #e1e1e1;box-shadow: 1px 1px 2px #C7C6C6;\">\n" +
                "    <div style=\" margin: 0; padding: 0px 5px;\">\n" +
                "        <div style=\"float: left;\" ><h1 style=\"font-size: 14pt; color: #333;\"> " + u.getFullName() + ", Welcome to ArtistaOne</h1></div>\n" +
                "        <div style=\"float: right;\" > <h1 style=\"font-size: 14pt; color: #333;\">ArtistaOne</h1></div>\n" +
                "    </div>\n" +
                "    <hr style=\"border-bottom: solid thin #FFFFFF;border-top: solid thin rgb(196, 196, 196);clear: both;margin: 0 0px;padding: 0px;\">\n" +
                "    <div style=\" margin: 0; padding: 0px 5px;\"  >\n" +
                "        <p>Hi " + u.getFullName() + ",</p>\n" +
                "        <p>Welcome to Discovery Delivery </p> </div>\n" +
                "    <hr style=\"border-bottom: solid thin #FFFFFF;border-top: solid thin rgb(196, 196, 196);clear: both;margin: 0 0px;padding: 0px;\">\n" +
                "    <div style=\" margin: 0; padding: 0px 5px;\">\n" +
                "        <div style=\"width: 300px;margin: auto;margin-top: 20px;margin-bottom: 20px;border: solid thin #C9C9C9;box-shadow: 1px 1px 2px #D6D2D2;border-radius: 6px;overflow: hidden;\">\n" +
                "           <a href=\" http://" + request().host() + "/profile/" + u.getUserName() + "\"><img style=\"width: 100%;height: auto;\" src=\" " + photoUrl + "\"> </a>\n" +
                "           <span  style=\" text-align: center;font-size: 16pt;display: block;padding: 2px;background: rgb(48, 48, 48);color: white;text-shadow: 1px 1px 2px #333;\"><a href=\" http://" + request().host() + "/profile/" + u.getUserName() + "\">  " + u.getFullName() + "</a></span>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");

        // set the alternative message
        email.setTextMsg("Your email client does not support HTML messages");
        //  email.addTo( "berberacrobat@gmail.com" );
        email.addTo(accountEmail);
        // send the email
        email.send();
    }


}
