package com.crio.jukebox.services;

import java.util.List;
import com.crio.jukebox.dtos.PlaylistSummaryDto;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.exceptions.EmptyPlaylistException;
import com.crio.jukebox.exceptions.PlaylistNotFoundException;
import com.crio.jukebox.exceptions.SongNotFoundException;
import com.crio.jukebox.repositories.IPlaylistRepository;
import com.crio.jukebox.repositories.ISongRepository;
import com.crio.jukebox.repositories.IUserRepositiory;

public class PlaylistService implements IPlaylistService {

    private IUserRepositiory userRepositiory;
    private IPlaylistRepository playlistRepository;
    private ISongRepository songRepository;

    public PlaylistService(IUserRepositiory userRepositiory, IPlaylistRepository playlistRepository,
            ISongRepository songRepository) {
        this.userRepositiory = userRepositiory;
        this.playlistRepository = playlistRepository;
        this.songRepository = songRepository;
    }

    @Override
    public Playlist createPlaylist(String playlistName, List<String> songs, String userId) throws SongNotFoundException {
        for (String songId : songs) {
            if (songRepository.isValidSong(songId) == false) {
                throw new SongNotFoundException("Some Requested Songs Not Available. Please try again." + songId);
            }
        }
        Playlist newPlaylist = new Playlist(playlistName, userId, songs);
        newPlaylist = playlistRepository.save(newPlaylist);

        User user = userRepositiory.getUser(userId);
        List<String>playlistIds = user.getPlaylistIds();
        playlistIds.add(newPlaylist.getId());
        //System.out.println(newPlaylist);

        user = userRepositiory.save(new User(user.getId(), user.getName(), playlistIds, user.getActivePlaylist()));
        
        return newPlaylist;
    }

    @Override
    public String deletePlaylist(String userId, String playlistId) throws PlaylistNotFoundException {
        if (userRepositiory.playlistPresent(userId, playlistId) == false) {
            throw new PlaylistNotFoundException("Playlist Not Found");
        }
        User user = userRepositiory.getUser(userId);
        List<String> playlistIds = user.getPlaylistIds();
        playlistIds.remove(playlistId);
        //System.out.println("after delete playlist ids " + playlistIds);

        // updated playlistids in user repo 
        // should also delete playlist from playlist repo ??
        
        User updatedUser = new User(user.getId(), user.getName(), playlistIds, null);
        user = userRepositiory.save(updatedUser);
        return "Delete Successful" ;
    }

    @Override
    public PlaylistSummaryDto addSong(String userId, String playlistId, List<String> songIds) throws SongNotFoundException{

        Playlist playlist = playlistRepository.getPlaylist(playlistId);
        List<String> userSongIds = playlist.getSongs();
        //System.out.println(userSongIds + " add " + songIds);

        for (String songId : songIds) {
            if (songRepository.isValidSong(songId) == false) {
                throw new SongNotFoundException(
                        "Some Requested Songs Not Available.Please try again.");
            } else {
                if (playlistRepository.findIfSongInPlaylist(playlistId, songId) == false) {
                    userSongIds.add(songId);
                }
            }
        }
        playlist = playlistRepository.save(new Playlist(playlist.getId(), playlist.getplaylistName(),
                userSongIds, playlist.getUser(),null));
        return new PlaylistSummaryDto(playlistId,playlist.getplaylistName(),playlist.getSongs());
    }

