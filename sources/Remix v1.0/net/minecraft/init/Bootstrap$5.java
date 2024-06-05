package net.minecraft.init;

import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.dispenser.*;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.*;

static final class Bootstrap$5 implements IBehaviorDispenseItem {
    private final BehaviorDefaultDispenseItem field_150843_b = new BehaviorDefaultDispenseItem();
    
    @Override
    public ItemStack dispense(final IBlockSource source, final ItemStack stack) {
        return ItemPotion.isSplash(stack.getMetadata()) ? new BehaviorProjectileDispense() {
            @Override
            protected IProjectile getProjectileEntity(final World worldIn, final IPosition position) {
                return new EntityPotion(worldIn, position.getX(), position.getY(), position.getZ(), stack.copy());
            }
            
            @Override
            protected float func_82498_a() {
                return super.func_82498_a() * 0.5f;
            }
            
            @Override
            protected float func_82500_b() {
                return super.func_82500_b() * 1.25f;
            }
        }.dispense(source, stack) : this.field_150843_b.dispense(source, stack);
    }
}