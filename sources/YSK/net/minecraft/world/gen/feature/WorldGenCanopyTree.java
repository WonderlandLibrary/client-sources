package net.minecraft.world.gen.feature;

import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.*;

public class WorldGenCanopyTree extends WorldGenAbstractTree
{
    private static final IBlockState field_181641_b;
    private static final IBlockState field_181640_a;
    
    private void func_181639_b(final World world, final BlockPos blockPos) {
        if (this.func_150523_a(world.getBlockState(blockPos).getBlock())) {
            this.setBlockAndNotifyAdequately(world, blockPos, WorldGenCanopyTree.field_181640_a);
        }
    }
    
    static {
        field_181640_a = Blocks.log2.getDefaultState().withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.DARK_OAK);
        field_181641_b = Blocks.leaves2.getDefaultState().withProperty(BlockNewLeaf.VARIANT, BlockPlanks.EnumType.DARK_OAK).withProperty((IProperty<Comparable>)BlockLeaves.CHECK_DECAY, "".length() != 0);
    }
    
    private boolean func_181638_a(final World world, final BlockPos blockPos, final int n) {
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        int i = "".length();
        "".length();
        if (-1 >= 0) {
            throw null;
        }
        while (i <= n + " ".length()) {
            int n2 = " ".length();
            if (i == 0) {
                n2 = "".length();
            }
            if (i >= n - " ".length()) {
                n2 = "  ".length();
            }
            int j = -n2;
            "".length();
            if (4 < 0) {
                throw null;
            }
            while (j <= n2) {
                int k = -n2;
                "".length();
                if (1 == 2) {
                    throw null;
                }
                while (k <= n2) {
                    if (!this.func_150523_a(world.getBlockState(mutableBlockPos.func_181079_c(x + j, y + i, z + k)).getBlock())) {
                        return "".length() != 0;
                    }
                    ++k;
                }
                ++j;
            }
            ++i;
        }
        return " ".length() != 0;
    }
    
    private void func_150526_a(final World world, final int n, final int n2, final int n3) {
        final BlockPos blockPos = new BlockPos(n, n2, n3);
        if (world.getBlockState(blockPos).getBlock().getMaterial() == Material.air) {
            this.setBlockAndNotifyAdequately(world, blockPos, WorldGenCanopyTree.field_181641_b);
        }
    }
    
    public WorldGenCanopyTree(final boolean b) {
        super(b);
    }
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        final int n = random.nextInt("   ".length()) + random.nextInt("  ".length()) + (0x7A ^ 0x7C);
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        if (y < " ".length() || y + n + " ".length() >= 192 + 118 - 268 + 214) {
            return "".length() != 0;
        }
        final BlockPos down = blockPos.down();
        final Block block = world.getBlockState(down).getBlock();
        if (block != Blocks.grass && block != Blocks.dirt) {
            return "".length() != 0;
        }
        if (!this.func_181638_a(world, blockPos, n)) {
            return "".length() != 0;
        }
        this.func_175921_a(world, down);
        this.func_175921_a(world, down.east());
        this.func_175921_a(world, down.south());
        this.func_175921_a(world, down.south().east());
        final EnumFacing random2 = EnumFacing.Plane.HORIZONTAL.random(random);
        final int n2 = n - random.nextInt(0x3C ^ 0x38);
        int n3 = "  ".length() - random.nextInt("   ".length());
        int n4 = x;
        int n5 = z;
        final int n6 = y + n - " ".length();
        int i = "".length();
        "".length();
        if (4 < 0) {
            throw null;
        }
        while (i < n) {
            if (i >= n2 && n3 > 0) {
                n4 += random2.getFrontOffsetX();
                n5 += random2.getFrontOffsetZ();
                --n3;
            }
            final BlockPos blockPos2 = new BlockPos(n4, y + i, n5);
            final Material material = world.getBlockState(blockPos2).getBlock().getMaterial();
            if (material == Material.air || material == Material.leaves) {
                this.func_181639_b(world, blockPos2);
                this.func_181639_b(world, blockPos2.east());
                this.func_181639_b(world, blockPos2.south());
                this.func_181639_b(world, blockPos2.east().south());
            }
            ++i;
        }
        int j = -"  ".length();
        "".length();
        if (4 <= -1) {
            throw null;
        }
        while (j <= 0) {
            int k = -"  ".length();
            "".length();
            if (2 <= 0) {
                throw null;
            }
            while (k <= 0) {
                final int n7 = -" ".length();
                this.func_150526_a(world, n4 + j, n6 + n7, n5 + k);
                this.func_150526_a(world, " ".length() + n4 - j, n6 + n7, n5 + k);
                this.func_150526_a(world, n4 + j, n6 + n7, " ".length() + n5 - k);
                this.func_150526_a(world, " ".length() + n4 - j, n6 + n7, " ".length() + n5 - k);
                if ((j > -"  ".length() || k > -" ".length()) && (j != -" ".length() || k != -"  ".length())) {
                    final int length = " ".length();
                    this.func_150526_a(world, n4 + j, n6 + length, n5 + k);
                    this.func_150526_a(world, " ".length() + n4 - j, n6 + length, n5 + k);
                    this.func_150526_a(world, n4 + j, n6 + length, " ".length() + n5 - k);
                    this.func_150526_a(world, " ".length() + n4 - j, n6 + length, " ".length() + n5 - k);
                }
                ++k;
            }
            ++j;
        }
        if (random.nextBoolean()) {
            this.func_150526_a(world, n4, n6 + "  ".length(), n5);
            this.func_150526_a(world, n4 + " ".length(), n6 + "  ".length(), n5);
            this.func_150526_a(world, n4 + " ".length(), n6 + "  ".length(), n5 + " ".length());
            this.func_150526_a(world, n4, n6 + "  ".length(), n5 + " ".length());
        }
        int l = -"   ".length();
        "".length();
        if (2 < -1) {
            throw null;
        }
        while (l <= (0x6C ^ 0x68)) {
            int n8 = -"   ".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
            while (n8 <= (0x59 ^ 0x5D)) {
                if ((l != -"   ".length() || n8 != -"   ".length()) && (l != -"   ".length() || n8 != (0x2A ^ 0x2E)) && (l != (0xC2 ^ 0xC6) || n8 != -"   ".length()) && (l != (0x43 ^ 0x47) || n8 != (0x6D ^ 0x69)) && (Math.abs(l) < "   ".length() || Math.abs(n8) < "   ".length())) {
                    this.func_150526_a(world, n4 + l, n6, n5 + n8);
                }
                ++n8;
            }
            ++l;
        }
        int n9 = -" ".length();
        "".length();
        if (3 < 1) {
            throw null;
        }
        while (n9 <= "  ".length()) {
            int n10 = -" ".length();
            "".length();
            if (3 < 3) {
                throw null;
            }
            while (n10 <= "  ".length()) {
                if ((n9 < 0 || n9 > " ".length() || n10 < 0 || n10 > " ".length()) && random.nextInt("   ".length()) <= 0) {
                    final int n11 = random.nextInt("   ".length()) + "  ".length();
                    int length2 = "".length();
                    "".length();
                    if (-1 == 2) {
                        throw null;
                    }
                    while (length2 < n11) {
                        this.func_181639_b(world, new BlockPos(x + n9, n6 - length2 - " ".length(), z + n10));
                        ++length2;
                    }
                    int n12 = -" ".length();
                    "".length();
                    if (2 <= -1) {
                        throw null;
                    }
                    while (n12 <= " ".length()) {
                        int n13 = -" ".length();
                        "".length();
                        if (2 <= -1) {
                            throw null;
                        }
                        while (n13 <= " ".length()) {
                            this.func_150526_a(world, n4 + n9 + n12, n6, n5 + n10 + n13);
                            ++n13;
                        }
                        ++n12;
                    }
                    int n14 = -"  ".length();
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                    while (n14 <= "  ".length()) {
                        int n15 = -"  ".length();
                        "".length();
                        if (3 != 3) {
                            throw null;
                        }
                        while (n15 <= "  ".length()) {
                            if (Math.abs(n14) != "  ".length() || Math.abs(n15) != "  ".length()) {
                                this.func_150526_a(world, n4 + n9 + n14, n6 - " ".length(), n5 + n10 + n15);
                            }
                            ++n15;
                        }
                        ++n14;
                    }
                }
                ++n10;
            }
            ++n9;
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
            if (4 != 4) {
                throw null;
            }
        }
        return sb.toString();
    }
}
