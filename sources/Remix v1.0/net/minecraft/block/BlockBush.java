package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.util.*;

public class BlockBush extends Block
{
    protected BlockBush(final Material materialIn) {
        super(materialIn);
        this.setTickRandomly(true);
        final float var2 = 0.2f;
        this.setBlockBounds(0.5f - var2, 0.0f, 0.5f - var2, 0.5f + var2, var2 * 3.0f, 0.5f + var2);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    protected BlockBush() {
        this(Material.plants);
    }
    
    @Override
    public boolean canPlaceBlockAt(final World worldIn, final BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos) && this.canPlaceBlockOn(worldIn.getBlockState(pos.offsetDown()).getBlock());
    }
    
    protected boolean canPlaceBlockOn(final Block ground) {
        return ground == Blocks.grass || ground == Blocks.dirt || ground == Blocks.farmland;
    }
    
    @Override
    public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        super.onNeighborBlockChange(worldIn, pos, state, neighborBlock);
        this.func_176475_e(worldIn, pos, state);
    }
    
    @Override
    public void updateTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        this.func_176475_e(worldIn, pos, state);
    }
    
    protected void func_176475_e(final World worldIn, final BlockPos p_176475_2_, final IBlockState p_176475_3_) {
        if (!this.canBlockStay(worldIn, p_176475_2_, p_176475_3_)) {
            this.dropBlockAsItem(worldIn, p_176475_2_, p_176475_3_, 0);
            worldIn.setBlockState(p_176475_2_, Blocks.air.getDefaultState(), 3);
        }
    }
    
    public boolean canBlockStay(final World worldIn, final BlockPos p_180671_2_, final IBlockState p_180671_3_) {
        return this.canPlaceBlockOn(worldIn.getBlockState(p_180671_2_.offsetDown()).getBlock());
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World worldIn, final BlockPos pos, final IBlockState state) {
        return null;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
}
