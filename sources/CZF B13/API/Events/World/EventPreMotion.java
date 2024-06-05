/*
 * Decompiled with CFR 0_132 Helper by Lightcolour E-mail wyy-666@hotmail.com.
 */
package gq.vapu.czfclient.API.Events.World;

import gq.vapu.czfclient.API.eventapi.events.Cancellable;
import gq.vapu.czfclient.API.eventapi.events.Event;

public class EventPreMotion
        implements Event,
        Cancellable {
    public double y;
    public float yaw;
    public float pitch;
    public boolean onGround;
    public boolean cancel;

    public EventPreMotion(double y, float yaw, float pitch, boolean onGround) {
        this.y = y;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    @Override
    public boolean isCancelled() {
        return this.cancel;
    }

    @Override
    public void setCancelled(boolean state) {
        this.cancel = state;
    }
}

