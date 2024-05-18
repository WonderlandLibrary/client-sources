package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockPrismarine extends Block
{
    public static final int ROUGH_META;
    public static final PropertyEnum<EnumType> VARIANT;
    public static final int DARK_META;
    private static final String[] I;
    public static final int BRICKS_META;
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return blockState.getValue(BlockPrismarine.VARIANT).getMetadata();
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        MapColor mapColor;
        if (blockState.getValue(BlockPrismarine.VARIANT) == EnumType.ROUGH) {
            mapColor = MapColor.cyanColor;
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else {
            mapColor = MapColor.diamondColor;
        }
        return mapColor;
    }
    
    @Override
    public String getLocalizedName() {
        return StatCollector.translateToLocal(String.valueOf(this.getUnlocalizedName()) + BlockPrismarine.I[" ".length()] + EnumType.ROUGH.getUnlocalizedName() + BlockPrismarine.I["  ".length()]);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockPrismarine.VARIANT, EnumType.byMetadata(n));
    }
    
    public BlockPrismarine() {
        super(Material.rock);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockPrismarine.VARIANT, EnumType.ROUGH));
        this.setCreativeTab(CreativeTabs.tabBlock);
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
            if (0 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List<ItemStack> list) {
        list.add(new ItemStack(item, " ".length(), BlockPrismarine.ROUGH_META));
        list.add(new ItemStack(item, " ".length(), BlockPrismarine.BRICKS_META));
        list.add(new ItemStack(item, " ".length(), BlockPrismarine.DARK_META));
    }
    
    static {
        I();
        VARIANT = PropertyEnum.create(BlockPrismarine.I["".length()], EnumType.class);
        ROUGH_META = EnumType.ROUGH.getMetadata();
        BRICKS_META = EnumType.BRICKS.getMetadata();
        DARK_META = EnumType.DARK.getMetadata();
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue(BlockPrismarine.VARIANT).getMetadata();
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("\u00018<<\u0015\u0019-", "wYNUt");
        BlockPrismarine.I[" ".length()] = I("G", "ilcoN");
        BlockPrismarine.I["  ".length()] = I("@=&8\u0014", "nSGUq");
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockPrismarine.VARIANT;
        return new BlockState(this, array);
    }
    
    public enum EnumType implements IStringSerializable
    {
        private final int meta;
        private final String unlocalizedName;
        private static final String[] I;
        
        ROUGH(EnumType.I["".length()], "".length(), "".length(), EnumType.I[" ".length()], EnumType.I["  ".length()]);
        
        private static final EnumType[] ENUM$VALUES;
        private final String name;
        
        BRICKS(EnumType.I["   ".length()], " ".length(), " ".length(), EnumType.I[0x72 ^ 0x76], EnumType.I[0xB0 ^ 0xB5]);
        
        private static final EnumType[] META_LOOKUP;
        
        DARK(EnumType.I[0xE ^ 0x8], "  ".length(), "  ".length(), EnumType.I[0xAD ^ 0xAA], EnumType.I[0x4D ^ 0x45]);
        
        @Override
        public String toString() {
            return this.name;
        }
        
        static {
            I();
            final EnumType[] enum$VALUES = new EnumType["   ".length()];
            enum$VALUES["".length()] = EnumType.ROUGH;
            enum$VALUES[" ".length()] = EnumType.BRICKS;
            enum$VALUES["  ".length()] = EnumType.DARK;
            ENUM$VALUES = enum$VALUES;
            META_LOOKUP = new EnumType[values().length];
            final EnumType[] values;
            final int length = (values = values()).length;
            int i = "".length();
            "".length();
            if (0 == 2) {
                throw null;
            }
            while (i < length) {
                final EnumType enumType = values[i];
                EnumType.META_LOOKUP[enumType.getMetadata()] = enumType;
                ++i;
            }
        }
        
        public static EnumType byMetadata(int length) {
            if (length < 0 || length >= EnumType.META_LOOKUP.length) {
                length = "".length();
            }
            return EnumType.META_LOOKUP[length];
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
        
        private EnumType(final String s, final int n, final int meta, final String name, final String unlocalizedName) {
            this.meta = meta;
            this.name = name;
            this.unlocalizedName = unlocalizedName;
        }
        
        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }
        
        private static void I() {
            (I = new String[0xA3 ^ 0xAA])["".length()] = I("5?%5:", "gpprr");
            EnumType.I[" ".length()] = I("\b\u0001\u0006%\u0002\u0019\u0001\u00068\n", "xsoVo");
            EnumType.I["  ".length()] = I("\n -#\u0011", "xOXDy");
            EnumType.I["   ".length()] = I(". \r\f ?", "lrDOk");
            EnumType.I[0x91 ^ 0x95] = I("\"8\u001c8*38\u001c%\"\r(\u0007\"$99", "RJuKG");
            EnumType.I[0x64 ^ 0x61] = I("\u0014>\u0011\u0012\u0005\u0005", "vLxqn");
            EnumType.I[0x3B ^ 0x3D] = I("\u0002\u0000\u0014(", "FAFcp");
            EnumType.I[0x35 ^ 0x32] = I("\u000e(<\u001d\f\u001a;'\u0005>\u000b;'\u00186", "jINvS");
            EnumType.I[0xA9 ^ 0xA1] = I("\u001d\u000b1<", "yjCWC");
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        public int getMetadata() {
            return this.meta;
        }
    }
}
