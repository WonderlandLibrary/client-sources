package com.viaversion.viabackwards.protocol.protocol1_20_2to1_20_3.rewriter;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
import com.viaversion.viabackwards.protocol.protocol1_20_2to1_20_3.Protocol1_20_2To1_20_3;
import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_20_2;
import com.viaversion.viaversion.api.type.types.version.Types1_20_3;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.packet.ServerboundPackets1_20_2;
import com.viaversion.viaversion.protocols.protocol1_20_3to1_20_2.packet.ClientboundPackets1_20_3;
import com.viaversion.viaversion.protocols.protocol1_20_3to1_20_2.rewriter.RecipeRewriter1_20_3;
import com.viaversion.viaversion.rewriter.BlockRewriter;

public final class BlockItemPacketRewriter1_20_3 extends ItemRewriter<ClientboundPackets1_20_3, ServerboundPackets1_20_2, Protocol1_20_2To1_20_3> {
   public BlockItemPacketRewriter1_20_3(Protocol1_20_2To1_20_3 protocol) {
      super(protocol, Type.ITEM1_20_2, Type.ITEM1_20_2_ARRAY);
   }

   @Override
   public void registerPackets() {
      BlockRewriter<ClientboundPackets1_20_3> blockRewriter = BlockRewriter.for1_20_2(this.protocol);
      blockRewriter.registerBlockAction(ClientboundPackets1_20_3.BLOCK_ACTION);
      blockRewriter.registerBlockChange(ClientboundPackets1_20_3.BLOCK_CHANGE);
      blockRewriter.registerVarLongMultiBlockChange1_20(ClientboundPackets1_20_3.MULTI_BLOCK_CHANGE);
      blockRewriter.registerEffect(ClientboundPackets1_20_3.EFFECT, 1010, 2001);
      blockRewriter.registerChunkData1_19(ClientboundPackets1_20_3.CHUNK_DATA, ChunkType1_20_2::new);
      blockRewriter.registerBlockEntityData(ClientboundPackets1_20_3.BLOCK_ENTITY_DATA);
      this.registerSetCooldown(ClientboundPackets1_20_3.COOLDOWN);
      this.registerWindowItems1_17_1(ClientboundPackets1_20_3.WINDOW_ITEMS);
      this.registerSetSlot1_17_1(ClientboundPackets1_20_3.SET_SLOT);
      this.registerEntityEquipmentArray(ClientboundPackets1_20_3.ENTITY_EQUIPMENT);
      this.registerClickWindow1_17_1(ServerboundPackets1_20_2.CLICK_WINDOW);
      this.registerTradeList1_19(ClientboundPackets1_20_3.TRADE_LIST);
      this.registerCreativeInvAction(ServerboundPackets1_20_2.CREATIVE_INVENTORY_ACTION);
      this.registerWindowPropertyEnchantmentHandler(ClientboundPackets1_20_3.WINDOW_PROPERTY);
      this.protocol.registerClientbound(ClientboundPackets1_20_3.SPAWN_PARTICLE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.BOOLEAN);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.INT);
            this.handler(wrapper -> {
               int id = wrapper.get(Type.VAR_INT, 0);
               ParticleMappings particleMappings = BlockItemPacketRewriter1_20_3.this.protocol.getMappingData().getParticleMappings();
               if (id == particleMappings.id("vibration")) {
                  int positionSourceType = wrapper.read(Type.VAR_INT);
                  if (positionSourceType == 0) {
                     wrapper.write(Type.STRING, "minecraft:block");
                  } else if (positionSourceType == 1) {
                     wrapper.write(Type.STRING, "minecraft:entity");
                  } else {
                     ViaBackwards.getPlatform().getLogger().warning("Unknown position source type: " + positionSourceType);
                     wrapper.cancel();
                  }
               }
            });
            this.handler(BlockItemPacketRewriter1_20_3.this.getSpawnParticleHandler(Type.VAR_INT));
         }
      });
      (new RecipeRewriter1_20_3<ClientboundPackets1_20_3>(this.protocol) {
         @Override
         public void handleCraftingShaped(PacketWrapper wrapper) throws Exception {
            String group = wrapper.read(Type.STRING);
            int craftingBookCategory = wrapper.read(Type.VAR_INT);
            int width = wrapper.passthrough(Type.VAR_INT);
            int height = wrapper.passthrough(Type.VAR_INT);
            wrapper.write(Type.STRING, group);
            wrapper.write(Type.VAR_INT, craftingBookCategory);
            int ingredients = height * width;

            for (int i = 0; i < ingredients; i++) {
               this.handleIngredient(wrapper);
            }

            this.rewrite(wrapper.passthrough(this.itemType()));
            wrapper.passthrough(Type.BOOLEAN);
         }
      }).register(ClientboundPackets1_20_3.DECLARE_RECIPES);
      this.protocol.registerClientbound(ClientboundPackets1_20_3.EXPLOSION, wrapper -> {
         wrapper.passthrough(Type.DOUBLE);
         wrapper.passthrough(Type.DOUBLE);
         wrapper.passthrough(Type.DOUBLE);
         wrapper.passthrough(Type.FLOAT);
         int blocks = wrapper.read(Type.VAR_INT);
         byte[][] toBlow = new byte[blocks][3];

         for (int i = 0; i < blocks; i++) {
            toBlow[i] = new byte[]{wrapper.read(Type.BYTE), wrapper.read(Type.BYTE), wrapper.read(Type.BYTE)};
         }

         float knockbackX = wrapper.read(Type.FLOAT);
         float knockbackY = wrapper.read(Type.FLOAT);
         float knockbackZ = wrapper.read(Type.FLOAT);
         int blockInteraction = wrapper.read(Type.VAR_INT);
         if (blockInteraction != 1 && blockInteraction != 2) {
            wrapper.write(Type.VAR_INT, 0);
         } else {
            wrapper.write(Type.VAR_INT, blocks);

            for (byte[] relativeXYZ : toBlow) {
               wrapper.write(Type.BYTE, relativeXYZ[0]);
               wrapper.write(Type.BYTE, relativeXYZ[1]);
               wrapper.write(Type.BYTE, relativeXYZ[2]);
            }
         }

         wrapper.write(Type.FLOAT, knockbackX);
         wrapper.write(Type.FLOAT, knockbackY);
         wrapper.write(Type.FLOAT, knockbackZ);
         wrapper.read(Types1_20_3.PARTICLE);
         wrapper.read(Types1_20_3.PARTICLE);
         wrapper.read(Type.STRING);
         wrapper.read(Type.OPTIONAL_FLOAT);
      });
   }
}
