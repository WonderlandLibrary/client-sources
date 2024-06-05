package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;

public static class BlockBannerHanging extends BlockBanner
{
    public BlockBannerHanging() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockBannerHanging.FACING_PROP, EnumFacing.NORTH));
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess access, final BlockPos pos) {
        final EnumFacing var3 = (EnumFacing)access.getBlockState(pos).getValue(BlockBannerHanging.FACING_PROP);
        final float var4 = 0.0f;
        final float var5 = 0.78125f;
        final float var6 = 0.0f;
        final float var7 = 1.0f;
        final float var8 = 0.125f;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        switch (SwitchEnumFacing.SWITCH_MAP[var3.ordinal()]) {
            default: {
                this.setBlockBounds(var6, var4, 1.0f - var8, var7, var5, 1.0f);
                break;
            }
            case 2: {
                this.setBlockBounds(var6, var4, 0.0f, var7, var5, var8);
                break;
            }
            case 3: {
                this.setBlockBounds(1.0f - var8, var4, var6, 1.0f, var5, var7);
                break;
            }
            case 4: {
                this.setBlockBounds(0.0f, var4, var6, var8, var5, var7);
                break;
            }
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        final EnumFacing var5 = (EnumFacing)state.getValue(BlockBannerHanging.FACING_PROP);
        if (!worldIn.getBlockState(pos.offset(var5.getOpposite())).getBlock().getMaterial().isSolid()) {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }
        super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int meta) {
        EnumFacing var2 = EnumFacing.getFront(meta);
        if (var2.getAxis() == EnumFacing.Axis.Y) {
            var2 = EnumFacing.NORTH;
        }
        return this.getDefaultState().withProperty(BlockBannerHanging.FACING_PROP, var2);
    }
    
    @Override
    public int getMetaFromState(final IBlockState state) {
        return ((EnumFacing)state.getValue(BlockBannerHanging.FACING_PROP)).getIndex();
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockBannerHanging.FACING_PROP });
    }
}
