/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.utils.network;

import net.minecraft.network.packet.Packet;

public final class LongPacketClass {
    private Packet<?> packet;
    private long id;

    public LongPacketClass(Packet packet, Long id) {
        this.id = id;
        this.packet = packet;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }

    public long getId() {
        return id;
    }

    public Packet getPacket() {
        return packet;
    }

}
