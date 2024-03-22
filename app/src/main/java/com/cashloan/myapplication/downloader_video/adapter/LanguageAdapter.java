package com.cashloan.myapplication.downloader_video.adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cashloan.myapplication.downloader_video.R;
import com.cashloan.myapplication.downloader_video.language.LanguageActivity;
import com.cashloan.myapplication.downloader_video.model.language_model.Languages;

import java.util.ArrayList;


public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.ViewHolder> {

    LanguageActivity languageActivity;
    ArrayList<Languages> languages;
    public int selected = -1;

    public LanguageAdapter(LanguageActivity languageActivity, ArrayList<Languages> languages) {
        this.languageActivity = languageActivity;
        this.languages = languages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(languageActivity);
        View itemView = inflater.inflate(R.layout.language_item_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Languages languages = this.languages.get(position);
        holder.language_name.setSelected(true);
        holder.language_name.setText(languages.getLanguageName());
        holder.language_flage.setImageResource(languages.getImage());

        if (selected == position) {
            Glide.with(languageActivity).load(R.drawable.language_select).into(holder.language_select);
            holder.idRLoan.setBackgroundResource(R.drawable.language_click_bg);
        } else {
            holder.language_select.setImageResource(0);
            holder.idRLoan.setBackgroundResource(R.drawable.language_color_bg);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = position;
                LanguageActivity.imgDone.setTextColor(Color.WHITE);
                LanguageActivity.imgDone.setBackgroundTintList(ColorStateList.valueOf(languageActivity.getColor(R.color.language_bg)));
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return languages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView language_name;
        ImageView language_select, language_flage;
        LinearLayout idRLoan;

        public ViewHolder(View itemView) {
            super(itemView);
            language_name = itemView.findViewById(R.id.language_name);
            language_select = itemView.findViewById(R.id.language_select);
            idRLoan = itemView.findViewById(R.id.idRLoan);
            language_flage = itemView.findViewById(R.id.language_flage);
        }
    }
}