package net.minecraft.init;

import net.minecraft.dispenser.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

static final class Bootstrap$9 extends BehaviorDefaultDispenseItem {
    private final BehaviorDefaultDispenseItem field_150842_b = new BehaviorDefaultDispenseItem();
    
    public ItemStack dispenseStack(final IBlockSource source, final ItemStack stack) {
        final EnumFacing var3 = BlockDispenser.getFacing(source.getBlockMetadata());
        final World var4 = source.getWorld();
        final double var5 = source.getX() + var3.getFrontOffsetX() * 1.125f;
        final double var6 = source.getY() + var3.getFrontOffsetY() * 1.125f;
        final double var7 = source.getZ() + var3.getFrontOffsetZ() * 1.125f;
        final BlockPos var8 = source.getBlockPos().offset(var3);
        final Material var9 = var4.getBlockState(var8).getBlock().getMaterial();
        double var10;
        if (Material.water.equals(var9)) {
            var10 = 1.0;
        }
        else {
            if (!Material.air.equals(var9) || !Material.water.equals(var4.getBlockState(var8.offsetDown()).getBlock().getMaterial())) {
                return this.field_150842_b.dispense(source, stack);
            }
            var10 = 0.0;
        }
        final EntityBoat var11 = new EntityBoat(var4, var5, var6 + var10, var7);
        var4.spawnEntityInWorld(var11);
        stack.splitStack(1);
        return stack;
    }
    
    @Override
    protected void playDispenseSound(final IBlockSource source) {
        source.getWorld().playAuxSFX(1000, source.getBlockPos(), 0);
    }
}