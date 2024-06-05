package net;

import java.util.Random;
import net.minecraft.entity.passive.EntitySquid;

public class aem {
   public static int c(EntitySquid var0) {
      return var0.getAge();
   }

   public static void a(EntitySquid var0, float var1, float var2, float var3) {
      var0.func_175568_b(var1, var2, var3);
   }

   public static Random b(EntitySquid var0) {
      return var0.getRNG();
   }

   public static boolean a(EntitySquid var0) {
      return var0.func_175567_n();
   }
}
