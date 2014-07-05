package controllers;


import models.Photo;
import models.S3File;
import models.SystemUser;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;

import java.io.IOException;

import static play.data.Form.form;

public class AddPhotos extends Controller {


    public static Result addMyPhotos() throws IOException {

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
