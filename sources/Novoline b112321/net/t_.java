package net;

import net.aTV;
import viaversion.viaversion.api.entities.EntityType;
import viaversion.viaversion.api.protocol.ClientboundPacketType;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

public class t_ {
   private static boolean b;

   public static void a(aTV var0, ClientboundPacketType var1) {
      var0.registerEntityDestroy(var1);
   }

   public static void a(aTV var0, ClientboundPacketType var1, Type var2) {
      var0.registerMetadataRewriter(var1, var2);
   }

   public static PacketHandler a(aTV var0, Type var1, EntityType var2) {
      return var0.getTrackerAndRewriter(var1, var2);
   }

   public static PacketHandler a(aTV var0, Type var1) {
      return var0.getTrackerAndRewriter(var1);
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
      if(b()) {
         b(true);
      }

   }
}
