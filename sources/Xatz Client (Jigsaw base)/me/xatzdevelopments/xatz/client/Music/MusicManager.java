package me.xatzdevelopments.xatz.client.Music;

import java.util.ArrayList;

import me.xatzdevelopments.xatz.client.Music.Songs.*;

public class MusicManager {

    ArrayList<Song> songs = new ArrayList<>();


    public MusicManager(){
     //   songs.add(new StiletoStateOfMind());
        songs.add(new SilentPartnerSpaceWalk());
        songs.add(new TheseDays());
        songs.add(new Closer());
    }

    public Song getSongByName(String name){
        for(Song song : songs){
            if(song.getName().equalsIgnoreCase(name)){
                return song;
            }
        }
        return null;
    }

}
