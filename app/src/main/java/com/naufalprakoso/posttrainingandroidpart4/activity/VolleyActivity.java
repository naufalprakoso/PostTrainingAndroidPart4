package com.naufalprakoso.posttrainingandroidpart4.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.naufalprakoso.posttrainingandroidpart4.R;
import com.naufalprakoso.posttrainingandroidpart4.adapter.MovieAdapter;
import com.naufalprakoso.posttrainingandroidpart4.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VolleyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);

        final MovieAdapter adapter = new MovieAdapter(this);

        RecyclerView recyclerView = findViewById(R.id.rv_data);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);

        final ArrayList<Movie> movies = new ArrayList<>();

        final String url = "https://api.myjson.com/bins/chup8";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        Movie movie = new Movie();
                        movie.setTitle(jsonObject.getString("title"));
                        movie.setRating(jsonObject.getDouble("rating"));
                        movie.setReleaseYear(jsonObject.getInt("releaseYear"));

                        movies.add(movie);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                adapter.setMovies(movies);
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}
