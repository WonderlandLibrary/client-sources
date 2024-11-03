package com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.packets;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_18;
import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.ClientboundPackets1_19_1;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.Protocol1_19_3To1_19_1;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ServerboundPackets1_19_3;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;
import com.viaversion.viaversion.util.Key;

public final class InventoryPackets extends ItemRewriter<ClientboundPackets1_19_1, ServerboundPackets1_19_3, Protocol1_19_3To1_19_1> {
   private static final int MISC_CRAFTING_BOOK_CATEGORY = 0;

   public InventoryPackets(Protocol1_19_3To1_19_1 protocol) {
      super(protocol, Type.ITEM1_13_2, Type.ITEM1_13_2_ARRAY);
   }

   @Override
   public void registerPackets() {
      BlockRewriter<ClientboundPackets1_19_1> blockRewriter = BlockRewriter.for1_14(this.protocol);
      blockRewriter.registerBlockAction(ClientboundPackets1_19_1.BLOCK_ACTION);
      blockRewriter.registerBlockChange(ClientboundPackets1_19_1.BLOCK_CHANGE);
      blockRewriter.registerVarLongMultiBlockChange(ClientboundPackets1_19_1.MULTI_BLOCK_CHANGE);
      blockRewriter.registerEffect(ClientboundPackets1_19_1.EFFECT, 1010, 2001);
      blockRewriter.registerChunkData1_19(ClientboundPackets1_19_1.CHUNK_DATA, ChunkType1_18::new);
      blockRewriter.registerBlockEntityData(ClientboundPackets1_19_1.BLOCK_ENTITY_DATA);
      this.registerSetCooldown(ClientboundPackets1_19_1.COOLDOWN);
      this.registerWindowItems1_17_1(ClientboundPackets1_19_1.WINDOW_ITEMS);
      this.registerSetSlot1_17_1(ClientboundPackets1_19_1.SET_SLOT);
      this.registerAdvancements(ClientboundPackets1_19_1.ADVANCEMENTS, Type.ITEM1_13_2);
      this.registerEntityEquipmentArray(ClientboundPackets1_19_1.ENTITY_EQUIPMENT);
      this.registerClickWindow1_17_1(ServerboundPackets1_19_3.CLICK_WINDOW);
      this.registerTradeList1_19(ClientboundPackets1_19_1.TRADE_LIST);
      this.registerCreativeInvAction(ServerboundPackets1_19_3.CREATIVE_INVENTORY_ACTION, Type.ITEM1_13_2);
      this.registerWindowPropertyEnchantmentHandler(ClientboundPackets1_19_1.WINDOW_PROPERTY);
      this.registerSpawnParticle1_19(ClientboundPackets1_19_1.SPAWN_PARTICLE);
      RecipeRewriter<ClientboundPackets1_19_1> recipeRewriter = new RecipeRewriter<>(this.protocol);
      this.protocol.registerClientbound(ClientboundPackets1_19_1.DECLARE_RECIPES, wrapper -> {
         int size = wrapper.passthrough(Type.VAR_INT);

         for (int i = 0; i < size; i++) {
            String type = Key.stripMinecraftNamespace(wrapper.passthrough(Type.STRING));
            wrapper.passthrough(Type.STRING);
            switch (type) {
               case "crafting_shapeless":
                  wrapper.passthrough(Type.STRING);
                  wrapper.write(Type.VAR_INT, 0);
                  int ingredients = wrapper.passthrough(Type.VAR_INT);

                  for (int j = 0; j < ingredients; j++) {
                     Item[] items = wrapper.passthrough(Type.ITEM1_13_2_ARRAY);

                     for (Item item : items) {
                        this.handleItemToClient(item);
                     }
                  }

                  this.handleItemToClient(wrapper.passthrough(Type.ITEM1_13_2));
                  break;
               case "crafting_shaped":
                  int ingredients = wrapper.passthrough(Type.VAR_INT) * wrapper.passthrough(Type.VAR_INT);
                  wrapper.passthrough(Type.STRING);
                  wrapper.write(Type.VAR_INT, 0);

                  for (int j = 0; j < ingredients; j++) {
                     Item[] items = wrapper.passthrough(Type.ITEM1_13_2_ARRAY);

                     for (Item item : items) {
                        this.handleItemToClient(item);
                     }
                  }

                  this.handleItemToClient(wrapper.passthrough(Type.ITEM1_13_2));
                  break;
               case "smelting":
               case "campfire_cooking":
               case "blasting":
               case "smoking":
                  wrapper.passthrough(Type.STRING);
                  wrapper.write(Type.VAR_INT, 0);
                  Item[] items = wrapper.passthrough(Type.ITEM1_13_2_ARRAY);

                  for (Item item : items) {
                     this.handleItemToClient(item);
                  }

                  this.handleItemToClient(wrapper.passthrough(Type.ITEM1_13_2));
                  wrapper.passthrough(Type.FLOAT);
                  wrapper.passthrough(Type.VAR_INT);
                  break;
               case "crafting_special_armordye":
               case "crafting_special_bookcloning":
               case "crafting_special_mapcloning":
               case "crafting_special_mapextending":
               case "crafting_special_firework_rocket":
               case "crafting_special_firework_star":
               case "crafting_special_firework_star_fade":
               case "crafting_special_tippedarrow":
               case "crafting_special_bannerduplicate":
               case "crafting_special_shielddecoration":
               case "crafting_special_shulkerboxcoloring":
               case "crafting_special_suspiciousstew":
               case "crafting_special_repairitem":
                  wrapper.write(Type.VAR_INT, 0);
                  break;
               default:
                  recipeRewriter.handleRecipeType(wrapper, type);
            }
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_19_1.EXPLOSION, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.FLOAT, Type.DOUBLE);
            this.map(Type.FLOAT, Type.DOUBLE);
            this.map(Type.FLOAT, Type.DOUBLE);
         }
      });
   }
}
