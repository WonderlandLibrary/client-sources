package com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.packets;

import com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.Protocol1_13To1_13_1;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
import com.viaversion.viaversion.rewriter.ItemRewriter;

public class InventoryPackets1_13_1 extends ItemRewriter<ClientboundPackets1_13, ServerboundPackets1_13, Protocol1_13To1_13_1> {
   public InventoryPackets1_13_1(Protocol1_13To1_13_1 protocol) {
      super(protocol, Type.ITEM1_13, Type.ITEM1_13_ARRAY);
   }

   @Override
   public void registerPackets() {
      this.registerSetCooldown(ClientboundPackets1_13.COOLDOWN);
      this.registerWindowItems(ClientboundPackets1_13.WINDOW_ITEMS, Type.ITEM1_13_ARRAY);
      this.registerSetSlot(ClientboundPackets1_13.SET_SLOT, Type.ITEM1_13);
      this.protocol.registerClientbound(ClientboundPackets1_13.PLUGIN_MESSAGE, wrapper -> {
         String channel = wrapper.passthrough(Type.STRING);
         if (channel.equals("minecraft:trader_list")) {
            wrapper.passthrough(Type.INT);
            int size = wrapper.passthrough(Type.UNSIGNED_BYTE);

            for (int i = 0; i < size; i++) {
               Item input = wrapper.passthrough(Type.ITEM1_13);
               this.handleItemToClient(input);
               Item output = wrapper.passthrough(Type.ITEM1_13);
               this.handleItemToClient(output);
               boolean secondItem = wrapper.passthrough(Type.BOOLEAN);
               if (secondItem) {
                  Item second = wrapper.passthrough(Type.ITEM1_13);
                  this.handleItemToClient(second);
               }

               wrapper.passthrough(Type.BOOLEAN);
               wrapper.passthrough(Type.INT);
               wrapper.passthrough(Type.INT);
            }
         }
      });
      this.registerEntityEquipment(ClientboundPackets1_13.ENTITY_EQUIPMENT, Type.ITEM1_13);
      this.registerClickWindow(ServerboundPackets1_13.CLICK_WINDOW, Type.ITEM1_13);
      this.registerCreativeInvAction(ServerboundPackets1_13.CREATIVE_INVENTORY_ACTION, Type.ITEM1_13);
      this.registerSpawnParticle(ClientboundPackets1_13.SPAWN_PARTICLE, Type.ITEM1_13, Type.FLOAT);
   }
}
