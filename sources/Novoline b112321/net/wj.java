package net;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Arrays;
import net.Gt;
import net.acE;
import viaversion.viaversion.api.Via;
import viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import viaversion.viaversion.api.minecraft.chunks.Chunk;
import viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.api.type.types.minecraft.BaseChunkType;
import viaversion.viaversion.api.type.types.version.Types1_16;

public class wj extends Type {
   private static final CompoundTag[] ah = new CompoundTag[0];

   public wj() {
      super("Chunk", Chunk.class);
   }

   public Chunk a(ByteBuf var1) throws Exception {
      int var3 = var1.readInt();
      int var4 = var1.readInt();
      boolean var5 = var1.readBoolean();
      boolean var6 = var1.readBoolean();
      Gt.b();
      int var7 = Type.VAR_INT.readPrimitive(var1);
      CompoundTag var8 = (CompoundTag)Type.NBT.read(var1);
      int[] var9 = var5?new int[1024]:null;
      if(var5) {
         int var10 = 0;
         if(var10 < 1024) {
            var9[var10] = var1.readInt();
            ++var10;
         }
      }

      Type.VAR_INT.readPrimitive(var1);
      ChunkSection[] var15 = new ChunkSection[16];
      int var11 = 0;
      if(var11 < 16) {
         if((var7 & 1 << var11) != 0) {
            short var12 = var1.readShort();
            ChunkSection var13 = (ChunkSection)Types1_16.CHUNK_SECTION.read(var1);
            var13.setNonAirBlocksCount(var12);
            var15[var11] = var13;
         }

         ++var11;
      }

      ArrayList var17 = new ArrayList(Arrays.asList((Object[])Type.NBT_ARRAY.read(var1)));
      if(var1.readableBytes() > 0) {
         byte[] var18 = (byte[])Type.REMAINING_BYTES.read(var1);
         if(Via.getManager().isDebug()) {
            Via.getPlatform().getLogger().warning("Found " + var18.length + " more bytes than expected while reading the chunk: " + var3 + "/" + var4);
         }
      }

      BaseChunk var10000 = new BaseChunk(var3, var4, var5, var6, var7, var15, var9, var8, var17);
      if(acE.b() == null) {
         Gt.b(new String[1]);
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
