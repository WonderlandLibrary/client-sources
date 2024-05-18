package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.init.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;

public class BlockStone extends Block
{
    public static final PropertyEnum<EnumType> VARIANT;
    private static final String[] I;
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return blockState.getValue(BlockStone.VARIANT).getMetadata();
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        Item item;
        if (blockState.getValue(BlockStone.VARIANT) == EnumType.STONE) {
            item = Item.getItemFromBlock(Blocks.cobblestone);
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        else {
            item = Item.getItemFromBlock(Blocks.stone);
        }
        return item;
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List<ItemStack> list) {
        final EnumType[] values;
        final int length = (values = EnumType.values()).length;
        int i = "".length();
        "".length();
        if (-1 >= 3) {
            throw null;
        }
        while (i < length) {
            list.add(new ItemStack(item, " ".length(), values[i].getMetadata()));
            ++i;
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
            if (-1 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("16?=;)#", "GWMTZ");
        BlockStone.I[" ".length()] = I("D", "jYygh");
        BlockStone.I["  ".length()] = I("W\u0007\u0013\u000f\r", "yirbh");
    }
    
    static {
        I();
        VARIANT = PropertyEnum.create(BlockStone.I["".length()], EnumType.class);
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return blockState.getValue(BlockStone.VARIANT).func_181072_c();
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockStone.VARIANT;
        return new BlockState(this, array);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue(BlockStone.VARIANT).getMetadata();
    }
    
    public BlockStone() {
        super(Material.rock);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockStone.VARIANT, EnumType.STONE));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockStone.VARIANT, EnumType.byMetadata(n));
    }
    
    @Override
    public String getLocalizedName() {
        return StatCollector.translateToLocal(String.valueOf(this.getUnlocalizedName()) + BlockStone.I[" ".length()] + EnumType.STONE.getUnlocalizedName() + BlockStone.I["  ".length()]);
    }
    
    public enum EnumType implements IStringSerializable
    {
        private final MapColor field_181073_l;
        private static final EnumType[] META_LOOKUP;
        private final int meta;
        private static final String[] I;
        
        GRANITE_SMOOTH(EnumType.I[0x42 ^ 0x46], "  ".length(), "  ".length(), MapColor.dirtColor, EnumType.I[0x7C ^ 0x79], EnumType.I[0xC6 ^ 0xC0]);
        
        private static final EnumType[] ENUM$VALUES;
        private final String unlocalizedName;
        
        ANDESITE(EnumType.I[0x5B ^ 0x57], 0x8F ^ 0x8A, 0xC7 ^ 0xC2, MapColor.stoneColor, EnumType.I[0x25 ^ 0x28]);
        
        private final String name;
        
        GRANITE(EnumType.I["  ".length()], " ".length(), " ".length(), MapColor.dirtColor, EnumType.I["   ".length()]), 
        STONE(EnumType.I["".length()], "".length(), "".length(), MapColor.stoneColor, EnumType.I[" ".length()]), 
        DIORITE(EnumType.I[0x54 ^ 0x53], "   ".length(), "   ".length(), MapColor.quartzColor, EnumType.I[0x4D ^ 0x45]), 
        ANDESITE_SMOOTH(EnumType.I[0x29 ^ 0x27], 0x50 ^ 0x56, 0x7A ^ 0x7C, MapColor.stoneColor, EnumType.I[0x41 ^ 0x4E], EnumType.I[0x6 ^ 0x16]), 
        DIORITE_SMOOTH(EnumType.I[0x1F ^ 0x16], 0xAF ^ 0xAB, 0x32 ^ 0x36, MapColor.quartzColor, EnumType.I[0x4A ^ 0x40], EnumType.I[0x1D ^ 0x16]);
        
        private EnumType(final String s, final int n, final int meta, final MapColor field_181073_l, final String name, final String unlocalizedName) {
            this.meta = meta;
            this.name = name;
            this.unlocalizedName = unlocalizedName;
            this.field_181073_l = field_181073_l;
        }
        
        private static void I() {
            (I = new String[0x90 ^ 0x81])["".length()] = I("\u0001\u001c\u0004!*", "RHKoo");
            EnumType.I[" ".length()] = I(":67\u001a\u000b", "IBXtn");
            EnumType.I["  ".length()] = I("2!*8\f!6", "uskvE");
            EnumType.I["   ".length()] = I("=\b1\u000b\u001a.\u001f", "ZzPes");
            EnumType.I[0x54 ^ 0x50] = I("1& 4\u000f\"1>)\u000b9;52", "vtazF");
            EnumType.I[0xA7 ^ 0xA2] = I("<\u0019!*\"'+)77!\u001d: ", "OtNEV");
            EnumType.I[0x31 ^ 0x37] = I(",\u001c\u0006%\u001a?\u000b4&\u001c$\u001a\u000f", "KngKs");
            EnumType.I[0x3E ^ 0x39] = I(">,\u0016;\u001a. ", "zeYiS");
            EnumType.I[0x2F ^ 0x27] = I("\u0001\u001b 5-\u0011\u0017", "erOGD");
            EnumType.I[0x76 ^ 0x7F] = I("\u0017\u001e;\u0014\u0007\u0007\u0012+\u0015\u0003\u001c\u0018 \u000e", "SWtFN");
            EnumType.I[0x6F ^ 0x65] = I("\u001d+\u001b\u0002\f\u0006\u0019\u0010\u0004\u0017\u001c/\u0000\b", "nFtmx");
            EnumType.I[0x47 ^ 0x4C] = I(">\u0000'4%.\f\u001b+#5\u001d ", "ZiHFL");
            EnumType.I[0xBD ^ 0xB1] = I("\u00156)6\u0017\u001d,(", "TxmsD");
            EnumType.I[0x7F ^ 0x72] = I("\u000e\u001a/\u0002\u0011\u0006\u0000.", "otKgb");
            EnumType.I[0x1C ^ 0x12] = I("&6\u0002$\u0004.,\u0003>\u0004*7\t5\u001f", "gxFaW");
            EnumType.I[0xB0 ^ 0xBF] = I("\u0003=7!\u0003\u0018\u000f9 \u0013\u0015#1:\u0012", "pPXNw");
            EnumType.I[0xA4 ^ 0xB4] = I("\u00006/\u0007\u0007\b,.1\u0019\u000e7?\n", "aXKbt");
        }
        
        public static EnumType byMetadata(int length) {
            if (length < 0 || length >= EnumType.META_LOOKUP.length) {
                length = "".length();
            }
            return EnumType.META_LOOKUP[length];
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        private EnumType(final String s, final int n, final int n2, final MapColor mapColor, final String s2) {
            this(s, n, n2, mapColor, s2, s2);
        }
        
        public int getMetadata() {
            return this.meta;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }
        
        public MapColor func_181072_c() {
            return this.field_181073_l;
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
                if (3 >= 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        static {
            I();
            final EnumType[] enum$VALUES = new EnumType[0x89 ^ 0x8E];
            enum$VALUES["".length()] = EnumType.STONE;
            enum$VALUES[" ".length()] = EnumType.GRANITE;
            enum$VALUES["  ".length()] = EnumType.GRANITE_SMOOTH;
            enum$VALUES["   ".length()] = EnumType.DIORITE;
            enum$VALUES[0xE ^ 0xA] = EnumType.DIORITE_SMOOTH;
            enum$VALUES[0x83 ^ 0x86] = EnumType.ANDESITE;
            enum$VALUES[0x17 ^ 0x11] = EnumType.ANDESITE_SMOOTH;
            ENUM$VALUES = enum$VALUES;
            META_LOOKUP = new EnumType[values().length];
            final EnumType[] values;
            final int length = (values = values()).length;
            int i = "".length();
            "".length();
            if (4 == 2) {
                throw null;
            }
            while (i < length) {
                final EnumType enumType = values[i];
                EnumType.META_LOOKUP[enumType.getMetadata()] = enumType;
                ++i;
            }
        }
    }
}
