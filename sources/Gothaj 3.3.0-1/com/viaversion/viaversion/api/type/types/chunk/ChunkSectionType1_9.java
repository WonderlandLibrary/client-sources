package com.viaversion.viaversion.api.type.types.chunk;

import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionImpl;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.util.CompactArrayUtil;
import io.netty.buffer.ByteBuf;

public class ChunkSectionType1_9 extends Type<ChunkSection> {
   private static final int GLOBAL_PALETTE = 13;

   public ChunkSectionType1_9() {
      super(ChunkSection.class);
   }

   public ChunkSection read(ByteBuf buffer) throws Exception {
      int bitsPerBlock = buffer.readUnsignedByte();
      if (bitsPerBlock < 4) {
         bitsPerBlock = 4;
      }

      if (bitsPerBlock > 8) {
         bitsPerBlock = 13;
      }

      int paletteLength = Type.VAR_INT.readPrimitive(buffer);
      ChunkSection chunkSection = bitsPerBlock != 13 ? new ChunkSectionImpl(true, paletteLength) : new ChunkSectionImpl(true);
      DataPalette blockPalette = chunkSection.palette(PaletteType.BLOCKS);

      for (int i = 0; i < paletteLength; i++) {
         if (bitsPerBlock != 13) {
            blockPalette.addId(Type.VAR_INT.readPrimitive(buffer));
         } else {
            Type.VAR_INT.readPrimitive(buffer);
         }
      }

      long[] blockData = Type.LONG_ARRAY_PRIMITIVE.read(buffer);
      if (blockData.length > 0) {
         int expectedLength = (int)Math.ceil((double)(4096 * bitsPerBlock) / 64.0);
         if (blockData.length == expectedLength) {
            CompactArrayUtil.iterateCompactArray(bitsPerBlock, 4096, blockData, bitsPerBlock == 13 ? blockPalette::setIdAt : blockPalette::setPaletteIndexAt);
         }
      }

      return chunkSection;
   }

   public void write(ByteBuf buffer, ChunkSection chunkSection) throws Exception {
      int bitsPerBlock = 4;
      DataPalette blockPalette = chunkSection.palette(PaletteType.BLOCKS);

      while (blockPalette.size() > 1 << bitsPerBlock) {
         bitsPerBlock++;
      }

      if (bitsPerBlock > 8) {
         bitsPerBlock = 13;
      }

      buffer.writeByte(bitsPerBlock);
      if (bitsPerBlock != 13) {
         Type.VAR_INT.writePrimitive(buffer, blockPalette.size());

         for (int i = 0; i < blockPalette.size(); i++) {
            Type.VAR_INT.writePrimitive(buffer, blockPalette.idByIndex(i));
         }
      } else {
         Type.VAR_INT.writePrimitive(buffer, 0);
      }

      long[] data = CompactArrayUtil.createCompactArray(bitsPerBlock, 4096, bitsPerBlock == 13 ? blockPalette::idAt : blockPalette::paletteIndexAt);
      Type.LONG_ARRAY_PRIMITIVE.write(buffer, data);
   }
}
