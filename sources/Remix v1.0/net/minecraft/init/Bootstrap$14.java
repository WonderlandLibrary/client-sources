package net.minecraft.init;

import net.minecraft.dispenser.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

static final class Bootstrap$14 extends BehaviorDefaultDispenseItem {
    @Override
    protected ItemStack dispenseStack(final IBlockSource source, final ItemStack stack) {
        final World var3 = source.getWorld();
        final BlockPos var4 = source.getBlockPos().offset(BlockDispenser.getFacing(source.getBlockMetadata()));
        final EntityTNTPrimed var5 = new EntityTNTPrimed(var3, var4.getX() + 0.5, var4.getY(), var4.getZ() + 0.5, null);
        var3.spawnEntityInWorld(var5);
        var3.playSoundAtEntity(var5, "game.tnt.primed", 1.0f, 1.0f);
        --stack.stackSize;
        return stack;
    }
}