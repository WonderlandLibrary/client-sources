package net;

import net.a_n;
import net.acE;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.Entity;

public class a4V {
   public static void b(ModelPlayer var0, float var1) {
      var0.renderCape(var1);
   }

   public static void a(ModelPlayer var0, float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      boolean var8 = a_n.b();
      var0.setRotationAngles(var1, var2, var3, var4, var5, var6, var7);
      if(acE.b() == null) {
         a_n.b(false);
      }

   }

   public static void b(ModelPlayer var0) {
      var0.renderRightArm();
   }

   public static void a(ModelPlayer var0) {
      var0.renderLeftArm();
   }

   public static void a(ModelPlayer var0, boolean var1) {
      var0.setInvisible(var1);
   }

   public static void a(ModelPlayer var0, float var1) {
      var0.renderDeadmau5Head(var1);
   }
}
