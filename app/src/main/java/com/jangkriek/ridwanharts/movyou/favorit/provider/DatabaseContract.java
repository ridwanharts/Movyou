package com.jangkriek.ridwanharts.movyou.favorit.provider;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static String TABLE_NAME = "movie_favorite";
    public static final  String AUTHORITY = "com.jangkriek.ridwanharts.movyou";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content").authority(AUTHORITY).appendPath(TABLE_NAME).build();

    public static final class FavoriteColumns implements BaseColumns {

        public static String MOVIE_ID = "movie_id";
        public static String COLUMN_JUDUL = "title";
        public static String COLUMN_DESKRIPSI = "overview";
        public static String COLUMN_TANGGAL = "release_date";
        public static String COLUMN_GAMBAR = "poster";
        public static String COLUMN_RATING = "vote";
    }

    public static String getColumnString(Cursor cursor, String nameColumn){
        return cursor.getString(cursor.getColumnIndex(nameColumn));
    }

    public static int getColumnInt(Cursor cursor, String nameColumn){
        return cursor.getInt(cursor.getColumnIndex(nameColumn));
    }

}
