package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class BlockHay extends BlockRotatedPillar
{
    public BlockHay() {
        super(Material.grass);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockHay.field_176298_M, EnumFacing.Axis.Y));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int meta) {
        EnumFacing.Axis var2 = EnumFacing.Axis.Y;
        final int var3 = meta & 0xC;
        if (var3 == 4) {
            var2 = EnumFacing.Axis.X;
        }
        else if (var3 == 8) {
            var2 = EnumFacing.Axis.Z;
        }
        return this.getDefaultState().withProperty(BlockHay.field_176298_M, var2);
    }
    
    @Override
    public int getMetaFromState(final IBlockState state) {
        int var2 = 0;
        final EnumFacing.Axis var3 = (EnumFacing.Axis)state.getValue(BlockHay.field_176298_M);
        if (var3 == EnumFacing.Axis.X) {
            var2 |= 0x4;
        }
        else if (var3 == EnumFacing.Axis.Z) {
            var2 |= 0x8;
        }
        return var2;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockHay.field_176298_M });
    }
    
    @Override
    protected ItemStack createStackedBlock(final IBlockState state) {
        return new ItemStack(Item.getItemFromBlock(this), 1, 0);
    }
    
    @Override
    public IBlockState onBlockPlaced(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(BlockHay.field_176298_M, facing.getAxis());
    }
}
