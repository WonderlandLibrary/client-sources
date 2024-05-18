package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import com.google.common.base.*;
import net.minecraft.block.material.*;
import net.minecraft.item.*;
import net.minecraft.creativetab.*;
import java.util.*;

public class BlockOldLog extends BlockLog
{
    private static final String[] I;
    private static int[] $SWITCH_TABLE$net$minecraft$block$BlockPlanks$EnumType;
    public static final PropertyEnum<BlockPlanks.EnumType> VARIANT;
    private static int[] $SWITCH_TABLE$net$minecraft$block$BlockLog$EnumAxis;
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty["  ".length()];
        array["".length()] = BlockOldLog.VARIANT;
        array[" ".length()] = BlockOldLog.LOG_AXIS;
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
            if (1 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = "".length() | blockState.getValue(BlockOldLog.VARIANT).getMetadata();
        switch ($SWITCH_TABLE$net$minecraft$block$BlockLog$EnumAxis()[blockState.getValue(BlockOldLog.LOG_AXIS).ordinal()]) {
            case 1: {
                n |= (0x62 ^ 0x66);
                "".length();
                if (2 != 2) {
                    throw null;
                }
                break;
            }
            case 3: {
                n |= (0x85 ^ 0x8D);
                "".length();
                if (3 != 3) {
                    throw null;
                }
                break;
            }
            case 4: {
                n |= (0x26 ^ 0x2A);
                break;
            }
        }
        return n;
    }
    
    static {
        I();
        VARIANT = PropertyEnum.create(BlockOldLog.I["".length()], BlockPlanks.EnumType.class, (com.google.common.base.Predicate<BlockPlanks.EnumType>)new Predicate<BlockPlanks.EnumType>() {
            public boolean apply(final BlockPlanks.EnumType enumType) {
                if (enumType.getMetadata() < (0x8D ^ 0x89)) {
                    return " ".length() != 0;
                }
                return "".length() != 0;
            }
            
            public boolean apply(final Object o) {
                return this.apply((BlockPlanks.EnumType)o);
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
                    if (4 == 0) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        });
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$block$BlockPlanks$EnumType() {
        final int[] $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType = BlockOldLog.$SWITCH_TABLE$net$minecraft$block$BlockPlanks$EnumType;
        if ($switch_TABLE$net$minecraft$block$BlockPlanks$EnumType != null) {
            return $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType;
        }
        final int[] $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType2 = new int[BlockPlanks.EnumType.values().length];
        try {
            $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType2[BlockPlanks.EnumType.ACACIA.ordinal()] = (0x7C ^ 0x79);
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType2[BlockPlanks.EnumType.BIRCH.ordinal()] = "   ".length();
            "".length();
            if (3 < -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType2[BlockPlanks.EnumType.DARK_OAK.ordinal()] = (0x8 ^ 0xE);
            "".length();
            if (3 < 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType2[BlockPlanks.EnumType.JUNGLE.ordinal()] = (0x87 ^ 0x83);
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType2[BlockPlanks.EnumType.OAK.ordinal()] = " ".length();
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType2[BlockPlanks.EnumType.SPRUCE.ordinal()] = "  ".length();
            "".length();
            if (2 < 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        return BlockOldLog.$SWITCH_TABLE$net$minecraft$block$BlockPlanks$EnumType = $switch_TABLE$net$minecraft$block$BlockPlanks$EnumType2;
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        final BlockPlanks.EnumType enumType = blockState.getValue(BlockOldLog.VARIANT);
        switch ($SWITCH_TABLE$net$minecraft$block$BlockLog$EnumAxis()[blockState.getValue(BlockOldLog.LOG_AXIS).ordinal()]) {
            default: {
                switch ($SWITCH_TABLE$net$minecraft$block$BlockPlanks$EnumType()[enumType.ordinal()]) {
                    default: {
                        return BlockPlanks.EnumType.SPRUCE.func_181070_c();
                    }
                    case 2: {
                        return BlockPlanks.EnumType.DARK_OAK.func_181070_c();
                    }
                    case 3: {
                        return MapColor.quartzColor;
                    }
                    case 4: {
                        return BlockPlanks.EnumType.SPRUCE.func_181070_c();
                    }
                }
                break;
            }
            case 2: {
                return enumType.func_181070_c();
            }
        }
    }
    
    @Override
    protected ItemStack createStackedBlock(final IBlockState blockState) {
        return new ItemStack(Item.getItemFromBlock(this), " ".length(), blockState.getValue(BlockOldLog.VARIANT).getMetadata());
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0018\u0012\u001a!%\u0000\u0007", "nshHD");
    }
    
    public BlockOldLog() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.OAK).withProperty(BlockOldLog.LOG_AXIS, EnumAxis.Y));
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final IBlockState withProperty = this.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.byMetadata((n & "   ".length()) % (0x89 ^ 0x8D)));
        IBlockState blockState = null;
        switch (n & (0x71 ^ 0x7D)) {
            case 0: {
                blockState = withProperty.withProperty(BlockOldLog.LOG_AXIS, EnumAxis.Y);
                "".length();
                if (0 >= 1) {
                    throw null;
                }
                break;
            }
            case 4: {
                blockState = withProperty.withProperty(BlockOldLog.LOG_AXIS, EnumAxis.X);
                "".length();
                if (3 <= 0) {
                    throw null;
                }
                break;
            }
            case 8: {
                blockState = withProperty.withProperty(BlockOldLog.LOG_AXIS, EnumAxis.Z);
                "".length();
                if (1 >= 3) {
                    throw null;
                }
                break;
            }
            default: {
                blockState = withProperty.withProperty(BlockOldLog.LOG_AXIS, EnumAxis.NONE);
                break;
            }
        }
        return blockState;
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return blockState.getValue(BlockOldLog.VARIANT).getMetadata();
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List<ItemStack> list) {
        list.add(new ItemStack(item, " ".length(), BlockPlanks.EnumType.OAK.getMetadata()));
        list.add(new ItemStack(item, " ".length(), BlockPlanks.EnumType.SPRUCE.getMetadata()));
        list.add(new ItemStack(item, " ".length(), BlockPlanks.EnumType.BIRCH.getMetadata()));
        list.add(new ItemStack(item, " ".length(), BlockPlanks.EnumType.JUNGLE.getMetadata()));
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$block$BlockLog$EnumAxis() {
        final int[] $switch_TABLE$net$minecraft$block$BlockLog$EnumAxis = BlockOldLog.$SWITCH_TABLE$net$minecraft$block$BlockLog$EnumAxis;
        if ($switch_TABLE$net$minecraft$block$BlockLog$EnumAxis != null) {
            return $switch_TABLE$net$minecraft$block$BlockLog$EnumAxis;
        }
        final int[] $switch_TABLE$net$minecraft$block$BlockLog$EnumAxis2 = new int[EnumAxis.values().length];
        try {
            $switch_TABLE$net$minecraft$block$BlockLog$EnumAxis2[EnumAxis.NONE.ordinal()] = (0x1D ^ 0x19);
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockLog$EnumAxis2[EnumAxis.X.ordinal()] = " ".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockLog$EnumAxis2[EnumAxis.Y.ordinal()] = "  ".length();
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockLog$EnumAxis2[EnumAxis.Z.ordinal()] = "   ".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        return BlockOldLog.$SWITCH_TABLE$net$minecraft$block$BlockLog$EnumAxis = $switch_TABLE$net$minecraft$block$BlockLog$EnumAxis2;
    }
}
