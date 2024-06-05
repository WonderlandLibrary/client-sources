/*
 * Decompiled with CFR 0_132.
 */
package gq.vapu.czfclient.API.Events.World;

import gq.vapu.czfclient.API.Event;
import net.minecraft.network.Packet;

public class EventPacketSend extends Event {
    private static Packet packet;

    public EventPacketSend(Packet packet) {
        EventPacketSend.packet = packet;
    }

    public static Packet getPacket() {
        return packet;
    }

    public void setPacket(Packet packet) {
        EventPacketSend.packet = packet;
    }
}
