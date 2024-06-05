/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.events.player;

import digital.rbq.events.Event;

public final class MoveEvent
implements Event {
    public double x;
    public double y;
    public double z;

    public MoveEvent(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}

