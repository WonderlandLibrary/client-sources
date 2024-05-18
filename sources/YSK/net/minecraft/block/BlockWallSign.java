package net.minecraft.block;

import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import com.google.common.base.*;
import net.minecraft.block.state.*;

public class BlockWallSign extends BlockSign
{
    private static final String[] I;
    private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;
    public static final PropertyDirection FACING;
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (!world.getBlockState(blockToAir.offset(blockState.getValue((IProperty<EnumFacing>)BlockWallSign.FACING).getOpposite())).getBlock().getMaterial().isSolid()) {
            this.dropBlockAsItem(world, blockToAir, blockState, "".length());
            world.setBlockToAir(blockToAir);
        }
        super.onNeighborBlockChange(world, blockToAir, blockState, block);
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
    
    public BlockWallSign() {
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockWallSign.FACING, EnumFacing.NORTH));
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        EnumFacing enumFacing = EnumFacing.getFront(n);
        if (enumFacing.getAxis() == EnumFacing.Axis.Y) {
            enumFacing = EnumFacing.NORTH;
        }
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockWallSign.FACING, enumFacing);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final EnumFacing enumFacing = blockAccess.getBlockState(blockPos).getValue((IProperty<EnumFacing>)BlockWallSign.FACING);
        final float n = 0.28125f;
        final float n2 = 0.78125f;
        final float n3 = 0.0f;
        final float n4 = 1.0f;
        final float n5 = 0.125f;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[enumFacing.ordinal()]) {
            case 3: {
                this.setBlockBounds(n3, n, 1.0f - n5, n4, n2, 1.0f);
                "".length();
                if (2 == 3) {
                    throw null;
                }
                break;
            }
            case 4: {
                this.setBlockBounds(n3, n, 0.0f, n4, n2, n5);
                "".length();
                if (4 < 1) {
                    throw null;
                }
                break;
            }
            case 5: {
                this.setBlockBounds(1.0f - n5, n, n3, 1.0f, n2, n4);
                "".length();
                if (2 < -1) {
                    throw null;
                }
                break;
            }
            case 6: {
                this.setBlockBounds(0.0f, n, n3, n5, n2, n4);
                break;
            }
        }
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue((IProperty<EnumFacing>)BlockWallSign.FACING).getIndex();
    }
    
    static {
        I();
        FACING = PropertyDirection.create(BlockWallSign.I["".length()], (Predicate<EnumFacing>)EnumFacing.Plane.HORIZONTAL);
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing() {
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing = BlockWallSign.$SWITCH_TABLE$net$minecraft$util$EnumFacing;
        if ($switch_TABLE$net$minecraft$util$EnumFacing != null) {
            return $switch_TABLE$net$minecraft$util$EnumFacing;
        }
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing2 = new int[EnumFacing.values().length];
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.DOWN.ordinal()] = " ".length();
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.EAST.ordinal()] = (0xA5 ^ 0xA3);
            "".length();
            if (3 == 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.NORTH.ordinal()] = "   ".length();
            "".length();
            if (3 < -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.SOUTH.ordinal()] = (0x7A ^ 0x7E);
            "".length();
            if (false) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.UP.ordinal()] = "  ".length();
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.WEST.ordinal()] = (0xA2 ^ 0xA7);
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        return BlockWallSign.$SWITCH_TABLE$net$minecraft$util$EnumFacing = $switch_TABLE$net$minecraft$util$EnumFacing2;
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockWallSign.FACING;
        return new BlockState(this, array);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("*\u000f\"?\u0007+", "LnAVi");
    }
}
