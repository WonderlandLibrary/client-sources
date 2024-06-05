package net;

import java.util.List;
import net.aTZ;
import viaversion.viaversion.api.data.UserConnection;
import viaversion.viaversion.api.entities.EntityType;
import viaversion.viaversion.api.protocol.ClientboundPacketType;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

public class Ao {
   private static String[] b;

   public static void a(aTZ var0, int var1, List var2, UserConnection var3) {
      var0.handleMetadata(var1, var2, var3);
   }

   public static void a(aTZ var0, ClientboundPacketType var1, EntityType var2) {
      var0.registerSpawnTrackerWithData(var1, var2);
   }

   public static void a(aTZ var0, ClientboundPacketType var1, Type var2) {
      var0.registerMetadataRewriter(var1, var2);
   }

   public static void a(aTZ var0, ClientboundPacketType var1) {
      var0.registerEntityDestroy(var1);
   }

   public static PacketHandler a(aTZ var0) {
      return var0.getTracker();
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new String[2]);
      }

   }
}
