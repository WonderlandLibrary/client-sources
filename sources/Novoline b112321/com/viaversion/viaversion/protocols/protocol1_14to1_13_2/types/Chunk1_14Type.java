package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.types;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Arrays;
import net.aEY;
import net.af3;
import net.aiV;
import net.w9;

public class Chunk1_14Type extends Type {
   public Chunk1_14Type() {
      super("Chunk", Chunk.class);
   }

   public Chunk read(ByteBuf var1) throws Exception {
      int var3 = var1.readInt();
      af3.c();
      int var4 = var1.readInt();
      boolean var5 = var1.readBoolean();
      int var6 = Type.VAR_INT.readPrimitive(var1);
      CompoundTag var7 = (CompoundTag)Type.ac.read(var1);
      Type.VAR_INT.readPrimitive(var1);
      aiV[] var8 = new aiV[16];
      int var9 = 0;
      if(var9 < 16) {
         if((var6 & 1 << var9) != 0) {
            short var10 = var1.readShort();
            aiV var11 = (aiV)aEY.c.read(var1);
            var11.b(var10);
            var8[var9] = var11;
         }

         ++var9;
      }

      int[] var13 = var5?new int[256]:null;
      if(var5) {
         int var14 = 0;
         if(var14 < 256) {
            var13[var14] = var1.readInt();
            ++var14;
         }
      }

      ArrayList var16 = new ArrayList(Arrays.asList((Object[])Type.j.read(var1)));
      if(var1.readableBytes() > 0) {
         byte[] var17 = (byte[])Type.REMAINING_BYTES.read(var1);
         if(Via.b().c()) {
            Via.d().getLogger().warning("Found " + var17.length + " more bytes than expected while reading the chunk: " + var3 + "/" + var4);
         }
      }

      BaseChunk var10000 = new BaseChunk(var3, var4, var5, false, var6, var8, var13, var7, var16);
      if(PacketRemapper.b() == null) {
         af3.b(new String[4]);
      }

      return var10000;
   }

   public void write(ByteBuf param1, Chunk param2) throws Exception {
      // $FF: Couldn't be decompiled
   }

   public Class getBaseClass() {
      return w9.class;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
