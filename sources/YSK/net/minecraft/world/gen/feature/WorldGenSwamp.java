package net.minecraft.world.gen.feature;

import net.minecraft.block.state.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;

public class WorldGenSwamp extends WorldGenAbstractTree
{
    private static final IBlockState field_181648_a;
    private static final IBlockState field_181649_b;
    
    @Override
    public boolean generate(final World world, final Random random, BlockPos down) {
        final int n = random.nextInt(0x30 ^ 0x34) + (0x36 ^ 0x33);
        "".length();
        if (false) {
            throw null;
        }
        while (world.getBlockState(down.down()).getBlock().getMaterial() == Material.water) {
            down = down.down();
        }
        int n2 = " ".length();
        if (down.getY() < " ".length() || down.getY() + n + " ".length() > 151 + 141 - 75 + 39) {
            return "".length() != 0;
        }
        int i = down.getY();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (i <= down.getY() + " ".length() + n) {
            int n3 = " ".length();
            if (i == down.getY()) {
                n3 = "".length();
            }
            if (i >= down.getY() + " ".length() + n - "  ".length()) {
                n3 = "   ".length();
            }
            final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
            int n4 = down.getX() - n3;
            "".length();
            if (3 != 3) {
                throw null;
            }
            while (n4 <= down.getX() + n3 && n2 != 0) {
                int n5 = down.getZ() - n3;
                "".length();
                if (-1 >= 3) {
                    throw null;
                }
                while (n5 <= down.getZ() + n3 && n2 != 0) {
                    if (i >= 0 && i < 52 + 39 - 22 + 187) {
                        final Block block = world.getBlockState(mutableBlockPos.func_181079_c(n4, i, n5)).getBlock();
                        if (block.getMaterial() != Material.air && block.getMaterial() != Material.leaves) {
                            if (block != Blocks.water && block != Blocks.flowing_water) {
                                n2 = "".length();
                                "".length();
                                if (-1 >= 0) {
                                    throw null;
                                }
                            }
                            else if (i > down.getY()) {
                                n2 = "".length();
                                "".length();
                                if (4 != 4) {
                                    throw null;
                                }
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
        final Block block2 = world.getBlockState(down.down()).getBlock();
        if ((block2 != Blocks.grass && block2 != Blocks.dirt) || down.getY() >= 124 + 195 - 315 + 252 - n - " ".length()) {
            return "".length() != 0;
        }
        this.func_175921_a(world, down.down());
        int j = down.getY() - "   ".length() + n;
        "".length();
        if (1 < 0) {
            throw null;
        }
        while (j <= down.getY() + n) {
            final int n6 = j - (down.getY() + n);
            final int n7 = "  ".length() - n6 / "  ".length();
            int k = down.getX() - n7;
            "".length();
            if (-1 == 3) {
                throw null;
            }
            while (k <= down.getX() + n7) {
                final int n8 = k - down.getX();
                int l = down.getZ() - n7;
                "".length();
                if (-1 == 4) {
                    throw null;
                }
                while (l <= down.getZ() + n7) {
                    final int n9 = l - down.getZ();
                    if (Math.abs(n8) != n7 || Math.abs(n9) != n7 || (random.nextInt("  ".length()) != 0 && n6 != 0)) {
                        final BlockPos blockPos = new BlockPos(k, j, l);
                        if (!world.getBlockState(blockPos).getBlock().isFullBlock()) {
                            this.setBlockAndNotifyAdequately(world, blockPos, WorldGenSwamp.field_181649_b);
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
        if (-1 >= 0) {
            throw null;
        }
        while (length < n) {
            final Block block3 = world.getBlockState(down.up(length)).getBlock();
            if (block3.getMaterial() == Material.air || block3.getMaterial() == Material.leaves || block3 == Blocks.flowing_water || block3 == Blocks.water) {
                this.setBlockAndNotifyAdequately(world, down.up(length), WorldGenSwamp.field_181648_a);
            }
            ++length;
        }
        int n10 = down.getY() - "   ".length() + n;
        "".length();
        if (2 <= 1) {
            throw null;
        }
        while (n10 <= down.getY() + n) {
            final int n11 = "  ".length() - (n10 - (down.getY() + n)) / "  ".length();
            final BlockPos.MutableBlockPos mutableBlockPos2 = new BlockPos.MutableBlockPos();
            int n12 = down.getX() - n11;
            "".length();
            if (-1 >= 1) {
                throw null;
            }
            while (n12 <= down.getX() + n11) {
                int n13 = down.getZ() - n11;
                "".length();
                if (3 < 0) {
                    throw null;
                }
                while (n13 <= down.getZ() + n11) {
                    mutableBlockPos2.func_181079_c(n12, n10, n13);
                    if (world.getBlockState(mutableBlockPos2).getBlock().getMaterial() == Material.leaves) {
                        final BlockPos west = mutableBlockPos2.west();
                        final BlockPos east = mutableBlockPos2.east();
                        final BlockPos north = mutableBlockPos2.north();
                        final BlockPos south = mutableBlockPos2.south();
                        if (random.nextInt(0xC6 ^ 0xC2) == 0 && world.getBlockState(west).getBlock().getMaterial() == Material.air) {
                            this.func_181647_a(world, west, BlockVine.EAST);
                        }
                        if (random.nextInt(0x73 ^ 0x77) == 0 && world.getBlockState(east).getBlock().getMaterial() == Material.air) {
                            this.func_181647_a(world, east, BlockVine.WEST);
                        }
                        if (random.nextInt(0xA8 ^ 0xAC) == 0 && world.getBlockState(north).getBlock().getMaterial() == Material.air) {
                            this.func_181647_a(world, north, BlockVine.SOUTH);
                        }
                        if (random.nextInt(0x5 ^ 0x1) == 0 && world.getBlockState(south).getBlock().getMaterial() == Material.air) {
                            this.func_181647_a(world, south, BlockVine.NORTH);
                        }
                    }
                    ++n13;
                }
                ++n12;
            }
            ++n10;
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
            if (4 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private void func_181647_a(final World world, BlockPos blockPos, final PropertyBool propertyBool) {
        final IBlockState withProperty = Blocks.vine.getDefaultState().withProperty((IProperty<Comparable>)propertyBool, " ".length() != 0);
        this.setBlockAndNotifyAdequately(world, blockPos, withProperty);
        int n = 0x81 ^ 0x85;
        blockPos = blockPos.down();
        "".length();
        if (0 >= 2) {
            throw null;
        }
        while (world.getBlockState(blockPos).getBlock().getMaterial() == Material.air && n > 0) {
            this.setBlockAndNotifyAdequately(world, blockPos, withProperty);
            blockPos = blockPos.down();
            --n;
        }
    }
    
    public WorldGenSwamp() {
        super("".length() != 0);
    }
    
    static {
        field_181648_a = Blocks.log.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.OAK);
        field_181649_b = Blocks.leaves.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.OAK).withProperty((IProperty<Comparable>)BlockOldLeaf.CHECK_DECAY, "".length() != 0);
    }
}
