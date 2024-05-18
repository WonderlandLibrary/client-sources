package net.minecraft.block;

import net.minecraft.block.properties.*;
import com.google.common.base.*;
import net.minecraft.block.state.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;

public abstract class BlockFlower extends BlockBush
{
    protected PropertyEnum<EnumFlowerType> type;
    private static final String[] I;
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return blockState.getValue(this.getTypeProperty()).getMeta();
    }
    
    public abstract EnumFlowerColor getBlockType();
    
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
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(this.getTypeProperty(), EnumFlowerType.getType(this.getBlockType(), n));
    }
    
    @Override
    public EnumOffsetType getOffsetType() {
        return EnumOffsetType.XZ;
    }
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0012-\t\u0015", "fTypI");
    }
    
    public IProperty<EnumFlowerType> getTypeProperty() {
        if (this.type == null) {
            this.type = PropertyEnum.create(BlockFlower.I["".length()], EnumFlowerType.class, (com.google.common.base.Predicate<EnumFlowerType>)new Predicate<EnumFlowerType>(this) {
                final BlockFlower this$0;
                
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
                
                public boolean apply(final EnumFlowerType enumFlowerType) {
                    if (enumFlowerType.getBlockType() == this.this$0.getBlockType()) {
                        return " ".length() != 0;
                    }
                    return "".length() != 0;
                }
                
                public boolean apply(final Object o) {
                    return this.apply((EnumFlowerType)o);
                }
            });
        }
        return this.type;
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = this.getTypeProperty();
        return new BlockState(this, array);
    }
    
    protected BlockFlower() {
        final IBlockState baseState = this.blockState.getBaseState();
        final IProperty<EnumFlowerType> typeProperty = this.getTypeProperty();
        EnumFlowerType enumFlowerType;
        if (this.getBlockType() == EnumFlowerColor.RED) {
            enumFlowerType = EnumFlowerType.POPPY;
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        else {
            enumFlowerType = EnumFlowerType.DANDELION;
        }
        this.setDefaultState(baseState.withProperty((IProperty<Comparable>)typeProperty, enumFlowerType));
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue(this.getTypeProperty()).getMeta();
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List<ItemStack> list) {
        final EnumFlowerType[] types;
        final int length = (types = EnumFlowerType.getTypes(this.getBlockType())).length;
        int i = "".length();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (i < length) {
            list.add(new ItemStack(item, " ".length(), types[i].getMeta()));
            ++i;
        }
    }
    
    public enum EnumFlowerColor
    {
        RED(EnumFlowerColor.I[" ".length()], " ".length());
        
        private static final String[] I;
        private static final EnumFlowerColor[] ENUM$VALUES;
        
        YELLOW(EnumFlowerColor.I["".length()], "".length());
        
        private static void I() {
            (I = new String["  ".length()])["".length()] = I("*$\n/7$", "saFcx");
            EnumFlowerColor.I[" ".length()] = I("\u001c\u001d(", "NXlWs");
        }
        
        static {
            I();
            final EnumFlowerColor[] enum$VALUES = new EnumFlowerColor["  ".length()];
            enum$VALUES["".length()] = EnumFlowerColor.YELLOW;
            enum$VALUES[" ".length()] = EnumFlowerColor.RED;
            ENUM$VALUES = enum$VALUES;
        }
        
        private EnumFlowerColor(final String s, final int n) {
        }
        
        public BlockFlower getBlock() {
            BlockFlower blockFlower;
            if (this == EnumFlowerColor.YELLOW) {
                blockFlower = Blocks.yellow_flower;
                "".length();
                if (2 <= -1) {
                    throw null;
                }
            }
            else {
                blockFlower = Blocks.red_flower;
            }
            return blockFlower;
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
    
    public enum EnumFlowerType implements IStringSerializable
    {
        ORANGE_TULIP(EnumFlowerType.I[0xD ^ 0x3], 0x1C ^ 0x1A, EnumFlowerColor.RED, 0x59 ^ 0x5C, EnumFlowerType.I[0x72 ^ 0x7D], EnumFlowerType.I[0x7 ^ 0x17]), 
        DANDELION(EnumFlowerType.I["".length()], "".length(), EnumFlowerColor.YELLOW, "".length(), EnumFlowerType.I[" ".length()]);
        
        private final EnumFlowerColor blockType;
        private static final EnumFlowerType[][] TYPES_FOR_BLOCK;
        
        BLUE_ORCHID(EnumFlowerType.I[0xB2 ^ 0xB6], "  ".length(), EnumFlowerColor.RED, " ".length(), EnumFlowerType.I[0x3B ^ 0x3E], EnumFlowerType.I[0x87 ^ 0x81]), 
        POPPY(EnumFlowerType.I["  ".length()], " ".length(), EnumFlowerColor.RED, "".length(), EnumFlowerType.I["   ".length()]), 
        HOUSTONIA(EnumFlowerType.I[0xA9 ^ 0xA0], 0x2B ^ 0x2F, EnumFlowerColor.RED, "   ".length(), EnumFlowerType.I[0xA3 ^ 0xA9]), 
        OXEYE_DAISY(EnumFlowerType.I[0x1 ^ 0x16], 0x5A ^ 0x53, EnumFlowerColor.RED, 0x69 ^ 0x61, EnumFlowerType.I[0x5C ^ 0x44], EnumFlowerType.I[0x43 ^ 0x5A]), 
        RED_TULIP(EnumFlowerType.I[0xB2 ^ 0xB9], 0x6D ^ 0x68, EnumFlowerColor.RED, 0x84 ^ 0x80, EnumFlowerType.I[0x5A ^ 0x56], EnumFlowerType.I[0x12 ^ 0x1F]), 
        PINK_TULIP(EnumFlowerType.I[0x14 ^ 0x0], 0x59 ^ 0x51, EnumFlowerColor.RED, 0x76 ^ 0x71, EnumFlowerType.I[0x5E ^ 0x4B], EnumFlowerType.I[0x7C ^ 0x6A]);
        
        private final String unlocalizedName;
        private static final EnumFlowerType[] ENUM$VALUES;
        private static final String[] I;
        private final int meta;
        private final String name;
        
        WHITE_TULIP(EnumFlowerType.I[0x4F ^ 0x5E], 0xB0 ^ 0xB7, EnumFlowerColor.RED, 0x60 ^ 0x66, EnumFlowerType.I[0x33 ^ 0x21], EnumFlowerType.I[0x1B ^ 0x8]), 
        ALLIUM(EnumFlowerType.I[0x40 ^ 0x47], "   ".length(), EnumFlowerColor.RED, "  ".length(), EnumFlowerType.I[0x96 ^ 0x9E]);
        
        private EnumFlowerType(final String s, final int n, final EnumFlowerColor blockType, final int meta, final String name, final String unlocalizedName) {
            this.blockType = blockType;
            this.meta = meta;
            this.name = name;
            this.unlocalizedName = unlocalizedName;
        }
        
        @Override
        public String getName() {
            return this.name;
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
        
        public static EnumFlowerType getType(final EnumFlowerColor enumFlowerColor, int length) {
            final EnumFlowerType[] array = EnumFlowerType.TYPES_FOR_BLOCK[enumFlowerColor.ordinal()];
            if (length < 0 || length >= array.length) {
                length = "".length();
            }
            return array[length];
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        public int getMeta() {
            return this.meta;
        }
        
        public EnumFlowerColor getBlockType() {
            return this.blockType;
        }
        
        private EnumFlowerType(final String s, final int n, final EnumFlowerColor enumFlowerColor, final int n2, final String s2) {
            this(s, n, enumFlowerColor, n2, s2, s2);
        }
        
        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }
        
        private static void I() {
            (I = new String[0x2F ^ 0x35])["".length()] = I("\u001e\u0005\u0002\u0001=\u0016\r\u0003\u000b", "ZDLEx");
            EnumFlowerType.I[" ".length()] = I(" \u001b\r\r\"(\u0013\f\u0007", "DzciG");
            EnumFlowerType.I["  ".length()] = I("\u0013\u0016!) ", "CYqyy");
            EnumFlowerType.I["   ".length()] = I("\u001b\u001c\u0018 \r", "kshPt");
            EnumFlowerType.I[0x76 ^ 0x72] = I(";\u0019=(\u00106\u0007+%\u0006=", "yUhmO");
            EnumFlowerType.I[0x44 ^ 0x41] = I("\u0005\u000b=\"\u001d\b\u0015+/+\u0003", "ggHGB");
            EnumFlowerType.I[0x41 ^ 0x47] = I("\t\u0016<\u001d\u0005\u0019\u0019!\u0011.", "kzIxJ");
            EnumFlowerType.I[0x9D ^ 0x9A] = I("%\u0014+$\u0011)", "dXgmD");
            EnumFlowerType.I[0x85 ^ 0x8D] = I("\u00128*./\u001e", "sTFGZ");
            EnumFlowerType.I[0x2B ^ 0x22] = I("0\u0000\u0017\u0017\u00077\u0001\u000b\u0005", "xOBDS");
            EnumFlowerType.I[0xA1 ^ 0xAB] = I("\u0018\u0018\u0011\u0015$\u001f\u0019\r\u0007", "pwdfP");
            EnumFlowerType.I[0xB3 ^ 0xB8] = I("\u000b!*+1\f('$", "Ydnte");
            EnumFlowerType.I[0x88 ^ 0x84] = I("\u000b\f0.!\f\u0005=\u0001", "yiTqU");
            EnumFlowerType.I[0xB1 ^ 0xBC] = I("\u0016<)-<0,!", "bIEDL");
            EnumFlowerType.I[0x3B ^ 0x35] = I("\u001b'):1\u0011*<!:\u001d%", "Tuhtv");
            EnumFlowerType.I[0x53 ^ 0x5C] = I("#78\u0002\u001d)\u001a-\u0019\u0016%5", "LEYlz");
            EnumFlowerType.I[0x69 ^ 0x79] = I("82?\u00079\u000352\u0000.)", "LGSnI");
            EnumFlowerType.I[0xA4 ^ 0xB5] = I("\u001b#- \u0003\u0013?18\u000f\u001c", "LkdtF");
            EnumFlowerType.I[0x60 ^ 0x72] = I("0\u0002'-\u0017\u0018\u001e;5\u001b7", "GjNYr");
            EnumFlowerType.I[0xA7 ^ 0xB4] = I("\"\u001a*3=\u0001\u0007/.(", "VoFZM");
            EnumFlowerType.I[0x26 ^ 0x32] = I("617'32-5%<", "fxyll");
            EnumFlowerType.I[0x65 ^ 0x70] = I("\u001b\u000e#\u0001-\u001f\u0012!\u0003\u0002", "kgMjr");
            EnumFlowerType.I[0xB4 ^ 0xA2] = I("\u0012\u0017\u001a1(6\u000b\u00183", "fbvXX");
            EnumFlowerType.I[0xD5 ^ 0xC2] = I("\"\u001a\u001c<\u001f2\u0006\u0018,\t4", "mBYeZ");
            EnumFlowerType.I[0x60 ^ 0x78] = I("\u001d?\u0003,\u0001-#\u0007<\u0017\u000b", "rGfUd");
            EnumFlowerType.I[0x48 ^ 0x51] = I("\f\u0000\u000335'\u0019\u000f9)", "cxfJP");
        }
        
        static {
            I();
            final EnumFlowerType[] enum$VALUES = new EnumFlowerType[0x78 ^ 0x72];
            enum$VALUES["".length()] = EnumFlowerType.DANDELION;
            enum$VALUES[" ".length()] = EnumFlowerType.POPPY;
            enum$VALUES["  ".length()] = EnumFlowerType.BLUE_ORCHID;
            enum$VALUES["   ".length()] = EnumFlowerType.ALLIUM;
            enum$VALUES[0x14 ^ 0x10] = EnumFlowerType.HOUSTONIA;
            enum$VALUES[0x5D ^ 0x58] = EnumFlowerType.RED_TULIP;
            enum$VALUES[0x6D ^ 0x6B] = EnumFlowerType.ORANGE_TULIP;
            enum$VALUES[0x15 ^ 0x12] = EnumFlowerType.WHITE_TULIP;
            enum$VALUES[0x60 ^ 0x68] = EnumFlowerType.PINK_TULIP;
            enum$VALUES[0x89 ^ 0x80] = EnumFlowerType.OXEYE_DAISY;
            ENUM$VALUES = enum$VALUES;
            TYPES_FOR_BLOCK = new EnumFlowerType[EnumFlowerColor.values().length][];
            final EnumFlowerColor[] values;
            final int length = (values = EnumFlowerColor.values()).length;
            int i = "".length();
            "".length();
            if (3 < 1) {
                throw null;
            }
            while (i < length) {
                final EnumFlowerColor enumFlowerColor = values[i];
                final Collection filter = Collections2.filter((Collection)Lists.newArrayList((Object[])values()), (Predicate)new Predicate<EnumFlowerType>() {
                    private final EnumFlowerColor val$blockflower$enumflowercolor;
                    
                    public boolean apply(final Object o) {
                        return this.apply((EnumFlowerType)o);
                    }
                    
                    public boolean apply(final EnumFlowerType enumFlowerType) {
                        if (enumFlowerType.getBlockType() == this.val$blockflower$enumflowercolor) {
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
                            if (0 == 4) {
                                throw null;
                            }
                        }
                        return sb.toString();
                    }
                });
                EnumFlowerType.TYPES_FOR_BLOCK[enumFlowerColor.ordinal()] = filter.toArray(new EnumFlowerType[filter.size()]);
                ++i;
            }
        }
        
        public static EnumFlowerType[] getTypes(final EnumFlowerColor enumFlowerColor) {
            return EnumFlowerType.TYPES_FOR_BLOCK[enumFlowerColor.ordinal()];
        }
    }
}
