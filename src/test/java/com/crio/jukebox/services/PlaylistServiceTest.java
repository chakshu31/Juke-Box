package com.crio.jukebox.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import com.crio.jukebox.dtos.PlaylistSummaryDto;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.exceptions.EmptyPlaylistException;
import com.crio.jukebox.exceptions.PlaylistNotFoundException;
import com.crio.jukebox.exceptions.SongNotFoundException;
import com.crio.jukebox.repositories.ISongRepository;
import com.crio.jukebox.repositories.IUserRepositiory;
import com.crio.jukebox.repositories.PlaylistRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PlaylistServiceTest {
    
    @Mock
    private PlaylistRepository playlistRepositoryMock;

    @Mock
    private IUserRepositiory userRepositoryMock;

    @Mock
    private ISongRepository songRepositoryMock;

    @Mock
    private IUserService userServiceMock;

    @InjectMocks
    private PlaylistService playlistService;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    @DisplayName("Should return song not found Exception")

    public void createPlaylist_shouldThrowException() {

        //when(songRepositoryMock.isValidSong(anyString())).thenReturn(false);
        List<String> songs = new ArrayList<>();
        songs.add("1");
        songs.add("2");
        Assertions.assertThrows(SongNotFoundException.class,
               () -> playlistService.createPlaylist("OneDirection", songs, "1"));
                
    }
    
    @Test
    @DisplayName("Should create a playlist")

    public void createPlaylist_shouldCreateSuccessfully() {
        String playlistName = "One Direction";
        List<String> songs = new ArrayList<>();
        songs.add("1");
        songs.add("2");
        String userId = "1";
        List<String> playlistIds = new ArrayList<>();
        playlistIds.add("1");
        playlistIds.add("2");
        User user = new User("1", "Name", playlistIds, null);

        Playlist expectedPlaylist = new Playlist("1",playlistName,songs, userId,null);
        when(songRepositoryMock.isValidSong(anyString())).thenReturn(true);
        when(playlistRepositoryMock.save(any(Playlist.class))).thenReturn(expectedPlaylist);
        when(userRepositoryMock.getUser(anyString())).thenReturn(user);
        when(userRepositoryMock.save(any(User.class))).thenReturn(any(User.class));
        Playlist actualPlaylist = playlistService.createPlaylist(playlistName, songs, userId);

        Assertions.assertEquals(expectedPlaylist, actualPlaylist);
    }

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    @DisplayName("Should return playlist not found Exception")

    public void deletePlaylist_shouldthrowException() {

        when(userRepositoryMock.playlistPresent(anyString(), anyString())).thenReturn(false);
        Assertions.assertThrows(PlaylistNotFoundException.class,
                () -> playlistService.deletePlaylist("1", "2"));
    }
    
    @Test
    @DisplayName("Should delete a playlist")

    public void deletePlaylist_shouldDeleteSuccessfully() {

        String expectedresponse = "Delete Successful";
        List<String> playlistIds = new ArrayList<>();
        playlistIds.add("1");
        playlistIds.add("2");

        List<String> expectedplaylistIds = new ArrayList<>();
        expectedplaylistIds.add("1");

        User initialuser = new User("1", "Name", playlistIds, null);
        User expecteduser = new User("1", "Name", expectedplaylistIds, null);

        when(userRepositoryMock.playlistPresent(anyString(), anyString())).thenReturn(true);
        when(userRepositoryMock.getUser(anyString())).thenReturn(initialuser);
        when(userRepositoryMock.save(any(User.class))).thenReturn(expecteduser);

        String actualresponse = playlistService.deletePlaylist("1", "2");
        Assertions.assertEquals(expectedresponse, actualresponse);
    }
    // should return string message
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    @DisplayName("ADD SONG - Should throw song not found exception")

    public void addSong_shouldthrowException() {

        List<String> songs = new ArrayList<>();
        songs.add("1");
        songs.add("2");

        Playlist initialPlaylist = new Playlist("1", "OneDirection", new ArrayList<>(), "1", null);
        //Playlist expectedPlaylist = new Playlist("1", "OneDirection", songs, "1", null);

        //User initialUser = new User("1", "Name1", new ArrayList<>(), null);
        //User expectedUser = new User("1", "Name1", songs, null);

        // when(userRepositoryMock.getUser(anyString())).thenReturn(initialUser);
        when(playlistRepositoryMock.getPlaylist(anyString())).thenReturn(initialPlaylist);
        when(songRepositoryMock.isValidSong(anyString())).thenReturn(false);


        Assertions.assertThrows(SongNotFoundException.class,
                () -> playlistService.addSong("1", "2", songs));
    }
    
    @Test
    @DisplayName("ADD SONG - Should return PlaylistDto")

    public void addSong_shouldAddSuccessfully() {

        List<String> songs = new ArrayList<>();
        songs.add("1");
        songs.add("2");

        List<String> addsongs = new ArrayList<>();
        addsongs.add("1");
        addsongs.add("4");

        List<String> expectedsongs = new ArrayList<>();
        expectedsongs.add("1");
        expectedsongs.add("2");
        //expectedsongs.add("3");
        expectedsongs.add("4");

        Playlist initialPlaylist = new Playlist("1", "OneDirection", songs, "1", null);
        Playlist expectedPlaylist = new Playlist("1", "OneDirection", expectedsongs, "1", null);

        //User initialUser = new User("1", "Name1", songs, null);
        
        when(playlistRepositoryMock.getPlaylist(anyString())).thenReturn(initialPlaylist);
        when(songRepositoryMock.isValidSong(anyString())).thenReturn(true);
        when(playlistRepositoryMock.findIfSongInPlaylist(anyString(), anyString()))
                .thenReturn(false);
        when(playlistRepositoryMock.save(any(Playlist.class))).thenReturn(expectedPlaylist);


        
        PlaylistSummaryDto expectedpls = new PlaylistSummaryDto("1", "OneDirection", expectedsongs);

        PlaylistSummaryDto actualpls = playlistService.addSong("1", "1", addsongs);

        Assertions.assertEquals(expectedpls,actualpls);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    @DisplayName("DELETE SONG - Should throw song not found exception if song not in pool")

    public void deleteSong_shouldthrowException1() {

        List<String> playlistIds = new ArrayList<>();
        playlistIds.add("1");
        playlistIds.add("2");

        List<String>  songs = new ArrayList<>();
        songs.add("1");
        songs.add("2");

        List<String> deleteids = new ArrayList<>();
        deleteids.add("5");

        //User user = new User("1", "Name", playlistIds, null);
        Playlist playlist = new Playlist("1", "OneDirection", songs, "1", null);

        //when(userRepositoryMock.getUser(anyString())).thenReturn(user);
        when(playlistRepositoryMock.getPlaylist(anyString())).thenReturn(playlist);
        when(songRepositoryMock.isValidSong(anyString())).thenReturn(false);

        Assertions.assertThrows(SongNotFoundException.class,
                () -> playlistService.deleteSong("1", "2", deleteids));
    }

    @Test
    @DisplayName("DELETE SONG - Should throw song not found exception if song not in playlist")

    public void deleteSong_shouldthrowException2() {

        List<String> playlistIds = new ArrayList<>();
        playlistIds.add("1");
        playlistIds.add("2");

        List<String> songs = new ArrayList<>();
        songs.add("1");
        songs.add("2");

        List<String> deleteids = new ArrayList<>();
        deleteids.add("5");

        //User user = new User("1", "Name", playlistIds, null);
        Playlist playlist = new Playlist("1", "OneDirection", songs, "1", null);

        //when(userRepositoryMock.getUser(anyString())).thenReturn(user);
        when(playlistRepositoryMock.getPlaylist(anyString())).thenReturn(playlist);
        when(songRepositoryMock.isValidSong(anyString())).thenReturn(true);
        when(playlistRepositoryMock.findIfSongInPlaylist(anyString(), anyString()))
                .thenReturn(false);


        Assertions.assertThrows(SongNotFoundException.class,
                () -> playlistService.deleteSong("1", "2", deleteids));
    }

    @Test
    @DisplayName("DELETE SONG - Should return PlaylistDto")

    public void deleteSong_shouldDeleteSuccessfully() {

        List<String> songs = new ArrayList<>();
        songs.add("1");
        songs.add("2");
        songs.add("3");

        List<String> deleteids = new ArrayList<>();
        deleteids.add("2");
        deleteids.add("1");

        List<String> songsFinally = new ArrayList<>();
        songsFinally.add("3");

        Playlist initialPlaylist = new Playlist("1", "OneDirection", songs, "1", null);
        Playlist expectedPlaylist = new Playlist("1", "OneDirection", songsFinally, "1", null);

        //User initialUser = new User("1", "Name1", songs, null);
        //User expectedUser = new User("1", "Name1", songsFinally, null);

        //when(userRepositoryMock.getUser(anyString())).thenReturn(initialUser);
        when(playlistRepositoryMock.getPlaylist(anyString())).thenReturn(initialPlaylist);
        when(songRepositoryMock.isValidSong(anyString())).thenReturn(true);
        when(playlistRepositoryMock.findIfSongInPlaylist(anyString(), anyString()))
                .thenReturn(true);
        when(playlistRepositoryMock.save(any(Playlist.class))).thenReturn(expectedPlaylist);
        //when(userRepositoryMock.save(any(User.class))).thenReturn(expectedUser);


        PlaylistSummaryDto expectedpls = new PlaylistSummaryDto("1", "OneDirection", songsFinally);

        PlaylistSummaryDto actualpls = playlistService.deleteSong("1", "1", deleteids);

        Assertions.assertEquals(expectedpls,actualpls);
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    @DisplayName("PLAY PLAYLIST - Should throw exception if playlist is empty")

    public void playPlaylist_shouldthrowException() {

        List<String> playlistIds = new ArrayList<>();
        playlistIds.add("1");
        List<String> songs = new ArrayList<>();

        User user = new User("1", "Name1", playlistIds, null);
        Playlist playlist = new Playlist("1", "OneDirection", songs, "1", null);

        when(userRepositoryMock.getUser(anyString())).thenReturn(user);
        when(playlistRepositoryMock.getPlaylist(anyString())).thenReturn(playlist);

        Assertions.assertThrows(EmptyPlaylistException.class,
                () -> playlistService.playPlaylist("1", "1"));
    }
    
    @Test
    @DisplayName("PLAY PLAYLIST - Should return first Song")

    public void playPlaylist_shouldReturnSong() {

        List<String> playlistIds = new ArrayList<>();
        playlistIds.add("1");
        List<String> songs = new ArrayList<>();
        songs.add("1");
        songs.add("2");

        User user = new User("1", "Name1", playlistIds, null);
        Playlist playlist = new Playlist("1", "OneDirection", songs, "1", null);

        List<String>featuredArtist = new ArrayList<>();
        featuredArtist.add("artist1");
        featuredArtist.add("artist2");
        

        Song song = new Song("1", "SongName", featuredArtist,"mainArtist", "Album1");
        
        when(playlistRepositoryMock.getPlaylist(anyString())).thenReturn(playlist);
        when(userRepositoryMock.getUser(anyString())).thenReturn(user);
        
        when(playlistRepositoryMock.save(any(Playlist.class))).thenReturn(playlist);
        when(userRepositoryMock.save(any(User.class))).thenReturn(user);
        when(songRepositoryMock.getSong(anyString())).thenReturn(song);

        Song actualSong = playlistService.playPlaylist("1", "1");
        Assertions.assertEquals(song, actualSong);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    @DisplayName("PLAY PREFERRED SONG - Should throw exception if song not found")

    public void playPreferredSong_shouldthrowException() {

        List<String> playlistIds = new ArrayList<>();
        playlistIds.add("1");
        List<String> songs = new ArrayList<>();
        songs.add("1");
        songs.add("2");

        Playlist oldPlaylist = new Playlist("1", "OneDirection", songs, "1", "1");
        //Playlist updatedPlaylist = new Playlist("1", "OneDirection", songs, "1", "2");
        //Song song = new Song("1", "SongName", "Artist1", "Album1");
        User user = new User("1", "Name1", playlistIds, "1");

        //when(userRepositoryMock.getUser(anyString())).thenReturn(user);
        when(userRepositoryMock.getUser(anyString())).thenReturn(user);
        when(playlistRepositoryMock.getPlaylist(anyString())).thenReturn(oldPlaylist);
        //when(playlistRepositoryMock.save(any(Playlist.class))).thenReturn(updatedPlaylist);
        when(playlistRepositoryMock.findIfSongInPlaylist(anyString(), anyString()))
                .thenReturn(false);

        Assertions.assertThrows(SongNotFoundException.class,
                () -> playlistService.preferredSong("1", "3"));

    }

    @Test
    @DisplayName("PLAY PREFERRED SONG")

    public void playPreferredSong_shouldPlaySong() {

        List<String> playlistIds = new ArrayList<>();
        playlistIds.add("1");
        List<String> songs = new ArrayList<>();
        songs.add("1");
        songs.add("2");

        Playlist oldPlaylist = new Playlist("1", "OneDirection", songs, "1", "1");
        Playlist updatedPlaylist = new Playlist("1", "OneDirection", songs, "1", "2");

        List<String> featuredArtist = new ArrayList<>();
        featuredArtist.add("artist1");
        featuredArtist.add("artist2");

        Song song = new Song("2", "SongName", featuredArtist, "mainArtist", "Album1");
        User user = new User("1", "Name1", playlistIds, "1");

        when(userRepositoryMock.getUser(anyString())).thenReturn(user);
        when(playlistRepositoryMock.getPlaylist(anyString())).thenReturn(oldPlaylist);
        when(playlistRepositoryMock.save(any(Playlist.class))).thenReturn(updatedPlaylist);
        when(playlistRepositoryMock.findIfSongInPlaylist(anyString(), anyString()))
                .thenReturn(true);
        when(songRepositoryMock.getSong(anyString())).thenReturn(song);

        Song actualSong = playlistService.preferredSong("1", "2");
        Assertions.assertEquals(song, actualSong);

    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    @DisplayName("PLAY NEXT SONG")

    public void playNextSong() {
        List<String> playlistIds = new ArrayList<>();
        playlistIds.add("1");
        List<String> songs = new ArrayList<>();
        songs.add("1");
        songs.add("2");
        songs.add("3");
        songs.add("4");

        Playlist oldPlaylist = new Playlist("2", "OneDirection", songs, "1", "2");
        Playlist updatedPlaylist = new Playlist("1", "OneDirection", songs, "1", "3");

        List<String> featuredArtist = new ArrayList<>();
        featuredArtist.add("artist1");
        featuredArtist.add("artist2");

        Song nextSong = new Song("3", "SongName", featuredArtist, "mainArtist", "Album1");
        User user = new User("1", "Name1", playlistIds, "1");

        when(userRepositoryMock.getUser(anyString())).thenReturn(user);
        when(playlistRepositoryMock.getPlaylist(anyString())).thenReturn(oldPlaylist);
        when(playlistRepositoryMock.save(any(Playlist.class))).thenReturn(updatedPlaylist);
        when(songRepositoryMock.getSong(anyString())).thenReturn(nextSong);

        Song actualSong = playlistService.nextSong("1");
        Assertions.assertEquals(nextSong, actualSong);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    @DisplayName("PLAY PREVIOUS SONG")

    public void playpreviousSong() {
        List<String> playlistIds = new ArrayList<>();
        playlistIds.add("1");
        List<String> songs = new ArrayList<>();
        songs.add("1");
        songs.add("2");
        songs.add("3");
        songs.add("4");

        Playlist oldPlaylist = new Playlist("2", "OneDirection", songs, "1", "2");
        Playlist updatedPlaylist = new Playlist("1", "OneDirection", songs, "1", "1");

        List<String> featuredArtist = new ArrayList<>();
        featuredArtist.add("artist1");
        featuredArtist.add("artist2");

        Song previousSong = new Song("3", "SongName", featuredArtist, "mainArtist", "Album1");
        User user = new User("1", "Name1", playlistIds, "1");

        when(userRepositoryMock.getUser(anyString())).thenReturn(user);
        when(playlistRepositoryMock.getPlaylist(anyString())).thenReturn(oldPlaylist);
        when(playlistRepositoryMock.save(any(Playlist.class))).thenReturn(updatedPlaylist);
        when(songRepositoryMock.getSong(anyString())).thenReturn(previousSong);

        Song actualSong = playlistService.previousSong("1");
        Assertions.assertEquals(previousSong, actualSong);

    }

}
