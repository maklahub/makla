package controllers;

import dataHelpers.SessionUser;
import models.Menu;
import models.SystemAccount;
import models.SystemUser;
import play.data.DynamicForm;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Controller;

import static play.data.Form.form;


public class LogIn extends Controller {

    public static Result index(){
        DynamicForm requestData = form().bindFromRequest();
        String email = requestData.get( "email" );
        String password = requestData.get( "password" );
        String fromUrl = requestData.get( "fromUrl" );
        if ( session("sessionUser") == null ){
            if ( email == null || email.isEmpty() || password == null || password.isEmpty() ){
                return  ok( views.html.login.render( "" ));
            }
            else{
               // SystemUser systemUser = SystemUser.findUserByEmailAndPass(email, password);
                SystemAccount systemAccount = SystemAccount.findSystemAccountByEmailAndPass( email, password );
                System.out.println("\nSystem account :\n " + systemAccount);
                if ( systemAccount == null ){
                    return ok( views.html.login.render( " No account found " ));
                }
                else {
                    SystemUser systemUser = systemAccount.getSystemUser();
                    SessionUser sessionUser = new SessionUser(systemUser);
                    String sessionUserAsJson = play.libs.Json.toJson( sessionUser).toString();
                    session("sessionUser" , sessionUserAsJson );
                    session("currentUserId", systemUser.getId());
                    String menus = Json.toJson(Menu.getAllMenus()).toString();
                    System.out.println(  "------------------------------> " + fromUrl );
                    return redirect( fromUrl );
                    /*
                    if ( systemUser.isItAPerson()){
                        return redirect( controllers.routes.Application.index() );
                    }
                    else {
                        return redirect( controllers.routes.Application.home() );
                    }
                    */

                }
            }
        }
        return redirect( "/" );

    }


    public static Result logIntoOrder(){

        return ok( views.html.login.render( " Please log in to Order " ));

    }

    public static Result loginToComment( ){
       // String r = request().path();

       // System.out.println("----------------------- request Url" + r);
        DynamicForm requestData = form().bindFromRequest();
        String url = requestData.get("currentUrl");
        String email = requestData.get("email");
        String password = requestData.get( "password" );
        if ( session("sessionUser") == null ){
            if ( email == null || email.isEmpty() || password == null || password.isEmpty() ){
                return  ok( views.html.login.render( "Please Sign in" ) );
            }
            else{
                // SystemUser systemUser = SystemUser.findUserByEmailAndPass(email, password);
                SystemAccount systemAccount = SystemAccount.findSystemAccountByEmailAndPass( email, password );
                System.out.println("\nSystem account :\n " + systemAccount);
                if ( systemAccount == null ){
                    return ok( views.html.login.render( " No account found " ) );
                }
                else {
                    SystemUser systemUser = systemAccount.getSystemUser();
                    SessionUser sessionUser = new SessionUser(systemUser);
                    String sessionUserAsJson = play.libs.Json.toJson( sessionUser).toString();
                    session("sessionUser" , sessionUserAsJson );
                    session("currentUserId", systemUser.getId());
                    return redirect( url );
                }
            }
        }
        return redirect( url );
    }



}
