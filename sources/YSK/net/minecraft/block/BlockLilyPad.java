package net.minecraft.block;

import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.creativetab.*;

public class BlockLilyPad extends BlockBush
{
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected boolean canPlaceBlockOn(final Block block) {
        if (block == Blocks.water) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public int getRenderColor(final IBlockState blockState) {
        return 7304124 + 3709613 - 6245464 + 2687307;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return "".length();
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos, final int n) {
        return 1943736 + 272813 - 1866603 + 1780022;
    }
    
    @Override
    public int getBlockColor() {
        return 5822634 + 6177245 - 4646263 + 101964;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return new AxisAlignedBB(blockPos.getX() + this.minX, blockPos.getY() + this.minY, blockPos.getZ() + this.minZ, blockPos.getX() + this.maxX, blockPos.getY() + this.maxY, blockPos.getZ() + this.maxZ);
    }
    
    @Override
    public boolean canBlockStay(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (blockPos.getY() < 0 || blockPos.getY() >= 88 + 38 + 80 + 50) {
            return "".length() != 0;
        }
        final IBlockState blockState2 = world.getBlockState(blockPos.down());
        if (blockState2.getBlock().getMaterial() == Material.water && blockState2.getValue((IProperty<Integer>)BlockLiquid.LEVEL) == 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void addCollisionBoxesToList(final World world, final BlockPos blockPos, final IBlockState blockState, final AxisAlignedBB axisAlignedBB, final List<AxisAlignedBB> list, final Entity entity) {
        if (entity == null || !(entity instanceof EntityBoat)) {
            super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        }
    }
    
    protected BlockLilyPad() {
        final float n = 0.5f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, 0.015625f, 0.5f + n);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
}
