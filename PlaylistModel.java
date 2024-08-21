package com.example.securevideostreamer;

public class PlaylistModel {
    private String playlistName;
    private int playlistImage;
    private double price;

    public PlaylistModel(String playlistName, int playlistImage, double price) {
        this.playlistName = playlistName;
        this.playlistImage = playlistImage;
        this.price = price;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public int getPlaylistImage() {
        return playlistImage;
    }

    public void setPlaylistImage(int playlistImage) {
        this.playlistImage = playlistImage;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}