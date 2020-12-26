package com.mobile.EnglishYoutube;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BookmarkView extends AppCompatActivity {

    public static String API_KEY="AIzaSyC4p23-0Ww7xhYWUpXl4h0zir9qvkIYbn8";
    String ID_PLAYLIST="PLwTZpBtz_v2JMulC52XNVzJLA-L_cNI0v";
    String urlGetJson="https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId="+ID_PLAYLIST+"&key="+API_KEY+"&maxResults=50";
    ListView listViewVideo;
    ArrayList<VideoEnglishYoutube> arrayVideo;
    VideoEnglishYoutubeAdapter2 videoAdapter;
    //   int REQUEST_VIDEO=123;
    YouTubePlayerView youTubePlayerView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmark_view_activity);

        listViewVideo=findViewById(R.id.listviewfavorite);


        arrayVideo =new ArrayList<>();

        GetJsonYoutube(urlGetJson);

        // bat su kien click
        listViewVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                XuLiChucNang.englishVideoHistory.add(arrayVideo.get(i));
                Intent intent=new Intent(BookmarkView.this,PlayVideoActivity.class);
                intent.putExtra("idVideoYoutube",arrayVideo.get(i).getIdVideo());
                startActivity(intent);

            }
        });


    }
    private void GetJsonYoutube(String url){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        final JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonItems =response.getJSONArray("items");
                    String title="";
                    String url="";
                    String idVideo="";

                    for(int i=0;i<jsonItems.length();i++){
                        JSONObject jsonItem=jsonItems.getJSONObject(i);
                        JSONObject jsonSnippet=jsonItem.getJSONObject("snippet");
                        title=jsonSnippet.getString("title");
                        JSONObject jsonThumbnail=jsonSnippet.getJSONObject( "thumbnails");
                        JSONObject jsonMedium =jsonThumbnail.getJSONObject("medium");
                        url=jsonMedium.getString("url");
                        JSONObject jsonResourceID =jsonSnippet.getJSONObject("resourceId");
                        idVideo=jsonResourceID.getString("videoId");
                        arrayVideo.add(new VideoEnglishYoutube(title,url,idVideo));


                    }


                    videoAdapter=new VideoEnglishYoutubeAdapter2(BookmarkView.this,R.layout.bookmark_view,arrayVideo);
                    listViewVideo.setAdapter(videoAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BookmarkView.this, "ERROR!!!", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
    @Override
    public void onPause() {
        super.onPause();
        XuLiChucNang xuLiChucNang = new XuLiChucNang(BookmarkView.this);
        xuLiChucNang.WriteToFileInternal(arrayVideo);
    }
}
