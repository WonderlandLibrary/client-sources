package club.mega.event.impl;

import club.mega.event.Event;

public class EventRenderPitch extends Event {

    private float pitch, prevPitch;

    public EventRenderPitch(final float pitch, final float prevPitch) {
        this.pitch = pitch;
        this.prevPitch = prevPitch;
    }

    public final float getPitch() {
        return pitch;
    }

    public final float getPrevPitch() {
        return prevPitch;
    }

    public final void setPitch(final float pitch) {
        this.pitch = pitch;
        this.prevPitch = pitch;
    }

}
