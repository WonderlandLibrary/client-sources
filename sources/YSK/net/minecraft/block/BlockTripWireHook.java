package net.minecraft.block;

import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import com.google.common.base.*;

public class BlockTripWireHook extends Block
{
    public static final PropertyBool SUSPENDED;
    private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;
    public static final PropertyDirection FACING;
    public static final PropertyBool POWERED;
    private static final String[] I;
    public static final PropertyBool ATTACHED;
    
    private static void I() {
        (I = new String[0x49 ^ 0x41])["".length()] = I("\u001235<%\u0013", "tRVUK");
        BlockTripWireHook.I[" ".length()] = I("(\u001e<\u0004\u001e=\u0015", "XqKal");
        BlockTripWireHook.I["  ".length()] = I("\u0010\u0003\u0000\u0019-\u0019\u0012\u0010", "qwtxN");
        BlockTripWireHook.I["   ".length()] = I("9\u001b\n\u0003\u0011$\n\u001c\u0017", "Jnyst");
        BlockTripWireHook.I[0x95 ^ 0x91] = I("+\u0002*+ 4M'#&:\b", "YcDOO");
        BlockTripWireHook.I[0x56 ^ 0x53] = I("\u0002\u0004)'-\u001dK$/+\u0013\u000e", "peGCB");
        BlockTripWireHook.I[0xBB ^ 0xBD] = I("\u001e\u000374\b\u0001L:<\u000e\u000f\t", "lbYPg");
        BlockTripWireHook.I[0x4B ^ 0x4C] = I("\u0002\u000f\u0007\u0010\u001e\u001d@\u000b\u001b\u0006\u0018\u0007\u001d", "pnitq");
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        this.func_176260_a(world, blockPos, blockState, "".length() != 0, "".length() != 0, -" ".length(), null);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    @Override
    public int getStrongPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        int n;
        if (!blockState.getValue((IProperty<Boolean>)BlockTripWireHook.POWERED)) {
            n = "".length();
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        else if (blockState.getValue((IProperty<Comparable>)BlockTripWireHook.FACING) == enumFacing) {
            n = (0x4F ^ 0x40);
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
        "".length();
        if (4 <= -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            if (world.getBlockState(blockPos.offset(iterator.next())).getBlock().isNormalCube()) {
                return " ".length() != 0;
            }
        }
        return "".length() != 0;
    }
    
    public BlockTripWireHook() {
        super(Material.circuits);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockTripWireHook.FACING, EnumFacing.NORTH).withProperty((IProperty<Comparable>)BlockTripWireHook.POWERED, "".length() != 0).withProperty((IProperty<Comparable>)BlockTripWireHook.ATTACHED, "".length() != 0).withProperty((IProperty<Comparable>)BlockTripWireHook.SUSPENDED, (boolean)("".length() != 0)));
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.setTickRandomly(" ".length() != 0);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = "".length() | blockState.getValue((IProperty<EnumFacing>)BlockTripWireHook.FACING).getHorizontalIndex();
        if (blockState.getValue((IProperty<Boolean>)BlockTripWireHook.POWERED)) {
            n |= (0x92 ^ 0x9A);
        }
        if (blockState.getValue((IProperty<Boolean>)BlockTripWireHook.ATTACHED)) {
            n |= (0x24 ^ 0x20);
        }
        return n;
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final boolean booleanValue = blockState.getValue((IProperty<Boolean>)BlockTripWireHook.ATTACHED);
        final boolean booleanValue2 = blockState.getValue((IProperty<Boolean>)BlockTripWireHook.POWERED);
        if (booleanValue || booleanValue2) {
            this.func_176260_a(world, blockPos, blockState, " ".length() != 0, "".length() != 0, -" ".length(), null);
        }
        if (booleanValue2) {
            world.notifyNeighborsOfStateChange(blockPos, this);
            world.notifyNeighborsOfStateChange(blockPos.offset(blockState.getValue((IProperty<EnumFacing>)BlockTripWireHook.FACING).getOpposite()), this);
        }
        super.breakBlock(world, blockPos, blockState);
    }
    
