package com.mobile.EnglishYoutube;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VideoTypeFragment extends Fragment {
    public static String API_KEY="AIzaSyC4p23-0Ww7xhYWUpXl4h0zir9qvkIYbn8";
    String ID_PLAYLIST1="PLwTZpBtz_v2LZSwL6e7nna2j8IOvQ8R2U";
    String ID_PLAYLIST2="PLwTZpBtz_v2JV_szUhGRHgM-6fr1JED8V";
    String ID_PLAYLIST3="PLwTZpBtz_v2LLc4lw86GJFdnk18MUDbaT";
    String ID_PLAYLIST4="PLwTZpBtz_v2IAgJKloQnBKMgM-oTVHjwQ";
    String urlGetJson1="https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId="+ID_PLAYLIST1+"&key="+API_KEY+"&maxResults=50";
    String urlGetJson2="https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId="+ID_PLAYLIST2+"&key="+API_KEY+"&maxResults=50";
    String urlGetJson3="https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId="+ID_PLAYLIST3+"&key="+API_KEY+"&maxResults=50";
    String urlGetJson4="https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId="+ID_PLAYLIST4+"&key="+API_KEY+"&maxResults=50";

    ListView listViewVideo;
    ArrayList<VideoEnglishYoutube> arrayVideo;
    VideoEnglishYoutubeAdapter1 videoAdapter;

    FloatingActionButton BookmarkView;
    ArrayList<VideoEnglishYoutube> arrayList;

    ImageView btnBack;

    public  VideoTypeFragment(){

    }

    public static VideoTypeFragment newInstance(int pos) {
       // Required empty public constructor
        Bundle bundle = new Bundle();
        bundle.putInt("category", pos);
        VideoTypeFragment videoTypeFragment=new VideoTypeFragment();
        videoTypeFragment.setArguments(bundle);
        return videoTypeFragment;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_categories, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle
            savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnBack=view.findViewById(R.id.btnBack);
        listViewVideo=view.findViewById(R.id.listView);
        BookmarkView=(FloatingActionButton)view.findViewById(R.id.btnBookmarkView);
        arrayVideo =new ArrayList<>();
        setHasOptionsMenu(true);


       Bundle bundle = getArguments();
        arrayVideo=getVideoFromCategories(bundle.getInt("category"));
//        listView = view.findViewById(R.id.listView);
 //       arrayList = xuLiChucNang.getVideoFromCategories(bundle.getInt("category"));



        listViewVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                XuLiChucNang.englishVideoHistory.add(arrayVideo.get(i));
                Intent intent=new Intent(getContext(),PlayVideoActivity.class);
                intent.putExtra("idVideoYoutube",arrayVideo.get(i).getIdVideo());
                startActivity(intent);

            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, new ThemeFragment());
                transaction.addToBackStack(null);
                transaction.commit();
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

    public ArrayList<VideoEnglishYoutube> getVideoFromCategories(int pos){
        ArrayList<VideoEnglishYoutube> tmp = new ArrayList<>();
        switch (pos){
            case 0:
                GetJsonYoutube(urlGetJson1);

                break;
            case 1:
                GetJsonYoutube(urlGetJson2);
                break;
            case 2:
                GetJsonYoutube(urlGetJson3);
                break;
            case 3:
                GetJsonYoutube(urlGetJson4);
                break;
        }
        return tmp;
    }
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
                    videoAdapter=new VideoEnglishYoutubeAdapter1(getContext(),R.layout.sub_item_listview,arrayVideo);
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



}
