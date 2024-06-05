package net;

import net.acE;
import viaversion.viaversion.api.protocol.ClientboundPacketType;
import viaversion.viaversion.api.protocol.ServerboundPacketType;
import viaversion.viaversion.protocols.protocol1_16to1_15_2.Protocol1_16To1_15_2;
import viaversion.viaversion.protocols.protocol1_16to1_15_2.data.MappingData;

public class a6q {
   private static int b;

   public static void a(Protocol1_16To1_15_2 var0, ClientboundPacketType var1, acE var2) {
      var0.a(var1, var2);
   }

   public static void a(Protocol1_16To1_15_2 var0, ServerboundPacketType var1, acE var2) {
      var0.a(var1, var2);
   }

   public static MappingData a(Protocol1_16To1_15_2 var0) {
      return var0.getMappingData();
   }

   public static Object a(Protocol1_16To1_15_2 var0, Class var1) {
      return var0.get(var1);
   }

   public static void a(Protocol1_16To1_15_2 var0, ClientboundPacketType var1, ClientboundPacketType var2, acE var3) {
      var0.a(var1, var2, var3);
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int c() {
      int var0 = b();
      return 71;
   }

   static {
      if(b() != 0) {
         b(72);
      }

   }
}
