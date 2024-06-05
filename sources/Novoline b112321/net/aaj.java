package net;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSheep1;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class aaj {
   public static void a(ModelSheep1 var0, ModelBase var1) {
      var0.setModelAttributes(var1);
   }

   public static void a(ModelSheep1 var0, EntityLivingBase var1, float var2, float var3, float var4) {
      var0.setLivingAnimations(var1, var2, var3, var4);
   }

   public static void a(ModelSheep1 var0, Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      var0.render(var1, var2, var3, var4, var5, var6, var7);
   }
}
