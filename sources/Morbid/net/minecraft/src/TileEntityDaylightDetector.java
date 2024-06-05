package net.minecraft.src;

public class TileEntityDaylightDetector extends TileEntity
{
    @Override
    public void updateEntity() {
        if (this.worldObj != null && !this.worldObj.isRemote && this.worldObj.getTotalWorldTime() % 20L == 0L) {
            this.blockType = this.getBlockType();
            if (this.blockType != null && this.blockType instanceof BlockDaylightDetector) {
                ((BlockDaylightDetector)this.blockType).updateLightLevel(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
        }
    }
}
