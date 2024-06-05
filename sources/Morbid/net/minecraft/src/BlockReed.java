package net.minecraft.src;

import java.util.*;

public class BlockReed extends Block
{
    protected BlockReed(final int par1) {
        super(par1, Material.plants);
        final float var2 = 0.375f;
        this.setBlockBounds(0.5f - var2, 0.0f, 0.5f - var2, 0.5f + var2, 1.0f, 0.5f + var2);
        this.setTickRandomly(true);
    }
    
    @Override
    public void updateTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        if (par1World.isAirBlock(par2, par3 + 1, par4)) {
            int var6;
            for (var6 = 1; par1World.getBlockId(par2, par3 - var6, par4) == this.blockID; ++var6) {}
            if (var6 < 3) {
                final int var7 = par1World.getBlockMetadata(par2, par3, par4);
                if (var7 == 15) {
                    par1World.setBlock(par2, par3 + 1, par4, this.blockID);
                    par1World.setBlockMetadataWithNotify(par2, par3, par4, 0, 4);
                }
                else {
                    par1World.setBlockMetadataWithNotify(par2, par3, par4, var7 + 1, 4);
                }
            }
        }
    }
    
    @Override
    public boolean canPlaceBlockAt(final World par1World, final int par2, final int par3, final int par4) {
        final int var5 = par1World.getBlockId(par2, par3 - 1, par4);
        return var5 == this.blockID || ((var5 == Block.grass.blockID || var5 == Block.dirt.blockID || var5 == Block.sand.blockID) && (par1World.getBlockMaterial(par2 - 1, par3 - 1, par4) == Material.water || par1World.getBlockMaterial(par2 + 1, par3 - 1, par4) == Material.water || par1World.getBlockMaterial(par2, par3 - 1, par4 - 1) == Material.water || par1World.getBlockMaterial(par2, par3 - 1, par4 + 1) == Material.water));
    }
    
    @Override
    public void onNeighborBlockChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        this.checkBlockCoordValid(par1World, par2, par3, par4);
    }
    
    protected final void checkBlockCoordValid(final World par1World, final int par2, final int par3, final int par4) {
        if (!this.canBlockStay(par1World, par2, par3, par4)) {
            this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlockToAir(par2, par3, par4);
        }
    }
    
    @Override
    public boolean canBlockStay(final World par1World, final int par2, final int par3, final int par4) {
        return this.canPlaceBlockAt(par1World, par2, par3, par4);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World par1World, final int par2, final int par3, final int par4) {
        return null;
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return Item.reed.itemID;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public int getRenderType() {
        return 1;
    }
    
    @Override
    public int idPicked(final World par1World, final int par2, final int par3, final int par4) {
        return Item.reed.itemID;
    }
}
