package com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.Protocol1_13_1To1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;
import com.viaversion.viaversion.util.Key;

public class InventoryPackets extends ItemRewriter<ClientboundPackets1_13, ServerboundPackets1_13, Protocol1_13_1To1_13> {
   public InventoryPackets(Protocol1_13_1To1_13 protocol) {
      super(protocol, Type.ITEM1_13, Type.ITEM1_13_ARRAY);
   }

   @Override
   public void registerPackets() {
      this.registerSetSlot(ClientboundPackets1_13.SET_SLOT, Type.ITEM1_13);
      this.registerWindowItems(ClientboundPackets1_13.WINDOW_ITEMS, Type.ITEM1_13_SHORT_ARRAY);
      this.registerAdvancements(ClientboundPackets1_13.ADVANCEMENTS, Type.ITEM1_13);
      this.registerSetCooldown(ClientboundPackets1_13.COOLDOWN);
      this.protocol.registerClientbound(ClientboundPackets1_13.PLUGIN_MESSAGE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.handler(wrapper -> {
               String channel = Key.namespaced(wrapper.get(Type.STRING, 0));
               if (channel.equals("minecraft:trader_list")) {
                  wrapper.passthrough(Type.INT);
                  int size = wrapper.passthrough(Type.UNSIGNED_BYTE);

                  for (int i = 0; i < size; i++) {
                     InventoryPackets.this.handleItemToClient(wrapper.passthrough(Type.ITEM1_13));
                     InventoryPackets.this.handleItemToClient(wrapper.passthrough(Type.ITEM1_13));
                     boolean secondItem = wrapper.passthrough(Type.BOOLEAN);
                     if (secondItem) {
                        InventoryPackets.this.handleItemToClient(wrapper.passthrough(Type.ITEM1_13));
                     }

                     wrapper.passthrough(Type.BOOLEAN);
                     wrapper.passthrough(Type.INT);
                     wrapper.passthrough(Type.INT);
                  }
               }
            });
         }
      });
      this.registerEntityEquipment(ClientboundPackets1_13.ENTITY_EQUIPMENT, Type.ITEM1_13);
      RecipeRewriter<ClientboundPackets1_13> recipeRewriter = new RecipeRewriter<ClientboundPackets1_13>(this.protocol) {
         @Override
         protected Type<Item> itemType() {
            return Type.ITEM1_13;
         }

         @Override
         protected Type<Item[]> itemArrayType() {
            return Type.ITEM1_13_ARRAY;
         }
      };
      this.protocol.registerClientbound(ClientboundPackets1_13.DECLARE_RECIPES, wrapper -> {
         int size = wrapper.passthrough(Type.VAR_INT);

         for (int i = 0; i < size; i++) {
            wrapper.passthrough(Type.STRING);
            String type = Key.stripMinecraftNamespace(wrapper.passthrough(Type.STRING));
            recipeRewriter.handleRecipeType(wrapper, type);
         }
      });
      this.registerClickWindow(ServerboundPackets1_13.CLICK_WINDOW, Type.ITEM1_13);
      this.registerCreativeInvAction(ServerboundPackets1_13.CREATIVE_INVENTORY_ACTION, Type.ITEM1_13);
      this.registerSpawnParticle(ClientboundPackets1_13.SPAWN_PARTICLE, Type.ITEM1_13, Type.FLOAT);
   }
}
