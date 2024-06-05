package net.minecraft.world.chunk.storage;

import net.minecraft.nbt.*;

public static class AnvilConverterData
{
    public final int x;
    public final int z;
    public long lastUpdated;
    public boolean terrainPopulated;
    public byte[] heightmap;
    public NibbleArrayReader blockLight;
    public NibbleArrayReader skyLight;
    public NibbleArrayReader data;
    public byte[] blocks;
    public NBTTagList entities;
    public NBTTagList tileEntities;
    public NBTTagList tileTicks;
    
    public AnvilConverterData(final int p_i1999_1_, final int p_i1999_2_) {
        this.x = p_i1999_1_;
        this.z = p_i1999_2_;
    }
}
