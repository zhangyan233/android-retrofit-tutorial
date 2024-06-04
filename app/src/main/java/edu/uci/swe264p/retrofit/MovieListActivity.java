package edu.uci.swe264p.retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MovieListActivity extends AppCompatActivity {
    static final String TAG = MovieListActivity.class.getSimpleName();
    static Retrofit retrofit = null;
    private RecyclerView recyclerView;
    private List<Movie> movieList;
    static final String BASE_URL = "https://api.themoviedb.org/3/";
    final static String API_KEY = "5f7cb29d489bbbb86ca6e6d90f422eb2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).
                    addConverterFactory(GsonConverterFactory.create()).build();
        }

        recyclerView = findViewById(R.id.rvMovieList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MovieListActivity.this));

        //create specific object to require network interface
        MovieApiService movieApiService=retrofit.create(MovieApiService.class);
        Call<TopRatedResponse> call=movieApiService.getTopMovies(API_KEY);

        call.enqueue(new Callback<TopRatedResponse>() {
            @Override
            public void onResponse(Call<TopRatedResponse> call, Response<TopRatedResponse> response) {
                if(response.isSuccessful()){
                    movieList = response.body().getTopMovies();
                    recyclerView.setAdapter(new MovieListAdapter(movieList));
                }

            }
            @Override
            public void onFailure(Call<TopRatedResponse> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }
}
