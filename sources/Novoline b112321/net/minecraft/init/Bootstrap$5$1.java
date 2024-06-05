package net.minecraft.init;

import net.oO;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

class Bootstrap$5$1 extends BehaviorProjectileDispense {
   final ItemStack val$stack;
   final oO c;

   Bootstrap$5$1(oO var1, ItemStack var2) {
      this.c = var1;
      this.val$stack = var2;
   }

   protected IProjectile getProjectileEntity(World var1, IPosition var2) {
      return new EntityPotion(var1, var2.getX(), var2.getY(), var2.getZ(), this.val$stack.copy());
   }

   protected float func_82498_a() {
      return super.func_82498_a() * 0.5F;
   }

   protected float func_82500_b() {
      return super.func_82500_b() * 1.25F;
   }
}
