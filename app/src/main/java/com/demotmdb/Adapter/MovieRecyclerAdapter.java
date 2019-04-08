package com.demotmdb.Adapter;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.demotmdb.MainActivity;
import com.demotmdb.R;

public class MovieRecyclerAdapter  extends RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder> {

    MainActivity mainActivity;
    public MovieRecyclerAdapter(MainActivity mainActivity) {
        this.mainActivity= mainActivity;
    }

    @NonNull
    @Override
    public MovieRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new MovieRecyclerAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieRecyclerAdapter.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, desc, date, rating , language;
         ImageView poster;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title= itemView.findViewById(R.id.titleMovie);
            desc= itemView.findViewById(R.id.descMovie);
            date= itemView.findViewById(R.id.date);
            rating = itemView.findViewById(R.id.rating);
            language= itemView.findViewById(R.id.language);
            poster= itemView.findViewById(R.id.imageMovie);
        }
    }
}
