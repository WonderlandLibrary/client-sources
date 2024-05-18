package net.minecraft.src;

import java.util.*;

public class BlockSnow extends Block
{
    protected BlockSnow(final int par1) {
        super(par1, Material.snow);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setBlockBoundsForSnowDepth(0);
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon("snow");
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World par1World, final int par2, final int par3, final int par4) {
        final int var5 = par1World.getBlockMetadata(par2, par3, par4) & 0x7;
        final float var6 = 0.125f;
        return AxisAlignedBB.getAABBPool().getAABB(par2 + this.minX, par3 + this.minY, par4 + this.minZ, par2 + this.maxX, par3 + var5 * var6, par4 + this.maxZ);
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
    public void setBlockBoundsForItemRender() {
        this.setBlockBoundsForSnowDepth(0);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        this.setBlockBoundsForSnowDepth(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
    }
    
    protected void setBlockBoundsForSnowDepth(final int par1) {
        final int var2 = par1 & 0x7;
        final float var3 = 2 * (1 + var2) / 16.0f;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, var3, 1.0f);
    }
    
    @Override
    public boolean canPlaceBlockAt(final World par1World, final int par2, final int par3, final int par4) {
        final int var5 = par1World.getBlockId(par2, par3 - 1, par4);
        return var5 != 0 && ((var5 == this.blockID && (par1World.getBlockMetadata(par2, par3 - 1, par4) & 0x7) == 0x7) || ((var5 == Block.leaves.blockID || Block.blocksList[var5].isOpaqueCube()) && par1World.getBlockMaterial(par2, par3 - 1, par4).blocksMovement()));
    }
    
    @Override
    public void onNeighborBlockChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        this.canSnowStay(par1World, par2, par3, par4);
    }
    
    private boolean canSnowStay(final World par1World, final int par2, final int par3, final int par4) {
        if (!this.canPlaceBlockAt(par1World, par2, par3, par4)) {
            this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlockToAir(par2, par3, par4);
            return false;
        }
        return true;
    }
    
    @Override
    public void harvestBlock(final World par1World, final EntityPlayer par2EntityPlayer, final int par3, final int par4, final int par5, final int par6) {
        final int var7 = Item.snowball.itemID;
        final int var8 = par6 & 0x7;
        this.dropBlockAsItem_do(par1World, par3, par4, par5, new ItemStack(var7, var8 + 1, 0));
        par1World.setBlockToAir(par3, par4, par5);
        par2EntityPlayer.addStat(StatList.mineBlockStatArray[this.blockID], 1);
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return Item.snowball.itemID;
    }
    
    @Override
    public int quantityDropped(final Random par1Random) {
        return 0;
    }
    
    @Override
    public void updateTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        if (par1World.getSavedLightValue(EnumSkyBlock.Block, par2, par3, par4) > 11) {
            this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlockToAir(par2, par3, par4);
        }
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        return par5 == 1 || super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
    }
}
