package net.minecraft.src;

import java.util.*;

public class BlockLadder extends Block
{
    protected BlockLadder(final int par1) {
        super(par1, Material.circuits);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World par1World, final int par2, final int par3, final int par4) {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(final World par1World, final int par2, final int par3, final int par4) {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        this.updateLadderBounds(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
    }
    
    public void updateLadderBounds(final int par1) {
        final float var3 = 0.125f;
        if (par1 == 2) {
            this.setBlockBounds(0.0f, 0.0f, 1.0f - var3, 1.0f, 1.0f, 1.0f);
        }
        if (par1 == 3) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, var3);
        }
        if (par1 == 4) {
            this.setBlockBounds(1.0f - var3, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        if (par1 == 5) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, var3, 1.0f, 1.0f);
        }
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
        return 8;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World par1World, final int par2, final int par3, final int par4) {
        return par1World.isBlockNormalCube(par2 - 1, par3, par4) || par1World.isBlockNormalCube(par2 + 1, par3, par4) || par1World.isBlockNormalCube(par2, par3, par4 - 1) || par1World.isBlockNormalCube(par2, par3, par4 + 1);
    }
    
    @Override
    public int onBlockPlaced(final World par1World, final int par2, final int par3, final int par4, final int par5, final float par6, final float par7, final float par8, final int par9) {
        int var10 = par9;
        if ((par9 == 0 || par5 == 2) && par1World.isBlockNormalCube(par2, par3, par4 + 1)) {
            var10 = 2;
        }
        if ((var10 == 0 || par5 == 3) && par1World.isBlockNormalCube(par2, par3, par4 - 1)) {
            var10 = 3;
        }
        if ((var10 == 0 || par5 == 4) && par1World.isBlockNormalCube(par2 + 1, par3, par4)) {
            var10 = 4;
        }
        if ((var10 == 0 || par5 == 5) && par1World.isBlockNormalCube(par2 - 1, par3, par4)) {
            var10 = 5;
        }
        return var10;
    }
    
    @Override
    public void onNeighborBlockChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        final int var6 = par1World.getBlockMetadata(par2, par3, par4);
        boolean var7 = false;
        if (var6 == 2 && par1World.isBlockNormalCube(par2, par3, par4 + 1)) {
            var7 = true;
        }
        if (var6 == 3 && par1World.isBlockNormalCube(par2, par3, par4 - 1)) {
            var7 = true;
        }
        if (var6 == 4 && par1World.isBlockNormalCube(par2 + 1, par3, par4)) {
            var7 = true;
        }
        if (var6 == 5 && par1World.isBlockNormalCube(par2 - 1, par3, par4)) {
            var7 = true;
        }
        if (!var7) {
            this.dropBlockAsItem(par1World, par2, par3, par4, var6, 0);
            par1World.setBlockToAir(par2, par3, par4);
        }
        super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
    }
    
    @Override
    public int quantityDropped(final Random par1Random) {
        return 1;
    }
}
