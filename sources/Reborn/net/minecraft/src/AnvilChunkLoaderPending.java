package net.minecraft.src;

class AnvilChunkLoaderPending
{
    public final ChunkCoordIntPair chunkCoordinate;
    public final NBTTagCompound nbtTags;
    
    public AnvilChunkLoaderPending(final ChunkCoordIntPair par1ChunkCoordIntPair, final NBTTagCompound par2NBTTagCompound) {
        this.chunkCoordinate = par1ChunkCoordIntPair;
        this.nbtTags = par2NBTTagCompound;
    }
}
