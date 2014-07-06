package controllers;

import dataHelpers.ProfileData;
import models.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import play.libs.Json;
import java.util.List;

import static play.data.Form.*;


public class Application extends Controller {



    public static Result index1(){

        String menus = Json.toJson( Menu.getAllMenus() ).toString();
        //System.out.print( allArtistas );
        //   return ok(views.html.index.render( artistasAsJson, userTypesAsJson));
       // return ok( menus );
        return ok( views.html.index.render( menus ) );


    }

    public static Result index() {
        /*Person p = new Person("Elhassan", "Rais", "b@b.com");
        Organization o = new Organization( "American Natural Food and Cafe") ;
        SystemUser su = new SystemUser( o , "1234", null );
        su.save();

        Menu m = new Menu( "Moroccan Cuisine ", su);
        MenuItem mi = new MenuItem( "Tomato a la mode", m, 9.99);
        MenuItem mi2 = new MenuItem( "Couscouss a la mode", m, 9.99);

        mi.save();
        mi2.save();

        */

      /*  if ( session("sessionUser") != null){
            return redirect( controllers.routes.Application.home() );
        } **/
        List< UserType > userTypes = UserType.getUserTypes();
        List< AccountType > accountTypes = AccountType.getAccountTypes();
        if ( userTypes.size() <=0 ){
            createUserTypeIfNotExist();
            userTypes = UserType.getUserTypes();
        }
        if ( accountTypes.size() <= 0 ){
            createAccountTypeIfNotExist();
            accountTypes = AccountType.getAccountTypes();
        }


        List<SystemUser> artistas = SystemUser.getSystemUsers();
        ObjectNode allArtistas = Json.newObject();
        allArtistas.put("allArtistas", Json.toJson( artistas ) );

        String userTypesAsJson = Json.toJson( userTypes ).toString();
        String artistasAsJson = allArtistas.toString();
        String menus = Json.toJson( Menu.getAllMenus() ).toString();
        //System.out.print( allArtistas );
       return ok(views.html.index.render( menus ));
      // return ok( menus );
       // return ok( artistasAsJson );

    }


    public static Result home(){
        if ( session("sessionUser") == null){

            return  redirect( controllers.routes.Application.index());
        }
        List<Feed> feeds = Feed.getFeeds();
        String feedsAsJson =  Json.toJson( feeds ).toString();
        return ok( views.html.home.home.render(  feedsAsJson ));
    }

    public static Result signOut(){
       // session().remove("sessionUser");
       // session().remove("currentUserId");
        session().clear();
        return redirect("/");

    }
    /*
   public static Result artistas() {
       List<SystemUser1> artistas = SystemUser1.getArtistas();
       ObjectNode allArtistas = Json.newObject();
       allArtistas.put("allArtistas", Json.toJson( artistas ));
       //System.out.print(allArtistas);
       String artistasAsJson = allArtistas.toString();
       //return ok( Json.toJson( allArtistas ));
       //return ok( allArtistas );
       return ok( views.html.artistas.render( artistasAsJson ) );
    }



    public static Result deleteArtista( Long id){
        System.out.println( "SystemUser1 Id: " + id);
         SystemUser1.deleteArtista(id);
        return redirect( routes.Application.artistas() );
    }
     **/




    public static Result byName( String name){
        List<SystemUser> artistas = SystemUser.findByName(name);
        ObjectNode allArtistas = Json.newObject();
        allArtistas.put("all artistas Found with Name: " + name, Json.toJson( artistas ));
        return ok( Json.toJson( allArtistas ));
    }

    public static Result searchArtistas( String q ){
        List<SystemUser> artistas = SystemUser.findByQuery( q );
        ObjectNode searchResult = Json.newObject();
        searchResult.put( "searchResult", Json.toJson( artistas ));
        return ok( Json.toJson( searchResult ));
    }

   public static Result profile( String userName ){
       SystemUser systemUser = SystemUser.findSystemUserByUserName( userName );
       ProfileData profileData = new ProfileData(systemUser);
       //return ok( views.html.profile.profile.render( profileData.toString() ));
      // return ok( views.html.profile.profile.render( Json.toJson( profileData ).toString() ));
       return ok( views.html.profile.profile.render( Json.toJson( profileData ).toString() ));
   }

