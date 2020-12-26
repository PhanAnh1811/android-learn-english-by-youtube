package com.mobile.EnglishYoutube;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Categories {
     String name;
     Bitmap image;
     ArrayList<VideoEnglishYoutube>arrayList;

    public Categories(String name, Bitmap image) {
       this.name=name;
       this.image=image;
    }
    public Categories(String name, ArrayList<VideoEnglishYoutube>arrayList) {
        this.name=name;
        this.image=image;
        this.arrayList=arrayList;
    }
    public Categories(String name,ArrayList<VideoEnglishYoutube>arrayList,Bitmap image) {
        this.name=name;
        this.image=image;
        this.arrayList=arrayList;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public ArrayList<VideoEnglishYoutube> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<VideoEnglishYoutube> arrayList) {
        this.arrayList = arrayList;
    }

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
