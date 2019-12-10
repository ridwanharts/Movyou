package com.jangkriek.ridwanharts.movyou.main;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jangkriek.ridwanharts.movyou.favorit.ItemResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MovieItems2 {



    @SerializedName("page")
    @Expose
    private Integer page;

    @SerializedName("results")
    @Expose
    private List<ItemResult>itemResults = null;

    public List<ItemResult> getItemResults() {
        return itemResults;
    }

    public void setItemResults(List<ItemResult> itemResults) {
        this.itemResults = itemResults;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public MovieItems2(Integer page, List<ItemResult>movieItems){
        this.page=page;
        itemResults = movieItems;
    }
}
