package com.enokoo.newske;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAVAdapter extends RecyclerView.Adapter<NewsAVAdapter.ViewHolder>  {
    private ArrayList<Articles> articlesArrayList;
    private Context context;

    public NewsAVAdapter(ArrayList<Articles> articlesArrayList, Context context) {
        this.articlesArrayList = articlesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsAVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.newsres,parent,false);
   return new NewsAVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAVAdapter.ViewHolder holder, int position) {
        Articles articles=articlesArrayList.get(position);
        holder.subTitleTV.setText(articles.getDescription());
        holder.titleTV.setText(articles.getTitle());
        Picasso.get().load(articles.getUrlToImage()).into(holder.newsTV);
holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      Intent i=new Intent(context,NewsDetailsActivity.class);
      i.putExtra("title",articles.getTitle());
        i.putExtra("content",articles.getContent());
        i.putExtra("desc",articles.getDescription());
        i.putExtra("image",articles.getUrlToImage());
        i.putExtra("url",articles.getUrl());
        context.startActivity(i);
    }
});

    }

    @Override
    public int getItemCount() {

        return articlesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
private TextView titleTV,subTitleTV;
private ImageView newsTV;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            titleTV=itemView.findViewById(R.id.TvHeading);
       subTitleTV=itemView.findViewById(R.id.tvsubTitle);
       newsTV=itemView.findViewById(R.id.IvNews);
        }
    }
}
