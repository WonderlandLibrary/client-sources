package net.minecraft.world.gen.feature;

import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;

public class WorldGenTaiga1 extends WorldGenAbstractTree
{
    private static final IBlockState field_181636_a;
    private static final IBlockState field_181637_b;
    
    static {
        field_181636_a = Blocks.log.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.SPRUCE);
        field_181637_b = Blocks.leaves.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.SPRUCE).withProperty((IProperty<Comparable>)BlockLeaves.CHECK_DECAY, "".length() != 0);
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
            if (1 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        final int n = random.nextInt(0x6E ^ 0x6B) + (0x75 ^ 0x72);
        final int n2 = n - random.nextInt("  ".length()) - "   ".length();
        final int n3 = " ".length() + random.nextInt(n - n2 + " ".length());
        int n4 = " ".length();
        if (blockPos.getY() < " ".length() || blockPos.getY() + n + " ".length() > 95 + 231 - 240 + 170) {
            return "".length() != 0;
        }
        int y = blockPos.getY();
        "".length();
        if (-1 < -1) {
            throw null;
        }
        while (y <= blockPos.getY() + " ".length() + n && n4 != 0) {
            " ".length();
            int length;
            if (y - blockPos.getY() < n2) {
                length = "".length();
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else {
                length = n3;
            }
            final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
            int n5 = blockPos.getX() - length;
            "".length();
            if (1 >= 3) {
                throw null;
            }
            while (n5 <= blockPos.getX() + length && n4 != 0) {
                int n6 = blockPos.getZ() - length;
                "".length();
                if (3 < 1) {
                    throw null;
                }
                while (n6 <= blockPos.getZ() + length && n4 != 0) {
                    if (y >= 0 && y < 239 + 35 - 225 + 207) {
                        if (!this.func_150523_a(world.getBlockState(mutableBlockPos.func_181079_c(n5, y, n6)).getBlock())) {
                            n4 = "".length();
                            "".length();
                            if (-1 >= 2) {
                                throw null;
                            }
                        }
                    }
                    else {
                        n4 = "".length();
                    }
                    ++n6;
                }
                ++n5;
            }
            ++y;
        }
        if (n4 == 0) {
            return "".length() != 0;
        }
        final Block block = world.getBlockState(blockPos.down()).getBlock();
        if ((block != Blocks.grass && block != Blocks.dirt) || blockPos.getY() >= 30 + 116 - 67 + 177 - n - " ".length()) {
            return "".length() != 0;
        }
        this.func_175921_a(world, blockPos.down());
        int length2 = "".length();
        int i = blockPos.getY() + n;
        "".length();
        if (2 == 0) {
            throw null;
        }
        while (i >= blockPos.getY() + n2) {
            int j = blockPos.getX() - length2;
            "".length();
            if (-1 >= 0) {
                throw null;
            }
            while (j <= blockPos.getX() + length2) {
                final int n7 = j - blockPos.getX();
                int k = blockPos.getZ() - length2;
                "".length();
                if (2 >= 4) {
                    throw null;
                }
                while (k <= blockPos.getZ() + length2) {
                    final int n8 = k - blockPos.getZ();
                    if (Math.abs(n7) != length2 || Math.abs(n8) != length2 || length2 <= 0) {
                        final BlockPos blockPos2 = new BlockPos(j, i, k);
                        if (!world.getBlockState(blockPos2).getBlock().isFullBlock()) {
                            this.setBlockAndNotifyAdequately(world, blockPos2, WorldGenTaiga1.field_181637_b);
                        }
                    }
                    ++k;
                }
                ++j;
            }
            if (length2 >= " ".length() && i == blockPos.getY() + n2 + " ".length()) {
                --length2;
                "".length();
                if (0 >= 3) {
                    throw null;
                }
            }
            else if (length2 < n3) {
                ++length2;
            }
            --i;
        }
        int l = "".length();
        "".length();
        if (3 == 4) {
            throw null;
        }
        while (l < n - " ".length()) {
            final Block block2 = world.getBlockState(blockPos.up(l)).getBlock();
            if (block2.getMaterial() == Material.air || block2.getMaterial() == Material.leaves) {
                this.setBlockAndNotifyAdequately(world, blockPos.up(l), WorldGenTaiga1.field_181636_a);
            }
            ++l;
        }
        return " ".length() != 0;
    }
    
    public WorldGenTaiga1() {
        super("".length() != 0);
    }
}
