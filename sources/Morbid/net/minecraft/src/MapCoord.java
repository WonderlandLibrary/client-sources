package net.minecraft.src;

public class MapCoord
{
    public byte iconSize;
    public byte centerX;
    public byte centerZ;
    public byte iconRotation;
    final MapData data;
    
    public MapCoord(final MapData par1MapData, final byte par2, final byte par3, final byte par4, final byte par5) {
        this.data = par1MapData;
        this.iconSize = par2;
        this.centerX = par3;
        this.centerZ = par4;
        this.iconRotation = par5;
    }
}
