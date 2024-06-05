package net.shoreline.client.impl.event.network;

import net.shoreline.client.api.event.Cancelable;
import net.shoreline.client.api.event.Event;

@Cancelable
public class ReachEvent extends Event {
    private float reach;

    public float getReach() {
        return reach;
    }

    public void setReach(float reach) {
        this.reach = reach;
    }
}
