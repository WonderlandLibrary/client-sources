package net.minecraft.src;

public class VillageDoorInfo
{
    public final int posX;
    public final int posY;
    public final int posZ;
    public final int insideDirectionX;
    public final int insideDirectionZ;
    public int lastActivityTimestamp;
    public boolean isDetachedFromVillageFlag;
    private int doorOpeningRestrictionCounter;
    
    public VillageDoorInfo(final int par1, final int par2, final int par3, final int par4, final int par5, final int par6) {
        this.isDetachedFromVillageFlag = false;
        this.doorOpeningRestrictionCounter = 0;
        this.posX = par1;
        this.posY = par2;
        this.posZ = par3;
        this.insideDirectionX = par4;
        this.insideDirectionZ = par5;
        this.lastActivityTimestamp = par6;
    }
    
    public int getDistanceSquared(final int par1, final int par2, final int par3) {
        final int var4 = par1 - this.posX;
        final int var5 = par2 - this.posY;
        final int var6 = par3 - this.posZ;
        return var4 * var4 + var5 * var5 + var6 * var6;
    }
    
    public int getInsideDistanceSquare(final int par1, final int par2, final int par3) {
        final int var4 = par1 - this.posX - this.insideDirectionX;
        final int var5 = par2 - this.posY;
        final int var6 = par3 - this.posZ - this.insideDirectionZ;
        return var4 * var4 + var5 * var5 + var6 * var6;
    }
    
    public int getInsidePosX() {
        return this.posX + this.insideDirectionX;
    }
    
    public int getInsidePosY() {
        return this.posY;
    }
    
    public int getInsidePosZ() {
        return this.posZ + this.insideDirectionZ;
    }
    
    public boolean isInside(final int par1, final int par2) {
        final int var3 = par1 - this.posX;
        final int var4 = par2 - this.posZ;
        return var3 * this.insideDirectionX + var4 * this.insideDirectionZ >= 0;
    }
    
    public void resetDoorOpeningRestrictionCounter() {
        this.doorOpeningRestrictionCounter = 0;
    }
    
    public void incrementDoorOpeningRestrictionCounter() {
        ++this.doorOpeningRestrictionCounter;
    }
    
    public int getDoorOpeningRestrictionCounter() {
        return this.doorOpeningRestrictionCounter;
    }
}
