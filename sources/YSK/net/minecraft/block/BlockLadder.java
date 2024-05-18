package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import com.google.common.base.*;

public class BlockLadder extends Block
{
    public static final PropertyDirection FACING;
    private static final String[] I;
    private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        int n;
        if (world.getBlockState(blockPos.west()).getBlock().isNormalCube()) {
            n = " ".length();
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else if (world.getBlockState(blockPos.east()).getBlock().isNormalCube()) {
            n = " ".length();
            "".length();
            if (4 < 3) {
                throw null;
            }
        }
        else if (world.getBlockState(blockPos.north()).getBlock().isNormalCube()) {
            n = " ".length();
            "".length();
            if (4 == 0) {
                throw null;
            }
        }
        else {
            n = (world.getBlockState(blockPos.south()).getBlock().isNormalCube() ? 1 : 0);
        }
        return n != 0;
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockLadder.FACING;
        return new BlockState(this, array);
    }
    
    protected boolean canBlockStay(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        return world.getBlockState(blockPos.offset(enumFacing.getOpposite())).getBlock().isNormalCube();
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        if (blockState.getBlock() == this) {
            final float n = 0.125f;
            switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[blockState.getValue((IProperty<EnumFacing>)BlockLadder.FACING).ordinal()]) {
                case 3: {
                    this.setBlockBounds(0.0f, 0.0f, 1.0f - n, 1.0f, 1.0f, 1.0f);
                    "".length();
                    if (-1 >= 1) {
                        throw null;
                    }
                    break;
                }
                case 4: {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, n);
                    "".length();
                    if (2 < 1) {
                        throw null;
                    }
                    break;
                }
                case 5: {
                    this.setBlockBounds(1.0f - n, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                    "".length();
                    if (2 <= -1) {
                        throw null;
                    }
                    break;
                }
                default: {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, n, 1.0f, 1.0f);
                    break;
                }
            }
        }
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue((IProperty<EnumFacing>)BlockLadder.FACING).getIndex();
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getCollisionBoundingBox(world, blockPos, blockState);
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        if (enumFacing.getAxis().isHorizontal() && this.canBlockStay(world, blockPos, enumFacing)) {
            return this.getDefaultState().withProperty((IProperty<Comparable>)BlockLadder.FACING, enumFacing);
        }
        final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
        "".length();
        if (2 <= 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final EnumFacing enumFacing2 = iterator.next();
            if (this.canBlockStay(world, blockPos, enumFacing2)) {
                return this.getDefaultState().withProperty((IProperty<Comparable>)BlockLadder.FACING, enumFacing2);
            }
        }
        return this.getDefaultState();
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (!this.canBlockStay(world, blockToAir, blockState.getValue((IProperty<EnumFacing>)BlockLadder.FACING))) {
            this.dropBlockAsItem(world, blockToAir, blockState, "".length());
            world.setBlockToAir(blockToAir);
        }
        super.onNeighborBlockChange(world, blockToAir, blockState, block);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\n\u0015$\u0018\t\u000b", "ltGqg");
    }
    
    protected BlockLadder() {
        super(Material.circuits);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockLadder.FACING, EnumFacing.NORTH));
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBox(final World world, final BlockPos blockPos) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getSelectedBoundingBox(world, blockPos);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        EnumFacing enumFacing = EnumFacing.getFront(n);
        if (enumFacing.getAxis() == EnumFacing.Axis.Y) {
            enumFacing = EnumFacing.NORTH;
        }
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockLadder.FACING, enumFacing);
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
            if (3 < 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
        FACING = PropertyDirection.create(BlockLadder.I["".length()], (Predicate<EnumFacing>)EnumFacing.Plane.HORIZONTAL);
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing() {
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing = BlockLadder.$SWITCH_TABLE$net$minecraft$util$EnumFacing;
        if ($switch_TABLE$net$minecraft$util$EnumFacing != null) {
            return $switch_TABLE$net$minecraft$util$EnumFacing;
        }
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing2 = new int[EnumFacing.values().length];
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.DOWN.ordinal()] = " ".length();
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.EAST.ordinal()] = (0x86 ^ 0x80);
            "".length();
            if (4 <= 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.NORTH.ordinal()] = "   ".length();
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.SOUTH.ordinal()] = (0x5D ^ 0x59);
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.UP.ordinal()] = "  ".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.WEST.ordinal()] = (0x32 ^ 0x37);
            "".length();
            if (-1 == 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        return BlockLadder.$SWITCH_TABLE$net$minecraft$util$EnumFacing = $switch_TABLE$net$minecraft$util$EnumFacing2;
    }
}
