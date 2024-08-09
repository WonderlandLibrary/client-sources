/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.protocol.packet.provider;

import com.google.common.annotations.Beta;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypeMap;
import java.util.Map;

@Beta
public interface PacketTypesProvider<CU extends ClientboundPacketType, CM extends ClientboundPacketType, SM extends ServerboundPacketType, SU extends ServerboundPacketType> {
    public Map<State, PacketTypeMap<CU>> unmappedClientboundPacketTypes();

    public Map<State, PacketTypeMap<SU>> unmappedServerboundPacketTypes();

    public Map<State, PacketTypeMap<CM>> mappedClientboundPacketTypes();

    public Map<State, PacketTypeMap<SM>> mappedServerboundPacketTypes();
}

