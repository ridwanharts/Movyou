package com.jangkriek.ridwanharts.movyou.favorit;


import android.arch.lifecycle.LiveData;
import android.content.ContentProvider;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jangkriek.ridwanharts.movyou.BuildConfig;
import com.jangkriek.ridwanharts.movyou.R;
import com.jangkriek.ridwanharts.movyou.api.APICall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.jangkriek.ridwanharts.movyou.favorit.provider.DatabaseContract.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritFragment extends Fragment {


    private Context context;
    private RecyclerView recyclerView;
    private Cursor cursor1;
    private FavoritAdapter favoritAdapter;
    //private static final String API_URL = BuildConfig.URL_MAIN+"/{id}?api_key="+BuildConfig.API_KEY +"&language=en-US";

    public FavoritFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_favorit,container,false);
        context = v.getContext();

        recyclerView = (RecyclerView)v.findViewById(R.id.rv_fav);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        favoritAdapter = new FavoritAdapter(cursor1,context);
        recyclerView.setAdapter(favoritAdapter);

        new initData().execute();
        return v;
    }

    private class initData extends AsyncTask<Void, Void, Cursor>{

        @Override
        protected Cursor doInBackground(Void... voids) {
            return  context.getContentResolver().query(CONTENT_URI,null,null,null,null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            cursor1 = cursor;
            favoritAdapter.setListMovie(cursor1);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        new initData().execute();
    }

    private void showSnackbarMessage(String message){
        Snackbar.make(recyclerView, message,Snackbar.LENGTH_SHORT).show();
    }
}
