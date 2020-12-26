package com.mobile.EnglishYoutube;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class DictionaryFragment extends Fragment {
    private EditText edtText;
    private RecyclerView lvList;

    ImageButton btnSearch;


    private DBHelper dataBaseHelper;

    private WordAdapter wordAdapter;

    private LinearLayoutManager linearLayoutManager;


    List<Word> wordList = new ArrayList<>();

    public DictionaryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtText = view.findViewById(R.id.edtText);
        lvList = view.findViewById(R.id.lvList);

        btnSearch=view.findViewById(R.id.search);

        dataBaseHelper = new DBHelper(getContext());
        dataBaseHelper.createDataBase();


        wordAdapter = new WordAdapter(wordList, getContext());
        linearLayoutManager = new LinearLayoutManager(getContext());

        lvList.setAdapter(wordAdapter);

        lvList.setLayoutManager(linearLayoutManager);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordList.clear();
                String word = edtText.getText().toString().trim();

                // kiểm tra nếu người dùng chưa nhập gì thì dừng lại và thông báo lỗi
                if (word.isEmpty()) {
                    edtText.setError("Vui lòng nhập dữ liệu !!!");
                    // nếu chữ ko empty thì tiếp tục tìm kiếm và hiển thị danh sách kết quả lên list nếu có
                } else {

                    List<Word> wordList1 = dataBaseHelper.searchWord(word);
                    wordList.addAll(wordList1);
                    wordAdapter.notifyDataSetChanged();

                }
            }
        });

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dictionary, container, false);
    }
    void gotoFragment(Fragment fragment,boolean isTop){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        transaction.replace(R.id.nav_view_dict, fragment);
        if (!isTop)
            transaction.addToBackStack(null);
        transaction.commit();
    }

}