package com.crio.jukebox.entities;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Playlist Test")
public class PlaylistTest {
    
    @Test
    @DisplayName("Return true if song is in playlist")
    public void checkIfSongExists_shouldReturnTrue() {
        String id = "1";
        String playlistName = "OneDirection";
        List<String> songs = new ArrayList<String>();
        songs.add("1");
        songs.add("2");
        songs.add("3");
        String userId = "1";
        String currentSongId = null;
        Playlist pl = new Playlist(id, playlistName, songs, userId, currentSongId);

        Assertions.assertTrue(pl.songInPlaylist("2"));
    }

    @Test
    @DisplayName("Return false if song is not in playlist")
    public void checkIfSongExists_shouldReturnFalse() {
        String id = "1";
        String playlistName = "OneDirection";
        List<String> songs = new ArrayList<String>();
        songs.add("1");
        songs.add("2");
        songs.add("3");
        String userId = "1";
        String currentSongId = null;
        Playlist pl = new Playlist(id, playlistName, songs, userId, currentSongId);

        Assertions.assertFalse(pl.songInPlaylist("4"));
    }
}
