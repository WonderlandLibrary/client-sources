package net.minecraft.world.gen.feature;

import net.minecraft.block.state.pattern.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import com.google.common.base.*;
import net.minecraft.block.*;

public class WorldGenDesertWells extends WorldGenerator
{
    private static final BlockStateHelper field_175913_a;
    private final IBlockState field_175912_c;
    private final IBlockState field_175910_d;
    private final IBlockState field_175911_b;
    
    @Override
    public boolean generate(final World world, final Random random, BlockPos down) {
        "".length();
        if (-1 < -1) {
            throw null;
        }
        while (world.isAirBlock(down) && down.getY() > "  ".length()) {
            down = down.down();
        }
        if (!WorldGenDesertWells.field_175913_a.apply(world.getBlockState(down))) {
            return "".length() != 0;
        }
        int i = -"  ".length();
        "".length();
        if (4 <= 2) {
            throw null;
        }
        while (i <= "  ".length()) {
            int j = -"  ".length();
            "".length();
            if (0 == -1) {
                throw null;
            }
            while (j <= "  ".length()) {
                if (world.isAirBlock(down.add(i, -" ".length(), j)) && world.isAirBlock(down.add(i, -"  ".length(), j))) {
                    return "".length() != 0;
                }
                ++j;
            }
            ++i;
        }
        int k = -" ".length();
        "".length();
        if (4 == 3) {
            throw null;
        }
        while (k <= 0) {
            int l = -"  ".length();
            "".length();
            if (-1 >= 0) {
                throw null;
            }
            while (l <= "  ".length()) {
                int n = -"  ".length();
                "".length();
                if (true != true) {
                    throw null;
                }
                while (n <= "  ".length()) {
                    world.setBlockState(down.add(l, k, n), this.field_175912_c, "  ".length());
                    ++n;
                }
                ++l;
            }
            ++k;
        }
        world.setBlockState(down, this.field_175910_d, "  ".length());
        final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
        "".length();
        if (-1 < -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            world.setBlockState(down.offset(iterator.next()), this.field_175910_d, "  ".length());
        }
        int n2 = -"  ".length();
        "".length();
        if (-1 == 1) {
            throw null;
        }
        while (n2 <= "  ".length()) {
            int n3 = -"  ".length();
            "".length();
            if (true != true) {
                throw null;
            }
            while (n3 <= "  ".length()) {
                if (n2 == -"  ".length() || n2 == "  ".length() || n3 == -"  ".length() || n3 == "  ".length()) {
                    world.setBlockState(down.add(n2, " ".length(), n3), this.field_175912_c, "  ".length());
                }
                ++n3;
            }
            ++n2;
        }
        world.setBlockState(down.add("  ".length(), " ".length(), "".length()), this.field_175911_b, "  ".length());
        world.setBlockState(down.add(-"  ".length(), " ".length(), "".length()), this.field_175911_b, "  ".length());
        world.setBlockState(down.add("".length(), " ".length(), "  ".length()), this.field_175911_b, "  ".length());
        world.setBlockState(down.add("".length(), " ".length(), -"  ".length()), this.field_175911_b, "  ".length());
        int n4 = -" ".length();
        "".length();
        if (0 <= -1) {
            throw null;
        }
        while (n4 <= " ".length()) {
            int n5 = -" ".length();
            "".length();
            if (-1 >= 2) {
                throw null;
            }
            while (n5 <= " ".length()) {
                if (n4 == 0 && n5 == 0) {
                    world.setBlockState(down.add(n4, 0x51 ^ 0x55, n5), this.field_175912_c, "  ".length());
                    "".length();
                    if (1 == -1) {
                        throw null;
                    }
                }
                else {
                    world.setBlockState(down.add(n4, 0x3F ^ 0x3B, n5), this.field_175911_b, "  ".length());
                }
                ++n5;
            }
            ++n4;
        }
        int length = " ".length();
        "".length();
        if (0 >= 2) {
            throw null;
        }
        while (length <= "   ".length()) {
            world.setBlockState(down.add(-" ".length(), length, -" ".length()), this.field_175912_c, "  ".length());
            world.setBlockState(down.add(-" ".length(), length, " ".length()), this.field_175912_c, "  ".length());
            world.setBlockState(down.add(" ".length(), length, -" ".length()), this.field_175912_c, "  ".length());
            world.setBlockState(down.add(" ".length(), length, " ".length()), this.field_175912_c, "  ".length());
            ++length;
        }
        return " ".length() != 0;
    }
    
    static {
        field_175913_a = BlockStateHelper.forBlock(Blocks.sand).where(BlockSand.VARIANT, (com.google.common.base.Predicate<? extends BlockSand.EnumType>)Predicates.equalTo((Object)BlockSand.EnumType.SAND));
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
            if (0 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public WorldGenDesertWells() {
        this.field_175911_b = Blocks.stone_slab.getDefaultState().withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.SAND).withProperty(BlockSlab.HALF, BlockSlab.EnumBlockHalf.BOTTOM);
        this.field_175912_c = Blocks.sandstone.getDefaultState();
        this.field_175910_d = Blocks.flowing_water.getDefaultState();
    }
}
