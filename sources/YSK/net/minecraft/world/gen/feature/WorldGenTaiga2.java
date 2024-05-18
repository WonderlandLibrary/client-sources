package net.minecraft.world.gen.feature;

import net.minecraft.block.state.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;

public class WorldGenTaiga2 extends WorldGenAbstractTree
{
    private static final IBlockState field_181645_a;
    private static final IBlockState field_181646_b;
    
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
            if (2 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        final int n = random.nextInt(0xA5 ^ 0xA1) + (0x66 ^ 0x60);
        final int n2 = " ".length() + random.nextInt("  ".length());
        final int n3 = n - n2;
        final int n4 = "  ".length() + random.nextInt("  ".length());
        int n5 = " ".length();
        if (blockPos.getY() < " ".length() || blockPos.getY() + n + " ".length() > 3 + 23 + 135 + 95) {
            return "".length() != 0;
        }
        int y = blockPos.getY();
        "".length();
        if (0 >= 4) {
            throw null;
        }
        while (y <= blockPos.getY() + " ".length() + n && n5 != 0) {
            " ".length();
            int length;
            if (y - blockPos.getY() < n2) {
                length = "".length();
                "".length();
                if (2 == 1) {
                    throw null;
                }
            }
            else {
                length = n4;
            }
            final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
            int n6 = blockPos.getX() - length;
            "".length();
            if (4 < 3) {
                throw null;
            }
            while (n6 <= blockPos.getX() + length && n5 != 0) {
                int n7 = blockPos.getZ() - length;
                "".length();
                if (2 == 3) {
                    throw null;
                }
                while (n7 <= blockPos.getZ() + length && n5 != 0) {
                    if (y >= 0 && y < 197 + 31 - 174 + 202) {
                        final Block block = world.getBlockState(mutableBlockPos.func_181079_c(n6, y, n7)).getBlock();
                        if (block.getMaterial() != Material.air && block.getMaterial() != Material.leaves) {
                            n5 = "".length();
                            "".length();
                            if (1 < -1) {
                                throw null;
                            }
                        }
                    }
                    else {
                        n5 = "".length();
                    }
                    ++n7;
                }
                ++n6;
            }
            ++y;
        }
        if (n5 == 0) {
            return "".length() != 0;
        }
        final Block block2 = world.getBlockState(blockPos.down()).getBlock();
        if ((block2 != Blocks.grass && block2 != Blocks.dirt && block2 != Blocks.farmland) || blockPos.getY() >= 193 + 197 - 298 + 164 - n - " ".length()) {
            return "".length() != 0;
        }
        this.func_175921_a(world, blockPos.down());
        int nextInt = random.nextInt("  ".length());
        int length2 = " ".length();
        int n8 = "".length();
        int i = "".length();
        "".length();
        if (0 < -1) {
            throw null;
        }
        while (i <= n3) {
            final int n9 = blockPos.getY() + n - i;
            int j = blockPos.getX() - nextInt;
            "".length();
            if (0 == 2) {
                throw null;
            }
            while (j <= blockPos.getX() + nextInt) {
                final int n10 = j - blockPos.getX();
                int k = blockPos.getZ() - nextInt;
                "".length();
                if (0 <= -1) {
                    throw null;
                }
                while (k <= blockPos.getZ() + nextInt) {
                    final int n11 = k - blockPos.getZ();
                    if (Math.abs(n10) != nextInt || Math.abs(n11) != nextInt || nextInt <= 0) {
                        final BlockPos blockPos2 = new BlockPos(j, n9, k);
                        if (!world.getBlockState(blockPos2).getBlock().isFullBlock()) {
                            this.setBlockAndNotifyAdequately(world, blockPos2, WorldGenTaiga2.field_181646_b);
                        }
                    }
                    ++k;
                }
                ++j;
            }
            if (nextInt >= length2) {
                nextInt = n8;
                n8 = " ".length();
                if (++length2 > n4) {
                    length2 = n4;
                    "".length();
                    if (-1 >= 2) {
                        throw null;
                    }
                }
            }
            else {
                ++nextInt;
            }
            ++i;
        }
        final int nextInt2 = random.nextInt("   ".length());
        int l = "".length();
        "".length();
        if (-1 == 2) {
            throw null;
        }
        while (l < n - nextInt2) {
            final Block block3 = world.getBlockState(blockPos.up(l)).getBlock();
            if (block3.getMaterial() == Material.air || block3.getMaterial() == Material.leaves) {
                this.setBlockAndNotifyAdequately(world, blockPos.up(l), WorldGenTaiga2.field_181645_a);
            }
            ++l;
        }
        return " ".length() != 0;
    }
    
    static {
        field_181645_a = Blocks.log.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.SPRUCE);
        field_181646_b = Blocks.leaves.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.SPRUCE).withProperty((IProperty<Comparable>)BlockLeaves.CHECK_DECAY, "".length() != 0);
    }
    
    public WorldGenTaiga2(final boolean b) {
        super(b);
    }
}
