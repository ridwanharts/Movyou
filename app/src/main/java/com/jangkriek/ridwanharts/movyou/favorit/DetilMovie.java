package com.jangkriek.ridwanharts.movyou.favorit;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.jangkriek.ridwanharts.movyou.favorit.provider.DatabaseContract;

import static android.provider.BaseColumns._ID;
import static com.jangkriek.ridwanharts.movyou.favorit.provider.DatabaseContract.getColumnInt;
import static com.jangkriek.ridwanharts.movyou.favorit.provider.DatabaseContract.getColumnString;

public class DetilMovie implements Parcelable {

    @SerializedName("title")
    private String mjudul;

    @SerializedName("overview")
    private String mdeskripsi;

    @SerializedName("release_date")
    private String mtanggal;

    @SerializedName("id")
    private int mId;

    @SerializedName("poster_path")
    private String mgambar;

    @SerializedName("vote_average")
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.mId);
        dest.writeValue(this.mjudul);
        dest.writeValue(this.mdeskripsi);
        dest.writeValue(this.mtanggal);
        dest.writeValue(this.mgambar);
        dest.writeValue(this.mrating);

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

    public static final  Parcelable.Creator<DetilMovie>CREATOR = new Parcelable.Creator<DetilMovie>(){


        @Override
        public DetilMovie createFromParcel(Parcel source) {
            return new DetilMovie(source);
        }

        @Override
        public DetilMovie[] newArray(int size) {
            return new DetilMovie[size];
        }
    };
}
