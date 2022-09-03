package com.crio.jukebox.services;

import com.crio.jukebox.entities.Song;
import com.crio.jukebox.repositories.ISongRepository;

public class SongService implements ISongService {
    
    private ISongRepository songRepository;

    public SongService(ISongRepository songRepository){
        this.songRepository = songRepository;
    }

    @Override
    public void loadSongs(Song song) {
        songRepository.save(song);
    }
    
}
