package com.jangkriek.ridwanharts.movyou.api;

import com.jangkriek.ridwanharts.movyou.favorit.DetilMovie;
import com.jangkriek.ridwanharts.movyou.favorit.ItemResult;
import com.jangkriek.ridwanharts.movyou.main.MovieItems2;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APICall {
    @GET("movie/{movie_id}")
    Call<DetilMovie>getIdMovie(
            @Path("movie_id")int movie_id,
            @Query("api_key")String apiKey);

    @GET("movie/now_playing")
    Call<MovieItems2>getNowPlaying(
            @Query("api_key") String apiKey,
            @Query("language")String language,
            @Query("page")int page,
            @Query("region")String region);

    @GET("movie/upcoming")
    Call<MovieItems2>getUpcoming(
            @Query("api_key") String apiKey,
            @Query("language")String language,
            @Query("page")int page,
            @Query("region")String region);

    @GET("search/movie")
    Call<MovieItems2>getSearch(
            @Query("api_key") String apiKey,
            @Query("language")String language,
            @Query("query")String querySearch);
}
