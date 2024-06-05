package com.viaversion.viaversion.protocols.protocol1_9to1_8.types;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk1_8;
import com.viaversion.viaversion.api.type.PartialType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.ClientChunks;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.logging.Level;
import net.a8M;
import net.aRF;
import net.aiV;
import net.w9;

public class Chunk1_9to1_8Type extends PartialType {
   public static final int SECTION_COUNT = 16;
   private static final int SECTION_SIZE = 16;
   private static final int BIOME_DATA_LENGTH = 256;

   public Chunk1_9to1_8Type(ClientChunks var1) {
      super(var1, "Chunk", Chunk.class);
   }

   private static long toLong(int var0, int var1) {
      return ((long)var0 << 32) + (long)var1 - -2147483648L;
   }

   public Class getBaseClass() {
      return w9.class;
   }

   public Chunk read(ByteBuf var1, ClientChunks var2) throws Exception {
      int var3 = a8M.d();
      if(var2.d().c().b().b(aRF.class) && Via.c().isReplacePistons()) {
         boolean var31 = true;
      } else {
         boolean var10000 = false;
      }

      int var5 = Via.c().getPistonReplacementId();
      int var6 = var1.readInt();
      int var7 = var1.readInt();
      long var8 = toLong(var6, var7);
      boolean var10 = var1.readByte() != 0;
      int var11 = var1.readUnsignedShort();
      int var12 = Type.VAR_INT.readPrimitive(var1);
      BitSet var13 = new BitSet(16);
      aiV[] var14 = new aiV[16];
      int[] var15 = null;
      int var16 = 0;
      if(var16 < 16) {
         if((var11 & 1 << var16) != 0) {
            var13.set(var16);
         }

         ++var16;
      }

      var16 = var13.cardinality();
      boolean var17 = var2.getBulkChunks().remove(Long.valueOf(var8));
      if(var16 == 0 && var10 && !var17 && var2.getLoadedChunks().contains(Long.valueOf(var8))) {
         var2.getLoadedChunks().remove(Long.valueOf(var8));
         return new Chunk1_8(var6, var7);
      } else {
         int var18 = var1.readerIndex();
         var2.getLoadedChunks().add(Long.valueOf(var8));
         int var19 = 0;
         if(var19 < 16) {
            if(var13.get(var19)) {
               aiV var20 = (aiV)Types1_8.CHUNK_SECTION.read(var1);
               var14[var19] = var20;
               var20.c(36, var5);
            }

            ++var19;
         }

         var19 = 0;
         if(var19 < 16) {
            if(var13.get(var19)) {
               var14[var19].b(var1);
            }

            ++var19;
         }

         var19 = var12 - (var1.readerIndex() - var18);
         if(var19 >= 2048) {
            int var27 = 0;
            if(var27 < 16) {
               if(var13.get(var27)) {
                  var14[var27].c(var1);
                  var19 -= 2048;
               }

               ++var27;
            }
         }

         if(var19 >= 256) {
            var15 = new int[256];
            int var29 = 0;
            if(var29 < 256) {
               var15[var29] = var1.readByte() & 255;
               ++var29;
            }

            var19 -= 256;
         }

         Via.d().getLogger().log(Level.WARNING, var19 + " Bytes left after reading chunks! (" + var10 + ")");
         return new Chunk1_8(var6, var7, var10, var11, var14, var15, new ArrayList());
      }
   }

   public void write(ByteBuf param1, ClientChunks param2, Chunk param3) throws Exception {
      // $FF: Couldn't be decompiled
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
