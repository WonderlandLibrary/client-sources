package com.viaversion.viaversion.protocols.protocol1_11to1_10.packets;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.EntityIdRewriter;
import com.viaversion.viaversion.protocols.protocol1_11to1_10.Protocol1_11To1_10;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
import com.viaversion.viaversion.rewriter.ItemRewriter;

public class InventoryPackets extends ItemRewriter<ClientboundPackets1_9_3, ServerboundPackets1_9_3, Protocol1_11To1_10> {
   public InventoryPackets(Protocol1_11To1_10 protocol) {
      super(protocol, null, null);
   }

   @Override
   public void registerPackets() {
      this.registerSetSlot(ClientboundPackets1_9_3.SET_SLOT, Type.ITEM1_8);
      this.registerWindowItems(ClientboundPackets1_9_3.WINDOW_ITEMS, Type.ITEM1_8_SHORT_ARRAY);
      this.registerEntityEquipment(ClientboundPackets1_9_3.ENTITY_EQUIPMENT, Type.ITEM1_8);
      this.protocol.registerClientbound(ClientboundPackets1_9_3.PLUGIN_MESSAGE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.handler(wrapper -> {
               if (wrapper.get(Type.STRING, 0).equalsIgnoreCase("MC|TrList")) {
                  wrapper.passthrough(Type.INT);
                  int size = wrapper.passthrough(Type.UNSIGNED_BYTE);

                  for (int i = 0; i < size; i++) {
                     EntityIdRewriter.toClientItem(wrapper.passthrough(Type.ITEM1_8));
                     EntityIdRewriter.toClientItem(wrapper.passthrough(Type.ITEM1_8));
                     boolean secondItem = wrapper.passthrough(Type.BOOLEAN);
                     if (secondItem) {
                        EntityIdRewriter.toClientItem(wrapper.passthrough(Type.ITEM1_8));
                     }

                     wrapper.passthrough(Type.BOOLEAN);
                     wrapper.passthrough(Type.INT);
                     wrapper.passthrough(Type.INT);
                  }
               }
            });
         }
      });
      this.registerClickWindow(ServerboundPackets1_9_3.CLICK_WINDOW, Type.ITEM1_8);
      this.registerCreativeInvAction(ServerboundPackets1_9_3.CREATIVE_INVENTORY_ACTION, Type.ITEM1_8);
   }

   @Override
   public Item handleItemToClient(Item item) {
      EntityIdRewriter.toClientItem(item);
      return item;
   }

   @Override
   public Item handleItemToServer(Item item) {
      EntityIdRewriter.toServerItem(item);
      if (item == null) {
         return null;
      } else {
         boolean newItem = item.identifier() >= 218 && item.identifier() <= 234;
         newItem |= item.identifier() == 449 || item.identifier() == 450;
         if (newItem) {
            item.setIdentifier(1);
            item.setData((short)0);
         }

         return item;
      }
   }
}