    private boolean checkForDrop(final World world, final BlockPos blockToAir, final IBlockState blockState) {
        if (!this.canPlaceBlockAt(world, blockToAir)) {
            this.dropBlockAsItem(world, blockToAir, blockState, "".length());
            world.setBlockToAir(blockToAir);
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    @Override
    public int getWeakPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        int length;
        if (blockState.getValue((IProperty<Boolean>)BlockTripWireHook.POWERED)) {
            length = (0x71 ^ 0x7E);
            "".length();
            if (4 <= 2) {
                throw null;
            }
        }
        else {
            length = "".length();
        }
        return length;
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        this.func_176260_a(world, blockPos, blockState, "".length() != 0, " ".length() != 0, -" ".length(), null);
    }
    
    private void func_176262_b(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        world.notifyNeighborsOfStateChange(blockPos, this);
        world.notifyNeighborsOfStateChange(blockPos.offset(enumFacing.getOpposite()), this);
    }
    
    private void func_180694_a(final World world, final BlockPos blockPos, final boolean b, final boolean b2, final boolean b3, final boolean b4) {
        if (b2 && !b4) {
            world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.1, blockPos.getZ() + 0.5, BlockTripWireHook.I[0xAE ^ 0xAA], 0.4f, 0.6f);
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        else if (!b2 && b4) {
            world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.1, blockPos.getZ() + 0.5, BlockTripWireHook.I[0xA4 ^ 0xA1], 0.4f, 0.5f);
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else if (b && !b3) {
            world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.1, blockPos.getZ() + 0.5, BlockTripWireHook.I[0x67 ^ 0x61], 0.4f, 0.7f);
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else if (!b && b3) {
            world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.1, blockPos.getZ() + 0.5, BlockTripWireHook.I[0x96 ^ 0x91], 0.4f, 1.2f / (world.rand.nextFloat() * 0.2f + 0.9f));
        }
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final float n = 0.1875f;
        switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[blockAccess.getBlockState(blockPos).getValue((IProperty<EnumFacing>)BlockTripWireHook.FACING).ordinal()]) {
            case 6: {
                this.setBlockBounds(0.0f, 0.2f, 0.5f - n, n * 2.0f, 0.8f, 0.5f + n);
                "".length();
                if (4 <= 2) {
                    throw null;
                }
                break;
            }
            case 5: {
                this.setBlockBounds(1.0f - n * 2.0f, 0.2f, 0.5f - n, 1.0f, 0.8f, 0.5f + n);
                "".length();
                if (0 == 4) {
                    throw null;
                }
                break;
            }
            case 4: {
                this.setBlockBounds(0.5f - n, 0.2f, 0.0f, 0.5f + n, 0.8f, n * 2.0f);
                "".length();
                if (1 >= 4) {
                    throw null;
                }
                break;
            }
            case 3: {
                this.setBlockBounds(0.5f - n, 0.2f, 1.0f - n * 2.0f, 0.5f + n, 0.8f, 1.0f);
                break;
            }
        }
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
            if (3 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        IBlockState blockState = this.getDefaultState().withProperty((IProperty<Comparable>)BlockTripWireHook.POWERED, "".length() != 0).withProperty((IProperty<Comparable>)BlockTripWireHook.ATTACHED, "".length() != 0).withProperty((IProperty<Comparable>)BlockTripWireHook.SUSPENDED, "".length() != 0);
        if (enumFacing.getAxis().isHorizontal()) {
            blockState = blockState.withProperty((IProperty<Comparable>)BlockTripWireHook.FACING, enumFacing);
        }
        return blockState;
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT_MIPPED;
    }
    
    @Override
    public boolean canProvidePower() {
        return " ".length() != 0;
    }
    
