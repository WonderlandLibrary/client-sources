package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;

public class BlockStandingSign extends BlockSign
{
    public static final PropertyInteger ROTATION_PROP;
    
    public BlockStandingSign() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockStandingSign.ROTATION_PROP, 0));
    }
    
    @Override
    public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        if (!worldIn.getBlockState(pos.offsetDown()).getBlock().getMaterial().isSolid()) {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }
        super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int meta) {
        return this.getDefaultState().withProperty(BlockStandingSign.ROTATION_PROP, meta);
    }
    
    @Override
    public int getMetaFromState(final IBlockState state) {
        return (int)state.getValue(BlockStandingSign.ROTATION_PROP);
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockStandingSign.ROTATION_PROP });
    }
    
    static {
        ROTATION_PROP = PropertyInteger.create("rotation", 0, 15);
    }
}
