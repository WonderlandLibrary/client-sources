/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.nemphis.events;

import net.minecraft.network.Packet;
import pw.vertexcode.util.event.Event;

public class EventPacketSend
implements Event {
    Packet packet;

    public EventPacketSend(Packet p_147297_1_) {
        this.packet = p_147297_1_;
    }

    public Packet getPacket() {
        return this.packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}