    public void func_176260_a(final World world, final BlockPos blockPos, final IBlockState blockState, final boolean b, final boolean b2, final int n, final IBlockState blockState2) {
        final EnumFacing enumFacing = blockState.getValue((IProperty<EnumFacing>)BlockTripWireHook.FACING);
        final boolean booleanValue = blockState.getValue((IProperty<Boolean>)BlockTripWireHook.ATTACHED);
        final boolean booleanValue2 = blockState.getValue((IProperty<Boolean>)BlockTripWireHook.POWERED);
        int n2;
        if (World.doesBlockHaveSolidTopSurface(world, blockPos.down())) {
            n2 = "".length();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            n2 = " ".length();
        }
        final int n3 = n2;
        int n4;
        if (b) {
            n4 = "".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n4 = " ".length();
        }
        int length = n4;
        int length2 = "".length();
        int length3 = "".length();
        final IBlockState[] array = new IBlockState[0xB5 ^ 0x9F];
        int i = " ".length();
        "".length();
        if (3 < 1) {
            throw null;
        }
        while (i < (0x66 ^ 0x4C)) {
            IBlockState blockState3 = world.getBlockState(blockPos.offset(enumFacing, i));
            if (blockState3.getBlock() == Blocks.tripwire_hook) {
                if (blockState3.getValue((IProperty<Comparable>)BlockTripWireHook.FACING) != enumFacing.getOpposite()) {
                    break;
                }
                length3 = i;
                "".length();
                if (1 >= 4) {
                    throw null;
                }
                break;
            }
            else {
                if (blockState3.getBlock() != Blocks.tripwire && i != n) {
                    array[i] = null;
                    length = "".length();
                    "".length();
                    if (-1 >= 0) {
                        throw null;
                    }
                }
                else {
                    if (i == n) {
                        blockState3 = (IBlockState)Objects.firstNonNull((Object)blockState2, (Object)blockState3);
                    }
                    int n5;
                    if (blockState3.getValue((IProperty<Boolean>)BlockTripWire.DISARMED)) {
                        n5 = "".length();
                        "".length();
                        if (-1 != -1) {
                            throw null;
                        }
                    }
                    else {
                        n5 = " ".length();
                    }
                    final int n6 = n5;
                    final boolean booleanValue3 = blockState3.getValue((IProperty<Boolean>)BlockTripWire.POWERED);
                    final boolean booleanValue4 = blockState3.getValue((IProperty<Boolean>)BlockTripWire.SUSPENDED);
                    final int n7 = length;
                    int n8;
                    if ((booleanValue4 ? 1 : 0) == n3) {
                        n8 = " ".length();
                        "".length();
                        if (-1 == 2) {
                            throw null;
                        }
                    }
                    else {
                        n8 = "".length();
                    }
                    length = (n7 & n8);
                    final int n9 = length2;
                    int n10;
                    if (n6 != 0 && booleanValue3) {
                        n10 = " ".length();
                        "".length();
                        if (-1 == 1) {
                            throw null;
                        }
                    }
                    else {
                        n10 = "".length();
                    }
                    length2 = (n9 | n10);
                    array[i] = blockState3;
                    if (i == n) {
                        world.scheduleUpdate(blockPos, this, this.tickRate(world));
                        length &= n6;
                    }
                }
                ++i;
            }
        }
        final int n11 = length;
        int n12;
        if (length3 > " ".length()) {
            n12 = " ".length();
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else {
            n12 = "".length();
        }
        final int n13 = n11 & n12;
        final boolean b3 = (length2 & n13) != 0x0;
        final IBlockState withProperty = this.getDefaultState().withProperty((IProperty<Comparable>)BlockTripWireHook.ATTACHED, n13 != 0).withProperty((IProperty<Comparable>)BlockTripWireHook.POWERED, b3);
        if (length3 > 0) {
            final BlockPos offset = blockPos.offset(enumFacing, length3);
            final EnumFacing opposite = enumFacing.getOpposite();
            world.setBlockState(offset, withProperty.withProperty((IProperty<Comparable>)BlockTripWireHook.FACING, opposite), "   ".length());
            this.func_176262_b(world, offset, opposite);
            this.func_180694_a(world, offset, n13 != 0, b3, booleanValue, booleanValue2);
        }
        this.func_180694_a(world, blockPos, n13 != 0, b3, booleanValue, booleanValue2);
        if (!b) {
            world.setBlockState(blockPos, withProperty.withProperty((IProperty<Comparable>)BlockTripWireHook.FACING, enumFacing), "   ".length());
            if (b2) {
                this.func_176262_b(world, blockPos, enumFacing);
            }
        }
        if ((booleanValue ? 1 : 0) != n13) {
            int j = " ".length();
            "".length();
            if (2 == 3) {
                throw null;
            }
            while (j < length3) {
                final BlockPos offset2 = blockPos.offset(enumFacing, j);
                final IBlockState blockState4 = array[j];
                if (blockState4 != null && world.getBlockState(offset2).getBlock() != Blocks.air) {
                    world.setBlockState(offset2, blockState4.withProperty((IProperty<Comparable>)BlockTripWireHook.ATTACHED, (boolean)(n13 != 0)), "   ".length());
                }
                ++j;
            }
        }
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing() {
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing = BlockTripWireHook.$SWITCH_TABLE$net$minecraft$util$EnumFacing;
        if ($switch_TABLE$net$minecraft$util$EnumFacing != null) {
            return $switch_TABLE$net$minecraft$util$EnumFacing;
        }
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing2 = new int[EnumFacing.values().length];
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.DOWN.ordinal()] = " ".length();
            "".length();
            if (1 == 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.EAST.ordinal()] = (0xB0 ^ 0xB6);
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.NORTH.ordinal()] = "   ".length();
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.SOUTH.ordinal()] = (0x76 ^ 0x72);
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.UP.ordinal()] = "  ".length();
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.WEST.ordinal()] = (0x5 ^ 0x0);
            "".length();
            if (3 < 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        return BlockTripWireHook.$SWITCH_TABLE$net$minecraft$util$EnumFacing = $switch_TABLE$net$minecraft$util$EnumFacing2;
    }
    
    @Override
    public boolean canPlaceBlockOnSide(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        if (enumFacing.getAxis().isHorizontal() && world.getBlockState(blockPos.offset(enumFacing.getOpposite())).getBlock().isNormalCube()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final IBlockState withProperty = this.getDefaultState().withProperty((IProperty<Comparable>)BlockTripWireHook.FACING, EnumFacing.getHorizontal(n & "   ".length()));
        final PropertyBool powered = BlockTripWireHook.POWERED;
        int n2;
        if ((n & (0xAC ^ 0xA4)) > 0) {
            n2 = " ".length();
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        final IBlockState withProperty2 = withProperty.withProperty((IProperty<Comparable>)powered, n2 != 0);
        final PropertyBool attached = BlockTripWireHook.ATTACHED;
        int n3;
        if ((n & (0xA3 ^ 0xA7)) > 0) {
            n3 = " ".length();
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        else {
            n3 = "".length();
        }
        return withProperty2.withProperty((IProperty<Comparable>)attached, n3 != 0);
    }
    
    @Override
    public IBlockState getActualState(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        final PropertyBool suspended = BlockTripWireHook.SUSPENDED;
        int n;
        if (World.doesBlockHaveSolidTopSurface(blockAccess, blockPos.down())) {
            n = "".length();
            "".length();
            if (1 < -1) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return blockState.withProperty((IProperty<Comparable>)suspended, n != 0);
    }
    
    @Override
    public void randomTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[0x41 ^ 0x45];
        array["".length()] = BlockTripWireHook.FACING;
        array[" ".length()] = BlockTripWireHook.POWERED;
        array["  ".length()] = BlockTripWireHook.ATTACHED;
        array["   ".length()] = BlockTripWireHook.SUSPENDED;
        return new BlockState(this, array);
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (block != this && this.checkForDrop(world, blockToAir, blockState) && !world.getBlockState(blockToAir.offset(blockState.getValue((IProperty<EnumFacing>)BlockTripWireHook.FACING).getOpposite())).getBlock().isNormalCube()) {
            this.dropBlockAsItem(world, blockToAir, blockState, "".length());
            world.setBlockToAir(blockToAir);
        }
    }
    
    static {
        I();
        FACING = PropertyDirection.create(BlockTripWireHook.I["".length()], (Predicate<EnumFacing>)EnumFacing.Plane.HORIZONTAL);
        POWERED = PropertyBool.create(BlockTripWireHook.I[" ".length()]);
        ATTACHED = PropertyBool.create(BlockTripWireHook.I["  ".length()]);
        SUSPENDED = PropertyBool.create(BlockTripWireHook.I["   ".length()]);
    }
}
