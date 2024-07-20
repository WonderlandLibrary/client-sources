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

    PacketTypeMapping(@Nullable PacketType mappedPacketType, @Nullable PacketHandler handler) {
        this.mappedPacketType = mappedPacketType;
        this.handler = handler;
    }

    @Override
    public void applyType(PacketWrapper wrapper) {
        if (this.mappedPacketType != null) {
            wrapper.setPacketType(this.mappedPacketType);
        }
    }

    @Override
    public @Nullable PacketHandler handler() {
        return this.handler;
    }
}

