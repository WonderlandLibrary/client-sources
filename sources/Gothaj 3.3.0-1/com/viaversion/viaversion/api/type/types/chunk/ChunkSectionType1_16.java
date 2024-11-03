package com.viaversion.viaversion.api.type.types.chunk;

import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionImpl;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.util.CompactArrayUtil;
import io.netty.buffer.ByteBuf;

public class ChunkSectionType1_16 extends Type<ChunkSection> {
   private static final int GLOBAL_PALETTE = 15;

   public ChunkSectionType1_16() {
      super(ChunkSection.class);
   }

   public ChunkSection read(ByteBuf buffer) throws Exception {
      int bitsPerBlock = buffer.readUnsignedByte();
      if (bitsPerBlock > 8) {
         bitsPerBlock = 15;
      } else if (bitsPerBlock < 4) {
         bitsPerBlock = 4;
      }

      ChunkSection chunkSection;
      if (bitsPerBlock != 15) {
         int paletteLength = Type.VAR_INT.readPrimitive(buffer);
         chunkSection = new ChunkSectionImpl(false, paletteLength);
         DataPalette blockPalette = chunkSection.palette(PaletteType.BLOCKS);

         for (int i = 0; i < paletteLength; i++) {
            blockPalette.addId(Type.VAR_INT.readPrimitive(buffer));
         }
      } else {
         chunkSection = new ChunkSectionImpl(false);
      }

      long[] blockData = Type.LONG_ARRAY_PRIMITIVE.read(buffer);
      if (blockData.length > 0) {
         char valuesPerLong = (char)(64 / bitsPerBlock);
         int expectedLength = (4096 + valuesPerLong - 1) / valuesPerLong;
         if (blockData.length == expectedLength) {
            DataPalette blockPalette = chunkSection.palette(PaletteType.BLOCKS);
            CompactArrayUtil.iterateCompactArrayWithPadding(
               bitsPerBlock, 4096, blockData, bitsPerBlock == 15 ? blockPalette::setIdAt : blockPalette::setPaletteIndexAt
            );
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
         bitsPerBlock = 15;
      }

      buffer.writeByte(bitsPerBlock);
      if (bitsPerBlock != 15) {
         Type.VAR_INT.writePrimitive(buffer, blockPalette.size());

         for (int i = 0; i < blockPalette.size(); i++) {
            Type.VAR_INT.writePrimitive(buffer, blockPalette.idByIndex(i));
         }
      }

      long[] data = CompactArrayUtil.createCompactArrayWithPadding(bitsPerBlock, 4096, bitsPerBlock == 15 ? blockPalette::idAt : blockPalette::paletteIndexAt);
      Type.LONG_ARRAY_PRIMITIVE.write(buffer, data);
   }
}