    @Override
    public PlaylistSummaryDto deleteSong(String userId, String playlistId, 
            List<String> deleteIds) throws SongNotFoundException {
       
        Playlist playlist = playlistRepository.getPlaylist(playlistId);
        //User user = userRepositiory.getUser(userId);
        List<String> userSongIds = playlist.getSongs();

        //System.out.println(userSongIds + " delete " + deleteIds);

        for (String deleteId : deleteIds) {
            if (songRepository.isValidSong(deleteId) == false) {
                throw new SongNotFoundException("Some Requested Songs For Deletion are not present in playlist. Please try again.");
            } else {
                if (playlistRepository.findIfSongInPlaylist(playlistId, deleteId) == true) {
                    userSongIds.remove(deleteId);
                } else {
                    throw new SongNotFoundException(
                            "Some Requested Songs For Deletion are not present in playlist. Please try again.");
                }
            }
        }
    
        playlist = playlistRepository.save(new Playlist(playlist.getId(), playlist.getplaylistName(),
                userSongIds, playlist.getUser(),null));
        // user  = userRepositiory.save(new User(userId, user.getName(), user.getPlaylistIds(),null));
        return new PlaylistSummaryDto(playlistId, playlist.getplaylistName(), userSongIds);
    }

    @Override
    public Song playPlaylist(String userId, String playlistId) throws EmptyPlaylistException {

        
        User user = userRepositiory.getUser(userId);
        //System.out.println(user.getPlaylistIds());
        Playlist playlist = playlistRepository.getPlaylist(playlistId);
        //System.out.println(playlist.getSongs());

        List<String> userSongIds = playlist.getSongs();

        if (userSongIds.size() == 0) {
            throw new EmptyPlaylistException("Playlist is empty.");
        }

        String songId = userSongIds.get(0);

        playlistRepository.save(new Playlist(playlistId, playlist.getplaylistName(),
                playlist.getSongs(), playlist.getUser(), songId));
        userRepositiory.save(new User(userId, user.getName(), user.getPlaylistIds(), playlistId));

        return songRepository.getSong(songId);
    }

    @Override
    public Song previousSong(String userId) {

        User user = userRepositiory.getUser(userId);
        String activePlaylistId = user.getActivePlaylist();
        Playlist activePlaylist = playlistRepository.getPlaylist(activePlaylistId);
        String currentSongId = activePlaylist.getCurrentSongId();
        List<String> songs = activePlaylist.getSongs();

        int n = songs.size();
        for (int i = 0; i < n; i++) {
            if (songs.get(i).equals(currentSongId)) {
                String previousSongId = songs.get((i - 1 + n) % n);
                Playlist pl = new Playlist(activePlaylistId, activePlaylist.getplaylistName(),
                        activePlaylist.getSongs(), activePlaylist.getUser(), previousSongId);
                pl = playlistRepository.save(pl);
                return songRepository.getSong(previousSongId);
            }
        }

        return null;
    }

    @Override
    public Song nextSong(String userId) {
        User user = userRepositiory.getUser(userId);
        String activePlaylistId = user.getActivePlaylist();
        Playlist activePlaylist = playlistRepository.getPlaylist(activePlaylistId);
        String currentSongId = activePlaylist.getCurrentSongId();
        List<String> songs = activePlaylist.getSongs();

        // System.out.println(currentSongId);
        // System.out.println(songs);

        int n = songs.size();
        String nextSongId="";
        for (int i = 0; i < n; i++) {
            if (songs.get(i).equals(currentSongId)) {
                nextSongId = songs.get((i + 1 + n) % n);
                Playlist pl = new Playlist(activePlaylistId, activePlaylist.getplaylistName(),
                        activePlaylist.getSongs(), activePlaylist.getUser(), nextSongId);
                pl = playlistRepository.save(pl);
                return songRepository.getSong(nextSongId);
            }
        }

        return songRepository.getSong(nextSongId);
    }

    @Override
    public Song preferredSong(String userId, String songId) throws SongNotFoundException {

        User user = userRepositiory.getUser(userId);
        String activePlaylistId = user.getActivePlaylist();
        Playlist activePlaylist = playlistRepository.getPlaylist(activePlaylistId);

        if (playlistRepository.findIfSongInPlaylist(activePlaylistId, songId) == false) {
            throw new SongNotFoundException("Given song id is not a part of the active playlist");
        }
        Playlist pl = new Playlist(activePlaylistId,activePlaylist.getplaylistName(),
                activePlaylist.getSongs(), activePlaylist.getUser(), songId);
        pl = playlistRepository.save(pl);
        return songRepository.getSong(songId);
    }
    
}
