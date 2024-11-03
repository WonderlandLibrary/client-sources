package com.viaversion.viaversion.api.type.types.chunk;

import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.DataPaletteImpl;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.util.CompactArrayUtil;
import com.viaversion.viaversion.util.MathUtil;
import io.netty.buffer.ByteBuf;

public final class PaletteType1_18 extends Type<DataPalette> {
   private final int globalPaletteBits;
   private final PaletteType type;

   public PaletteType1_18(PaletteType type, int globalPaletteBits) {
      super(DataPalette.class);
      this.globalPaletteBits = globalPaletteBits;
      this.type = type;
   }

   public DataPalette read(ByteBuf buffer) throws Exception {
      int bitsPerValue = buffer.readByte();
      if (bitsPerValue == 0) {
         DataPaletteImpl palette = new DataPaletteImpl(this.type.size(), 1);
         palette.addId(Type.VAR_INT.readPrimitive(buffer));
         Type.LONG_ARRAY_PRIMITIVE.read(buffer);
         return palette;
      } else {
         if (bitsPerValue < 0 || bitsPerValue > this.type.highestBitsPerValue()) {
            bitsPerValue = this.globalPaletteBits;
         } else if (this.type == PaletteType.BLOCKS && bitsPerValue < 4) {
            bitsPerValue = 4;
         }

         DataPaletteImpl palette;
         if (bitsPerValue != this.globalPaletteBits) {
            int paletteLength = Type.VAR_INT.readPrimitive(buffer);
            palette = new DataPaletteImpl(this.type.size(), paletteLength);

            for (int i = 0; i < paletteLength; i++) {
               palette.addId(Type.VAR_INT.readPrimitive(buffer));
            }
         } else {
            palette = new DataPaletteImpl(this.type.size());
         }

         long[] values = Type.LONG_ARRAY_PRIMITIVE.read(buffer);
         if (values.length > 0) {
            int valuesPerLong = (char)(64 / bitsPerValue);
            int expectedLength = (this.type.size() + valuesPerLong - 1) / valuesPerLong;
            if (values.length == expectedLength) {
               CompactArrayUtil.iterateCompactArrayWithPadding(
                  bitsPerValue, this.type.size(), values, bitsPerValue == this.globalPaletteBits ? palette::setIdAt : palette::setPaletteIndexAt
               );
            }
         }

         return palette;
      }
   }

   public void write(ByteBuf buffer, DataPalette palette) throws Exception {
      int size = palette.size();
      if (size == 1) {
         buffer.writeByte(0);
         Type.VAR_INT.writePrimitive(buffer, palette.idByIndex(0));
         Type.VAR_INT.writePrimitive(buffer, 0);
      } else {
         int min = this.type == PaletteType.BLOCKS ? 4 : 1;
         int bitsPerValue = Math.max(min, MathUtil.ceilLog2(size));
         if (bitsPerValue > this.type.highestBitsPerValue()) {
            bitsPerValue = this.globalPaletteBits;
         }

         buffer.writeByte(bitsPerValue);
         if (bitsPerValue != this.globalPaletteBits) {
            Type.VAR_INT.writePrimitive(buffer, size);

            for (int i = 0; i < size; i++) {
               Type.VAR_INT.writePrimitive(buffer, palette.idByIndex(i));
            }
         }

         Type.LONG_ARRAY_PRIMITIVE
            .write(
               buffer,
               CompactArrayUtil.createCompactArrayWithPadding(
                  bitsPerValue, this.type.size(), bitsPerValue == this.globalPaletteBits ? palette::idAt : palette::paletteIndexAt
               )
            );
      }
   }
}
