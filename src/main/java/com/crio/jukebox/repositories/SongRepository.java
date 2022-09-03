package com.crio.jukebox.repositories;

import java.util.HashMap;
import java.util.Map;
import com.crio.jukebox.entities.Song;

public class SongRepository implements ISongRepository{
    private Map<String, Song> songMap;
    private Integer autoIncrement = 0;
    
    public SongRepository() {
        songMap = new HashMap<String,Song>();
    }
    
    public SongRepository(Map<String, Song> songMap) {
        this.songMap = songMap;
    }
    
    @Override
    public Song save(Song entity) {
        if (entity.getId() == null) {
            autoIncrement++;
            Song sg = new Song(Integer.toString(autoIncrement), entity.getSongName(), entity.getFeaturedArtists(),
                    entity.getMainArtist(), entity.getAlbum());
            songMap.put(sg.getId(), sg);

            //System.out.println(sg.getId() + " " + sg.getSongName());
            return sg;

        }
        songMap.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public boolean isValidSong(String songId) {
        // for (Map.Entry<String, Song> entry : songMap.entrySet()) {
        //     if (entry.getValue().getId() == songId) {
        //         return true;
        //     }
        // }
        // return false;
        if (songMap.containsKey(songId) == false){
            System.out.println("not pre");
        }
        return songMap.containsKey(songId);
    }
    @Override
    public Song getSong(String songId) {
        return songMap.get(songId);
    }
    
}
