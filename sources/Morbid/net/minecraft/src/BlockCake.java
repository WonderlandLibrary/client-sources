package net.minecraft.src;

import java.util.*;

public class BlockCake extends Block
{
    private Icon cakeTopIcon;
    private Icon cakeBottomIcon;
    private Icon field_94382_c;
    
    protected BlockCake(final int par1) {
        super(par1, Material.cake);
        this.setTickRandomly(true);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        final int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        final float var6 = 0.0625f;
        final float var7 = (1 + var5 * 2) / 16.0f;
        final float var8 = 0.5f;
        this.setBlockBounds(var7, 0.0f, var6, 1.0f - var6, var8, 1.0f - var6);
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        final float var1 = 0.0625f;
        final float var2 = 0.5f;
        this.setBlockBounds(var1, 0.0f, var1, 1.0f - var1, var2, 1.0f - var1);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World par1World, final int par2, final int par3, final int par4) {
        final int var5 = par1World.getBlockMetadata(par2, par3, par4);
        final float var6 = 0.0625f;
        final float var7 = (1 + var5 * 2) / 16.0f;
        final float var8 = 0.5f;
        return AxisAlignedBB.getAABBPool().getAABB(par2 + var7, par3, par4 + var6, par2 + 1 - var6, par3 + var8 - var6, par4 + 1 - var6);
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(final World par1World, final int par2, final int par3, final int par4) {
        final int var5 = par1World.getBlockMetadata(par2, par3, par4);
        final float var6 = 0.0625f;
        final float var7 = (1 + var5 * 2) / 16.0f;
        final float var8 = 0.5f;
        return AxisAlignedBB.getAABBPool().getAABB(par2 + var7, par3, par4 + var6, par2 + 1 - var6, par3 + var8, par4 + 1 - var6);
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        return (par1 == 1) ? this.cakeTopIcon : ((par1 == 0) ? this.cakeBottomIcon : ((par2 > 0 && par1 == 4) ? this.field_94382_c : this.blockIcon));
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon("cake_side");
        this.field_94382_c = par1IconRegister.registerIcon("cake_inner");
        this.cakeTopIcon = par1IconRegister.registerIcon("cake_top");
        this.cakeBottomIcon = par1IconRegister.registerIcon("cake_bottom");
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean onBlockActivated(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer, final int par6, final float par7, final float par8, final float par9) {
        this.eatCakeSlice(par1World, par2, par3, par4, par5EntityPlayer);
        return true;
    }
    
    @Override
    public void onBlockClicked(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer) {
        this.eatCakeSlice(par1World, par2, par3, par4, par5EntityPlayer);
    }
    
    private void eatCakeSlice(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer) {
        if (par5EntityPlayer.canEat(false)) {
            par5EntityPlayer.getFoodStats().addStats(2, 0.1f);
            final int var6 = par1World.getBlockMetadata(par2, par3, par4) + 1;
            if (var6 >= 6) {
                par1World.setBlockToAir(par2, par3, par4);
            }
            else {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, var6, 2);
            }
        }
    }
    
    @Override
    public boolean canPlaceBlockAt(final World par1World, final int par2, final int par3, final int par4) {
        return super.canPlaceBlockAt(par1World, par2, par3, par4) && this.canBlockStay(par1World, par2, par3, par4);
    }
    
    @Override
    public void onNeighborBlockChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        if (!this.canBlockStay(par1World, par2, par3, par4)) {
            par1World.setBlockToAir(par2, par3, par4);
        }
    }
    
    @Override
    public boolean canBlockStay(final World par1World, final int par2, final int par3, final int par4) {
        return par1World.getBlockMaterial(par2, par3 - 1, par4).isSolid();
    }
    
    @Override
    public int quantityDropped(final Random par1Random) {
        return 0;
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return 0;
    }
    
    @Override
    public int idPicked(final World par1World, final int par2, final int par3, final int par4) {
        return Item.cake.itemID;
    }
}
