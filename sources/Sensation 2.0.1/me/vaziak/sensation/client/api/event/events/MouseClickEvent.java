package me.vaziak.sensation.client.api.event.events;

/**
 * @author antja03
 */
public class MouseClickEvent {
    private int mouseButton;

    public MouseClickEvent(int mouseButton) {
        this.mouseButton = mouseButton;
    }

    public int getMouseButton() {
        return mouseButton;
    }
}
