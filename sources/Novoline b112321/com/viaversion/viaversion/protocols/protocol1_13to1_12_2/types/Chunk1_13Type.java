package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.types;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.Environment;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.PartialType;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import net.aEY;
import net.aiV;
import net.awZ;
import net.cT;
import net.w9;

public class Chunk1_13Type extends PartialType {
   public Chunk1_13Type(cT var1) {
      super(var1, "Chunk", Chunk.class);
   }

   public Chunk a(ByteBuf var1, cT var2) throws Exception {
      int var4 = var1.readInt();
      int var5 = var1.readInt();
      awZ.b();
      boolean var6 = var1.readBoolean();
      int var7 = Type.VAR_INT.readPrimitive(var1);
      ByteBuf var8 = var1.readSlice(Type.VAR_INT.readPrimitive(var1));
      aiV[] var9 = new aiV[16];
      int var10 = 0;
      if(var10 < 16) {
         if((var7 & 1 << var10) != 0) {
            aiV var11 = (aiV)aEY.c.read(var8);
            var9[var10] = var11;
            var11.b(var8);
            if(var2.a() == Environment.NORMAL) {
               var11.c(var8);
            }
         }

         ++var10;
      }

      int[] var14 = var6?new int[256]:null;
      if(var6) {
         if(var8.readableBytes() >= 1024) {
            int var15 = 0;
            if(var15 < 256) {
               var14[var15] = var8.readInt();
               ++var15;
            }
         }

         Via.d().getLogger().log(Level.WARNING, "Chunk x=" + var4 + " z=" + var5 + " doesn\'t have biome data!");
      }

      ArrayList var17 = new ArrayList(Arrays.asList((Object[])Type.j.read(var1)));
      if(var1.readableBytes() > 0) {
         byte[] var12 = (byte[])Type.REMAINING_BYTES.read(var1);
         if(Via.b().c()) {
            Via.d().getLogger().warning("Found " + var12.length + " more bytes than expected while reading the chunk: " + var4 + "/" + var5);
         }
      }

      BaseChunk var10000 = new BaseChunk(var4, var5, var6, false, var7, var9, var14, var17);
      if(PacketRemapper.b() == null) {
         awZ.b(new PacketRemapper[5]);
      }

      return var10000;
   }

   public void a(ByteBuf param1, cT param2, Chunk param3) throws Exception {
      // $FF: Couldn't be decompiled
   }

   public Class getBaseClass() {
      return w9.class;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
