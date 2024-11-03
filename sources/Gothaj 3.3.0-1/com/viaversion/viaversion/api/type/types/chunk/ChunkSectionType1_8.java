package com.viaversion.viaversion.api.type.types.chunk;

import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionImpl;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import java.nio.ByteOrder;

public class ChunkSectionType1_8 extends Type<ChunkSection> {
   public ChunkSectionType1_8() {
      super(ChunkSection.class);
   }

   public ChunkSection read(ByteBuf buffer) throws Exception {
      ChunkSection chunkSection = new ChunkSectionImpl(true);
      DataPalette blocks = chunkSection.palette(PaletteType.BLOCKS);
      blocks.addId(0);
      ByteBuf littleEndianView = buffer.order(ByteOrder.LITTLE_ENDIAN);

      for (int idx = 0; idx < 4096; idx++) {
         blocks.setIdAt(idx, littleEndianView.readUnsignedShort());
      }

      return chunkSection;
   }

   public void write(ByteBuf buffer, ChunkSection chunkSection) throws Exception {
      DataPalette blocks = chunkSection.palette(PaletteType.BLOCKS);
      ByteBuf littleEndianView = buffer.order(ByteOrder.LITTLE_ENDIAN);

      for (int idx = 0; idx < 4096; idx++) {
         littleEndianView.writeShort(blocks.idAt(idx));
      }
   }
}
