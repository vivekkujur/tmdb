package com.demotmdb;

import android.annotation.SuppressLint;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.demotmdb.Setting.Config;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieActivity extends BaseActivity {


    TextView desc, date, rating, budget, revenue, language, runningtime,genresList;
    ImageView poster;
    String id;
    final String[] Selected = new String[]{"Jan", "Feb", "Mar", "Apr",
            "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    View loading;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        final Toolbar toolbar= findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.white));

        init();

        loading.setVisibility(View.VISIBLE);

        Bundle bundle= getIntent().getExtras();
        if (bundle != null) {
             id= bundle.getString("id");
        }
        final RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_image_black_24dp);


        call= api.GetMovieDetails(id,Config.api_key);
        call.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    Log.e("onResponse: ",response.body()+"" );
                    if(response.code()==200){
                        if (response.body() != null) {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            toolbar.setTitle(jsonObject.getString("original_title"));
                            try{
                                runningtime.setText(jsonObject.getString("runtime")+" minutes");

                            }catch(Exception e){
                                e.printStackTrace();
                            }
                            try{
                                String datestr= jsonObject.getString("release_date");

                                String[] arrOfStr = datestr.split("-");
                                if(Integer.parseInt(arrOfStr[2]) > 10 && Integer.parseInt(arrOfStr[2])<14)
                                    arrOfStr[2]   = arrOfStr[2] + "th ";
                                else{
                                    if(arrOfStr[2].endsWith("1")) arrOfStr[2] = arrOfStr[2] + "st";
                                    else if(arrOfStr[2].endsWith("2")) arrOfStr[2] = arrOfStr[2] + "nd";
                                    else if(arrOfStr[2].endsWith("3")) arrOfStr[2] = arrOfStr[2] + "rd";
                                    else arrOfStr[2] = arrOfStr[2] + "th";
                                }
                                date.setText(arrOfStr[2]+" "+Selected[Integer.parseInt(arrOfStr[1])-1]+arrOfStr[0]);


                            }catch(Exception e){
                                e.printStackTrace();
                            }
                            try{
                                language.setText(jsonObject.getString("original_language"));

                            }catch(Exception e){
                                e.printStackTrace();
                            }
                            try{
                                rating.setText(jsonObject.getString("vote_average"));

                            }catch(Exception e){
                                e.printStackTrace();
                            }
                            try{
                                desc.setText(jsonObject.getString("overview"));

                            }catch(Exception e){
                                e.printStackTrace();
                            }

                            try {
                                Glide.with(getApplicationContext()).setDefaultRequestOptions(requestOptions)
                                        .load(Config.ImageBaseUrl+jsonObject.getString("backdrop_path"))
                                        .into(poster);
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            int sizeGenres=jsonObject.getJSONArray("genres").length();
                            String genres="";
                            for(int i= 0;i<sizeGenres;i++){
                                if(i!=0){
                                    genres=genres+", ";
                                }
                                genres = genres + jsonObject.getJSONArray("genres").getJSONObject(i).getString("name");

                            }
                            genresList.setText(genres);

                            int budgeta= Integer.parseInt(jsonObject.getString("budget"));
                            int budgetd= budgeta/1000000;
                            budget.setText("$"+budgetd+"Million");

                            int revenueA= Integer.parseInt(jsonObject.getString("revenue"));
                            int revenueD= revenueA/1000000;
                            revenue.setText("$"+revenueD+"Million");

                        }
                        loading.setVisibility(View.GONE);

                    }
                }catch(Exception e){
                    e.printStackTrace();
                    loading.setVisibility(View.GONE);
                    Toast.makeText(mContext, R.string.ERROR_MSG, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mContext, R.string.ERROR_MSG, Toast.LENGTH_SHORT).show();
                loading.setVisibility(View.GONE);

            }
        });

    }

    private void init() {

        poster = findViewById(R.id.poster);
        desc= findViewById(R.id.movieoverview);
        date= findViewById(R.id.daterelease);
        rating= findViewById(R.id.ratingmovie);
        budget= findViewById(R.id.budget);
        revenue= findViewById(R.id.revenue);
        language= findViewById(R.id.languagemovie);
        runningtime= findViewById(R.id.runninnTzime);
        genresList= findViewById(R.id.genre);
        loading= findViewById(R.id.loading);

    }
}
