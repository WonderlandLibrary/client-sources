package net.minecraft.src;

public class ChunkCoordinates implements Comparable
{
    public int posX;
    public int posY;
    public int posZ;
    
    public ChunkCoordinates() {
    }
    
    public ChunkCoordinates(final int par1, final int par2, final int par3) {
        this.posX = par1;
        this.posY = par2;
        this.posZ = par3;
    }
    
    public ChunkCoordinates(final ChunkCoordinates par1ChunkCoordinates) {
        this.posX = par1ChunkCoordinates.posX;
        this.posY = par1ChunkCoordinates.posY;
        this.posZ = par1ChunkCoordinates.posZ;
    }
    
    @Override
    public boolean equals(final Object par1Obj) {
        if (!(par1Obj instanceof ChunkCoordinates)) {
            return false;
        }
        final ChunkCoordinates var2 = (ChunkCoordinates)par1Obj;
        return this.posX == var2.posX && this.posY == var2.posY && this.posZ == var2.posZ;
    }
    
    @Override
    public int hashCode() {
        return this.posX + this.posZ << 8 + this.posY << 16;
    }
    
    public int compareChunkCoordinate(final ChunkCoordinates par1ChunkCoordinates) {
        return (this.posY == par1ChunkCoordinates.posY) ? ((this.posZ == par1ChunkCoordinates.posZ) ? (this.posX - par1ChunkCoordinates.posX) : (this.posZ - par1ChunkCoordinates.posZ)) : (this.posY - par1ChunkCoordinates.posY);
    }
    
    public void set(final int par1, final int par2, final int par3) {
        this.posX = par1;
        this.posY = par2;
        this.posZ = par3;
    }
    
    public float getDistanceSquared(final int par1, final int par2, final int par3) {
        final int var4 = this.posX - par1;
        final int var5 = this.posY - par2;
        final int var6 = this.posZ - par3;
        return var4 * var4 + var5 * var5 + var6 * var6;
    }
    
    public float getDistanceSquaredToChunkCoordinates(final ChunkCoordinates par1ChunkCoordinates) {
        return this.getDistanceSquared(par1ChunkCoordinates.posX, par1ChunkCoordinates.posY, par1ChunkCoordinates.posZ);
    }
    
    @Override
    public int compareTo(final Object par1Obj) {
        return this.compareChunkCoordinate((ChunkCoordinates)par1Obj);
    }
}
