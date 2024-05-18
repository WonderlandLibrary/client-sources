package net.minecraft.block;

import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockSand extends BlockFalling
{
    public static final PropertyEnum<EnumType> VARIANT;
    private static final String[] I;
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\f/\n?\u0019\u0014:", "zNxVx");
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
            if (4 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List<ItemStack> list) {
        final EnumType[] values;
        final int length = (values = EnumType.values()).length;
        int i = "".length();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (i < length) {
            list.add(new ItemStack(item, " ".length(), values[i].getMetadata()));
            ++i;
        }
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue(BlockSand.VARIANT).getMetadata();
    }
    
    public BlockSand() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockSand.VARIANT, EnumType.SAND));
    }
    
    static {
        I();
        VARIANT = PropertyEnum.create(BlockSand.I["".length()], EnumType.class);
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return blockState.getValue(BlockSand.VARIANT).getMapColor();
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockSand.VARIANT, EnumType.byMetadata(n));
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockSand.VARIANT;
        return new BlockState(this, array);
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return blockState.getValue(BlockSand.VARIANT).getMetadata();
    }
    
    public enum EnumType implements IStringSerializable
    {
        private final int meta;
        private static final String[] I;
        
        SAND(EnumType.I["".length()], "".length(), "".length(), EnumType.I[" ".length()], EnumType.I["  ".length()], MapColor.sandColor), 
        RED_SAND(EnumType.I["   ".length()], " ".length(), " ".length(), EnumType.I[0x1E ^ 0x1A], EnumType.I[0x33 ^ 0x36], MapColor.adobeColor);
        
        private final String unlocalizedName;
        private static final EnumType[] ENUM$VALUES;
        private final String name;
        private final MapColor mapColor;
        private static final EnumType[] META_LOOKUP;
        
        @Override
        public String toString() {
            return this.name;
        }
        
        private static void I() {
            (I = new String[0xA6 ^ 0xA0])["".length()] = I("\u0003.\u0000\t", "PoNMO");
            EnumType.I[" ".length()] = I("\t\u0018\u001d\u0001", "zyseZ");
            EnumType.I["  ".length()] = I("\u0005\t0\u0014\f\r\u0018", "alVuy");
            EnumType.I["   ".length()] = I("\u001d\t .\u0016\u000e\u0002 ", "OLdqE");
            EnumType.I[0x91 ^ 0x95] = I("$\b\u0005\b97\u0003\u0005", "VmaWJ");
            EnumType.I[0x3B ^ 0x3E] = I("\u0007<\n", "uYnPV");
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
        
        public MapColor getMapColor() {
            return this.mapColor;
        }
        
        private EnumType(final String s, final int n, final int meta, final String name, final String unlocalizedName, final MapColor mapColor) {
            this.meta = meta;
            this.name = name;
            this.mapColor = mapColor;
            this.unlocalizedName = unlocalizedName;
        }
        
        public int getMetadata() {
            return this.meta;
        }
        
        static {
            I();
            final EnumType[] enum$VALUES = new EnumType["  ".length()];
            enum$VALUES["".length()] = EnumType.SAND;
            enum$VALUES[" ".length()] = EnumType.RED_SAND;
            ENUM$VALUES = enum$VALUES;
            META_LOOKUP = new EnumType[values().length];
            final EnumType[] values;
            final int length = (values = values()).length;
            int i = "".length();
            "".length();
            if (3 == 0) {
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
