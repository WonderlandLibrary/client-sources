package com.shroomclient.shroomclientnextgen.events.impl;

import com.shroomclient.shroomclientnextgen.events.Event;

public class StepEvent extends Event {

    public double height;

    public StepEvent(double height) {
        this.height = height;
    }
}
