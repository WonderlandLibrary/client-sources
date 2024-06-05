package net.minecraft.src;

import java.util.concurrent.*;

class CallableTileEntityName implements Callable
{
    final TileEntity theTileEntity;
    
    CallableTileEntityName(final TileEntity par1TileEntity) {
        this.theTileEntity = par1TileEntity;
    }
    
    public String callTileEntityName() {
        return String.valueOf(TileEntity.getClassToNameMap().get(this.theTileEntity.getClass())) + " // " + this.theTileEntity.getClass().getCanonicalName();
    }
    
    @Override
    public Object call() {
        return this.callTileEntityName();
    }
}
