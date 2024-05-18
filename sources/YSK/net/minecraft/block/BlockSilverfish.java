package net.minecraft.block;

import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import net.minecraft.block.properties.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockSilverfish extends Block
{
    private static final String[] I;
    public static final PropertyEnum<EnumType> VARIANT;
    private static int[] $SWITCH_TABLE$net$minecraft$block$BlockSilverfish$EnumType;
    
    static {
        I();
        VARIANT = PropertyEnum.create(BlockSilverfish.I["".length()], EnumType.class);
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return "".length();
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List<ItemStack> list) {
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
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        if (!world.isRemote && world.getGameRules().getBoolean(BlockSilverfish.I[" ".length()])) {
            final EntitySilverfish entitySilverfish = new EntitySilverfish(world);
            entitySilverfish.setLocationAndAngles(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5, 0.0f, 0.0f);
            world.spawnEntityInWorld(entitySilverfish);
            entitySilverfish.spawnExplosionParticle();
        }
    }
    
    @Override
    protected ItemStack createStackedBlock(final IBlockState blockState) {
        switch ($SWITCH_TABLE$net$minecraft$block$BlockSilverfish$EnumType()[blockState.getValue(BlockSilverfish.VARIANT).ordinal()]) {
            case 2: {
                return new ItemStack(Blocks.cobblestone);
            }
            case 3: {
                return new ItemStack(Blocks.stonebrick);
            }
            case 4: {
                return new ItemStack(Blocks.stonebrick, " ".length(), BlockStoneBrick.EnumType.MOSSY.getMetadata());
            }
            case 5: {
                return new ItemStack(Blocks.stonebrick, " ".length(), BlockStoneBrick.EnumType.CRACKED.getMetadata());
            }
            case 6: {
                return new ItemStack(Blocks.stonebrick, " ".length(), BlockStoneBrick.EnumType.CHISELED.getMetadata());
            }
            default: {
                return new ItemStack(Blocks.stone);
            }
        }
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockSilverfish.VARIANT, EnumType.byMetadata(n));
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I(" \u000b>\b\n8\u001e", "VjLak");
        BlockSilverfish.I[" ".length()] = I("3\u001f<\u0006\u000e24\u001a\u0000\u0012$", "Wphob");
    }
    
    public BlockSilverfish() {
        super(Material.clay);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockSilverfish.VARIANT, EnumType.STONE));
        this.setHardness(0.0f);
        this.setCreativeTab(CreativeTabs.tabDecorations);
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
            if (false) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockSilverfish.VARIANT;
        return new BlockState(this, array);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue(BlockSilverfish.VARIANT).getMetadata();
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$block$BlockSilverfish$EnumType() {
        final int[] $switch_TABLE$net$minecraft$block$BlockSilverfish$EnumType = BlockSilverfish.$SWITCH_TABLE$net$minecraft$block$BlockSilverfish$EnumType;
        if ($switch_TABLE$net$minecraft$block$BlockSilverfish$EnumType != null) {
            return $switch_TABLE$net$minecraft$block$BlockSilverfish$EnumType;
        }
        final int[] $switch_TABLE$net$minecraft$block$BlockSilverfish$EnumType2 = new int[EnumType.values().length];
        try {
            $switch_TABLE$net$minecraft$block$BlockSilverfish$EnumType2[EnumType.CHISELED_STONEBRICK.ordinal()] = (0x69 ^ 0x6F);
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockSilverfish$EnumType2[EnumType.COBBLESTONE.ordinal()] = "  ".length();
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockSilverfish$EnumType2[EnumType.CRACKED_STONEBRICK.ordinal()] = (0xB8 ^ 0xBD);
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockSilverfish$EnumType2[EnumType.MOSSY_STONEBRICK.ordinal()] = (0x3D ^ 0x39);
            "".length();
            if (3 == 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockSilverfish$EnumType2[EnumType.STONE.ordinal()] = " ".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockSilverfish$EnumType2[EnumType.STONEBRICK.ordinal()] = "   ".length();
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        return BlockSilverfish.$SWITCH_TABLE$net$minecraft$block$BlockSilverfish$EnumType = $switch_TABLE$net$minecraft$block$BlockSilverfish$EnumType2;
    }
    
    @Override
    public int getDamageValue(final World world, final BlockPos blockPos) {
        final IBlockState blockState = world.getBlockState(blockPos);
        return blockState.getBlock().getMetaFromState(blockState);
    }
    
    public static boolean canContainSilverfish(final IBlockState blockState) {
        final Block block = blockState.getBlock();
        if (blockState != Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.STONE) && block != Blocks.cobblestone && block != Blocks.stonebrick) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    public enum EnumType implements IStringSerializable
    {
        private final String unlocalizedName;
        private static final EnumType[] ENUM$VALUES;
        private final String name;
        private final int meta;
        
        CRACKED_STONEBRICK(0x57 ^ 0x53, 0xB0 ^ 0xB4, EnumType.I[0x9B ^ 0x97], EnumType.I[0x6A ^ 0x67]) {
            @Override
            public IBlockState getModelBlock() {
                return Blocks.stonebrick.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CRACKED);
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
                    if (2 == 4) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        }, 
        COBBLESTONE(" ".length(), " ".length(), EnumType.I["   ".length()], EnumType.I[0x3B ^ 0x3F]) {
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
                    if (!true) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public IBlockState getModelBlock() {
                return Blocks.cobblestone.getDefaultState();
            }
        }, 
        MOSSY_STONEBRICK("   ".length(), "   ".length(), EnumType.I[0x59 ^ 0x50], EnumType.I[0x26 ^ 0x2C]) {
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
                    if (-1 == 4) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public IBlockState getModelBlock() {
                return Blocks.stonebrick.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.MOSSY);
            }
        }, 
        CHISELED_STONEBRICK(0x71 ^ 0x74, 0x8A ^ 0x8F, EnumType.I[0x6C ^ 0x63], EnumType.I[0x53 ^ 0x43]) {
            @Override
            public IBlockState getModelBlock() {
                return Blocks.stonebrick.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED);
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
                    if (!true) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        };
        
        private static final EnumType[] META_LOOKUP;
        
        STONEBRICK("  ".length(), "  ".length(), EnumType.I[0x76 ^ 0x70], EnumType.I[0x6B ^ 0x6C]) {
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
                    if (2 <= -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public IBlockState getModelBlock() {
                return Blocks.stonebrick.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.DEFAULT);
            }
        }, 
        STONE("".length(), "".length(), EnumType.I[" ".length()]) {
            @Override
            public IBlockState getModelBlock() {
                return Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.STONE);
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
        };
        
        private static final String[] I;
        
        public int getMetadata() {
            return this.meta;
        }
        
        static {
            I();
            final EnumType[] enum$VALUES = new EnumType[0x27 ^ 0x21];
            enum$VALUES["".length()] = EnumType.STONE;
            enum$VALUES[" ".length()] = EnumType.COBBLESTONE;
            enum$VALUES["  ".length()] = EnumType.STONEBRICK;
            enum$VALUES["   ".length()] = EnumType.MOSSY_STONEBRICK;
            enum$VALUES[0x94 ^ 0x90] = EnumType.CRACKED_STONEBRICK;
            enum$VALUES[0x65 ^ 0x60] = EnumType.CHISELED_STONEBRICK;
            ENUM$VALUES = enum$VALUES;
            META_LOOKUP = new EnumType[values().length];
            final EnumType[] values;
            final int length = (values = values()).length;
            int i = "".length();
            "".length();
            if (0 == 3) {
                throw null;
            }
            while (i < length) {
                final EnumType enumType = values[i];
                EnumType.META_LOOKUP[enumType.getMetadata()] = enumType;
                ++i;
            }
        }
        
        public abstract IBlockState getModelBlock();
        
        private static void I() {
            (I = new String[0x56 ^ 0x47])["".length()] = I("\u001f\"5\u0019*", "LvzWo");
            EnumType.I[" ".length()] = I("90\u0017\u0018\"", "JDxvG");
            EnumType.I["  ".length()] = I("&\u0019\u000b0\t \u0005\u001d=\u000b ", "eVIrE");
            EnumType.I["   ".length()] = I("5\u00034'!3\u001f\"*#3", "VlVEM");
            EnumType.I[0xBB ^ 0xBF] = I("\n\u001d*4\u001e\f", "irHVr");
            EnumType.I[0x52 ^ 0x57] = I("\u0019\u0001\u00008\n\b\u0007\u00065\u0004", "JUOvO");
            EnumType.I[0x5C ^ 0x5A] = I("\u0019=\u00028+5+\u001f?-\u0001", "jImVN");
            EnumType.I[0xF ^ 0x8] = I("\u0014\n,:\u0004", "vxEYo");
            EnumType.I[0xAA ^ 0xA2] = I("8\r#'\u0018*\u0011$;\u000f0\u0000\"=\u0002>", "uBptA");
            EnumType.I[0x2F ^ 0x26] = I("\u0014()\u0010=&%(\n'\u0012", "yGZcD");
            EnumType.I[0xA7 ^ 0xAD] = I("))\u000b6\u001e&4\u0011&\f", "DFxEg");
            EnumType.I[0x86 ^ 0x8D] = I("1\u001e \u0015\r7\b>\u0005\u0012=\u0002$\u0014\u0014;\u000f*", "rLaVF");
            EnumType.I[0x2 ^ 0xE] = I("\u0016\u00136$?\u0010\u0005\b%&\u001c\u0002<", "uaWGT");
            EnumType.I[0x2E ^ 0x23] = I("\u001756*\u000e\u0011#5;\f\u0017,", "tGWIe");
            EnumType.I[0xD ^ 0x3] = I("(\u0018\u00102/'\u0015\u001d>9?\u001f\u0017$(9\u0019\u001a*", "kPYaj");
            EnumType.I[0x3C ^ 0x33] = I("\u0013\u0005\u000b*\u0014\u001c\b\u0006\u0006\u0013\u0002\u0004\u00012", "pmbYq");
            EnumType.I[0x66 ^ 0x76] = I(";:.<\u000e47#-\u001911,", "XRGOk");
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        public static EnumType forModelBlock(final IBlockState blockState) {
            final EnumType[] values;
            final int length = (values = values()).length;
            int i = "".length();
            "".length();
            if (0 < 0) {
                throw null;
            }
            while (i < length) {
                final EnumType enumType = values[i];
                if (blockState == enumType.getModelBlock()) {
                    return enumType;
                }
                ++i;
            }
            return EnumType.STONE;
        }
        
        private EnumType(final String s, final int n, final int meta, final String name, final String unlocalizedName) {
            this.meta = meta;
            this.name = name;
            this.unlocalizedName = unlocalizedName;
        }
        
        private EnumType(final String s, final int n, final int n2, final String s2) {
            this(s, n, n2, s2, s2);
        }
        
        EnumType(final String s, final int n, final int n2, final String s2, final EnumType enumType) {
            this(s, n, n2, s2);
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
        
        EnumType(final String s, final int n, final int n2, final String s2, final String s3, final EnumType enumType) {
            this(s, n, n2, s2, s3);
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
                if (2 == -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public String getName() {
            return this.name;
        }
    }
}
