package com.mobile.EnglishYoutube;

import android.content.Context;
import android.print.PrinterId;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

public class VideoEnglishYoutubeAdapter extends BaseAdapter  {
    private Context context;
    private  int layout;
    private List<VideoEnglishYoutube> videoEnglishYoutubeList;
    private ArrayList<VideoEnglishYoutube> arrayList;

    public VideoEnglishYoutubeAdapter(Context context, int layout, List<VideoEnglishYoutube> videoEnglishYoutubeList) {
        this.context = context;
        this.layout = layout;
        this.videoEnglishYoutubeList = videoEnglishYoutubeList;

        this.arrayList=new ArrayList<VideoEnglishYoutube>();
        this.arrayList.addAll(videoEnglishYoutubeList);
    }
    public VideoEnglishYoutubeAdapter(Context context, List<VideoEnglishYoutube> videoEnglishYoutubeList) {
        this.context = context;
        this.videoEnglishYoutubeList = videoEnglishYoutubeList;
    }

    @Override
    public int getCount() {
        return videoEnglishYoutubeList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
         final ViewHolder holder;

        if(view==null){
            holder=new ViewHolder();
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view= inflater.inflate(R.layout.video_youtube_list,null);

     //       holder.btnfavorite=(ImageButton)view.findViewById(R.id.btnBookmark);
            holder.txtTitle=(TextView)view.findViewById(R.id.textViewTitle);
            holder.imgThumbnail=(ImageView)view.findViewById(R.id.imageviewThumbnails);

            view.setTag(holder);
        }
        else {

            holder=(ViewHolder)view.getTag();
        }
        VideoEnglishYoutube video=videoEnglishYoutubeList.get(i);

        holder.txtTitle.setText(video.getTitle());
        Picasso.with(context).load(video.getThumbnails()).into(holder.imgThumbnail);

/*
        holder.btnfavorite.setTag(100);
        holder.btnfavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int j=(int)holder.btnfavorite.getTag();
                if(j == 100)
                {
                    holder.btnfavorite.setImageResource(R.drawable.ic_favorite);
                    holder.btnfavorite.setTag(120);
                }
                else if(j == 120)
                {
                    holder.btnfavorite.setImageResource(R.drawable.ic_favorite_border);
                    holder.btnfavorite.setTag(100);
                }
            }
        });

*/
        return view;
    }

    private  class  ViewHolder{
        ImageView imgThumbnail;
        TextView txtTitle;
   //     ImageButton btnfavorite;
    }


    //Filter class
    public void filter(String charText){
        charText =charText.toLowerCase(Locale.getDefault());
        videoEnglishYoutubeList.clear();
        if(charText.length()==0) {
            videoEnglishYoutubeList.addAll(arrayList);
        } else {
                for(VideoEnglishYoutube video : arrayList){
                    if(video.getTitle().toLowerCase(Locale.getDefault()).contains(charText))
                    {
                        videoEnglishYoutubeList.add(video);
                    }
                }
            }


        notifyDataSetChanged();
    }

}
