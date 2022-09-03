package com.crio.jukebox.entities;

import java.util.List;

public class Artist {
    private String artistName;
    //private List<Artist> artists;
    private List<String> artists;

    public Artist(Artist artist) {
        this(artist.artistName, artist.artists);
    }

    public Artist(String artistName, List<String> artists) {
        this.artistName = artistName; // e.g. oneDirection
        this.artists = artists;
    }

    public List<String> getArtists(){
        return artists;
    }

    @Override
    public String toString() {
        return "Artist [Artist Name=" + artistName + ", Artists=" + artists + "]";
    } 
}
