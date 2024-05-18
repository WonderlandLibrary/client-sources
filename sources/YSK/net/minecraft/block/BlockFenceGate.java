package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;

public class BlockFenceGate extends BlockDirectional
{
    private static final String[] I;
    public static final PropertyBool OPEN;
    public static final PropertyBool IN_WALL;
    public static final PropertyBool POWERED;
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        return " ".length() != 0;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        if (blockAccess.getBlockState(blockPos).getValue((IProperty<EnumFacing>)BlockFenceGate.FACING).getAxis() == EnumFacing.Axis.Z) {
            this.setBlockBounds(0.0f, 0.0f, 0.375f, 1.0f, 1.0f, 0.625f);
            "".length();
            if (1 < 1) {
                throw null;
            }
        }
        else {
            this.setBlockBounds(0.375f, 0.0f, 0.0f, 0.625f, 1.0f, 1.0f);
        }
    }
    
    static {
        I();
        OPEN = PropertyBool.create(BlockFenceGate.I["".length()]);
        POWERED = PropertyBool.create(BlockFenceGate.I[" ".length()]);
        IN_WALL = PropertyBool.create(BlockFenceGate.I["  ".length()]);
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        if (!world.isRemote) {
            final boolean blockPowered = world.isBlockPowered(blockPos);
            if (blockPowered || block.canProvidePower()) {
                if (blockPowered && !blockState.getValue((IProperty<Boolean>)BlockFenceGate.OPEN) && !blockState.getValue((IProperty<Boolean>)BlockFenceGate.POWERED)) {
                    world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockFenceGate.OPEN, " ".length() != 0).withProperty((IProperty<Comparable>)BlockFenceGate.POWERED, (boolean)(" ".length() != 0)), "  ".length());
                    world.playAuxSFXAtEntity(null, 932 + 757 - 1629 + 943, blockPos, "".length());
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
                else if (!blockPowered && blockState.getValue((IProperty<Boolean>)BlockFenceGate.OPEN) && blockState.getValue((IProperty<Boolean>)BlockFenceGate.POWERED)) {
                    world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockFenceGate.OPEN, "".length() != 0).withProperty((IProperty<Comparable>)BlockFenceGate.POWERED, (boolean)("".length() != 0)), "  ".length());
                    world.playAuxSFXAtEntity(null, 362 + 299 + 176 + 169, blockPos, "".length());
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                }
                else if (blockPowered != blockState.getValue((IProperty<Boolean>)BlockFenceGate.POWERED)) {
                    world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockFenceGate.POWERED, blockPowered), "  ".length());
                }
            }
        }
    }
    
    public BlockFenceGate(final BlockPlanks.EnumType enumType) {
        super(Material.wood, enumType.func_181070_c());
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockFenceGate.OPEN, "".length() != 0).withProperty((IProperty<Comparable>)BlockFenceGate.POWERED, "".length() != 0).withProperty((IProperty<Comparable>)BlockFenceGate.IN_WALL, (boolean)("".length() != 0)));
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    public boolean isPassable(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return blockAccess.getBlockState(blockPos).getValue((IProperty<Boolean>)BlockFenceGate.OPEN);
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[0x48 ^ 0x4C];
        array["".length()] = BlockFenceGate.FACING;
        array[" ".length()] = BlockFenceGate.OPEN;
        array["  ".length()] = BlockFenceGate.POWERED;
        array["   ".length()] = BlockFenceGate.IN_WALL;
        return new BlockState(this, array);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (blockState.getValue((IProperty<Boolean>)BlockFenceGate.OPEN)) {
            return null;
        }
        AxisAlignedBB axisAlignedBB;
        if (blockState.getValue((IProperty<EnumFacing>)BlockFenceGate.FACING).getAxis() == EnumFacing.Axis.Z) {
            axisAlignedBB = new AxisAlignedBB(blockPos.getX(), blockPos.getY(), blockPos.getZ() + 0.375f, blockPos.getX() + " ".length(), blockPos.getY() + 1.5f, blockPos.getZ() + 0.625f);
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        else {
            axisAlignedBB = new AxisAlignedBB(blockPos.getX() + 0.375f, blockPos.getY(), blockPos.getZ(), blockPos.getX() + 0.625f, blockPos.getY() + 1.5f, blockPos.getZ() + " ".length());
        }
        return axisAlignedBB;
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
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
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockFenceGate.FACING, entityLivingBase.getHorizontalFacing()).withProperty((IProperty<Comparable>)BlockFenceGate.OPEN, "".length() != 0).withProperty((IProperty<Comparable>)BlockFenceGate.POWERED, "".length() != 0).withProperty((IProperty<Comparable>)BlockFenceGate.IN_WALL, "".length() != 0);
    }
    
    @Override
    public IBlockState getActualState(IBlockState withProperty, final IBlockAccess blockAccess, final BlockPos blockPos) {
        final EnumFacing.Axis axis = withProperty.getValue((IProperty<EnumFacing>)BlockFenceGate.FACING).getAxis();
        if ((axis == EnumFacing.Axis.Z && (blockAccess.getBlockState(blockPos.west()).getBlock() == Blocks.cobblestone_wall || blockAccess.getBlockState(blockPos.east()).getBlock() == Blocks.cobblestone_wall)) || (axis == EnumFacing.Axis.X && (blockAccess.getBlockState(blockPos.north()).getBlock() == Blocks.cobblestone_wall || blockAccess.getBlockState(blockPos.south()).getBlock() == Blocks.cobblestone_wall))) {
            withProperty = withProperty.withProperty((IProperty<Comparable>)BlockFenceGate.IN_WALL, " ".length() != 0);
        }
        return withProperty;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = "".length() | blockState.getValue((IProperty<EnumFacing>)BlockFenceGate.FACING).getHorizontalIndex();
        if (blockState.getValue((IProperty<Boolean>)BlockFenceGate.POWERED)) {
            n |= (0xAE ^ 0xA6);
        }
        if (blockState.getValue((IProperty<Boolean>)BlockFenceGate.OPEN)) {
            n |= (0x87 ^ 0x83);
        }
        return n;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final IBlockState withProperty = this.getDefaultState().withProperty((IProperty<Comparable>)BlockFenceGate.FACING, EnumFacing.getHorizontal(n));
        final PropertyBool open = BlockFenceGate.OPEN;
        int n2;
        if ((n & (0xBB ^ 0xBF)) != 0x0) {
            n2 = " ".length();
            "".length();
            if (2 < 0) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        final IBlockState withProperty2 = withProperty.withProperty((IProperty<Comparable>)open, n2 != 0);
        final PropertyBool powered = BlockFenceGate.POWERED;
        int n3;
        if ((n & (0x89 ^ 0x81)) != 0x0) {
            n3 = " ".length();
            "".length();
            if (0 == 3) {
                throw null;
            }
        }
        else {
            n3 = "".length();
        }
        return withProperty2.withProperty((IProperty<Comparable>)powered, n3 != 0);
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (blockState.getValue((IProperty<Boolean>)BlockFenceGate.OPEN)) {
            blockState = blockState.withProperty((IProperty<Comparable>)BlockFenceGate.OPEN, "".length() != 0);
            world.setBlockState(blockPos, blockState, "  ".length());
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        else {
            final EnumFacing fromAngle = EnumFacing.fromAngle(entityPlayer.rotationYaw);
            if (blockState.getValue((IProperty<Comparable>)BlockFenceGate.FACING) == fromAngle.getOpposite()) {
                blockState = blockState.withProperty((IProperty<Comparable>)BlockFenceGate.FACING, fromAngle);
            }
            blockState = blockState.withProperty((IProperty<Comparable>)BlockFenceGate.OPEN, " ".length() != 0);
            world.setBlockState(blockPos, blockState, "  ".length());
        }
        int n4;
        if (blockState.getValue((IProperty<Boolean>)BlockFenceGate.OPEN)) {
            n4 = 179 + 560 + 35 + 229;
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else {
            n4 = 473 + 457 - 251 + 327;
        }
        world.playAuxSFXAtEntity(entityPlayer, n4, blockPos, "".length());
        return " ".length() != 0;
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("#%#\u001d", "LUFsU");
        BlockFenceGate.I[" ".length()] = I("\b=5\u0004<\u001d6", "xRBaN");
        BlockFenceGate.I["  ".length()] = I("\u0018-\u0005;.\u001d/", "qCZLO");
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        int n;
        if (world.getBlockState(blockPos.down()).getBlock().getMaterial().isSolid()) {
            n = (super.canPlaceBlockAt(world, blockPos) ? 1 : 0);
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
}
