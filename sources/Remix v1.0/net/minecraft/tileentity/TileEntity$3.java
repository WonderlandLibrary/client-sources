package net.minecraft.tileentity;

import java.util.concurrent.*;
import net.minecraft.block.state.*;

class TileEntity$3 implements Callable {
    @Override
    public String call() {
        final IBlockState var1 = TileEntity.this.worldObj.getBlockState(TileEntity.this.pos);
        final int var2 = var1.getBlock().getMetaFromState(var1);
        if (var2 < 0) {
            return "Unknown? (Got " + var2 + ")";
        }
        final String var3 = String.format("%4s", Integer.toBinaryString(var2)).replace(" ", "0");
        return String.format("%1$d / 0x%1$X / 0b%2$s", var2, var3);
    }
}