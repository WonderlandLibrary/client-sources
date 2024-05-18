package net.minecraft.block;

import java.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.dispenser.*;
import net.minecraft.inventory.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import net.minecraft.stats.*;

public class BlockDispenser extends BlockContainer
{
    public static final PropertyDirection FACING;
    public static final PropertyBool TRIGGERED;
    public static final RegistryDefaulted<Item, IBehaviorDispenseItem> dispenseBehaviorRegistry;
    protected Random rand;
    private static final String[] I;
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = "".length() | blockState.getValue((IProperty<EnumFacing>)BlockDispenser.FACING).getIndex();
        if (blockState.getValue((IProperty<Boolean>)BlockDispenser.TRIGGERED)) {
            n |= (0x40 ^ 0x48);
        }
        return n;
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (!world.isRemote) {
            this.dispense(world, blockPos);
        }
    }
    
    @Override
    public int getComparatorInputOverride(final World world, final BlockPos blockPos) {
        return Container.calcRedstone(world.getTileEntity(blockPos));
    }
    
    @Override
    public int tickRate(final World world) {
        return 0x22 ^ 0x26;
    }
    
    public static EnumFacing getFacing(final int n) {
        return EnumFacing.getFront(n & (0x95 ^ 0x92));
    }
    
    @Override
    public int getRenderType() {
        return "   ".length();
    }
    
    protected BlockDispenser() {
        super(Material.rock);
        this.rand = new Random();
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockDispenser.FACING, EnumFacing.NORTH).withProperty((IProperty<Comparable>)BlockDispenser.TRIGGERED, (boolean)("".length() != 0)));
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockDispenser.FACING, BlockPistonBase.getFacingFromEntity(world, blockPos, entityLivingBase)).withProperty((IProperty<Comparable>)BlockDispenser.TRIGGERED, "".length() != 0);
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        int n;
        if (!world.isBlockPowered(blockPos) && !world.isBlockPowered(blockPos.up())) {
            n = "".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        final int n2 = n;
        final boolean booleanValue = blockState.getValue((IProperty<Boolean>)BlockDispenser.TRIGGERED);
        if (n2 != 0 && !booleanValue) {
            world.scheduleUpdate(blockPos, this, this.tickRate(world));
            world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockDispenser.TRIGGERED, (boolean)(" ".length() != 0)), 0xC2 ^ 0xC6);
            "".length();
            if (0 == 4) {
                throw null;
            }
        }
        else if (n2 == 0 && booleanValue) {
            world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockDispenser.TRIGGERED, (boolean)("".length() != 0)), 0x74 ^ 0x70);
        }
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return " ".length() != 0;
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        super.onBlockAdded(world, blockPos, blockState);
        this.setDefaultDirection(world, blockPos, blockState);
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockDispenser.FACING, BlockPistonBase.getFacingFromEntity(world, blockPos, entityLivingBase)), "  ".length());
        if (itemStack.hasDisplayName()) {
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityDispenser) {
                ((TileEntityDispenser)tileEntity).setCustomName(itemStack.getDisplayName());
            }
        }
    }
    
    protected void dispense(final World world, final BlockPos blockPos) {
        final BlockSourceImpl blockSourceImpl = new BlockSourceImpl(world, blockPos);
        final TileEntityDispenser tileEntityDispenser = blockSourceImpl.getBlockTileEntity();
        if (tileEntityDispenser != null) {
            final int dispenseSlot = tileEntityDispenser.getDispenseSlot();
            if (dispenseSlot < 0) {
                world.playAuxSFX(418 + 251 + 185 + 147, blockPos, "".length());
                "".length();
                if (1 <= 0) {
                    throw null;
                }
            }
            else {
                final ItemStack stackInSlot = tileEntityDispenser.getStackInSlot(dispenseSlot);
                final IBehaviorDispenseItem behavior = this.getBehavior(stackInSlot);
                if (behavior != IBehaviorDispenseItem.itemDispenseBehaviorProvider) {
                    final ItemStack dispense = behavior.dispense(blockSourceImpl, stackInSlot);
                    final TileEntityDispenser tileEntityDispenser2 = tileEntityDispenser;
                    final int n = dispenseSlot;
                    ItemStack itemStack;
                    if (dispense.stackSize <= 0) {
                        itemStack = null;
                        "".length();
                        if (0 >= 3) {
                            throw null;
                        }
                    }
                    else {
                        itemStack = dispense;
                    }
                    tileEntityDispenser2.setInventorySlotContents(n, itemStack);
                }
            }
        }
    }
    
    @Override
    public IBlockState getStateForEntityRender(final IBlockState blockState) {
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockDispenser.FACING, EnumFacing.SOUTH);
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityDispenser();
    }
    
    private void setDefaultDirection(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!world.isRemote) {
            EnumFacing enumFacing = blockState.getValue((IProperty<EnumFacing>)BlockDispenser.FACING);
            final boolean fullBlock = world.getBlockState(blockPos.north()).getBlock().isFullBlock();
            final boolean fullBlock2 = world.getBlockState(blockPos.south()).getBlock().isFullBlock();
            if (enumFacing == EnumFacing.NORTH && fullBlock && !fullBlock2) {
                enumFacing = EnumFacing.SOUTH;
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else if (enumFacing == EnumFacing.SOUTH && fullBlock2 && !fullBlock) {
                enumFacing = EnumFacing.NORTH;
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else {
                final boolean fullBlock3 = world.getBlockState(blockPos.west()).getBlock().isFullBlock();
                final boolean fullBlock4 = world.getBlockState(blockPos.east()).getBlock().isFullBlock();
                if (enumFacing == EnumFacing.WEST && fullBlock3 && !fullBlock4) {
                    enumFacing = EnumFacing.EAST;
                    "".length();
                    if (4 <= 3) {
                        throw null;
                    }
                }
                else if (enumFacing == EnumFacing.EAST && fullBlock4 && !fullBlock3) {
                    enumFacing = EnumFacing.WEST;
                }
            }
            world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockDispenser.FACING, enumFacing).withProperty((IProperty<Comparable>)BlockDispenser.TRIGGERED, (boolean)("".length() != 0)), "  ".length());
        }
    }
    
    public static IPosition getDispensePosition(final IBlockSource blockSource) {
        final EnumFacing facing = getFacing(blockSource.getBlockMetadata());
        return new PositionImpl(blockSource.getX() + 0.7 * facing.getFrontOffsetX(), blockSource.getY() + 0.7 * facing.getFrontOffsetY(), blockSource.getZ() + 0.7 * facing.getFrontOffsetZ());
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final IBlockState withProperty = this.getDefaultState().withProperty((IProperty<Comparable>)BlockDispenser.FACING, getFacing(n));
        final PropertyBool triggered = BlockDispenser.TRIGGERED;
        int n2;
        if ((n & (0x3E ^ 0x36)) > 0) {
            n2 = " ".length();
            "".length();
            if (0 == 3) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        return withProperty.withProperty((IProperty<Comparable>)triggered, n2 != 0);
    }
    
    protected IBehaviorDispenseItem getBehavior(final ItemStack itemStack) {
        final RegistryDefaulted<Item, IBehaviorDispenseItem> dispenseBehaviorRegistry = BlockDispenser.dispenseBehaviorRegistry;
        Item item;
        if (itemStack == null) {
            item = null;
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else {
            item = itemStack.getItem();
        }
        return dispenseBehaviorRegistry.getObject(item);
    }
    
    static {
        I();
        FACING = PropertyDirection.create(BlockDispenser.I["".length()]);
        TRIGGERED = PropertyBool.create(BlockDispenser.I[" ".length()]);
        dispenseBehaviorRegistry = new RegistryDefaulted<Item, IBehaviorDispenseItem>(new BehaviorDefaultDispenseItem());
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityDispenser) {
            InventoryHelper.dropInventoryItems(world, blockPos, (IInventory)tileEntity);
            world.updateComparatorOutputLevel(blockPos, this);
        }
        super.breakBlock(world, blockPos, blockState);
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty["  ".length()];
        array["".length()] = BlockDispenser.FACING;
        array[" ".length()] = BlockDispenser.TRIGGERED;
        return new BlockState(this, array);
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u0007\u0012$+&\u0006", "asGBH");
        BlockDispenser.I[" ".length()] = I(" \u0017\n)01\u0017\u0006*", "TecNW");
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return " ".length() != 0;
        }
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityDispenser) {
            entityPlayer.displayGUIChest((IInventory)tileEntity);
            if (tileEntity instanceof TileEntityDropper) {
                entityPlayer.triggerAchievement(StatList.field_181731_O);
                "".length();
                if (2 == 1) {
                    throw null;
                }
            }
            else {
                entityPlayer.triggerAchievement(StatList.field_181733_Q);
            }
        }
        return " ".length() != 0;
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
            if (-1 >= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
}
