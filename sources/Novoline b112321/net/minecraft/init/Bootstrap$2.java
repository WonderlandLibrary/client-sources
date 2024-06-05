package net.minecraft.init;

import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.world.World;

final class Bootstrap$2 extends BehaviorProjectileDispense {
   protected IProjectile getProjectileEntity(World var1, IPosition var2) {
      return new EntityEgg(var1, var2.getX(), var2.getY(), var2.getZ());
   }
}
