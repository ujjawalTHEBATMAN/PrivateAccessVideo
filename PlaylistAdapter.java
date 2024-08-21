package com.example.securevideostreamer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {
    private List<PlaylistModel> playlistModels;
    private OnPlaylistButtonClickListener onPlaylistButtonClickListener;

    public interface OnPlaylistButtonClickListener {
        void onPlaylistButtonClick(int position);
    }

    public void setOnPlaylistButtonClickListener(OnPlaylistButtonClickListener onPlaylistButtonClickListener) {
        this.onPlaylistButtonClickListener = onPlaylistButtonClickListener;
    }

    public PlaylistAdapter(List<PlaylistModel> playlistModels) {
        this.playlistModels = playlistModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlaylistModel playlistModel = playlistModels.get(position);
        holder.playlistImage.setImageResource(playlistModel.getPlaylistImage());
        holder.playlistName.setText(playlistModel.getPlaylistName());
        holder.playlistPrice.setText(String.valueOf(playlistModel.getPrice()));
        holder.playlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPlaylistButtonClickListener != null) {
                    onPlaylistButtonClickListener.onPlaylistButtonClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return playlistModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView playlistImage;
        TextView playlistName;
        TextView playlistPrice;
        Button playlistButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            playlistImage = itemView.findViewById(R.id.playlist_image);
            playlistName = itemView.findViewById(R.id.playlist_name);
            playlistPrice = itemView.findViewById(R.id.playlist_price);
            playlistButton = itemView.findViewById(R.id.playlist_button);
        }
    }
}