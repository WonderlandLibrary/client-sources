package net.minecraft.src;

import java.util.*;

public class BlockSnowBlock extends Block
{
    protected BlockSnowBlock(final int par1) {
        super(par1, Material.craftedSnow);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return Item.snowball.itemID;
    }
    
    @Override
    public int quantityDropped(final Random par1Random) {
        return 4;
    }
    
    @Override
    public void updateTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        if (par1World.getSavedLightValue(EnumSkyBlock.Block, par2, par3, par4) > 11) {
            this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlockToAir(par2, par3, par4);
        }
    }
}
