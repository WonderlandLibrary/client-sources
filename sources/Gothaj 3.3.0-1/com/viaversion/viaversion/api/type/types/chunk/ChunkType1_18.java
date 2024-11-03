package com.viaversion.viaversion.api.type.types.chunk;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk1_18;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;

public final class ChunkType1_18 extends Type<Chunk> {
   private final ChunkSectionType1_18 sectionType;
   private final int ySectionCount;

   public ChunkType1_18(int ySectionCount, int globalPaletteBlockBits, int globalPaletteBiomeBits) {
      super(Chunk.class);
      Preconditions.checkArgument(ySectionCount > 0);
      this.sectionType = new ChunkSectionType1_18(globalPaletteBlockBits, globalPaletteBiomeBits);
      this.ySectionCount = ySectionCount;
   }

   public Chunk read(ByteBuf buffer) throws Exception {
      int chunkX = buffer.readInt();
      int chunkZ = buffer.readInt();
      CompoundTag heightMap = Type.NAMED_COMPOUND_TAG.read(buffer);
      ByteBuf sectionsBuf = buffer.readBytes(Type.VAR_INT.readPrimitive(buffer));
      ChunkSection[] sections = new ChunkSection[this.ySectionCount];

      try {
         for (int i = 0; i < this.ySectionCount; i++) {
            sections[i] = this.sectionType.read(sectionsBuf);
         }
      } finally {
         sectionsBuf.release();
      }

      int var12 = Type.VAR_INT.readPrimitive(buffer);
      ArrayList blockEntities = new ArrayList(var12);

      for (int i = 0; i < var12; i++) {
         blockEntities.add(Type.BLOCK_ENTITY1_18.read(buffer));
      }

      return new Chunk1_18(chunkX, chunkZ, sections, heightMap, blockEntities);
   }

   public void write(ByteBuf buffer, Chunk chunk) throws Exception {
      buffer.writeInt(chunk.getX());
      buffer.writeInt(chunk.getZ());
      Type.NAMED_COMPOUND_TAG.write(buffer, chunk.getHeightMap());
      ByteBuf sectionBuffer = buffer.alloc().buffer();

      try {
         for (ChunkSection section : chunk.getSections()) {
            this.sectionType.write(sectionBuffer, section);
         }

         sectionBuffer.readerIndex(0);
         Type.VAR_INT.writePrimitive(buffer, sectionBuffer.readableBytes());
         buffer.writeBytes(sectionBuffer);
      } finally {
         sectionBuffer.release();
      }

      Type.VAR_INT.writePrimitive(buffer, chunk.blockEntities().size());

      for (BlockEntity blockEntity : chunk.blockEntities()) {
         Type.BLOCK_ENTITY1_18.write(buffer, blockEntity);
      }
   }
}
