package net.minecraft.world.gen.feature;

import net.minecraft.block.state.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;

public class WorldGenMegaJungle extends WorldGenHugeTrees
{
    public WorldGenMegaJungle(final boolean b, final int n, final int n2, final IBlockState blockState, final IBlockState blockState2) {
        super(b, n, n2, blockState, blockState2);
    }
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        final int func_150533_a = this.func_150533_a(random);
        if (!this.func_175929_a(world, random, blockPos, func_150533_a)) {
            return "".length() != 0;
        }
        this.func_175930_c(world, blockPos.up(func_150533_a), "  ".length());
        int i = blockPos.getY() + func_150533_a - "  ".length() - random.nextInt(0x50 ^ 0x54);
        "".length();
        if (true != true) {
            throw null;
        }
        while (i > blockPos.getY() + func_150533_a / "  ".length()) {
            final float n = random.nextFloat() * 3.1415927f * 2.0f;
            int n2 = blockPos.getX() + (int)(0.5f + MathHelper.cos(n) * 4.0f);
            int n3 = blockPos.getZ() + (int)(0.5f + MathHelper.sin(n) * 4.0f);
            int j = "".length();
            "".length();
            if (2 < 2) {
                throw null;
            }
            while (j < (0x6C ^ 0x69)) {
                n2 = blockPos.getX() + (int)(1.5f + MathHelper.cos(n) * j);
                n3 = blockPos.getZ() + (int)(1.5f + MathHelper.sin(n) * j);
                this.setBlockAndNotifyAdequately(world, new BlockPos(n2, i - "   ".length() + j / "  ".length(), n3), this.woodMetadata);
                ++j;
            }
            final int n4 = " ".length() + random.nextInt("  ".length());
            final int n5 = i;
            int k = i - n4;
            "".length();
            if (2 == 1) {
                throw null;
            }
            while (k <= n5) {
                this.func_175928_b(world, new BlockPos(n2, k, n3), " ".length() - (k - n5));
                ++k;
            }
            i -= "  ".length() + random.nextInt(0x18 ^ 0x1C);
        }
        int l = "".length();
        "".length();
        if (3 <= 2) {
            throw null;
        }
        while (l < func_150533_a) {
            final BlockPos up = blockPos.up(l);
            if (this.func_150523_a(world.getBlockState(up).getBlock())) {
                this.setBlockAndNotifyAdequately(world, up, this.woodMetadata);
                if (l > 0) {
                    this.func_181632_a(world, random, up.west(), BlockVine.EAST);
                    this.func_181632_a(world, random, up.north(), BlockVine.SOUTH);
                }
            }
            if (l < func_150533_a - " ".length()) {
                final BlockPos east = up.east();
                if (this.func_150523_a(world.getBlockState(east).getBlock())) {
                    this.setBlockAndNotifyAdequately(world, east, this.woodMetadata);
                    if (l > 0) {
                        this.func_181632_a(world, random, east.east(), BlockVine.WEST);
                        this.func_181632_a(world, random, east.north(), BlockVine.SOUTH);
                    }
                }
                final BlockPos east2 = up.south().east();
                if (this.func_150523_a(world.getBlockState(east2).getBlock())) {
                    this.setBlockAndNotifyAdequately(world, east2, this.woodMetadata);
                    if (l > 0) {
                        this.func_181632_a(world, random, east2.east(), BlockVine.WEST);
                        this.func_181632_a(world, random, east2.south(), BlockVine.NORTH);
                    }
                }
                final BlockPos south = up.south();
                if (this.func_150523_a(world.getBlockState(south).getBlock())) {
                    this.setBlockAndNotifyAdequately(world, south, this.woodMetadata);
                    if (l > 0) {
                        this.func_181632_a(world, random, south.west(), BlockVine.EAST);
                        this.func_181632_a(world, random, south.south(), BlockVine.NORTH);
                    }
                }
            }
            ++l;
        }
        return " ".length() != 0;
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
            if (1 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private void func_181632_a(final World world, final Random random, final BlockPos blockPos, final PropertyBool propertyBool) {
        if (random.nextInt("   ".length()) > 0 && world.isAirBlock(blockPos)) {
            this.setBlockAndNotifyAdequately(world, blockPos, Blocks.vine.getDefaultState().withProperty((IProperty<Comparable>)propertyBool, (boolean)(" ".length() != 0)));
        }
    }
    
    private void func_175930_c(final World world, final BlockPos blockPos, final int n) {
        int i = -"  ".length();
        "".length();
        if (true != true) {
            throw null;
        }
        while (i <= 0) {
            this.func_175925_a(world, blockPos.up(i), n + " ".length() - i);
            ++i;
        }
    }
}
