/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.protocol.packet.provider;

import com.viaversion.viaversion.api.protocol.packet.PacketType;
import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypeMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

final class PacketTypeArrayMap<P extends PacketType>
implements PacketTypeMap<P> {
    private final Map<String, P> packetsByName;
    private final P[] packets;

    PacketTypeArrayMap(Map<String, P> packetsByName, P[] packets) {
        this.packetsByName = packetsByName;
        this.packets = packets;
    }

    @Override
    public @Nullable P typeByName(String packetTypeName) {
        return (P)((PacketType)this.packetsByName.get(packetTypeName));
    }

    @Override
    public @Nullable P typeById(int packetTypeId) {
        return packetTypeId >= 0 && packetTypeId < this.packets.length ? (P)this.packets[packetTypeId] : null;
    }

    @Override
    public Collection<P> types() {
        return Arrays.asList(this.packets);
    }
}

