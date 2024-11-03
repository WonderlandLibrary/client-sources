package com.viaversion.viabackwards.protocol.protocol1_11to1_11_1.packets;

import com.viaversion.viabackwards.api.rewriters.LegacyBlockItemRewriter;
import com.viaversion.viabackwards.api.rewriters.LegacyEnchantmentRewriter;
import com.viaversion.viabackwards.protocol.protocol1_11to1_11_1.Protocol1_11To1_11_1;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;

public class ItemPackets1_11_1 extends LegacyBlockItemRewriter<ClientboundPackets1_9_3, ServerboundPackets1_9_3, Protocol1_11To1_11_1> {
   private LegacyEnchantmentRewriter enchantmentRewriter;

   public ItemPackets1_11_1(Protocol1_11To1_11_1 protocol) {
      super(protocol);
   }

   @Override
   protected void registerPackets() {
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
                     wrapper.write(Type.ITEM1_8, ItemPackets1_11_1.this.handleItemToClient(wrapper.read(Type.ITEM1_8)));
                     wrapper.write(Type.ITEM1_8, ItemPackets1_11_1.this.handleItemToClient(wrapper.read(Type.ITEM1_8)));
                     boolean secondItem = wrapper.passthrough(Type.BOOLEAN);
                     if (secondItem) {
                        wrapper.write(Type.ITEM1_8, ItemPackets1_11_1.this.handleItemToClient(wrapper.read(Type.ITEM1_8)));
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
      this.protocol.getEntityRewriter().filter().handler((event, meta) -> {
         if (meta.metaType().type().equals(Type.ITEM1_8)) {
            meta.setValue(this.handleItemToClient((Item)meta.getValue()));
         }
      });
   }

   @Override
   protected void registerRewrites() {
      this.enchantmentRewriter = new LegacyEnchantmentRewriter(this.nbtTagName);
      this.enchantmentRewriter.registerEnchantment(22, "§7Sweeping Edge");
   }

   @Override
   public Item handleItemToClient(Item item) {
      if (item == null) {
         return null;
      } else {
         super.handleItemToClient(item);
         CompoundTag tag = item.tag();
         if (tag == null) {
            return item;
         } else {
            if (tag.get("ench") instanceof ListTag) {
               this.enchantmentRewriter.rewriteEnchantmentsToClient(tag, false);
            }

            if (tag.get("StoredEnchantments") instanceof ListTag) {
               this.enchantmentRewriter.rewriteEnchantmentsToClient(tag, true);
            }

            return item;
         }
      }
   }

   @Override
   public Item handleItemToServer(Item item) {
      if (item == null) {
         return null;
      } else {
         super.handleItemToServer(item);
         CompoundTag tag = item.tag();
         if (tag == null) {
            return item;
         } else {
            if (tag.contains(this.nbtTagName + "|ench")) {
               this.enchantmentRewriter.rewriteEnchantmentsToServer(tag, false);
            }

            if (tag.contains(this.nbtTagName + "|StoredEnchantments")) {
               this.enchantmentRewriter.rewriteEnchantmentsToServer(tag, true);
            }

            return item;
         }
      }
   }
}
