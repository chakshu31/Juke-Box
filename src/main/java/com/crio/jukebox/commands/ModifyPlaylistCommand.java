package com.crio.jukebox.commands;

import java.util.ArrayList;
import java.util.List;
import com.crio.jukebox.dtos.PlaylistSummaryDto;
import com.crio.jukebox.exceptions.SongNotFoundException;
import com.crio.jukebox.services.IPlaylistService;

public class ModifyPlaylistCommand implements ICommand{
    private IPlaylistService playlistService;

    public ModifyPlaylistCommand(IPlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Override
    public void execute(List<String> tokens) {
        String userId = tokens.get(2);
        String playlistId = tokens.get(3);
        List<String> listSongs = new ArrayList<String>();
        for (int i = 4; i < tokens.size(); i++) {
            listSongs.add(tokens.get(i));
        }

        if (tokens.get(1).equals("ADD-SONG")) {
            try{
                PlaylistSummaryDto plDto = playlistService.addSong(userId, playlistId, listSongs);
                System.out.println("Playlist ID - " + plDto.getPlaylistId());
                System.out.println("Playlist Name - " + plDto.getPlaylistName());
                System.out.println("Song IDs - " + plDto.getSongIds());
            } catch (SongNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
        else {
            try {
                PlaylistSummaryDto plDto = playlistService.deleteSong(userId, playlistId, 
                        listSongs);
                System.out.println("Playlist ID - " + plDto.getPlaylistId());
                System.out.println("Playlist Name - " + plDto.getPlaylistName());
                System.out.println("Song IDs - " + plDto.getSongIds());
            } catch (SongNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
