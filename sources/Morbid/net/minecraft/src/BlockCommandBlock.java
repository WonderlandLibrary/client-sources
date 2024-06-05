package net.minecraft.src;

import java.util.*;

public class BlockCommandBlock extends BlockContainer
{
    public BlockCommandBlock(final int par1) {
        super(par1, Material.iron);
    }
    
    @Override
    public TileEntity createNewTileEntity(final World par1World) {
        return new TileEntityCommandBlock();
    }
    
    @Override
    public void onNeighborBlockChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        if (!par1World.isRemote) {
            final boolean var6 = par1World.isBlockIndirectlyGettingPowered(par2, par3, par4);
            final int var7 = par1World.getBlockMetadata(par2, par3, par4);
            final boolean var8 = (var7 & 0x1) != 0x0;
            if (var6 && !var8) {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, var7 | 0x1, 4);
                par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate(par1World));
            }
            else if (!var6 && var8) {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, var7 & 0xFFFFFFFE, 4);
            }
        }
    }
    
    @Override
    public void updateTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        final TileEntity var6 = par1World.getBlockTileEntity(par2, par3, par4);
        if (var6 != null && var6 instanceof TileEntityCommandBlock) {
            final TileEntityCommandBlock var7 = (TileEntityCommandBlock)var6;
            var7.func_96102_a(var7.executeCommandOnPowered(par1World));
            par1World.func_96440_m(par2, par3, par4, this.blockID);
        }
    }
    
    @Override
    public int tickRate(final World par1World) {
        return 1;
    }
    
    @Override
    public boolean onBlockActivated(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer, final int par6, final float par7, final float par8, final float par9) {
        final TileEntityCommandBlock var10 = (TileEntityCommandBlock)par1World.getBlockTileEntity(par2, par3, par4);
        if (var10 != null) {
            par5EntityPlayer.displayGUIEditSign(var10);
        }
        return true;
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }
    
    @Override
    public int getComparatorInputOverride(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        final TileEntity var6 = par1World.getBlockTileEntity(par2, par3, par4);
        return (var6 != null && var6 instanceof TileEntityCommandBlock) ? ((TileEntityCommandBlock)var6).func_96103_d() : 0;
    }
    
    @Override
    public void onBlockPlacedBy(final World par1World, final int par2, final int par3, final int par4, final EntityLiving par5EntityLiving, final ItemStack par6ItemStack) {
        final TileEntityCommandBlock var7 = (TileEntityCommandBlock)par1World.getBlockTileEntity(par2, par3, par4);
        if (par6ItemStack.hasDisplayName()) {
            var7.setCommandSenderName(par6ItemStack.getDisplayName());
        }
    }
}
