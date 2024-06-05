package net;

import java.util.List;
import java.util.Map;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.Explosion;

public class m2 {
   public static void c(Explosion var0) {
      var0.doExplosionA();
   }

   public static void a(Explosion var0, boolean var1) {
      var0.doExplosionB(var1);
   }

   public static void b(Explosion var0) {
      var0.func_180342_d();
   }

   public static List d(Explosion var0) {
      return var0.getAffectedBlockPositions();
   }

   public static Map a(Explosion var0) {
      return var0.getPlayerKnockbackMap();
   }

   public static EntityLivingBase e(Explosion var0) {
      return var0.getExplosivePlacedBy();
   }
}
