package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.*;

public abstract class BlockButton extends Block
{
    private static final String[] I;
    private final boolean wooden;
    private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;
    public static final PropertyDirection FACING;
    public static final PropertyBool POWERED;
    
    @Override
    public boolean canPlaceBlockOnSide(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        return func_181088_a(world, blockPos, enumFacing.getOpposite());
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        final float n = 0.1875f;
        final float n2 = 0.125f;
        final float n3 = 0.125f;
        this.setBlockBounds(0.5f - n, 0.5f - n2, 0.5f - n3, 0.5f + n, 0.5f + n2, 0.5f + n3);
    }
    
    @Override
    public int getStrongPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        int n;
        if (!blockState.getValue((IProperty<Boolean>)BlockButton.POWERED)) {
            n = "".length();
            "".length();
            if (0 == -1) {
                throw null;
            }
        }
        else if (blockState.getValue((IProperty<Comparable>)BlockButton.FACING) == enumFacing) {
            n = (0x7 ^ 0x8);
            "".length();
            if (3 == -1) {
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
        final EnumFacing[] values;
        final int length = (values = EnumFacing.values()).length;
        int i = "".length();
        "".length();
        if (0 == 2) {
            throw null;
        }
        while (i < length) {
            if (func_181088_a(world, blockPos, values[i])) {
                return " ".length() != 0;
            }
            ++i;
        }
        return "".length() != 0;
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing() {
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing = BlockButton.$SWITCH_TABLE$net$minecraft$util$EnumFacing;
        if ($switch_TABLE$net$minecraft$util$EnumFacing != null) {
            return $switch_TABLE$net$minecraft$util$EnumFacing;
        }
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing2 = new int[EnumFacing.values().length];
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.DOWN.ordinal()] = " ".length();
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.EAST.ordinal()] = (0x83 ^ 0x85);
            "".length();
            if (true != true) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.NORTH.ordinal()] = "   ".length();
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.SOUTH.ordinal()] = (0xC5 ^ 0xC1);
            "".length();
            if (1 < -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.UP.ordinal()] = "  ".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.WEST.ordinal()] = (0x62 ^ 0x67);
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        return BlockButton.$SWITCH_TABLE$net$minecraft$util$EnumFacing = $switch_TABLE$net$minecraft$util$EnumFacing2;
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
            if (3 <= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int tickRate(final World world) {
        int n;
        if (this.wooden) {
            n = (0x5A ^ 0x44);
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        else {
            n = (0x28 ^ 0x3C);
        }
        return n;
    }
    
    private boolean checkForDrop(final World world, final BlockPos blockToAir, final IBlockState blockState) {
        if (this.canPlaceBlockAt(world, blockToAir)) {
            return " ".length() != 0;
        }
        this.dropBlockAsItem(world, blockToAir, blockState, "".length());
        world.setBlockToAir(blockToAir);
        return "".length() != 0;
    }
    
    static {
        I();
        FACING = PropertyDirection.create(BlockButton.I["".length()]);
        POWERED = PropertyBool.create(BlockButton.I[" ".length()]);
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (blockState.getValue((IProperty<Boolean>)BlockButton.POWERED)) {
            this.notifyNeighbors(world, blockPos, blockState.getValue((IProperty<EnumFacing>)BlockButton.FACING));
        }
        super.breakBlock(world, blockPos, blockState);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (this.checkForDrop(world, blockToAir, blockState) && !func_181088_a(world, blockToAir, blockState.getValue((IProperty<EnumFacing>)BlockButton.FACING).getOpposite())) {
            this.dropBlockAsItem(world, blockToAir, blockState, "".length());
            world.setBlockToAir(blockToAir);
        }
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockPos, final IBlockState blockState, final Entity entity) {
        if (!world.isRemote && this.wooden && !blockState.getValue((IProperty<Boolean>)BlockButton.POWERED)) {
            this.checkForArrows(world, blockPos, blockState);
        }
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (blockState.getValue((IProperty<Boolean>)BlockButton.POWERED)) {
            return " ".length() != 0;
        }
        world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockButton.POWERED, (boolean)(" ".length() != 0)), "   ".length());
        world.markBlockRangeForRenderUpdate(blockPos, blockPos);
        world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, BlockButton.I["  ".length()], 0.3f, 0.6f);
        this.notifyNeighbors(world, blockPos, blockState.getValue((IProperty<EnumFacing>)BlockButton.FACING));
        world.scheduleUpdate(blockPos, this, this.tickRate(world));
        return " ".length() != 0;
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty["  ".length()];
        array["".length()] = BlockButton.FACING;
        array[" ".length()] = BlockButton.POWERED;
        return new BlockState(this, array);
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (!world.isRemote && blockState.getValue((IProperty<Boolean>)BlockButton.POWERED)) {
            if (this.wooden) {
                this.checkForArrows(world, blockPos, blockState);
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            else {
                world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockButton.POWERED, (boolean)("".length() != 0)));
                this.notifyNeighbors(world, blockPos, blockState.getValue((IProperty<EnumFacing>)BlockButton.FACING));
                world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, BlockButton.I["   ".length()], 0.3f, 0.5f);
                world.markBlockRangeForRenderUpdate(blockPos, blockPos);
            }
        }
    }
    
    @Override
    public int getWeakPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        int length;
        if (blockState.getValue((IProperty<Boolean>)BlockButton.POWERED)) {
            length = (0x59 ^ 0x56);
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            length = "".length();
        }
        return length;
    }
    
    private static void I() {
        (I = new String[0x84 ^ 0x82])["".length()] = I("\u001c\n3\u001d'\u001d", "zkPtI");
        BlockButton.I[" ".length()] = I("\u00048!0'\u00113", "tWVUU");
        BlockButton.I["  ".length()] = I("+*\u000b&\r4e\u0006.\u000b: ", "YKeBb");
        BlockButton.I["   ".length()] = I("9\u000f\n\u000b\b&@\u0007\u0003\u000e(\u0005", "Kndog");
        BlockButton.I[0xB8 ^ 0xBC] = I("\u0017\u0002\u0002+6\bM\u000f#0\u0006\b", "eclOY");
        BlockButton.I[0x7E ^ 0x7B] = I("86\t\u001d\t'y\u0004\u0015\u000f)<", "JWgyf");
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = 0;
        switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[blockState.getValue((IProperty<EnumFacing>)BlockButton.FACING).ordinal()]) {
            case 6: {
                n = " ".length();
                "".length();
                if (2 <= 0) {
                    throw null;
                }
                break;
            }
            case 5: {
                n = "  ".length();
                "".length();
                if (-1 != -1) {
                    throw null;
                }
                break;
            }
            case 4: {
                n = "   ".length();
                "".length();
                if (0 == 2) {
                    throw null;
                }
                break;
            }
            case 3: {
                n = (0x3D ^ 0x39);
                "".length();
                if (4 <= 1) {
                    throw null;
                }
                break;
            }
            default: {
                n = (0xC3 ^ 0xC6);
                "".length();
                if (-1 >= 1) {
                    throw null;
                }
                break;
            }
            case 1: {
                n = "".length();
                break;
            }
        }
        if (blockState.getValue((IProperty<Boolean>)BlockButton.POWERED)) {
            n |= (0xAE ^ 0xA6);
        }
        return n;
    }
    
    @Override
    public boolean canProvidePower() {
        return " ".length() != 0;
    }
    
    @Override
    public void randomTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
    }
    
    protected BlockButton(final boolean wooden) {
        super(Material.circuits);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockButton.FACING, EnumFacing.NORTH).withProperty((IProperty<Comparable>)BlockButton.POWERED, (boolean)("".length() != 0)));
        this.setTickRandomly(" ".length() != 0);
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.wooden = wooden;
    }
    
    protected static boolean func_181088_a(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        final BlockPos offset = blockPos.offset(enumFacing);
        boolean b;
        if (enumFacing == EnumFacing.DOWN) {
            b = World.doesBlockHaveSolidTopSurface(world, offset);
            "".length();
            if (1 == -1) {
                throw null;
            }
        }
        else {
            b = world.getBlockState(offset).getBlock().isNormalCube();
        }
        return b;
    }
    
    private void checkForArrows(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.updateBlockBounds(blockState);
        int n;
        if (world.getEntitiesWithinAABB((Class<? extends Entity>)EntityArrow.class, new AxisAlignedBB(blockPos.getX() + this.minX, blockPos.getY() + this.minY, blockPos.getZ() + this.minZ, blockPos.getX() + this.maxX, blockPos.getY() + this.maxY, blockPos.getZ() + this.maxZ)).isEmpty()) {
            n = "".length();
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        final int n2 = n;
        final boolean booleanValue = blockState.getValue((IProperty<Boolean>)BlockButton.POWERED);
        if (n2 != 0 && !booleanValue) {
            world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockButton.POWERED, (boolean)(" ".length() != 0)));
            this.notifyNeighbors(world, blockPos, blockState.getValue((IProperty<EnumFacing>)BlockButton.FACING));
            world.markBlockRangeForRenderUpdate(blockPos, blockPos);
            world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, BlockButton.I[0xA5 ^ 0xA1], 0.3f, 0.6f);
        }
        if (n2 == 0 && booleanValue) {
            world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockButton.POWERED, (boolean)("".length() != 0)));
            this.notifyNeighbors(world, blockPos, blockState.getValue((IProperty<EnumFacing>)BlockButton.FACING));
            world.markBlockRangeForRenderUpdate(blockPos, blockPos);
            world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, BlockButton.I[0x53 ^ 0x56], 0.3f, 0.5f);
        }
        if (n2 != 0) {
            world.scheduleUpdate(blockPos, this, this.tickRate(world));
        }
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        this.updateBlockBounds(blockAccess.getBlockState(blockPos));
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        EnumFacing enumFacing = null;
        switch (n & (0x9E ^ 0x99)) {
            case 0: {
                enumFacing = EnumFacing.DOWN;
                "".length();
                if (2 < 2) {
                    throw null;
                }
                break;
            }
            case 1: {
                enumFacing = EnumFacing.EAST;
                "".length();
                if (!true) {
                    throw null;
                }
                break;
            }
            case 2: {
                enumFacing = EnumFacing.WEST;
                "".length();
                if (4 != 4) {
                    throw null;
                }
                break;
            }
            case 3: {
                enumFacing = EnumFacing.SOUTH;
                "".length();
                if (1 < -1) {
                    throw null;
                }
                break;
            }
            case 4: {
                enumFacing = EnumFacing.NORTH;
                "".length();
                if (-1 == 3) {
                    throw null;
                }
                break;
            }
            default: {
                enumFacing = EnumFacing.UP;
                break;
            }
        }
        final IBlockState withProperty = this.getDefaultState().withProperty((IProperty<Comparable>)BlockButton.FACING, enumFacing);
        final PropertyBool powered = BlockButton.POWERED;
        int n2;
        if ((n & (0x8F ^ 0x87)) > 0) {
            n2 = " ".length();
            "".length();
            if (3 == -1) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        return withProperty.withProperty((IProperty<Comparable>)powered, n2 != 0);
    }
    
    private void notifyNeighbors(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        world.notifyNeighborsOfStateChange(blockPos, this);
        world.notifyNeighborsOfStateChange(blockPos.offset(enumFacing.getOpposite()), this);
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        IBlockState blockState;
        if (func_181088_a(world, blockPos, enumFacing.getOpposite())) {
            blockState = this.getDefaultState().withProperty((IProperty<Comparable>)BlockButton.FACING, enumFacing).withProperty((IProperty<Comparable>)BlockButton.POWERED, "".length() != 0);
            "".length();
            if (4 < 0) {
                throw null;
            }
        }
        else {
            blockState = this.getDefaultState().withProperty((IProperty<Comparable>)BlockButton.FACING, EnumFacing.DOWN).withProperty((IProperty<Comparable>)BlockButton.POWERED, "".length() != 0);
        }
        return blockState;
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    private void updateBlockBounds(final IBlockState blockState) {
        final EnumFacing enumFacing = blockState.getValue((IProperty<EnumFacing>)BlockButton.FACING);
        int n;
        if (blockState.getValue((IProperty<Boolean>)BlockButton.POWERED)) {
            n = " ".length();
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else {
            n = "  ".length();
        }
        final float n2 = n / 16.0f;
        switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[enumFacing.ordinal()]) {
            case 6: {
                this.setBlockBounds(0.0f, 0.375f, 0.3125f, n2, 0.625f, 0.6875f);
                "".length();
                if (3 < 2) {
                    throw null;
                }
                break;
            }
            case 5: {
                this.setBlockBounds(1.0f - n2, 0.375f, 0.3125f, 1.0f, 0.625f, 0.6875f);
                "".length();
                if (3 <= 0) {
                    throw null;
                }
                break;
            }
            case 4: {
                this.setBlockBounds(0.3125f, 0.375f, 0.0f, 0.6875f, 0.625f, n2);
                "".length();
                if (false) {
                    throw null;
                }
                break;
            }
            case 3: {
                this.setBlockBounds(0.3125f, 0.375f, 1.0f - n2, 0.6875f, 0.625f, 1.0f);
                "".length();
                if (1 < 0) {
                    throw null;
                }
                break;
            }
            case 2: {
                this.setBlockBounds(0.3125f, 0.0f, 0.375f, 0.6875f, 0.0f + n2, 0.625f);
                "".length();
                if (4 != 4) {
                    throw null;
                }
                break;
            }
            case 1: {
                this.setBlockBounds(0.3125f, 1.0f - n2, 0.375f, 0.6875f, 1.0f, 0.625f);
                break;
            }
        }
    }
}
