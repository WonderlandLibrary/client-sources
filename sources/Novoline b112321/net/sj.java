package net;

import net.acE;
import net.ai4;
import viaversion.viaversion.api.protocol.ClientboundPacketType;
import viaversion.viaversion.protocols.protocol1_15to1_14_4.Protocol1_15To1_14_4;

public class sj {
   private static String[] b;

   public static ai4 a(Protocol1_15To1_14_4 var0) {
      return var0.a();
   }

   public static void a(Protocol1_15To1_14_4 var0, ClientboundPacketType var1, acE var2) {
      var0.a(var1, var2);
   }

   public static Object a(Protocol1_15To1_14_4 var0, Class var1) {
      return var0.get(var1);
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new String[3]);
      }

   }
}
