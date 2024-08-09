/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.protocol.packet.mapping;

import com.viaversion.viaversion.api.protocol.packet.PacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.mapping.PacketMapping;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import org.checkerframework.checker.nullness.qual.Nullable;

final class PacketTypeMapping
implements PacketMapping {
    private final PacketType mappedPacketType;
    private final PacketHandler handler;

    PacketTypeMapping(@Nullable PacketType packetType, @Nullable PacketHandler packetHandler) {
        this.mappedPacketType = packetType;
        this.handler = packetHandler;
    }

    @Override
    public void applyType(PacketWrapper packetWrapper) {
        if (this.mappedPacketType != null) {
            packetWrapper.setPacketType(this.mappedPacketType);
        }
    }

    @Override
    public @Nullable PacketHandler handler() {
        return this.handler;
    }
}

