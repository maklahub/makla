package controllers;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import models.*;
import play.data.DynamicForm;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;

import java.io.IOException;

import static play.data.Form.form;

public class AddMenuItem extends Controller {


    public static Result addMenuItemToCart() {
        CartItem cartItem = null;
        JsonNode json = request().body().asJson();
        SystemUser u = SystemUser.findUserById(session("currentUserId"));

        if(json == null) {
            return badRequest("Expecting Json data");
        } else {
            String menuItemId = json.findPath("id").textValue();
            if(menuItemId== null) {
                return badRequest("Missing parameter [id]");
            } else {
                MenuItem menuItem = MenuItem.findMenuItemById( menuItemId );
                Cart cart = Cart.findCartBySystemUser( u.getId() );
                if ( cart == null ){
                    cart = new Cart( "New cart", u);
                    cartItem = new CartItem( cart, menuItem );
                    cartItem.save();
                    cart.save();
                }
                else {
                    cartItem = new CartItem( cart, menuItem );
                    cartItem.save();
                    cart.save();
                }


               // return ok(Json.toJson(menuItemId).toString());
                return ok( Json.toJson( cartItem ) );
            }
        }

        /*SystemUser u = SystemUser.findUserById(session("currentUserId"));
        String fileName = "";
        Http.MultipartFormData b = request().body().asMultipartFormData();
        DynamicForm requestData = form().bindFromRequest();
        String photoTitle= requestData.get("photo-title");
        FilePart picture = b.getFile("myphotos-upload");
        if (picture != null) {
            S3File s3File = new S3File();
            s3File.name = picture.getFilename();
            s3File.file = picture.getFile();
            s3File.save();
            // MyPhoto myphoto = new MyPhoto(imageUrl, fileName, u);
            Photo photo = new Photo(u, photoTitle , s3File.getUrl().toString(), null);
            photo.save();

        }
        */
      //  return redirect( routes.Application.myPhotos() );
      //  return ok("Menu Item Item Added");
    }



    public static Result removeCartItem(){

        JsonNode json = request().body().asJson();
        SystemUser u = SystemUser.findUserById(session("currentUserId"));

        if(json == null) {
            return badRequest("Expecting Json data");
        } else {
            String cartItemId = json.findPath("id").textValue();
            if( cartItemId== null) {
                return badRequest("Missing parameter [id]");
            } else {
                CartItem cartItem = CartItem.findCartItemById( cartItemId );
                cartItem.setCart( null );
                cartItem.setCartItemPhoto( null );
                //cartItem.delete();
                Ebean.delete( cartItem );

                // return ok(Json.toJson(cartItem).toString());
                return ok( "Item has beem removed " + cartItem.getName() );
            }
        }

    }


    public static Result addMenuItemToMenu() throws IOException {

        SystemUser u = SystemUser.findUserById(session("currentUserId"));
        String fileName = "";
        Http.MultipartFormData b = request().body().asMultipartFormData();
        DynamicForm requestData = form().bindFromRequest();
        String photoTitle= requestData.get("photo-title");
        FilePart picture = b.getFile("myphotos-upload");
        if (picture != null) {
            S3File s3File = new S3File();
            s3File.name = picture.getFilename();
            s3File.file = picture.getFile();
            s3File.save();
            // MyPhoto myphoto = new MyPhoto(imageUrl, fileName, u);
            Photo photo = new Photo(u, photoTitle , s3File.getUrl().toString(), null);
            photo.save();
        }
        return redirect( routes.Application.myPhotos() );

    }
}
