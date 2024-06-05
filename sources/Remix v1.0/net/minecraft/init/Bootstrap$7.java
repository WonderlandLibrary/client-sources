package net.minecraft.init;

import net.minecraft.dispenser.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

static final class Bootstrap$7 extends BehaviorDefaultDispenseItem {
    public ItemStack dispenseStack(final IBlockSource source, final ItemStack stack) {
        final EnumFacing var3 = BlockDispenser.getFacing(source.getBlockMetadata());
        final double var4 = source.getX() + var3.getFrontOffsetX();
        final double var5 = source.getBlockPos().getY() + 0.2f;
        final double var6 = source.getZ() + var3.getFrontOffsetZ();
        final EntityFireworkRocket var7 = new EntityFireworkRocket(source.getWorld(), var4, var5, var6, stack);
        source.getWorld().spawnEntityInWorld(var7);
        stack.splitStack(1);
        return stack;
    }
    
    @Override
    protected void playDispenseSound(final IBlockSource source) {
        source.getWorld().playAuxSFX(1002, source.getBlockPos(), 0);
    }
}