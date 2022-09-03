package com.crio.jukebox.entities;

import java.util.List;

public class Song extends BaseEntity{
    private String songName;
    private List<String> featuredArtists;
    private String mainArtist;
    private String album;

    public Song(Song song) {
        this(song.id, song.songName,song.featuredArtists ,song.mainArtist,song.album);
    }

    public Song(String id, String songName, List<String>featuredArtists,String mainArtist ,String album) {
        this.id = id;
        this.songName = songName;
        this.featuredArtists = featuredArtists;
        this.mainArtist = mainArtist;
        this.album = album;
    }
    
    public String getSongName() {
        return songName;
    }
    
    public String getMainArtist() {
        return mainArtist;
    }

    public String getAlbum() {
        return album;
    }
    
    public List<String> getFeaturedArtists() {
        return featuredArtists;
    }
    @Override
    public String toString(){
        return "Song [id=" + id + ", name=" + songName + ", featured artist="+featuredArtists +", mainArtist=" + mainArtist + ", album=" + album
                + "]";
    }
}
