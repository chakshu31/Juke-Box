package com.crio.jukebox.services;

import java.util.List;
import com.crio.jukebox.dtos.PlaylistSummaryDto;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.exceptions.EmptyPlaylistException;
import com.crio.jukebox.exceptions.PlaylistNotFoundException;
import com.crio.jukebox.exceptions.SongNotFoundException;

public interface IPlaylistService{
    public Playlist createPlaylist(String playlistName, List<String> songs, String userId) throws SongNotFoundException;

    public String deletePlaylist(String userId, String playlistId) throws PlaylistNotFoundException;

    public PlaylistSummaryDto addSong(String userId, String playlistName, List<String> songIds) throws SongNotFoundException;

    public PlaylistSummaryDto deleteSong(String userId, String playlistName, 
            List<String> deleteIds) throws SongNotFoundException;
            
    public Song playPlaylist(String userId, String playlistId) throws EmptyPlaylistException;

    public Song previousSong(String userId);

    public Song nextSong(String userId);

    public Song preferredSong(String userId, String songId) throws SongNotFoundException;
}
