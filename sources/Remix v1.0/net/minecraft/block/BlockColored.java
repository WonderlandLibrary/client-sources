package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;

public class BlockColored extends Block
{
    public static final PropertyEnum COLOR;
    
    public BlockColored(final Material p_i45398_1_) {
        super(p_i45398_1_);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockColored.COLOR, EnumDyeColor.WHITE));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public int damageDropped(final IBlockState state) {
        return ((EnumDyeColor)state.getValue(BlockColored.COLOR)).func_176765_a();
    }
    
    @Override
    public void getSubBlocks(final Item itemIn, final CreativeTabs tab, final List list) {
        for (final EnumDyeColor var7 : EnumDyeColor.values()) {
            list.add(new ItemStack(itemIn, 1, var7.func_176765_a()));
        }
    }
    
    @Override
    public MapColor getMapColor(final IBlockState state) {
        return ((EnumDyeColor)state.getValue(BlockColored.COLOR)).func_176768_e();
    }
    
    @Override
    public IBlockState getStateFromMeta(final int meta) {
        return this.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.func_176764_b(meta));
    }
    
    @Override
    public int getMetaFromState(final IBlockState state) {
        return ((EnumDyeColor)state.getValue(BlockColored.COLOR)).func_176765_a();
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockColored.COLOR });
    }
    
    static {
        COLOR = PropertyEnum.create("color", EnumDyeColor.class);
    }
}
