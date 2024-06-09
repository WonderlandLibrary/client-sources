package me.teus.eclipse.events.player;

import xyz.lemon.event.bus.Event;

public class EventChat extends Event {

    public String message;

    public EventChat(String message){
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
