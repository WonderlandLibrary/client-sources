package net.minecraft.src;

import java.util.*;

public class BlockIce extends BlockBreakable
{
    public BlockIce(final int par1) {
        super(par1, "ice", Material.ice, false);
        this.slipperiness = 0.98f;
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public int getRenderBlockPass() {
        return 1;
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        return super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, 1 - par5);
    }
    
    @Override
    public void harvestBlock(final World par1World, final EntityPlayer par2EntityPlayer, final int par3, final int par4, final int par5, final int par6) {
        par2EntityPlayer.addStat(StatList.mineBlockStatArray[this.blockID], 1);
        par2EntityPlayer.addExhaustion(0.025f);
        if (this.canSilkHarvest() && EnchantmentHelper.getSilkTouchModifier(par2EntityPlayer)) {
            final ItemStack var9 = this.createStackedBlock(par6);
            if (var9 != null) {
                this.dropBlockAsItem_do(par1World, par3, par4, par5, var9);
            }
        }
        else {
            if (par1World.provider.isHellWorld) {
                par1World.setBlockToAir(par3, par4, par5);
                return;
            }
            final int var10 = EnchantmentHelper.getFortuneModifier(par2EntityPlayer);
            this.dropBlockAsItem(par1World, par3, par4, par5, par6, var10);
            final Material var11 = par1World.getBlockMaterial(par3, par4 - 1, par5);
            if (var11.blocksMovement() || var11.isLiquid()) {
                par1World.setBlock(par3, par4, par5, Block.waterMoving.blockID);
            }
        }
    }
    
    @Override
    public int quantityDropped(final Random par1Random) {
        return 0;
    }
    
    @Override
    public void updateTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        if (par1World.getSavedLightValue(EnumSkyBlock.Block, par2, par3, par4) > 11 - Block.lightOpacity[this.blockID]) {
            if (par1World.provider.isHellWorld) {
                par1World.setBlockToAir(par2, par3, par4);
                return;
            }
            this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlock(par2, par3, par4, Block.waterStill.blockID);
        }
    }
    
    @Override
    public int getMobilityFlag() {
        return 0;
    }
}
