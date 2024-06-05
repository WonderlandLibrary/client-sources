package net;

import net.acE;
import net.aqX;
import viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import viaversion.viabackwards.protocol.protocol1_15_2to1_16.Protocol1_15_2To1_16;
import viaversion.viabackwards.protocol.protocol1_15_2to1_16.data.BackwardsMappings;
import viaversion.viaversion.api.protocol.ClientboundPacketType;
import viaversion.viaversion.api.protocol.ServerboundPacketType;

public class a8_ {
   private static boolean b;

   public static TranslatableRewriter b(Protocol1_15_2To1_16 var0) {
      return var0.getTranslatableRewriter();
   }

   public static void a(Protocol1_15_2To1_16 var0, ClientboundPacketType var1, acE var2) {
      var0.a(var1, var2);
   }

   public static aqX c(Protocol1_15_2To1_16 var0) {
      return var0.c();
   }

   public static BackwardsMappings a(Protocol1_15_2To1_16 var0) {
      return var0.getMappingData();
   }

   public static void a(Protocol1_15_2To1_16 var0, ServerboundPacketType var1, acE var2) {
      var0.a(var1, var2);
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean b() {
      return b;
   }

   public static boolean c() {
      boolean var0 = b();
      return true;
   }

   static {
      if(!b()) {
         b(true);
      }

   }
}
