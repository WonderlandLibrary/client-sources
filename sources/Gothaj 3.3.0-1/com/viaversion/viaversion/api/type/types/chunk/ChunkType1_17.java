package com.viaversion.viaversion.api.type.types.chunk;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_16;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

public final class ChunkType1_17 extends Type<Chunk> {
   private static final CompoundTag[] EMPTY_COMPOUNDS = new CompoundTag[0];
   private final int ySectionCount;

   public ChunkType1_17(int ySectionCount) {
      super(Chunk.class);
      Preconditions.checkArgument(ySectionCount > 0);
      this.ySectionCount = ySectionCount;
   }

   public Chunk read(ByteBuf input) throws Exception {
      int chunkX = input.readInt();
      int chunkZ = input.readInt();
      BitSet sectionsMask = BitSet.valueOf(Type.LONG_ARRAY_PRIMITIVE.read(input));
      CompoundTag heightMap = Type.NAMED_COMPOUND_TAG.read(input);
      int[] biomeData = Type.VAR_INT_ARRAY_PRIMITIVE.read(input);
      ByteBuf data = input.readSlice(Type.VAR_INT.readPrimitive(input));
      ChunkSection[] sections = new ChunkSection[this.ySectionCount];

      for (int i = 0; i < this.ySectionCount; i++) {
         if (sectionsMask.get(i)) {
            short nonAirBlocksCount = data.readShort();
            ChunkSection section = Types1_16.CHUNK_SECTION.read(data);
            section.setNonAirBlocksCount(nonAirBlocksCount);
            sections[i] = section;
         }
      }

      List<CompoundTag> nbtData = new ArrayList<>(Arrays.asList(Type.NAMED_COMPOUND_TAG_ARRAY.read(input)));
      return new BaseChunk(chunkX, chunkZ, true, false, sectionsMask, sections, biomeData, heightMap, nbtData);
   }

   public void write(ByteBuf output, Chunk chunk) throws Exception {
      output.writeInt(chunk.getX());
      output.writeInt(chunk.getZ());
      Type.LONG_ARRAY_PRIMITIVE.write(output, chunk.getChunkMask().toLongArray());
      Type.NAMED_COMPOUND_TAG.write(output, chunk.getHeightMap());
      Type.VAR_INT_ARRAY_PRIMITIVE.write(output, chunk.getBiomeData());
      ByteBuf buf = output.alloc().buffer();

      try {
         ChunkSection[] sections = chunk.getSections();

         for (ChunkSection section : sections) {
            if (section != null) {
               buf.writeShort(section.getNonAirBlocksCount());
               Types1_16.CHUNK_SECTION.write(buf, section);
            }
         }

         buf.readerIndex(0);
         Type.VAR_INT.writePrimitive(output, buf.readableBytes());
         output.writeBytes(buf);
      } finally {
         buf.release();
      }

      Type.NAMED_COMPOUND_TAG_ARRAY.write(output, chunk.getBlockEntities().toArray(EMPTY_COMPOUNDS));
   }
}
