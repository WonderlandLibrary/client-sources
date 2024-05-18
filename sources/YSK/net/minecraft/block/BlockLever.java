package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockLever extends Block
{
    private static final String[] I;
    private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;
    public static final PropertyBool POWERED;
    public static final PropertyEnum<EnumOrientation> FACING;
    private static int[] $SWITCH_TABLE$net$minecraft$block$BlockLever$EnumOrientation;
    
    @Override
    public boolean canProvidePower() {
        return " ".length() != 0;
    }
    
    protected static boolean func_181090_a(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        return BlockButton.func_181088_a(world, blockPos, enumFacing);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    protected BlockLever() {
        super(Material.circuits);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockLever.FACING, EnumOrientation.NORTH).withProperty((IProperty<Comparable>)BlockLever.POWERED, (boolean)("".length() != 0)));
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    public int getWeakPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        int length;
        if (blockState.getValue((IProperty<Boolean>)BlockLever.POWERED)) {
            length = (0xA8 ^ 0xA7);
            "".length();
            if (0 < -1) {
                throw null;
            }
        }
        else {
            length = "".length();
        }
        return length;
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("\"\u0015(8$#", "DtKQJ");
        BlockLever.I[" ".length()] = I("\u0000=-&\u000b\u00156", "pRZCy");
        BlockLever.I["  ".length()] = I("\u000117\u0011\t\u001e~:\u0019\u000f\u0010;", "sPYuf");
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        final IBlockState withProperty = this.getDefaultState().withProperty((IProperty<Comparable>)BlockLever.POWERED, "".length() != 0);
        if (func_181090_a(world, blockPos, enumFacing.getOpposite())) {
            return withProperty.withProperty(BlockLever.FACING, EnumOrientation.forFacings(enumFacing, entityLivingBase.getHorizontalFacing()));
        }
        final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
        "".length();
        if (1 <= -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final EnumFacing enumFacing2 = iterator.next();
            if (enumFacing2 != enumFacing && func_181090_a(world, blockPos, enumFacing2.getOpposite())) {
                return withProperty.withProperty(BlockLever.FACING, EnumOrientation.forFacings(enumFacing2, entityLivingBase.getHorizontalFacing()));
            }
        }
        if (World.doesBlockHaveSolidTopSurface(world, blockPos.down())) {
            return withProperty.withProperty(BlockLever.FACING, EnumOrientation.forFacings(EnumFacing.UP, entityLivingBase.getHorizontalFacing()));
        }
        return withProperty;
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, IBlockState cycleProperty, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return " ".length() != 0;
        }
        cycleProperty = cycleProperty.cycleProperty((IProperty<Comparable>)BlockLever.POWERED);
        world.setBlockState(blockPos, cycleProperty, "   ".length());
        final double n4 = blockPos.getX() + 0.5;
        final double n5 = blockPos.getY() + 0.5;
        final double n6 = blockPos.getZ() + 0.5;
        final String s = BlockLever.I["  ".length()];
        final float n7 = 0.3f;
        float n8;
        if (cycleProperty.getValue((IProperty<Boolean>)BlockLever.POWERED)) {
            n8 = 0.6f;
            "".length();
            if (1 < -1) {
                throw null;
            }
        }
        else {
            n8 = 0.5f;
        }
        world.playSoundEffect(n4, n5, n6, s, n7, n8);
        world.notifyNeighborsOfStateChange(blockPos, this);
        world.notifyNeighborsOfStateChange(blockPos.offset(cycleProperty.getValue(BlockLever.FACING).getFacing().getOpposite()), this);
        return " ".length() != 0;
    }
    
    public static int getMetadataForFacing(final EnumFacing enumFacing) {
        switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[enumFacing.ordinal()]) {
            case 1: {
                return "".length();
            }
            case 2: {
                return 0x4F ^ 0x4A;
            }
            case 3: {
                return 0xAF ^ 0xAB;
            }
            case 4: {
                return "   ".length();
            }
            case 5: {
                return "  ".length();
            }
            case 6: {
                return " ".length();
            }
            default: {
                return -" ".length();
            }
        }
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (blockState.getValue((IProperty<Boolean>)BlockLever.POWERED)) {
            world.notifyNeighborsOfStateChange(blockPos, this);
            world.notifyNeighborsOfStateChange(blockPos.offset(blockState.getValue(BlockLever.FACING).getFacing().getOpposite()), this);
        }
        super.breakBlock(world, blockPos, blockState);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final IBlockState withProperty = this.getDefaultState().withProperty(BlockLever.FACING, EnumOrientation.byMetadata(n & (0x46 ^ 0x41)));
        final PropertyBool powered = BlockLever.POWERED;
        int n2;
        if ((n & (0xCE ^ 0xC6)) > 0) {
            n2 = " ".length();
            "".length();
            if (4 < 1) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        return withProperty.withProperty((IProperty<Comparable>)powered, n2 != 0);
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$block$BlockLever$EnumOrientation() {
        final int[] $switch_TABLE$net$minecraft$block$BlockLever$EnumOrientation = BlockLever.$SWITCH_TABLE$net$minecraft$block$BlockLever$EnumOrientation;
        if ($switch_TABLE$net$minecraft$block$BlockLever$EnumOrientation != null) {
            return $switch_TABLE$net$minecraft$block$BlockLever$EnumOrientation;
        }
        final int[] $switch_TABLE$net$minecraft$block$BlockLever$EnumOrientation2 = new int[EnumOrientation.values().length];
        try {
            $switch_TABLE$net$minecraft$block$BlockLever$EnumOrientation2[EnumOrientation.DOWN_X.ordinal()] = " ".length();
            "".length();
            if (0 == 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockLever$EnumOrientation2[EnumOrientation.DOWN_Z.ordinal()] = (0xB1 ^ 0xB9);
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockLever$EnumOrientation2[EnumOrientation.EAST.ordinal()] = "  ".length();
            "".length();
            if (4 == 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockLever$EnumOrientation2[EnumOrientation.NORTH.ordinal()] = (0xC1 ^ 0xC4);
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockLever$EnumOrientation2[EnumOrientation.SOUTH.ordinal()] = (0x41 ^ 0x45);
            "".length();
            if (3 < 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockLever$EnumOrientation2[EnumOrientation.UP_X.ordinal()] = (0x1D ^ 0x1A);
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockLever$EnumOrientation2[EnumOrientation.UP_Z.ordinal()] = (0xA6 ^ 0xA0);
            "".length();
            if (3 < 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError7) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockLever$EnumOrientation2[EnumOrientation.WEST.ordinal()] = "   ".length();
            "".length();
            if (3 == 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError8) {}
        return BlockLever.$SWITCH_TABLE$net$minecraft$block$BlockLever$EnumOrientation = $switch_TABLE$net$minecraft$block$BlockLever$EnumOrientation2;
    }
    
    private boolean func_181091_e(final World world, final BlockPos blockToAir, final IBlockState blockState) {
        if (this.canPlaceBlockAt(world, blockToAir)) {
            return " ".length() != 0;
        }
        this.dropBlockAsItem(world, blockToAir, blockState, "".length());
        world.setBlockToAir(blockToAir);
        return "".length() != 0;
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty["  ".length()];
        array["".length()] = BlockLever.FACING;
        array[" ".length()] = BlockLever.POWERED;
        return new BlockState(this, array);
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
            if (false) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing() {
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing = BlockLever.$SWITCH_TABLE$net$minecraft$util$EnumFacing;
        if ($switch_TABLE$net$minecraft$util$EnumFacing != null) {
            return $switch_TABLE$net$minecraft$util$EnumFacing;
        }
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing2 = new int[EnumFacing.values().length];
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.DOWN.ordinal()] = " ".length();
            "".length();
            if (2 == 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.EAST.ordinal()] = (0x51 ^ 0x57);
            "".length();
            if (3 == 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.NORTH.ordinal()] = "   ".length();
            "".length();
            if (4 < -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.SOUTH.ordinal()] = (0x4 ^ 0x0);
            "".length();
            if (3 < 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.UP.ordinal()] = "  ".length();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.WEST.ordinal()] = (0x87 ^ 0x82);
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        return BlockLever.$SWITCH_TABLE$net$minecraft$util$EnumFacing = $switch_TABLE$net$minecraft$util$EnumFacing2;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final float n = 0.1875f;
        switch ($SWITCH_TABLE$net$minecraft$block$BlockLever$EnumOrientation()[blockAccess.getBlockState(blockPos).getValue(BlockLever.FACING).ordinal()]) {
            case 2: {
                this.setBlockBounds(0.0f, 0.2f, 0.5f - n, n * 2.0f, 0.8f, 0.5f + n);
                "".length();
                if (2 < -1) {
                    throw null;
                }
                break;
            }
            case 3: {
                this.setBlockBounds(1.0f - n * 2.0f, 0.2f, 0.5f - n, 1.0f, 0.8f, 0.5f + n);
                "".length();
                if (3 == 2) {
                    throw null;
                }
                break;
            }
            case 4: {
                this.setBlockBounds(0.5f - n, 0.2f, 0.0f, 0.5f + n, 0.8f, n * 2.0f);
                "".length();
                if (2 <= 0) {
                    throw null;
                }
                break;
            }
            case 5: {
                this.setBlockBounds(0.5f - n, 0.2f, 1.0f - n * 2.0f, 0.5f + n, 0.8f, 1.0f);
                "".length();
                if (4 <= 3) {
                    throw null;
                }
                break;
            }
            case 6:
            case 7: {
                final float n2 = 0.25f;
                this.setBlockBounds(0.5f - n2, 0.0f, 0.5f - n2, 0.5f + n2, 0.6f, 0.5f + n2);
                "".length();
                if (3 != 3) {
                    throw null;
                }
                break;
            }
            case 1:
            case 8: {
                final float n3 = 0.25f;
                this.setBlockBounds(0.5f - n3, 0.4f, 0.5f - n3, 0.5f + n3, 1.0f, 0.5f + n3);
                break;
            }
        }
    }
    
    static {
        I();
        FACING = PropertyEnum.create(BlockLever.I["".length()], EnumOrientation.class);
        POWERED = PropertyBool.create(BlockLever.I[" ".length()]);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = "".length() | blockState.getValue(BlockLever.FACING).getMetadata();
        if (blockState.getValue((IProperty<Boolean>)BlockLever.POWERED)) {
            n |= (0xBC ^ 0xB4);
        }
        return n;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        final EnumFacing[] values;
        final int length = (values = EnumFacing.values()).length;
        int i = "".length();
        "".length();
        if (1 <= -1) {
            throw null;
        }
        while (i < length) {
            if (func_181090_a(world, blockPos, values[i])) {
                return " ".length() != 0;
            }
            ++i;
        }
        return "".length() != 0;
    }
    
    @Override
    public boolean canPlaceBlockOnSide(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        return func_181090_a(world, blockPos, enumFacing.getOpposite());
    }
    
    @Override
    public int getStrongPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        int n;
        if (!blockState.getValue((IProperty<Boolean>)BlockLever.POWERED)) {
            n = "".length();
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        else if (blockState.getValue(BlockLever.FACING).getFacing() == enumFacing) {
            n = (0xAB ^ 0xA4);
            "".length();
            if (0 == 3) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n;
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (this.func_181091_e(world, blockToAir, blockState) && !func_181090_a(world, blockToAir, blockState.getValue(BlockLever.FACING).getFacing().getOpposite())) {
            this.dropBlockAsItem(world, blockToAir, blockState, "".length());
            world.setBlockToAir(blockToAir);
        }
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    public enum EnumOrientation implements IStringSerializable
    {
        private static final String[] I;
        
        SOUTH(EnumOrientation.I[0xC6 ^ 0xC0], "   ".length(), "   ".length(), EnumOrientation.I[0x82 ^ 0x85], EnumFacing.SOUTH), 
        EAST(EnumOrientation.I["  ".length()], " ".length(), " ".length(), EnumOrientation.I["   ".length()], EnumFacing.EAST);
        
        private static final EnumOrientation[] META_LOOKUP;
        
        UP_X(EnumOrientation.I[0x9C ^ 0x90], 0x2D ^ 0x2B, 0x59 ^ 0x5F, EnumOrientation.I[0x69 ^ 0x64], EnumFacing.UP);
        
        private final int meta;
        private final EnumFacing facing;
        private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;
        
        UP_Z(EnumOrientation.I[0x67 ^ 0x6D], 0x34 ^ 0x31, 0x4A ^ 0x4F, EnumOrientation.I[0x9C ^ 0x97], EnumFacing.UP), 
        DOWN_X(EnumOrientation.I["".length()], "".length(), "".length(), EnumOrientation.I[" ".length()], EnumFacing.DOWN), 
        DOWN_Z(EnumOrientation.I[0x41 ^ 0x4F], 0x5B ^ 0x5C, 0x2F ^ 0x28, EnumOrientation.I[0x33 ^ 0x3C], EnumFacing.DOWN), 
        WEST(EnumOrientation.I[0x94 ^ 0x90], "  ".length(), "  ".length(), EnumOrientation.I[0x24 ^ 0x21], EnumFacing.WEST), 
        NORTH(EnumOrientation.I[0x7 ^ 0xF], 0x4F ^ 0x4B, 0x8E ^ 0x8A, EnumOrientation.I[0xB2 ^ 0xBB], EnumFacing.NORTH);
        
        private final String name;
        private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing$Axis;
        private static final EnumOrientation[] ENUM$VALUES;
        
        @Override
        public String toString() {
            return this.name;
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        static {
            I();
            final EnumOrientation[] enum$VALUES = new EnumOrientation[0x93 ^ 0x9B];
            enum$VALUES["".length()] = EnumOrientation.DOWN_X;
            enum$VALUES[" ".length()] = EnumOrientation.EAST;
            enum$VALUES["  ".length()] = EnumOrientation.WEST;
            enum$VALUES["   ".length()] = EnumOrientation.SOUTH;
            enum$VALUES[0x39 ^ 0x3D] = EnumOrientation.NORTH;
            enum$VALUES[0xD ^ 0x8] = EnumOrientation.UP_Z;
            enum$VALUES[0xA7 ^ 0xA1] = EnumOrientation.UP_X;
            enum$VALUES[0x2C ^ 0x2B] = EnumOrientation.DOWN_Z;
            ENUM$VALUES = enum$VALUES;
            META_LOOKUP = new EnumOrientation[values().length];
            final EnumOrientation[] values;
            final int length = (values = values()).length;
            int i = "".length();
            "".length();
            if (1 < 0) {
                throw null;
            }
            while (i < length) {
                final EnumOrientation enumOrientation = values[i];
                EnumOrientation.META_LOOKUP[enumOrientation.getMetadata()] = enumOrientation;
                ++i;
            }
        }
        
        static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing() {
            final int[] $switch_TABLE$net$minecraft$util$EnumFacing = EnumOrientation.$SWITCH_TABLE$net$minecraft$util$EnumFacing;
            if ($switch_TABLE$net$minecraft$util$EnumFacing != null) {
                return $switch_TABLE$net$minecraft$util$EnumFacing;
            }
            final int[] $switch_TABLE$net$minecraft$util$EnumFacing2 = new int[EnumFacing.values().length];
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.DOWN.ordinal()] = " ".length();
                "".length();
                if (3 < -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.EAST.ordinal()] = (0x4F ^ 0x49);
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.NORTH.ordinal()] = "   ".length();
                "".length();
                if (2 <= 1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.SOUTH.ordinal()] = (0xBE ^ 0xBA);
                "".length();
                if (4 <= 2) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.UP.ordinal()] = "  ".length();
                "".length();
                if (2 < -1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.WEST.ordinal()] = (0x91 ^ 0x94);
                "".length();
                if (0 >= 4) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            return EnumOrientation.$SWITCH_TABLE$net$minecraft$util$EnumFacing = $switch_TABLE$net$minecraft$util$EnumFacing2;
        }
        
        private static void I() {
            (I = new String[0x2 ^ 0x17])["".length()] = I("+9<,\u001b7", "ovkbD");
            EnumOrientation.I[" ".length()] = I("#\u001a\u000f\u0017\u0014?", "GuxyK");
            EnumOrientation.I["  ".length()] = I("\t4%\u0005", "LuvQx");
            EnumOrientation.I["   ".length()] = I("\u0000')<", "eFZHg");
            EnumOrientation.I[0x15 ^ 0x11] = I("\u0015\u0002\u000b\u0003", "BGXWd");
            EnumOrientation.I[0x7C ^ 0x79] = I("\u0013#\"<", "dFQHs");
            EnumOrientation.I[0xA8 ^ 0xAE] = I("\u0018\r?\u0005\u0001", "KBjQI");
            EnumOrientation.I[0x21 ^ 0x26] = I("48%\u0019>", "GWPmV");
            EnumOrientation.I[0x14 ^ 0x1C] = I("\u001d)9\u0013\t", "SfkGA");
            EnumOrientation.I[0x1A ^ 0x13] = I("\u001c\u0005\u001d2.", "rjoFF");
            EnumOrientation.I[0x18 ^ 0x12] = I("?:(\u000f", "jjwUH");
            EnumOrientation.I[0x3F ^ 0x34] = I("\r'>\u0010", "xWajc");
            EnumOrientation.I[0x3C ^ 0x30] = I("\u0014\u001f70", "AOhha");
            EnumOrientation.I[0x13 ^ 0x1E] = I("-;\u001b7", "XKDOK");
            EnumOrientation.I[0x9F ^ 0x91] = I("*\"5854", "nmbvj");
            EnumOrientation.I[0x5C ^ 0x53] = I("\r\u0005\u0003\u00061\u0013", "ijthn");
            EnumOrientation.I[0x27 ^ 0x37] = I(":\u001e\f\u0003'\u001a\u0014Z\u0007%\u0007\u0019\u000e\u001b\r\u0012\u0013\u0013\f,S", "spzbK");
            EnumOrientation.I[0x68 ^ 0x79] = I("C\u001f\u0003\u0010w\u0005\u0018\u000f\u000b9\u0004Y", "cylbW");
            EnumOrientation.I[0x5E ^ 0x4C] = I("9<\u001e0'\u00196H4%\u0004;\u001c(\r\u00111\u0001?,P", "pRhQK");
            EnumOrientation.I[0x37 ^ 0x24] = I("L!97t\n&5,:\u000bg", "lGVET");
            EnumOrientation.I[0x15 ^ 0x1] = I("\u001e\u001e\u000f\u0006\u0000>\u0014Y\u0001\r4\u0019\u0017\u0000Vw", "Wpygl");
        }
        
        public int getMetadata() {
            return this.meta;
        }
        
        public EnumFacing getFacing() {
            return this.facing;
        }
        
        private EnumOrientation(final String s, final int n, final int meta, final String name, final EnumFacing facing) {
            this.meta = meta;
            this.name = name;
            this.facing = facing;
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
                if (3 < 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public static EnumOrientation byMetadata(int length) {
            if (length < 0 || length >= EnumOrientation.META_LOOKUP.length) {
                length = "".length();
            }
            return EnumOrientation.META_LOOKUP[length];
        }
        
        public static EnumOrientation forFacings(final EnumFacing enumFacing, final EnumFacing enumFacing2) {
            switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[enumFacing.ordinal()]) {
                case 1: {
                    switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing$Axis()[enumFacing2.getAxis().ordinal()]) {
                        case 1: {
                            return EnumOrientation.DOWN_X;
                        }
                        case 3: {
                            return EnumOrientation.DOWN_Z;
                        }
                        default: {
                            throw new IllegalArgumentException(EnumOrientation.I[0x1C ^ 0xC] + enumFacing2 + EnumOrientation.I[0x94 ^ 0x85] + enumFacing);
                        }
                    }
                    break;
                }
                case 2: {
                    switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing$Axis()[enumFacing2.getAxis().ordinal()]) {
                        case 1: {
                            return EnumOrientation.UP_X;
                        }
                        case 3: {
                            return EnumOrientation.UP_Z;
                        }
                        default: {
                            throw new IllegalArgumentException(EnumOrientation.I[0x8A ^ 0x98] + enumFacing2 + EnumOrientation.I[0x89 ^ 0x9A] + enumFacing);
                        }
                    }
                    break;
                }
                case 3: {
                    return EnumOrientation.NORTH;
                }
                case 4: {
                    return EnumOrientation.SOUTH;
                }
                case 5: {
                    return EnumOrientation.WEST;
                }
                case 6: {
                    return EnumOrientation.EAST;
                }
                default: {
                    throw new IllegalArgumentException(EnumOrientation.I[0x8C ^ 0x98] + enumFacing);
                }
            }
        }
        
        static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing$Axis() {
            final int[] $switch_TABLE$net$minecraft$util$EnumFacing$Axis = EnumOrientation.$SWITCH_TABLE$net$minecraft$util$EnumFacing$Axis;
            if ($switch_TABLE$net$minecraft$util$EnumFacing$Axis != null) {
                return $switch_TABLE$net$minecraft$util$EnumFacing$Axis;
            }
            final int[] $switch_TABLE$net$minecraft$util$EnumFacing$Axis2 = new int[EnumFacing.Axis.values().length];
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing$Axis2[EnumFacing.Axis.X.ordinal()] = " ".length();
                "".length();
                if (false == true) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing$Axis2[EnumFacing.Axis.Y.ordinal()] = "  ".length();
                "".length();
                if (3 < 0) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing$Axis2[EnumFacing.Axis.Z.ordinal()] = "   ".length();
                "".length();
                if (3 >= 4) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            return EnumOrientation.$SWITCH_TABLE$net$minecraft$util$EnumFacing$Axis = $switch_TABLE$net$minecraft$util$EnumFacing$Axis2;
        }
    }
}
