package com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.packets;

import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord1_16_2;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_16;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.Protocol1_16_2To1_16_1;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import java.util.ArrayList;
import java.util.List;

public class WorldPackets {
   private static final BlockChangeRecord[] EMPTY_RECORDS = new BlockChangeRecord[0];

   public static void register(Protocol1_16_2To1_16_1 protocol) {
      BlockRewriter<ClientboundPackets1_16> blockRewriter = BlockRewriter.for1_14(protocol);
      blockRewriter.registerBlockAction(ClientboundPackets1_16.BLOCK_ACTION);
      blockRewriter.registerBlockChange(ClientboundPackets1_16.BLOCK_CHANGE);
      blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_16.ACKNOWLEDGE_PLAYER_DIGGING);
      protocol.registerClientbound(ClientboundPackets1_16.CHUNK_DATA, wrapper -> {
         Chunk chunk = wrapper.read(ChunkType1_16.TYPE);
         wrapper.write(ChunkType1_16_2.TYPE, chunk);

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
      });
      protocol.registerClientbound(ClientboundPackets1_16.MULTI_BLOCK_CHANGE, wrapper -> {
         wrapper.cancel();
         int chunkX = wrapper.read(Type.INT);
         int chunkZ = wrapper.read(Type.INT);
         long chunkPosition = 0L;
         chunkPosition |= ((long)chunkX & 4194303L) << 42;
         chunkPosition |= ((long)chunkZ & 4194303L) << 20;
         List<BlockChangeRecord>[] sectionRecords = new List[16];
         BlockChangeRecord[] blockChangeRecord = wrapper.read(Type.BLOCK_CHANGE_RECORD_ARRAY);

         for (BlockChangeRecord record : blockChangeRecord) {
            int chunkY = record.getY() >> 4;
            List<BlockChangeRecord> list = sectionRecords[chunkY];
            if (list == null) {
               sectionRecords[chunkY] = list = new ArrayList<>();
            }

            int blockId = protocol.getMappingData().getNewBlockStateId(record.getBlockId());
            list.add(new BlockChangeRecord1_16_2(record.getSectionX(), record.getSectionY(), record.getSectionZ(), blockId));
         }

         for (int chunkY = 0; chunkY < sectionRecords.length; chunkY++) {
            List<BlockChangeRecord> sectionRecord = sectionRecords[chunkY];
            if (sectionRecord != null) {
               PacketWrapper newPacket = wrapper.create(ClientboundPackets1_16_2.MULTI_BLOCK_CHANGE);
               newPacket.write(Type.LONG, chunkPosition | (long)chunkY & 1048575L);
               newPacket.write(Type.BOOLEAN, false);
               newPacket.write(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY, sectionRecord.toArray(EMPTY_RECORDS));
               newPacket.send(Protocol1_16_2To1_16_1.class);
            }
         }
      });
      blockRewriter.registerEffect(ClientboundPackets1_16.EFFECT, 1010, 2001);
   }
}
