package com.viaversion.viaversion.api.protocol.packet.provider;

import com.google.common.annotations.Beta;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.State;
import java.util.Map;

@Beta
public interface PacketTypesProvider<CU extends ClientboundPacketType, CM extends ClientboundPacketType, SM extends ServerboundPacketType, SU extends ServerboundPacketType> {
   Map<State, PacketTypeMap<CU>> unmappedClientboundPacketTypes();

   Map<State, PacketTypeMap<SU>> unmappedServerboundPacketTypes();

   Map<State, PacketTypeMap<CM>> mappedClientboundPacketTypes();

   Map<State, PacketTypeMap<SM>> mappedServerboundPacketTypes();
}
