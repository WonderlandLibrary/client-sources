package net.minecraft.init;

import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.World;

final class Bootstrap$1 extends BehaviorProjectileDispense {
   protected IProjectile getProjectileEntity(World var1, IPosition var2) {
      EntityArrow var3 = new EntityArrow(var1, var2.getX(), var2.getY(), var2.getZ());
      var3.canBePickedUp = 1;
      return var3;
   }
}
