package org.example.music;

import java.awt.*;
import java.util.ArrayList;

public class Playlist extends ArrayList<Song> {

    public Song atSeconds(int seconds){
        if(seconds <= 0){
            throw new IndexOutOfBoundsException(seconds+" equal 0 or below");
        }
        int index = 0;
        int sum = 0;
        for(Song song:this){
            sum += song.duration();
            if(sum>= seconds){
                return song;
            }
        }
        throw new IndexOutOfBoundsException("Seconds too high for Playlist indexes: "+seconds);
    }
}
