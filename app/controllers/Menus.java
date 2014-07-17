package controllers;

import models.*;
import play.data.DynamicForm;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;

import static play.data.Form.form;


public class Menus extends Controller {


    public static Result index(){
        SystemUser user = SystemUser.findUserById(session("currentUserId"));
        List<Menu> menus = Menu.findMenuByOrganization( user.getId() );
        System.out.println( "Menus by user: " + menus );

        String menusAsJson = Json.toJson( menus ).toString();

        return ok( menusAsJson ) ;

    }
}
