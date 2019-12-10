package com.jangkriek.ridwanharts.movyou.favorit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.jangkriek.ridwanharts.movyou.favorit.DetilMovie;
import com.jangkriek.ridwanharts.movyou.favorit.provider.DatabaseContract;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.jangkriek.ridwanharts.movyou.favorit.provider.DatabaseContract.FavoriteColumns.COLUMN_DESKRIPSI;
import static com.jangkriek.ridwanharts.movyou.favorit.provider.DatabaseContract.FavoriteColumns.COLUMN_GAMBAR;
import static com.jangkriek.ridwanharts.movyou.favorit.provider.DatabaseContract.FavoriteColumns.COLUMN_JUDUL;
import static com.jangkriek.ridwanharts.movyou.favorit.provider.DatabaseContract.FavoriteColumns.COLUMN_RATING;
import static com.jangkriek.ridwanharts.movyou.favorit.provider.DatabaseContract.FavoriteColumns.COLUMN_TANGGAL;
import static com.jangkriek.ridwanharts.movyou.favorit.provider.DatabaseContract.FavoriteColumns.MOVIE_ID;

public class FavoriteHelper {

    private static String TABLE_NAME = DatabaseContract.TABLE_NAME;
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;
    public  FavoriteHelper(Context context){
        this.context=context;
    }

    public FavoriteHelper open()throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        databaseHelper.close();
    }

    public ArrayList<DetilMovie>query(){
        ArrayList<DetilMovie>arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, null, null, null, null, null, _ID + " DESC",null);
        cursor.moveToFirst();
        DetilMovie detilMovie;
        if (cursor.getCount()>0){
            do {
                detilMovie = new DetilMovie();
                detilMovie.setmId(cursor.getInt(cursor.getColumnIndexOrThrow(MOVIE_ID)));
                detilMovie.setmJudul(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JUDUL)));
                detilMovie.setmDeskripsi(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESKRIPSI)));
                detilMovie.setmTanggal(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TANGGAL)));
                detilMovie.setmGambar(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GAMBAR)));
                detilMovie.setmRating(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RATING)));

                arrayList.add(detilMovie);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public int update(DetilMovie detilMovie){
        ContentValues cv = new ContentValues();
        cv.put(MOVIE_ID, detilMovie.getmId());
        cv.put(COLUMN_JUDUL, detilMovie.getmJudul());
        cv.put(COLUMN_DESKRIPSI, detilMovie.getmDeskripsi());
        cv.put(COLUMN_TANGGAL, detilMovie.getmTanggal());
        cv.put(COLUMN_GAMBAR, detilMovie.getmGambar());
        cv.put(COLUMN_RATING, detilMovie.getmRating());

        return sqLiteDatabase.update(TABLE_NAME, cv, MOVIE_ID + "= '" + detilMovie.getmId() + "'", null);
    }
    public int delete(int id){
        return sqLiteDatabase.delete(TABLE_NAME, MOVIE_ID + " = '" + id + "'", null);
    }
    public Cursor isFavor(int id){
        String movieId = "" + id;
        return sqLiteDatabase.query(
                TABLE_NAME, null, MOVIE_ID + " = ?", new String[]{movieId},null,null,null);
    }

    public Cursor queryProvider(){
        return  sqLiteDatabase.query(
            TABLE_NAME,null, null, null, null,null,_ID +" DESC"

        );
    }

    public Cursor queryByIdProvider(String id){
        return  sqLiteDatabase.query(TABLE_NAME, null,MOVIE_ID+ " = ?", new String[]{id},null,null,null);
    }

    public long insertProvider(ContentValues cv){
        return sqLiteDatabase.insert(TABLE_NAME, null,cv);
    }

    public int updateProvider(String id, ContentValues cv){
        return sqLiteDatabase.update(TABLE_NAME, cv,MOVIE_ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id){
        return sqLiteDatabase.delete(TABLE_NAME,MOVIE_ID+ " = ?", new String[]{id});
    }
}
