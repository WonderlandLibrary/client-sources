package net.minecraft.block;

import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.chunk.*;
import net.minecraft.block.state.*;

static final class BlockBeacon$1 implements Runnable {
    final /* synthetic */ World val$worldIn;
    final /* synthetic */ BlockPos val$p_176450_1_;
    
    @Override
    public void run() {
        final Chunk var1 = this.val$worldIn.getChunkFromBlockCoords(this.val$p_176450_1_);
        for (int var2 = this.val$p_176450_1_.getY() - 1; var2 >= 0; --var2) {
            final BlockPos var3 = new BlockPos(this.val$p_176450_1_.getX(), var2, this.val$p_176450_1_.getZ());
            if (!var1.canSeeSky(var3)) {
                break;
            }
            final IBlockState var4 = this.val$worldIn.getBlockState(var3);
            if (var4.getBlock() == Blocks.beacon) {
                ((WorldServer)this.val$worldIn).addScheduledTask(new Runnable() {
                    @Override
                    public void run() {
                        final TileEntity var1 = Runnable.this.val$worldIn.getTileEntity(var3);
                        if (var1 instanceof TileEntityBeacon) {
                            ((TileEntityBeacon)var1).func_174908_m();
                            Runnable.this.val$worldIn.addBlockEvent(var3, Blocks.beacon, 1, 0);
                        }
                    }
                });
            }
        }
    }
}