package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.block.properties.*;

public class WorldGenVines extends WorldGenerator
{
    @Override
    public boolean generate(final World world, final Random random, BlockPos blockPos) {
        "".length();
        if (3 < -1) {
            throw null;
        }
        while (blockPos.getY() < 122 + 28 - 87 + 65) {
            Label_0348: {
                if (world.isAirBlock(blockPos)) {
                    final EnumFacing[] facings;
                    final int length = (facings = EnumFacing.Plane.HORIZONTAL.facings()).length;
                    int i = "".length();
                    "".length();
                    if (false) {
                        throw null;
                    }
                    while (i < length) {
                        final EnumFacing enumFacing = facings[i];
                        if (Blocks.vine.canPlaceBlockOnSide(world, blockPos, enumFacing)) {
                            final IBlockState defaultState = Blocks.vine.getDefaultState();
                            final PropertyBool north = BlockVine.NORTH;
                            int n;
                            if (enumFacing == EnumFacing.NORTH) {
                                n = " ".length();
                                "".length();
                                if (2 < 2) {
                                    throw null;
                                }
                            }
                            else {
                                n = "".length();
                            }
                            final IBlockState withProperty = defaultState.withProperty((IProperty<Comparable>)north, n != 0);
                            final PropertyBool east = BlockVine.EAST;
                            int n2;
                            if (enumFacing == EnumFacing.EAST) {
                                n2 = " ".length();
                                "".length();
                                if (3 < 2) {
                                    throw null;
                                }
                            }
                            else {
                                n2 = "".length();
                            }
                            final IBlockState withProperty2 = withProperty.withProperty((IProperty<Comparable>)east, n2 != 0);
                            final PropertyBool south = BlockVine.SOUTH;
                            int n3;
                            if (enumFacing == EnumFacing.SOUTH) {
                                n3 = " ".length();
                                "".length();
                                if (-1 >= 0) {
                                    throw null;
                                }
                            }
                            else {
                                n3 = "".length();
                            }
                            final IBlockState withProperty3 = withProperty2.withProperty((IProperty<Comparable>)south, n3 != 0);
                            final PropertyBool west = BlockVine.WEST;
                            int n4;
                            if (enumFacing == EnumFacing.WEST) {
                                n4 = " ".length();
                                "".length();
                                if (false) {
                                    throw null;
                                }
                            }
                            else {
                                n4 = "".length();
                            }
                            world.setBlockState(blockPos, withProperty3.withProperty((IProperty<Comparable>)west, (boolean)(n4 != 0)), "  ".length());
                            "".length();
                            if (false) {
                                throw null;
                            }
                            break Label_0348;
                        }
                        else {
                            ++i;
                        }
                    }
                    "".length();
                    if (0 >= 2) {
                        throw null;
                    }
                }
                else {
                    blockPos = blockPos.add(random.nextInt(0x85 ^ 0x81) - random.nextInt(0x1F ^ 0x1B), "".length(), random.nextInt(0x48 ^ 0x4C) - random.nextInt(0x33 ^ 0x37));
                }
            }
            blockPos = blockPos.up();
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
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
