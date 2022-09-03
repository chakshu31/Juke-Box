package com.crio.jukebox.commands;

import java.util.List;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.exceptions.SongNotFoundException;
import com.crio.jukebox.services.IPlaylistService;

public class PlaySongCommand implements ICommand{
    private IPlaylistService playlistService;

    public PlaySongCommand(IPlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Override
    public void execute(List<String> tokens) {
        String userId = tokens.get(1);
        String option = tokens.get(2);
        if (option.equals("NEXT")) {
            Song song = playlistService.nextSong(userId);
            System.out.println("Current Song Playing");
            System.out.println("Song - " + song.getSongName());
            System.out.println("Album - " + song.getAlbum());
            System.out.println("Artists - " + song.getFeaturedArtists());
        }
        else if (option.equals("BACK")) {
            Song song = playlistService.previousSong(userId);
            System.out.println("Current Song Playing");
            System.out.println("Song - " + song.getSongName());
            System.out.println("Album - " + song.getAlbum());
            System.out.println("Artists - " + song.getFeaturedArtists());
        }
        else{
            try{
                Song song = playlistService.preferredSong(userId, tokens.get(2));
                System.out.println("Current Song Playing");
                System.out.println("Song - " + song.getSongName());
                System.out.println("Album - " + song.getAlbum());
                System.out.println("Artists - " + song.getFeaturedArtists());
            } catch (SongNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
