/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.protocol.packet.provider;

import com.google.common.annotations.Beta;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypeMap;
import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypesProvider;
import java.util.Map;

@Beta
public final class SimplePacketTypesProvider<CU extends ClientboundPacketType, CM extends ClientboundPacketType, SM extends ServerboundPacketType, SU extends ServerboundPacketType>
implements PacketTypesProvider<CU, CM, SM, SU> {
    private final Map<State, PacketTypeMap<CU>> unmappedClientboundPacketTypes;
    private final Map<State, PacketTypeMap<CM>> mappedClientboundPacketTypes;
    private final Map<State, PacketTypeMap<SM>> mappedServerboundPacketTypes;
    private final Map<State, PacketTypeMap<SU>> unmappedServerboundPacketTypes;

    public SimplePacketTypesProvider(Map<State, PacketTypeMap<CU>> map, Map<State, PacketTypeMap<CM>> map2, Map<State, PacketTypeMap<SM>> map3, Map<State, PacketTypeMap<SU>> map4) {
        this.unmappedClientboundPacketTypes = map;
        this.mappedClientboundPacketTypes = map2;
        this.mappedServerboundPacketTypes = map3;
        this.unmappedServerboundPacketTypes = map4;
    }

    @Override
    public Map<State, PacketTypeMap<CU>> unmappedClientboundPacketTypes() {
        return this.unmappedClientboundPacketTypes;
    }

    @Override
    public Map<State, PacketTypeMap<CM>> mappedClientboundPacketTypes() {
        return this.mappedClientboundPacketTypes;
    }

    @Override
    public Map<State, PacketTypeMap<SM>> mappedServerboundPacketTypes() {
        return this.mappedServerboundPacketTypes;
    }

    @Override
    public Map<State, PacketTypeMap<SU>> unmappedServerboundPacketTypes() {
        return this.unmappedServerboundPacketTypes;
    }
}

