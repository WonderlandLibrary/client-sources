package club.mega.event.impl;

import club.mega.event.Event;

public class EventDrawGuiScreen extends Event {

    private final int mouseX, mouseY;

    public EventDrawGuiScreen(final int mouseX, final int mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    public final int getMouseX() {
        return mouseX;
    }

    public final int getMouseY() {
        return mouseY;
    }

}
