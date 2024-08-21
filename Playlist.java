package com.example.securevideostreamer;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Playlist extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PlaylistAdapter adapter;
    private List<PlaylistModel> playlistModels;
     String[] json={"https://raw.githubusercontent.com/ujjawalTHEBATMAN/jsonFileForCAStoreCourseURL/main/jk.json","https://raw.githubusercontent.com/ujjawalTHEBATMAN/jsonFileForCAStoreCourseURL/main/playlist2.json","https://raw.githubusercontent.com/ujjawalTHEBATMAN/jsonFileForCAStoreCourseURL/main/jk.json"};
    String temp="gl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        playlistModels = new ArrayList<>();

        playlistModels.add(new PlaylistModel("Playlist 1", R.drawable.gl, 9.99));
        playlistModels.add(new PlaylistModel("Playlist 2", R.drawable.gl, 12.99));
        playlistModels.add(new PlaylistModel("Playlist 3", R.drawable.gl, 14.99));

        adapter = new PlaylistAdapter(playlistModels);
        recyclerView.setAdapter(adapter);

        adapter.setOnPlaylistButtonClickListener(new PlaylistAdapter.OnPlaylistButtonClickListener() {
            @Override
            public void onPlaylistButtonClick(int position) {
                // Handle the button click here
                startActivity(new Intent(getApplicationContext(), mainDashboard.class).putExtra("Enter code",json[position]));

            }
        });
    }
}