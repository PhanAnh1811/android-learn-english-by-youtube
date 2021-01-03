package com.mobile.EnglishYoutube;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
//import com.google.android.youtube.player.YouTubeBaseActivity;
//import com.google.android.youtube.player.YouTubeInitializationResult;
//import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import java.lang.reflect.Method;
import java.util.ArrayList;
//import java.util.List;

public class HomeFragment extends Fragment{
    public static String API_KEY="AIzaSyC4p23-0Ww7xhYWUpXl4h0zir9qvkIYbn8";
    String ID_PLAYLIST="PLwTZpBtz_v2JMulC52XNVzJLA-L_cNI0v";
    String urlGetJson="https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId="+ID_PLAYLIST+"&key="+API_KEY+"&maxResults=50";
    ListView listViewVideo;
    ArrayList<VideoEnglishYoutube> arrayVideo;
    VideoEnglishYoutubeAdapter videoAdapter;
    //   int REQUEST_VIDEO=123;
    YouTubePlayerView youTubePlayerView;

    // Floating button bookmark

    FloatingActionButton BookmarkView;




    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test, container, false);
    }
    /*
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            //      super.onViewCreated(view, savedInstanceState);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fragment_test);
            //      youTubePlayerView=(YouTubePlayerView) findViewById(R.id.myYouTube);
            //     youTubePlayerView.initialize(API_KEY,this);

            listViewVideo=findViewById(R.id.listviewVideo);
            arrayVideo =new ArrayList<>();



            //    videoAdapter=new VideoEnglishYoutubeAdapter(Test.this,R.layout.video_youtube_list,arrayVideo);
            //     listViewVideo.setAdapter(videoAdapter);

            GetJsonYoutube(urlGetJson);

            // bat su kien click
            listViewVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



                    Intent intent=new Intent(Test.this,PlayVideoActivity.class);
                    intent.putExtra("idVideoYoutube",arrayVideo.get(i).getIdVideo());
                    startActivity(intent);

                }
            });
        }
    */
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //       setContentView(R.layout.fragment_test);
        //      youTubePlayerView=(YouTubePlayerView) findViewById(R.id.myYouTube);
        //     youTubePlayerView.initialize(API_KEY,this);

        BookmarkView=(FloatingActionButton)view.findViewById(R.id.btnBookmarkView);
        listViewVideo=view.findViewById(R.id.listviewVideo);
        arrayVideo =new ArrayList<>();
        setHasOptionsMenu(true);


        //    videoAdapter=new VideoEnglishYoutubeAdapter(Test.this,R.layout.video_youtube_list,arrayVideo);
        //     listViewVideo.setAdapter(videoAdapter);

        GetJsonYoutube(urlGetJson);

        // bat su kien click
        listViewVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                XuLiChucNang.englishVideoHistory.add(arrayVideo.get(i));
                Intent intent=new Intent(getContext(),PlayVideoActivity.class);
                intent.putExtra("idVideoYoutube",arrayVideo.get(i).getIdVideo());
                startActivity(intent);

            }
        });

        BookmarkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BookmarkView.class);
                startActivity(intent);
            }
        });

    }
    /*
       public boolean onCreateOptionsMenu(final Menu menu) {
           getMenuInflater().inflate(R.menu.main_menu,menu);
           final MenuItem item=menu.findItem(R.id.action_search);
           androidx.appcompat.widget.SearchView searchView =(androidx.appcompat.widget.SearchView)item.getActionView();
           searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
               @Override
               public boolean onQueryTextSubmit(String query) {
                   Toast.makeText(getContext(), query, Toast.LENGTH_SHORT).show();
                   return false;
               }

               @Override
               public boolean onQueryTextChange(String newText) {
                   videoAdapter.filter(newText.trim());
                   return false;
               }
           });
           return super.onCreateOptionsMenu(menu);
       }
   */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.main_menu,menu);
        final MenuItem item=menu.findItem(R.id.action_search);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW|MenuItem.SHOW_AS_ACTION_IF_ROOM);
        androidx.appcompat.widget.SearchView searchView =(androidx.appcompat.widget.SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getContext(), query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                videoAdapter.filter(newText.trim());
                return false;
            }
        });
    }





    private void GetJsonYoutube(String url){
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
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


                    videoAdapter=new VideoEnglishYoutubeAdapter(getContext(),R.layout.video_youtube_list,arrayVideo);
                    listViewVideo.setAdapter(videoAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "ERROR!!!", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
    @Override
    public void onPause() {
        super.onPause();
        XuLiChucNang xuLiChucNang = new XuLiChucNang(getContext());
        xuLiChucNang.WriteToFileInternal(arrayVideo);
    }

/*
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer youTubePlayer, boolean b) {
            youTubePlayer.cueVideo("ekzHIouo8Q4");
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            if(youTubeInitializationResult.isUserRecoverableError()){
                youTubeInitializationResult.getErrorDialog(Test.this,REQUEST_VIDEO);
            }
            else {
                Toast.makeText(this, "ERROR!!!", Toast.LENGTH_SHORT).show();
            }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQUEST_VIDEO){
            youTubePlayerView.initialize(API_KEY,Test.this);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    /
 */

}
