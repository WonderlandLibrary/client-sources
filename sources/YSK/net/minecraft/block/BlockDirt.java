package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;

public class BlockDirt extends Block
{
    private static final String[] I;
    public static final PropertyBool SNOWY;
    public static final PropertyEnum<DirtType> VARIANT;
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        DirtType dirt = blockState.getValue(BlockDirt.VARIANT);
        if (dirt == DirtType.PODZOL) {
            dirt = DirtType.DIRT;
        }
        return dirt.getMetadata();
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
            if (2 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getDamageValue(final World world, final BlockPos blockPos) {
        final IBlockState blockState = world.getBlockState(blockPos);
        int n;
        if (blockState.getBlock() != this) {
            n = "".length();
            "".length();
            if (!true) {
                throw null;
            }
        }
        else {
            n = blockState.getValue(BlockDirt.VARIANT).getMetadata();
        }
        return n;
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty["  ".length()];
        array["".length()] = BlockDirt.VARIANT;
        array[" ".length()] = BlockDirt.SNOWY;
        return new BlockState(this, array);
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List<ItemStack> list) {
        list.add(new ItemStack(this, " ".length(), DirtType.DIRT.getMetadata()));
        list.add(new ItemStack(this, " ".length(), DirtType.COARSE_DIRT.getMetadata()));
        list.add(new ItemStack(this, " ".length(), DirtType.PODZOL.getMetadata()));
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue(BlockDirt.VARIANT).getMetadata();
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockDirt.VARIANT, DirtType.byMetadata(n));
    }
    
    static {
        I();
        VARIANT = PropertyEnum.create(BlockDirt.I["".length()], DirtType.class);
        SNOWY = PropertyBool.create(BlockDirt.I[" ".length()]);
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return blockState.getValue(BlockDirt.VARIANT).func_181066_d();
    }
    
    @Override
    public IBlockState getActualState(IBlockState withProperty, final IBlockAccess blockAccess, final BlockPos blockPos) {
        if (withProperty.getValue(BlockDirt.VARIANT) == DirtType.PODZOL) {
            final Block block = blockAccess.getBlockState(blockPos.up()).getBlock();
            final IBlockState blockState = withProperty;
            final PropertyBool snowy = BlockDirt.SNOWY;
            int n;
            if (block != Blocks.snow && block != Blocks.snow_layer) {
                n = "".length();
                "".length();
                if (0 == -1) {
                    throw null;
                }
            }
            else {
                n = " ".length();
            }
            withProperty = blockState.withProperty((IProperty<Comparable>)snowy, n != 0);
        }
        return withProperty;
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u0013\u0019\u0011\u001c#\u000b\f", "excuB");
        BlockDirt.I[" ".length()] = I("\u0006\f\b>\u001f", "ubgIf");
    }
    
    protected BlockDirt() {
        super(Material.ground);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockDirt.VARIANT, DirtType.DIRT).withProperty((IProperty<Comparable>)BlockDirt.SNOWY, (boolean)("".length() != 0)));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    public enum DirtType implements IStringSerializable
    {
        COARSE_DIRT(DirtType.I["   ".length()], " ".length(), " ".length(), DirtType.I[0x2 ^ 0x6], DirtType.I[0xC1 ^ 0xC4], MapColor.dirtColor), 
        DIRT(DirtType.I["".length()], "".length(), "".length(), DirtType.I[" ".length()], DirtType.I["  ".length()], MapColor.dirtColor);
        
        private final int metadata;
        private static final DirtType[] ENUM$VALUES;
        private final MapColor field_181067_h;
        
        PODZOL(DirtType.I[0xA ^ 0xC], "  ".length(), "  ".length(), DirtType.I[0x21 ^ 0x26], MapColor.obsidianColor);
        
        private static final String[] I;
        private static final DirtType[] METADATA_LOOKUP;
        private final String name;
        private final String unlocalizedName;
        
        private DirtType(final String s, final int n, final int metadata, final String name, final String unlocalizedName, final MapColor field_181067_h) {
            this.metadata = metadata;
            this.name = name;
            this.unlocalizedName = unlocalizedName;
            this.field_181067_h = field_181067_h;
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
                if (-1 == 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private DirtType(final String s, final int n, final int n2, final String s2, final MapColor mapColor) {
            this(s, n, n2, s2, s2, mapColor);
        }
        
        static {
            I();
            final DirtType[] enum$VALUES = new DirtType["   ".length()];
            enum$VALUES["".length()] = DirtType.DIRT;
            enum$VALUES[" ".length()] = DirtType.COARSE_DIRT;
            enum$VALUES["  ".length()] = DirtType.PODZOL;
            ENUM$VALUES = enum$VALUES;
            METADATA_LOOKUP = new DirtType[values().length];
            final DirtType[] values;
            final int length = (values = values()).length;
            int i = "".length();
            "".length();
            if (3 == 2) {
                throw null;
            }
            while (i < length) {
                final DirtType dirtType = values[i];
                DirtType.METADATA_LOOKUP[dirtType.getMetadata()] = dirtType;
                ++i;
            }
        }
        
        private static void I() {
            (I = new String[0x20 ^ 0x28])["".length()] = I(" \u0004\u00156", "dMGbn");
            DirtType.I[" ".length()] = I("\u0005\u0013<\u0006", "azNrx");
            DirtType.I["  ".length()] = I("\u0016*1\u001b\u0013\u001e;", "rOWzf");
            DirtType.I["   ".length()] = I("\u0011:\u000f&0\u0017*\n=1\u0006", "RuNtc");
            DirtType.I[0x64 ^ 0x60] = I("5\u001c\u0018\u000703,\u001d\u001c1\"", "VsyuC");
            DirtType.I[0xA9 ^ 0xAC] = I("\u000b\u00190\u0018#\r", "hvQjP");
            DirtType.I[0xA9 ^ 0xAF] = I("\u001a\u0005\u0011\u000e!\u0006", "JJUTn");
            DirtType.I[0x59 ^ 0x5E] = I(" \u0019\u00026\u0002<", "PvfLm");
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        public static DirtType byMetadata(int length) {
            if (length < 0 || length >= DirtType.METADATA_LOOKUP.length) {
                length = "".length();
            }
            return DirtType.METADATA_LOOKUP[length];
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        public int getMetadata() {
            return this.metadata;
        }
        
        public MapColor func_181066_d() {
            return this.field_181067_h;
        }
        
        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }
    }
}
