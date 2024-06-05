package net;

import net.aD5;
import net.afz;
import net.cT;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.Chunk1_7_10Type;
import viaversion.viarewind.protocol.protocol1_8to1_9.types.Chunk1_8Type;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.chunks.Chunk;
import viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import viaversion.viaversion.api.remapper.PacketHandler;

class sm implements PacketHandler {
   final aD5 a;

   sm(aD5 var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      cT var3 = (cT)var1.user().b(cT.class);
      Chunk var4 = (Chunk)var1.read(new Chunk1_8Type(var3));
      var1.write(new Chunk1_7_10Type(var3), var4);
      ChunkSection[] var5 = var4.getSections();
      afz.b();
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
