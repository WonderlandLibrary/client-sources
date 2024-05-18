package net.minecraft.block;

import net.minecraft.block.properties.*;
import com.google.common.base.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;

public class BlockRailPowered extends BlockRailBase
{
    public static final PropertyBool POWERED;
    private static int[] $SWITCH_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection;
    private static final String[] I;
    public static final PropertyEnum<EnumRailDirection> SHAPE;
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = "".length() | blockState.getValue(BlockRailPowered.SHAPE).getMetadata();
        if (blockState.getValue((IProperty<Boolean>)BlockRailPowered.POWERED)) {
            n |= (0x1A ^ 0x12);
        }
        return n;
    }
    
    static {
        I();
        SHAPE = PropertyEnum.create(BlockRailPowered.I["".length()], EnumRailDirection.class, (com.google.common.base.Predicate<EnumRailDirection>)new Predicate<EnumRailDirection>() {
            public boolean apply(final EnumRailDirection enumRailDirection) {
                if (enumRailDirection != EnumRailDirection.NORTH_EAST && enumRailDirection != EnumRailDirection.NORTH_WEST && enumRailDirection != EnumRailDirection.SOUTH_EAST && enumRailDirection != EnumRailDirection.SOUTH_WEST) {
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
                    if (1 == 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public boolean apply(final Object o) {
                return this.apply((EnumRailDirection)o);
            }
        });
        POWERED = PropertyBool.create(BlockRailPowered.I[" ".length()]);
    }
    
    protected BlockRailPowered() {
        super(" ".length() != 0);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockRailPowered.SHAPE, EnumRailDirection.NORTH_SOUTH).withProperty((IProperty<Comparable>)BlockRailPowered.POWERED, (boolean)("".length() != 0)));
    }
    
    protected boolean func_176566_a(final World world, final BlockPos blockPos, final IBlockState blockState, final boolean b, final int n) {
        if (n >= (0x58 ^ 0x50)) {
            return "".length() != 0;
        }
        int x = blockPos.getX();
        int y = blockPos.getY();
        int z = blockPos.getZ();
        int n2 = " ".length();
        EnumRailDirection enumRailDirection = blockState.getValue(BlockRailPowered.SHAPE);
        switch ($SWITCH_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection()[enumRailDirection.ordinal()]) {
            case 1: {
                if (b) {
                    ++z;
                    "".length();
                    if (0 <= -1) {
                        throw null;
                    }
                    break;
                }
                else {
                    --z;
                    "".length();
                    if (false) {
                        throw null;
                    }
                    break;
                }
                break;
            }
            case 2: {
                if (b) {
                    --x;
                    "".length();
                    if (3 <= -1) {
                        throw null;
                    }
                    break;
                }
                else {
                    ++x;
                    "".length();
                    if (-1 >= 3) {
                        throw null;
                    }
                    break;
                }
                break;
            }
            case 3: {
                if (b) {
                    --x;
                    "".length();
                    if (0 == 2) {
                        throw null;
                    }
                }
                else {
                    ++x;
                    ++y;
                    n2 = "".length();
                }
                enumRailDirection = EnumRailDirection.EAST_WEST;
                "".length();
                if (4 <= 3) {
                    throw null;
                }
                break;
            }
            case 4: {
                if (b) {
                    --x;
                    ++y;
                    n2 = "".length();
                    "".length();
                    if (1 < -1) {
                        throw null;
                    }
                }
                else {
                    ++x;
                }
                enumRailDirection = EnumRailDirection.EAST_WEST;
                "".length();
                if (3 >= 4) {
                    throw null;
                }
                break;
            }
            case 5: {
                if (b) {
                    ++z;
                    "".length();
                    if (4 <= -1) {
                        throw null;
                    }
                }
                else {
                    --z;
                    ++y;
                    n2 = "".length();
                }
                enumRailDirection = EnumRailDirection.NORTH_SOUTH;
                "".length();
                if (1 == 4) {
                    throw null;
                }
                break;
            }
            case 6: {
                if (b) {
                    ++z;
                    ++y;
                    n2 = "".length();
                    "".length();
                    if (-1 == 1) {
                        throw null;
                    }
                }
                else {
                    --z;
                }
                enumRailDirection = EnumRailDirection.NORTH_SOUTH;
                break;
            }
        }
        int n3;
        if (this.func_176567_a(world, new BlockPos(x, y, z), b, n, enumRailDirection)) {
            n3 = " ".length();
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        else if (n2 != 0 && this.func_176567_a(world, new BlockPos(x, y - " ".length(), z), b, n, enumRailDirection)) {
            n3 = " ".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n3 = "".length();
        }
        return n3 != 0;
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection() {
        final int[] $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection = BlockRailPowered.$SWITCH_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection;
        if ($switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection != null) {
            return $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection;
        }
        final int[] $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection2 = new int[EnumRailDirection.values().length];
        try {
            $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection2[EnumRailDirection.ASCENDING_EAST.ordinal()] = "   ".length();
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection2[EnumRailDirection.ASCENDING_NORTH.ordinal()] = (0x80 ^ 0x85);
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection2[EnumRailDirection.ASCENDING_SOUTH.ordinal()] = (0x6 ^ 0x0);
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection2[EnumRailDirection.ASCENDING_WEST.ordinal()] = (0x76 ^ 0x72);
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection2[EnumRailDirection.EAST_WEST.ordinal()] = "  ".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection2[EnumRailDirection.NORTH_EAST.ordinal()] = (0x4E ^ 0x44);
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection2[EnumRailDirection.NORTH_SOUTH.ordinal()] = " ".length();
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError7) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection2[EnumRailDirection.NORTH_WEST.ordinal()] = (0x24 ^ 0x2D);
            "".length();
            if (4 < 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError8) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection2[EnumRailDirection.SOUTH_EAST.ordinal()] = (0xC7 ^ 0xC0);
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError9) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection2[EnumRailDirection.SOUTH_WEST.ordinal()] = (0x78 ^ 0x70);
            "".length();
            if (2 == 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError10) {}
        return BlockRailPowered.$SWITCH_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection = $switch_TABLE$net$minecraft$block$BlockRailBase$EnumRailDirection2;
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I(":\u0018.3)", "IpOCL");
        BlockRailPowered.I[" ".length()] = I("1\u0000\u001f3\u0019$\u000b", "AohVk");
    }
    
    @Override
    public IProperty<EnumRailDirection> getShapeProperty() {
        return BlockRailPowered.SHAPE;
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
            if (4 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected boolean func_176567_a(final World world, final BlockPos blockPos, final boolean b, final int n, final EnumRailDirection enumRailDirection) {
        final IBlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() != this) {
            return "".length() != 0;
        }
        final EnumRailDirection enumRailDirection2 = blockState.getValue(BlockRailPowered.SHAPE);
        int n2;
        if (enumRailDirection != EnumRailDirection.EAST_WEST || (enumRailDirection2 != EnumRailDirection.NORTH_SOUTH && enumRailDirection2 != EnumRailDirection.ASCENDING_NORTH && enumRailDirection2 != EnumRailDirection.ASCENDING_SOUTH)) {
            if (enumRailDirection != EnumRailDirection.NORTH_SOUTH || (enumRailDirection2 != EnumRailDirection.EAST_WEST && enumRailDirection2 != EnumRailDirection.ASCENDING_EAST && enumRailDirection2 != EnumRailDirection.ASCENDING_WEST)) {
                if (blockState.getValue((IProperty<Boolean>)BlockRailPowered.POWERED)) {
                    if (world.isBlockPowered(blockPos)) {
                        n2 = " ".length();
                        "".length();
                        if (2 == 4) {
                            throw null;
                        }
                    }
                    else {
                        n2 = (this.func_176566_a(world, blockPos, blockState, b, n + " ".length()) ? 1 : 0);
                        "".length();
                        if (0 < -1) {
                            throw null;
                        }
                    }
                }
                else {
                    n2 = "".length();
                    "".length();
                    if (3 < -1) {
                        throw null;
                    }
                }
            }
            else {
                n2 = "".length();
                "".length();
                if (1 < 1) {
                    throw null;
                }
            }
        }
        else {
            n2 = "".length();
        }
        return n2 != 0;
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty["  ".length()];
        array["".length()] = BlockRailPowered.SHAPE;
        array[" ".length()] = BlockRailPowered.POWERED;
        return new BlockState(this, array);
    }
    
    @Override
    protected void onNeighborChangedInternal(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        final boolean booleanValue = blockState.getValue((IProperty<Boolean>)BlockRailPowered.POWERED);
        int n;
        if (!world.isBlockPowered(blockPos) && !this.func_176566_a(world, blockPos, blockState, " ".length() != 0, "".length()) && !this.func_176566_a(world, blockPos, blockState, "".length() != 0, "".length())) {
            n = "".length();
            "".length();
            if (2 < 2) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        final int n2 = n;
        if (n2 != (booleanValue ? 1 : 0)) {
            world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockRailPowered.POWERED, (boolean)(n2 != 0)), "   ".length());
            world.notifyNeighborsOfStateChange(blockPos.down(), this);
            if (blockState.getValue(BlockRailPowered.SHAPE).isAscending()) {
                world.notifyNeighborsOfStateChange(blockPos.up(), this);
            }
        }
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final IBlockState withProperty = this.getDefaultState().withProperty(BlockRailPowered.SHAPE, EnumRailDirection.byMetadata(n & (0x73 ^ 0x74)));
        final PropertyBool powered = BlockRailPowered.POWERED;
        int n2;
        if ((n & (0x3D ^ 0x35)) > 0) {
            n2 = " ".length();
            "".length();
            if (1 == 4) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        return withProperty.withProperty((IProperty<Comparable>)powered, n2 != 0);
    }
}
