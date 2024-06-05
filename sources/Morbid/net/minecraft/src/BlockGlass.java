package net.minecraft.src;

import java.util.*;

public class BlockGlass extends BlockBreakable
{
    public BlockGlass(final int par1, final Material par2Material, final boolean par3) {
        super(par1, "glass", par2Material, par3);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public int quantityDropped(final Random par1Random) {
        return 0;
    }
    
    @Override
    public int getRenderBlockPass() {
        return 0;
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
    protected boolean canSilkHarvest() {
        return true;
    }
}
