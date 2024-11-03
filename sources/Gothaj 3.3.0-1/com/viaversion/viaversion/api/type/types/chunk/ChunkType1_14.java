package com.viaversion.viaversion.api.type.types.chunk;

import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_13;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChunkType1_14 extends Type<Chunk> {
   public static final Type<Chunk> TYPE = new ChunkType1_14();

   public ChunkType1_14() {
      super(Chunk.class);
   }

   public Chunk read(ByteBuf input) throws Exception {
      int chunkX = input.readInt();
      int chunkZ = input.readInt();
      boolean fullChunk = input.readBoolean();
      int primaryBitmask = Type.VAR_INT.readPrimitive(input);
      CompoundTag heightMap = Type.NAMED_COMPOUND_TAG.read(input);
      ByteBuf data = input.readSlice(Type.VAR_INT.readPrimitive(input));
      ChunkSection[] sections = new ChunkSection[16];

      for (int i = 0; i < 16; i++) {
         if ((primaryBitmask & 1 << i) != 0) {
            short nonAirBlocksCount = data.readShort();
            ChunkSection section = Types1_13.CHUNK_SECTION.read(data);
            section.setNonAirBlocksCount(nonAirBlocksCount);
            sections[i] = section;
         }
      }

      int[] biomeData = fullChunk ? new int[256] : null;
      if (fullChunk) {
         for (int ix = 0; ix < 256; ix++) {
            biomeData[ix] = data.readInt();
         }
      }

      List<CompoundTag> nbtData = new ArrayList<>(Arrays.asList(Type.NAMED_COMPOUND_TAG_ARRAY.read(input)));
      return new BaseChunk(chunkX, chunkZ, fullChunk, false, primaryBitmask, sections, biomeData, heightMap, nbtData);
   }

   public void write(ByteBuf output, Chunk chunk) throws Exception {
      output.writeInt(chunk.getX());
      output.writeInt(chunk.getZ());
      output.writeBoolean(chunk.isFullChunk());
      Type.VAR_INT.writePrimitive(output, chunk.getBitmask());
      Type.NAMED_COMPOUND_TAG.write(output, chunk.getHeightMap());
      ByteBuf buf = output.alloc().buffer();

      try {
         for (int i = 0; i < 16; i++) {
            ChunkSection section = chunk.getSections()[i];
            if (section != null) {
               buf.writeShort(section.getNonAirBlocksCount());
               Types1_13.CHUNK_SECTION.write(buf, section);
            }
         }

         buf.readerIndex(0);
         Type.VAR_INT.writePrimitive(output, buf.readableBytes() + (chunk.isBiomeData() ? 1024 : 0));
         output.writeBytes(buf);
      } finally {
         buf.release();
      }

      if (chunk.isBiomeData()) {
         for (int value : chunk.getBiomeData()) {
            output.writeInt(value & 0xFF);
         }
      }

      Type.NAMED_COMPOUND_TAG_ARRAY.write(output, chunk.getBlockEntities().toArray(new CompoundTag[0]));
   }
}
