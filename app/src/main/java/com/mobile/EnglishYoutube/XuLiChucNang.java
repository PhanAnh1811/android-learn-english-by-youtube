package com.mobile.EnglishYoutube;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class XuLiChucNang {
    Context context;
    public XuLiChucNang(Context context) {
        this.context = context;
    }
    static String filename="idVideoYoutube";

   //////////////// history
    static ArrayList<VideoEnglishYoutube> englishVideoHistory = new ArrayList<>();
    public void addVideoHistorry(VideoEnglishYoutube englishVideo) {

        if(englishVideoHistory.indexOf(englishVideo) > 0) {
            this.englishVideoHistory.add(0, englishVideo);
        }
    }
    public ArrayList<VideoEnglishYoutube> getVideoHistory(){
        return this.englishVideoHistory; }

 //////////////////bookmark
    static ArrayList<VideoEnglishYoutube> englishVideoBookmark = new ArrayList<>();
    public void addBookmark(VideoEnglishYoutube englishVideo) {

        if(englishVideoBookmark.indexOf(englishVideo) > 0) {
            this.englishVideoBookmark.add(0, englishVideo);
        }
    }
    public ArrayList<VideoEnglishYoutube> getEnglishVideoBookmark(){
        return this.englishVideoBookmark; }


        //
    public void WriteToFileInternal(ArrayList<VideoEnglishYoutube> arrayList){
        try {
            File file = new File(context.getFilesDir(), filename);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new
                    ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(arrayList);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<VideoEnglishYoutube> LoadFileInternal(){
        ArrayList<VideoEnglishYoutube> arrayList = new ArrayList<>();
        try {
            File file = new File(context.getFilesDir(), filename);
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new
                    ObjectInputStream(fileInputStream);
            arrayList = (ArrayList<VideoEnglishYoutube>)
                    objectInputStream.readObject();
            Log.d("FurnitureApp", arrayList.size()+"");
            return arrayList;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Bitmap convertStringToBitmapFromAccess(String filename){
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
