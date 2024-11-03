package com.viaversion.viabackwards.protocol.protocol1_13_1to1_13_2;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.protocol.protocol1_13_1to1_13_2.packets.EntityPackets1_13_2;
import com.viaversion.viabackwards.protocol.protocol1_13_1to1_13_2.packets.InventoryPackets1_13_2;
import com.viaversion.viabackwards.protocol.protocol1_13_1to1_13_2.packets.WorldPackets1_13_2;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;

public class Protocol1_13_1To1_13_2 extends BackwardsProtocol<ClientboundPackets1_13, ClientboundPackets1_13, ServerboundPackets1_13, ServerboundPackets1_13> {
   public Protocol1_13_1To1_13_2() {
      super(ClientboundPackets1_13.class, ClientboundPackets1_13.class, ServerboundPackets1_13.class, ServerboundPackets1_13.class);
   }

   @Override
   protected void registerPackets() {
      InventoryPackets1_13_2.register(this);
      WorldPackets1_13_2.register(this);
      EntityPackets1_13_2.register(this);
      this.registerServerbound(ServerboundPackets1_13.EDIT_BOOK, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.ITEM1_13, Type.ITEM1_13_2);
         }
      });
      this.registerClientbound(ClientboundPackets1_13.ADVANCEMENTS, wrapper -> {
         wrapper.passthrough(Type.BOOLEAN);
         int size = wrapper.passthrough(Type.VAR_INT);

         for (int i = 0; i < size; i++) {
            wrapper.passthrough(Type.STRING);
            if (wrapper.passthrough(Type.BOOLEAN)) {
               wrapper.passthrough(Type.STRING);
            }

            if (wrapper.passthrough(Type.BOOLEAN)) {
               wrapper.passthrough(Type.COMPONENT);
               wrapper.passthrough(Type.COMPONENT);
               Item icon = wrapper.read(Type.ITEM1_13_2);
               wrapper.write(Type.ITEM1_13, icon);
               wrapper.passthrough(Type.VAR_INT);
               int flags = wrapper.passthrough(Type.INT);
               if ((flags & 1) != 0) {
                  wrapper.passthrough(Type.STRING);
               }

               wrapper.passthrough(Type.FLOAT);
               wrapper.passthrough(Type.FLOAT);
            }

            wrapper.passthrough(Type.STRING_ARRAY);
            int arrayLength = wrapper.passthrough(Type.VAR_INT);

            for (int array = 0; array < arrayLength; array++) {
               wrapper.passthrough(Type.STRING_ARRAY);
            }
         }
      });
   }
}
