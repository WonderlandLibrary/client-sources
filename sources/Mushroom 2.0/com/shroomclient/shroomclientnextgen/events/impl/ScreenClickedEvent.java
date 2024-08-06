package com.shroomclient.shroomclientnextgen.events.impl;

import com.shroomclient.shroomclientnextgen.events.Event;

public class ScreenClickedEvent extends Event {

    public double mouseX;
    public double mouseY;
    public int button;
    public boolean down;

    public ScreenClickedEvent(
        double mouseX,
        double mouseY,
        int button,
        boolean down
    ) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.button = button;
        this.down = down;
    }
}
