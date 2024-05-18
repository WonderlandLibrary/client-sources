package net.minecraft.world.gen.feature;

import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.util.*;

public class WorldGenMegaPineTree extends WorldGenHugeTrees
{
    private static final IBlockState field_181635_g;
    private static final IBlockState field_181634_f;
    private static final IBlockState field_181633_e;
    private boolean useBaseHeight;
    
    public WorldGenMegaPineTree(final boolean b, final boolean useBaseHeight) {
        super(b, 0x7B ^ 0x76, 0x57 ^ 0x58, WorldGenMegaPineTree.field_181633_e, WorldGenMegaPineTree.field_181634_f);
        this.useBaseHeight = useBaseHeight;
    }
    
    private void func_175934_c(final World world, final BlockPos blockPos) {
        int i = "  ".length();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (i >= -"   ".length()) {
            final BlockPos up = blockPos.up(i);
            final Block block = world.getBlockState(up).getBlock();
            if (block == Blocks.grass || block == Blocks.dirt) {
                this.setBlockAndNotifyAdequately(world, up, WorldGenMegaPineTree.field_181635_g);
                "".length();
                if (0 == -1) {
                    throw null;
                }
                break;
            }
            else if (block.getMaterial() != Material.air && i < 0) {
                "".length();
                if (4 < 3) {
                    throw null;
                }
                break;
            }
            else {
                --i;
            }
        }
    }
    
    static {
        field_181633_e = Blocks.log.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.SPRUCE);
        field_181634_f = Blocks.leaves.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.SPRUCE).withProperty((IProperty<Comparable>)BlockLeaves.CHECK_DECAY, "".length() != 0);
        field_181635_g = Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL);
    }
    
    @Override
    public void func_180711_a(final World world, final Random random, final BlockPos blockPos) {
        this.func_175933_b(world, blockPos.west().north());
        this.func_175933_b(world, blockPos.east("  ".length()).north());
        this.func_175933_b(world, blockPos.west().south("  ".length()));
        this.func_175933_b(world, blockPos.east("  ".length()).south("  ".length()));
        int i = "".length();
        "".length();
        if (true != true) {
            throw null;
        }
        while (i < (0x41 ^ 0x44)) {
            final int nextInt = random.nextInt(0x48 ^ 0x8);
            final int n = nextInt % (0xBD ^ 0xB5);
            final int n2 = nextInt / (0x21 ^ 0x29);
            if (n == 0 || n == (0x3E ^ 0x39) || n2 == 0 || n2 == (0x93 ^ 0x94)) {
                this.func_175933_b(world, blockPos.add(-"   ".length() + n, "".length(), -"   ".length() + n2));
            }
            ++i;
        }
    }
    
    private void func_150541_c(final World world, final int n, final int n2, final int n3, final int n4, final Random random) {
        final int nextInt = random.nextInt(0x5A ^ 0x5F);
        int n5;
        if (this.useBaseHeight) {
            n5 = this.baseHeight;
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        else {
            n5 = "   ".length();
        }
        final int n6 = nextInt + n5;
        int length = "".length();
        int i = n3 - n6;
        "".length();
        if (3 <= 2) {
            throw null;
        }
        while (i <= n3) {
            final int n7 = n3 - i;
            final int n8 = n4 + MathHelper.floor_float(n7 / n6 * 3.5f);
            final BlockPos blockPos = new BlockPos(n, i, n2);
            final int n9 = n8;
            int n10;
            if (n7 > 0 && n8 == length && (i & " ".length()) == 0x0) {
                n10 = " ".length();
                "".length();
                if (3 <= 1) {
                    throw null;
                }
            }
            else {
                n10 = "".length();
            }
            this.func_175925_a(world, blockPos, n9 + n10);
            length = n8;
            ++i;
        }
    }
    
    private void func_175933_b(final World world, final BlockPos blockPos) {
        int i = -"  ".length();
        "".length();
        if (4 == 1) {
            throw null;
        }
        while (i <= "  ".length()) {
            int j = -"  ".length();
            "".length();
            if (0 >= 4) {
                throw null;
            }
            while (j <= "  ".length()) {
                if (Math.abs(i) != "  ".length() || Math.abs(j) != "  ".length()) {
                    this.func_175934_c(world, blockPos.add(i, "".length(), j));
                }
                ++j;
            }
            ++i;
        }
    }
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        final int func_150533_a = this.func_150533_a(random);
        if (!this.func_175929_a(world, random, blockPos, func_150533_a)) {
            return "".length() != 0;
        }
        this.func_150541_c(world, blockPos.getX(), blockPos.getZ(), blockPos.getY() + func_150533_a, "".length(), random);
        int i = "".length();
        "".length();
        if (2 < 0) {
            throw null;
        }
        while (i < func_150533_a) {
            final Block block = world.getBlockState(blockPos.up(i)).getBlock();
            if (block.getMaterial() == Material.air || block.getMaterial() == Material.leaves) {
                this.setBlockAndNotifyAdequately(world, blockPos.up(i), this.woodMetadata);
            }
            if (i < func_150533_a - " ".length()) {
                final Block block2 = world.getBlockState(blockPos.add(" ".length(), i, "".length())).getBlock();
                if (block2.getMaterial() == Material.air || block2.getMaterial() == Material.leaves) {
                    this.setBlockAndNotifyAdequately(world, blockPos.add(" ".length(), i, "".length()), this.woodMetadata);
                }
                final Block block3 = world.getBlockState(blockPos.add(" ".length(), i, " ".length())).getBlock();
                if (block3.getMaterial() == Material.air || block3.getMaterial() == Material.leaves) {
                    this.setBlockAndNotifyAdequately(world, blockPos.add(" ".length(), i, " ".length()), this.woodMetadata);
                }
                final Block block4 = world.getBlockState(blockPos.add("".length(), i, " ".length())).getBlock();
                if (block4.getMaterial() == Material.air || block4.getMaterial() == Material.leaves) {
                    this.setBlockAndNotifyAdequately(world, blockPos.add("".length(), i, " ".length()), this.woodMetadata);
                }
            }
            ++i;
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
            if (4 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
