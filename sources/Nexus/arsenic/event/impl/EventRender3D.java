package arsenic.event.impl;

import arsenic.event.types.Event;

public class EventRender3D implements Event {
    private float ticks;

    public EventRender3D(float ticks) {
        this.ticks = ticks;
    }

    public float getTicks() {
        return ticks;
    }

    public void setTicks(float ticks) {
        this.ticks = ticks;
    }
}
