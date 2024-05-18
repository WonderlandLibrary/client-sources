package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.init.*;
import net.minecraft.util.*;

public class BlockBush extends Block
{
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    public boolean canBlockStay(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return this.canPlaceBlockOn(world.getBlockState(blockPos.down()).getBlock());
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        this.checkAndDropBlock(world, blockPos, blockState);
    }
    
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
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        super.onNeighborBlockChange(world, blockPos, blockState, block);
        this.checkAndDropBlock(world, blockPos, blockState);
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        if (super.canPlaceBlockAt(world, blockPos) && this.canPlaceBlockOn(world.getBlockState(blockPos.down()).getBlock())) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    protected BlockBush(final Material material, final MapColor mapColor) {
        super(material, mapColor);
        this.setTickRandomly(" ".length() != 0);
        final float n = 0.2f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, n * 3.0f, 0.5f + n);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    protected void checkAndDropBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!this.canBlockStay(world, blockPos, blockState)) {
            this.dropBlockAsItem(world, blockPos, blockState, "".length());
            world.setBlockState(blockPos, Blocks.air.getDefaultState(), "   ".length());
        }
    }
    
    protected boolean canPlaceBlockOn(final Block block) {
        if (block != Blocks.grass && block != Blocks.dirt && block != Blocks.farmland) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    protected BlockBush(final Material material) {
        this(material, material.getMaterialMapColor());
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    protected BlockBush() {
        this(Material.plants);
    }
}
