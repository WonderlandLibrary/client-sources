package net.minecraft.src;

import java.util.concurrent.*;

class CallableTileEntityID implements Callable
{
    final TileEntity theTileEntity;
    
    CallableTileEntityID(final TileEntity par1TileEntity) {
        this.theTileEntity = par1TileEntity;
    }
    
    public String callTileEntityID() {
        final int var1 = this.theTileEntity.worldObj.getBlockId(this.theTileEntity.xCoord, this.theTileEntity.yCoord, this.theTileEntity.zCoord);
        try {
            return String.format("ID #%d (%s // %s)", var1, Block.blocksList[var1].getUnlocalizedName(), Block.blocksList[var1].getClass().getCanonicalName());
        }
        catch (Throwable var2) {
            return "ID #" + var1;
        }
    }
    
    @Override
    public Object call() {
        return this.callTileEntityID();
    }
}
