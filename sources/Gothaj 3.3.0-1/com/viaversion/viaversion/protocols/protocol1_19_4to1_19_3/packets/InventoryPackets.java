package com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.packets;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_18;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ClientboundPackets1_19_3;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.rewriter.RecipeRewriter1_19_3;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.Protocol1_19_4To1_19_3;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ServerboundPackets1_19_4;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.ItemRewriter;

public final class InventoryPackets extends ItemRewriter<ClientboundPackets1_19_3, ServerboundPackets1_19_4, Protocol1_19_4To1_19_3> {
   public InventoryPackets(Protocol1_19_4To1_19_3 protocol) {
      super(protocol, Type.ITEM1_13_2, Type.ITEM1_13_2_ARRAY);
   }

   @Override
   public void registerPackets() {
      BlockRewriter<ClientboundPackets1_19_3> blockRewriter = BlockRewriter.for1_14(this.protocol);
      blockRewriter.registerBlockAction(ClientboundPackets1_19_3.BLOCK_ACTION);
      blockRewriter.registerBlockChange(ClientboundPackets1_19_3.BLOCK_CHANGE);
      blockRewriter.registerVarLongMultiBlockChange(ClientboundPackets1_19_3.MULTI_BLOCK_CHANGE);
      blockRewriter.registerChunkData1_19(ClientboundPackets1_19_3.CHUNK_DATA, ChunkType1_18::new);
      blockRewriter.registerBlockEntityData(ClientboundPackets1_19_3.BLOCK_ENTITY_DATA);
      this.protocol.registerClientbound(ClientboundPackets1_19_3.EFFECT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.map(Type.POSITION1_14);
            this.map(Type.INT);
            this.handler(wrapper -> {
               int id = wrapper.get(Type.INT, 0);
               int data = wrapper.get(Type.INT, 1);
               if (id == 1010) {
                  if (data >= 1092 && data <= 1106) {
                     wrapper.set(Type.INT, 1, InventoryPackets.this.protocol.getMappingData().getNewItemId(data));
                  } else {
                     wrapper.set(Type.INT, 0, 1011);
                     wrapper.set(Type.INT, 1, 0);
                  }
               } else if (id == 2001) {
                  wrapper.set(Type.INT, 1, InventoryPackets.this.protocol.getMappingData().getNewBlockStateId(data));
               }
            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_19_3.OPEN_WINDOW, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.map(Type.COMPONENT);
            this.handler(wrapper -> {
               int windowType = wrapper.get(Type.VAR_INT, 1);
               if (windowType >= 21) {
                  wrapper.set(Type.VAR_INT, 1, windowType + 1);
               }
            });
         }
      });
      this.registerSetCooldown(ClientboundPackets1_19_3.COOLDOWN);
      this.registerWindowItems1_17_1(ClientboundPackets1_19_3.WINDOW_ITEMS);
      this.registerSetSlot1_17_1(ClientboundPackets1_19_3.SET_SLOT);
      this.registerAdvancements(ClientboundPackets1_19_3.ADVANCEMENTS, Type.ITEM1_13_2);
      this.registerEntityEquipmentArray(ClientboundPackets1_19_3.ENTITY_EQUIPMENT);
      this.registerTradeList1_19(ClientboundPackets1_19_3.TRADE_LIST);
      this.registerWindowPropertyEnchantmentHandler(ClientboundPackets1_19_3.WINDOW_PROPERTY);
      this.registerSpawnParticle1_19(ClientboundPackets1_19_3.SPAWN_PARTICLE);
      this.registerCreativeInvAction(ServerboundPackets1_19_4.CREATIVE_INVENTORY_ACTION, Type.ITEM1_13_2);
      this.registerClickWindow1_17_1(ServerboundPackets1_19_4.CLICK_WINDOW);
      (new RecipeRewriter1_19_3<ClientboundPackets1_19_3>(this.protocol) {
         @Override
         public void handleCraftingShaped(PacketWrapper wrapper) throws Exception {
            super.handleCraftingShaped(wrapper);
            wrapper.write(Type.BOOLEAN, true);
         }
      }).register(ClientboundPackets1_19_3.DECLARE_RECIPES);
   }
}
