package com.viaversion.viaversion.api.minecraft.chunks;

import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.util.ArrayList;
import java.util.List;
import net.aiV;

public class Chunk1_8 extends BaseChunk {
   private boolean unloadPacket;

   public Chunk1_8(int var1, int var2, boolean var3, int var4, aiV[] var5, int[] var6, List var7) {
      super(var1, var2, var3, false, var4, var5, var6, var7);
   }

   public Chunk1_8(int var1, int var2) {
      this(var1, var2, true, 0, new aiV[16], (int[])null, new ArrayList());
      this.unloadPacket = true;
   }

   public boolean e() {
      PacketRemapper[] var1 = aiV.b();
      return this.biomeData != null && this.fullChunk;
   }

   public boolean isUnloadPacket() {
      return this.unloadPacket;
   }
}
