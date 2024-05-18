package net.minecraft.src;

public class DestroyBlockProgress
{
    private final int miningPlayerEntId;
    private final int partialBlockX;
    private final int partialBlockY;
    private final int partialBlockZ;
    private int partialBlockProgress;
    private int createdAtCloudUpdateTick;
    
    public DestroyBlockProgress(final int par1, final int par2, final int par3, final int par4) {
        this.miningPlayerEntId = par1;
        this.partialBlockX = par2;
        this.partialBlockY = par3;
        this.partialBlockZ = par4;
    }
    
    public int getPartialBlockX() {
        return this.partialBlockX;
    }
    
    public int getPartialBlockY() {
        return this.partialBlockY;
    }
    
    public int getPartialBlockZ() {
        return this.partialBlockZ;
    }
    
    public void setPartialBlockDamage(int par1) {
        if (par1 > 10) {
            par1 = 10;
        }
        this.partialBlockProgress = par1;
    }
    
    public int getPartialBlockDamage() {
        return this.partialBlockProgress;
    }
    
    public void setCloudUpdateTick(final int par1) {
        this.createdAtCloudUpdateTick = par1;
    }
    
    public int getCreationCloudUpdateTick() {
        return this.createdAtCloudUpdateTick;
    }
}
