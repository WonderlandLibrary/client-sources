package net.minecraft.world.gen.feature;

import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;

public class WorldGenForest extends WorldGenAbstractTree
{
    private static final IBlockState field_181629_a;
    private static final IBlockState field_181630_b;
    private boolean useExtraRandomHeight;
    
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
            if (4 == 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        field_181629_a = Blocks.log.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.BIRCH);
        field_181630_b = Blocks.leaves.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.BIRCH).withProperty((IProperty<Comparable>)BlockOldLeaf.CHECK_DECAY, "".length() != 0);
    }
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        int n = random.nextInt("   ".length()) + (0x98 ^ 0x9D);
        if (this.useExtraRandomHeight) {
            n += random.nextInt(0x67 ^ 0x60);
        }
        int n2 = " ".length();
        if (blockPos.getY() < " ".length() || blockPos.getY() + n + " ".length() > 170 + 13 - 61 + 134) {
            return "".length() != 0;
        }
        int i = blockPos.getY();
        "".length();
        if (3 <= 2) {
            throw null;
        }
        while (i <= blockPos.getY() + " ".length() + n) {
            int n3 = " ".length();
            if (i == blockPos.getY()) {
                n3 = "".length();
            }
            if (i >= blockPos.getY() + " ".length() + n - "  ".length()) {
                n3 = "  ".length();
            }
            final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
            int n4 = blockPos.getX() - n3;
            "".length();
            if (-1 != -1) {
                throw null;
            }
            while (n4 <= blockPos.getX() + n3 && n2 != 0) {
                int n5 = blockPos.getZ() - n3;
                "".length();
                if (1 >= 4) {
                    throw null;
                }
                while (n5 <= blockPos.getZ() + n3 && n2 != 0) {
                    if (i >= 0 && i < 74 + 96 - 144 + 230) {
                        if (!this.func_150523_a(world.getBlockState(mutableBlockPos.func_181079_c(n4, i, n5)).getBlock())) {
                            n2 = "".length();
                            "".length();
                            if (4 <= 2) {
                                throw null;
                            }
                        }
                    }
                    else {
                        n2 = "".length();
                    }
                    ++n5;
                }
                ++n4;
            }
            ++i;
        }
        if (n2 == 0) {
            return "".length() != 0;
        }
        final Block block = world.getBlockState(blockPos.down()).getBlock();
        if ((block != Blocks.grass && block != Blocks.dirt && block != Blocks.farmland) || blockPos.getY() >= 47 + 19 + 141 + 49 - n - " ".length()) {
            return "".length() != 0;
        }
        this.func_175921_a(world, blockPos.down());
        int j = blockPos.getY() - "   ".length() + n;
        "".length();
        if (2 >= 4) {
            throw null;
        }
        while (j <= blockPos.getY() + n) {
            final int n6 = j - (blockPos.getY() + n);
            final int n7 = " ".length() - n6 / "  ".length();
            int k = blockPos.getX() - n7;
            "".length();
            if (true != true) {
                throw null;
            }
            while (k <= blockPos.getX() + n7) {
                final int n8 = k - blockPos.getX();
                int l = blockPos.getZ() - n7;
                "".length();
                if (0 <= -1) {
                    throw null;
                }
                while (l <= blockPos.getZ() + n7) {
                    final int n9 = l - blockPos.getZ();
                    if (Math.abs(n8) != n7 || Math.abs(n9) != n7 || (random.nextInt("  ".length()) != 0 && n6 != 0)) {
                        final BlockPos blockPos2 = new BlockPos(k, j, l);
                        final Block block2 = world.getBlockState(blockPos2).getBlock();
                        if (block2.getMaterial() == Material.air || block2.getMaterial() == Material.leaves) {
                            this.setBlockAndNotifyAdequately(world, blockPos2, WorldGenForest.field_181630_b);
                        }
                    }
                    ++l;
                }
                ++k;
            }
            ++j;
        }
        int length = "".length();
        "".length();
        if (1 < 0) {
            throw null;
        }
        while (length < n) {
            final Block block3 = world.getBlockState(blockPos.up(length)).getBlock();
            if (block3.getMaterial() == Material.air || block3.getMaterial() == Material.leaves) {
                this.setBlockAndNotifyAdequately(world, blockPos.up(length), WorldGenForest.field_181629_a);
            }
            ++length;
        }
        return " ".length() != 0;
    }
    
    public WorldGenForest(final boolean b, final boolean useExtraRandomHeight) {
        super(b);
        this.useExtraRandomHeight = useExtraRandomHeight;
    }
}
