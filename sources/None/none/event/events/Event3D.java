package none.event.events;

import none.event.Event;

public class Event3D extends Event {
    private float partialTicks;

    public void fire(float partialTicks) {
        this.partialTicks = partialTicks;
        super.fire();
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}
