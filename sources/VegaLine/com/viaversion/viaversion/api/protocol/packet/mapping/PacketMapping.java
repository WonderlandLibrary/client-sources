/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.protocol.packet.mapping;

import com.viaversion.viaversion.api.protocol.packet.PacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.mapping.PacketIdMapping;
import com.viaversion.viaversion.api.protocol.packet.mapping.PacketTypeMapping;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface PacketMapping {
    public void applyType(PacketWrapper var1);

    public @Nullable PacketHandler handler();

    public static PacketMapping of(int mappedPacketId, @Nullable PacketHandler handler) {
        return new PacketIdMapping(mappedPacketId, handler);
    }

    public static PacketMapping of(@Nullable PacketType mappedPacketType, @Nullable PacketHandler handler) {
        return new PacketTypeMapping(mappedPacketType, handler);
    }
}

