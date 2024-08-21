package com.example.securevideostreamer;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class mainDashboard extends AppCompatActivity {


    private static final String TAG = "TAG";
    private RecyclerView videoList;
    private VideoAdapter adapter;
    private List<Video> allVideos;
    private RequestQueue requestQueue;
    String jshon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard);
        allVideos = new ArrayList<>();

        jshon=getIntent().getStringExtra("Enter code").toString();
        videoList = findViewById(R.id.videoList);
        videoList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VideoAdapter(this, allVideos);
        videoList.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(this);
        getJsonData();
    }

    private void getJsonData() {
        String url = jshon;
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray categories = response.getJSONArray("categories");
                    JSONObject categoriesData = categories.getJSONObject(0);
                    JSONArray videos = categoriesData.getJSONArray("videos");

                    for (int i = 0; i < videos.length(); i++) {
                        JSONObject video = videos.getJSONObject(i);
                        Video v = parseVideo(video);
                        allVideos.add(v);
                    }
                    updateUI();
                } catch (JSONException e) {
                    Log.e(TAG, "Error parsing JSON", e);
                    Toast.makeText(mainDashboard.this, ""+e, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error fetching data", error);
                // Display an error message to the user
            }
        });
        requestQueue.add(objectRequest);
    }

    private Video parseVideo(JSONObject video) throws JSONException {
        Video v = new Video();
        v.setTitle(video.getString("title"));
        v.setDescription(video.getString("description"));
        v.setAuthor(video.getString("subtitle"));
        v.setImageUrl(video.getString("thumb"));
        JSONArray videoUrl = video.getJSONArray("sources");
        String videoUrlString = videoUrl.getString(0);
        videoUrlString = videoUrlString.replace("http:", "https:"); // Replace http with https
        v.setVideoUrl(videoUrlString);
        return v;
    }

    private void updateUI() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }
}