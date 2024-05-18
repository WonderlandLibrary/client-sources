package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.block.properties.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public abstract class BlockLog extends BlockRotatedPillar
{
    private static final String[] I;
    public static final PropertyEnum<EnumAxis> LOG_AXIS;
    
    static {
        I();
        LOG_AXIS = PropertyEnum.create(BlockLog.I["".length()], EnumAxis.class);
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
    
    public BlockLog() {
        super(Material.wood);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setHardness(2.0f);
        this.setStepSound(BlockLog.soundTypeWood);
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final int n = 0x31 ^ 0x35;
        final int n2 = n + " ".length();
        if (world.isAreaLoaded(blockPos.add(-n2, -n2, -n2), blockPos.add(n2, n2, n2))) {
            final Iterator<BlockPos> iterator = BlockPos.getAllInBox(blockPos.add(-n, -n, -n), blockPos.add(n, n, n)).iterator();
            "".length();
            if (1 >= 3) {
                throw null;
            }
            while (iterator.hasNext()) {
                final BlockPos blockPos2 = iterator.next();
                final IBlockState blockState2 = world.getBlockState(blockPos2);
                if (blockState2.getBlock().getMaterial() == Material.leaves && !blockState2.getValue((IProperty<Boolean>)BlockLeaves.CHECK_DECAY)) {
                    world.setBlockState(blockPos2, blockState2.withProperty((IProperty<Comparable>)BlockLeaves.CHECK_DECAY, (boolean)(" ".length() != 0)), 0x5C ^ 0x58);
                }
            }
        }
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0004\f\u00116", "etxEf");
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return super.onBlockPlaced(world, blockPos, enumFacing, n, n2, n3, n4, entityLivingBase).withProperty(BlockLog.LOG_AXIS, EnumAxis.fromFacingAxis(enumFacing.getAxis()));
    }
    
    public enum EnumAxis implements IStringSerializable
    {
        private static final String[] I;
        private final String name;
        private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing$Axis;
        
        NONE(EnumAxis.I[0x40 ^ 0x46], "   ".length(), EnumAxis.I[0x26 ^ 0x21]), 
        Z(EnumAxis.I[0x3A ^ 0x3E], "  ".length(), EnumAxis.I[0x69 ^ 0x6C]), 
        Y(EnumAxis.I["  ".length()], " ".length(), EnumAxis.I["   ".length()]);
        
        private static final EnumAxis[] ENUM$VALUES;
        
        X(EnumAxis.I["".length()], "".length(), EnumAxis.I[" ".length()]);
        
        public static EnumAxis fromFacingAxis(final EnumFacing.Axis axis) {
            switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing$Axis()[axis.ordinal()]) {
                case 1: {
                    return EnumAxis.X;
                }
                case 2: {
                    return EnumAxis.Y;
                }
                case 3: {
                    return EnumAxis.Z;
                }
                default: {
                    return EnumAxis.NONE;
                }
            }
        }
        
        private static void I() {
            (I = new String[0x76 ^ 0x7E])["".length()] = I(":", "bqitK");
            EnumAxis.I[" ".length()] = I("-", "UHssL");
            EnumAxis.I["  ".length()] = I("\u0015", "LmXBU");
            EnumAxis.I["   ".length()] = I("\u001a", "cWyWp");
            EnumAxis.I[0x72 ^ 0x76] = I("3", "iNPAA");
            EnumAxis.I[0x44 ^ 0x41] = I("+", "QXTyD");
            EnumAxis.I[0xC2 ^ 0xC4] = I("\u00038\n\u0001", "MwDDF");
            EnumAxis.I[0x8F ^ 0x88] = I("#);\u0000", "MFUes");
        }
        
        private EnumAxis(final String s, final int n, final String name) {
            this.name = name;
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
                if (4 == -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        static {
            I();
            final EnumAxis[] enum$VALUES = new EnumAxis[0x8C ^ 0x88];
            enum$VALUES["".length()] = EnumAxis.X;
            enum$VALUES[" ".length()] = EnumAxis.Y;
            enum$VALUES["  ".length()] = EnumAxis.Z;
            enum$VALUES["   ".length()] = EnumAxis.NONE;
            ENUM$VALUES = enum$VALUES;
        }
        
        static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing$Axis() {
            final int[] $switch_TABLE$net$minecraft$util$EnumFacing$Axis = EnumAxis.$SWITCH_TABLE$net$minecraft$util$EnumFacing$Axis;
            if ($switch_TABLE$net$minecraft$util$EnumFacing$Axis != null) {
                return $switch_TABLE$net$minecraft$util$EnumFacing$Axis;
            }
            final int[] $switch_TABLE$net$minecraft$util$EnumFacing$Axis2 = new int[EnumFacing.Axis.values().length];
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing$Axis2[EnumFacing.Axis.X.ordinal()] = " ".length();
                "".length();
                if (2 < 2) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing$Axis2[EnumFacing.Axis.Y.ordinal()] = "  ".length();
                "".length();
                if (4 <= 0) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                $switch_TABLE$net$minecraft$util$EnumFacing$Axis2[EnumFacing.Axis.Z.ordinal()] = "   ".length();
                "".length();
                if (1 <= 0) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            return EnumAxis.$SWITCH_TABLE$net$minecraft$util$EnumFacing$Axis = $switch_TABLE$net$minecraft$util$EnumFacing$Axis2;
        }
        
        @Override
        public String getName() {
            return this.name;
        }
    }
}
