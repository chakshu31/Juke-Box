package com.crio.jukebox.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.crio.jukebox.entities.Playlist;

public class PlaylistRepository implements IPlaylistRepository{
    private Map<String,Playlist> playlistMap;
    private Integer autoIncrement = 0;

    public PlaylistRepository() {
        playlistMap = new HashMap<String, Playlist>();
    }
    
    public PlaylistRepository(Map<String, Playlist> playlistMap) {
        this.playlistMap = playlistMap;
    }

    @Override
    public Playlist save(Playlist entity){
        if (entity.getId() == null) {
            autoIncrement++;
            Playlist pl = new Playlist(Integer.toString(autoIncrement), entity.getplaylistName(),
                    entity.getSongs(), entity.getUser(),null);
            playlistMap.put(pl.getId(), pl);
            return pl;
        }
        playlistMap.put(entity.getId(),entity);
        return entity;
    }

    @Override
    public boolean findIfSongInPlaylist(String playlistId, String songId) {
        Playlist pl = playlistMap.get(playlistId);
        List<String> allSongs = pl.getSongs();
        for (String id : allSongs) {
            if (id.equals(songId)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public Playlist getPlaylist(String playlistId){
        return playlistMap.get(playlistId);
    }
}
