/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.protocol.packet;

import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketType;

public interface ServerboundPacketType
extends PacketType {
    @Override
    default public Direction direction() {
        return Direction.SERVERBOUND;
    }
}

