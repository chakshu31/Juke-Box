package com.crio.jukebox.commands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.services.ISongService;

public class LoadDataCommand implements ICommand{
    private ISongService songService;

    public LoadDataCommand(ISongService songService) {
        this.songService = songService;
    }

    @Override
    public void execute(List<String> tokens) {
        String line = "";
        String splitBy = ",";
        
        try{
            BufferedReader br = new BufferedReader(new FileReader(tokens.get(1)));
            while ((line = br.readLine()) != null) {
                String[] songElements = line.split(splitBy);
                String[] artists = songElements[4].split("#");

                String songName = songElements[0];
                String albumName = songElements[2];

                String artistName = songElements[3];
                List<String> featuredArtists = Arrays.asList(artists);


                Song song = new Song(null, songName, featuredArtists,artistName, albumName);
                songService.loadSongs(song);
            }
            System.out.println("Songs Loaded successfully");
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        
    }

    
    
    
}
