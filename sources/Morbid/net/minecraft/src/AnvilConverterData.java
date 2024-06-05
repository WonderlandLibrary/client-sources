package net.minecraft.src;

public class AnvilConverterData
{
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
    public final int x;
    public final int z;
    
    public AnvilConverterData(final int par1, final int par2) {
        this.x = par1;
        this.z = par2;
    }
}
