package net.minecraft.init;

import net.minecraft.world.*;
import net.minecraft.dispenser.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;

static final class Bootstrap$4 extends BehaviorProjectileDispense {
    @Override
    protected IProjectile getProjectileEntity(final World worldIn, final IPosition position) {
        return new EntityExpBottle(worldIn, position.getX(), position.getY(), position.getZ());
    }
    
    @Override
    protected float func_82498_a() {
        return super.func_82498_a() * 0.5f;
    }
    
    @Override
    protected float func_82500_b() {
        return super.func_82500_b() * 1.25f;
    }
}