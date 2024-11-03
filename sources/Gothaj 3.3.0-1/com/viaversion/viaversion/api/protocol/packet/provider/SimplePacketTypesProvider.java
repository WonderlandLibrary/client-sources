package com.viaversion.viaversion.api.protocol.packet.provider;

import com.google.common.annotations.Beta;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.State;
import java.util.Map;

@Beta
public final class SimplePacketTypesProvider<CU extends ClientboundPacketType, CM extends ClientboundPacketType, SM extends ServerboundPacketType, SU extends ServerboundPacketType>
   implements PacketTypesProvider<CU, CM, SM, SU> {
   private final Map<State, PacketTypeMap<CU>> unmappedClientboundPacketTypes;
   private final Map<State, PacketTypeMap<CM>> mappedClientboundPacketTypes;
   private final Map<State, PacketTypeMap<SM>> mappedServerboundPacketTypes;
   private final Map<State, PacketTypeMap<SU>> unmappedServerboundPacketTypes;

   public SimplePacketTypesProvider(
      Map<State, PacketTypeMap<CU>> unmappedClientboundPacketTypes,
      Map<State, PacketTypeMap<CM>> mappedClientboundPacketTypes,
      Map<State, PacketTypeMap<SM>> mappedServerboundPacketTypes,
      Map<State, PacketTypeMap<SU>> unmappedServerboundPacketTypes
   ) {
      this.unmappedClientboundPacketTypes = unmappedClientboundPacketTypes;
      this.mappedClientboundPacketTypes = mappedClientboundPacketTypes;
      this.mappedServerboundPacketTypes = mappedServerboundPacketTypes;
      this.unmappedServerboundPacketTypes = unmappedServerboundPacketTypes;
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