    //
    public static Result myProfile(){

        if ( session("sessionUser") != null){
            List< UserType > userTypes = UserType.getUserTypes();
            List< PersonCategory> personCategories = PersonCategory.getPersonCategories();
            List< OrganizationCategory> orgCategories = OrganizationCategory.getOrganizationCategories();
            String userTypesAsJson = Json.toJson( userTypes ).toString();
            String personCategoriesAsJson = Json.toJson( personCategories ).toString();
            String orgCategoriesAsJson = Json.toJson( orgCategories ).toString();
            String currentUserId = session("currentUserId");
            SystemUser currentUser = SystemUser.findUserById( currentUserId );
            System.out.println( "********* current User: " + currentUser);
            ProfileData profileData = new ProfileData( currentUser );
             //return  ok( session("user"));
           return  ok( views.html.profile.editProfile.render( Json.toJson( profileData ).toString(),personCategoriesAsJson, orgCategoriesAsJson ) );
        }
        else {
           return  ok(" no session -  not logged in");
        }
    }
    //
    public static Result myPhotos(){
        SystemUser u = SystemUser.findUserById(session("currentUserId"));
        if ( session("sessionUser") != null && u != null ){
            List<Photo> photos = Photo.getPhotosByOwnerId( u.getId());
            return  ok( views.html.profile.myphotos.render( Json.toJson( photos ).toString() ));
            //return  ok( session("user"));
        }
        else {
            return  ok(" no session -  not logged in");
        }
    }

    public static Result myWidget( String userName){
        SystemUser u = SystemUser.findSystemUserByUserName( userName );
        ProfileData profileData = new ProfileData( u );
        //return ok( views.html.profile.profile.render( profileData.toString() ));

        return  ok( views.html.widget.mywidget.render( Json.toJson( profileData ).toString() ));

    }

    public static Result myVideos(){
        SystemUser u = SystemUser.findUserById(session("currentUserId"));
        if ( session("sessionUser") != null){
            List<Video> myvideos = Video.getVideosByOwnerId( u.getId());
            return  ok( views.html.profile.myvideos.render( Json.toJson( myvideos ).toString() ));
            //return  ok( session("user"));
        }
        else {
            return  ok(" no session -  not logged in");
        }
    }

    public static Result addComment(){
        DynamicForm requestData = form().bindFromRequest();
        SystemUser u = SystemUser.findUserById(session("currentUserId"));
        String dataType = requestData.get("dataType");
        String photoId = requestData.get( "dataId");
        String comment = requestData.get("comment");
        System.out.println(" *******  Add Coment");

        /*if ( dataType.equals("profileImage")){
             System.out.println(" It is profile Image ");
             ProfileImageComment profileImagecomment = new ProfileImageComment( u, comment);
             ProfileImage profileImage = ProfileImage.findMyProfilePhotoById( photoId );
             profileImagecomment.setMyphoto( profileImage );
             profileImagecomment.save();
            return ok( Json.toJson( profileImagecomment ));
        }
        else {
*/
             Photo photo = Photo.findPhotoById( photoId );
             Comment photoComment = new Comment( photo,  u, comment );
             photoComment.setPhoto( photo );
             photoComment.save();
             return ok( Json.toJson(photoComment));
       // }

        //return ok( Json.toJson( Comment.getCommentsByMyPhoto( myphotoId )));
        //return ok( Json.toJson( myComment));
    }

    public static Result getComments( String photoId){
        Photo photo = Photo.findPhotoById( photoId );
        List <Comment> comments = Comment.getCommentsByPhotoId( photoId );
        return ok( Json.toJson( comments ));

    }




    public static void createAccountTypeIfNotExist(){
        new AccountType( "free", "at-00001", "free" ).save();
        new AccountType( "silver", "at-00002", "silver").save();
        new AccountType( "gold", "at-00003", "gold" ).save();
        new AccountType( "platinum", "at-00004", "platinum" ).save();

    }
    public static void createUserTypeIfNotExist(){
        new UserType( "restaurant", "ut-00001", "Restaurant" ).save();
        new UserType( "performance", "ut-00002", "Cafe" ).save();
        new UserType( "festival", "ut-00003", "Whole food" ).save();
        new UserType( "theater", "ut-00004", "Catering" ).save();

    }


}
  

