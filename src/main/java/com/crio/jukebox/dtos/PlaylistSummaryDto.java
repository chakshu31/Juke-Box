package com.crio.jukebox.dtos;

import java.util.List;

public class PlaylistSummaryDto {
    private String playlistId;
    private String playlistName;
    private List<String> songIds;
    
    public PlaylistSummaryDto(PlaylistSummaryDto pl) {
        this(pl.playlistId, pl.playlistName, pl.songIds);
    }

    public PlaylistSummaryDto(String playlistId, String playlistName, List<String> songIds) {
        this.playlistId = playlistId;
        this.playlistName = playlistName;
        this.songIds = songIds;
    }
    
    public String getPlaylistId() {
        return playlistId;
    }
    
    public String getPlaylistName() {
        return playlistName;
    }
    
    public List<String> getSongIds() {
        return songIds;
    }

    @Override
    public String toString() {
        return "PlaylistId=" + playlistId + ", PlaylistName=" + playlistName + ", SongIds="
                + songIds + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((playlistId == null) ? 0 : playlistId.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        
        if (getClass() != obj.getClass())
            return false;
        PlaylistSummaryDto other = (PlaylistSummaryDto) obj;
        if (playlistId == null) {
            if (other.playlistId != null)
                return false;
        } else if (!playlistId.equals(other.playlistId))
            return false;
        return true;

        
    }
}
