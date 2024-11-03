package com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.packets;

import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.Protocol1_16_1To1_16_2;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord1_8;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_16;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_16_2;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ServerboundPackets1_16;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;

public class BlockItemPackets1_16_2 extends ItemRewriter<ClientboundPackets1_16_2, ServerboundPackets1_16, Protocol1_16_1To1_16_2> {
   public BlockItemPackets1_16_2(Protocol1_16_1To1_16_2 protocol) {
      super(protocol);
   }

   @Override
   protected void registerPackets() {
      BlockRewriter<ClientboundPackets1_16_2> blockRewriter = BlockRewriter.for1_14(this.protocol);
      new RecipeRewriter<>(this.protocol).register(ClientboundPackets1_16_2.DECLARE_RECIPES);
      this.registerSetCooldown(ClientboundPackets1_16_2.COOLDOWN);
      this.registerWindowItems(ClientboundPackets1_16_2.WINDOW_ITEMS, Type.ITEM1_13_2_SHORT_ARRAY);
      this.registerSetSlot(ClientboundPackets1_16_2.SET_SLOT, Type.ITEM1_13_2);
      this.registerEntityEquipmentArray(ClientboundPackets1_16_2.ENTITY_EQUIPMENT);
      this.registerTradeList(ClientboundPackets1_16_2.TRADE_LIST);
      this.registerAdvancements(ClientboundPackets1_16_2.ADVANCEMENTS, Type.ITEM1_13_2);
      this.protocol.registerClientbound(ClientboundPackets1_16_2.UNLOCK_RECIPES, wrapper -> {
         wrapper.passthrough(Type.VAR_INT);
         wrapper.passthrough(Type.BOOLEAN);
         wrapper.passthrough(Type.BOOLEAN);
         wrapper.passthrough(Type.BOOLEAN);
         wrapper.passthrough(Type.BOOLEAN);
         wrapper.read(Type.BOOLEAN);
         wrapper.read(Type.BOOLEAN);
         wrapper.read(Type.BOOLEAN);
         wrapper.read(Type.BOOLEAN);
      });
      blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_16_2.ACKNOWLEDGE_PLAYER_DIGGING);
      blockRewriter.registerBlockAction(ClientboundPackets1_16_2.BLOCK_ACTION);
      blockRewriter.registerBlockChange(ClientboundPackets1_16_2.BLOCK_CHANGE);
      this.protocol.registerClientbound(ClientboundPackets1_16_2.CHUNK_DATA, wrapper -> {
         Chunk chunk = wrapper.read(ChunkType1_16_2.TYPE);
         wrapper.write(ChunkType1_16.TYPE, chunk);
         chunk.setIgnoreOldLightData(true);

         for (int i = 0; i < chunk.getSections().length; i++) {
            ChunkSection section = chunk.getSections()[i];
            if (section != null) {
               DataPalette palette = section.palette(PaletteType.BLOCKS);

               for (int j = 0; j < palette.size(); j++) {
                  int mappedBlockStateId = this.protocol.getMappingData().getNewBlockStateId(palette.idByIndex(j));
                  palette.setIdByIndex(j, mappedBlockStateId);
               }
            }
         }

         for (CompoundTag blockEntity : chunk.getBlockEntities()) {
            if (blockEntity != null) {
               this.handleBlockEntity(blockEntity);
            }
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_16_2.BLOCK_ENTITY_DATA, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.POSITION1_14);
            this.map(Type.UNSIGNED_BYTE);
            this.handler(wrapper -> BlockItemPackets1_16_2.this.handleBlockEntity(wrapper.passthrough(Type.NAMED_COMPOUND_TAG)));
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_16_2.MULTI_BLOCK_CHANGE, wrapper -> {
         long chunkPosition = wrapper.read(Type.LONG);
         wrapper.read(Type.BOOLEAN);
         int chunkX = (int)(chunkPosition >> 42);
         int chunkY = (int)(chunkPosition << 44 >> 44);
         int chunkZ = (int)(chunkPosition << 22 >> 42);
         wrapper.write(Type.INT, chunkX);
         wrapper.write(Type.INT, chunkZ);
         BlockChangeRecord[] blockChangeRecord = wrapper.read(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY);
         wrapper.write(Type.BLOCK_CHANGE_RECORD_ARRAY, blockChangeRecord);

         for (int i = 0; i < blockChangeRecord.length; i++) {
            BlockChangeRecord record = blockChangeRecord[i];
            int blockId = this.protocol.getMappingData().getNewBlockStateId(record.getBlockId());
            blockChangeRecord[i] = new BlockChangeRecord1_8(record.getSectionX(), record.getY(chunkY), record.getSectionZ(), blockId);
         }
      });
      blockRewriter.registerEffect(ClientboundPackets1_16_2.EFFECT, 1010, 2001);
      this.registerSpawnParticle(ClientboundPackets1_16_2.SPAWN_PARTICLE, Type.ITEM1_13_2, Type.DOUBLE);
      this.registerClickWindow(ServerboundPackets1_16.CLICK_WINDOW, Type.ITEM1_13_2);
      this.registerCreativeInvAction(ServerboundPackets1_16.CREATIVE_INVENTORY_ACTION, Type.ITEM1_13_2);
      this.protocol.registerServerbound(ServerboundPackets1_16.EDIT_BOOK, wrapper -> this.handleItemToServer(wrapper.passthrough(Type.ITEM1_13_2)));
   }

   private void handleBlockEntity(CompoundTag tag) {
      StringTag idTag = tag.get("id");
      if (idTag != null) {
         if (idTag.getValue().equals("minecraft:skull")) {
            Tag skullOwnerTag = tag.get("SkullOwner");
            if (!(skullOwnerTag instanceof CompoundTag)) {
               return;
            }

            CompoundTag skullOwnerCompoundTag = (CompoundTag)skullOwnerTag;
            if (!skullOwnerCompoundTag.contains("Id")) {
               return;
            }

            CompoundTag properties = skullOwnerCompoundTag.get("Properties");
            if (properties == null) {
               return;
            }

            ListTag textures = properties.get("textures");
            if (textures == null) {
               return;
            }

            CompoundTag first = textures.size() > 0 ? textures.get(0) : null;
            if (first == null) {
               return;
            }

            int hashCode = first.<Tag>get("Value").getValue().hashCode();
            int[] uuidIntArray = new int[]{hashCode, 0, 0, 0};
            skullOwnerCompoundTag.put("Id", new IntArrayTag(uuidIntArray));
         }
      }
   }
}
