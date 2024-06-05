package net;

import java.util.ArrayList;
import viaversion.viarewind.protocol.protocol1_8to1_9.storage.Cooldown;

public class axk {
   public static void a(Cooldown var0) {
      var0.hit();
   }

   public static void a(Cooldown var0, double var1, ArrayList var3) {
      var0.setAttackSpeed(var1, var3);
   }

   public static void a(Cooldown var0, long var1) {
      var0.setLastHit(var1);
   }
}
