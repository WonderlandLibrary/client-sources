package net.minecraft.block;

import net.minecraft.block.state.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;

public class BlockSandStone extends Block
{
    private static final String[] I;
    public static final PropertyEnum<EnumType> TYPE;
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0010\u001b(\"", "dbXGc");
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return MapColor.sandColor;
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
            if (3 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockSandStone.TYPE;
        return new BlockState(this, array);
    }
    
    static {
        I();
        TYPE = PropertyEnum.create(BlockSandStone.I["".length()], EnumType.class);
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List<ItemStack> list) {
        final EnumType[] values;
        final int length = (values = EnumType.values()).length;
        int i = "".length();
        "".length();
        if (2 == -1) {
            throw null;
        }
        while (i < length) {
            list.add(new ItemStack(item, " ".length(), values[i].getMetadata()));
            ++i;
        }
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockSandStone.TYPE, EnumType.byMetadata(n));
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return blockState.getValue(BlockSandStone.TYPE).getMetadata();
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue(BlockSandStone.TYPE).getMetadata();
    }
    
    public BlockSandStone() {
        super(Material.rock);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockSandStone.TYPE, EnumType.DEFAULT));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    public enum EnumType implements IStringSerializable
    {
        SMOOTH(EnumType.I[0x44 ^ 0x42], "  ".length(), "  ".length(), EnumType.I[0x10 ^ 0x17], EnumType.I[0x44 ^ 0x4C]);
        
        private static final EnumType[] ENUM$VALUES;
        
        DEFAULT(EnumType.I["".length()], "".length(), "".length(), EnumType.I[" ".length()], EnumType.I["  ".length()]);
        
        private static final EnumType[] META_LOOKUP;
        private final int metadata;
        private final String unlocalizedName;
        private final String name;
        
        CHISELED(EnumType.I["   ".length()], " ".length(), " ".length(), EnumType.I[0x24 ^ 0x20], EnumType.I[0x9D ^ 0x98]);
        
        private static final String[] I;
        
        static {
            I();
            final EnumType[] enum$VALUES = new EnumType["   ".length()];
            enum$VALUES["".length()] = EnumType.DEFAULT;
            enum$VALUES[" ".length()] = EnumType.CHISELED;
            enum$VALUES["  ".length()] = EnumType.SMOOTH;
            ENUM$VALUES = enum$VALUES;
            META_LOOKUP = new EnumType[values().length];
            final EnumType[] values;
            final int length = (values = values()).length;
            int i = "".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
            while (i < length) {
                final EnumType enumType = values[i];
                EnumType.META_LOOKUP[enumType.getMetadata()] = enumType;
                ++i;
            }
        }
        
        private EnumType(final String s, final int n, final int metadata, final String name, final String unlocalizedName) {
            this.metadata = metadata;
            this.name = name;
            this.unlocalizedName = unlocalizedName;
        }
        
        public static EnumType byMetadata(int length) {
            if (length < 0 || length >= EnumType.META_LOOKUP.length) {
                length = "".length();
            }
            return EnumType.META_LOOKUP[length];
        }
        
        public int getMetadata() {
            return this.metadata;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        private static void I() {
            (I = new String[0xCF ^ 0xC6])["".length()] = I("\u0011?*\u0016\u001a\u0019.", "UzlWO");
            EnumType.I[" ".length()] = I("\"\n \u0011\u0018%\u0004 \u0010", "QkNuk");
            EnumType.I["  ".length()] = I("1*\u0001 \u00069;", "UOgAs");
            EnumType.I["   ".length()] = I("5\f\u000b\u001c<:\u0001\u0006", "vDBOy");
            EnumType.I[0x5B ^ 0x5F] = I("\u0006>#;\f\t3.\u0017\u001a\u00048.;\u001d\n8/", "eVJHi");
            EnumType.I[0x30 ^ 0x35] = I("\u0019\u001c\u001c<,\u0016\u0011\u0011", "ztuOI");
            EnumType.I[0x32 ^ 0x34] = I("\u0017?\u0004\u0006%\f", "DrKIq");
            EnumType.I[0xBE ^ 0xB9] = I("7:\u001f.\u0017,\b\u0003 \r $\u0004.\r!", "DWpAc");
            EnumType.I[0x78 ^ 0x70] = I("\t\u001b#\u001c\u001b\u0012", "zvLso");
        }
        
        public String getUnlocalizedName() {
            return this.unlocalizedName;
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
                if (4 == 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
