package net;

import net.acE;
import net.minecraft.network.status.INetHandlerStatusServer;
import net.minecraft.network.status.client.C00PacketServerQuery;
import net.minecraft.network.status.client.C01PacketPing;

public class mo {
   private static acE[] b;

   public static void a(INetHandlerStatusServer var0, C01PacketPing var1) {
      var0.processPing(var1);
   }

   public static void a(INetHandlerStatusServer var0, C00PacketServerQuery var1) {
      var0.processServerQuery(var1);
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new acE[5]);
      }

   }
}
