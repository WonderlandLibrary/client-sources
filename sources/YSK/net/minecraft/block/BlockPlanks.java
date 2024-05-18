package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;

public class BlockPlanks extends Block
{
    public static final PropertyEnum<EnumType> VARIANT;
    private static final String[] I;
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockPlanks.VARIANT;
        return new BlockState(this, array);
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List<ItemStack> list) {
        final EnumType[] values;
        final int length = (values = EnumType.values()).length;
        int i = "".length();
        "".length();
        if (false) {
            throw null;
        }
        while (i < length) {
            list.add(new ItemStack(item, " ".length(), values[i].getMetadata()));
            ++i;
        }
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0012/\u000b0\u0006\n:", "dNyYg");
    }
    
    static {
        I();
        VARIANT = PropertyEnum.create(BlockPlanks.I["".length()], EnumType.class);
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
            if (3 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public BlockPlanks() {
        super(Material.wood);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockPlanks.VARIANT, EnumType.OAK));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockPlanks.VARIANT, EnumType.byMetadata(n));
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue(BlockPlanks.VARIANT).getMetadata();
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return blockState.getValue(BlockPlanks.VARIANT).func_181070_c();
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return blockState.getValue(BlockPlanks.VARIANT).getMetadata();
    }
    
    public enum EnumType implements IStringSerializable
    {
        BIRCH(EnumType.I[0x6D ^ 0x69], "  ".length(), "  ".length(), EnumType.I[0xB9 ^ 0xBC], MapColor.sandColor);
        
        private final String unlocalizedName;
        
        OAK(EnumType.I["".length()], "".length(), "".length(), EnumType.I[" ".length()], MapColor.woodColor), 
        JUNGLE(EnumType.I[0xBE ^ 0xB8], "   ".length(), "   ".length(), EnumType.I[0x73 ^ 0x74], MapColor.dirtColor), 
        SPRUCE(EnumType.I["  ".length()], " ".length(), " ".length(), EnumType.I["   ".length()], MapColor.obsidianColor);
        
        private final String name;
        private final MapColor field_181071_k;
        private static final String[] I;
        
        DARK_OAK(EnumType.I[0x6C ^ 0x66], 0xA7 ^ 0xA2, 0x78 ^ 0x7D, EnumType.I[0x34 ^ 0x3F], EnumType.I[0x91 ^ 0x9D], MapColor.brownColor), 
        ACACIA(EnumType.I[0x2 ^ 0xA], 0x51 ^ 0x55, 0x27 ^ 0x23, EnumType.I[0x9A ^ 0x93], MapColor.adobeColor);
        
        private static final EnumType[] META_LOOKUP;
        private final int meta;
        private static final EnumType[] ENUM$VALUES;
        
        private EnumType(final String s, final int n, final int meta, final String name, final String unlocalizedName, final MapColor field_181071_k) {
            this.meta = meta;
            this.name = name;
            this.unlocalizedName = unlocalizedName;
            this.field_181071_k = field_181071_k;
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
                if (3 != 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private EnumType(final String s, final int n, final int n2, final String s2, final MapColor mapColor) {
            this(s, n, n2, s2, s2, mapColor);
        }
        
        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        public static EnumType byMetadata(int length) {
            if (length < 0 || length >= EnumType.META_LOOKUP.length) {
                length = "".length();
            }
            return EnumType.META_LOOKUP[length];
        }
        
        public MapColor func_181070_c() {
            return this.field_181071_k;
        }
        
        static {
            I();
            final EnumType[] enum$VALUES = new EnumType[0x23 ^ 0x25];
            enum$VALUES["".length()] = EnumType.OAK;
            enum$VALUES[" ".length()] = EnumType.SPRUCE;
            enum$VALUES["  ".length()] = EnumType.BIRCH;
            enum$VALUES["   ".length()] = EnumType.JUNGLE;
            enum$VALUES[0xBD ^ 0xB9] = EnumType.ACACIA;
            enum$VALUES[0xD ^ 0x8] = EnumType.DARK_OAK;
            ENUM$VALUES = enum$VALUES;
            META_LOOKUP = new EnumType[values().length];
            final EnumType[] values;
            final int length = (values = values()).length;
            int i = "".length();
            "".length();
            if (4 < 1) {
                throw null;
            }
            while (i < length) {
                final EnumType enumType = values[i];
                EnumType.META_LOOKUP[enumType.getMetadata()] = enumType;
                ++i;
            }
        }
        
        public int getMetadata() {
            return this.meta;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        private static void I() {
            (I = new String[0x8C ^ 0x81])["".length()] = I("\f\u0014\u0019", "CURMg");
            EnumType.I[" ".length()] = I("\t\u0007\u0005", "ffnDA");
            EnumType.I["  ".length()] = I(" ?\u0004\u0005\"6", "soVPa");
            EnumType.I["   ".length()] = I("2\u0017\u0014\f\u0006$", "Agfye");
            EnumType.I[0x6D ^ 0x69] = I("8\u0013\u000439", "zZVpq");
            EnumType.I[0x2E ^ 0x2B] = I("\u000b\u000b(.\u0018", "ibZMp");
            EnumType.I[0x56 ^ 0x50] = I("\u000e\u0014)\u0012&\u0001", "DAgUj");
            EnumType.I[0x43 ^ 0x44] = I(". \u001b\t/!", "DUunC");
            EnumType.I[0xCB ^ 0xC3] = I("\u001b\u0013,\u0002\u0005\u001b", "ZPmAL");
            EnumType.I[0xCA ^ 0xC3] = I("\u0013,,$/\u0013", "rOMGF");
            EnumType.I[0xA2 ^ 0xA8] = I("\u0015,<\u0019)\u001e,%", "QmnRv");
            EnumType.I[0x43 ^ 0x48] = I("\u0015\u0016<!\t\u001e\u0016%", "qwNJV");
            EnumType.I[0x4A ^ 0x46] = I("2.-\u0015$1,", "PGJJK");
        }
    }
}
