package com.crio.jukebox.repositories;

import com.crio.jukebox.entities.Song;

public interface ISongRepository extends CRUDRepository<Song, String>{
    public boolean isValidSong(String songId);

    public Song getSong(String songId);
}
