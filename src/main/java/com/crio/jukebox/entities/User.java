package com.crio.jukebox.entities;

import java.util.ArrayList;
import java.util.List;

public class User extends BaseEntity{
    private String name;
    private List<String> playlistIds;
    private String activePlaylist;
    
    public User(User user) {
        this(user.id, user.name, user.playlistIds, user.activePlaylist);
    }
    
    public User(String name){
        this.name = name;
        this.id = null;
        this.playlistIds = new ArrayList<String>();
        this.activePlaylist = "";
    }

    public User(String id, String name,List<String>playlistIds,String activePlaylist) {
        this.id = id;
        this.name = name;
        this.playlistIds = playlistIds;
        this.activePlaylist = activePlaylist;
    }

    public String getName() {
        return name;
    }

    public List<String> getPlaylistIds() {
        return playlistIds;
    }

    public String getActivePlaylist() {
        return activePlaylist;
    }

    @Override
    public String toString(){
        return "User [id=" + id + ", name" + name + ", Playlist=" + playlistIds
                + ", ActivePlaylist=" + activePlaylist + "]";
    }
}
