package com.viaversion.viaversion.protocols.protocol1_17to1_16_4.packets;

import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord1_16_2;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_16_2;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_17;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ClientboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.Protocol1_17To1_16_4;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public final class WorldPackets {
   public static void register(Protocol1_17To1_16_4 protocol) {
      BlockRewriter<ClientboundPackets1_16_2> blockRewriter = BlockRewriter.for1_14(protocol);
      blockRewriter.registerBlockAction(ClientboundPackets1_16_2.BLOCK_ACTION);
      blockRewriter.registerBlockChange(ClientboundPackets1_16_2.BLOCK_CHANGE);
      blockRewriter.registerVarLongMultiBlockChange(ClientboundPackets1_16_2.MULTI_BLOCK_CHANGE);
      blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_16_2.ACKNOWLEDGE_PLAYER_DIGGING);
      protocol.registerClientbound(ClientboundPackets1_16_2.WORLD_BORDER, null, wrapper -> {
         int type = wrapper.read(Type.VAR_INT);
         ClientboundPacketType packetType;
         switch (type) {
            case 0:
               packetType = ClientboundPackets1_17.WORLD_BORDER_SIZE;
               break;
            case 1:
               packetType = ClientboundPackets1_17.WORLD_BORDER_LERP_SIZE;
               break;
            case 2:
               packetType = ClientboundPackets1_17.WORLD_BORDER_CENTER;
               break;
            case 3:
               packetType = ClientboundPackets1_17.WORLD_BORDER_INIT;
               break;
            case 4:
               packetType = ClientboundPackets1_17.WORLD_BORDER_WARNING_DELAY;
               break;
            case 5:
               packetType = ClientboundPackets1_17.WORLD_BORDER_WARNING_DISTANCE;
               break;
            default:
               throw new IllegalArgumentException("Invalid world border type received: " + type);
         }

         wrapper.setPacketType(packetType);
      });
      protocol.registerClientbound(ClientboundPackets1_16_2.UPDATE_LIGHT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.map(Type.BOOLEAN);
            this.handler(wrapper -> {
               int skyLightMask = wrapper.read(Type.VAR_INT);
               int blockLightMask = wrapper.read(Type.VAR_INT);
               wrapper.write(Type.LONG_ARRAY_PRIMITIVE, this.toBitSetLongArray(skyLightMask));
               wrapper.write(Type.LONG_ARRAY_PRIMITIVE, this.toBitSetLongArray(blockLightMask));
               wrapper.write(Type.LONG_ARRAY_PRIMITIVE, this.toBitSetLongArray(wrapper.read(Type.VAR_INT)));
               wrapper.write(Type.LONG_ARRAY_PRIMITIVE, this.toBitSetLongArray(wrapper.read(Type.VAR_INT)));
               this.writeLightArrays(wrapper, skyLightMask);
               this.writeLightArrays(wrapper, blockLightMask);
            });
         }

         private void writeLightArrays(PacketWrapper wrapper, int bitMask) throws Exception {
            List<byte[]> light = new ArrayList<>();

            for (int i = 0; i < 18; i++) {
               if (this.isSet(bitMask, i)) {
                  light.add(wrapper.read(Type.BYTE_ARRAY_PRIMITIVE));
               }
            }

            wrapper.write(Type.VAR_INT, light.size());

            for (byte[] bytes : light) {
               wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, bytes);
            }
         }

         private long[] toBitSetLongArray(int bitmask) {
            return new long[]{(long)bitmask};
         }

         private boolean isSet(int mask, int i) {
            return (mask & 1 << i) != 0;
         }
      });
      protocol.registerClientbound(ClientboundPackets1_16_2.CHUNK_DATA, wrapper -> {
         Chunk chunk = wrapper.read(ChunkType1_16_2.TYPE);
         if (!chunk.isFullChunk()) {
            writeMultiBlockChangePacket(wrapper, chunk);
            wrapper.cancel();
         } else {
            wrapper.write(new ChunkType1_17(chunk.getSections().length), chunk);
            chunk.setChunkMask(BitSet.valueOf(new long[]{(long)chunk.getBitmask()}));

            for (int s = 0; s < chunk.getSections().length; s++) {
               ChunkSection section = chunk.getSections()[s];
               if (section != null) {
                  DataPalette palette = section.palette(PaletteType.BLOCKS);

                  for (int i = 0; i < palette.size(); i++) {
                     int mappedBlockStateId = protocol.getMappingData().getNewBlockStateId(palette.idByIndex(i));
                     palette.setIdByIndex(i, mappedBlockStateId);
                  }
               }
            }
         }
      });
      blockRewriter.registerEffect(ClientboundPackets1_16_2.EFFECT, 1010, 2001);
   }

   private static void writeMultiBlockChangePacket(PacketWrapper wrapper, Chunk chunk) throws Exception {
      long chunkPosition = ((long)chunk.getX() & 4194303L) << 42;
      chunkPosition |= ((long)chunk.getZ() & 4194303L) << 20;
      ChunkSection[] sections = chunk.getSections();

      for (int chunkY = 0; chunkY < sections.length; chunkY++) {
         ChunkSection section = sections[chunkY];
         if (section != null) {
            PacketWrapper blockChangePacket = wrapper.create(ClientboundPackets1_17.MULTI_BLOCK_CHANGE);
            blockChangePacket.write(Type.LONG, chunkPosition | (long)chunkY & 1048575L);
            blockChangePacket.write(Type.BOOLEAN, true);
            BlockChangeRecord[] blockChangeRecords = new BlockChangeRecord[4096];
            DataPalette palette = section.palette(PaletteType.BLOCKS);
            int j = 0;

            for (int x = 0; x < 16; x++) {
               for (int y = 0; y < 16; y++) {
                  for (int z = 0; z < 16; z++) {
                     int blockStateId = Protocol1_17To1_16_4.MAPPINGS.getNewBlockStateId(palette.idAt(x, y, z));
                     blockChangeRecords[j++] = new BlockChangeRecord1_16_2(x, y, z, blockStateId);
                  }
               }
            }

            blockChangePacket.write(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY, blockChangeRecords);
            blockChangePacket.send(Protocol1_17To1_16_4.class);
         }
      }
   }
}
