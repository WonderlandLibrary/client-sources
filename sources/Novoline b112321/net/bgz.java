package net;

import net.aTM;
import net.cN;
import viaversion.viaversion.api.entities.EntityType;
import viaversion.viaversion.api.protocol.ClientboundPacketType;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

public class bgz {
   private static boolean b;

   public static int a(int var0, cN var1) {
      return aTM.a(var0, var1);
   }

   public static PacketHandler a(aTM var0, Type var1) {
      return var0.getTrackerAndRewriter(var1);
   }

   public static void a(aTM var0, ClientboundPacketType var1) {
      var0.registerEntityDestroy(var1);
   }

   public static void a(aTM var0, ClientboundPacketType var1, Type var2, Type var3) {
      var0.registerMetadataRewriter(var1, var2, var3);
   }

   public static PacketHandler a(aTM var0, Type var1, EntityType var2) {
      return var0.getTrackerAndRewriter(var1, var2);
   }

   public static int a(aTM var0, int var1) {
      return var0.getNewEntityId(var1);
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
      if(!a()) {
         b(true);
      }

   }
}
