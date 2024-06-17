package org.example.music;

import org.example.pliki.DatabaseConnection;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.*;

public class SongTest {
    @BeforeAll
    static void openDatabase(){
        DatabaseConnection con = new DatabaseConnection();
        con.connect("songs.db","songs");
    }
    @Test
    public void testReadCsv() throws FileNotFoundException {
        Optional<Song> testSong = Song.Persistence.readCsv(5,"songs.csv");
        Song expectedSong = new Song("Queen","Bohemian Rhapsody",355);
        assertTrue(testSong.isPresent());
        assertEquals(expectedSong,testSong.get());
    }


    @Test
    public void readSingle() throws SQLException {
        Optional<Song> testSong = Song.Persistence.read(5);
        Song expectedSong = new Song("Queen","Bohemian Rhapsody",355);
        assertTrue(testSong.isPresent());
        assertEquals(expectedSong,testSong.get());
    }
    @Test
    public void readSingleWrongID() throws SQLException {
        Optional<Song> testSong = Song.Persistence.read(53);
        //Song expectedSong = new Song("Queen","Bohemian Rhapsody",355);
        assertFalse(testSong.isPresent());
    }

    @AfterAll
    static void closeDatabase(){
        DatabaseConnection.disconnect("songs");
    }
}
