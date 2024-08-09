package im.expensive.events;

import net.minecraftforge.eventbus.api.Event;

public class EventMouseTick extends Event {
    private final int button;
    private final double mouseX;
    private final double mouseY;

    public EventMouseTick(int button, double mouseX, double mouseY) {
        this.button = button;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    public int getButton() {
        return this.button;
    }

    public double getMouseX() {
        return this.mouseX;
    }

    public double getMouseY() {
        return this.mouseY;
    }
}
