package com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.packets;

import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
import com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.Protocol1_19_1To1_19_3;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_18;
import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.ServerboundPackets1_19_1;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ClientboundPackets1_19_3;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;
import com.viaversion.viaversion.util.Key;

public final class BlockItemPackets1_19_3 extends ItemRewriter<ClientboundPackets1_19_3, ServerboundPackets1_19_1, Protocol1_19_1To1_19_3> {
   public BlockItemPackets1_19_3(Protocol1_19_1To1_19_3 protocol) {
      super(protocol);
   }

   @Override
   protected void registerPackets() {
      BlockRewriter<ClientboundPackets1_19_3> blockRewriter = BlockRewriter.for1_14(this.protocol);
      blockRewriter.registerBlockAction(ClientboundPackets1_19_3.BLOCK_ACTION);
      blockRewriter.registerBlockChange(ClientboundPackets1_19_3.BLOCK_CHANGE);
      blockRewriter.registerVarLongMultiBlockChange(ClientboundPackets1_19_3.MULTI_BLOCK_CHANGE);
      blockRewriter.registerEffect(ClientboundPackets1_19_3.EFFECT, 1010, 2001);
      blockRewriter.registerChunkData1_19(ClientboundPackets1_19_3.CHUNK_DATA, ChunkType1_18::new);
      blockRewriter.registerBlockEntityData(ClientboundPackets1_19_3.BLOCK_ENTITY_DATA);
      this.registerSetCooldown(ClientboundPackets1_19_3.COOLDOWN);
      this.registerWindowItems1_17_1(ClientboundPackets1_19_3.WINDOW_ITEMS);
      this.registerSetSlot1_17_1(ClientboundPackets1_19_3.SET_SLOT);
      this.registerEntityEquipmentArray(ClientboundPackets1_19_3.ENTITY_EQUIPMENT);
      this.registerAdvancements(ClientboundPackets1_19_3.ADVANCEMENTS, Type.ITEM1_13_2);
      this.registerClickWindow1_17_1(ServerboundPackets1_19_1.CLICK_WINDOW);
      this.registerTradeList1_19(ClientboundPackets1_19_3.TRADE_LIST);
      this.registerCreativeInvAction(ServerboundPackets1_19_1.CREATIVE_INVENTORY_ACTION, Type.ITEM1_13_2);
      this.registerWindowPropertyEnchantmentHandler(ClientboundPackets1_19_3.WINDOW_PROPERTY);
      this.registerSpawnParticle1_19(ClientboundPackets1_19_3.SPAWN_PARTICLE);
      this.protocol.registerClientbound(ClientboundPackets1_19_3.EXPLOSION, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.DOUBLE, Type.FLOAT);
            this.map(Type.DOUBLE, Type.FLOAT);
            this.map(Type.DOUBLE, Type.FLOAT);
         }
      });
      RecipeRewriter<ClientboundPackets1_19_3> recipeRewriter = new RecipeRewriter<>(this.protocol);
      this.protocol.registerClientbound(ClientboundPackets1_19_3.DECLARE_RECIPES, wrapper -> {
         int size = wrapper.passthrough(Type.VAR_INT);

         for (int i = 0; i < size; i++) {
            String type = Key.stripMinecraftNamespace(wrapper.passthrough(Type.STRING));
            wrapper.passthrough(Type.STRING);
            switch (type) {
               case "crafting_shapeless":
                  wrapper.passthrough(Type.STRING);
                  wrapper.read(Type.VAR_INT);
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
                  wrapper.read(Type.VAR_INT);

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
                  wrapper.read(Type.VAR_INT);
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
                  wrapper.read(Type.VAR_INT);
                  break;
               default:
                  recipeRewriter.handleRecipeType(wrapper, type);
            }
         }
      });
   }
}
