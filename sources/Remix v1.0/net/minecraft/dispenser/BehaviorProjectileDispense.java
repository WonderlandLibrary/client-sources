package net.minecraft.dispenser;

import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public abstract class BehaviorProjectileDispense extends BehaviorDefaultDispenseItem
{
    public ItemStack dispenseStack(final IBlockSource source, final ItemStack stack) {
        final World var3 = source.getWorld();
        final IPosition var4 = BlockDispenser.getDispensePosition(source);
        final EnumFacing var5 = BlockDispenser.getFacing(source.getBlockMetadata());
        final IProjectile var6 = this.getProjectileEntity(var3, var4);
        var6.setThrowableHeading(var5.getFrontOffsetX(), var5.getFrontOffsetY() + 0.1f, var5.getFrontOffsetZ(), this.func_82500_b(), this.func_82498_a());
        var3.spawnEntityInWorld((Entity)var6);
        stack.splitStack(1);
        return stack;
    }
    
    @Override
    protected void playDispenseSound(final IBlockSource source) {
        source.getWorld().playAuxSFX(1002, source.getBlockPos(), 0);
    }
    
    protected abstract IProjectile getProjectileEntity(final World p0, final IPosition p1);
    
    protected float func_82498_a() {
        return 6.0f;
    }
    
    protected float func_82500_b() {
        return 1.1f;
    }
}
