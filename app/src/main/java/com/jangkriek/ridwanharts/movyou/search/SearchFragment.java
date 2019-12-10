package com.jangkriek.ridwanharts.movyou.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jangkriek.ridwanharts.movyou.BuildConfig;
import com.jangkriek.ridwanharts.movyou.DetilActivity;
import com.jangkriek.ridwanharts.movyou.R;
import com.jangkriek.ridwanharts.movyou.api.APIClient;
import com.jangkriek.ridwanharts.movyou.favorit.ItemResult;
import com.jangkriek.ridwanharts.movyou.main.MovieItems2;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private EditText etCari;
    private String search="cari";
    private RecyclerView rvHasil;
    private Button btnCari;
    private SearchAdapter mAdapter;
    private TextView tvNo;
    private List<ItemResult> movieItemsList;
    static final String EXTRAS_FILM = "EXTRAS_FILM";
    private View view;
    private static final String LANG = "en-us";
    private APIClient apiClient = null;
    private Call<MovieItems2> apiCall;;
    ProgressBar pb;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);
        pb = (ProgressBar)view.findViewById(R.id.pb_search);


        etCari = (EditText) view.findViewById(R.id.et_cari);
        tvNo = (TextView)view.findViewById(R.id.tv_no);
        btnCari = (Button)view.findViewById(R.id.b_cari);



        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager input = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                input.hideSoftInputFromWindow(etCari.getWindowToken(),0);

                etCari.onEditorAction(EditorInfo.IME_ACTION_DONE);
                String cariMovie = etCari.getText().toString();
                Toast.makeText(getActivity(), getString(R.string.search_hint)+" : "+cariMovie,Toast.LENGTH_LONG).show();

                if(TextUtils.isEmpty(cariMovie)){
                    return;
                }
                cari();

                Bundle bundle = new Bundle();
                bundle.putString(EXTRAS_FILM, cariMovie);
            }


        });

        movieItemsList = new ArrayList<>();
        rvHasil = (RecyclerView) view.findViewById(R.id.list_hasil);
        rvHasil.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        rvHasil.setHasFixedSize(true);
        mAdapter = new SearchAdapter(movieItemsList, getActivity());
        rvHasil.setAdapter(mAdapter);

        String cariMovie = etCari.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_FILM, cariMovie);

        return view;
    }


    private void cari (){
        pb.setVisibility(View.VISIBLE);
        apiClient = APIClient.getInstance();
        if (etCari == null){
            apiCall = apiClient.getApiCall().getSearch(BuildConfig.API_KEY, LANG, search);
            apiCall.enqueue(new Callback<MovieItems2>() {
                @Override
                public void onResponse(Call<MovieItems2> call, Response<MovieItems2> response) {
                    movieItemsList = response.body().getItemResults();
                    if (movieItemsList !=null){
                        pb.setVisibility(View.GONE);
                        mAdapter = new SearchAdapter(movieItemsList,getActivity());
                        rvHasil.setAdapter(mAdapter);
                    }
                    if (movieItemsList.size()==0){
                        Toast.makeText(getContext(),"Movie not found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<MovieItems2> call, Throwable t) {

                }
            });
        }else{
            apiCall = apiClient.getApiCall().getSearch(BuildConfig.API_KEY, LANG,  etCari.getText().toString());
            apiCall.enqueue(new Callback<MovieItems2>() {
                @Override
                public void onResponse(Call<MovieItems2> call, Response<MovieItems2> response) {
                    movieItemsList = response.body().getItemResults();
                    if (movieItemsList !=null){
                        pb.setVisibility(View.GONE);
                        mAdapter = new SearchAdapter(movieItemsList,getActivity());
                        rvHasil.setAdapter(mAdapter);
                    }
                    if (movieItemsList.size()==0){
                        Toast.makeText(getContext(),"Movie not found", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<MovieItems2> call, Throwable t) {

                }
            });
        }
    }


}
