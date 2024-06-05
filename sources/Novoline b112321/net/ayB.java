package net;

import net.HR;
import net.aKF;
import net.cT;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.chunks.Chunk;
import viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.types.Chunk1_13Type;

class ayB implements PacketHandler {
   final aKF a;

   ayB(aKF var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      HR.b();
      cT var3 = (cT)var1.user().b(cT.class);
      Chunk var4 = (Chunk)var1.passthrough(new Chunk1_13Type(var3));
      ChunkSection[] var5 = var4.getSections();
      int var6 = var5.length;
      int var7 = 0;
      if(var7 < var6) {
         ChunkSection var8 = var5[var7];
         ++var7;
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
