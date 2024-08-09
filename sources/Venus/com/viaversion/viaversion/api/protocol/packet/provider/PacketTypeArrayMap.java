/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.protocol.packet.provider;

import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypeMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

final class PacketTypeArrayMap<P>
implements PacketTypeMap<P> {
    private final Map<String, P> packetsByName;
    private final P[] packets;

    PacketTypeArrayMap(Map<String, P> map, P[] PArray) {
        this.packetsByName = map;
        this.packets = PArray;
    }

    @Override
    public @Nullable P typeByName(String string) {
        return this.packetsByName.get(string);
    }

    @Override
    public @Nullable P typeById(int n) {
        return n >= 0 && n < this.packets.length ? (P)this.packets[n] : null;
    }

    @Override
    public Collection<P> types() {
        return Arrays.asList(this.packets);
    }
}

