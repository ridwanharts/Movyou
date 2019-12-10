package com.jangkriek.ridwanharts.movyou.search;

import org.json.JSONException;
import org.json.JSONObject;

class MovieItems {

    private String judul;
    private String deskripsi;
    private String tanggal;
    private String id;
    private String gambar;
    private String rating;

    public MovieItems(JSONObject objek){
        try{
            String aJudul = objek.getString("title");
            String aDeskripsi = objek.getString("overview");
            String aTanggal = objek.getString("release_date");
            String aNo = objek.getString("id");
            String aGambar = objek.getString("poster_path");
            String aRating = objek.getString("vote_average");

            this.judul=aJudul;
            this.deskripsi=aDeskripsi;
            this.gambar=aGambar;
            this.id=aNo;
            this.rating=aRating;
            this.tanggal=aTanggal;
        }catch (JSONException a){
            a.printStackTrace();
        }
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
