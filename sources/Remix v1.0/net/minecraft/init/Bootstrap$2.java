package net.minecraft.init;

import net.minecraft.world.*;
import net.minecraft.dispenser.*;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.*;

static final class Bootstrap$2 extends BehaviorProjectileDispense {
    @Override
    protected IProjectile getProjectileEntity(final World worldIn, final IPosition position) {
        return new EntityEgg(worldIn, position.getX(), position.getY(), position.getZ());
    }
}