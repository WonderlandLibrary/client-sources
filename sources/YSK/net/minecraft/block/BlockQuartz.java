package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.util.*;

public class BlockQuartz extends Block
{
    private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing$Axis;
    private static final String[] I;
    public static final PropertyEnum<EnumType> VARIANT;
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue(BlockQuartz.VARIANT).getMetadata();
    }
    
    @Override
    protected ItemStack createStackedBlock(final IBlockState blockState) {
        final EnumType enumType = blockState.getValue(BlockQuartz.VARIANT);
        ItemStack stackedBlock;
        if (enumType != EnumType.LINES_X && enumType != EnumType.LINES_Z) {
            stackedBlock = super.createStackedBlock(blockState);
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        else {
            stackedBlock = new ItemStack(Item.getItemFromBlock(this), " ".length(), EnumType.LINES_Y.getMetadata());
        }
        return stackedBlock;
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        if (n4 != EnumType.LINES_Y.getMetadata()) {
            IBlockState blockState;
            if (n4 == EnumType.CHISELED.getMetadata()) {
                blockState = this.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.CHISELED);
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
            }
            else {
                blockState = this.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.DEFAULT);
            }
            return blockState;
        }
        switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing$Axis()[enumFacing.getAxis().ordinal()]) {
            case 3: {
                return this.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Z);
            }
            case 1: {
                return this.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_X);
            }
            default: {
                return this.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.LINES_Y);
            }
        }
    }
    
    public BlockQuartz() {
        super(Material.rock);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockQuartz.VARIANT, EnumType.DEFAULT));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("8\r\u0018\u000e# \u0018", "NljgB");
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return MapColor.quartzColor;
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockQuartz.VARIANT;
        return new BlockState(this, array);
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
            if (4 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        final EnumType enumType = blockState.getValue(BlockQuartz.VARIANT);
        int n;
        if (enumType != EnumType.LINES_X && enumType != EnumType.LINES_Z) {
            n = enumType.getMetadata();
            "".length();
            if (2 == 3) {
                throw null;
            }
        }
        else {
            n = EnumType.LINES_Y.getMetadata();
        }
        return n;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockQuartz.VARIANT, EnumType.byMetadata(n));
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing$Axis() {
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing$Axis = BlockQuartz.$SWITCH_TABLE$net$minecraft$util$EnumFacing$Axis;
        if ($switch_TABLE$net$minecraft$util$EnumFacing$Axis != null) {
            return $switch_TABLE$net$minecraft$util$EnumFacing$Axis;
        }
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing$Axis2 = new int[EnumFacing.Axis.values().length];
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing$Axis2[EnumFacing.Axis.X.ordinal()] = " ".length();
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing$Axis2[EnumFacing.Axis.Y.ordinal()] = "  ".length();
            "".length();
            if (4 == 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing$Axis2[EnumFacing.Axis.Z.ordinal()] = "   ".length();
            "".length();
            if (1 < -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        return BlockQuartz.$SWITCH_TABLE$net$minecraft$util$EnumFacing$Axis = $switch_TABLE$net$minecraft$util$EnumFacing$Axis2;
    }
    
    static {
        I();
        VARIANT = PropertyEnum.create(BlockQuartz.I["".length()], EnumType.class);
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List<ItemStack> list) {
        list.add(new ItemStack(item, " ".length(), EnumType.DEFAULT.getMetadata()));
        list.add(new ItemStack(item, " ".length(), EnumType.CHISELED.getMetadata()));
        list.add(new ItemStack(item, " ".length(), EnumType.LINES_Y.getMetadata()));
    }
    
    public enum EnumType implements IStringSerializable
    {
        private static final EnumType[] META_LOOKUP;
        
        DEFAULT(EnumType.I["".length()], "".length(), "".length(), EnumType.I[" ".length()], EnumType.I["  ".length()]);
        
        private static final EnumType[] ENUM$VALUES;
        
        LINES_Y(EnumType.I[0x43 ^ 0x45], "  ".length(), "  ".length(), EnumType.I[0x3A ^ 0x3D], EnumType.I[0xB7 ^ 0xBF]), 
        LINES_X(EnumType.I[0x50 ^ 0x59], "   ".length(), "   ".length(), EnumType.I[0x21 ^ 0x2B], EnumType.I[0x8B ^ 0x80]);
        
        private static final String[] I;
        private final int meta;
        private final String unlocalizedName;
        private final String field_176805_h;
        
        CHISELED(EnumType.I["   ".length()], " ".length(), " ".length(), EnumType.I[0x88 ^ 0x8C], EnumType.I[0x53 ^ 0x56]), 
        LINES_Z(EnumType.I[0x17 ^ 0x1B], 0xA5 ^ 0xA1, 0x1E ^ 0x1A, EnumType.I[0x68 ^ 0x65], EnumType.I[0x1F ^ 0x11]);
        
        public static EnumType byMetadata(int length) {
            if (length < 0 || length >= EnumType.META_LOOKUP.length) {
                length = "".length();
            }
            return EnumType.META_LOOKUP[length];
        }
        
        public int getMetadata() {
            return this.meta;
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
        
        @Override
        public String getName() {
            return this.field_176805_h;
        }
        
        private static void I() {
            (I = new String[0xB1 ^ 0xBE])["".length()] = I("\u0014&1\"?\u001c7", "Pcwcj");
            EnumType.I[" ".length()] = I(">\f\r\u0014?6\u001d", "ZikuJ");
            EnumType.I["  ".length()] = I("\u0001\u0006\u001e\u000f2\t\u0017", "ecxnG");
            EnumType.I["   ".length()] = I("\u0016\u001d&63\u0019\u0010+", "UUoev");
            EnumType.I[0xBE ^ 0xBA] = I("\u0016\u001f\u0013\u0003\u0001\u0019\u0012\u001e", "uwzpd");
            EnumType.I[0x94 ^ 0x91] = I("\u0015.\u001b \u0002\u001a#\u0016", "vFrSg");
            EnumType.I[0x11 ^ 0x17] = I("\u000e\u001f\u00054:\u001d\u000f", "BVKqi");
            EnumType.I[0x42 ^ 0x45] = I("$-\u0001\u000e\u0006\u0017=", "HDoku");
            EnumType.I[0x63 ^ 0x6B] = I("\u001d\u001f\u0018-=", "qvvHN");
            EnumType.I[0xC9 ^ 0xC0] = I("\n\n\u001e\u000e\u0005\u0019\u001b", "FCPKV");
            EnumType.I[0x53 ^ 0x59] = I("\u000e>%\u0004\"=/", "bWKaQ");
            EnumType.I[0xC ^ 0x7] = I("?\u001c\f+'", "SubNT");
            EnumType.I[0x3B ^ 0x37] = I("*1'\u000f79\"", "fxiJd");
            EnumType.I[0x38 ^ 0x35] = I("9 %\u001c\u0019\n3", "UIKyj");
            EnumType.I[0x0 ^ 0xE] = I(";-:#\u0004", "WDTFw");
        }
        
        private EnumType(final String s, final int n, final int meta, final String field_176805_h, final String unlocalizedName) {
            this.meta = meta;
            this.field_176805_h = field_176805_h;
            this.unlocalizedName = unlocalizedName;
        }
        
        static {
            I();
            final EnumType[] enum$VALUES = new EnumType[0xA5 ^ 0xA0];
            enum$VALUES["".length()] = EnumType.DEFAULT;
            enum$VALUES[" ".length()] = EnumType.CHISELED;
            enum$VALUES["  ".length()] = EnumType.LINES_Y;
            enum$VALUES["   ".length()] = EnumType.LINES_X;
            enum$VALUES[0xA ^ 0xE] = EnumType.LINES_Z;
            ENUM$VALUES = enum$VALUES;
            META_LOOKUP = new EnumType[values().length];
            final EnumType[] values;
            final int length = (values = values()).length;
            int i = "".length();
            "".length();
            if (2 == -1) {
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
            return this.unlocalizedName;
        }
    }
}
