package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.util.*;

public class BlockGlass extends BlockBreakable
{
    public BlockGlass(final Material p_i45408_1_, final boolean p_i45408_2_) {
        super(p_i45408_1_, p_i45408_2_);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return 0;
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    protected boolean canSilkHarvest() {
        return true;
    }
}
