package com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.packets;

import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.Protocol1_18_2To1_19;
import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_18;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.ClientboundPackets1_19;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;
import com.viaversion.viaversion.util.MathUtil;

public final class BlockItemPackets1_19 extends ItemRewriter<ClientboundPackets1_19, ServerboundPackets1_17, Protocol1_18_2To1_19> {
   public BlockItemPackets1_19(Protocol1_18_2To1_19 protocol) {
      super(protocol);
   }

   @Override
   protected void registerPackets() {
      BlockRewriter<ClientboundPackets1_19> blockRewriter = BlockRewriter.for1_14(this.protocol);
      new RecipeRewriter<>(this.protocol).register(ClientboundPackets1_19.DECLARE_RECIPES);
      this.registerSetCooldown(ClientboundPackets1_19.COOLDOWN);
      this.registerWindowItems1_17_1(ClientboundPackets1_19.WINDOW_ITEMS);
      this.registerSetSlot1_17_1(ClientboundPackets1_19.SET_SLOT);
      this.registerEntityEquipmentArray(ClientboundPackets1_19.ENTITY_EQUIPMENT);
      this.registerAdvancements(ClientboundPackets1_19.ADVANCEMENTS, Type.ITEM1_13_2);
      this.registerClickWindow1_17_1(ServerboundPackets1_17.CLICK_WINDOW);
      blockRewriter.registerBlockAction(ClientboundPackets1_19.BLOCK_ACTION);
      blockRewriter.registerBlockChange(ClientboundPackets1_19.BLOCK_CHANGE);
      blockRewriter.registerVarLongMultiBlockChange(ClientboundPackets1_19.MULTI_BLOCK_CHANGE);
      blockRewriter.registerEffect(ClientboundPackets1_19.EFFECT, 1010, 2001);
      this.registerCreativeInvAction(ServerboundPackets1_17.CREATIVE_INVENTORY_ACTION, Type.ITEM1_13_2);
      this.protocol.registerClientbound(ClientboundPackets1_19.TRADE_LIST, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.handler(wrapper -> {
               int size = wrapper.read(Type.VAR_INT);
               wrapper.write(Type.UNSIGNED_BYTE, (short)size);

               for (int i = 0; i < size; i++) {
                  BlockItemPackets1_19.this.handleItemToClient(wrapper.passthrough(Type.ITEM1_13_2));
                  BlockItemPackets1_19.this.handleItemToClient(wrapper.passthrough(Type.ITEM1_13_2));
                  Item secondItem = wrapper.read(Type.ITEM1_13_2);
                  if (secondItem != null) {
                     BlockItemPackets1_19.this.handleItemToClient(secondItem);
                     wrapper.write(Type.BOOLEAN, true);
                     wrapper.write(Type.ITEM1_13_2, secondItem);
                  } else {
                     wrapper.write(Type.BOOLEAN, false);
                  }

                  wrapper.passthrough(Type.BOOLEAN);
                  wrapper.passthrough(Type.INT);
                  wrapper.passthrough(Type.INT);
                  wrapper.passthrough(Type.INT);
                  wrapper.passthrough(Type.INT);
                  wrapper.passthrough(Type.FLOAT);
                  wrapper.passthrough(Type.INT);
               }
            });
         }
      });
      this.registerWindowPropertyEnchantmentHandler(ClientboundPackets1_19.WINDOW_PROPERTY);
      this.protocol.registerClientbound(ClientboundPackets1_19.BLOCK_CHANGED_ACK, null, new PacketHandlers() {
         @Override
         public void register() {
            this.read(Type.VAR_INT);
            this.handler(PacketWrapper::cancel);
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_19.SPAWN_PARTICLE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT, Type.INT);
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
               int id = wrapper.get(Type.INT, 0);
               ParticleMappings particleMappings = BlockItemPackets1_19.this.protocol.getMappingData().getParticleMappings();
               if (id == particleMappings.id("sculk_charge")) {
                  wrapper.set(Type.INT, 0, -1);
                  wrapper.cancel();
               } else if (id == particleMappings.id("shriek")) {
                  wrapper.set(Type.INT, 0, -1);
                  wrapper.cancel();
               } else if (id == particleMappings.id("vibration")) {
                  wrapper.set(Type.INT, 0, -1);
                  wrapper.cancel();
               }
            });
            this.handler(BlockItemPackets1_19.this.getSpawnParticleHandler());
         }
      });
      this.protocol
         .registerClientbound(
            ClientboundPackets1_19.CHUNK_DATA,
            wrapper -> {
               EntityTracker tracker = this.protocol.getEntityRewriter().tracker(wrapper.user());
               ChunkType1_18 chunkType = new ChunkType1_18(
                  tracker.currentWorldSectionHeight(),
                  MathUtil.ceilLog2(this.protocol.getMappingData().getBlockStateMappings().mappedSize()),
                  MathUtil.ceilLog2(tracker.biomesSent())
               );
               Chunk chunk = wrapper.passthrough(chunkType);

               for (ChunkSection section : chunk.getSections()) {
                  DataPalette blockPalette = section.palette(PaletteType.BLOCKS);

                  for (int i = 0; i < blockPalette.size(); i++) {
                     int id = blockPalette.idByIndex(i);
                     blockPalette.setIdByIndex(i, this.protocol.getMappingData().getNewBlockStateId(id));
                  }
               }
            }
         );
      this.protocol.registerServerbound(ServerboundPackets1_17.PLAYER_DIGGING, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.POSITION1_14);
            this.map(Type.UNSIGNED_BYTE);
            this.create(Type.VAR_INT, Integer.valueOf(0));
         }
      });
      this.protocol.registerServerbound(ServerboundPackets1_17.PLAYER_BLOCK_PLACEMENT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.POSITION1_14);
            this.map(Type.VAR_INT);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.BOOLEAN);
            this.create(Type.VAR_INT, Integer.valueOf(0));
         }
      });
      this.protocol.registerServerbound(ServerboundPackets1_17.USE_ITEM, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.create(Type.VAR_INT, Integer.valueOf(0));
         }
      });
      this.protocol.registerServerbound(ServerboundPackets1_17.SET_BEACON_EFFECT, wrapper -> {
         int primaryEffect = wrapper.read(Type.VAR_INT);
         if (primaryEffect != -1) {
            wrapper.write(Type.BOOLEAN, true);
            wrapper.write(Type.VAR_INT, primaryEffect);
         } else {
            wrapper.write(Type.BOOLEAN, false);
         }

         int secondaryEffect = wrapper.read(Type.VAR_INT);
         if (secondaryEffect != -1) {
            wrapper.write(Type.BOOLEAN, true);
            wrapper.write(Type.VAR_INT, secondaryEffect);
         } else {
            wrapper.write(Type.BOOLEAN, false);
         }
      });
   }
}
