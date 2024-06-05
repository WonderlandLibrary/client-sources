package net;

import io.netty.buffer.ByteBuf;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import net.aIa;
import net.aMh;
import net.aRY;
import net.aXe;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.Via;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.protocols.protocol1_9to1_8.storage.ClientChunks;

class o9 implements PacketHandler {
   final aMh a;

   o9(aMh var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      var1.cancel();
      aXe.b();
      aIa var3 = (aIa)Via.getManager().f().b(aIa.class);
      if(var3.a()) {
         List var4 = var3.a(var1, (ClientChunks)var1.user().b(ClientChunks.class));
         Iterator var5 = var4.iterator();
         if(var5.hasNext()) {
            Object var6 = var5.next();
            if(!(var6 instanceof PacketWrapper)) {
               throw new IOException("transformMapChunkBulk returned the wrong object type");
            }

            PacketWrapper var7 = (PacketWrapper)var6;
            ByteBuf var8 = var1.user().getChannel().alloc().buffer();
            PacketWrapper var10000 = var7;
            byte var10001 = -1;

            try {
               var10000.setId(var10001);
               var7.writeToBuffer(var8);
               PacketWrapper var9 = new PacketWrapper(33, var8, var1.user());
               var9.send(aRY.class, false, true);
            } finally {
               var8.release();
            }
         }

      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
