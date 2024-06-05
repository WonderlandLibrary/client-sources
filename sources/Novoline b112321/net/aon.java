package net;

import net.acE;
import net.aq6;
import net.cK;
import net.cT;
import viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage.ChunkLightStorage$ChunkLight;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.chunks.Chunk;
import viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.types.Chunk1_13Type;
import viaversion.viaversion.protocols.protocol1_14to1_13_2.types.Chunk1_14Type;

class aon extends acE {
   final aq6 c;

   aon(aq6 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(PacketWrapper var1) throws Exception {
      cT var3 = (cT)var1.user().b(cT.class);
      aq6.a();
      Chunk var4 = (Chunk)var1.read(new Chunk1_14Type());
      var1.write(new Chunk1_13Type(var3), var4);
      ChunkLightStorage$ChunkLight var5 = ((cK)var1.user().b(cK.class)).a(var4.getX(), var4.getZ());
      int var6 = 0;
      if(var6 < var4.getSections().length) {
         ChunkSection var7 = var4.getSections()[var6];
         ++var6;
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
