package com.jangkriek.ridwanharts.movyou.favorit.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jangkriek.ridwanharts.movyou.favorit.provider.DatabaseContract;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static int DATABASE_VERSION = 1;
    public static String DATABASE_NAME = "db_movie";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE  %s"
            + " (%s integer PRIMARY KEY AUTOINCREMENT," +
            "%s text not null," +
            "%s text not null," +
            "%s text not null," +
            "%s text not null," +
            "%s text not null," +
            "%s text not null)",
                    DatabaseContract.TABLE_NAME,
                    DatabaseContract.FavoriteColumns._ID,
                    DatabaseContract.FavoriteColumns.MOVIE_ID,
                    DatabaseContract.FavoriteColumns.COLUMN_JUDUL,
                    DatabaseContract.FavoriteColumns.COLUMN_DESKRIPSI,
                    DatabaseContract.FavoriteColumns.COLUMN_TANGGAL,
                    DatabaseContract.FavoriteColumns.COLUMN_GAMBAR,
                    DatabaseContract.FavoriteColumns.COLUMN_RATING
    );

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DatabaseContract.TABLE_NAME);
        onCreate(db);

    }
}
