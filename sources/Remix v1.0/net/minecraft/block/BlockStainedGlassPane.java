package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public class BlockStainedGlassPane extends BlockPane
{
    public static final PropertyEnum field_176245_a;
    
    public BlockStainedGlassPane() {
        super(Material.glass, false);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockStainedGlassPane.NORTH, false).withProperty(BlockStainedGlassPane.EAST, false).withProperty(BlockStainedGlassPane.SOUTH, false).withProperty(BlockStainedGlassPane.WEST, false).withProperty(BlockStainedGlassPane.field_176245_a, EnumDyeColor.WHITE));
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public int damageDropped(final IBlockState state) {
        return ((EnumDyeColor)state.getValue(BlockStainedGlassPane.field_176245_a)).func_176765_a();
    }
    
    @Override
    public void getSubBlocks(final Item itemIn, final CreativeTabs tab, final List list) {
        for (int var4 = 0; var4 < EnumDyeColor.values().length; ++var4) {
            list.add(new ItemStack(itemIn, 1, var4));
        }
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.TRANSLUCENT;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int meta) {
        return this.getDefaultState().withProperty(BlockStainedGlassPane.field_176245_a, EnumDyeColor.func_176764_b(meta));
    }
    
    @Override
    public int getMetaFromState(final IBlockState state) {
        return ((EnumDyeColor)state.getValue(BlockStainedGlassPane.field_176245_a)).func_176765_a();
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockStainedGlassPane.NORTH, BlockStainedGlassPane.EAST, BlockStainedGlassPane.WEST, BlockStainedGlassPane.SOUTH, BlockStainedGlassPane.field_176245_a });
    }
    
    @Override
    public void onBlockAdded(final World worldIn, final BlockPos pos, final IBlockState state) {
        if (!worldIn.isRemote) {
            BlockBeacon.func_176450_d(worldIn, pos);
        }
    }
    
    @Override
    public void breakBlock(final World worldIn, final BlockPos pos, final IBlockState state) {
        if (!worldIn.isRemote) {
            BlockBeacon.func_176450_d(worldIn, pos);
        }
    }
    
    static {
        field_176245_a = PropertyEnum.create("color", EnumDyeColor.class);
    }
}
