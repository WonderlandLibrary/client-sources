package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;

public class BlockStainedGlass extends BlockBreakable
{
    public static final PropertyEnum field_176547_a;
    
    public BlockStainedGlass(final Material p_i45427_1_) {
        super(p_i45427_1_, false);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockStainedGlass.field_176547_a, EnumDyeColor.WHITE));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public int damageDropped(final IBlockState state) {
        return ((EnumDyeColor)state.getValue(BlockStainedGlass.field_176547_a)).func_176765_a();
    }
    
    @Override
    public void getSubBlocks(final Item itemIn, final CreativeTabs tab, final List list) {
        for (final EnumDyeColor var7 : EnumDyeColor.values()) {
            list.add(new ItemStack(itemIn, 1, var7.func_176765_a()));
        }
    }
    
    @Override
    public MapColor getMapColor(final IBlockState state) {
        return ((EnumDyeColor)state.getValue(BlockStainedGlass.field_176547_a)).func_176768_e();
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.TRANSLUCENT;
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return 0;
    }
    
    @Override
    protected boolean canSilkHarvest() {
        return true;
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int meta) {
        return this.getDefaultState().withProperty(BlockStainedGlass.field_176547_a, EnumDyeColor.func_176764_b(meta));
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
    
    @Override
    public int getMetaFromState(final IBlockState state) {
        return ((EnumDyeColor)state.getValue(BlockStainedGlass.field_176547_a)).func_176765_a();
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockStainedGlass.field_176547_a });
    }
    
    static {
        field_176547_a = PropertyEnum.create("color", EnumDyeColor.class);
    }
}
