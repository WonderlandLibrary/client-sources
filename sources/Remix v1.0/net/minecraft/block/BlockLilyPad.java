package net.minecraft.block;

import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;

public class BlockLilyPad extends BlockBush
{
    protected BlockLilyPad() {
        final float var1 = 0.5f;
        final float var2 = 0.015625f;
        this.setBlockBounds(0.5f - var1, 0.0f, 0.5f - var1, 0.5f + var1, var2, 0.5f + var1);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public void addCollisionBoxesToList(final World worldIn, final BlockPos pos, final IBlockState state, final AxisAlignedBB mask, final List list, final Entity collidingEntity) {
        if (collidingEntity == null || !(collidingEntity instanceof EntityBoat)) {
            super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
        }
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World worldIn, final BlockPos pos, final IBlockState state) {
        return new AxisAlignedBB(pos.getX() + this.minX, pos.getY() + this.minY, pos.getZ() + this.minZ, pos.getX() + this.maxX, pos.getY() + this.maxY, pos.getZ() + this.maxZ);
    }
    
    @Override
    public int getBlockColor() {
        return 7455580;
    }
    
    @Override
    public int getRenderColor(final IBlockState state) {
        return 7455580;
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess worldIn, final BlockPos pos, final int renderPass) {
        return 2129968;
    }
    
    @Override
    protected boolean canPlaceBlockOn(final Block ground) {
        return ground == Blocks.water;
    }
    
    @Override
    public boolean canBlockStay(final World worldIn, final BlockPos p_180671_2_, final IBlockState p_180671_3_) {
        if (p_180671_2_.getY() >= 0 && p_180671_2_.getY() < 256) {
            final IBlockState var4 = worldIn.getBlockState(p_180671_2_.offsetDown());
            return var4.getBlock().getMaterial() == Material.water && (int)var4.getValue(BlockLiquid.LEVEL) == 0;
        }
        return false;
    }
    
    @Override
    public int getMetaFromState(final IBlockState state) {
        return 0;
    }
}
