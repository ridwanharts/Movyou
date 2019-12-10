package com.jangkriek.ridwanharts.favorit;


import android.database.Cursor;
import android.os.Parcel;
import static com.jangkriek.ridwanharts.favorit.DatabaseContract.getColumnInt;
import static com.jangkriek.ridwanharts.favorit.DatabaseContract.getColumnString;

public class DetilMovie {

    private String mjudul;

    private String mdeskripsi;

    private String mtanggal;

    private int mId;

    private String mgambar;

    private String mrating;

    public String getmJudul() {
        return mjudul;
    }

    public void setmJudul(String judul) {
        this.mjudul = judul;
    }

    public String getmDeskripsi() {
        return mdeskripsi;
    }

    public void setmDeskripsi(String deskripsi) {
        this.mdeskripsi = deskripsi;
    }

    public String getmTanggal() {
        return mtanggal;
    }

    public void setmTanggal(String tanggal) {
        this.mtanggal = tanggal;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int id) {
        this.mId = id;
    }

    public String getmGambar() {
        return mgambar;
    }

    public void setmGambar(String gambar) {
        this.mgambar = gambar;
    }

    public String getmRating() {
        return mrating;
    }

    public void setmRating(String rating) {
        this.mrating = rating;
    }

    public DetilMovie(Cursor cursor){
        this.mId = getColumnInt(cursor, DatabaseContract.FavoriteColumns.MOVIE_ID);
        this.mjudul = getColumnString(cursor, DatabaseContract.FavoriteColumns.COLUMN_JUDUL);
        this.mdeskripsi = getColumnString(cursor, DatabaseContract.FavoriteColumns.COLUMN_DESKRIPSI);
        this.mtanggal = getColumnString(cursor, DatabaseContract.FavoriteColumns.COLUMN_TANGGAL);
        this.mgambar = getColumnString(cursor, DatabaseContract.FavoriteColumns.COLUMN_GAMBAR);
        this.mrating = getColumnString(cursor, DatabaseContract.FavoriteColumns.COLUMN_RATING);
    }


    @Override
    public String toString() {
        return
                "DetilMovie{" +
                        "title = '"+mjudul+ '\'' +
                        ",overview = '"+mdeskripsi+ '\'' +
                        ",release_date = '"+mtanggal+ '\'' +
                        ",id = '"+mId+ '\'' +
                        ",poster_path = '"+mgambar+ '\'' +
                        ",vote_average = '"+mrating+ '\'' +
                        "}";
    }


    public DetilMovie(){

    }

    protected DetilMovie(Parcel in){
        this.mId=(Integer)in.readValue(Integer.class.getClassLoader());
        this.mjudul=in.readString();
        this.mtanggal=in.readString();
        this.mdeskripsi=in.readString();
        this.mgambar=in.readString();
        this.mrating=in.readString();
    }

    public DetilMovie(Integer id,String judul, String deskripsi, String tanggal, String gambar, String rating){
        this.mId=id;
        this.mjudul=judul;
        this.mtanggal=tanggal;
        this.mdeskripsi=deskripsi;
        this.mgambar=gambar;
        this.mrating=rating;
    }
}
