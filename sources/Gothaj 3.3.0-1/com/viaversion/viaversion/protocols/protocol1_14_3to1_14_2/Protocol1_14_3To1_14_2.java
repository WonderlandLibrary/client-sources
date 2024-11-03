package com.viaversion.viaversion.protocols.protocol1_14_3to1_14_2;

import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;

public class Protocol1_14_3To1_14_2 extends AbstractProtocol<ClientboundPackets1_14, ClientboundPackets1_14, ServerboundPackets1_14, ServerboundPackets1_14> {
   public Protocol1_14_3To1_14_2() {
      super(ClientboundPackets1_14.class, ClientboundPackets1_14.class, null, null);
   }

   @Override
   protected void registerPackets() {
      this.registerClientbound(ClientboundPackets1_14.TRADE_LIST, wrapper -> {
         wrapper.passthrough(Type.VAR_INT);
         int size = wrapper.passthrough(Type.UNSIGNED_BYTE);

         for (int i = 0; i < size; i++) {
            wrapper.passthrough(Type.ITEM1_13_2);
            wrapper.passthrough(Type.ITEM1_13_2);
            if (wrapper.passthrough(Type.BOOLEAN)) {
               wrapper.passthrough(Type.ITEM1_13_2);
            }

            wrapper.passthrough(Type.BOOLEAN);
            wrapper.passthrough(Type.INT);
            wrapper.passthrough(Type.INT);
            wrapper.passthrough(Type.INT);
            wrapper.passthrough(Type.INT);
            wrapper.passthrough(Type.FLOAT);
         }

         wrapper.passthrough(Type.VAR_INT);
         wrapper.passthrough(Type.VAR_INT);
         boolean regularVillager = wrapper.passthrough(Type.BOOLEAN);
         wrapper.write(Type.BOOLEAN, regularVillager);
      });
   }
}
