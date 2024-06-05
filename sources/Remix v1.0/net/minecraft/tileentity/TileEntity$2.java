package net.minecraft.tileentity;

import java.util.concurrent.*;
import net.minecraft.block.*;

class TileEntity$2 implements Callable {
    @Override
    public String call() {
        final int var1 = Block.getIdFromBlock(TileEntity.this.worldObj.getBlockState(TileEntity.this.pos).getBlock());
        try {
            return String.format("ID #%d (%s // %s)", var1, Block.getBlockById(var1).getUnlocalizedName(), Block.getBlockById(var1).getClass().getCanonicalName());
        }
        catch (Throwable var2) {
            return "ID #" + var1;
        }
    }
}