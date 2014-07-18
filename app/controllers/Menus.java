package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import dataHelpers.ProfileData;
import models.*;
import play.data.DynamicForm;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import static play.data.Form.form;


public class Menus extends Controller {


    public static Result index(){
        SystemUser user = SystemUser.findUserById(session("currentUserId"));
        ProfileData profileData = new ProfileData( user );

        List<Menu> menus = Menu.findMenuByOrganization( user.getId() );
        System.out.println( "Menus by user: " + menus );

        String menusAsJson = Json.toJson( menus ).toString();
        String profileDataAsJson = Json.toJson( profileData ).toString();

        //return ok( menusAsJson ) ;
        return ok( views.html.menus.mymenus.render( menusAsJson, profileDataAsJson ) ) ;

    }

    public static Result addMenu() throws MalformedURLException {
        SystemUser u = SystemUser.findUserById(session("currentUserId"));

        String fileName = "";
        Http.MultipartFormData b = request().body().asMultipartFormData();
        DynamicForm requestData = form().bindFromRequest();
        String menuTitle= requestData.get("menuTitle");
        Http.MultipartFormData.FilePart picture = b.getFile("menuImage");
        if ( session("sessionUser") != null ){

            Menu menu = new Menu( menuTitle, u );

            if (picture != null) {
                S3File s3File = new S3File();
                s3File.name = picture.getFilename();
                s3File.file = picture.getFile();
                s3File.save();
                // MyPhoto myphoto = new MyPhoto(imageUrl, fileName, u);
                Photo photo = new Photo(u, menuTitle , s3File.getUrl().toString(), null);
                menu.setMenuPhoto( photo );
                menu.save();
                photo.save();
            }


        }

        return redirect( routes.Menus.index() );
    }
}
