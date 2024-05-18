package pw.latematt.xiv.event.events;

import pw.latematt.xiv.event.Event;

/**
 * Created by TehNeon on 9/24/2015.
 */
public class Render3DEvent extends Event {

    private float partialTicks;

    public Render3DEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}
