package net.minecraft.world.gen.feature;

import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import java.util.*;

public class WorldGenTrees extends WorldGenAbstractTree
{
    private final IBlockState metaLeaves;
    private static final IBlockState field_181653_a;
    private final boolean vinesGrow;
    private final IBlockState metaWood;
    private final int minTreeHeight;
    private static final IBlockState field_181654_b;
    
    public WorldGenTrees(final boolean b) {
        this(b, 0x22 ^ 0x26, WorldGenTrees.field_181653_a, WorldGenTrees.field_181654_b, "".length() != 0);
    }
    
    private void func_181650_b(final World world, BlockPos blockPos, final PropertyBool propertyBool) {
        this.func_181651_a(world, blockPos, propertyBool);
        int n = 0x8A ^ 0x8E;
        blockPos = blockPos.down();
        "".length();
        if (1 == 4) {
            throw null;
        }
        while (world.getBlockState(blockPos).getBlock().getMaterial() == Material.air && n > 0) {
            this.func_181651_a(world, blockPos, propertyBool);
            blockPos = blockPos.down();
            --n;
        }
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
    
    private void func_181651_a(final World world, final BlockPos blockPos, final PropertyBool propertyBool) {
        this.setBlockAndNotifyAdequately(world, blockPos, Blocks.vine.getDefaultState().withProperty((IProperty<Comparable>)propertyBool, (boolean)(" ".length() != 0)));
    }
    
    private void func_181652_a(final World world, final int n, final BlockPos blockPos, final EnumFacing enumFacing) {
        this.setBlockAndNotifyAdequately(world, blockPos, Blocks.cocoa.getDefaultState().withProperty((IProperty<Comparable>)BlockCocoa.AGE, n).withProperty((IProperty<Comparable>)BlockCocoa.FACING, enumFacing));
    }
    
    static {
        field_181653_a = Blocks.log.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.OAK);
        field_181654_b = Blocks.leaves.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.OAK).withProperty((IProperty<Comparable>)BlockLeaves.CHECK_DECAY, "".length() != 0);
    }
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        final int n = random.nextInt("   ".length()) + this.minTreeHeight;
        int n2 = " ".length();
        if (blockPos.getY() < " ".length() || blockPos.getY() + n + " ".length() > 90 + 84 - 58 + 140) {
            return "".length() != 0;
        }
        int i = blockPos.getY();
        "".length();
        if (-1 != -1) {
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
            if (-1 >= 3) {
                throw null;
            }
            while (n4 <= blockPos.getX() + n3 && n2 != 0) {
                int n5 = blockPos.getZ() - n3;
                "".length();
                if (0 <= -1) {
                    throw null;
                }
                while (n5 <= blockPos.getZ() + n3 && n2 != 0) {
                    if (i >= 0 && i < 59 + 151 + 30 + 16) {
                        if (!this.func_150523_a(world.getBlockState(mutableBlockPos.func_181079_c(n4, i, n5)).getBlock())) {
                            n2 = "".length();
                            "".length();
                            if (3 <= 0) {
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
        if ((block != Blocks.grass && block != Blocks.dirt && block != Blocks.farmland) || blockPos.getY() >= 187 + 200 - 219 + 88 - n - " ".length()) {
            return "".length() != 0;
        }
        this.func_175921_a(world, blockPos.down());
        final int length = "   ".length();
        final int length2 = "".length();
        int j = blockPos.getY() - length + n;
        "".length();
        if (3 < 1) {
            throw null;
        }
        while (j <= blockPos.getY() + n) {
            final int n6 = j - (blockPos.getY() + n);
            final int n7 = length2 + " ".length() - n6 / "  ".length();
            int k = blockPos.getX() - n7;
            "".length();
            if (2 == 1) {
                throw null;
            }
            while (k <= blockPos.getX() + n7) {
                final int n8 = k - blockPos.getX();
                int l = blockPos.getZ() - n7;
                "".length();
                if (4 <= 2) {
                    throw null;
                }
                while (l <= blockPos.getZ() + n7) {
                    final int n9 = l - blockPos.getZ();
                    if (Math.abs(n8) != n7 || Math.abs(n9) != n7 || (random.nextInt("  ".length()) != 0 && n6 != 0)) {
                        final BlockPos blockPos2 = new BlockPos(k, j, l);
                        final Block block2 = world.getBlockState(blockPos2).getBlock();
                        if (block2.getMaterial() == Material.air || block2.getMaterial() == Material.leaves || block2.getMaterial() == Material.vine) {
                            this.setBlockAndNotifyAdequately(world, blockPos2, this.metaLeaves);
                        }
                    }
                    ++l;
                }
                ++k;
            }
            ++j;
        }
        int length3 = "".length();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (length3 < n) {
            final Block block3 = world.getBlockState(blockPos.up(length3)).getBlock();
            if (block3.getMaterial() == Material.air || block3.getMaterial() == Material.leaves || block3.getMaterial() == Material.vine) {
                this.setBlockAndNotifyAdequately(world, blockPos.up(length3), this.metaWood);
                if (this.vinesGrow && length3 > 0) {
                    if (random.nextInt("   ".length()) > 0 && world.isAirBlock(blockPos.add(-" ".length(), length3, "".length()))) {
                        this.func_181651_a(world, blockPos.add(-" ".length(), length3, "".length()), BlockVine.EAST);
                    }
                    if (random.nextInt("   ".length()) > 0 && world.isAirBlock(blockPos.add(" ".length(), length3, "".length()))) {
                        this.func_181651_a(world, blockPos.add(" ".length(), length3, "".length()), BlockVine.WEST);
                    }
                    if (random.nextInt("   ".length()) > 0 && world.isAirBlock(blockPos.add("".length(), length3, -" ".length()))) {
                        this.func_181651_a(world, blockPos.add("".length(), length3, -" ".length()), BlockVine.SOUTH);
                    }
                    if (random.nextInt("   ".length()) > 0 && world.isAirBlock(blockPos.add("".length(), length3, " ".length()))) {
                        this.func_181651_a(world, blockPos.add("".length(), length3, " ".length()), BlockVine.NORTH);
                    }
                }
            }
            ++length3;
        }
        if (this.vinesGrow) {
            int n10 = blockPos.getY() - "   ".length() + n;
            "".length();
            if (-1 >= 0) {
                throw null;
            }
            while (n10 <= blockPos.getY() + n) {
                final int n11 = "  ".length() - (n10 - (blockPos.getY() + n)) / "  ".length();
                final BlockPos.MutableBlockPos mutableBlockPos2 = new BlockPos.MutableBlockPos();
                int n12 = blockPos.getX() - n11;
                "".length();
                if (4 <= 0) {
                    throw null;
                }
                while (n12 <= blockPos.getX() + n11) {
                    int n13 = blockPos.getZ() - n11;
                    "".length();
                    if (3 <= -1) {
                        throw null;
                    }
                    while (n13 <= blockPos.getZ() + n11) {
                        mutableBlockPos2.func_181079_c(n12, n10, n13);
                        if (world.getBlockState(mutableBlockPos2).getBlock().getMaterial() == Material.leaves) {
                            final BlockPos west = mutableBlockPos2.west();
                            final BlockPos east = mutableBlockPos2.east();
                            final BlockPos north = mutableBlockPos2.north();
                            final BlockPos south = mutableBlockPos2.south();
                            if (random.nextInt(0x17 ^ 0x13) == 0 && world.getBlockState(west).getBlock().getMaterial() == Material.air) {
                                this.func_181650_b(world, west, BlockVine.EAST);
                            }
                            if (random.nextInt(0xB0 ^ 0xB4) == 0 && world.getBlockState(east).getBlock().getMaterial() == Material.air) {
                                this.func_181650_b(world, east, BlockVine.WEST);
                            }
                            if (random.nextInt(0xE ^ 0xA) == 0 && world.getBlockState(north).getBlock().getMaterial() == Material.air) {
                                this.func_181650_b(world, north, BlockVine.SOUTH);
                            }
                            if (random.nextInt(0xB9 ^ 0xBD) == 0 && world.getBlockState(south).getBlock().getMaterial() == Material.air) {
                                this.func_181650_b(world, south, BlockVine.NORTH);
                            }
                        }
                        ++n13;
                    }
                    ++n12;
                }
                ++n10;
            }
            if (random.nextInt(0xBA ^ 0xBF) == 0 && n > (0x4A ^ 0x4F)) {
                int length4 = "".length();
                "".length();
                if (4 < 1) {
                    throw null;
                }
                while (length4 < "  ".length()) {
                    final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
                    "".length();
                    if (false == true) {
                        throw null;
                    }
                    while (iterator.hasNext()) {
                        final EnumFacing enumFacing = iterator.next();
                        if (random.nextInt((0xB ^ 0xF) - length4) == 0) {
                            final EnumFacing opposite = enumFacing.getOpposite();
                            this.func_181652_a(world, random.nextInt("   ".length()), blockPos.add(opposite.getFrontOffsetX(), n - (0xAF ^ 0xAA) + length4, opposite.getFrontOffsetZ()), enumFacing);
                        }
                    }
                    ++length4;
                }
            }
        }
        return " ".length() != 0;
    }
    
    public WorldGenTrees(final boolean b, final int minTreeHeight, final IBlockState metaWood, final IBlockState metaLeaves, final boolean vinesGrow) {
        super(b);
        this.minTreeHeight = minTreeHeight;
        this.metaWood = metaWood;
        this.metaLeaves = metaLeaves;
        this.vinesGrow = vinesGrow;
    }
}
