package com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
import com.viaversion.viabackwards.api.rewriters.MapColorRewriter;
import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.Protocol1_16_4To1_17;
import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.data.MapColorRewrites;
import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.storage.PingRequests;
import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.storage.PlayerLastCursorItem;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_16_2;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_17;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ServerboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ClientboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;
import com.viaversion.viaversion.util.CompactArrayUtil;
import com.viaversion.viaversion.util.MathUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

public final class BlockItemPackets1_17 extends ItemRewriter<ClientboundPackets1_17, ServerboundPackets1_16_2, Protocol1_16_4To1_17> {
   public BlockItemPackets1_17(Protocol1_16_4To1_17 protocol) {
      super(protocol);
   }

   @Override
   protected void registerPackets() {
      BlockRewriter<ClientboundPackets1_17> blockRewriter = BlockRewriter.for1_14(this.protocol);
      new RecipeRewriter<>(this.protocol).register(ClientboundPackets1_17.DECLARE_RECIPES);
      this.registerSetCooldown(ClientboundPackets1_17.COOLDOWN);
      this.registerWindowItems(ClientboundPackets1_17.WINDOW_ITEMS, Type.ITEM1_13_2_SHORT_ARRAY);
      this.registerEntityEquipmentArray(ClientboundPackets1_17.ENTITY_EQUIPMENT);
      this.registerTradeList(ClientboundPackets1_17.TRADE_LIST);
      this.registerAdvancements(ClientboundPackets1_17.ADVANCEMENTS, Type.ITEM1_13_2);
      blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_17.ACKNOWLEDGE_PLAYER_DIGGING);
      blockRewriter.registerBlockAction(ClientboundPackets1_17.BLOCK_ACTION);
      blockRewriter.registerEffect(ClientboundPackets1_17.EFFECT, 1010, 2001);
      this.registerCreativeInvAction(ServerboundPackets1_16_2.CREATIVE_INVENTORY_ACTION, Type.ITEM1_13_2);
      this.protocol.registerServerbound(ServerboundPackets1_16_2.EDIT_BOOK, wrapper -> this.handleItemToServer(wrapper.passthrough(Type.ITEM1_13_2)));
      this.protocol.registerServerbound(ServerboundPackets1_16_2.CLICK_WINDOW, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.UNSIGNED_BYTE);
            this.handler(wrapper -> {
               short slot = wrapper.passthrough(Type.SHORT);
               byte button = wrapper.passthrough(Type.BYTE);
               wrapper.read(Type.SHORT);
               int mode = wrapper.passthrough(Type.VAR_INT);
               Item clicked = BlockItemPackets1_17.this.handleItemToServer(wrapper.read(Type.ITEM1_13_2));
               wrapper.write(Type.VAR_INT, 0);
               PlayerLastCursorItem state = wrapper.user().get(PlayerLastCursorItem.class);
               if (mode == 0 && button == 0 && clicked != null) {
                  state.setLastCursorItem(clicked);
               } else if (mode == 0 && button == 1 && clicked != null) {
                  if (state.isSet()) {
                     state.setLastCursorItem(clicked);
                  } else {
                     state.setLastCursorItem(clicked, (clicked.amount() + 1) / 2);
                  }
               } else if (mode != 5 || slot != -999 || button != 0 && button != 4) {
                  state.setLastCursorItem(null);
               }

               Item carried = state.getLastCursorItem();
               if (carried == null) {
                  wrapper.write(Type.ITEM1_13_2, clicked);
               } else {
                  wrapper.write(Type.ITEM1_13_2, carried);
               }
            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_17.SET_SLOT, wrapper -> {
         short windowId = wrapper.passthrough(Type.UNSIGNED_BYTE);
         short slot = wrapper.passthrough(Type.SHORT);
         Item carried = wrapper.read(Type.ITEM1_13_2);
         if (carried != null && windowId == -1 && slot == -1) {
            wrapper.user().get(PlayerLastCursorItem.class).setLastCursorItem(carried);
         }

         wrapper.write(Type.ITEM1_13_2, this.handleItemToClient(carried));
      });
      this.protocol.registerServerbound(ServerboundPackets1_16_2.WINDOW_CONFIRMATION, null, wrapper -> {
         wrapper.cancel();
         if (ViaBackwards.getConfig().handlePingsAsInvAcknowledgements()) {
            short inventoryId = wrapper.read(Type.UNSIGNED_BYTE);
            short confirmationId = wrapper.read(Type.SHORT);
            boolean accepted = wrapper.read(Type.BOOLEAN);
            if (inventoryId == 0 && accepted && wrapper.user().get(PingRequests.class).removeId(confirmationId)) {
               PacketWrapper pongPacket = wrapper.create(ServerboundPackets1_17.PONG);
               pongPacket.write(Type.INT, Integer.valueOf(confirmationId));
               pongPacket.sendToServer(Protocol1_16_4To1_17.class);
            }
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_17.SPAWN_PARTICLE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
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
               if (id == 16) {
                  wrapper.passthrough(Type.FLOAT);
                  wrapper.passthrough(Type.FLOAT);
                  wrapper.passthrough(Type.FLOAT);
                  wrapper.passthrough(Type.FLOAT);
                  wrapper.read(Type.FLOAT);
                  wrapper.read(Type.FLOAT);
                  wrapper.read(Type.FLOAT);
               } else if (id == 37) {
                  wrapper.set(Type.INT, 0, -1);
                  wrapper.cancel();
               }
            });
            this.handler(BlockItemPackets1_17.this.getSpawnParticleHandler());
         }
      });
      this.protocol.mergePacket(ClientboundPackets1_17.WORLD_BORDER_SIZE, ClientboundPackets1_16_2.WORLD_BORDER, 0);
      this.protocol.mergePacket(ClientboundPackets1_17.WORLD_BORDER_LERP_SIZE, ClientboundPackets1_16_2.WORLD_BORDER, 1);
      this.protocol.mergePacket(ClientboundPackets1_17.WORLD_BORDER_CENTER, ClientboundPackets1_16_2.WORLD_BORDER, 2);
      this.protocol.mergePacket(ClientboundPackets1_17.WORLD_BORDER_INIT, ClientboundPackets1_16_2.WORLD_BORDER, 3);
      this.protocol.mergePacket(ClientboundPackets1_17.WORLD_BORDER_WARNING_DELAY, ClientboundPackets1_16_2.WORLD_BORDER, 4);
      this.protocol.mergePacket(ClientboundPackets1_17.WORLD_BORDER_WARNING_DISTANCE, ClientboundPackets1_16_2.WORLD_BORDER, 5);
      this.protocol.registerClientbound(ClientboundPackets1_17.UPDATE_LIGHT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.map(Type.BOOLEAN);
            this.handler(wrapper -> {
               EntityTracker tracker = wrapper.user().getEntityTracker(Protocol1_16_4To1_17.class);
               int startFromSection = Math.max(0, -(tracker.currentMinY() >> 4));
               long[] skyLightMask = wrapper.read(Type.LONG_ARRAY_PRIMITIVE);
               long[] blockLightMask = wrapper.read(Type.LONG_ARRAY_PRIMITIVE);
               int cutSkyLightMask = BlockItemPackets1_17.this.cutLightMask(skyLightMask, startFromSection);
               int cutBlockLightMask = BlockItemPackets1_17.this.cutLightMask(blockLightMask, startFromSection);
               wrapper.write(Type.VAR_INT, cutSkyLightMask);
               wrapper.write(Type.VAR_INT, cutBlockLightMask);
               long[] emptySkyLightMask = wrapper.read(Type.LONG_ARRAY_PRIMITIVE);
               long[] emptyBlockLightMask = wrapper.read(Type.LONG_ARRAY_PRIMITIVE);
               wrapper.write(Type.VAR_INT, BlockItemPackets1_17.this.cutLightMask(emptySkyLightMask, startFromSection));
               wrapper.write(Type.VAR_INT, BlockItemPackets1_17.this.cutLightMask(emptyBlockLightMask, startFromSection));
               this.writeLightArrays(wrapper, BitSet.valueOf(skyLightMask), cutSkyLightMask, startFromSection, tracker.currentWorldSectionHeight());
               this.writeLightArrays(wrapper, BitSet.valueOf(blockLightMask), cutBlockLightMask, startFromSection, tracker.currentWorldSectionHeight());
            });
         }

         private void writeLightArrays(PacketWrapper wrapper, BitSet bitMask, int cutBitMask, int startFromSection, int sectionHeight) throws Exception {
            wrapper.read(Type.VAR_INT);
            List<byte[]> light = new ArrayList<>();

            for (int i = 0; i < startFromSection; i++) {
               if (bitMask.get(i)) {
                  wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
               }
            }

            for (int ix = 0; ix < 18; ix++) {
               if (this.isSet(cutBitMask, ix)) {
                  light.add(wrapper.read(Type.BYTE_ARRAY_PRIMITIVE));
               }
            }

            for (int ixx = startFromSection + 18; ixx < sectionHeight + 2; ixx++) {
               if (bitMask.get(ixx)) {
                  wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
               }
            }

            for (byte[] bytes : light) {
               wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, bytes);
            }
         }

         private boolean isSet(int mask, int i) {
            return (mask & 1 << i) != 0;
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_17.MULTI_BLOCK_CHANGE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.LONG);
            this.map(Type.BOOLEAN);
            this.handler(wrapper -> {
               long chunkPos = wrapper.get(Type.LONG, 0);
               int chunkY = (int)(chunkPos << 44 >> 44);
               if (chunkY >= 0 && chunkY <= 15) {
                  BlockChangeRecord[] records = wrapper.passthrough(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY);

                  for (BlockChangeRecord record : records) {
                     record.setBlockId(BlockItemPackets1_17.this.protocol.getMappingData().getNewBlockStateId(record.getBlockId()));
                  }
               } else {
                  wrapper.cancel();
               }
            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_17.BLOCK_CHANGE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.POSITION1_14);
            this.map(Type.VAR_INT);
            this.handler(wrapper -> {
               int y = wrapper.get(Type.POSITION1_14, 0).y();
               if (y >= 0 && y <= 255) {
                  wrapper.set(Type.VAR_INT, 0, BlockItemPackets1_17.this.protocol.getMappingData().getNewBlockStateId(wrapper.get(Type.VAR_INT, 0)));
               } else {
                  wrapper.cancel();
               }
            });
         }
      });
      this.protocol
         .registerClientbound(
            ClientboundPackets1_17.CHUNK_DATA,
            wrapper -> {
               EntityTracker tracker = wrapper.user().getEntityTracker(Protocol1_16_4To1_17.class);
               int currentWorldSectionHeight = tracker.currentWorldSectionHeight();
               Chunk chunk = wrapper.read(new ChunkType1_17(currentWorldSectionHeight));
               wrapper.write(ChunkType1_16_2.TYPE, chunk);
               int startFromSection = Math.max(0, -(tracker.currentMinY() >> 4));
               chunk.setBiomeData(Arrays.copyOfRange(chunk.getBiomeData(), startFromSection * 64, startFromSection * 64 + 1024));
               chunk.setBitmask(this.cutMask(chunk.getChunkMask(), startFromSection, false));
               chunk.setChunkMask(null);
               ChunkSection[] sections = Arrays.copyOfRange(chunk.getSections(), startFromSection, startFromSection + 16);
               chunk.setSections(sections);
               CompoundTag heightMaps = chunk.getHeightMap();

               for (Tag heightMapTag : heightMaps.values()) {
                  LongArrayTag heightMap = (LongArrayTag)heightMapTag;
                  int[] heightMapData = new int[256];
                  int bitsPerEntry = MathUtil.ceilLog2((currentWorldSectionHeight << 4) + 1);
                  CompactArrayUtil.iterateCompactArrayWithPadding(
                     bitsPerEntry, heightMapData.length, heightMap.getValue(), (i, v) -> heightMapData[i] = MathUtil.clamp(v + tracker.currentMinY(), 0, 255)
                  );
                  heightMap.setValue(CompactArrayUtil.createCompactArrayWithPadding(9, heightMapData.length, i -> (long)heightMapData[i]));
               }

               for (int i = 0; i < 16; i++) {
                  ChunkSection section = sections[i];
                  if (section != null) {
                     DataPalette palette = section.palette(PaletteType.BLOCKS);

                     for (int j = 0; j < palette.size(); j++) {
                        int mappedBlockStateId = this.protocol.getMappingData().getNewBlockStateId(palette.idByIndex(j));
                        palette.setIdByIndex(j, mappedBlockStateId);
                     }
                  }
               }

               chunk.getBlockEntities().removeIf(compound -> {
                  NumberTag tag = compound.get("y");
                  return tag != null && (tag.asInt() < 0 || tag.asInt() > 255);
               });
            }
         );
      this.protocol.registerClientbound(ClientboundPackets1_17.BLOCK_ENTITY_DATA, wrapper -> {
         int y = wrapper.passthrough(Type.POSITION1_14).y();
         if (y < 0 || y > 255) {
            wrapper.cancel();
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_17.BLOCK_BREAK_ANIMATION, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.handler(wrapper -> {
               int y = wrapper.passthrough(Type.POSITION1_14).y();
               if (y < 0 || y > 255) {
                  wrapper.cancel();
               }
            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_17.MAP_DATA, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.BYTE);
            this.handler(wrapper -> wrapper.write(Type.BOOLEAN, true));
            this.map(Type.BOOLEAN);
            this.handler(wrapper -> {
               boolean hasMarkers = wrapper.read(Type.BOOLEAN);
               if (!hasMarkers) {
                  wrapper.write(Type.VAR_INT, 0);
               } else {
                  MapColorRewriter.getRewriteHandler(MapColorRewrites::getMappedColor).handle(wrapper);
               }
            });
         }
      });
   }

   private int cutLightMask(long[] mask, int startFromSection) {
      return mask.length == 0 ? 0 : this.cutMask(BitSet.valueOf(mask), startFromSection, true);
   }

   private int cutMask(BitSet mask, int startFromSection, boolean lightMask) {
      int cutMask = 0;
      int to = startFromSection + (lightMask ? 18 : 16);
      int i = startFromSection;

      for (int j = 0; i < to; j++) {
         if (mask.get(i)) {
            cutMask |= 1 << j;
         }

         i++;
      }

      return cutMask;
   }
}
