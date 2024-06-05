package net.minecraft.init;

import net.minecraft.dispenser.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

static final class Bootstrap$12 extends BehaviorDefaultDispenseItem {
    private boolean field_150839_b = true;
    
    @Override
    protected ItemStack dispenseStack(final IBlockSource source, final ItemStack stack) {
        final World var3 = source.getWorld();
        final BlockPos var4 = source.getBlockPos().offset(BlockDispenser.getFacing(source.getBlockMetadata()));
        if (var3.isAirBlock(var4)) {
            var3.setBlockState(var4, Blocks.fire.getDefaultState());
            if (stack.attemptDamageItem(1, var3.rand)) {
                stack.stackSize = 0;
            }
        }
        else if (var3.getBlockState(var4).getBlock() == Blocks.tnt) {
            Blocks.tnt.onBlockDestroyedByPlayer(var3, var4, Blocks.tnt.getDefaultState().withProperty(BlockTNT.field_176246_a, true));
            var3.setBlockToAir(var4);
        }
        else {
            this.field_150839_b = false;
        }
        return stack;
    }
    
    @Override
    protected void playDispenseSound(final IBlockSource source) {
        if (this.field_150839_b) {
            source.getWorld().playAuxSFX(1000, source.getBlockPos(), 0);
        }
        else {
            source.getWorld().playAuxSFX(1001, source.getBlockPos(), 0);
        }
    }
}