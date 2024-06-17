package org.example.music;

import org.example.pliki.DatabaseConnection;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public record Song(String artist, String title, int duration) {
    static public class Persistence{
        static DatabaseConnection connection;

        public static Optional<Song> read(int id) throws SQLException {
            PreparedStatement statement = DatabaseConnection.getCon().prepareStatement("SELECT artist, title, length FROM songs WHERE id = ?");
            statement.setInt(1,id);
            ResultSet res = statement.executeQuery();
            if(res.next()){
                String artist = statement.getResultSet().getString(1);
                String title = statement.getResultSet().getString(2);
                int length = statement.getResultSet().getInt(3);
                Optional<Song> song = Optional.of(new Song(artist, title, length));
            }
            return Optional.empty();
        }

        public static Optional<Song> readCsv(int id,String path) throws FileNotFoundException {
            try(BufferedReader br = new BufferedReader(new FileReader(path))) {
                String line = "";
                int idCsv = 0;
                for (int i = 0; i!= id;i++){
                    br.readLine();
                }
                line = br.readLine();
                String[] values = line.split(",");
                Song song1 = new Song(values[1], values[2], Integer.parseInt(values[3]));
                Optional<Song> song = Optional.of(song1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return Optional.empty();
        }
    }
}
