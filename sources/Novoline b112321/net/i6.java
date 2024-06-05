package net;

import io.netty.buffer.ByteBuf;
import net.a66;
import net.aRY;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.data.UserConnection;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider;
import viaversion.viaversion.protocols.protocol1_9to1_8.storage.MovementTracker;

public class i6 extends MovementTransmitterProvider {
   private static boolean b;

   public Object getFlyingPacket() {
      return null;
   }

   public Object getGroundPacket() {
      return null;
   }

   public void sendPlayer(UserConnection var1) {
      if(var1.getProtocolInfo().e() == a66.PLAY) {
         PacketWrapper var2 = new PacketWrapper(3, (ByteBuf)null, var1);
         var2.write(Type.BOOLEAN, Boolean.valueOf(((MovementTracker)var1.b(MovementTracker.class)).isGround()));
         PacketWrapper var10000 = var2;
         Class var10001 = aRY.class;

         try {
            var10000.sendToServer(var10001);
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      }

   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean b() {
      return b;
   }

   public static boolean a() {
      boolean var0 = b();
      return true;
   }

   static {
      if(!b()) {
         b(true);
      }

   }
}
