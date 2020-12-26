package com.mobile.EnglishYoutube;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class VideoEnglishYoutubeAdapterGrid extends BaseAdapter {
    LayoutInflater inflater;
    TextView Category_name;
    ImageView VideoImage;
    Context context;
    ArrayList<Categories> arrayList;
    public VideoEnglishYoutubeAdapterGrid(Context context, ArrayList<Categories> list) {
        // arraylist chưa có
        arrayList = list;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.categories_item_layout, null);
        // Thiết lập thông tin hiển thị
        Category_name = (TextView) view.findViewById(R.id.category_name);
        Category_name.setText(arrayList.get(i).name);
        VideoImage = view.findViewById(R.id.imagevideoview);
        VideoImage.setImageBitmap(arrayList.get(i).image);
        return view;
    }
}
