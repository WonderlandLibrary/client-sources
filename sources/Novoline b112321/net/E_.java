package net;

import java.util.UUID;
import viaversion.viarewind.protocol.protocol1_8to1_9.storage.BossBarStorage;

public class E_ {
   public static void a(BossBarStorage var0) {
      var0.updateLocation();
   }

   public static void a(BossBarStorage var0, UUID var1, String var2, float var3) {
      var0.add(var1, var2, var3);
   }

   public static void a(BossBarStorage var0, UUID var1) {
      var0.remove(var1);
   }

   public static void a(BossBarStorage var0, UUID var1, float var2) {
      var0.updateHealth(var1, var2);
   }

   public static void a(BossBarStorage var0, UUID var1, String var2) {
      var0.updateTitle(var1, var2);
   }

   public static void b(BossBarStorage var0) {
      var0.changeWorld();
   }
}
