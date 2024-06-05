package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;

public class BlockCompressed extends Block
{
    private final MapColor mapColor;
    
    public BlockCompressed(final MapColor p_i45414_1_) {
        super(Material.iron);
        this.mapColor = p_i45414_1_;
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public MapColor getMapColor(final IBlockState state) {
        return this.mapColor;
    }
}
