package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;

public abstract class BlockStoneSlabNew extends BlockSlab
{
    public static final PropertyBool SEAMLESS;
    public static final PropertyEnum<EnumType> VARIANT;
    private static final String[] I;
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final IBlockState withProperty = this.getDefaultState().withProperty(BlockStoneSlabNew.VARIANT, EnumType.byMetadata(n & (0xC6 ^ 0xC1)));
        IBlockState blockState2;
        if (this.isDouble()) {
            final IBlockState blockState = withProperty;
            final PropertyBool seamless = BlockStoneSlabNew.SEAMLESS;
            int n2;
            if ((n & (0x60 ^ 0x68)) != 0x0) {
                n2 = " ".length();
                "".length();
                if (false) {
                    throw null;
                }
            }
            else {
                n2 = "".length();
            }
            blockState2 = blockState.withProperty((IProperty<Comparable>)seamless, n2 != 0);
            "".length();
            if (4 == 0) {
                throw null;
            }
        }
        else {
            final IBlockState blockState3 = withProperty;
            final PropertyEnum<EnumBlockHalf> half = BlockStoneSlabNew.HALF;
            EnumBlockHalf enumBlockHalf;
            if ((n & (0x91 ^ 0x99)) == 0x0) {
                enumBlockHalf = EnumBlockHalf.BOTTOM;
                "".length();
                if (3 < 2) {
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
    
    private static void I() {
        (I = new String[0x27 ^ 0x23])["".length()] = I("7=#\u0017\u0005!+1", "DXBzi");
        BlockStoneSlabNew.I[" ".length()] = I("0#!\u0006\u0015(6", "FBSot");
        BlockStoneSlabNew.I["  ".length()] = I("G\u001d.10\u001a\u000e%1\u001c\u001d\u0000%0A\u0007\u000e&0", "ioKUo");
        BlockStoneSlabNew.I["   ".length()] = I("d", "JGhov");
    }
    
    @Override
    public Object getVariant(final ItemStack itemStack) {
        return EnumType.byMetadata(itemStack.getMetadata() & (0xD ^ 0xA));
    }
    
    static {
        I();
        SEAMLESS = PropertyBool.create(BlockStoneSlabNew.I["".length()]);
        VARIANT = PropertyEnum.create(BlockStoneSlabNew.I[" ".length()], EnumType.class);
    }
    
    public BlockStoneSlabNew() {
        super(Material.rock);
        final IBlockState baseState = this.blockState.getBaseState();
        IBlockState blockState;
        if (this.isDouble()) {
            blockState = baseState.withProperty((IProperty<Comparable>)BlockStoneSlabNew.SEAMLESS, "".length() != 0);
            "".length();
            if (-1 == 1) {
                throw null;
            }
        }
        else {
            blockState = baseState.withProperty(BlockStoneSlabNew.HALF, EnumBlockHalf.BOTTOM);
        }
        this.setDefaultState(blockState.withProperty(BlockStoneSlabNew.VARIANT, EnumType.RED_SANDSTONE));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Item.getItemFromBlock(Blocks.stone_slab2);
    }
    
    @Override
    public IProperty<?> getVariantProperty() {
        return BlockStoneSlabNew.VARIANT;
    }
    
    @Override
    public String getUnlocalizedName(final int n) {
        return String.valueOf(super.getUnlocalizedName()) + BlockStoneSlabNew.I["   ".length()] + EnumType.byMetadata(n).getUnlocalizedName();
    }
    
    @Override
    public String getLocalizedName() {
        return StatCollector.translateToLocal(String.valueOf(this.getUnlocalizedName()) + BlockStoneSlabNew.I["  ".length()]);
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return blockState.getValue(BlockStoneSlabNew.VARIANT).getMetadata();
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
            if (-1 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Item.getItemFromBlock(Blocks.stone_slab2);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = "".length() | blockState.getValue(BlockStoneSlabNew.VARIANT).getMetadata();
        if (this.isDouble()) {
            if (blockState.getValue((IProperty<Boolean>)BlockStoneSlabNew.SEAMLESS)) {
                n |= (0x6E ^ 0x66);
                "".length();
                if (1 < -1) {
                    throw null;
                }
            }
        }
        else if (blockState.getValue(BlockStoneSlabNew.HALF) == EnumBlockHalf.TOP) {
            n |= (0x6B ^ 0x63);
        }
        return n;
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List<ItemStack> list) {
        if (item != Item.getItemFromBlock(Blocks.double_stone_slab2)) {
            final EnumType[] values;
            final int length = (values = EnumType.values()).length;
            int i = "".length();
            "".length();
            if (0 >= 4) {
                throw null;
            }
            while (i < length) {
                list.add(new ItemStack(item, " ".length(), values[i].getMetadata()));
                ++i;
            }
        }
    }
    
    @Override
    protected BlockState createBlockState() {
        BlockState blockState;
        if (this.isDouble()) {
            final IProperty[] array;
            blockState = new BlockState(this, array);
            array = new IProperty["  ".length()];
            array["".length()] = BlockStoneSlabNew.SEAMLESS;
            array[" ".length()] = BlockStoneSlabNew.VARIANT;
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        else {
            final IProperty[] array2;
            blockState = new BlockState(this, array2);
            array2 = new IProperty["  ".length()];
            array2["".length()] = BlockStoneSlabNew.HALF;
            array2[" ".length()] = BlockStoneSlabNew.VARIANT;
        }
        return blockState;
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return blockState.getValue(BlockStoneSlabNew.VARIANT).func_181068_c();
    }
    
    public enum EnumType implements IStringSerializable
    {
        private static final EnumType[] ENUM$VALUES;
        private final MapColor field_181069_e;
        private static final String[] I;
        private final String name;
        private final int meta;
        private static final EnumType[] META_LOOKUP;
        
        RED_SANDSTONE(EnumType.I["".length()], "".length(), "".length(), EnumType.I[" ".length()], BlockSand.EnumType.RED_SAND.getMapColor());
        
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
                if (3 < 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private EnumType(final String s, final int n, final int meta, final String name, final MapColor field_181069_e) {
            this.meta = meta;
            this.name = name;
            this.field_181069_e = field_181069_e;
        }
        
        static {
            I();
            final EnumType[] enum$VALUES = new EnumType[" ".length()];
            enum$VALUES["".length()] = EnumType.RED_SANDSTONE;
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
        
        @Override
        public String toString() {
            return this.name;
        }
        
        public MapColor func_181068_c() {
            return this.field_181069_e;
        }
        
        public int getMetadata() {
            return this.meta;
        }
        
        public String getUnlocalizedName() {
            return this.name;
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        private static void I() {
            (I = new String["  ".length()])["".length()] = I("\u001f\u0006\"\u0010\n\f\r\"\u001c\r\u0002\r#", "MCfOY");
            EnumType.I[" ".length()] = I("\u001a0\u00158\t\t;\u0015\u0014\u000e\u0007;\u0014", "hUqgz");
        }
        
        public static EnumType byMetadata(int length) {
            if (length < 0 || length >= EnumType.META_LOOKUP.length) {
                length = "".length();
            }
            return EnumType.META_LOOKUP[length];
        }
    }
}
