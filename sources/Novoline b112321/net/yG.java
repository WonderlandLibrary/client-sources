package net;

import java.util.List;
import java.util.Set;
import viaversion.viaversion.api.data.UserConnection;
import viaversion.viaversion.protocols.protocol1_9to1_8.storage.ClientChunks;

public class yG {
   public static Set a(ClientChunks var0) {
      return var0.getBulkChunks();
   }

   public static long a(int var0, int var1) {
      return ClientChunks.toLong(var0, var1);
   }

   public static UserConnection b(ClientChunks var0) {
      return var0.d();
   }

   public static Set c(ClientChunks var0) {
      return var0.getLoadedChunks();
   }

   public static List a(ClientChunks var0, Object var1) {
      return var0.transformMapChunkBulk(var1);
   }
}
