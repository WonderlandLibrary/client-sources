package net.minecraft.block;

import net.minecraft.block.properties.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;
import com.google.common.base.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.util.*;

public class BlockStairs extends Block
{
    private final Block modelBlock;
    private static final String[] I;
    public static final PropertyDirection FACING;
    private final IBlockState modelState;
    public static final PropertyEnum<EnumShape> SHAPE;
    private int rayTracePass;
    private static final int[][] field_150150_a;
    public static final PropertyEnum<EnumHalf> HALF;
    private boolean hasRaytraced;
    
    public static boolean isBlockStairs(final Block block) {
        return block instanceof BlockStairs;
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        this.modelBlock.updateTick(world, blockPos, blockState, random);
    }
    
    @Override
    public float getExplosionResistance(final Entity entity) {
        return this.modelBlock.getExplosionResistance(entity);
    }
    
    @Override
    public int getMixedBrightnessForBlock(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return this.modelBlock.getMixedBrightnessForBlock(blockAccess, blockPos);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I(".*5 8/", "HKVIV");
        BlockStairs.I[" ".length()] = I("\u000e#\b1", "fBdWS");
        BlockStairs.I["  ".length()] = I("\u001c=\f\n#", "oUmzF");
    }
    
    @Override
    public boolean canCollideCheck(final IBlockState blockState, final boolean b) {
        return this.modelBlock.canCollideCheck(blockState, b);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final IBlockState defaultState = this.getDefaultState();
        final PropertyEnum<EnumHalf> half = BlockStairs.HALF;
        EnumHalf enumHalf;
        if ((n & (0x55 ^ 0x51)) > 0) {
            enumHalf = EnumHalf.TOP;
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else {
            enumHalf = EnumHalf.BOTTOM;
        }
        return defaultState.withProperty((IProperty<Comparable>)half, enumHalf).withProperty((IProperty<Comparable>)BlockStairs.FACING, EnumFacing.getFront((0x7C ^ 0x79) - (n & "   ".length())));
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int length = "".length();
        if (blockState.getValue(BlockStairs.HALF) == EnumHalf.TOP) {
            length |= (0x1C ^ 0x18);
        }
        return length | (0x8A ^ 0x8F) - blockState.getValue((IProperty<EnumFacing>)BlockStairs.FACING).getIndex();
    }
    
    public void setBaseCollisionBounds(final IBlockAccess blockAccess, final BlockPos blockPos) {
        if (blockAccess.getBlockState(blockPos).getValue(BlockStairs.HALF) == EnumHalf.TOP) {
            this.setBlockBounds(0.0f, 0.5f, 0.0f, 1.0f, 1.0f, 1.0f);
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        }
    }
    
    public static boolean isSameStair(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState) {
        final IBlockState blockState2 = blockAccess.getBlockState(blockPos);
        if (isBlockStairs(blockState2.getBlock()) && blockState2.getValue(BlockStairs.HALF) == blockState.getValue(BlockStairs.HALF) && blockState2.getValue((IProperty<Comparable>)BlockStairs.FACING) == blockState.getValue((IProperty<Comparable>)BlockStairs.FACING)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        if (this.hasRaytraced) {
            this.setBlockBounds(0.5f * (this.rayTracePass % "  ".length()), 0.5f * (this.rayTracePass / (0x27 ^ 0x23) % "  ".length()), 0.5f * (this.rayTracePass / "  ".length() % "  ".length()), 0.5f + 0.5f * (this.rayTracePass % "  ".length()), 0.5f + 0.5f * (this.rayTracePass / (0x1F ^ 0x1B) % "  ".length()), 0.5f + 0.5f * (this.rayTracePass / "  ".length() % "  ".length()));
            "".length();
            if (2 == -1) {
                throw null;
            }
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
    }
    
    @Override
    public IBlockState getActualState(IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        if (this.func_176306_h(blockAccess, blockPos)) {
            switch (this.func_176305_g(blockAccess, blockPos)) {
                case 0: {
                    blockState = blockState.withProperty(BlockStairs.SHAPE, EnumShape.STRAIGHT);
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                    return blockState;
                }
                case 1: {
                    blockState = blockState.withProperty(BlockStairs.SHAPE, EnumShape.INNER_RIGHT);
                    "".length();
                    if (4 == 3) {
                        throw null;
                    }
                    return blockState;
                }
                case 2: {
                    blockState = blockState.withProperty(BlockStairs.SHAPE, EnumShape.INNER_LEFT);
                    break;
                }
            }
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else {
            switch (this.func_176307_f(blockAccess, blockPos)) {
                case 0: {
                    blockState = blockState.withProperty(BlockStairs.SHAPE, EnumShape.STRAIGHT);
                    "".length();
                    if (false) {
                        throw null;
                    }
                    break;
                }
                case 1: {
                    blockState = blockState.withProperty(BlockStairs.SHAPE, EnumShape.OUTER_RIGHT);
                    "".length();
                    if (2 <= 1) {
                        throw null;
                    }
                    break;
                }
                case 2: {
                    blockState = blockState.withProperty(BlockStairs.SHAPE, EnumShape.OUTER_LEFT);
                    break;
                }
            }
        }
        return blockState;
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockPos, final Entity entity) {
        this.modelBlock.onEntityCollidedWithBlock(world, blockPos, entity);
    }
    
    @Override
    public int tickRate(final World world) {
        return this.modelBlock.tickRate(world);
    }
    
    @Override
    public MovingObjectPosition collisionRayTrace(final World world, final BlockPos blockPos, final Vec3 vec3, final Vec3 vec4) {
        final MovingObjectPosition[] array = new MovingObjectPosition[0xAC ^ 0xA4];
        final IBlockState blockState = world.getBlockState(blockPos);
        final int horizontalIndex = blockState.getValue((IProperty<EnumFacing>)BlockStairs.FACING).getHorizontalIndex();
        int n;
        if (blockState.getValue(BlockStairs.HALF) == EnumHalf.TOP) {
            n = " ".length();
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        final int n2 = n;
        final int[][] field_150150_a = BlockStairs.field_150150_a;
        final int n3 = horizontalIndex;
        int length;
        if (n2 != 0) {
            length = (0xA1 ^ 0xA5);
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        else {
            length = "".length();
        }
        final int[] array2 = field_150150_a[n3 + length];
        this.hasRaytraced = (" ".length() != 0);
        int i = "".length();
        "".length();
        if (-1 >= 3) {
            throw null;
        }
        while (i < (0x8B ^ 0x83)) {
            this.rayTracePass = i;
            if (Arrays.binarySearch(array2, i) < 0) {
                array[i] = super.collisionRayTrace(world, blockPos, vec3, vec4);
            }
            ++i;
        }
        final int[] array3;
        final int length2 = (array3 = array2).length;
        int j = "".length();
        "".length();
        if (3 < 0) {
            throw null;
        }
        while (j < length2) {
            array[array3[j]] = null;
            ++j;
        }
        MovingObjectPosition movingObjectPosition = null;
        double n4 = 0.0;
        final MovingObjectPosition[] array4;
        final int length3 = (array4 = array).length;
        int k = "".length();
        "".length();
        if (2 >= 3) {
            throw null;
        }
        while (k < length3) {
            final MovingObjectPosition movingObjectPosition2 = array4[k];
            if (movingObjectPosition2 != null) {
                final double squareDistanceTo = movingObjectPosition2.hitVec.squareDistanceTo(vec4);
                if (squareDistanceTo > n4) {
                    movingObjectPosition = movingObjectPosition2;
                    n4 = squareDistanceTo;
                }
            }
            ++k;
        }
        return movingObjectPosition;
    }
    
    public boolean func_176306_h(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        final EnumFacing enumFacing = blockState.getValue((IProperty<EnumFacing>)BlockStairs.FACING);
        final EnumHalf enumHalf = blockState.getValue(BlockStairs.HALF);
        int n;
        if (enumHalf == EnumHalf.TOP) {
            n = " ".length();
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        final int n2 = n;
        float n3 = 0.5f;
        float n4 = 1.0f;
        if (n2 != 0) {
            n3 = 0.0f;
            n4 = 0.5f;
        }
        float n5 = 0.0f;
        float n6 = 1.0f;
        float n7 = 0.0f;
        float n8 = 0.5f;
        int n9 = " ".length();
        if (enumFacing == EnumFacing.EAST) {
            n5 = 0.5f;
            n8 = 1.0f;
            final IBlockState blockState2 = blockAccess.getBlockState(blockPos.east());
            if (isBlockStairs(blockState2.getBlock()) && enumHalf == blockState2.getValue(BlockStairs.HALF)) {
                final EnumFacing enumFacing2 = blockState2.getValue((IProperty<EnumFacing>)BlockStairs.FACING);
                if (enumFacing2 == EnumFacing.NORTH && !isSameStair(blockAccess, blockPos.south(), blockState)) {
                    n8 = 0.5f;
                    n9 = "".length();
                    "".length();
                    if (2 <= -1) {
                        throw null;
                    }
                }
                else if (enumFacing2 == EnumFacing.SOUTH && !isSameStair(blockAccess, blockPos.north(), blockState)) {
                    n7 = 0.5f;
                    n9 = "".length();
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
            }
        }
        else if (enumFacing == EnumFacing.WEST) {
            n6 = 0.5f;
            n8 = 1.0f;
            final IBlockState blockState3 = blockAccess.getBlockState(blockPos.west());
            if (isBlockStairs(blockState3.getBlock()) && enumHalf == blockState3.getValue(BlockStairs.HALF)) {
                final EnumFacing enumFacing3 = blockState3.getValue((IProperty<EnumFacing>)BlockStairs.FACING);
                if (enumFacing3 == EnumFacing.NORTH && !isSameStair(blockAccess, blockPos.south(), blockState)) {
                    n8 = 0.5f;
                    n9 = "".length();
                    "".length();
                    if (0 == 2) {
                        throw null;
                    }
                }
                else if (enumFacing3 == EnumFacing.SOUTH && !isSameStair(blockAccess, blockPos.north(), blockState)) {
                    n7 = 0.5f;
                    n9 = "".length();
                    "".length();
                    if (3 == 1) {
                        throw null;
                    }
                }
            }
        }
        else if (enumFacing == EnumFacing.SOUTH) {
            n7 = 0.5f;
            n8 = 1.0f;
            final IBlockState blockState4 = blockAccess.getBlockState(blockPos.south());
            if (isBlockStairs(blockState4.getBlock()) && enumHalf == blockState4.getValue(BlockStairs.HALF)) {
                final EnumFacing enumFacing4 = blockState4.getValue((IProperty<EnumFacing>)BlockStairs.FACING);
                if (enumFacing4 == EnumFacing.WEST && !isSameStair(blockAccess, blockPos.east(), blockState)) {
                    n6 = 0.5f;
                    n9 = "".length();
                    "".length();
                    if (3 < 0) {
                        throw null;
                    }
                }
                else if (enumFacing4 == EnumFacing.EAST && !isSameStair(blockAccess, blockPos.west(), blockState)) {
                    n5 = 0.5f;
                    n9 = "".length();
                    "".length();
                    if (1 >= 3) {
                        throw null;
                    }
                }
            }
        }
        else if (enumFacing == EnumFacing.NORTH) {
            final IBlockState blockState5 = blockAccess.getBlockState(blockPos.north());
            if (isBlockStairs(blockState5.getBlock()) && enumHalf == blockState5.getValue(BlockStairs.HALF)) {
                final EnumFacing enumFacing5 = blockState5.getValue((IProperty<EnumFacing>)BlockStairs.FACING);
                if (enumFacing5 == EnumFacing.WEST && !isSameStair(blockAccess, blockPos.east(), blockState)) {
                    n6 = 0.5f;
                    n9 = "".length();
                    "".length();
                    if (3 <= 1) {
                        throw null;
                    }
                }
                else if (enumFacing5 == EnumFacing.EAST && !isSameStair(blockAccess, blockPos.west(), blockState)) {
                    n5 = 0.5f;
                    n9 = "".length();
                }
            }
        }
        this.setBlockBounds(n5, n3, n7, n6, n4, n8);
        return n9 != 0;
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBox(final World world, final BlockPos blockPos) {
        return this.modelBlock.getSelectedBoundingBox(world, blockPos);
    }
    
    public int func_176307_f(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        final EnumFacing enumFacing = blockState.getValue((IProperty<EnumFacing>)BlockStairs.FACING);
        final EnumHalf enumHalf = blockState.getValue(BlockStairs.HALF);
        int n;
        if (enumHalf == EnumHalf.TOP) {
            n = " ".length();
            "".length();
            if (4 == 0) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        final int n2 = n;
        if (enumFacing == EnumFacing.EAST) {
            final IBlockState blockState2 = blockAccess.getBlockState(blockPos.east());
            if (isBlockStairs(blockState2.getBlock()) && enumHalf == blockState2.getValue(BlockStairs.HALF)) {
                final EnumFacing enumFacing2 = blockState2.getValue((IProperty<EnumFacing>)BlockStairs.FACING);
                if (enumFacing2 == EnumFacing.NORTH && !isSameStair(blockAccess, blockPos.south(), blockState)) {
                    int n3;
                    if (n2 != 0) {
                        n3 = " ".length();
                        "".length();
                        if (false) {
                            throw null;
                        }
                    }
                    else {
                        n3 = "  ".length();
                    }
                    return n3;
                }
                if (enumFacing2 == EnumFacing.SOUTH && !isSameStair(blockAccess, blockPos.north(), blockState)) {
                    int n4;
                    if (n2 != 0) {
                        n4 = "  ".length();
                        "".length();
                        if (-1 != -1) {
                            throw null;
                        }
                    }
                    else {
                        n4 = " ".length();
                    }
                    return n4;
                }
            }
        }
        else if (enumFacing == EnumFacing.WEST) {
            final IBlockState blockState3 = blockAccess.getBlockState(blockPos.west());
            if (isBlockStairs(blockState3.getBlock()) && enumHalf == blockState3.getValue(BlockStairs.HALF)) {
                final EnumFacing enumFacing3 = blockState3.getValue((IProperty<EnumFacing>)BlockStairs.FACING);
                if (enumFacing3 == EnumFacing.NORTH && !isSameStair(blockAccess, blockPos.south(), blockState)) {
                    int n5;
                    if (n2 != 0) {
                        n5 = "  ".length();
                        "".length();
                        if (4 <= 2) {
                            throw null;
                        }
                    }
                    else {
                        n5 = " ".length();
                    }
                    return n5;
                }
                if (enumFacing3 == EnumFacing.SOUTH && !isSameStair(blockAccess, blockPos.north(), blockState)) {
                    int n6;
                    if (n2 != 0) {
                        n6 = " ".length();
                        "".length();
                        if (-1 == 4) {
                            throw null;
                        }
                    }
                    else {
                        n6 = "  ".length();
                    }
                    return n6;
                }
            }
        }
        else if (enumFacing == EnumFacing.SOUTH) {
            final IBlockState blockState4 = blockAccess.getBlockState(blockPos.south());
            if (isBlockStairs(blockState4.getBlock()) && enumHalf == blockState4.getValue(BlockStairs.HALF)) {
                final EnumFacing enumFacing4 = blockState4.getValue((IProperty<EnumFacing>)BlockStairs.FACING);
                if (enumFacing4 == EnumFacing.WEST && !isSameStair(blockAccess, blockPos.east(), blockState)) {
                    int n7;
                    if (n2 != 0) {
                        n7 = "  ".length();
                        "".length();
                        if (0 >= 3) {
                            throw null;
                        }
                    }
                    else {
                        n7 = " ".length();
                    }
                    return n7;
                }
                if (enumFacing4 == EnumFacing.EAST && !isSameStair(blockAccess, blockPos.west(), blockState)) {
                    int n8;
                    if (n2 != 0) {
                        n8 = " ".length();
                        "".length();
                        if (3 <= 2) {
                            throw null;
                        }
                    }
                    else {
                        n8 = "  ".length();
                    }
                    return n8;
                }
            }
        }
        else if (enumFacing == EnumFacing.NORTH) {
            final IBlockState blockState5 = blockAccess.getBlockState(blockPos.north());
            if (isBlockStairs(blockState5.getBlock()) && enumHalf == blockState5.getValue(BlockStairs.HALF)) {
                final EnumFacing enumFacing5 = blockState5.getValue((IProperty<EnumFacing>)BlockStairs.FACING);
                if (enumFacing5 == EnumFacing.WEST && !isSameStair(blockAccess, blockPos.east(), blockState)) {
                    int n9;
                    if (n2 != 0) {
                        n9 = " ".length();
                        "".length();
                        if (1 >= 3) {
                            throw null;
                        }
                    }
                    else {
                        n9 = "  ".length();
                    }
                    return n9;
                }
                if (enumFacing5 == EnumFacing.EAST && !isSameStair(blockAccess, blockPos.west(), blockState)) {
                    int n10;
                    if (n2 != 0) {
                        n10 = "  ".length();
                        "".length();
                        if (-1 < -1) {
                            throw null;
                        }
                    }
                    else {
                        n10 = " ".length();
                    }
                    return n10;
                }
            }
        }
        return "".length();
    }
    
    @Override
    public void addCollisionBoxesToList(final World world, final BlockPos blockPos, final IBlockState blockState, final AxisAlignedBB axisAlignedBB, final List<AxisAlignedBB> list, final Entity entity) {
        this.setBaseCollisionBounds(world, blockPos);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        final boolean func_176306_h = this.func_176306_h(world, blockPos);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        if (func_176306_h && this.func_176304_i(world, blockPos)) {
            super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
        }
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void onBlockDestroyedByExplosion(final World world, final BlockPos blockPos, final Explosion explosion) {
        this.modelBlock.onBlockDestroyedByExplosion(world, blockPos, explosion);
    }
    
    @Override
    public boolean isCollidable() {
        return this.modelBlock.isCollidable();
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        this.modelBlock.randomDisplayTick(world, blockPos, blockState, random);
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        return this.modelBlock.onBlockActivated(world, blockPos, this.modelState, entityPlayer, EnumFacing.DOWN, 0.0f, 0.0f, 0.0f);
    }
    
    @Override
    public void onBlockClicked(final World world, final BlockPos blockPos, final EntityPlayer entityPlayer) {
        this.modelBlock.onBlockClicked(world, blockPos, entityPlayer);
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        final IBlockState withProperty = super.onBlockPlaced(world, blockPos, enumFacing, n, n2, n3, n4, entityLivingBase).withProperty((IProperty<Comparable>)BlockStairs.FACING, entityLivingBase.getHorizontalFacing()).withProperty(BlockStairs.SHAPE, EnumShape.STRAIGHT);
        IBlockState blockState;
        if (enumFacing != EnumFacing.DOWN && (enumFacing == EnumFacing.UP || n2 <= 0.5)) {
            blockState = withProperty.withProperty(BlockStairs.HALF, EnumHalf.BOTTOM);
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else {
            blockState = withProperty.withProperty(BlockStairs.HALF, EnumHalf.TOP);
        }
        return blockState;
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return this.modelBlock.getBlockLayer();
    }
    
    protected BlockStairs(final IBlockState modelState) {
        super(modelState.getBlock().blockMaterial);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockStairs.FACING, EnumFacing.NORTH).withProperty(BlockStairs.HALF, EnumHalf.BOTTOM).withProperty(BlockStairs.SHAPE, EnumShape.STRAIGHT));
        this.modelBlock = modelState.getBlock();
        this.modelState = modelState;
        this.setHardness(this.modelBlock.blockHardness);
        this.setResistance(this.modelBlock.blockResistance / 3.0f);
        this.setStepSound(this.modelBlock.stepSound);
        this.setLightOpacity(87 + 147 - 78 + 99);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public Vec3 modifyAcceleration(final World world, final BlockPos blockPos, final Entity entity, final Vec3 vec3) {
        return this.modelBlock.modifyAcceleration(world, blockPos, entity, vec3);
    }
    
    @Override
    public void onBlockDestroyedByPlayer(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.modelBlock.onBlockDestroyedByPlayer(world, blockPos, blockState);
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty["   ".length()];
        array["".length()] = BlockStairs.FACING;
        array[" ".length()] = BlockStairs.HALF;
        array["  ".length()] = BlockStairs.SHAPE;
        return new BlockState(this, array);
    }
    
    public int func_176305_g(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        final EnumFacing enumFacing = blockState.getValue((IProperty<EnumFacing>)BlockStairs.FACING);
        final EnumHalf enumHalf = blockState.getValue(BlockStairs.HALF);
        int n;
        if (enumHalf == EnumHalf.TOP) {
            n = " ".length();
            "".length();
            if (3 < 0) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        final int n2 = n;
        if (enumFacing == EnumFacing.EAST) {
            final IBlockState blockState2 = blockAccess.getBlockState(blockPos.west());
            if (isBlockStairs(blockState2.getBlock()) && enumHalf == blockState2.getValue(BlockStairs.HALF)) {
                final EnumFacing enumFacing2 = blockState2.getValue((IProperty<EnumFacing>)BlockStairs.FACING);
                if (enumFacing2 == EnumFacing.NORTH && !isSameStair(blockAccess, blockPos.north(), blockState)) {
                    int n3;
                    if (n2 != 0) {
                        n3 = " ".length();
                        "".length();
                        if (-1 != -1) {
                            throw null;
                        }
                    }
                    else {
                        n3 = "  ".length();
                    }
                    return n3;
                }
                if (enumFacing2 == EnumFacing.SOUTH && !isSameStair(blockAccess, blockPos.south(), blockState)) {
                    int n4;
                    if (n2 != 0) {
                        n4 = "  ".length();
                        "".length();
                        if (4 <= -1) {
                            throw null;
                        }
                    }
                    else {
                        n4 = " ".length();
                    }
                    return n4;
                }
            }
        }
        else if (enumFacing == EnumFacing.WEST) {
            final IBlockState blockState3 = blockAccess.getBlockState(blockPos.east());
            if (isBlockStairs(blockState3.getBlock()) && enumHalf == blockState3.getValue(BlockStairs.HALF)) {
                final EnumFacing enumFacing3 = blockState3.getValue((IProperty<EnumFacing>)BlockStairs.FACING);
                if (enumFacing3 == EnumFacing.NORTH && !isSameStair(blockAccess, blockPos.north(), blockState)) {
                    int n5;
                    if (n2 != 0) {
                        n5 = "  ".length();
                        "".length();
                        if (0 >= 2) {
                            throw null;
                        }
                    }
                    else {
                        n5 = " ".length();
                    }
                    return n5;
                }
                if (enumFacing3 == EnumFacing.SOUTH && !isSameStair(blockAccess, blockPos.south(), blockState)) {
                    int n6;
                    if (n2 != 0) {
                        n6 = " ".length();
                        "".length();
                        if (2 <= 0) {
                            throw null;
                        }
                    }
                    else {
                        n6 = "  ".length();
                    }
                    return n6;
                }
            }
        }
        else if (enumFacing == EnumFacing.SOUTH) {
            final IBlockState blockState4 = blockAccess.getBlockState(blockPos.north());
            if (isBlockStairs(blockState4.getBlock()) && enumHalf == blockState4.getValue(BlockStairs.HALF)) {
                final EnumFacing enumFacing4 = blockState4.getValue((IProperty<EnumFacing>)BlockStairs.FACING);
                if (enumFacing4 == EnumFacing.WEST && !isSameStair(blockAccess, blockPos.west(), blockState)) {
                    int n7;
                    if (n2 != 0) {
                        n7 = "  ".length();
                        "".length();
                        if (2 <= -1) {
                            throw null;
                        }
                    }
                    else {
                        n7 = " ".length();
                    }
                    return n7;
                }
                if (enumFacing4 == EnumFacing.EAST && !isSameStair(blockAccess, blockPos.east(), blockState)) {
                    int n8;
                    if (n2 != 0) {
                        n8 = " ".length();
                        "".length();
                        if (4 == 1) {
                            throw null;
                        }
                    }
                    else {
                        n8 = "  ".length();
                    }
                    return n8;
                }
            }
        }
        else if (enumFacing == EnumFacing.NORTH) {
            final IBlockState blockState5 = blockAccess.getBlockState(blockPos.south());
            if (isBlockStairs(blockState5.getBlock()) && enumHalf == blockState5.getValue(BlockStairs.HALF)) {
                final EnumFacing enumFacing5 = blockState5.getValue((IProperty<EnumFacing>)BlockStairs.FACING);
                if (enumFacing5 == EnumFacing.WEST && !isSameStair(blockAccess, blockPos.west(), blockState)) {
                    int n9;
                    if (n2 != 0) {
                        n9 = " ".length();
                        "".length();
                        if (4 != 4) {
                            throw null;
                        }
                    }
                    else {
                        n9 = "  ".length();
                    }
                    return n9;
                }
                if (enumFacing5 == EnumFacing.EAST && !isSameStair(blockAccess, blockPos.east(), blockState)) {
                    int n10;
                    if (n2 != 0) {
                        n10 = "  ".length();
                        "".length();
                        if (0 == -1) {
                            throw null;
                        }
                    }
                    else {
                        n10 = " ".length();
                    }
                    return n10;
                }
            }
        }
        return "".length();
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.modelBlock.breakBlock(world, blockPos, this.modelState);
    }
    
    static {
        I();
        FACING = PropertyDirection.create(BlockStairs.I["".length()], (Predicate<EnumFacing>)EnumFacing.Plane.HORIZONTAL);
        HALF = PropertyEnum.create(BlockStairs.I[" ".length()], EnumHalf.class);
        SHAPE = PropertyEnum.create(BlockStairs.I["  ".length()], EnumShape.class);
        final int[][] field_150150_a2 = new int[0x38 ^ 0x30][];
        final int length = "".length();
        final int[] array = new int["  ".length()];
        array["".length()] = (0xB8 ^ 0xBC);
        array[" ".length()] = (0x35 ^ 0x30);
        field_150150_a2[length] = array;
        final int length2 = " ".length();
        final int[] array2 = new int["  ".length()];
        array2["".length()] = (0x63 ^ 0x66);
        array2[" ".length()] = (0x56 ^ 0x51);
        field_150150_a2[length2] = array2;
        final int length3 = "  ".length();
        final int[] array3 = new int["  ".length()];
        array3["".length()] = (0xA6 ^ 0xA0);
        array3[" ".length()] = (0xBB ^ 0xBC);
        field_150150_a2[length3] = array3;
        final int length4 = "   ".length();
        final int[] array4 = new int["  ".length()];
        array4["".length()] = (0x9C ^ 0x98);
        array4[" ".length()] = (0x6B ^ 0x6D);
        field_150150_a2[length4] = array4;
        final int n = 0x80 ^ 0x84;
        final int[] array5 = new int["  ".length()];
        array5[" ".length()] = " ".length();
        field_150150_a2[n] = array5;
        final int n2 = 0xE ^ 0xB;
        final int[] array6 = new int["  ".length()];
        array6["".length()] = " ".length();
        array6[" ".length()] = "   ".length();
        field_150150_a2[n2] = array6;
        final int n3 = 0xAD ^ 0xAB;
        final int[] array7 = new int["  ".length()];
        array7["".length()] = "  ".length();
        array7[" ".length()] = "   ".length();
        field_150150_a2[n3] = array7;
        final int n4 = 0xC6 ^ 0xC1;
        final int[] array8 = new int["  ".length()];
        array8[" ".length()] = "  ".length();
        field_150150_a2[n4] = array8;
        field_150150_a = field_150150_a2;
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
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public boolean func_176304_i(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        final EnumFacing enumFacing = blockState.getValue((IProperty<EnumFacing>)BlockStairs.FACING);
        final EnumHalf enumHalf = blockState.getValue(BlockStairs.HALF);
        int n;
        if (enumHalf == EnumHalf.TOP) {
            n = " ".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        final int n2 = n;
        float n3 = 0.5f;
        float n4 = 1.0f;
        if (n2 != 0) {
            n3 = 0.0f;
            n4 = 0.5f;
        }
        float n5 = 0.0f;
        float n6 = 0.5f;
        float n7 = 0.5f;
        float n8 = 1.0f;
        int n9 = "".length();
        if (enumFacing == EnumFacing.EAST) {
            final IBlockState blockState2 = blockAccess.getBlockState(blockPos.west());
            if (isBlockStairs(blockState2.getBlock()) && enumHalf == blockState2.getValue(BlockStairs.HALF)) {
                final EnumFacing enumFacing2 = blockState2.getValue((IProperty<EnumFacing>)BlockStairs.FACING);
                if (enumFacing2 == EnumFacing.NORTH && !isSameStair(blockAccess, blockPos.north(), blockState)) {
                    n7 = 0.0f;
                    n8 = 0.5f;
                    n9 = " ".length();
                    "".length();
                    if (-1 == 1) {
                        throw null;
                    }
                }
                else if (enumFacing2 == EnumFacing.SOUTH && !isSameStair(blockAccess, blockPos.south(), blockState)) {
                    n7 = 0.5f;
                    n8 = 1.0f;
                    n9 = " ".length();
                    "".length();
                    if (1 <= 0) {
                        throw null;
                    }
                }
            }
        }
        else if (enumFacing == EnumFacing.WEST) {
            final IBlockState blockState3 = blockAccess.getBlockState(blockPos.east());
            if (isBlockStairs(blockState3.getBlock()) && enumHalf == blockState3.getValue(BlockStairs.HALF)) {
                n5 = 0.5f;
                n6 = 1.0f;
                final EnumFacing enumFacing3 = blockState3.getValue((IProperty<EnumFacing>)BlockStairs.FACING);
                if (enumFacing3 == EnumFacing.NORTH && !isSameStair(blockAccess, blockPos.north(), blockState)) {
                    n7 = 0.0f;
                    n8 = 0.5f;
                    n9 = " ".length();
                    "".length();
                    if (3 == 4) {
                        throw null;
                    }
                }
                else if (enumFacing3 == EnumFacing.SOUTH && !isSameStair(blockAccess, blockPos.south(), blockState)) {
                    n7 = 0.5f;
                    n8 = 1.0f;
                    n9 = " ".length();
                    "".length();
                    if (1 >= 3) {
                        throw null;
                    }
                }
            }
        }
        else if (enumFacing == EnumFacing.SOUTH) {
            final IBlockState blockState4 = blockAccess.getBlockState(blockPos.north());
            if (isBlockStairs(blockState4.getBlock()) && enumHalf == blockState4.getValue(BlockStairs.HALF)) {
                n7 = 0.0f;
                n8 = 0.5f;
                final EnumFacing enumFacing4 = blockState4.getValue((IProperty<EnumFacing>)BlockStairs.FACING);
                if (enumFacing4 == EnumFacing.WEST && !isSameStair(blockAccess, blockPos.west(), blockState)) {
                    n9 = " ".length();
                    "".length();
                    if (-1 >= 4) {
                        throw null;
                    }
                }
                else if (enumFacing4 == EnumFacing.EAST && !isSameStair(blockAccess, blockPos.east(), blockState)) {
                    n5 = 0.5f;
                    n6 = 1.0f;
                    n9 = " ".length();
                    "".length();
                    if (3 < -1) {
                        throw null;
                    }
                }
            }
        }
        else if (enumFacing == EnumFacing.NORTH) {
            final IBlockState blockState5 = blockAccess.getBlockState(blockPos.south());
            if (isBlockStairs(blockState5.getBlock()) && enumHalf == blockState5.getValue(BlockStairs.HALF)) {
                final EnumFacing enumFacing5 = blockState5.getValue((IProperty<EnumFacing>)BlockStairs.FACING);
                if (enumFacing5 == EnumFacing.WEST && !isSameStair(blockAccess, blockPos.west(), blockState)) {
                    n9 = " ".length();
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
                else if (enumFacing5 == EnumFacing.EAST && !isSameStair(blockAccess, blockPos.east(), blockState)) {
                    n5 = 0.5f;
                    n6 = 1.0f;
                    n9 = " ".length();
                }
            }
        }
        if (n9 != 0) {
            this.setBlockBounds(n5, n3, n7, n6, n4, n8);
        }
        return n9 != 0;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        return this.modelBlock.canPlaceBlockAt(world, blockPos);
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return this.modelBlock.getMapColor(this.modelState);
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.onNeighborBlockChange(world, blockPos, this.modelState, Blocks.air);
        this.modelBlock.onBlockAdded(world, blockPos, this.modelState);
    }
    
    public enum EnumHalf implements IStringSerializable
    {
        private final String name;
        private static final EnumHalf[] ENUM$VALUES;
        private static final String[] I;
        
        TOP(EnumHalf.I["".length()], "".length(), EnumHalf.I[" ".length()]), 
        BOTTOM(EnumHalf.I["  ".length()], " ".length(), EnumHalf.I["   ".length()]);
        
        private static void I() {
            (I = new String[0x0 ^ 0x4])["".length()] = I("\u0004\u0016(", "PYxmb");
            EnumHalf.I[" ".length()] = I("1\u00009", "EoIEy");
            EnumHalf.I["  ".length()] = I("/;\u001a#\r ", "mtNwB");
            EnumHalf.I["   ".length()] = I("\t\u0002\u0007\u000e\u0018\u0006", "kmszw");
        }
        
        private EnumHalf(final String s, final int n, final String name) {
            this.name = name;
        }
        
        @Override
        public String getName() {
            return this.name;
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
                if (2 != 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        static {
            I();
            final EnumHalf[] enum$VALUES = new EnumHalf["  ".length()];
            enum$VALUES["".length()] = EnumHalf.TOP;
            enum$VALUES[" ".length()] = EnumHalf.BOTTOM;
            ENUM$VALUES = enum$VALUES;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
    }
    
    public enum EnumShape implements IStringSerializable
    {
        INNER_RIGHT(EnumShape.I[0x4A ^ 0x4E], "  ".length(), EnumShape.I[0x2B ^ 0x2E]), 
        STRAIGHT(EnumShape.I["".length()], "".length(), EnumShape.I[" ".length()]);
        
        private static final EnumShape[] ENUM$VALUES;
        private final String name;
        
        OUTER_LEFT(EnumShape.I[0xC1 ^ 0xC7], "   ".length(), EnumShape.I[0x63 ^ 0x64]), 
        OUTER_RIGHT(EnumShape.I[0x79 ^ 0x71], 0x98 ^ 0x9C, EnumShape.I[0xA5 ^ 0xAC]), 
        INNER_LEFT(EnumShape.I["  ".length()], " ".length(), EnumShape.I["   ".length()]);
        
        private static final String[] I;
        
        private static void I() {
            (I = new String[0xCD ^ 0xC7])["".length()] = I("%:\u0019$%1&\u001f", "vnKel");
            EnumShape.I[" ".length()] = I("\u0016\u0018\u00158\u0006\u0002\u0004\u0013", "elgYo");
            EnumShape.I["  ".length()] = I("+\u001f\n\u001c\u0011=\u001d\u0001\u001f\u0017", "bQDYC");
            EnumShape.I["   ".length()] = I("\u0001?\u0004+\u001e7=\u000f(\u0018", "hQjNl");
            EnumShape.I[0x77 ^ 0x73] = I("\u0011\u0003\u0006\n#\u0007\u001f\u0001\b9\f", "XMHOq");
            EnumShape.I[0x6F ^ 0x6A] = I("\r\u00009\u0007>;\u001c>\u0005$\u0010", "dnWbL");
            EnumShape.I[0x1D ^ 0x1B] = I("\u0003\u0005\u001f\u0002\u0010\u0013\u001c\u000e\u0001\u0016", "LPKGB");
            EnumShape.I[0x95 ^ 0x92] = I("\u0007\u000f\u001e\u001c07\u0016\u000f\u001f6", "hzjyB");
            EnumShape.I[0x54 ^ 0x5C] = I("\u000e\u0012\u00184\u001f\u001e\u0015\u00056\u0005\u0015", "AGLqM");
            EnumShape.I[0xB4 ^ 0xBD] = I("'1'*\u0017\u00176:(\r<", "HDSOe");
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        private EnumShape(final String s, final int n, final String name) {
            this.name = name;
        }
        
        @Override
        public String getName() {
            return this.name;
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
                if (0 <= -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        static {
            I();
            final EnumShape[] enum$VALUES = new EnumShape[0x9A ^ 0x9F];
            enum$VALUES["".length()] = EnumShape.STRAIGHT;
            enum$VALUES[" ".length()] = EnumShape.INNER_LEFT;
            enum$VALUES["  ".length()] = EnumShape.INNER_RIGHT;
            enum$VALUES["   ".length()] = EnumShape.OUTER_LEFT;
            enum$VALUES[0xBC ^ 0xB8] = EnumShape.OUTER_RIGHT;
            ENUM$VALUES = enum$VALUES;
        }
    }
}
