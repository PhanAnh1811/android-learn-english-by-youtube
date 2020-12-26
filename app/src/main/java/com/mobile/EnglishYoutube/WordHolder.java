package com.mobile.EnglishYoutube;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class WordHolder extends RecyclerView.ViewHolder {

    public final TextView tvWord;

    public WordHolder(@NonNull View itemView) {
        super(itemView);

        tvWord = itemView.findViewById(R.id.tvText);
    }
}
