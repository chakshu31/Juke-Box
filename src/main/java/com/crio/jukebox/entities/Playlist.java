package com.crio.jukebox.entities;

import java.util.List;

public class Playlist extends BaseEntity{
    private String playlistName;
    private List<String> songs; 
    private String userId; // user id
    private String currentSongId;

    public Playlist(Playlist playlist) {
        this(playlist.id,playlist.playlistName, playlist.songs, playlist.userId,playlist.currentSongId);
    }
    
    public Playlist(String id, String playlistName, List<String> songs, String userId,
            String currentSongId) {
        this.id = id;
        this.playlistName = playlistName;
        this.songs = songs;
        this.userId = userId;
        this.currentSongId = currentSongId;
    }
    
    public Playlist(String playlistName, String userId, List<String> songs) {
        this.id = null;
        this.playlistName = playlistName;
        this.songs = songs;
        this.userId = userId;
        this.currentSongId = "";
    }

    public String getplaylistName() {
        return this.playlistName;
    }
    
    public List<String> getSongs() {
        return this.songs;
    }
    
    public String getUser() {
        return this.userId;
    }
    
    public String getCurrentSongId() {
        return currentSongId;
    }
    
    public boolean songInPlaylist(String songId){
        if (songs.contains(songId) == true) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Playlist [id=" + id + ", name=" + playlistName + ", Songs=" + songs + ", userId="
                + userId + "]";
    }
}
