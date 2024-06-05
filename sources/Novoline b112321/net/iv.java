package net;

import net.a66;
import net.acE;
import net.aqI;
import net.ayk;
import viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.BackwardsMappings;
import viaversion.viaversion.api.protocol.ClientboundPacketType;
import viaversion.viaversion.api.protocol.ServerboundPacketType;

public class iv {
   private static int[] b;

   public static aqI b(ayk var0) {
      return var0.a();
   }

   public static BackwardsMappings a(ayk var0) {
      return var0.c();
   }

   public static void a(ayk var0, ClientboundPacketType var1, acE var2) {
      var0.a(var1, var2);
   }

   public static void a(ayk var0, ClientboundPacketType var1, ClientboundPacketType var2, acE var3) {
      var0.a(var1, var2, var3);
   }

   public static void a(ayk var0, ServerboundPacketType var1, acE var2) {
      var0.a(var1, var2);
   }

   public static void a(ayk var0, a66 var1, int var2, int var3, acE var4) {
      var0.b(var1, var2, var3, var4);
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new int[4]);
      }

   }
}
