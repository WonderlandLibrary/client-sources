/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.events.packet;

import net.minecraft.network.Packet;
import digital.rbq.events.Cancellable;
import digital.rbq.events.Event;

public final class ReceivePacketEvent
extends Cancellable
implements Event {
    private final Packet<?> packet;

    public ReceivePacketEvent(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return this.packet;
    }
}

