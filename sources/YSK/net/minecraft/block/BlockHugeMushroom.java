package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.entity.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockHugeMushroom extends Block
{
    private final Block smallBlock;
    private static int[] $SWITCH_TABLE$net$minecraft$block$BlockHugeMushroom$EnumType;
    public static final PropertyEnum<EnumType> VARIANT;
    private static final String[] I;
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Item.getItemFromBlock(this.smallBlock);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Item.getItemFromBlock(this.smallBlock);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0002.%&.\u001a;", "tOWOO");
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockHugeMushroom.VARIANT, EnumType.byMetadata(n));
    }
    
    static {
        I();
        VARIANT = PropertyEnum.create(BlockHugeMushroom.I["".length()], EnumType.class);
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState();
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return Math.max("".length(), random.nextInt(0x87 ^ 0x8D) - (0x12 ^ 0x15));
    }
    
    public BlockHugeMushroom(final Material material, final MapColor mapColor, final Block smallBlock) {
        super(material, mapColor);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockHugeMushroom.VARIANT, EnumType.ALL_OUTSIDE));
        this.smallBlock = smallBlock;
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockHugeMushroom.VARIANT;
        return new BlockState(this, array);
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$block$BlockHugeMushroom$EnumType() {
        final int[] $switch_TABLE$net$minecraft$block$BlockHugeMushroom$EnumType = BlockHugeMushroom.$SWITCH_TABLE$net$minecraft$block$BlockHugeMushroom$EnumType;
        if ($switch_TABLE$net$minecraft$block$BlockHugeMushroom$EnumType != null) {
            return $switch_TABLE$net$minecraft$block$BlockHugeMushroom$EnumType;
        }
        final int[] $switch_TABLE$net$minecraft$block$BlockHugeMushroom$EnumType2 = new int[EnumType.values().length];
        try {
            $switch_TABLE$net$minecraft$block$BlockHugeMushroom$EnumType2[EnumType.ALL_INSIDE.ordinal()] = (0x27 ^ 0x2C);
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockHugeMushroom$EnumType2[EnumType.ALL_OUTSIDE.ordinal()] = (0x0 ^ 0xC);
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockHugeMushroom$EnumType2[EnumType.ALL_STEM.ordinal()] = (0xA0 ^ 0xAD);
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockHugeMushroom$EnumType2[EnumType.CENTER.ordinal()] = (0x28 ^ 0x2D);
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockHugeMushroom$EnumType2[EnumType.EAST.ordinal()] = (0x9A ^ 0x9C);
            "".length();
            if (3 < -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockHugeMushroom$EnumType2[EnumType.NORTH.ordinal()] = "  ".length();
            "".length();
            if (-1 == 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockHugeMushroom$EnumType2[EnumType.NORTH_EAST.ordinal()] = "   ".length();
            "".length();
            if (3 < 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError7) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockHugeMushroom$EnumType2[EnumType.NORTH_WEST.ordinal()] = " ".length();
            "".length();
            if (2 == 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError8) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockHugeMushroom$EnumType2[EnumType.SOUTH.ordinal()] = (0x1A ^ 0x12);
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError9) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockHugeMushroom$EnumType2[EnumType.SOUTH_EAST.ordinal()] = (0xA8 ^ 0xA1);
            "".length();
            if (2 < 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError10) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockHugeMushroom$EnumType2[EnumType.SOUTH_WEST.ordinal()] = (0x55 ^ 0x52);
            "".length();
            if (4 == -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError11) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockHugeMushroom$EnumType2[EnumType.STEM.ordinal()] = (0x74 ^ 0x7E);
            "".length();
            if (3 == -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError12) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockHugeMushroom$EnumType2[EnumType.WEST.ordinal()] = (0xBF ^ 0xBB);
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError13) {}
        return BlockHugeMushroom.$SWITCH_TABLE$net$minecraft$block$BlockHugeMushroom$EnumType = $switch_TABLE$net$minecraft$block$BlockHugeMushroom$EnumType2;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue(BlockHugeMushroom.VARIANT).getMetadata();
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        switch ($SWITCH_TABLE$net$minecraft$block$BlockHugeMushroom$EnumType()[blockState.getValue(BlockHugeMushroom.VARIANT).ordinal()]) {
            case 13: {
                return MapColor.clothColor;
            }
            case 11: {
                return MapColor.sandColor;
            }
            case 10: {
                return MapColor.sandColor;
            }
            default: {
                return super.getMapColor(blockState);
            }
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
            if (4 <= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public enum EnumType implements IStringSerializable
    {
        CENTER(EnumType.I[0x30 ^ 0x38], 0x57 ^ 0x53, 0xC0 ^ 0xC5, EnumType.I[0x6E ^ 0x67]), 
        STEM(EnumType.I[0x55 ^ 0x47], 0xAE ^ 0xA7, 0x58 ^ 0x52, EnumType.I[0xA ^ 0x19]);
        
        private static final EnumType[] ENUM$VALUES;
        
        NORTH(EnumType.I["  ".length()], " ".length(), "  ".length(), EnumType.I["   ".length()]), 
        ALL_OUTSIDE(EnumType.I[0xB7 ^ 0xA1], 0x15 ^ 0x1E, 0x5D ^ 0x53, EnumType.I[0x7 ^ 0x10]), 
        NORTH_EAST(EnumType.I[0xB9 ^ 0xBD], "  ".length(), "   ".length(), EnumType.I[0x7A ^ 0x7F]), 
        ALL_STEM(EnumType.I[0x41 ^ 0x59], 0x47 ^ 0x4B, 0xB2 ^ 0xBD, EnumType.I[0x23 ^ 0x3A]), 
        WEST(EnumType.I[0xC1 ^ 0xC7], "   ".length(), 0x1D ^ 0x19, EnumType.I[0x3C ^ 0x3B]), 
        ALL_INSIDE(EnumType.I[0x18 ^ 0xC], 0x9D ^ 0x97, "".length(), EnumType.I[0x38 ^ 0x2D]), 
        NORTH_WEST(EnumType.I["".length()], "".length(), " ".length(), EnumType.I[" ".length()]);
        
        private final String name;
        
        SOUTH(EnumType.I[0x67 ^ 0x69], 0x92 ^ 0x95, 0xBA ^ 0xB2, EnumType.I[0x66 ^ 0x69]);
        
        private static final String[] I;
        
        SOUTH_WEST(EnumType.I[0x4D ^ 0x41], 0xBE ^ 0xB8, 0x3C ^ 0x3B, EnumType.I[0x4 ^ 0x9]);
        
        private static final EnumType[] META_LOOKUP;
        private final int meta;
        
        EAST(EnumType.I[0x82 ^ 0x88], 0x5B ^ 0x5E, 0x58 ^ 0x5E, EnumType.I[0x73 ^ 0x78]), 
        SOUTH_EAST(EnumType.I[0xB ^ 0x1B], 0x14 ^ 0x1C, 0x28 ^ 0x21, EnumType.I[0xB2 ^ 0xA3]);
        
        private EnumType(final String s, final int n, final int meta, final String name) {
            this.meta = meta;
            this.name = name;
        }
        
        private static void I() {
            (I = new String[0x47 ^ 0x5D])["".length()] = I("\"\u0018\u0005\u0015*3\u0000\u0012\u00126", "lWWAb");
            EnumType.I[" ".length()] = I("=>\u001f\u0006\u001a\f&\b\u0001\u0006", "SQmrr");
            EnumType.I["  ".length()] = I("='<\u0006!", "shnRi");
            EnumType.I["   ".length()] = I("\t9\u001f\u0007\r", "gVmse");
            EnumType.I[0x4F ^ 0x4B] = I("('\u00131\u00189-\u00006\u0004", "fhAeP");
            EnumType.I[0x4E ^ 0x4B] = I("\"\n#\u001b\u0011\u0013\u00000\u001c\r", "LeQoy");
            EnumType.I[0xC6 ^ 0xC0] = I("\u0000\u001172", "WTdfK");
            EnumType.I[0x67 ^ 0x60] = I("\u0016$\u0005\u0004", "aAvph");
            EnumType.I[0x1F ^ 0x17] = I("\u0012\u0006\u001f\u0013\u0014\u0003", "QCQGQ");
            EnumType.I[0x75 ^ 0x7C] = I("-4?\u0016\u0017<", "NQQbr");
            EnumType.I[0xAA ^ 0xA0] = I("\u0011\u00008=", "TAkiI");
            EnumType.I[0x7D ^ 0x76] = I("5\"*%", "PCYQE");
            EnumType.I[0xB1 ^ 0xBD] = I("6\r,\r\u000f:\u0015<\n\u0013", "eByYG");
            EnumType.I[0x18 ^ 0x15] = I("\u0018+\u0016#\u001943\u0006$\u0005", "kDcWq");
            EnumType.I[0x61 ^ 0x6F] = I("\u0016\"<>\u000f", "EmijG");
            EnumType.I[0x51 ^ 0x5E] = I(")\u0016\u0002-\u0002", "ZywYj");
            EnumType.I[0x78 ^ 0x68] = I("\u0003\u001d\u0010\u001d>\u000f\u0017\u0004\u001a\"", "PREIv");
            EnumType.I[0xB3 ^ 0xA2] = I("%'\u0011-\u0003\t-\u0005*\u001f", "VHdYk");
            EnumType.I[0xA0 ^ 0xB2] = I("\u0007\f\u000e8", "TXKua");
            EnumType.I[0x33 ^ 0x20] = I("\u001977/", "jCRBX");
            EnumType.I[0x19 ^ 0xD] = I("64(&19+-==", "wxdyx");
            EnumType.I[0x7 ^ 0x12] = I("4\u0001\u00036\u0003;\u001e\u0006\r\u000f", "Umoij");
            EnumType.I[0x9F ^ 0x89] = I("0)+\f\u001a$14\u001a\u00114", "qegSU");
            EnumType.I[0x1B ^ 0xC] = I("66\u0007*6\".\u0018\u001c=2", "WZkuY");
            EnumType.I[0x83 ^ 0x9B] = I("\u000f\u0015)7&\u001a\u001c(", "NYehu");
            EnumType.I[0x1D ^ 0x4] = I("$\u0016\u0006271\u001f\u0007", "EzjmD");
        }
        
        public int getMetadata() {
            return this.meta;
        }
        
        static {
            I();
            final EnumType[] enum$VALUES = new EnumType[0x91 ^ 0x9C];
            enum$VALUES["".length()] = EnumType.NORTH_WEST;
            enum$VALUES[" ".length()] = EnumType.NORTH;
            enum$VALUES["  ".length()] = EnumType.NORTH_EAST;
            enum$VALUES["   ".length()] = EnumType.WEST;
            enum$VALUES[0x23 ^ 0x27] = EnumType.CENTER;
            enum$VALUES[0x64 ^ 0x61] = EnumType.EAST;
            enum$VALUES[0xB ^ 0xD] = EnumType.SOUTH_WEST;
            enum$VALUES[0x27 ^ 0x20] = EnumType.SOUTH;
            enum$VALUES[0x6F ^ 0x67] = EnumType.SOUTH_EAST;
            enum$VALUES[0x1A ^ 0x13] = EnumType.STEM;
            enum$VALUES[0x73 ^ 0x79] = EnumType.ALL_INSIDE;
            enum$VALUES[0x8 ^ 0x3] = EnumType.ALL_OUTSIDE;
            enum$VALUES[0xBE ^ 0xB2] = EnumType.ALL_STEM;
            ENUM$VALUES = enum$VALUES;
            META_LOOKUP = new EnumType[0x7C ^ 0x6C];
            final EnumType[] values;
            final int length = (values = values()).length;
            int i = "".length();
            "".length();
            if (1 >= 2) {
                throw null;
            }
            while (i < length) {
                final EnumType enumType = values[i];
                EnumType.META_LOOKUP[enumType.getMetadata()] = enumType;
                ++i;
            }
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        public static EnumType byMetadata(int length) {
            if (length < 0 || length >= EnumType.META_LOOKUP.length) {
                length = "".length();
            }
            final EnumType enumType = EnumType.META_LOOKUP[length];
            EnumType enumType2;
            if (enumType == null) {
                enumType2 = EnumType.META_LOOKUP["".length()];
                "".length();
                if (1 <= -1) {
                    throw null;
                }
            }
            else {
                enumType2 = enumType;
            }
            return enumType2;
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
                if (-1 >= 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
