package net.minecraft.world.gen.feature;

import net.minecraft.block.state.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;

public class WorldGenSavannaTree extends WorldGenAbstractTree
{
    private static final IBlockState field_181644_b;
    private static final IBlockState field_181643_a;
    
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
            if (0 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        final int n = random.nextInt("   ".length()) + random.nextInt("   ".length()) + (0x50 ^ 0x55);
        int n2 = " ".length();
        if (blockPos.getY() < " ".length() || blockPos.getY() + n + " ".length() > 192 + 220 - 374 + 218) {
            return "".length() != 0;
        }
        int i = blockPos.getY();
        "".length();
        if (2 != 2) {
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
            if (4 < 3) {
                throw null;
            }
            while (n4 <= blockPos.getX() + n3 && n2 != 0) {
                int n5 = blockPos.getZ() - n3;
                "".length();
                if (1 <= 0) {
                    throw null;
                }
                while (n5 <= blockPos.getZ() + n3 && n2 != 0) {
                    if (i >= 0 && i < 253 + 50 - 52 + 5) {
                        if (!this.func_150523_a(world.getBlockState(mutableBlockPos.func_181079_c(n4, i, n5)).getBlock())) {
                            n2 = "".length();
                            "".length();
                            if (2 == 3) {
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
        if ((block != Blocks.grass && block != Blocks.dirt) || blockPos.getY() >= 47 + 207 - 216 + 218 - n - " ".length()) {
            return "".length() != 0;
        }
        this.func_175921_a(world, blockPos.down());
        final EnumFacing random2 = EnumFacing.Plane.HORIZONTAL.random(random);
        final int n6 = n - random.nextInt(0x4 ^ 0x0) - " ".length();
        int n7 = "   ".length() - random.nextInt("   ".length());
        int x = blockPos.getX();
        int z = blockPos.getZ();
        int length = "".length();
        int j = "".length();
        "".length();
        if (2 >= 4) {
            throw null;
        }
        while (j < n) {
            final int n8 = blockPos.getY() + j;
            if (j >= n6 && n7 > 0) {
                x += random2.getFrontOffsetX();
                z += random2.getFrontOffsetZ();
                --n7;
            }
            final BlockPos blockPos2 = new BlockPos(x, n8, z);
            final Material material = world.getBlockState(blockPos2).getBlock().getMaterial();
            if (material == Material.air || material == Material.leaves) {
                this.func_181642_b(world, blockPos2);
                length = n8;
            }
            ++j;
        }
        final BlockPos blockPos3 = new BlockPos(x, length, z);
        int k = -"   ".length();
        "".length();
        if (4 == 3) {
            throw null;
        }
        while (k <= "   ".length()) {
            int l = -"   ".length();
            "".length();
            if (1 < 1) {
                throw null;
            }
            while (l <= "   ".length()) {
                if (Math.abs(k) != "   ".length() || Math.abs(l) != "   ".length()) {
                    this.func_175924_b(world, blockPos3.add(k, "".length(), l));
                }
                ++l;
            }
            ++k;
        }
        final BlockPos up = blockPos3.up();
        int n9 = -" ".length();
        "".length();
        if (false == true) {
            throw null;
        }
        while (n9 <= " ".length()) {
            int n10 = -" ".length();
            "".length();
            if (2 <= -1) {
                throw null;
            }
            while (n10 <= " ".length()) {
                this.func_175924_b(world, up.add(n9, "".length(), n10));
                ++n10;
            }
            ++n9;
        }
        this.func_175924_b(world, up.east("  ".length()));
        this.func_175924_b(world, up.west("  ".length()));
        this.func_175924_b(world, up.south("  ".length()));
        this.func_175924_b(world, up.north("  ".length()));
        int x2 = blockPos.getX();
        int z2 = blockPos.getZ();
        final EnumFacing random3 = EnumFacing.Plane.HORIZONTAL.random(random);
        if (random3 != random2) {
            final int n11 = n6 - random.nextInt("  ".length()) - " ".length();
            int n12 = " ".length() + random.nextInt("   ".length());
            int length2 = "".length();
            int n13 = n11;
            "".length();
            if (0 >= 4) {
                throw null;
            }
            while (n13 < n && n12 > 0) {
                if (n13 >= " ".length()) {
                    final int n14 = blockPos.getY() + n13;
                    x2 += random3.getFrontOffsetX();
                    z2 += random3.getFrontOffsetZ();
                    final BlockPos blockPos4 = new BlockPos(x2, n14, z2);
                    final Material material2 = world.getBlockState(blockPos4).getBlock().getMaterial();
                    if (material2 == Material.air || material2 == Material.leaves) {
                        this.func_181642_b(world, blockPos4);
                        length2 = n14;
                    }
                }
                ++n13;
                --n12;
            }
            if (length2 > 0) {
                final BlockPos blockPos5 = new BlockPos(x2, length2, z2);
                int n15 = -"  ".length();
                "".length();
                if (4 == -1) {
                    throw null;
                }
                while (n15 <= "  ".length()) {
                    int n16 = -"  ".length();
                    "".length();
                    if (3 >= 4) {
                        throw null;
                    }
                    while (n16 <= "  ".length()) {
                        if (Math.abs(n15) != "  ".length() || Math.abs(n16) != "  ".length()) {
                            this.func_175924_b(world, blockPos5.add(n15, "".length(), n16));
                        }
                        ++n16;
                    }
                    ++n15;
                }
                final BlockPos up2 = blockPos5.up();
                int n17 = -" ".length();
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
                while (n17 <= " ".length()) {
                    int n18 = -" ".length();
                    "".length();
                    if (-1 < -1) {
                        throw null;
                    }
                    while (n18 <= " ".length()) {
                        this.func_175924_b(world, up2.add(n17, "".length(), n18));
                        ++n18;
                    }
                    ++n17;
                }
            }
        }
        return " ".length() != 0;
    }
    
    private void func_175924_b(final World world, final BlockPos blockPos) {
        final Material material = world.getBlockState(blockPos).getBlock().getMaterial();
        if (material == Material.air || material == Material.leaves) {
            this.setBlockAndNotifyAdequately(world, blockPos, WorldGenSavannaTree.field_181644_b);
        }
    }
    
    public WorldGenSavannaTree(final boolean b) {
        super(b);
    }
    
    private void func_181642_b(final World world, final BlockPos blockPos) {
        this.setBlockAndNotifyAdequately(world, blockPos, WorldGenSavannaTree.field_181643_a);
    }
    
    static {
        field_181643_a = Blocks.log2.getDefaultState().withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.ACACIA);
        field_181644_b = Blocks.leaves2.getDefaultState().withProperty(BlockNewLeaf.VARIANT, BlockPlanks.EnumType.ACACIA).withProperty((IProperty<Comparable>)BlockLeaves.CHECK_DECAY, "".length() != 0);
    }
}
