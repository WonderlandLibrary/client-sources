package net;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Arrays;
import net.aEY;
import net.a_4;
import net.acE;
import viaversion.viaversion.api.Via;
import viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import viaversion.viaversion.api.minecraft.chunks.Chunk;
import viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.api.type.types.minecraft.BaseChunkType;

public class wa extends Type {
   private static final CompoundTag[] ah = new CompoundTag[0];

   public wa() {
      super("Chunk", Chunk.class);
   }

   public Chunk a(ByteBuf var1) throws Exception {
      int var3 = var1.readInt();
      int var4 = var1.readInt();
      boolean var5 = var1.readBoolean();
      a_4.c();
      int var6 = Type.VAR_INT.readPrimitive(var1);
      CompoundTag var7 = (CompoundTag)Type.NBT.read(var1);
      int[] var8 = var5?new int[1024]:null;
      if(var5) {
         int var9 = 0;
         if(var9 < 1024) {
            var8[var9] = var1.readInt();
            ++var9;
         }
      }

      Type.VAR_INT.readPrimitive(var1);
      ChunkSection[] var14 = new ChunkSection[16];
      int var10 = 0;
      if(var10 < 16) {
         if((var6 & 1 << var10) != 0) {
            short var11 = var1.readShort();
            ChunkSection var12 = (ChunkSection)aEY.c.read(var1);
            var12.setNonAirBlocksCount(var11);
            var14[var10] = var12;
         }

         ++var10;
      }

      ArrayList var16 = new ArrayList(Arrays.asList((Object[])Type.NBT_ARRAY.read(var1)));
      if(var1.readableBytes() > 0) {
         byte[] var17 = (byte[])Type.REMAINING_BYTES.read(var1);
         if(Via.getManager().isDebug()) {
            Via.getPlatform().getLogger().warning("Found " + var17.length + " more bytes than expected while reading the chunk: " + var3 + "/" + var4);
         }
      }

      BaseChunk var10000 = new BaseChunk(var3, var4, var5, false, var6, var14, var8, var7, var16);
      if(acE.b() == null) {
         a_4.b(false);
      }

      return var10000;
   }

   public void a(ByteBuf param1, Chunk param2) throws Exception {
      // $FF: Couldn't be decompiled
   }

   public Class getBaseClass() {
      return BaseChunkType.class;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
