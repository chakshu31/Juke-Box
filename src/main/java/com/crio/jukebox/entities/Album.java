package com.crio.jukebox.entities;
import java.util.List;

public class Album {
    private String albumOwner;
    private List<Song> songs;
    private List<Artist> artists;
    private String albumName;

    public Album(Album album){
        this(album.albumOwner,album.songs,album.artists,album.albumName);
    }
    public Album(String albumOwner,List<Song>songs,List<Artist>artists,String albumName){
        this.albumOwner= albumOwner;
        this.songs=songs;
        this.artists = artists;
        this.albumName = albumName;
    }

    @Override
    public String toString(){
        return "Album [Album Name=" + albumName + ", AlbumOwner=" + albumOwner + ", Artists="
                + artists + ", Songs=" + songs + "]";
    }
}
