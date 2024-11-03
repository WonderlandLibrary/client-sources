package com.viaversion.viaversion.protocols.protocol1_13_2to1_13_1.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_13_2to1_13_1.Protocol1_13_2To1_13_1;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
import com.viaversion.viaversion.util.Key;

public class InventoryPackets {
   public static void register(Protocol1_13_2To1_13_1 protocol) {
      protocol.registerClientbound(ClientboundPackets1_13.SET_SLOT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.SHORT);
            this.map(Type.ITEM1_13, Type.ITEM1_13_2);
         }
      });
      protocol.registerClientbound(ClientboundPackets1_13.WINDOW_ITEMS, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.ITEM1_13_SHORT_ARRAY, Type.ITEM1_13_2_SHORT_ARRAY);
         }
      });
      protocol.registerClientbound(ClientboundPackets1_13.PLUGIN_MESSAGE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.handler(wrapper -> {
               String channel = Key.namespaced(wrapper.get(Type.STRING, 0));
               if (channel.equals("minecraft:trader_list")) {
                  wrapper.passthrough(Type.INT);
                  int size = wrapper.passthrough(Type.UNSIGNED_BYTE);

                  for (int i = 0; i < size; i++) {
                     wrapper.write(Type.ITEM1_13_2, wrapper.read(Type.ITEM1_13));
                     wrapper.write(Type.ITEM1_13_2, wrapper.read(Type.ITEM1_13));
                     boolean secondItem = wrapper.passthrough(Type.BOOLEAN);
                     if (secondItem) {
                        wrapper.write(Type.ITEM1_13_2, wrapper.read(Type.ITEM1_13));
                     }

                     wrapper.passthrough(Type.BOOLEAN);
                     wrapper.passthrough(Type.INT);
                     wrapper.passthrough(Type.INT);
                  }
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_13.ENTITY_EQUIPMENT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.map(Type.ITEM1_13, Type.ITEM1_13_2);
         }
      });
      protocol.registerClientbound(ClientboundPackets1_13.DECLARE_RECIPES, wrapper -> {
         int recipesNo = wrapper.passthrough(Type.VAR_INT);

         for (int i = 0; i < recipesNo; i++) {
            wrapper.passthrough(Type.STRING);
            String type = wrapper.passthrough(Type.STRING);
            if (type.equals("crafting_shapeless")) {
               wrapper.passthrough(Type.STRING);
               int ingredientsNo = wrapper.passthrough(Type.VAR_INT);

               for (int i1 = 0; i1 < ingredientsNo; i1++) {
                  wrapper.write(Type.ITEM1_13_2_ARRAY, wrapper.read(Type.ITEM1_13_ARRAY));
               }

               wrapper.write(Type.ITEM1_13_2, wrapper.read(Type.ITEM1_13));
            } else if (!type.equals("crafting_shaped")) {
               if (type.equals("smelting")) {
                  wrapper.passthrough(Type.STRING);
                  wrapper.write(Type.ITEM1_13_2_ARRAY, wrapper.read(Type.ITEM1_13_ARRAY));
                  wrapper.write(Type.ITEM1_13_2, wrapper.read(Type.ITEM1_13));
                  wrapper.passthrough(Type.FLOAT);
                  wrapper.passthrough(Type.VAR_INT);
               }
            } else {
               int ingredientsNo = wrapper.passthrough(Type.VAR_INT) * wrapper.passthrough(Type.VAR_INT);
               wrapper.passthrough(Type.STRING);

               for (int i1 = 0; i1 < ingredientsNo; i1++) {
                  wrapper.write(Type.ITEM1_13_2_ARRAY, wrapper.read(Type.ITEM1_13_ARRAY));
               }

               wrapper.write(Type.ITEM1_13_2, wrapper.read(Type.ITEM1_13));
            }
         }
      });
      protocol.registerServerbound(ServerboundPackets1_13.CLICK_WINDOW, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.SHORT);
            this.map(Type.BYTE);
            this.map(Type.SHORT);
            this.map(Type.VAR_INT);
            this.map(Type.ITEM1_13_2, Type.ITEM1_13);
         }
      });
      protocol.registerServerbound(ServerboundPackets1_13.CREATIVE_INVENTORY_ACTION, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.SHORT);
            this.map(Type.ITEM1_13_2, Type.ITEM1_13);
         }
      });
   }
}
