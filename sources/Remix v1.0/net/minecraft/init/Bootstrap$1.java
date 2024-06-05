package net.minecraft.init;

import net.minecraft.world.*;
import net.minecraft.dispenser.*;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.*;

static final class Bootstrap$1 extends BehaviorProjectileDispense {
    @Override
    protected IProjectile getProjectileEntity(final World worldIn, final IPosition position) {
        final EntityArrow var3 = new EntityArrow(worldIn, position.getX(), position.getY(), position.getZ());
        var3.canBePickedUp = 1;
        return var3;
    }
}