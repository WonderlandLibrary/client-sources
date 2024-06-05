package net.minecraft.src;

import java.util.*;

public class BlockLilyPad extends BlockFlower
{
    protected BlockLilyPad(final int par1) {
        super(par1);
        final float var2 = 0.5f;
        final float var3 = 0.015625f;
        this.setBlockBounds(0.5f - var2, 0.0f, 0.5f - var2, 0.5f + var2, var3, 0.5f + var2);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public int getRenderType() {
        return 23;
    }
    
    @Override
    public void addCollisionBoxesToList(final World par1World, final int par2, final int par3, final int par4, final AxisAlignedBB par5AxisAlignedBB, final List par6List, final Entity par7Entity) {
        if (par7Entity == null || !(par7Entity instanceof EntityBoat)) {
            super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World par1World, final int par2, final int par3, final int par4) {
        return AxisAlignedBB.getAABBPool().getAABB(par2 + this.minX, par3 + this.minY, par4 + this.minZ, par2 + this.maxX, par3 + this.maxY, par4 + this.maxZ);
    }
    
    @Override
    public int getBlockColor() {
        return 2129968;
    }
    
    @Override
    public int getRenderColor(final int par1) {
        return 2129968;
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        return 2129968;
    }
    
    @Override
    protected boolean canThisPlantGrowOnThisBlockID(final int par1) {
        return par1 == Block.waterStill.blockID;
    }
    
    @Override
    public boolean canBlockStay(final World par1World, final int par2, final int par3, final int par4) {
        return par3 >= 0 && par3 < 256 && (par1World.getBlockMaterial(par2, par3 - 1, par4) == Material.water && par1World.getBlockMetadata(par2, par3 - 1, par4) == 0);
    }
}
