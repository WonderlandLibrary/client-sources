package net.minecraft.init;

import net.minecraft.dispenser.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

static final class Bootstrap$17 extends BehaviorDefaultDispenseItem {
    @Override
    protected ItemStack dispenseStack(final IBlockSource source, final ItemStack stack) {
        final World var3 = source.getWorld();
        final BlockPos var4 = source.getBlockPos().offset(BlockDispenser.getFacing(source.getBlockMetadata()));
        if (var3.isAirBlock(var4)) {
            if (!var3.isRemote) {
                final IBlockState var5 = Blocks.command_block.getDefaultState().withProperty(BlockCommandBlock.TRIGGERED_PROP, false);
                var3.setBlockState(var4, var5, 3);
                ItemBlock.setTileEntityNBT(var3, var4, stack);
                var3.notifyNeighborsOfStateChange(source.getBlockPos(), source.getBlock());
            }
            --stack.stackSize;
        }
        return stack;
    }
    
    @Override
    protected void playDispenseSound(final IBlockSource source) {
    }
    
    @Override
    protected void spawnDispenseParticles(final IBlockSource source, final EnumFacing facingIn) {
    }
}