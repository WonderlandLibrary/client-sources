package net.minecraft.world.chunk.storage;

import net.minecraft.world.*;
import net.minecraft.nbt.*;

static class PendingChunk
{
    public final ChunkCoordIntPair chunkCoordinate;
    public final NBTTagCompound nbtTags;
    
    public PendingChunk(final ChunkCoordIntPair p_i2002_1_, final NBTTagCompound p_i2002_2_) {
        this.chunkCoordinate = p_i2002_1_;
        this.nbtTags = p_i2002_2_;
    }
}
