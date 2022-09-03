package com.crio.jukebox.repositories;

import com.crio.jukebox.entities.Playlist;

public interface IPlaylistRepository extends CRUDRepository<Playlist,String>{
    public boolean findIfSongInPlaylist(String playlistId, String songId);

    public Playlist getPlaylist(String playlistId);

    public Playlist save(Playlist playlist);
}
