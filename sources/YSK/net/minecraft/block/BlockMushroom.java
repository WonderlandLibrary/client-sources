package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.world.gen.feature.*;
import java.util.*;
import net.minecraft.block.properties.*;

public class BlockMushroom extends BlockBush implements IGrowable
{
    @Override
    protected boolean canPlaceBlockOn(final Block block) {
        return block.isFullBlock();
    }
    
    public boolean generateBigMushroom(final World world, final BlockPos blockToAir, final IBlockState blockState, final Random random) {
        world.setBlockToAir(blockToAir);
        WorldGenerator worldGenerator = null;
        if (this == Blocks.brown_mushroom) {
            worldGenerator = new WorldGenBigMushroom(Blocks.brown_mushroom_block);
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else if (this == Blocks.red_mushroom) {
            worldGenerator = new WorldGenBigMushroom(Blocks.red_mushroom_block);
        }
        if (worldGenerator != null && worldGenerator.generate(world, random, blockToAir)) {
            return " ".length() != 0;
        }
        world.setBlockState(blockToAir, blockState, "   ".length());
        return "".length() != 0;
    }
    
    @Override
    public void updateTick(final World world, BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (random.nextInt(0xAB ^ 0xB2) == 0) {
            int n = 0xB1 ^ 0xB4;
            final Iterator<BlockPos.MutableBlockPos> iterator = BlockPos.getAllInBoxMutable(blockPos.add(-(0x2B ^ 0x2F), -" ".length(), -(0x21 ^ 0x25)), blockPos.add(0xB8 ^ 0xBC, " ".length(), 0x97 ^ 0x93)).iterator();
            "".length();
            if (2 != 2) {
                throw null;
            }
            while (iterator.hasNext()) {
                if (world.getBlockState(iterator.next()).getBlock() == this && --n <= 0) {
                    return;
                }
            }
            BlockPos blockPos2 = blockPos.add(random.nextInt("   ".length()) - " ".length(), random.nextInt("  ".length()) - random.nextInt("  ".length()), random.nextInt("   ".length()) - " ".length());
            int i = "".length();
            "".length();
            if (4 <= -1) {
                throw null;
            }
            while (i < (0x9D ^ 0x99)) {
                if (world.isAirBlock(blockPos2) && this.canBlockStay(world, blockPos2, this.getDefaultState())) {
                    blockPos = blockPos2;
                }
                blockPos2 = blockPos.add(random.nextInt("   ".length()) - " ".length(), random.nextInt("  ".length()) - random.nextInt("  ".length()), random.nextInt("   ".length()) - " ".length());
                ++i;
            }
            if (world.isAirBlock(blockPos2) && this.canBlockStay(world, blockPos2, this.getDefaultState())) {
                world.setBlockState(blockPos2, this.getDefaultState(), "  ".length());
            }
        }
    }
    
    protected BlockMushroom() {
        final float n = 0.2f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, n * 2.0f, 0.5f + n);
        this.setTickRandomly(" ".length() != 0);
    }
    
    @Override
    public boolean canBlockStay(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (blockPos.getY() >= 0 && blockPos.getY() < 47 + 88 + 12 + 109) {
            final IBlockState blockState2 = world.getBlockState(blockPos.down());
            int n;
            if (blockState2.getBlock() == Blocks.mycelium) {
                n = " ".length();
                "".length();
                if (0 >= 1) {
                    throw null;
                }
            }
            else if (blockState2.getBlock() == Blocks.dirt && blockState2.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.PODZOL) {
                n = " ".length();
                "".length();
                if (0 == 4) {
                    throw null;
                }
            }
            else if (world.getLight(blockPos) < (0x52 ^ 0x5F) && this.canPlaceBlockOn(blockState2.getBlock())) {
                n = " ".length();
                "".length();
                if (2 == 3) {
                    throw null;
                }
            }
            else {
                n = "".length();
            }
            return n != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public boolean canUseBonemeal(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        if (random.nextFloat() < 0.4) {
            return " ".length() != 0;
        }
        return "".length() != 0;
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
            if (1 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean canGrow(final World world, final BlockPos blockPos, final IBlockState blockState, final boolean b) {
        return " ".length() != 0;
    }
    
    @Override
    public void grow(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        this.generateBigMushroom(world, blockPos, blockState, random);
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        if (super.canPlaceBlockAt(world, blockPos) && this.canBlockStay(world, blockPos, this.getDefaultState())) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
}
