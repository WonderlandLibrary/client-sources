package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import com.google.common.base.*;
import net.minecraft.world.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.stats.*;

public class BlockChest extends BlockContainer
{
    private static final String[] I;
    public final int chestType;
    public static final PropertyDirection FACING;
    
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
            if (3 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockChest.FACING, entityLivingBase.getHorizontalFacing());
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        super.onNeighborBlockChange(world, blockPos, blockState, block);
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityChest) {
            tileEntity.updateContainingBlockInfo();
        }
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        if (blockAccess.getBlockState(blockPos.north()).getBlock() == this) {
            this.setBlockBounds(0.0625f, 0.0f, 0.0f, 0.9375f, 0.875f, 0.9375f);
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else if (blockAccess.getBlockState(blockPos.south()).getBlock() == this) {
            this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.875f, 1.0f);
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else if (blockAccess.getBlockState(blockPos.west()).getBlock() == this) {
            this.setBlockBounds(0.0f, 0.0f, 0.0625f, 0.9375f, 0.875f, 0.9375f);
            "".length();
            if (4 < 2) {
                throw null;
            }
        }
        else if (blockAccess.getBlockState(blockPos.east()).getBlock() == this) {
            this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 1.0f, 0.875f, 0.9375f);
            "".length();
            if (3 == -1) {
                throw null;
            }
        }
        else {
            this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.875f, 0.9375f);
        }
    }
    
    @Override
    public int getComparatorInputOverride(final World world, final BlockPos blockPos) {
        return Container.calcRedstoneFromInventory(this.getLockableContainer(world, blockPos));
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockChest.FACING;
        return new BlockState(this, array);
    }
    
    private boolean isOcelotSittingOnChest(final World world, final BlockPos blockPos) {
        final Iterator<Entity> iterator = world.getEntitiesWithinAABB((Class<? extends Entity>)EntityOcelot.class, new AxisAlignedBB(blockPos.getX(), blockPos.getY() + " ".length(), blockPos.getZ(), blockPos.getX() + " ".length(), blockPos.getY() + "  ".length(), blockPos.getZ() + " ".length())).iterator();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            if (((EntityOcelot)iterator.next()).isSitting()) {
                return " ".length() != 0;
            }
        }
        return "".length() != 0;
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityChest();
    }
    
    private boolean isBlocked(final World world, final BlockPos blockPos) {
        if (!this.isBelowSolidBlock(world, blockPos) && !this.isOcelotSittingOnChest(world, blockPos)) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    public IBlockState checkForSurroundingChests(final World world, final BlockPos blockPos, IBlockState withProperty) {
        if (world.isRemote) {
            return withProperty;
        }
        final IBlockState blockState = world.getBlockState(blockPos.north());
        final IBlockState blockState2 = world.getBlockState(blockPos.south());
        final IBlockState blockState3 = world.getBlockState(blockPos.west());
        final IBlockState blockState4 = world.getBlockState(blockPos.east());
        EnumFacing enumFacing = withProperty.getValue((IProperty<EnumFacing>)BlockChest.FACING);
        final Block block = blockState.getBlock();
        final Block block2 = blockState2.getBlock();
        final Block block3 = blockState3.getBlock();
        final Block block4 = blockState4.getBlock();
        if (block != this && block2 != this) {
            final boolean fullBlock = block.isFullBlock();
            final boolean fullBlock2 = block2.isFullBlock();
            if (block3 == this || block4 == this) {
                BlockPos blockPos2;
                if (block3 == this) {
                    blockPos2 = blockPos.west();
                    "".length();
                    if (1 >= 3) {
                        throw null;
                    }
                }
                else {
                    blockPos2 = blockPos.east();
                }
                final BlockPos blockPos3 = blockPos2;
                final IBlockState blockState5 = world.getBlockState(blockPos3.north());
                final IBlockState blockState6 = world.getBlockState(blockPos3.south());
                enumFacing = EnumFacing.SOUTH;
                EnumFacing enumFacing2;
                if (block3 == this) {
                    enumFacing2 = blockState3.getValue((IProperty<EnumFacing>)BlockChest.FACING);
                    "".length();
                    if (2 < 0) {
                        throw null;
                    }
                }
                else {
                    enumFacing2 = blockState4.getValue((IProperty<EnumFacing>)BlockChest.FACING);
                }
                if (enumFacing2 == EnumFacing.NORTH) {
                    enumFacing = EnumFacing.NORTH;
                }
                final Block block5 = blockState5.getBlock();
                final Block block6 = blockState6.getBlock();
                if ((fullBlock || block5.isFullBlock()) && !fullBlock2 && !block6.isFullBlock()) {
                    enumFacing = EnumFacing.SOUTH;
                }
                if ((fullBlock2 || block6.isFullBlock()) && !fullBlock && !block5.isFullBlock()) {
                    enumFacing = EnumFacing.NORTH;
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                }
            }
        }
        else {
            BlockPos blockPos4;
            if (block == this) {
                blockPos4 = blockPos.north();
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            else {
                blockPos4 = blockPos.south();
            }
            final BlockPos blockPos5 = blockPos4;
            final IBlockState blockState7 = world.getBlockState(blockPos5.west());
            final IBlockState blockState8 = world.getBlockState(blockPos5.east());
            enumFacing = EnumFacing.EAST;
            EnumFacing enumFacing3;
            if (block == this) {
                enumFacing3 = blockState.getValue((IProperty<EnumFacing>)BlockChest.FACING);
                "".length();
                if (3 < 0) {
                    throw null;
                }
            }
            else {
                enumFacing3 = blockState2.getValue((IProperty<EnumFacing>)BlockChest.FACING);
            }
            if (enumFacing3 == EnumFacing.WEST) {
                enumFacing = EnumFacing.WEST;
            }
            final Block block7 = blockState7.getBlock();
            final Block block8 = blockState8.getBlock();
            if ((block3.isFullBlock() || block7.isFullBlock()) && !block4.isFullBlock() && !block8.isFullBlock()) {
                enumFacing = EnumFacing.EAST;
            }
            if ((block4.isFullBlock() || block8.isFullBlock()) && !block3.isFullBlock() && !block7.isFullBlock()) {
                enumFacing = EnumFacing.WEST;
            }
        }
        withProperty = withProperty.withProperty((IProperty<Comparable>)BlockChest.FACING, enumFacing);
        world.setBlockState(blockPos, withProperty, "   ".length());
        return withProperty;
    }
    
    @Override
    public int getStrongPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        int n;
        if (enumFacing == EnumFacing.UP) {
            n = this.getWeakPower(blockAccess, blockPos, blockState, enumFacing);
            "".length();
            if (-1 == 0) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n;
    }
    
    @Override
    public int getRenderType() {
        return "  ".length();
    }
    
    public IBlockState correctFacing(final World world, final BlockPos blockPos, final IBlockState blockState) {
        EnumFacing enumFacing = null;
        final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            final EnumFacing enumFacing2 = iterator.next();
            final IBlockState blockState2 = world.getBlockState(blockPos.offset(enumFacing2));
            if (blockState2.getBlock() == this) {
                return blockState;
            }
            if (!blockState2.getBlock().isFullBlock()) {
                continue;
            }
            if (enumFacing != null) {
                enumFacing = null;
                "".length();
                if (3 == -1) {
                    throw null;
                }
                break;
            }
            else {
                enumFacing = enumFacing2;
            }
        }
        if (enumFacing != null) {
            return blockState.withProperty((IProperty<Comparable>)BlockChest.FACING, enumFacing.getOpposite());
        }
        EnumFacing enumFacing3 = blockState.getValue((IProperty<EnumFacing>)BlockChest.FACING);
        if (world.getBlockState(blockPos.offset(enumFacing3)).getBlock().isFullBlock()) {
            enumFacing3 = enumFacing3.getOpposite();
        }
        if (world.getBlockState(blockPos.offset(enumFacing3)).getBlock().isFullBlock()) {
            enumFacing3 = enumFacing3.rotateY();
        }
        if (world.getBlockState(blockPos.offset(enumFacing3)).getBlock().isFullBlock()) {
            enumFacing3 = enumFacing3.getOpposite();
        }
        return blockState.withProperty((IProperty<Comparable>)BlockChest.FACING, enumFacing3);
    }
    
    protected BlockChest(final int chestType) {
        super(Material.wood);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockChest.FACING, EnumFacing.NORTH));
        this.chestType = chestType;
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.875f, 0.9375f);
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, IBlockState withProperty, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        final EnumFacing opposite = EnumFacing.getHorizontal(MathHelper.floor_double(entityLivingBase.rotationYaw * 4.0f / 360.0f + 0.5) & "   ".length()).getOpposite();
        withProperty = withProperty.withProperty((IProperty<Comparable>)BlockChest.FACING, opposite);
        final BlockPos north = blockPos.north();
        final BlockPos south = blockPos.south();
        final BlockPos west = blockPos.west();
        final BlockPos east = blockPos.east();
        int n;
        if (this == world.getBlockState(north).getBlock()) {
            n = " ".length();
            "".length();
            if (2 == 1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        final int n2 = n;
        int n3;
        if (this == world.getBlockState(south).getBlock()) {
            n3 = " ".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            n3 = "".length();
        }
        final int n4 = n3;
        int n5;
        if (this == world.getBlockState(west).getBlock()) {
            n5 = " ".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n5 = "".length();
        }
        final int n6 = n5;
        int n7;
        if (this == world.getBlockState(east).getBlock()) {
            n7 = " ".length();
            "".length();
            if (4 == 0) {
                throw null;
            }
        }
        else {
            n7 = "".length();
        }
        final int n8 = n7;
        if (n2 == 0 && n4 == 0 && n6 == 0 && n8 == 0) {
            world.setBlockState(blockPos, withProperty, "   ".length());
            "".length();
            if (2 == 1) {
                throw null;
            }
        }
        else if (opposite.getAxis() != EnumFacing.Axis.X || (n2 == 0 && n4 == 0)) {
            if (opposite.getAxis() == EnumFacing.Axis.Z && (n6 != 0 || n8 != 0)) {
                if (n6 != 0) {
                    world.setBlockState(west, withProperty, "   ".length());
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                }
                else {
                    world.setBlockState(east, withProperty, "   ".length());
                }
                world.setBlockState(blockPos, withProperty, "   ".length());
                "".length();
                if (3 < 0) {
                    throw null;
                }
            }
        }
        else {
            if (n2 != 0) {
                world.setBlockState(north, withProperty, "   ".length());
                "".length();
                if (0 >= 1) {
                    throw null;
                }
            }
            else {
                world.setBlockState(south, withProperty, "   ".length());
            }
            world.setBlockState(blockPos, withProperty, "   ".length());
        }
        if (itemStack.hasDisplayName()) {
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityChest) {
                ((TileEntityChest)tileEntity).setCustomName(itemStack.getDisplayName());
            }
        }
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof IInventory) {
            InventoryHelper.dropInventoryItems(world, blockPos, (IInventory)tileEntity);
            world.updateComparatorOutputLevel(blockPos, this);
        }
        super.breakBlock(world, blockPos, blockState);
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        int length = "".length();
        final BlockPos west = blockPos.west();
        final BlockPos east = blockPos.east();
        final BlockPos north = blockPos.north();
        final BlockPos south = blockPos.south();
        if (world.getBlockState(west).getBlock() == this) {
            if (this.isDoubleChest(world, west)) {
                return "".length() != 0;
            }
            ++length;
        }
        if (world.getBlockState(east).getBlock() == this) {
            if (this.isDoubleChest(world, east)) {
                return "".length() != 0;
            }
            ++length;
        }
        if (world.getBlockState(north).getBlock() == this) {
            if (this.isDoubleChest(world, north)) {
                return "".length() != 0;
            }
            ++length;
        }
        if (world.getBlockState(south).getBlock() == this) {
            if (this.isDoubleChest(world, south)) {
                return "".length() != 0;
            }
            ++length;
        }
        if (length <= " ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    static {
        I();
        FACING = PropertyDirection.create(BlockChest.I["".length()], (Predicate<EnumFacing>)EnumFacing.Plane.HORIZONTAL);
    }
    
    @Override
    public int getWeakPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        if (!this.canProvidePower()) {
            return "".length();
        }
        int n = "".length();
        final TileEntity tileEntity = blockAccess.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityChest) {
            n = ((TileEntityChest)tileEntity).numPlayersUsing;
        }
        return MathHelper.clamp_int(n, "".length(), 0x48 ^ 0x47);
    }
    
    private boolean isDoubleChest(final World world, final BlockPos blockPos) {
        if (world.getBlockState(blockPos).getBlock() != this) {
            return "".length() != 0;
        }
        final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
        "".length();
        if (0 == 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            if (world.getBlockState(blockPos.offset(iterator.next())).getBlock() == this) {
                return " ".length() != 0;
            }
        }
        return "".length() != 0;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    public ILockableContainer getLockableContainer(final World world, final BlockPos blockPos) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (!(tileEntity instanceof TileEntityChest)) {
            return null;
        }
        IInventory inventory = (TileEntityChest)tileEntity;
        if (this.isBlocked(world, blockPos)) {
            return null;
        }
        final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            final EnumFacing enumFacing = iterator.next();
            final BlockPos offset = blockPos.offset(enumFacing);
            if (world.getBlockState(offset).getBlock() == this) {
                if (this.isBlocked(world, offset)) {
                    return null;
                }
                final TileEntity tileEntity2 = world.getTileEntity(offset);
                if (!(tileEntity2 instanceof TileEntityChest)) {
                    continue;
                }
                if (enumFacing != EnumFacing.WEST && enumFacing != EnumFacing.NORTH) {
                    inventory = new InventoryLargeChest(BlockChest.I[" ".length()], (ILockableContainer)inventory, (ILockableContainer)tileEntity2);
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                    continue;
                }
                else {
                    inventory = new InventoryLargeChest(BlockChest.I["  ".length()], (ILockableContainer)tileEntity2, (ILockableContainer)inventory);
                }
            }
        }
        return (ILockableContainer)inventory;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        EnumFacing enumFacing = EnumFacing.getFront(n);
        if (enumFacing.getAxis() == EnumFacing.Axis.Y) {
            enumFacing = EnumFacing.NORTH;
        }
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockChest.FACING, enumFacing);
    }
    
    private boolean isBelowSolidBlock(final World world, final BlockPos blockPos) {
        return world.getBlockState(blockPos.up()).getBlock().isNormalCube();
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.checkForSurroundingChests(world, blockPos, blockState);
        final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
        "".length();
        if (false) {
            throw null;
        }
        while (iterator.hasNext()) {
            final BlockPos offset = blockPos.offset(iterator.next());
            final IBlockState blockState2 = world.getBlockState(offset);
            if (blockState2.getBlock() == this) {
                this.checkForSurroundingChests(world, offset, blockState2);
            }
        }
    }
    
    @Override
    public boolean canProvidePower() {
        if (this.chestType == " ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue((IProperty<EnumFacing>)BlockChest.FACING).getIndex();
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return " ".length() != 0;
        }
        final ILockableContainer lockableContainer = this.getLockableContainer(world, blockPos);
        if (lockableContainer != null) {
            entityPlayer.displayGUIChest(lockableContainer);
            if (this.chestType == 0) {
                entityPlayer.triggerAchievement(StatList.field_181723_aa);
                "".length();
                if (2 == -1) {
                    throw null;
                }
            }
            else if (this.chestType == " ".length()) {
                entityPlayer.triggerAchievement(StatList.field_181737_U);
            }
        }
        return " ".length() != 0;
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("47\n&85", "RViOV");
        BlockChest.I[" ".length()] = I(")\n\u001c\u001b\u0005#\u000b\u0017\u001dJ)\r\u0017\u001c\u0010\u000e\n\u0007\r\b/", "Jerod");
        BlockChest.I["  ".length()] = I("\u001b+\u0017\u0007\u001b\u0011*\u001c\u0001T\u001b,\u001c\u0000\u000e<+\f\u0011\u0016\u001d", "xDysz");
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return " ".length() != 0;
    }
}
