/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.protocol.packet;

import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.State;

public interface PacketType {
    public int getId();

    public String getName();

    public Direction direction();

    default public State state() {
        return State.PLAY;
    }
}

