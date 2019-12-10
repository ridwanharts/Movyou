package com.jangkriek.ridwanharts.movyou.main;


import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jangkriek.ridwanharts.movyou.BuildConfig;
import com.jangkriek.ridwanharts.movyou.R;
import com.jangkriek.ridwanharts.movyou.api.APIClient;
import com.jangkriek.ridwanharts.movyou.favorit.FavoritAdapter;
import com.jangkriek.ridwanharts.movyou.favorit.FavoritFragment;
import com.jangkriek.ridwanharts.movyou.favorit.ItemResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

import static com.jangkriek.ridwanharts.movyou.favorit.provider.DatabaseContract.CONTENT_URI;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingFragment extends Fragment {

    private Context context;
    private RecyclerView recyclerView;
    private Cursor cursor1;
    private MenuAdapter menuAdapter;

    private Call<MovieItems2> apiCall;
    private APIClient apiClient = null;
    private List<ItemResult>upcoming = new ArrayList<>();

    ProgressBar pb;

    public UpcomingFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);
        pb = (ProgressBar)view.findViewById(R.id.pb_upcoming);
        pb.setVisibility(View.VISIBLE);
        recyclerView = (RecyclerView)view.findViewById(R.id.rv_list2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        if(savedInstanceState != null){
            upcoming = savedInstanceState.getParcelableArrayList("movies");
            menuAdapter = new MenuAdapter(getActivity(), upcoming);
            recyclerView.setAdapter(menuAdapter);

        }else{
            apiClient = apiClient.getInstance();
            apiCall = apiClient.getApiCall().getUpcoming(BuildConfig.API_KEY, "en-us",1, Locale.getDefault().getCountry());
            apiCall.enqueue(new Callback<MovieItems2>() {
                @Override
                public void onResponse(Call<MovieItems2> call, retrofit2.Response<MovieItems2> response) {
                    if (response.isSuccessful()){
                        upcoming = response.body().getItemResults();
                        if(upcoming!=null){
                            pb.setVisibility(View.GONE);
                            menuAdapter = new MenuAdapter(getActivity(),upcoming);
                            recyclerView.setAdapter(menuAdapter);
                        }
                    }
                }

                @Override
                public void onFailure(Call<MovieItems2> call, Throwable t) {

                }
            });
        }

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        /*if(menuAdapter.getResult() != null){
            outState.putParcelableArrayList("movie", new ArrayList<>(menuAdapter.getResult()));
        }*/

    }
}
