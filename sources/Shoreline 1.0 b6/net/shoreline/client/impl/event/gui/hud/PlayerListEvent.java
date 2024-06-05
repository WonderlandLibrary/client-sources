package net.shoreline.client.impl.event.gui.hud;

import net.shoreline.client.api.event.Cancelable;
import net.shoreline.client.api.event.Event;

@Cancelable
public class PlayerListEvent extends Event {

    private int size;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
