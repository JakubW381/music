package org.example.music;

import org.junit.Test;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.*;

public class PlaylistTest {

    @Test
    public void testPlaylist(){
        Playlist playlist = new Playlist();
        assertTrue(playlist.isEmpty());
    }
    @Test
    public void testPlaylistAdd(){
        Playlist playlist = new Playlist();
        playlist.add(new Song("Modern Talking","Cheri Cheri Lady",197));
        assertEquals(1,playlist.size());
    }
    @Test
    public void testPlaylistAdded(){
        Playlist playlist = new Playlist();
        playlist.add(new Song("Modern Talking","Cheri Cheri Lady",197));
        assertEquals(
                new Song("Modern Talking","Cheri Cheri Lady",197),
                playlist.getFirst());
    }
    @Test
    public void testCheckSong2(){
        Playlist playlist = new Playlist();
        Song song = new Song("J.Hendrix","Purple Haze",30);
        playlist.add(song);
        Song song2 = new Song("J.Hendrix","Purple Haze",30);
        playlist.add(song2);
        assertEquals(song2,playlist.get(0));
    }
    @Test
    public void testPlaylistSongByIndex(){
        Song song1 = new Song("Heavy Young Heathens", "Being Evil Has a Price", 203);
        Song song2 = new Song("The Forest Rangers", "John The Revelator", 334);
        Song song3 = new Song("Lee DeWyze", "Blackbird Song", 254);
        Playlist playlist = new Playlist();
        playlist.add(song1);
        playlist.add(song2);
        playlist.add(song3);
        assertEquals(song2,playlist.atSeconds(400));
    }
    @Test
    public void testIndexOutOfBounds(){
        Song song1 = new Song("Heavy Young Heathens", "Being Evil Has a Price", 203);
        Song song2 = new Song("The Forest Rangers", "John The Revelator", 334);
        Song song3 = new Song("Lee DeWyze", "Blackbird Song", 254);
        Playlist playlist = new Playlist();
        playlist.add(song1);
        playlist.add(song2);
        playlist.add(song3);

        IndexOutOfBoundsException exception = assertThrows(IndexOutOfBoundsException.class,() -> playlist.atSeconds(-10));
        IndexOutOfBoundsException exception2 = assertThrows(IndexOutOfBoundsException.class,() -> playlist.atSeconds(1000));
        assertEquals("-10 equal 0 or below",exception.getMessage());
        assertEquals("Seconds too high for Playlist indexes: 1000",exception2.getMessage());
    }
}
