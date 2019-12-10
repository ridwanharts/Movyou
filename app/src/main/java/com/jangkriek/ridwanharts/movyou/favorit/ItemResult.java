package com.jangkriek.ridwanharts.movyou.favorit;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ItemResult implements Parcelable {

    @SerializedName("title")
    private String mjudul;

    @SerializedName("overview")
    private String mdeskripsi;

    @SerializedName("release_date")
    private String mtanggal;

    @SerializedName("id")
    private int movieId;

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

    public int getmovieId() {
        return movieId;
    }

    public void setmovieId(int id) {
        this.movieId = id;
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

    @Override
    public String toString() {
        return
                "ItemResult{" +
                        "title = '"+mjudul+ '\'' +
                        ",overview = '"+mdeskripsi+ '\'' +
                        ",release_date = '"+mtanggal+ '\'' +
                        ",id = '"+movieId+ '\'' +
                        ",poster_path = '"+mgambar+ '\'' +
                        ",vote_average = '"+mrating+ '\'' +
                        "}";
    }

    public static final  Parcelable.Creator<ItemResult>CREATOR = new Parcelable.Creator<ItemResult>(){


        @Override
        public ItemResult createFromParcel(Parcel source) {
            return new ItemResult(source);
        }

        @Override
        public ItemResult[] newArray(int size) {
            return new ItemResult[size];
        }
    };

    public ItemResult(long movieId, String judul, String deskripsi, String tanggal, String gambar, String rating){

    }

    public ItemResult(Parcel in){
        this.movieId=in.readInt();
        this.mjudul=in.readString();
        this.mtanggal=in.readString();
        this.mdeskripsi=in.readString();
        this.mgambar=in.readString();
        this.mrating=in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.movieId);
        dest.writeValue(this.mjudul);
        dest.writeValue(this.mdeskripsi);
        dest.writeValue(this.mtanggal);
        dest.writeValue(this.mgambar);
        dest.writeValue(this.mrating);

    }
}
