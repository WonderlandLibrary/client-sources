package net;

import java.util.UUID;
import viaversion.viaversion.api.data.UserConnection;
import viaversion.viaversion.protocols.protocol1_9to1_8.providers.BossBarProvider;

public class uz {
   public static void a(BossBarProvider var0, UserConnection var1, UUID var2) {
      var0.handleRemove(var1, var2);
   }

   public static void b(BossBarProvider var0, UserConnection var1, UUID var2) {
      var0.handleAdd(var1, var2);
   }
}
