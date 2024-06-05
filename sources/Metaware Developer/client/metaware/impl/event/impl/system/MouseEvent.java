package client.metaware.impl.event.impl.system;

import client.metaware.impl.event.Event;
public class MouseEvent extends Event {
    private final int mouseButton;

    public MouseEvent(int mouseButton) {
        this.mouseButton = mouseButton;
    }

    public int getMouseButton() {
        return mouseButton;
    }
}
