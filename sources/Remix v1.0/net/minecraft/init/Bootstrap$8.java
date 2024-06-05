package net.minecraft.init;

import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.dispenser.*;
import net.minecraft.world.*;
import java.util.*;

static final class Bootstrap$8 extends BehaviorDefaultDispenseItem {
    public ItemStack dispenseStack(final IBlockSource source, final ItemStack stack) {
        final EnumFacing var3 = BlockDispenser.getFacing(source.getBlockMetadata());
        final IPosition var4 = BlockDispenser.getDispensePosition(source);
        final double var5 = var4.getX() + var3.getFrontOffsetX() * 0.3f;
        final double var6 = var4.getY() + var3.getFrontOffsetX() * 0.3f;
        final double var7 = var4.getZ() + var3.getFrontOffsetZ() * 0.3f;
        final World var8 = source.getWorld();
        final Random var9 = var8.rand;
        final double var10 = var9.nextGaussian() * 0.05 + var3.getFrontOffsetX();
        final double var11 = var9.nextGaussian() * 0.05 + var3.getFrontOffsetY();
        final double var12 = var9.nextGaussian() * 0.05 + var3.getFrontOffsetZ();
        var8.spawnEntityInWorld(new EntitySmallFireball(var8, var5, var6, var7, var10, var11, var12));
        stack.splitStack(1);
        return stack;
    }
    
    @Override
    protected void playDispenseSound(final IBlockSource source) {
        source.getWorld().playAuxSFX(1009, source.getBlockPos(), 0);
    }
}