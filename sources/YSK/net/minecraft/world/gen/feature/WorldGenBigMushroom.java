package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;

public class WorldGenBigMushroom extends WorldGenerator
{
    private Block mushroomType;
    
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
            if (2 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public WorldGenBigMushroom() {
        super("".length() != 0);
    }
    
    public WorldGenBigMushroom(final Block mushroomType) {
        super(" ".length() != 0);
        this.mushroomType = mushroomType;
    }
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        if (this.mushroomType == null) {
            Block mushroomType;
            if (random.nextBoolean()) {
                mushroomType = Blocks.brown_mushroom_block;
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else {
                mushroomType = Blocks.red_mushroom_block;
            }
            this.mushroomType = mushroomType;
        }
        final int n = random.nextInt("   ".length()) + (0x7A ^ 0x7E);
        int n2 = " ".length();
        if (blockPos.getY() < " ".length() || blockPos.getY() + n + " ".length() >= 64 + 86 - 144 + 250) {
            return "".length() != 0;
        }
        int i = blockPos.getY();
        "".length();
        if (3 <= 2) {
            throw null;
        }
        while (i <= blockPos.getY() + " ".length() + n) {
            int n3 = "   ".length();
            if (i <= blockPos.getY() + "   ".length()) {
                n3 = "".length();
            }
            final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
            int n4 = blockPos.getX() - n3;
            "".length();
            if (2 != 2) {
                throw null;
            }
            while (n4 <= blockPos.getX() + n3 && n2 != 0) {
                int n5 = blockPos.getZ() - n3;
                "".length();
                if (4 != 4) {
                    throw null;
                }
                while (n5 <= blockPos.getZ() + n3 && n2 != 0) {
                    if (i >= 0 && i < 69 + 185 - 94 + 96) {
                        final Block block = world.getBlockState(mutableBlockPos.func_181079_c(n4, i, n5)).getBlock();
                        if (block.getMaterial() != Material.air && block.getMaterial() != Material.leaves) {
                            n2 = "".length();
                            "".length();
                            if (1 >= 3) {
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
        final Block block2 = world.getBlockState(blockPos.down()).getBlock();
        if (block2 != Blocks.dirt && block2 != Blocks.grass && block2 != Blocks.mycelium) {
            return "".length() != 0;
        }
        int n6 = blockPos.getY() + n;
        if (this.mushroomType == Blocks.red_mushroom_block) {
            n6 = blockPos.getY() + n - "   ".length();
        }
        int j = n6;
        "".length();
        if (0 == 4) {
            throw null;
        }
        while (j <= blockPos.getY() + n) {
            int n7 = " ".length();
            if (j < blockPos.getY() + n) {
                ++n7;
            }
            if (this.mushroomType == Blocks.brown_mushroom_block) {
                n7 = "   ".length();
            }
            final int n8 = blockPos.getX() - n7;
            final int n9 = blockPos.getX() + n7;
            final int n10 = blockPos.getZ() - n7;
            final int n11 = blockPos.getZ() + n7;
            int k = n8;
            "".length();
            if (3 != 3) {
                throw null;
            }
            while (k <= n9) {
                int l = n10;
                "".length();
                if (true != true) {
                    throw null;
                }
                while (l <= n11) {
                    int n12 = 0x60 ^ 0x65;
                    if (k == n8) {
                        --n12;
                        "".length();
                        if (3 == 0) {
                            throw null;
                        }
                    }
                    else if (k == n9) {
                        ++n12;
                    }
                    if (l == n10) {
                        n12 -= 3;
                        "".length();
                        if (2 <= -1) {
                            throw null;
                        }
                    }
                    else if (l == n11) {
                        n12 += 3;
                    }
                    BlockHugeMushroom.EnumType enumType = BlockHugeMushroom.EnumType.byMetadata(n12);
                    Label_1065: {
                        if (this.mushroomType == Blocks.brown_mushroom_block || j < blockPos.getY() + n) {
                            if (k == n8 || k == n9) {
                                if (l == n10) {
                                    break Label_1065;
                                }
                                if (l == n11) {
                                    "".length();
                                    if (1 >= 3) {
                                        throw null;
                                    }
                                    break Label_1065;
                                }
                            }
                            if (k == blockPos.getX() - (n7 - " ".length()) && l == n10) {
                                enumType = BlockHugeMushroom.EnumType.NORTH_WEST;
                            }
                            if (k == n8 && l == blockPos.getZ() - (n7 - " ".length())) {
                                enumType = BlockHugeMushroom.EnumType.NORTH_WEST;
                            }
                            if (k == blockPos.getX() + (n7 - " ".length()) && l == n10) {
                                enumType = BlockHugeMushroom.EnumType.NORTH_EAST;
                            }
                            if (k == n9 && l == blockPos.getZ() - (n7 - " ".length())) {
                                enumType = BlockHugeMushroom.EnumType.NORTH_EAST;
                            }
                            if (k == blockPos.getX() - (n7 - " ".length()) && l == n11) {
                                enumType = BlockHugeMushroom.EnumType.SOUTH_WEST;
                            }
                            if (k == n8 && l == blockPos.getZ() + (n7 - " ".length())) {
                                enumType = BlockHugeMushroom.EnumType.SOUTH_WEST;
                            }
                            if (k == blockPos.getX() + (n7 - " ".length()) && l == n11) {
                                enumType = BlockHugeMushroom.EnumType.SOUTH_EAST;
                            }
                            if (k == n9 && l == blockPos.getZ() + (n7 - " ".length())) {
                                enumType = BlockHugeMushroom.EnumType.SOUTH_EAST;
                            }
                        }
                        if (enumType == BlockHugeMushroom.EnumType.CENTER && j < blockPos.getY() + n) {
                            enumType = BlockHugeMushroom.EnumType.ALL_INSIDE;
                        }
                        if (blockPos.getY() >= blockPos.getY() + n - " ".length() || enumType != BlockHugeMushroom.EnumType.ALL_INSIDE) {
                            final BlockPos blockPos2 = new BlockPos(k, j, l);
                            if (!world.getBlockState(blockPos2).getBlock().isFullBlock()) {
                                this.setBlockAndNotifyAdequately(world, blockPos2, this.mushroomType.getDefaultState().withProperty(BlockHugeMushroom.VARIANT, enumType));
                            }
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
        if (-1 >= 4) {
            throw null;
        }
        while (length < n) {
            if (!world.getBlockState(blockPos.up(length)).getBlock().isFullBlock()) {
                this.setBlockAndNotifyAdequately(world, blockPos.up(length), this.mushroomType.getDefaultState().withProperty(BlockHugeMushroom.VARIANT, BlockHugeMushroom.EnumType.STEM));
            }
            ++length;
        }
        return " ".length() != 0;
    }
}
