package net.minecraft.src;

import java.util.*;

public class BlockEnderChest extends BlockContainer
{
    protected BlockEnderChest(final int par1) {
        super(par1, Material.rock);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.875f, 0.9375f);
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
        return 22;
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return Block.obsidian.blockID;
    }
    
    @Override
    public int quantityDropped(final Random par1Random) {
        return 8;
    }
    
    @Override
    protected boolean canSilkHarvest() {
        return true;
    }
    
    @Override
    public void onBlockPlacedBy(final World par1World, final int par2, final int par3, final int par4, final EntityLiving par5EntityLiving, final ItemStack par6ItemStack) {
        byte var7 = 0;
        final int var8 = MathHelper.floor_double(par5EntityLiving.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
        if (var8 == 0) {
            var7 = 2;
        }
        if (var8 == 1) {
            var7 = 5;
        }
        if (var8 == 2) {
            var7 = 3;
        }
        if (var8 == 3) {
            var7 = 4;
        }
        par1World.setBlockMetadataWithNotify(par2, par3, par4, var7, 2);
    }
    
    @Override
    public boolean onBlockActivated(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer, final int par6, final float par7, final float par8, final float par9) {
        final InventoryEnderChest var10 = par5EntityPlayer.getInventoryEnderChest();
        final TileEntityEnderChest var11 = (TileEntityEnderChest)par1World.getBlockTileEntity(par2, par3, par4);
        if (var10 == null || var11 == null) {
            return true;
        }
        if (par1World.isBlockNormalCube(par2, par3 + 1, par4)) {
            return true;
        }
        if (par1World.isRemote) {
            return true;
        }
        var10.setAssociatedChest(var11);
        par5EntityPlayer.displayGUIChest(var10);
        return true;
    }
    
    @Override
    public TileEntity createNewTileEntity(final World par1World) {
        return new TileEntityEnderChest();
    }
    
    @Override
    public void randomDisplayTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        for (int var6 = 0; var6 < 3; ++var6) {
            double var7 = par2 + par5Random.nextFloat();
            final double var8 = par3 + par5Random.nextFloat();
            var7 = par4 + par5Random.nextFloat();
            double var9 = 0.0;
            double var10 = 0.0;
            double var11 = 0.0;
            final int var12 = par5Random.nextInt(2) * 2 - 1;
            final int var13 = par5Random.nextInt(2) * 2 - 1;
            var9 = (par5Random.nextFloat() - 0.5) * 0.125;
            var10 = (par5Random.nextFloat() - 0.5) * 0.125;
            var11 = (par5Random.nextFloat() - 0.5) * 0.125;
            final double var14 = par4 + 0.5 + 0.25 * var13;
            var11 = par5Random.nextFloat() * 1.0f * var13;
            final double var15 = par2 + 0.5 + 0.25 * var12;
            var9 = par5Random.nextFloat() * 1.0f * var12;
            par1World.spawnParticle("portal", var15, var8, var14, var9, var10, var11);
        }
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon("obsidian");
    }
}
