/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.protocol.packet.mapping;

import com.viaversion.viaversion.api.protocol.packet.PacketType;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.packet.mapping.PacketArrayMappings;
import com.viaversion.viaversion.api.protocol.packet.mapping.PacketMapping;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface PacketMappings {
    public @Nullable PacketMapping mappedPacket(State var1, int var2);

    default public boolean hasMapping(PacketType packetType) {
        return this.mappedPacket(packetType.state(), packetType.getId()) != null;
    }

    default public boolean hasMapping(State state, int n) {
        return this.mappedPacket(state, n) != null;
    }

    default public void addMapping(PacketType packetType, PacketMapping packetMapping) {
        this.addMapping(packetType.state(), packetType.getId(), packetMapping);
    }

    public void addMapping(State var1, int var2, PacketMapping var3);

    public static PacketMappings arrayMappings() {
        return new PacketArrayMappings();
    }
}

