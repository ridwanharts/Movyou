package com.jangkriek.ridwanharts.favorit;


import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.jangkriek.ridwanharts.favorit.DatabaseContract.CONTENT_URI;
import static com.jangkriek.ridwanharts.favorit.DatabaseContract.getColumnString;

public class DetilActivity extends AppCompatActivity {

    public static final String movie_item = "movie_item";

    private TextView tvJudul, tvDeskripsi, tvTanggal, tvRating;
    private ImageView imgGambar;

    private int movieId;
    private DetilMovie detilMovie;
    private Uri uri;
    private String state;

    public static String MOVIE_ID = "movie_id";
    public static String STATE = "state";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detil);

        tvJudul = (TextView)findViewById(R.id.tvJdl1);
        tvDeskripsi = (TextView)findViewById(R.id.tvDesk1);
        tvTanggal = (TextView)findViewById(R.id.tvTglRilis1);
        imgGambar = (ImageView)findViewById(R.id.ivFilm1);
        tvRating = (TextView)findViewById(R.id.tvRating1);


        uri = getIntent().getData();

        movieId = getIntent().getIntExtra(MOVIE_ID, movieId);
        state = getIntent().getStringExtra(STATE);
            if (uri == null)return;
            Cursor cursor = getContentResolver().query(uri, null,null,null,null);
            if(cursor!=null){
                if(cursor.moveToFirst())detilMovie = new DetilMovie(cursor);
                cursor.close();
            }
            setMovie();
            loadDataSqlite();
    }

    private void loadDataSqlite(){
        Cursor c = getContentResolver().query(Uri.parse(CONTENT_URI + "/" + movieId), null, null, null,null);
        if (c != null) {
            if (c.moveToFirst()) {
                String mId = "" + movieId;
                String idMovie = getColumnString(c, DatabaseContract.FavoriteColumns.MOVIE_ID);
                if (idMovie.equals(mId)) {
                }
            }
            c.close();
        }
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
        Picasso.get().load("http://image.tmdb.org/t/p/w780/"+detilMovie.getmGambar()).into(imgGambar);
    }
}