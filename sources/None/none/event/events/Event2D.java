package none.event.events;

import none.event.Event;

public class Event2D extends Event {
    private float width, height;

    public void fire(float width, float height) {
        this.width = width;
        this.height = height;
        super.fire();
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
