package com.jangkriek.ridwanharts.movyou;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.jangkriek.ridwanharts.movyou.api.APIClient;
import com.jangkriek.ridwanharts.movyou.favorit.DetilMovie;
import com.jangkriek.ridwanharts.movyou.favorit.provider.DatabaseContract;
import com.squareup.picasso.Picasso;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.jangkriek.ridwanharts.movyou.favorit.provider.DatabaseContract.CONTENT_URI;
import static com.jangkriek.ridwanharts.movyou.favorit.provider.DatabaseContract.getColumnString;

public class DetilActivity extends AppCompatActivity implements View.OnClickListener {

    public static String EXTRA_JUDUL = "extra_judul";
    public static String EXTRA_DESKRIPSI = "extra_deskripsi";
    public static String EXTRA_TANGGAL = "extra_tanggal";
    public static String EXTRA_GAMBAR = "extra_gambar";
    public static String EXTRA_RATING = "extra_rating";

    public static final String movie_item = "movie_item";

    private TextView tvJudul, tvDeskripsi, tvTanggal, tvRating;
    private ImageView imgGambar;
    private ImageView imgFavorAdd;
    private ImageView imgFavorOut;

    private boolean isFavor;
    private int movieId;
    private Call<DetilMovie> apiCall;
    private APIClient apiClient = null;
    private DetilMovie detilMovie;

    private Uri uri;
    private String state;
    private ProgressDialog pd;

    public static String MOVIE_ID = "movie_id";
    public static String STATE = "state";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detil);

        tvJudul = (TextView)findViewById(R.id.tvJdl);
        tvDeskripsi = (TextView)findViewById(R.id.tvDesk);
        tvTanggal = (TextView)findViewById(R.id.tvTglRilis);
        imgGambar = (ImageView)findViewById(R.id.ivFilm);
        tvRating = (TextView)findViewById(R.id.tvRating);
        imgFavorAdd = (ImageView)findViewById(R.id.iv_favor_add);
        imgFavorOut = (ImageView)findViewById(R.id.iv_favor_out);
        pd = new ProgressDialog(this);


        uri = getIntent().getData();

        movieId = getIntent().getIntExtra(MOVIE_ID, movieId);
        state = getIntent().getStringExtra(STATE);
        imgFavorAdd.setOnClickListener(this);
        imgFavorOut.setOnClickListener(this);

        if(savedInstanceState != null){
            detilMovie = savedInstanceState.getParcelable("movies");
            //
            if(state == null){
                state = "0";
            }
            if(state.equals("1")){
                pd.setMessage("Loading...");
                pd.show();
                Cursor c = getContentResolver().query(uri, null, null, null, null);
                if(c!=null){
                    if (c.moveToFirst()){
                        detilMovie = new DetilMovie(c);
                        setMovie();
                    }
                    c.close();
                }
                pd.dismiss();
            }
            //
            if (state.equals("0")){
                pd.setMessage("Loading...");
                pd.show();
                pd.setCanceledOnTouchOutside(false);
                setMovie();
            }

        }else{
            if (state.equals("1")){
                pd.setMessage("Loading...");
                pd.show();
                Cursor c = getContentResolver().query(uri, null, null, null, null);
                if(c!=null){
                    if (c.moveToFirst()){
                        detilMovie = new DetilMovie(c);
                        pd.dismiss();
                        setMovie();
                    }
                    c.close();
                }
                pd.dismiss();
            }
            if (state.equals("0")){
                apiClient = APIClient.getInstance();

                apiCall = apiClient.getApiCall().getIdMovie(movieId, BuildConfig.API_KEY);
                apiCall.enqueue(new Callback<DetilMovie>() {
                    @Override
                    public void onResponse(Call<DetilMovie> call, Response<DetilMovie> response) {
                        if (response.isSuccessful()){
                            detilMovie = response.body();
                            pd.dismiss();
                            setMovie();
                        }
                    }
                    @Override
                    public void onFailure(Call<DetilMovie> call, Throwable t) {
                        Toast.makeText(DetilActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        loadDataSqlite();
    }
    private void addFavorite(){

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.FavoriteColumns.MOVIE_ID, detilMovie.getmId());
        values.put(DatabaseContract.FavoriteColumns.COLUMN_JUDUL, detilMovie.getmJudul());
        values.put(DatabaseContract.FavoriteColumns.COLUMN_DESKRIPSI, detilMovie.getmDeskripsi());
        values.put(DatabaseContract.FavoriteColumns.COLUMN_TANGGAL, detilMovie.getmTanggal());
        values.put(DatabaseContract.FavoriteColumns.COLUMN_GAMBAR, detilMovie.getmGambar());
        values.put(DatabaseContract.FavoriteColumns.COLUMN_RATING, detilMovie.getmRating());
        getContentResolver().insert(CONTENT_URI, values);
        Toast.makeText(getBaseContext(), "Movie added to favorite list",Toast.LENGTH_SHORT).show();
    }
    private void removeFavorit(){
        Toast.makeText(getBaseContext(), "Hapus : " + CONTENT_URI+ "/" + movieId + " atau : " + detilMovie.getmId(), Toast.LENGTH_SHORT).show();
        getContentResolver().delete(Uri.parse(CONTENT_URI+ "/" + detilMovie.getmId()), null, null);
        Toast.makeText(getBaseContext(), "Movie has been removed",Toast.LENGTH_SHORT).show();
    }

    private void loadDataSqlite(){
        Cursor c = getContentResolver().query(Uri.parse(CONTENT_URI + "/" + movieId), null, null, null,null);
        if (c != null) {
           if (c.moveToFirst()) {
               String mId = "" + movieId;
               String idMovie = getColumnString(c, DatabaseContract.FavoriteColumns.MOVIE_ID);
               if (idMovie.equals(mId)) {
                   isFavor = true;
               }
           }
           c.close();
        }else {
           isFavor = false;

        }
        setFavor();
    }
    private void setFavor(){
        if(isFavor){
            imgFavorOut.setVisibility(View.INVISIBLE);
            imgFavorAdd.setVisibility(View.VISIBLE);
        }else{
            imgFavorOut.setVisibility(View.VISIBLE);
            imgFavorAdd.setVisibility(View.INVISIBLE);
        }
    }
    public static String getCountry(){
        String c = Locale.getDefault().getCountry().toLowerCase();
        switch (c){
            case "id":
                break;
                default:
                    c = "en";
                    break;
        }
        return c;
    }

    private void setMovie(){
        tvJudul.setText(detilMovie.getmJudul());
        tvDeskripsi.setText(detilMovie.getmDeskripsi());
        tvRating.setText(detilMovie.getmRating());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date date = format.parse(detilMovie.getmTanggal());
            SimpleDateFormat formatBaru = new SimpleDateFormat("EEEE, MMM dd, yyyy");
            String rilis = formatBaru.format(date);
            tvTanggal.setText(rilis);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Picasso.get().load("https:///image.tmdb.org/t/p/w780/"+detilMovie.getmGambar()).into(imgGambar);
    }

    @Override
    public void onClick(View v) {
        int click = v.getId();
        if(click == R.id.iv_favor_add){
            isFavor=false;
            setFavor();
            removeFavorit();
        }else if(click == R.id.iv_favor_out){

            isFavor=true;
            setFavor();
            addFavorite();
        }
    }


}
