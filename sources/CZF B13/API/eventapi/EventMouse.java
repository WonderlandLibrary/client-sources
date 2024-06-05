package gq.vapu.czfclient.API.eventapi;

import gq.vapu.czfclient.API.Event;

public class EventMouse extends Event {
    private int buttonID;
    private boolean mouseDown;

    public void fire(int buttonID, boolean mouseDown) {
        this.buttonID = buttonID;
        super.fire();
    }

    public int getButtonID() {
        return this.buttonID;
    }

    public void setButtonID(int buttonID) {
        this.buttonID = buttonID;
    }

    public boolean isMouseDown() {
        return this.mouseDown;
    }

    public void setMouseDown(boolean mouseDown) {
        this.mouseDown = mouseDown;
    }

    public boolean isMotionEvent() {
        return this.buttonID == -1;
    }
}

