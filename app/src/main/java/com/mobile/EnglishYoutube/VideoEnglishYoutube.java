package com.mobile.EnglishYoutube;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageButton;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public class VideoEnglishYoutube implements Serializable {
    private String Title;
    private  String Thumbnails;
    private  String IdVideo;
   // private ImageButton bookmark;
    Bitmap image;
    public VideoEnglishYoutube(String title, String thumbnails, String idVideo) {
        Title = title;
        Thumbnails = thumbnails;
        IdVideo = idVideo;
    }
    public VideoEnglishYoutube(String title, String thumbnails, String idVideo,Bitmap image) {
        Title = title;
        Thumbnails = thumbnails;
        IdVideo = idVideo;
        this.image=image;
    }
    /*
    public VideoEnglishYoutube(String title, String thumbnails, String idVideo,ImageButton btn) {
        Title = title;
        Thumbnails = thumbnails;
        IdVideo = idVideo;
        bookmark=btn;
    }

     */
    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getThumbnails() {
        return Thumbnails;
    }

    public void setThumbnails(String thumbnails) {
        Thumbnails = thumbnails;
    }

    public String getIdVideo() {
        return IdVideo;
    }

    public void setIdVideo(String idVideo) {
        IdVideo = idVideo;
    }

/*
    public ImageButton getBookmark() {
        return bookmark;
    }

    public void setBookmark(ImageButton bookmark) {
        this.bookmark = bookmark;
    }
*/
    public static Bitmap convertStringToBitmapFromAccess(Context context, String
            filename){
        AssetManager assetManager = context.getAssets();
        try {
            InputStream is = assetManager.open(filename);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
