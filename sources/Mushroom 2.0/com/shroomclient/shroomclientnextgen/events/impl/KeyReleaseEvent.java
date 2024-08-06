package com.shroomclient.shroomclientnextgen.events.impl;

import com.shroomclient.shroomclientnextgen.events.Event;

public class KeyReleaseEvent extends Event {

    public int key;
    public int scanCode;

    public KeyReleaseEvent(int key, int scanCode) {
        this.key = key;
        this.scanCode = scanCode;
    }
}
