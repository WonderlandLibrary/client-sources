package net.minecraft.src;

import java.util.*;

public class BlockSign extends BlockContainer
{
    private Class signEntityClass;
    private boolean isFreestanding;
    
    protected BlockSign(final int par1, final Class par2Class, final boolean par3) {
        super(par1, Material.wood);
        this.isFreestanding = par3;
        this.signEntityClass = par2Class;
        final float var4 = 0.25f;
        final float var5 = 1.0f;
        this.setBlockBounds(0.5f - var4, 0.0f, 0.5f - var4, 0.5f + var4, var5, 0.5f + var4);
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        return Block.planks.getBlockTextureFromSide(par1);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World par1World, final int par2, final int par3, final int par4) {
        return null;
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(final World par1World, final int par2, final int par3, final int par4) {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        if (!this.isFreestanding) {
            final int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
            final float var6 = 0.28125f;
            final float var7 = 0.78125f;
            final float var8 = 0.0f;
            final float var9 = 1.0f;
            final float var10 = 0.125f;
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            if (var5 == 2) {
                this.setBlockBounds(var8, var6, 1.0f - var10, var9, var7, 1.0f);
            }
            if (var5 == 3) {
                this.setBlockBounds(var8, var6, 0.0f, var9, var7, var10);
            }
            if (var5 == 4) {
                this.setBlockBounds(1.0f - var10, var6, var8, 1.0f, var7, var9);
            }
            if (var5 == 5) {
                this.setBlockBounds(0.0f, var6, var8, var10, var7, var9);
            }
        }
    }
    
    @Override
    public int getRenderType() {
        return -1;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public boolean getBlocksMovement(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        return true;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public TileEntity createNewTileEntity(final World par1World) {
        try {
            return this.signEntityClass.newInstance();
        }
        catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return Item.sign.itemID;
    }
    
    @Override
    public void onNeighborBlockChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        boolean var6 = false;
        if (this.isFreestanding) {
            if (!par1World.getBlockMaterial(par2, par3 - 1, par4).isSolid()) {
                var6 = true;
            }
        }
        else {
            final int var7 = par1World.getBlockMetadata(par2, par3, par4);
            var6 = true;
            if (var7 == 2 && par1World.getBlockMaterial(par2, par3, par4 + 1).isSolid()) {
                var6 = false;
            }
            if (var7 == 3 && par1World.getBlockMaterial(par2, par3, par4 - 1).isSolid()) {
                var6 = false;
            }
            if (var7 == 4 && par1World.getBlockMaterial(par2 + 1, par3, par4).isSolid()) {
                var6 = false;
            }
            if (var7 == 5 && par1World.getBlockMaterial(par2 - 1, par3, par4).isSolid()) {
                var6 = false;
            }
        }
        if (var6) {
            this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlockToAir(par2, par3, par4);
        }
        super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
    }
    
    @Override
    public int idPicked(final World par1World, final int par2, final int par3, final int par4) {
        return Item.sign.itemID;
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
    }
}
