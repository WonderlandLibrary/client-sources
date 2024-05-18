package net.minecraft.src;

import java.util.*;

public class EmptyChunk extends Chunk
{
    public EmptyChunk(final World par1World, final int par2, final int par3) {
        super(par1World, par2, par3);
    }
    
    @Override
    public boolean isAtLocation(final int par1, final int par2) {
        return par1 == this.xPosition && par2 == this.zPosition;
    }
    
    @Override
    public int getHeightValue(final int par1, final int par2) {
        return 0;
    }
    
    @Override
    public void generateHeightMap() {
    }
    
    @Override
    public void generateSkylightMap() {
    }
    
    @Override
    public int getBlockID(final int par1, final int par2, final int par3) {
        return 0;
    }
    
    @Override
    public int getBlockLightOpacity(final int par1, final int par2, final int par3) {
        return 255;
    }
    
    @Override
    public boolean setBlockIDWithMetadata(final int par1, final int par2, final int par3, final int par4, final int par5) {
        return true;
    }
    
    @Override
    public int getBlockMetadata(final int par1, final int par2, final int par3) {
        return 0;
    }
    
    @Override
    public boolean setBlockMetadata(final int par1, final int par2, final int par3, final int par4) {
        return false;
    }
    
    @Override
    public int getSavedLightValue(final EnumSkyBlock par1EnumSkyBlock, final int par2, final int par3, final int par4) {
        return 0;
    }
    
    @Override
    public void setLightValue(final EnumSkyBlock par1EnumSkyBlock, final int par2, final int par3, final int par4, final int par5) {
    }
    
    @Override
    public int getBlockLightValue(final int par1, final int par2, final int par3, final int par4) {
        return 0;
    }
    
    @Override
    public void addEntity(final Entity par1Entity) {
    }
    
    @Override
    public void removeEntity(final Entity par1Entity) {
    }
    
    @Override
    public void removeEntityAtIndex(final Entity par1Entity, final int par2) {
    }
    
    @Override
    public boolean canBlockSeeTheSky(final int par1, final int par2, final int par3) {
        return false;
    }
    
    @Override
    public TileEntity getChunkBlockTileEntity(final int par1, final int par2, final int par3) {
        return null;
    }
    
    @Override
    public void addTileEntity(final TileEntity par1TileEntity) {
    }
    
    @Override
    public void setChunkBlockTileEntity(final int par1, final int par2, final int par3, final TileEntity par4TileEntity) {
    }
    
    @Override
    public void removeChunkBlockTileEntity(final int par1, final int par2, final int par3) {
    }
    
    @Override
    public void onChunkLoad() {
    }
    
    @Override
    public void onChunkUnload() {
    }
    
    @Override
    public void setChunkModified() {
    }
    
    @Override
    public void getEntitiesWithinAABBForEntity(final Entity par1Entity, final AxisAlignedBB par2AxisAlignedBB, final List par3List, final IEntitySelector par4IEntitySelector) {
    }
    
    @Override
    public void getEntitiesOfTypeWithinAAAB(final Class par1Class, final AxisAlignedBB par2AxisAlignedBB, final List par3List, final IEntitySelector par4IEntitySelector) {
    }
    
    @Override
    public boolean needsSaving(final boolean par1) {
        return false;
    }
    
    @Override
    public Random getRandomWithSeed(final long par1) {
        return new Random(this.worldObj.getSeed() + this.xPosition * this.xPosition * 4987142 + this.xPosition * 5947611 + this.zPosition * this.zPosition * 4392871L + this.zPosition * 389711 ^ par1);
    }
    
    @Override
    public boolean isEmpty() {
        return true;
    }
    
    @Override
    public boolean getAreLevelsEmpty(final int par1, final int par2) {
        return true;
    }
}
