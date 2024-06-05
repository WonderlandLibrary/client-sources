package net.minecraft.tileentity;

import net.minecraft.server.gui.*;
import net.minecraft.block.*;

public class TileEntityDaylightDetector extends TileEntity implements IUpdatePlayerListBox
{
    @Override
    public void update() {
        if (this.worldObj != null && !this.worldObj.isRemote && this.worldObj.getTotalWorldTime() % 20L == 0L) {
            this.blockType = this.getBlockType();
            if (this.blockType instanceof BlockDaylightDetector) {
                ((BlockDaylightDetector)this.blockType).func_180677_d(this.worldObj, this.pos);
            }
        }
    }
}
