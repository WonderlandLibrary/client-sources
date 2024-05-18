package net.minecraft.block;

import com.google.common.base.*;
import net.minecraft.item.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.creativetab.*;
import java.util.*;

public class BlockNewLog extends BlockLog
{
    public static final PropertyEnum<BlockPlanks.EnumType> VARIANT;
    private static int[] $SWITCH_TABLE$net$minecraft$block$BlockLog$EnumAxis;
    private static final String[] I;
    private static int[] $SWITCH_TABLE$net$minecraft$block$BlockPlanks$EnumType;
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("8'1\u000b, 2", "NFCbM");
    }
    
    static {
        I();
        VARIANT = PropertyEnum.create(BlockNewLog.I["".length()], BlockPlanks.EnumType.class, (com.google.common.base.Predicate<BlockPlanks.EnumType>)new Predicate<BlockPlanks.EnumType>() {
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
                    if (0 == 3) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public boolean apply(final BlockPlanks.EnumType enumType) {
                if (enumType.getMetadata() >= (0xC0 ^ 0xC4)) {
                    return " ".length() != 0;
                }
                return "".length() != 0;
            }
            
            public boolean apply(final Object o) {
                return this.apply((BlockPlanks.EnumType)o);
            }
        });
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$block$BlockLog$EnumAxis() {
        final int[] $switch_TABLE$net$minecraft$block$BlockLog$EnumAxis = BlockNewLog.$SWITCH_TABLE$net$minecraft$block$BlockLog$EnumAxis;
        if ($switch_TABLE$net$minecraft$block$BlockLog$EnumAxis != null) {
            return $switch_TABLE$net$minecraft$block$BlockLog$EnumAxis;
        }
        final int[] $switch_TABLE$net$minecraft$block$BlockLog$EnumAxis2 = new int[EnumAxis.values().length];
        try {
            $switch_TABLE$net$minecraft$block$BlockLog$EnumAxis2[EnumAxis.NONE.ordinal()] = (0xC6 ^ 0xC2);
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockLog$EnumAxis2[EnumAxis.X.ordinal()] = " ".length();
            "".length();
            if (4 <= 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockLog$EnumAxis2[EnumAxis.Y.ordinal()] = "  ".length();
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockLog$EnumAxis2[EnumAxis.Z.ordinal()] = "   ".length();
            "".length();
            if (-1 == 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        return BlockNewLog.$SWITCH_TABLE$net$minecraft$block$BlockLog$EnumAxis = $switch_TABLE$net$minecraft$block$BlockLog$EnumAxis2;
    }
    
    @Override
    protected ItemStack createStackedBlock(final IBlockState blockState) {
        return new ItemStack(Item.getItemFromBlock(this), " ".length(), blockState.getValue(BlockNewLog.VARIANT).getMetadata() - (0x17 ^ 0x13));
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final IBlockState withProperty = this.getDefaultState().withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.byMetadata((n & "   ".length()) + (0xBA ^ 0xBE)));
        IBlockState blockState = null;
        switch (n & (0x8C ^ 0x80)) {
            case 0: {
                blockState = withProperty.withProperty(BlockNewLog.LOG_AXIS, EnumAxis.Y);
                "".length();
                if (1 == 4) {
                    throw null;
                }
                break;
            }
            case 4: {
                blockState = withProperty.withProperty(BlockNewLog.LOG_AXIS, EnumAxis.X);
                "".length();
                if (3 < 2) {
                    throw null;
                }
                break;
            }
            case 8: {
                blockState = withProperty.withProperty(BlockNewLog.LOG_AXIS, EnumAxis.Z);
                "".length();
                if (1 >= 4) {
                    throw null;
                }
                break;
            }
            default: {
                blockState = withProperty.withProperty(BlockNewLog.LOG_AXIS, EnumAxis.NONE);
                break;
            }
        }
        return blockState;
    }
    
    public BlockNewLog() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.ACACIA).withProperty(BlockNewLog.LOG_AXIS, EnumAxis.Y));
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = "".length() | blockState.getValue(BlockNewLog.VARIANT).getMetadata() - (0x38 ^ 0x3C);
        switch ($SWITCH_TABLE$net$minecraft$block$BlockLog$EnumAxis()[blockState.getValue(BlockNewLog.LOG_AXIS).ordinal()]) {
            case 1: {
                n |= (0x78 ^ 0x7C);
                "".length();
                if (3 == -1) {
                    throw null;
                }
                break;
            }
            case 3: {
                n |= (0xA1 ^ 0xA9);
                "".length();
                if (2 < 0) {
                    throw null;
                }
                break;
            }
            case 4: {
                n |= (0x40 ^ 0x4C);
                break;
            }
        }
        return n;
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        final BlockPlanks.EnumType enumType = blockState.getValue(BlockNewLog.VARIANT);
        switch ($SWITCH_TABLE$net$minecraft$block$BlockLog$EnumAxis()[blockState.getValue(BlockNewLog.LOG_AXIS).ordinal()]) {
            default: {
                switch ($SWITCH_TABLE$net$minecraft$block$BlockPlanks$EnumType()[enumType.ordinal()]) {
                    default: {
                        return MapColor.stoneColor;
                    }
                    case 6: {
                        return BlockPlanks.EnumType.DARK_OAK.func_181070_c();
                    }
                }
                break;
            }
            case 2: {
                return enumType.func_181070_c();
            }
        }
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$block$BlockPlanks$EnumType() {
        final int[] $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType = BlockNewLog.$SWITCH_TABLE$net$minecraft$block$BlockPlanks$EnumType;
        if ($switch_TABLE$net$minecraft$block$BlockPlanks$EnumType != null) {
            return $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType;
        }
        final int[] $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType2 = new int[BlockPlanks.EnumType.values().length];
        try {
            $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType2[BlockPlanks.EnumType.ACACIA.ordinal()] = (0x6A ^ 0x6F);
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType2[BlockPlanks.EnumType.BIRCH.ordinal()] = "   ".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType2[BlockPlanks.EnumType.DARK_OAK.ordinal()] = (0x11 ^ 0x17);
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType2[BlockPlanks.EnumType.JUNGLE.ordinal()] = (0xB7 ^ 0xB3);
            "".length();
            if (2 == 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType2[BlockPlanks.EnumType.OAK.ordinal()] = " ".length();
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType2[BlockPlanks.EnumType.SPRUCE.ordinal()] = "  ".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        return BlockNewLog.$SWITCH_TABLE$net$minecraft$block$BlockPlanks$EnumType = $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType2;
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty["  ".length()];
        array["".length()] = BlockNewLog.VARIANT;
        array[" ".length()] = BlockNewLog.LOG_AXIS;
        return new BlockState(this, array);
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return blockState.getValue(BlockNewLog.VARIANT).getMetadata() - (0x35 ^ 0x31);
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
            if (2 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List<ItemStack> list) {
        list.add(new ItemStack(item, " ".length(), BlockPlanks.EnumType.ACACIA.getMetadata() - (0x2B ^ 0x2F)));
        list.add(new ItemStack(item, " ".length(), BlockPlanks.EnumType.DARK_OAK.getMetadata() - (0x41 ^ 0x45)));
    }
}
