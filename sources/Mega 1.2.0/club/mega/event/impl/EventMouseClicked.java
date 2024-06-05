package club.mega.event.impl;

import club.mega.event.Event;

public class EventMouseClicked extends Event {

    private final int mouseX, mouseY, mouseButton;

    public EventMouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.mouseButton = mouseButton;
    }

    public final int getMouseX() {
        return mouseX;
    }

    public final int getMouseY() {
        return mouseY;
    }

    public final int getMouseButton() {
        return mouseButton;
    }

}
