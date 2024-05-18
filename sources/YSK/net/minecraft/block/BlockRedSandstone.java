package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockRedSandstone extends Block
{
    public static final PropertyEnum<EnumType> TYPE;
    private static final String[] I;
    
    public BlockRedSandstone() {
        super(Material.rock, BlockSand.EnumType.RED_SAND.getMapColor());
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockRedSandstone.TYPE, EnumType.DEFAULT));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return blockState.getValue(BlockRedSandstone.TYPE).getMetadata();
    }
    
    static {
        I();
        TYPE = PropertyEnum.create(BlockRedSandstone.I["".length()], EnumType.class);
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List<ItemStack> list) {
        final EnumType[] values;
        final int length = (values = EnumType.values()).length;
        int i = "".length();
        "".length();
        if (3 <= -1) {
            throw null;
        }
        while (i < length) {
            list.add(new ItemStack(item, " ".length(), values[i].getMetadata()));
            ++i;
        }
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockRedSandstone.TYPE;
        return new BlockState(this, array);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0013\u0016\u00046", "gotSx");
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
            if (1 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockRedSandstone.TYPE, EnumType.byMetadata(n));
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue(BlockRedSandstone.TYPE).getMetadata();
    }
    
    public enum EnumType implements IStringSerializable
    {
        private static final EnumType[] META_LOOKUP;
        private final int meta;
        private final String unlocalizedName;
        private static final String[] I;
        
        CHISELED(EnumType.I["   ".length()], " ".length(), " ".length(), EnumType.I[0x71 ^ 0x75], EnumType.I[0xB1 ^ 0xB4]), 
        SMOOTH(EnumType.I[0x3 ^ 0x5], "  ".length(), "  ".length(), EnumType.I[0x76 ^ 0x71], EnumType.I[0xB3 ^ 0xBB]), 
        DEFAULT(EnumType.I["".length()], "".length(), "".length(), EnumType.I[" ".length()], EnumType.I["  ".length()]);
        
        private final String name;
        private static final EnumType[] ENUM$VALUES;
        
        @Override
        public String getName() {
            return this.name;
        }
        
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
            if (false) {
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
        
        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }
        
        private EnumType(final String s, final int n, final int meta, final String name, final String unlocalizedName) {
            this.meta = meta;
            this.name = name;
            this.unlocalizedName = unlocalizedName;
        }
        
        public int getMetadata() {
            return this.meta;
        }
        
        private static void I() {
            (I = new String[0x2A ^ 0x23])["".length()] = I(".<\u00122\u0013&-", "jyTsF");
            EnumType.I[" ".length()] = I(";\"<+=()<\u0007:&)=", "IGXtN");
            EnumType.I["  ".length()] = I("!\f/\u0000\u000f)\u001d", "EiIaz");
            EnumType.I["   ".length()] = I("\u0000/\u0006<0\u000f\"\u000b", "CgOou");
            EnumType.I[0x3B ^ 0x3F] = I("7)\u00192\u001c8$\u0014\u001e\u000b1%/2\u0018:%\u00035\u0016:$", "TApAy");
            EnumType.I[0x7F ^ 0x7A] = I("\u0007\u0012\u0010)\u0013\b\u001f\u001d", "dzyZv");
            EnumType.I[0x9E ^ 0x98] = I("\u0015\u001d\n:\u000e\u000e", "FPEuZ");
            EnumType.I[0xC7 ^ 0xC0] = I(" \u001e\u001a\u001c\u0015;,\u0007\u0016\u0005\f\u0000\u0014\u001d\u0005 \u0007\u001a\u001d\u0004", "Ssusa");
            EnumType.I[0xB7 ^ 0xBF] = I("?:\n?<$", "LWePH");
        }
        
        @Override
        public String toString() {
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
                if (2 == 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
