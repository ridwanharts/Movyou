package com.jangkriek.ridwanharts.movyou.api;

import android.os.Build;

import com.jangkriek.ridwanharts.movyou.BuildConfig;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private  static final String URL_MAIN = BuildConfig.URL_MAIN;
    private APICall apiCall;
    private static APIClient instance = null;

    private APIClient(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL_MAIN).addConverterFactory(GsonConverterFactory.create()).build();
        apiCall = retrofit.create(APICall.class);
    }
    public static APIClient getInstance(){
        if(instance == null){
            instance = new APIClient();
        }
        return instance;
    }

    public APICall getApiCall(){
        return apiCall;
    }
    public static Retrofit retrofit = null;
    public static Retrofit getClient(){
        if (retrofit==null){
            retrofit=new Retrofit.Builder().baseUrl(URL_MAIN).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
