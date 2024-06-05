package net;

import io.netty.buffer.ByteBuf;
import java.util.function.IntToLongFunction;
import net.aes;
import viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.util.BiIntConsumer;
import viaversion.viaversion.util.CompactArrayUtil;

public class wO extends Type {
   private static final int ah = 13;

   public wO() {
      super("Chunk Section Type", ChunkSection.class);
   }

   public ChunkSection a(ByteBuf var1) throws Exception {
      aes.b();
      short var3 = var1.readUnsignedByte();
      short var4 = var3;
      if(var3 == 0) {
         var3 = 13;
      }

      if(var3 < 4) {
         var3 = 4;
      }

      if(var3 > 8) {
         var3 = 13;
      }

      int var5 = Type.VAR_INT.readPrimitive(var1);
      ChunkSection var6 = var3 != 13?new ChunkSection(var5):new ChunkSection();
      int var7 = 0;
      if(var7 < var5) {
         if(var3 != 13) {
            var6.addPaletteEntry(Type.VAR_INT.readPrimitive(var1));
         }

         Type.VAR_INT.readPrimitive(var1);
         ++var7;
      }

      long[] var11 = new long[Type.VAR_INT.readPrimitive(var1)];
      if(var11.length > 0) {
         int var8 = (int)Math.ceil((double)(4096 * var3) / 64.0D);
         if(var11.length != var8) {
            throw new IllegalStateException("Block data length (" + var11.length + ") does not match expected length (" + var8 + ")! bitsPerBlock=" + var3 + ", originalBitsPerBlock=" + var4);
         }

         int var9 = 0;
         if(var9 < var11.length) {
            var11[var9] = var1.readLong();
            ++var9;
         }

         CompactArrayUtil.iterateCompactArray(var3, 4096, var11, var3 == 13?var6::setFlatBlock:var6::setPaletteIndex);
      }

      return var6;
   }

   public void a(ByteBuf var1, ChunkSection var2) throws Exception {
      aes.b();
      int var4 = 4;
      if(var2.getPaletteSize() > 1 << var4) {
         ++var4;
      }

      if(var4 > 8) {
         var4 = 13;
      }

      long var5 = (1L << var4) - 1L;
      var1.writeByte(var4);
      if(var4 != 13) {
         Type.VAR_INT.writePrimitive(var1, var2.getPaletteSize());
         int var7 = 0;
         if(var7 < var2.getPaletteSize()) {
            Type.VAR_INT.writePrimitive(var1, var2.getPaletteEntry(var7));
            ++var7;
         }
      }

      Type.VAR_INT.writePrimitive(var1, 0);
      long[] var14 = CompactArrayUtil.createCompactArray(var4, 4096, var4 == 13?var2::getFlatBlock:var2::getPaletteIndex);
      Type.VAR_INT.writePrimitive(var1, var14.length);
      int var9 = var14.length;
      int var10 = 0;
      if(var10 < var9) {
         long var11 = var14[var10];
         var1.writeLong(var11);
         ++var10;
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
