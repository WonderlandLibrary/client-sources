package net;

import net.aMG;
import net.aXe;
import net.amb;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.Via;
import viaversion.viaversion.api.minecraft.chunks.Chunk1_8;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_9to1_8.storage.ClientChunks;
import viaversion.viaversion.protocols.protocol1_9to1_8.types.Chunk1_9to1_8Type;

class B_ implements PacketHandler {
   final aMG a;

   B_(aMG var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      aXe.c();
      ClientChunks var3 = (ClientChunks)var1.user().b(ClientChunks.class);
      Chunk1_9to1_8Type var4 = new Chunk1_9to1_8Type(var3);
      Chunk1_8 var5 = (Chunk1_8)var1.read(var4);
      if(var5.isUnloadPacket()) {
         var1.setId(29);
         var1.write(Type.INT, Integer.valueOf(var5.getX()));
         var1.write(Type.INT, Integer.valueOf(var5.getZ()));
         amb var6 = (amb)Via.getManager().f().b(amb.class);
         var6.a(var1.user(), var5.getX(), var5.getZ());
      }

      var1.write(var4, var5);
      var1.read(Type.REMAINING_BYTES);
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
