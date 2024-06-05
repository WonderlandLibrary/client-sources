package net.minecraft.init;

import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.dispenser.*;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.*;

class Bootstrap$5$1 extends BehaviorProjectileDispense {
    final /* synthetic */ ItemStack val$stack;
    
    @Override
    protected IProjectile getProjectileEntity(final World worldIn, final IPosition position) {
        return new EntityPotion(worldIn, position.getX(), position.getY(), position.getZ(), this.val$stack.copy());
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