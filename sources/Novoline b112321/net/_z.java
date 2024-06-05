package net;

import net.BS;
import net.aW3;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.Via;
import viaversion.viaversion.api.data.UserConnection;
import viaversion.viaversion.api.minecraft.Position;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;

class _z implements PacketHandler {
   final aW3 a;

   _z(aW3 var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      Position var3 = (Position)var1.get(Type.POSITION, 0);
      BS.b();
      int var4 = BS.a(((Integer)var1.get(Type.VAR_INT, 0)).intValue());
      UserConnection var5 = var1.user();
      if(Via.getConfig().isServersideBlockConnections()) {
         ConnectionData.updateBlockStorage(var5, var3.getX(), var3.getY(), var3.getZ(), var4);
         var4 = ConnectionData.connect(var5, var3, var4);
      }

      var1.set(Type.VAR_INT, 0, Integer.valueOf(BS.a(var1.user(), var3, var4)));
      if(Via.getConfig().isServersideBlockConnections()) {
         var1.send(Protocol1_13To1_12_2.class, true, true);
         var1.cancel();
         ConnectionData.update(var5, var3);
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
