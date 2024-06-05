package net;

import net.aTK;
import net.acE;
import viaversion.viaversion.api.entities.EntityType;
import viaversion.viaversion.api.protocol.ClientboundPacketType;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

public class agk {
   private static acE[] b;

   public static PacketHandler a(aTK var0, Type var1) {
      return var0.getTrackerAndRewriter(var1);
   }

   public static PacketHandler a(aTK var0, Type var1, EntityType var2) {
      return var0.getTrackerAndRewriter(var1, var2);
   }

   public static void a(aTK var0, ClientboundPacketType var1) {
      var0.registerEntityDestroy(var1);
   }

   public static void a(aTK var0, ClientboundPacketType var1, Type var2) {
      var0.registerMetadataRewriter(var1, var2);
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new acE[4]);
      }

   }
}
