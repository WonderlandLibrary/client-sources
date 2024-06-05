package net.minecraft.tileentity;

import java.util.concurrent.*;

class TileEntity$1 implements Callable {
    @Override
    public String call() {
        return TileEntity.access$000().get(TileEntity.this.getClass()) + " // " + TileEntity.this.getClass().getCanonicalName();
    }
}