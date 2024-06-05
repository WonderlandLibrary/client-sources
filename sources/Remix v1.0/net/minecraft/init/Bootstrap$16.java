package net.minecraft.init;

import net.minecraft.dispenser.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

static final class Bootstrap$16 extends BehaviorDefaultDispenseItem {
    private boolean field_179241_b = true;
    
    @Override
    protected ItemStack dispenseStack(final IBlockSource source, final ItemStack stack) {
        final World var3 = source.getWorld();
        final BlockPos var4 = source.getBlockPos().offset(BlockDispenser.getFacing(source.getBlockMetadata()));
        final BlockPumpkin var5 = (BlockPumpkin)Blocks.pumpkin;
        if (var3.isAirBlock(var4) && var5.func_176390_d(var3, var4)) {
            if (!var3.isRemote) {
                var3.setBlockState(var4, var5.getDefaultState(), 3);
            }
            --stack.stackSize;
        }
        else {
            this.field_179241_b = false;
        }
        return stack;
    }
    
    @Override
    protected void playDispenseSound(final IBlockSource source) {
        if (this.field_179241_b) {
            source.getWorld().playAuxSFX(1000, source.getBlockPos(), 0);
        }
        else {
            source.getWorld().playAuxSFX(1001, source.getBlockPos(), 0);
        }
    }
}