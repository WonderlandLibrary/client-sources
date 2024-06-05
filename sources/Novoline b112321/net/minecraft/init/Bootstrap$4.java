package net.minecraft.init;

import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.world.World;

final class Bootstrap$4 extends BehaviorProjectileDispense {
   protected IProjectile getProjectileEntity(World var1, IPosition var2) {
      return new EntityExpBottle(var1, var2.getX(), var2.getY(), var2.getZ());
   }

   protected float func_82498_a() {
      return super.func_82498_a() * 0.5F;
   }

   protected float func_82500_b() {
      return super.func_82500_b() * 1.25F;
   }
}
