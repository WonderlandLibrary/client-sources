package me.finz0.osiris.event.events;

import me.finz0.osiris.event.OsirisEvent;
public class PlayerLeaveEvent extends OsirisEvent {

    private final String name;

    public PlayerLeaveEvent(String n){
        super();
        name = n;
    }

    public String getName(){
        return name;
    }


}
