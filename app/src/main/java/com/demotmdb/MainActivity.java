package com.demotmdb;

import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.demotmdb.Adapter.MovieRecyclerAdapter;
import com.demotmdb.Setting.Config;

import org.json.JSONObject;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    RecyclerView movieRecycler;
    MovieRecyclerAdapter movieRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Populer movies");
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.white));

        init();

        JSONObject jsonObject = new JSONObject();
        call= api.GetMovieList(Config.api_key);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try{
                    if(response.code()==200){
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        initrecycler(jsonObject);
                    }else{

                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(mContext, R.string.ERROR_MSG, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mContext, R.string.ERROR_MSG, Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void initrecycler(JSONObject jsonObject){
        movieRecyclerAdapter= new MovieRecyclerAdapter(this,jsonObject);
        movieRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        movieRecycler.setAdapter(movieRecyclerAdapter);
    }

    private void init() {

        movieRecycler= findViewById(R.id.movieRecycler);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.more_tab_menu, menu);
        return true;
            }
}
