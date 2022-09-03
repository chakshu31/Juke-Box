package com.crio.jukebox.commands;

import java.util.List;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.exceptions.EmptyPlaylistException;
import com.crio.jukebox.services.IPlaylistService;

public class PlayPlaylistCommand implements ICommand {
    private IPlaylistService playlistService;

    public PlayPlaylistCommand(IPlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Override
    public void execute(List<String> tokens) {

        String userId = tokens.get(1);
        String playlistId = tokens.get(2);
        try{
            Song song = playlistService.playPlaylist(userId, playlistId);
            System.out.println("Current Song Playing");
            System.out.println("Song - " + song.getSongName());
            System.out.println("Album - " + song.getAlbum());
            System.out.println("Artists - " + song.getFeaturedArtists());
        } catch (EmptyPlaylistException e) {
            System.out.println(e.getMessage());
        }
    }
    
}
