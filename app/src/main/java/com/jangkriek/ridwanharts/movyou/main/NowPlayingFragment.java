package com.jangkriek.ridwanharts.movyou.main;


import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jangkriek.ridwanharts.movyou.MainActivity;
import com.jangkriek.ridwanharts.movyou.R;
import com.jangkriek.ridwanharts.movyou.api.APIClient;
import com.jangkriek.ridwanharts.movyou.favorit.ItemResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

import static com.jangkriek.ridwanharts.movyou.BuildConfig.API_KEY;


/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends Fragment {

    private Context context;
    private RecyclerView recyclerView;
    private Cursor cursor1;
    private MenuAdapter menuAdapter;

    private Call<MovieItems2> apiCall;
    private APIClient apiClient = null;
    private List<ItemResult>nowPlaying = new ArrayList<>();
    private int page = 1;
    private static final String LANG = "en-us";
    ProgressBar pb;
    public NowPlayingFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_now_playing, container, false);
        pb = (ProgressBar)v.findViewById(R.id.pb_now);
        pb.setVisibility(View.VISIBLE);


        recyclerView = (RecyclerView)v.findViewById(R.id.rv_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        if(savedInstanceState != null){
            nowPlaying = savedInstanceState.getParcelableArrayList("movies");
            menuAdapter = new MenuAdapter(getActivity(), nowPlaying);
            recyclerView.setAdapter(menuAdapter);

        }else{
            apiClient = apiClient.getInstance();
            apiCall = apiClient.getApiCall().getNowPlaying(API_KEY, LANG, page, Locale.getDefault().getCountry());
            apiCall.enqueue(new Callback<MovieItems2>() {
                @Override
                public void onResponse(Call<MovieItems2> call, retrofit2.Response<MovieItems2> response) {
                    if (response.isSuccessful()){
                        nowPlaying = response.body().getItemResults();
                        if(nowPlaying!=null){
                            pb.setVisibility(View.GONE);
                            menuAdapter = new MenuAdapter(getActivity(),nowPlaying);
                            recyclerView.setAdapter(menuAdapter);
                        }
                    }
                }

                @Override
                public void onFailure(Call<MovieItems2> call, Throwable t) {

                }
            });
        }
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //if(menuAdapter.getResult() != null) outState.putParcelableArrayList("movie", new ArrayList<>(menuAdapter.getResult()));
    }
}
