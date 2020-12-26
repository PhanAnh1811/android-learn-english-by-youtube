package com.mobile.EnglishYoutube;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThemeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThemeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    GridView gridView;
    ArrayList<Categories> arrayList;
    VideoEnglishYoutubeAdapterGrid videoEnglishYoutubeAdapterGrid;

    public ThemeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThemeFragment newInstance(String param1, String param2) {
        ThemeFragment fragment = new ThemeFragment();

        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        gridView = view.findViewById(R.id.gridView);
        arrayList = getMockData();
         videoEnglishYoutubeAdapterGrid= new VideoEnglishYoutubeAdapterGrid(getContext(),arrayList);
        gridView.setAdapter(videoEnglishYoutubeAdapterGrid);
        // thêm click theo danh muc
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i,
                                    long l) {
                FragmentTransaction fragmentTransaction =
                        getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment,VideoTypeFragment.newInstance(i)
                );
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_theme, container, false);
    }
    public ArrayList<Categories> getMockData(){
        ArrayList<VideoEnglishYoutube> arrayListSport = new ArrayList<>();
        ArrayList<VideoEnglishYoutube> arrayListCulture = new ArrayList<>();
        ArrayList<VideoEnglishYoutube> arrayListEducating = new ArrayList<>();
        ArrayList<VideoEnglishYoutube> arrayListTravel = new ArrayList<>();
        ArrayList<Categories> tmp = new ArrayList<>();
        tmp.add(new Categories("Sport", arrayListSport,
                Categories.convertStringToBitmapFromAccess(getContext(), "sport.jpg")));
        tmp.add(new Categories("Culture", arrayListCulture,
                Categories.convertStringToBitmapFromAccess(getContext(), "culture.jpg")));
        tmp.add(new Categories("Educating", arrayListEducating,
                Categories.convertStringToBitmapFromAccess(getContext(), "educating.jpg")));
        tmp.add(new Categories("Travel", arrayListTravel,
                Categories.convertStringToBitmapFromAccess(getContext(), "travel.jpg")));
        return tmp;
    }




}