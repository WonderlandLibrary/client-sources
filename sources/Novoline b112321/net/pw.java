package net;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.item.ItemStack;
import net.minecraft.world.DifficultyInstance;

public class pw {
   public static ItemStack a(EntityWitch var0) {
      return var0.getHeldItem();
   }

   public static void a(EntityWitch var0, double var1, double var3, double var5, float var7, float var8) {
      var0.setLocationAndAngles(var1, var3, var5, var7, var8);
   }

   public static IEntityLivingData a(EntityWitch var0, DifficultyInstance var1, IEntityLivingData var2) {
      return var0.onInitialSpawn(var1, var2);
   }
}
