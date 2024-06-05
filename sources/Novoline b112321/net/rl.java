package net;

import java.util.List;
import viaversion.viaversion.api.data.UserConnection;
import viaversion.viaversion.api.protocol.ClientboundPacketType;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.rewriters.MetadataRewriter;
import viaversion.viaversion.api.type.Type;

public class rl {
   public static void a(MetadataRewriter var0, ClientboundPacketType var1) {
      var0.registerEntityDestroy(var1);
   }

   public static void a(MetadataRewriter var0, ClientboundPacketType var1, Type var2) {
      var0.registerMetadataRewriter(var1, var2);
   }

   public static PacketHandler a(MetadataRewriter var0) {
      return var0.getObjectTracker();
   }

   public static PacketHandler a(MetadataRewriter var0, Type var1) {
      return var0.getTrackerAndRewriter(var1);
   }

   public static void a(MetadataRewriter var0, int var1, List var2, UserConnection var3) {
      var0.handleMetadata(var1, var2, var3);
   }
}
