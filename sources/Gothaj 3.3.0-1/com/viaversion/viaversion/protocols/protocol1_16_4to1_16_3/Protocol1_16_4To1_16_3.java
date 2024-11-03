package com.viaversion.viaversion.protocols.protocol1_16_4to1_16_3;

import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ServerboundPackets1_16_2;

public class Protocol1_16_4To1_16_3
   extends AbstractProtocol<ClientboundPackets1_16_2, ClientboundPackets1_16_2, ServerboundPackets1_16_2, ServerboundPackets1_16_2> {
   public Protocol1_16_4To1_16_3() {
      super(ClientboundPackets1_16_2.class, ClientboundPackets1_16_2.class, ServerboundPackets1_16_2.class, ServerboundPackets1_16_2.class);
   }

   @Override
   protected void registerPackets() {
      this.registerServerbound(ServerboundPackets1_16_2.EDIT_BOOK, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.ITEM1_13_2);
            this.map(Type.BOOLEAN);
            this.handler(wrapper -> {
               int slot = wrapper.read(Type.VAR_INT);
               wrapper.write(Type.VAR_INT, slot == 40 ? 1 : 0);
            });
         }
      });
   }
}
