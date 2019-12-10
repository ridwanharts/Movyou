package com.jangkriek.ridwanharts.favorit;

import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import static com.jangkriek.ridwanharts.favorit.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity {

    private Cursor cursor;
    private FavoritAdapter favoritAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.rv_fav);

        favoritAdapter = new FavoritAdapter(cursor, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(favoritAdapter);

        new loadData().execute();

    }

    private class loadData extends AsyncTask<Void, Void, Cursor>{


        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContentResolver().query(CONTENT_URI, null, null, null,null);
        }

        @Override
        protected void onPostExecute(Cursor items) {
            super.onPostExecute(items);
            cursor = items;
            favoritAdapter.setListMovie(cursor);

        }
    }
}
