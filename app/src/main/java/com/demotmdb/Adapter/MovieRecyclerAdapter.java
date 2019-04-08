package com.demotmdb.Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.demotmdb.MainActivity;
import com.demotmdb.MovieActivity;
import com.demotmdb.R;
import com.demotmdb.Setting.Config;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieRecyclerAdapter  extends RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder> {

    MainActivity mainActivity;
    JSONObject jsonObject= new JSONObject();
    final String[] Selected = new String[]{"Jan", "Feb", "Mar", "Apr",
            "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    public MovieRecyclerAdapter(MainActivity mainActivity, JSONObject jsonObject) {
        this.mainActivity= mainActivity;
        this.jsonObject= jsonObject;
    }

    public MovieRecyclerAdapter() {

    }
    @NonNull
    @Override
    public MovieRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new MovieRecyclerAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_list, parent, false));
    }

    @SuppressLint({"CheckResult", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull MovieRecyclerAdapter.ViewHolder viewHolder, final int i) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_image_black_24dp);
        try {
            Glide.with(mainActivity).setDefaultRequestOptions(requestOptions).load(Config.ImageBaseUrl+jsonObject.getJSONArray("results").getJSONObject(i).getString("poster_path"))
                    .into(viewHolder.poster);

            viewHolder.language.setText(jsonObject.getJSONArray("results").getJSONObject(i).getString("original_language"));
            viewHolder.desc.setText(jsonObject.getJSONArray("results").getJSONObject(i).getString("overview"));
            viewHolder.title.setText(jsonObject.getJSONArray("results").getJSONObject(i).getString("title"));
            viewHolder.rating.setText(jsonObject.getJSONArray("results").getJSONObject(i).getString("vote_average"));


        try{
            String datestr = jsonObject.getJSONArray("results").getJSONObject(i).getString("release_date");

            String[] arrOfStr = datestr.split("-");
            if(Integer.parseInt(arrOfStr[2]) > 10 && Integer.parseInt(arrOfStr[2])<14)
                arrOfStr[2]   = arrOfStr[2] + "th ";
            else{
                if(arrOfStr[2].endsWith("1")) arrOfStr[2] = arrOfStr[2] + "st";
                else if(arrOfStr[2].endsWith("2")) arrOfStr[2] = arrOfStr[2] + "nd";
                else if(arrOfStr[2].endsWith("3")) arrOfStr[2] = arrOfStr[2] + "rd";
                else arrOfStr[2] = arrOfStr[2] + "th";
            }
            viewHolder.date.setText(arrOfStr[2]+" "+Selected[Integer.parseInt(arrOfStr[1])-1]+arrOfStr[0]);


        }catch(Exception e){
            e.printStackTrace();
        }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        viewHolder.mainlv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivity, MovieActivity.class);

                try {
                    intent.putExtra("id",jsonObject.getJSONArray("results").getJSONObject(i).getString("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mainActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        try {
            return jsonObject.getJSONArray("results").length();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, desc, date, rating , language;
         ImageView poster;
         ConstraintLayout mainlv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title= itemView.findViewById(R.id.titleMovie);
            desc= itemView.findViewById(R.id.descMovie);
            date= itemView.findViewById(R.id.date);
            rating = itemView.findViewById(R.id.rating);
            language= itemView.findViewById(R.id.language);
            poster= itemView.findViewById(R.id.imageMovie);
            mainlv= itemView.findViewById(R.id.mainlv);
        }
    }
}
