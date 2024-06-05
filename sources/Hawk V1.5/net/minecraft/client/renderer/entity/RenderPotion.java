package net.minecraft.client.renderer.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RenderPotion extends RenderSnowball {
   private static final String __OBFID = "CL_00002430";

   public RenderPotion(RenderManager var1, RenderItem var2) {
      super(var1, Items.potionitem, var2);
   }

   public ItemStack func_177085_a(EntityPotion var1) {
      return new ItemStack(this.field_177084_a, 1, var1.getPotionDamage());
   }

   public ItemStack func_177082_d(Entity var1) {
      return this.func_177085_a((EntityPotion)var1);
   }
}
