package com.viaversion.viaversion.api.type.types.chunk;

import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionImpl;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;

public final class ChunkSectionType1_18 extends Type<ChunkSection> {
   private final PaletteType1_18 blockPaletteType;
   private final PaletteType1_18 biomePaletteType;

   public ChunkSectionType1_18(int globalPaletteBlockBits, int globalPaletteBiomeBits) {
      super(ChunkSection.class);
      this.blockPaletteType = new PaletteType1_18(PaletteType.BLOCKS, globalPaletteBlockBits);
      this.biomePaletteType = new PaletteType1_18(PaletteType.BIOMES, globalPaletteBiomeBits);
   }

   public ChunkSection read(ByteBuf buffer) throws Exception {
      ChunkSection chunkSection = new ChunkSectionImpl();
      chunkSection.setNonAirBlocksCount(buffer.readShort());
      chunkSection.addPalette(PaletteType.BLOCKS, this.blockPaletteType.read(buffer));
      chunkSection.addPalette(PaletteType.BIOMES, this.biomePaletteType.read(buffer));
      return chunkSection;
   }

   public void write(ByteBuf buffer, ChunkSection section) throws Exception {
      buffer.writeShort(section.getNonAirBlocksCount());
      this.blockPaletteType.write(buffer, section.palette(PaletteType.BLOCKS));
      this.biomePaletteType.write(buffer, section.palette(PaletteType.BIOMES));
   }
}
