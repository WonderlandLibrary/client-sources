package net.minecraft.src;

import java.util.concurrent.*;

class CallableTileEntityData implements Callable
{
    final TileEntity theTileEntity;
    
    CallableTileEntityData(final TileEntity par1TileEntity) {
        this.theTileEntity = par1TileEntity;
    }
    
    public String callTileEntityDataInfo() {
        final int var1 = this.theTileEntity.worldObj.getBlockMetadata(this.theTileEntity.xCoord, this.theTileEntity.yCoord, this.theTileEntity.zCoord);
        if (var1 < 0) {
            return "Unknown? (Got " + var1 + ")";
        }
        final String var2 = String.format("%4s", Integer.toBinaryString(var1)).replace(" ", "0");
        return String.format("%1$d / 0x%1$X / 0b%2$s", var1, var2);
    }
    
    @Override
    public Object call() {
        return this.callTileEntityDataInfo();
    }
}
