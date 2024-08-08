package me.xatzdevelopments.xatz.client.Music;

import me.xatzdevelopments.xatz.client.main.Xatz;

public class Song {

    String name;
    String location;

    public Song(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public void play(){
        Xatz.instance.soundHandler.playSound(location);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
