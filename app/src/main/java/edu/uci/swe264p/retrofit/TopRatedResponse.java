package edu.uci.swe264p.retrofit;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.List;

public class TopRatedResponse {
    @SerializedName("results")
    private List<Movie> topMovies;

    public TopRatedResponse(List<Movie> list) {
        this.topMovies = list;
    }

    public List<Movie> getTopMovies() {
        return this.topMovies;
    }
}
