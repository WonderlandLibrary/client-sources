package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;

public class BlockCactus extends Block
{
    public static final PropertyInteger AGE_PROP;
    
    protected BlockCactus() {
        super(Material.cactus);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockCactus.AGE_PROP, 0));
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public void updateTick(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        final BlockPos var5 = pos.offsetUp();
        if (worldIn.isAirBlock(var5)) {
            int var6;
            for (var6 = 1; worldIn.getBlockState(pos.offsetDown(var6)).getBlock() == this; ++var6) {}
            if (var6 < 3) {
                final int var7 = (int)state.getValue(BlockCactus.AGE_PROP);
                if (var7 == 15) {
                    worldIn.setBlockState(var5, this.getDefaultState());
                    final IBlockState var8 = state.withProperty(BlockCactus.AGE_PROP, 0);
                    worldIn.setBlockState(pos, var8, 4);
                    this.onNeighborBlockChange(worldIn, var5, var8, this);
                }
                else {
                    worldIn.setBlockState(pos, state.withProperty(BlockCactus.AGE_PROP, var7 + 1), 4);
                }
            }
        }
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World worldIn, final BlockPos pos, final IBlockState state) {
        final float var4 = 0.0625f;
        return new AxisAlignedBB(pos.getX() + var4, pos.getY(), pos.getZ() + var4, pos.getX() + 1 - var4, pos.getY() + 1 - var4, pos.getZ() + 1 - var4);
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBox(final World worldIn, final BlockPos pos) {
        final float var3 = 0.0625f;
        return new AxisAlignedBB(pos.getX() + var3, pos.getY(), pos.getZ() + var3, pos.getX() + 1 - var3, pos.getY() + 1, pos.getZ() + 1 - var3);
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World worldIn, final BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos) && this.canBlockStay(worldIn, pos);
    }
    
    @Override
    public void onNeighborBlockChange(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        if (!this.canBlockStay(worldIn, pos)) {
            worldIn.destroyBlock(pos, true);
        }
    }
    
    public boolean canBlockStay(final World worldIn, final BlockPos p_176586_2_) {
        for (final EnumFacing var4 : EnumFacing.Plane.HORIZONTAL) {
            if (worldIn.getBlockState(p_176586_2_.offset(var4)).getBlock().getMaterial().isSolid()) {
                return false;
            }
        }
        final Block var5 = worldIn.getBlockState(p_176586_2_.offsetDown()).getBlock();
        return var5 == Blocks.cactus || var5 == Blocks.sand;
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World worldIn, final BlockPos pos, final IBlockState state, final Entity entityIn) {
        entityIn.attackEntityFrom(DamageSource.cactus, 1.0f);
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int meta) {
        return this.getDefaultState().withProperty(BlockCactus.AGE_PROP, meta);
    }
    
    @Override
    public int getMetaFromState(final IBlockState state) {
        return (int)state.getValue(BlockCactus.AGE_PROP);
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockCactus.AGE_PROP });
    }
    
    static {
        AGE_PROP = PropertyInteger.create("age", 0, 15);
    }
}
