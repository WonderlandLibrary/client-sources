package net.minecraft.block;

import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.tileentity.*;

class BlockBeacon$1$1 implements Runnable {
    final /* synthetic */ BlockPos val$var3;
    
    @Override
    public void run() {
        final TileEntity var1 = Runnable.this.val$worldIn.getTileEntity(this.val$var3);
        if (var1 instanceof TileEntityBeacon) {
            ((TileEntityBeacon)var1).func_174908_m();
            Runnable.this.val$worldIn.addBlockEvent(this.val$var3, Blocks.beacon, 1, 0);
        }
    }
}