package net;

import java.util.UUID;
import viaversion.viaversion.api.boss.BossBar;
import viaversion.viaversion.api.data.UserConnection;

public class ro {
   public static BossBar c(BossBar var0) {
      return var0.hide();
   }

   public static UUID b(BossBar var0) {
      return var0.getId();
   }

   public static BossBar a(BossBar var0, UserConnection var1) {
      return var0.addConnection(var1);
   }

   public static BossBar a(BossBar var0) {
      return var0.show();
   }

   public static BossBar a(BossBar var0, String var1) {
      return var0.setTitle(var1);
   }

   public static BossBar a(BossBar var0, float var1) {
      return var0.setHealth(var1);
   }
}
