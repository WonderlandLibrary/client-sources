/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.base;

import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.State;

public enum ServerboundLoginPackets implements ServerboundPacketType
{
    HELLO,
    ENCRYPTION_KEY,
    CUSTOM_QUERY;


    @Override
    public final int getId() {
        return this.ordinal();
    }

    @Override
    public final String getName() {
        return this.name();
    }

    @Override
    public final State state() {
        return State.LOGIN;
    }
}

