package in.momin5.cookieclient.api.event.events;

import in.momin5.cookieclient.api.event.Event;

public class EventRender extends Event {
    private final float partialTicks;

    public EventRender(float partialTicks) {
        super();
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }
}