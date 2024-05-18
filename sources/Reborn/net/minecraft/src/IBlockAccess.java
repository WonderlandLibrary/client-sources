package net.minecraft.src;

public interface IBlockAccess
{
    int getBlockId(final int p0, final int p1, final int p2);
    
    TileEntity getBlockTileEntity(final int p0, final int p1, final int p2);
    
    int getLightBrightnessForSkyBlocks(final int p0, final int p1, final int p2, final int p3);
    
    float getBrightness(final int p0, final int p1, final int p2, final int p3);
    
    float getLightBrightness(final int p0, final int p1, final int p2);
    
    int getBlockMetadata(final int p0, final int p1, final int p2);
    
    Material getBlockMaterial(final int p0, final int p1, final int p2);
    
    boolean isBlockOpaqueCube(final int p0, final int p1, final int p2);
    
    boolean isBlockNormalCube(final int p0, final int p1, final int p2);
    
    boolean isAirBlock(final int p0, final int p1, final int p2);
    
    BiomeGenBase getBiomeGenForCoords(final int p0, final int p1);
    
    int getHeight();
    
    boolean extendedLevelsInChunkCache();
    
    boolean doesBlockHaveSolidTopSurface(final int p0, final int p1, final int p2);
    
    Vec3Pool getWorldVec3Pool();
    
    int isBlockProvidingPowerTo(final int p0, final int p1, final int p2, final int p3);
}
