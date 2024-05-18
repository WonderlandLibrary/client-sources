package net.minecraft.src;

public class TileEntityEnderChest extends TileEntity
{
    public float lidAngle;
    public float prevLidAngle;
    public int numUsingPlayers;
    private int ticksSinceSync;
    
    @Override
    public void updateEntity() {
        super.updateEntity();
        if (++this.ticksSinceSync % 20 * 4 == 0) {
            this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, Block.enderChest.blockID, 1, this.numUsingPlayers);
        }
        this.prevLidAngle = this.lidAngle;
        final float var1 = 0.1f;
        if (this.numUsingPlayers > 0 && this.lidAngle == 0.0f) {
            final double var2 = this.xCoord + 0.5;
            final double var3 = this.zCoord + 0.5;
            this.worldObj.playSoundEffect(var2, this.yCoord + 0.5, var3, "random.chestopen", 0.5f, this.worldObj.rand.nextFloat() * 0.1f + 0.9f);
        }
        if ((this.numUsingPlayers == 0 && this.lidAngle > 0.0f) || (this.numUsingPlayers > 0 && this.lidAngle < 1.0f)) {
            final float var4 = this.lidAngle;
            if (this.numUsingPlayers > 0) {
                this.lidAngle += var1;
            }
            else {
                this.lidAngle -= var1;
            }
            if (this.lidAngle > 1.0f) {
                this.lidAngle = 1.0f;
            }
            final float var5 = 0.5f;
            if (this.lidAngle < var5 && var4 >= var5) {
                final double var3 = this.xCoord + 0.5;
                final double var6 = this.zCoord + 0.5;
                this.worldObj.playSoundEffect(var3, this.yCoord + 0.5, var6, "random.chestclosed", 0.5f, this.worldObj.rand.nextFloat() * 0.1f + 0.9f);
            }
            if (this.lidAngle < 0.0f) {
                this.lidAngle = 0.0f;
            }
        }
    }
    
    @Override
    public boolean receiveClientEvent(final int par1, final int par2) {
        if (par1 == 1) {
            this.numUsingPlayers = par2;
            return true;
        }
        return super.receiveClientEvent(par1, par2);
    }
    
    @Override
    public void invalidate() {
        this.updateContainingBlockInfo();
        super.invalidate();
    }
    
    public void openChest() {
        ++this.numUsingPlayers;
        this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, Block.enderChest.blockID, 1, this.numUsingPlayers);
    }
    
    public void closeChest() {
        --this.numUsingPlayers;
        this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, Block.enderChest.blockID, 1, this.numUsingPlayers);
    }
    
    public boolean isUseableByPlayer(final EntityPlayer par1EntityPlayer) {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && par1EntityPlayer.getDistanceSq(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5) <= 64.0;
    }
}
