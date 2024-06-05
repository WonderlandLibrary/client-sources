package net.minecraft.init;

import net.minecraft.dispenser.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

static final class Bootstrap$6 extends BehaviorDefaultDispenseItem {
    public ItemStack dispenseStack(final IBlockSource source, final ItemStack stack) {
        final EnumFacing var3 = BlockDispenser.getFacing(source.getBlockMetadata());
        final double var4 = source.getX() + var3.getFrontOffsetX();
        final double var5 = source.getBlockPos().getY() + 0.2f;
        final double var6 = source.getZ() + var3.getFrontOffsetZ();
        final Entity var7 = ItemMonsterPlacer.spawnCreature(source.getWorld(), stack.getMetadata(), var4, var5, var6);
        if (var7 instanceof EntityLivingBase && stack.hasDisplayName()) {
            ((EntityLiving)var7).setCustomNameTag(stack.getDisplayName());
        }
        stack.splitStack(1);
        return stack;
    }
}