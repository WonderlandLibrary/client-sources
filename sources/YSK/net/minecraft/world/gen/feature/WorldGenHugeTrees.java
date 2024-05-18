package net.minecraft.world.gen.feature;

import net.minecraft.block.state.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import java.util.*;

public abstract class WorldGenHugeTrees extends WorldGenAbstractTree
{
    protected final int baseHeight;
    protected int extraRandomHeight;
    protected final IBlockState woodMetadata;
    protected final IBlockState leavesMetadata;
    
    private boolean func_175927_a(final BlockPos blockPos, final World world) {
        final BlockPos down = blockPos.down();
        final Block block = world.getBlockState(down).getBlock();
        if ((block == Blocks.grass || block == Blocks.dirt) && blockPos.getY() >= "  ".length()) {
            this.func_175921_a(world, down);
            this.func_175921_a(world, down.east());
            this.func_175921_a(world, down.south());
            this.func_175921_a(world, down.south().east());
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    protected void func_175925_a(final World world, final BlockPos blockPos, final int n) {
        final int n2 = n * n;
        int i = -n;
        "".length();
        if (3 == 2) {
            throw null;
        }
        while (i <= n + " ".length()) {
            int j = -n;
            "".length();
            if (1 >= 3) {
                throw null;
            }
            while (j <= n + " ".length()) {
                final int n3 = i - " ".length();
                final int n4 = j - " ".length();
                if (i * i + j * j <= n2 || n3 * n3 + n4 * n4 <= n2 || i * i + n4 * n4 <= n2 || n3 * n3 + j * j <= n2) {
                    final BlockPos add = blockPos.add(i, "".length(), j);
                    final Material material = world.getBlockState(add).getBlock().getMaterial();
                    if (material == Material.air || material == Material.leaves) {
                        this.setBlockAndNotifyAdequately(world, add, this.leavesMetadata);
                    }
                }
                ++j;
            }
            ++i;
        }
    }
    
    protected void func_175928_b(final World world, final BlockPos blockPos, final int n) {
        final int n2 = n * n;
        int i = -n;
        "".length();
        if (0 == 2) {
            throw null;
        }
        while (i <= n) {
            int j = -n;
            "".length();
            if (4 < 1) {
                throw null;
            }
            while (j <= n) {
                if (i * i + j * j <= n2) {
                    final BlockPos add = blockPos.add(i, "".length(), j);
                    final Material material = world.getBlockState(add).getBlock().getMaterial();
                    if (material == Material.air || material == Material.leaves) {
                        this.setBlockAndNotifyAdequately(world, add, this.leavesMetadata);
                    }
                }
                ++j;
            }
            ++i;
        }
    }
    
    private boolean func_175926_c(final World world, final BlockPos blockPos, final int n) {
        int n2 = " ".length();
        if (blockPos.getY() < " ".length() || blockPos.getY() + n + " ".length() > 180 + 145 - 300 + 231) {
            return "".length() != 0;
        }
        int i = "".length();
        "".length();
        if (false) {
            throw null;
        }
        while (i <= " ".length() + n) {
            int n3 = "  ".length();
            if (i == 0) {
                n3 = " ".length();
                "".length();
                if (-1 == 0) {
                    throw null;
                }
            }
            else if (i >= " ".length() + n - "  ".length()) {
                n3 = "  ".length();
            }
            int n4 = -n3;
            "".length();
            if (3 != 3) {
                throw null;
            }
            while (n4 <= n3 && n2 != 0) {
                int n5 = -n3;
                "".length();
                if (2 >= 4) {
                    throw null;
                }
                while (n5 <= n3 && n2 != 0) {
                    if (blockPos.getY() + i < 0 || blockPos.getY() + i >= 127 + 187 - 211 + 153 || !this.func_150523_a(world.getBlockState(blockPos.add(n4, i, n5)).getBlock())) {
                        n2 = "".length();
                    }
                    ++n5;
                }
                ++n4;
            }
            ++i;
        }
        return n2 != 0;
    }
    
    public WorldGenHugeTrees(final boolean b, final int baseHeight, final int extraRandomHeight, final IBlockState woodMetadata, final IBlockState leavesMetadata) {
        super(b);
        this.baseHeight = baseHeight;
        this.extraRandomHeight = extraRandomHeight;
        this.woodMetadata = woodMetadata;
        this.leavesMetadata = leavesMetadata;
    }
    
    protected int func_150533_a(final Random random) {
        int n = random.nextInt("   ".length()) + this.baseHeight;
        if (this.extraRandomHeight > " ".length()) {
            n += random.nextInt(this.extraRandomHeight);
        }
        return n;
    }
    
    protected boolean func_175929_a(final World world, final Random random, final BlockPos blockPos, final int n) {
        if (this.func_175926_c(world, blockPos, n) && this.func_175927_a(blockPos, world)) {
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
            if (0 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
}
