package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.util.*;

public abstract class BlockStoneSlab extends BlockSlab
{
    public static final PropertyEnum<EnumType> VARIANT;
    public static final PropertyBool SEAMLESS;
    private static final String[] I;
    
    @Override
    public String getUnlocalizedName(final int n) {
        return String.valueOf(super.getUnlocalizedName()) + BlockStoneSlab.I["  ".length()] + EnumType.byMetadata(n).getUnlocalizedName();
    }
    
    @Override
    public Object getVariant(final ItemStack itemStack) {
        return EnumType.byMetadata(itemStack.getMetadata() & (0x88 ^ 0x8F));
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return blockState.getValue(BlockStoneSlab.VARIANT).func_181074_c();
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final IBlockState withProperty = this.getDefaultState().withProperty(BlockStoneSlab.VARIANT, EnumType.byMetadata(n & (0xF ^ 0x8)));
        IBlockState blockState2;
        if (this.isDouble()) {
            final IBlockState blockState = withProperty;
            final PropertyBool seamless = BlockStoneSlab.SEAMLESS;
            int n2;
            if ((n & (0xAE ^ 0xA6)) != 0x0) {
                n2 = " ".length();
                "".length();
                if (3 < 1) {
                    throw null;
                }
            }
            else {
                n2 = "".length();
            }
            blockState2 = blockState.withProperty((IProperty<Comparable>)seamless, n2 != 0);
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        else {
            final IBlockState blockState3 = withProperty;
            final PropertyEnum<EnumBlockHalf> half = BlockStoneSlab.HALF;
            EnumBlockHalf enumBlockHalf;
            if ((n & (0x3D ^ 0x35)) == 0x0) {
                enumBlockHalf = EnumBlockHalf.BOTTOM;
                "".length();
                if (4 <= 2) {
                    throw null;
                }
            }
            else {
                enumBlockHalf = EnumBlockHalf.TOP;
            }
            blockState2 = blockState3.withProperty((IProperty<Comparable>)half, enumBlockHalf);
        }
        return blockState2;
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Item.getItemFromBlock(Blocks.stone_slab);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Item.getItemFromBlock(Blocks.stone_slab);
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
            if (4 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
        SEAMLESS = PropertyBool.create(BlockStoneSlab.I["".length()]);
        VARIANT = PropertyEnum.create(BlockStoneSlab.I[" ".length()], EnumType.class);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = "".length() | blockState.getValue(BlockStoneSlab.VARIANT).getMetadata();
        if (this.isDouble()) {
            if (blockState.getValue((IProperty<Boolean>)BlockStoneSlab.SEAMLESS)) {
                n |= (0x20 ^ 0x28);
                "".length();
                if (0 < 0) {
                    throw null;
                }
            }
        }
        else if (blockState.getValue(BlockStoneSlab.HALF) == EnumBlockHalf.TOP) {
            n |= (0x34 ^ 0x3C);
        }
        return n;
    }
    
    public BlockStoneSlab() {
        super(Material.rock);
        final IBlockState baseState = this.blockState.getBaseState();
        IBlockState blockState;
        if (this.isDouble()) {
            blockState = baseState.withProperty((IProperty<Comparable>)BlockStoneSlab.SEAMLESS, "".length() != 0);
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        else {
            blockState = baseState.withProperty(BlockStoneSlab.HALF, EnumBlockHalf.BOTTOM);
        }
        this.setDefaultState(blockState.withProperty(BlockStoneSlab.VARIANT, EnumType.STONE));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    protected BlockState createBlockState() {
        BlockState blockState;
        if (this.isDouble()) {
            final IProperty[] array;
            blockState = new BlockState(this, array);
            array = new IProperty["  ".length()];
            array["".length()] = BlockStoneSlab.SEAMLESS;
            array[" ".length()] = BlockStoneSlab.VARIANT;
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        else {
            final IProperty[] array2;
            blockState = new BlockState(this, array2);
            array2 = new IProperty["  ".length()];
            array2["".length()] = BlockStoneSlab.HALF;
            array2[" ".length()] = BlockStoneSlab.VARIANT;
        }
        return blockState;
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List<ItemStack> list) {
        if (item != Item.getItemFromBlock(Blocks.double_stone_slab)) {
            final EnumType[] values;
            final int length = (values = EnumType.values()).length;
            int i = "".length();
            "".length();
            if (2 <= -1) {
                throw null;
            }
            while (i < length) {
                final EnumType enumType = values[i];
                if (enumType != EnumType.WOOD) {
                    list.add(new ItemStack(item, " ".length(), enumType.getMetadata()));
                }
                ++i;
            }
        }
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return blockState.getValue(BlockStoneSlab.VARIANT).getMetadata();
    }
    
    @Override
    public IProperty<?> getVariantProperty() {
        return BlockStoneSlab.VARIANT;
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("9\u0017\u0013((/\u0001\u0001", "JrrED");
        BlockStoneSlab.I[" ".length()] = I("\u000e'1-\u0017\u00162", "xFCDv");
        BlockStoneSlab.I["  ".length()] = I("k", "EtJwb");
    }
    
    public enum EnumType implements IStringSerializable
    {
        NETHERBRICK(EnumType.I[0x1A ^ 0xA], 0x58 ^ 0x5E, 0x12 ^ 0x14, MapColor.netherrackColor, EnumType.I[0x90 ^ 0x81], EnumType.I[0x27 ^ 0x35]);
        
        private final MapColor field_181075_k;
        private final String unlocalizedName;
        
        QUARTZ(EnumType.I[0xB6 ^ 0xA5], 0x34 ^ 0x33, 0x9C ^ 0x9B, MapColor.quartzColor, EnumType.I[0xA5 ^ 0xB1]);
        
        private final int meta;
        
        WOOD(EnumType.I[0x2D ^ 0x28], "  ".length(), "  ".length(), MapColor.woodColor, EnumType.I[0x56 ^ 0x50], EnumType.I[0xC4 ^ 0xC3]);
        
        private static final EnumType[] ENUM$VALUES;
        
        STONE(EnumType.I["".length()], "".length(), "".length(), MapColor.stoneColor, EnumType.I[" ".length()]), 
        SMOOTHBRICK(EnumType.I[0xA2 ^ 0xAF], 0x84 ^ 0x81, 0x8D ^ 0x88, MapColor.stoneColor, EnumType.I[0x7A ^ 0x74], EnumType.I[0x8F ^ 0x80]);
        
        private final String name;
        private static final String[] I;
        private static final EnumType[] META_LOOKUP;
        
        COBBLESTONE(EnumType.I[0x7F ^ 0x77], "   ".length(), "   ".length(), MapColor.stoneColor, EnumType.I[0xAD ^ 0xA4], EnumType.I[0x70 ^ 0x7A]), 
        SAND(EnumType.I["  ".length()], " ".length(), " ".length(), MapColor.sandColor, EnumType.I["   ".length()], EnumType.I[0x1E ^ 0x1A]), 
        BRICK(EnumType.I[0x6 ^ 0xD], 0xBA ^ 0xBE, 0x8A ^ 0x8E, MapColor.redColor, EnumType.I[0xCC ^ 0xC0]);
        
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
                if (2 != 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        private EnumType(final String s, final int n, final int meta, final MapColor field_181075_k, final String name, final String unlocalizedName) {
            this.meta = meta;
            this.field_181075_k = field_181075_k;
            this.name = name;
            this.unlocalizedName = unlocalizedName;
        }
        
        public MapColor func_181074_c() {
            return this.field_181075_k;
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        private static void I() {
            (I = new String[0xA0 ^ 0xB5])["".length()] = I("63-+,", "egbei");
            EnumType.I[" ".length()] = I("\u0011!;-\u0017", "bUTCr");
            EnumType.I["  ".length()] = I("\u0015\u0014,\u0012", "FUbVf");
            EnumType.I["   ".length()] = I("\n\u0006\u0018\u0007*\r\b\u0018\u0006", "ygvcY");
            EnumType.I[0x5D ^ 0x59] = I("* (\u0002", "YAFfz");
            EnumType.I[0x5B ^ 0x5E] = I("\u0015#\u0003\u0006", "BlLBB");
            EnumType.I[0x48 ^ 0x4E] = I("\u0018.\u0003>\u0011\u0000-\b", "oAlZN");
            EnumType.I[0x63 ^ 0x64] = I("<\u001d<\u0002", "KrSfr");
            EnumType.I[0x76 ^ 0x7E] = I(" \u001e\u000f!\r&\u0002\u0019,\u000f&", "cQMcA");
            EnumType.I[0x88 ^ 0x81] = I("-$*-=+8< ?+", "NKHOQ");
            EnumType.I[0x88 ^ 0x82] = I("\u0002.\u0011\u0005\u001f\u0004", "aAsgs");
            EnumType.I[0x27 ^ 0x2C] = I("'*:\u0005\u0018", "exsFS");
            EnumType.I[0xC ^ 0x0] = I("\u0001\n+\u0005>", "cxBfU");
            EnumType.I[0x2D ^ 0x20] = I("\u0018\u000b,:\u0005\u0003\u00041<\u0012\u0000", "KFcuQ");
            EnumType.I[0x5A ^ 0x54] = I("\u0010\u001a\b7&<\f\u00150 \b", "cngYC");
            EnumType.I[0x44 ^ 0x4B] = I("89$,\u0005#\u0007?,\u001f.\u00169*\u0012 ", "KTKCq");
            EnumType.I[0x4B ^ 0x5B] = I("<\f\u001e\r\t \u000b\u0018\f\u000f9", "rIJEL");
            EnumType.I[0x66 ^ 0x77] = I("\u000f\u0002=\u000f,\u00138+\u0015 \u0002\f", "agIgI");
            EnumType.I[0x88 ^ 0x9A] = I("\u0017\u0015-\u0002\u000e\u000b2+\u0003\b\u0012", "ypYjk");
            EnumType.I[0xA6 ^ 0xB5] = I("%;\u0007\u001a:.", "tnFHn");
            EnumType.I[0x3 ^ 0x17] = I("\u001b14\b\f\u0010", "jDUzx");
        }
        
        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }
        
        public static EnumType byMetadata(int length) {
            if (length < 0 || length >= EnumType.META_LOOKUP.length) {
                length = "".length();
            }
            return EnumType.META_LOOKUP[length];
        }
        
        public int getMetadata() {
            return this.meta;
        }
        
        private EnumType(final String s, final int n, final int n2, final MapColor mapColor, final String s2) {
            this(s, n, n2, mapColor, s2, s2);
        }
        
        static {
            I();
            final EnumType[] enum$VALUES = new EnumType[0x74 ^ 0x7C];
            enum$VALUES["".length()] = EnumType.STONE;
            enum$VALUES[" ".length()] = EnumType.SAND;
            enum$VALUES["  ".length()] = EnumType.WOOD;
            enum$VALUES["   ".length()] = EnumType.COBBLESTONE;
            enum$VALUES[0x4A ^ 0x4E] = EnumType.BRICK;
            enum$VALUES[0xC1 ^ 0xC4] = EnumType.SMOOTHBRICK;
            enum$VALUES[0x34 ^ 0x32] = EnumType.NETHERBRICK;
            enum$VALUES[0xB7 ^ 0xB0] = EnumType.QUARTZ;
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
    }
}
