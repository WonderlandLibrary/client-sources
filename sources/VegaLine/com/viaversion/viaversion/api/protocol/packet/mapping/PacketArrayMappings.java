/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.protocol.packet.mapping;

import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.packet.mapping.PacketMapping;
import com.viaversion.viaversion.api.protocol.packet.mapping.PacketMappings;
import java.util.Arrays;
import org.checkerframework.checker.nullness.qual.Nullable;

final class PacketArrayMappings
implements PacketMappings {
    private final PacketMapping[][] packets = new PacketMapping[State.values().length][];

    PacketArrayMappings() {
    }

    @Override
    public @Nullable PacketMapping mappedPacket(State state, int unmappedId) {
        PacketMapping[] packets = this.packets[state.ordinal()];
        if (packets != null && unmappedId >= 0 && unmappedId < packets.length) {
            return packets[unmappedId];
        }
        return null;
    }

    @Override
    public void addMapping(State state, int unmappedId, PacketMapping mapping) {
        int ordinal = state.ordinal();
        PacketMapping[] packets = this.packets[ordinal];
        if (packets == null) {
            packets = new PacketMapping[unmappedId + 8];
            this.packets[ordinal] = packets;
        } else if (unmappedId >= packets.length) {
            packets = Arrays.copyOf(packets, unmappedId + 32);
            this.packets[ordinal] = packets;
        }
        packets[unmappedId] = mapping;
    }
}

