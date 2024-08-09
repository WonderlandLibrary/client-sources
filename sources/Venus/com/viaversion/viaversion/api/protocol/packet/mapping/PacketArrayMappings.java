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
    public @Nullable PacketMapping mappedPacket(State state, int n) {
        PacketMapping[] packetMappingArray = this.packets[state.ordinal()];
        if (packetMappingArray != null && n >= 0 && n < packetMappingArray.length) {
            return packetMappingArray[n];
        }
        return null;
    }

    @Override
    public void addMapping(State state, int n, PacketMapping packetMapping) {
        int n2 = state.ordinal();
        PacketMapping[] packetMappingArray = this.packets[n2];
        if (packetMappingArray == null) {
            packetMappingArray = new PacketMapping[n + 8];
            this.packets[n2] = packetMappingArray;
        } else if (n >= packetMappingArray.length) {
            packetMappingArray = Arrays.copyOf(packetMappingArray, n + 32);
            this.packets[n2] = packetMappingArray;
        }
        packetMappingArray[n] = packetMapping;
    }
}

