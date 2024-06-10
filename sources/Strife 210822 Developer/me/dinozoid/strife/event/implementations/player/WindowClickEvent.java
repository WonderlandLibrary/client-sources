package me.dinozoid.strife.event.implementations.player;

import me.dinozoid.strife.event.Event;

public class WindowClickEvent extends Event {

    private final int windowId, slotId, mouseButton, mode;

    public WindowClickEvent(int windowId, int slotId, int mouseButton, int mode) {
        this.windowId = windowId;
        this.slotId = slotId;
        this.mouseButton = mouseButton;
        this.mode = mode;
    }

    public int windowId() {
        return windowId;
    }

    public int slotId() {
        return slotId;
    }

    public int mouseButton() {
        return mouseButton;
    }

    public int mode() {
        return mode;
    }

}
