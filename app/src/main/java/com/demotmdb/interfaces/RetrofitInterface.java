package com.demotmdb.interfaces;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @GET("discover/movie")
    Call<ResponseBody> GetMovieList( @Query("api_key") String api_key, @Body RequestBody body);
}