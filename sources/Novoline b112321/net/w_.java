package net;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Arrays;
import net.aPu;
import net.acE;
import viaversion.viaversion.api.Via;
import viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import viaversion.viaversion.api.minecraft.chunks.Chunk;
import viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.api.type.types.minecraft.BaseChunkType;
import viaversion.viaversion.api.type.types.version.Types1_16;

public class w_ extends Type {
   private static final CompoundTag[] ah = new CompoundTag[0];

   public w_() {
      super("Chunk", Chunk.class);
   }

   public Chunk a(ByteBuf var1) throws Exception {
      aPu.b();
      int var3 = var1.readInt();
      int var4 = var1.readInt();
      boolean var5 = var1.readBoolean();
      int var6 = Type.VAR_INT.readPrimitive(var1);
      CompoundTag var7 = (CompoundTag)Type.NBT.read(var1);
      Object var8 = null;
      int[] var13 = (int[])Type.VAR_INT_ARRAY_PRIMITIVE.read(var1);
      Type.VAR_INT.readPrimitive(var1);
      ChunkSection[] var9 = new ChunkSection[16];
      int var10 = 0;
      if(var10 < 16) {
         if((var6 & 1 << var10) != 0) {
            short var11 = var1.readShort();
            ChunkSection var12 = (ChunkSection)Types1_16.CHUNK_SECTION.read(var1);
            var12.setNonAirBlocksCount(var11);
            var9[var10] = var12;
         }

         ++var10;
      }

      ArrayList var15 = new ArrayList(Arrays.asList((Object[])Type.NBT_ARRAY.read(var1)));
      if(var1.readableBytes() > 0) {
         byte[] var16 = (byte[])Type.REMAINING_BYTES.read(var1);
         if(Via.getManager().isDebug()) {
            Via.getPlatform().getLogger().warning("Found " + var16.length + " more bytes than expected while reading the chunk: " + var3 + "/" + var4);
         }
      }

      BaseChunk var10000 = new BaseChunk(var3, var4, var5, false, var6, var9, var13, var7, var15);
      if(acE.b() == null) {
         aPu.b(new String[4]);
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
