package net.minecraft.src;

public class ChunkCoordIntPair
{
    public final int chunkXPos;
    public final int chunkZPos;
    
    public ChunkCoordIntPair(final int par1, final int par2) {
        this.chunkXPos = par1;
        this.chunkZPos = par2;
    }
    
    public static long chunkXZ2Int(final int par0, final int par1) {
        return (par0 & 0xFFFFFFFFL) | (par1 & 0xFFFFFFFFL) << 32;
    }
    
    @Override
    public int hashCode() {
        final long var1 = chunkXZ2Int(this.chunkXPos, this.chunkZPos);
        final int var2 = (int)var1;
        final int var3 = (int)(var1 >> 32);
        return var2 ^ var3;
    }
    
    @Override
    public boolean equals(final Object par1Obj) {
        final ChunkCoordIntPair var2 = (ChunkCoordIntPair)par1Obj;
        return var2.chunkXPos == this.chunkXPos && var2.chunkZPos == this.chunkZPos;
    }
    
    public int getCenterXPos() {
        return (this.chunkXPos << 4) + 8;
    }
    
    public int getCenterZPosition() {
        return (this.chunkZPos << 4) + 8;
    }
    
    public ChunkPosition getChunkPosition(final int par1) {
        return new ChunkPosition(this.getCenterXPos(), par1, this.getCenterZPosition());
    }
    
    @Override
    public String toString() {
        return "[" + this.chunkXPos + ", " + this.chunkZPos + "]";
    }
}
