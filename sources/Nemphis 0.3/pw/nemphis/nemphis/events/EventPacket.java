/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.nemphis.events;

import com.darkmagician6.eventapi.events.Event;
import net.minecraft.network.Packet;

public class EventPacket
implements Event {
    private Packet packet = null;
    private boolean cancel;

    public EventPacket() {
    }

    public EventPacket(Packet p) {
        this.packet = p;
    }

    public Packet getPacket() {
        return this.packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }

    public boolean isCancelled() {
        return this.cancel;
    }

    public void setCancelled(boolean b) {
        this.cancel = b;
    }
}

