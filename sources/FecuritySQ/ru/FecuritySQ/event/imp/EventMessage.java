package ru.FecuritySQ.event.imp;

import ru.FecuritySQ.event.Event;

public class EventMessage extends Event {

    public String msg;

    public EventMessage(String msg){
        this.msg = msg;
    }

}
