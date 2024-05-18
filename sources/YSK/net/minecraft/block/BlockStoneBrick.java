package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;

public class BlockStoneBrick extends Block
{
    public static final int CRACKED_META;
    public static final PropertyEnum<EnumType> VARIANT;
    public static final int DEFAULT_META;
    public static final int MOSSY_META;
    private static final String[] I;
    public static final int CHISELED_META;
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockStoneBrick.VARIANT;
        return new BlockState(this, array);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue(BlockStoneBrick.VARIANT).getMetadata();
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List<ItemStack> list) {
        final EnumType[] values;
        final int length = (values = EnumType.values()).length;
        int i = "".length();
        "".length();
        if (2 < 1) {
            throw null;
        }
        while (i < length) {
            list.add(new ItemStack(item, " ".length(), values[i].getMetadata()));
            ++i;
        }
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u001a\u0018\u0019#\n\u0002\r", "lykJk");
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
            if (1 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockStoneBrick.VARIANT, EnumType.byMetadata(n));
    }
    
    static {
        I();
        VARIANT = PropertyEnum.create(BlockStoneBrick.I["".length()], EnumType.class);
        DEFAULT_META = EnumType.DEFAULT.getMetadata();
        MOSSY_META = EnumType.MOSSY.getMetadata();
        CRACKED_META = EnumType.CRACKED.getMetadata();
        CHISELED_META = EnumType.CHISELED.getMetadata();
    }
    
    public BlockStoneBrick() {
        super(Material.rock);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockStoneBrick.VARIANT, EnumType.DEFAULT));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return blockState.getValue(BlockStoneBrick.VARIANT).getMetadata();
    }
    
    public enum EnumType implements IStringSerializable
    {
        private static final EnumType[] ENUM$VALUES;
        private final String name;
        
        CHISELED(EnumType.I[0xBE ^ 0xB7], "   ".length(), "   ".length(), EnumType.I[0x44 ^ 0x4E], EnumType.I[0x63 ^ 0x68]), 
        MOSSY(EnumType.I["   ".length()], " ".length(), " ".length(), EnumType.I[0x45 ^ 0x41], EnumType.I[0x96 ^ 0x93]), 
        CRACKED(EnumType.I[0x25 ^ 0x23], "  ".length(), "  ".length(), EnumType.I[0x25 ^ 0x22], EnumType.I[0x42 ^ 0x4A]);
        
        private static final String[] I;
        private final int meta;
        private final String unlocalizedName;
        
        DEFAULT(EnumType.I["".length()], "".length(), "".length(), EnumType.I[" ".length()], EnumType.I["  ".length()]);
        
        private static final EnumType[] META_LOOKUP;
        
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
                if (-1 < -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }
        
        public int getMetadata() {
            return this.meta;
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        private EnumType(final String s, final int n, final int meta, final String name, final String unlocalizedName) {
            this.meta = meta;
            this.name = name;
            this.unlocalizedName = unlocalizedName;
        }
        
        static {
            I();
            final EnumType[] enum$VALUES = new EnumType[0xB7 ^ 0xB3];
            enum$VALUES["".length()] = EnumType.DEFAULT;
            enum$VALUES[" ".length()] = EnumType.MOSSY;
            enum$VALUES["  ".length()] = EnumType.CRACKED;
            enum$VALUES["   ".length()] = EnumType.CHISELED;
            ENUM$VALUES = enum$VALUES;
            META_LOOKUP = new EnumType[values().length];
            final EnumType[] values;
            final int length = (values = values()).length;
            int i = "".length();
            "".length();
            if (3 <= 0) {
                throw null;
            }
            while (i < length) {
                final EnumType enumType = values[i];
                EnumType.META_LOOKUP[enumType.getMetadata()] = enumType;
                ++i;
            }
        }
        
        private static void I() {
            (I = new String[0x85 ^ 0x89])["".length()] = I("\r\u0010\r\u0006=\u0005\u0001", "IUKGh");
            EnumType.I[" ".length()] = I("\u0016\u001b\u001d6\u0003\u0007\u001d\u001b;\r", "eorXf");
            EnumType.I["  ".length()] = I("=\u000e*#!5\u001f", "YkLBT");
            EnumType.I["   ".length()] = I("8\u001f\n\u0007\b", "uPYTQ");
            EnumType.I[0x62 ^ 0x66] = I(" \u0016\u0010\u001d6\u0012\n\u0017\u0001!(\u001b\u0011\u0007,&", "MycnO");
            EnumType.I[0x44 ^ 0x41] = I("\u0015,\u0003#\u001f", "xCpPf");
            EnumType.I[0xB5 ^ 0xB3] = I("3\u001a\u000e7\u00115\f", "pHOtZ");
            EnumType.I[0x79 ^ 0x7E] = I("\u0015\u00107\b>\u0013\u0006\t\u0018!\u0019\f3\t'\u001f\u0001=", "vbVkU");
            EnumType.I[0x8A ^ 0x82] = I("'<4%\u0012!*", "DNUFy");
            EnumType.I[0xA6 ^ 0xAF] = I("\u0011\u0003$*\b\u001e\u000e)", "RKmyM");
            EnumType.I[0xCF ^ 0xC5] = I("\u0017#*:\u0001\u0018.'\u0016\u0017\u0000$-,\u0006\u0006\" \"", "tKCId");
            EnumType.I[0x13 ^ 0x18] = I("$\"(=)+/%", "GJANL");
        }
        
        public static EnumType byMetadata(int length) {
            if (length < 0 || length >= EnumType.META_LOOKUP.length) {
                length = "".length();
            }
            return EnumType.META_LOOKUP[length];
        }
    }
}
