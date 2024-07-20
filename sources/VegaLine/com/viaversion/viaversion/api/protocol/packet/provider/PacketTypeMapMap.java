/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.protocol.packet.provider;

import com.viaversion.viaversion.api.protocol.packet.PacketType;
import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypeMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import java.util.Collection;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

final class PacketTypeMapMap<P extends PacketType>
implements PacketTypeMap<P> {
    private final Map<String, P> packetsByName;
    private final Int2ObjectMap<P> packetsById;

    PacketTypeMapMap(Map<String, P> packetsByName, Int2ObjectMap<P> packetsById) {
        this.packetsByName = packetsByName;
        this.packetsById = packetsById;
    }

    @Override
    public @Nullable P typeByName(String packetTypeName) {
        return (P)((PacketType)this.packetsByName.get(packetTypeName));
    }

    @Override
    public @Nullable P typeById(int packetTypeId) {
        return (P)((PacketType)this.packetsById.get(packetTypeId));
    }

    @Override
    public Collection<P> types() {
        return this.packetsById.values();
    }
}

