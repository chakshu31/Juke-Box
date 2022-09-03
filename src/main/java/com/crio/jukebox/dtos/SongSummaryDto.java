package com.crio.jukebox.dtos;

import java.util.List;
import com.crio.jukebox.entities.Artist;

public class SongSummaryDto {
    private String songName;
    private String albumName;
    private List<Artist> artists;
    
    public SongSummaryDto(String songName, String albumName, List<Artist> artists) {
        this.songName = songName;
        this.albumName = albumName;
        this.artists = artists;
    }

    public SongSummaryDto(SongSummaryDto ss) {
        this(ss.songName, ss.albumName, ss.artists);
    }

    @Override
    public String toString(){
        return "SongName=" + songName + ", AlbumName=" + albumName + ", Artists=" + artists;
    }
}
