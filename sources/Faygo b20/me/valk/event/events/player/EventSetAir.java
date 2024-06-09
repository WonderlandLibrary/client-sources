package me.valk.event.events.player;

import me.valk.event.Event;

/**
 * Created by Zeb on 4/24/2016.
 */
public class EventSetAir extends Event {

    private int air;

    public EventSetAir(int air){
        this.air = air;
    }

    public int getAir(){
        return air;
    }
}
