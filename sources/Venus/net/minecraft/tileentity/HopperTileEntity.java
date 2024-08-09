/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.HopperBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ISidedInventoryProvider;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.HopperContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.IHopper;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class HopperTileEntity
extends LockableLootTileEntity
implements IHopper,
ITickableTileEntity {
    private NonNullList<ItemStack> inventory = NonNullList.withSize(5, ItemStack.EMPTY);
    private int transferCooldown = -1;
    private long tickedGameTime;

    public HopperTileEntity() {
        super(TileEntityType.HOPPER);
    }

    @Override
    public void read(BlockState blockState, CompoundNBT compoundNBT) {
        super.read(blockState, compoundNBT);
        this.inventory = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        if (!this.checkLootAndRead(compoundNBT)) {
            ItemStackHelper.loadAllItems(compoundNBT, this.inventory);
        }
        this.transferCooldown = compoundNBT.getInt("TransferCooldown");
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        super.write(compoundNBT);
        if (!this.checkLootAndWrite(compoundNBT)) {
            ItemStackHelper.saveAllItems(compoundNBT, this.inventory);
        }
        compoundNBT.putInt("TransferCooldown", this.transferCooldown);
        return compoundNBT;
    }

    @Override
    public int getSizeInventory() {
        return this.inventory.size();
    }

    @Override
    public ItemStack decrStackSize(int n, int n2) {
        this.fillWithLoot(null);
        return ItemStackHelper.getAndSplit(this.getItems(), n, n2);
    }

    @Override
    public void setInventorySlotContents(int n, ItemStack itemStack) {
        this.fillWithLoot(null);
        this.getItems().set(n, itemStack);
        if (itemStack.getCount() > this.getInventoryStackLimit()) {
            itemStack.setCount(this.getInventoryStackLimit());
        }
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.hopper");
    }

    @Override
    public void tick() {
        if (this.world != null && !this.world.isRemote) {
            --this.transferCooldown;
            this.tickedGameTime = this.world.getGameTime();
            if (!this.isOnTransferCooldown()) {
                this.setTransferCooldown(0);
                this.updateHopper(this::lambda$tick$0);
            }
        }
    }

    private boolean updateHopper(Supplier<Boolean> supplier) {
        if (this.world != null && !this.world.isRemote) {
            if (!this.isOnTransferCooldown() && this.getBlockState().get(HopperBlock.ENABLED).booleanValue()) {
                boolean bl = false;
                if (!this.isEmpty()) {
                    bl = this.transferItemsOut();
                }
                if (!this.isFull()) {
                    bl |= supplier.get().booleanValue();
                }
                if (bl) {
                    this.setTransferCooldown(8);
                    this.markDirty();
                    return false;
                }
            }
            return true;
        }
        return true;
    }

    private boolean isFull() {
        for (ItemStack itemStack : this.inventory) {
            if (!itemStack.isEmpty() && itemStack.getCount() == itemStack.getMaxStackSize()) continue;
            return true;
        }
        return false;
    }

    private boolean transferItemsOut() {
        IInventory iInventory = this.getInventoryForHopperTransfer();
        if (iInventory == null) {
            return true;
        }
        Direction direction = this.getBlockState().get(HopperBlock.FACING).getOpposite();
        if (this.isInventoryFull(iInventory, direction)) {
            return true;
        }
        for (int i = 0; i < this.getSizeInventory(); ++i) {
            if (this.getStackInSlot(i).isEmpty()) continue;
            ItemStack itemStack = this.getStackInSlot(i).copy();
            ItemStack itemStack2 = HopperTileEntity.putStackInInventoryAllSlots(this, iInventory, this.decrStackSize(i, 1), direction);
            if (itemStack2.isEmpty()) {
                iInventory.markDirty();
                return false;
            }
            this.setInventorySlotContents(i, itemStack);
        }
        return true;
    }

    private static IntStream func_213972_a(IInventory iInventory, Direction direction) {
        return iInventory instanceof ISidedInventory ? IntStream.of(((ISidedInventory)iInventory).getSlotsForFace(direction)) : IntStream.range(0, iInventory.getSizeInventory());
    }

    private boolean isInventoryFull(IInventory iInventory, Direction direction) {
        return HopperTileEntity.func_213972_a(iInventory, direction).allMatch(arg_0 -> HopperTileEntity.lambda$isInventoryFull$1(iInventory, arg_0));
    }

    private static boolean isInventoryEmpty(IInventory iInventory, Direction direction) {
        return HopperTileEntity.func_213972_a(iInventory, direction).allMatch(arg_0 -> HopperTileEntity.lambda$isInventoryEmpty$2(iInventory, arg_0));
    }

    public static boolean pullItems(IHopper iHopper) {
        IInventory iInventory = HopperTileEntity.getSourceInventory(iHopper);
        if (iInventory != null) {
            Direction direction = Direction.DOWN;
            return HopperTileEntity.isInventoryEmpty(iInventory, direction) ? false : HopperTileEntity.func_213972_a(iInventory, direction).anyMatch(arg_0 -> HopperTileEntity.lambda$pullItems$3(iHopper, iInventory, direction, arg_0));
        }
        for (ItemEntity itemEntity : HopperTileEntity.getCaptureItems(iHopper)) {
            if (!HopperTileEntity.captureItem(iHopper, itemEntity)) continue;
            return false;
        }
        return true;
    }

    private static boolean pullItemFromSlot(IHopper iHopper, IInventory iInventory, int n, Direction direction) {
        ItemStack itemStack = iInventory.getStackInSlot(n);
        if (!itemStack.isEmpty() && HopperTileEntity.canExtractItemFromSlot(iInventory, itemStack, n, direction)) {
            ItemStack itemStack2 = itemStack.copy();
            ItemStack itemStack3 = HopperTileEntity.putStackInInventoryAllSlots(iInventory, iHopper, iInventory.decrStackSize(n, 1), null);
            if (itemStack3.isEmpty()) {
                iInventory.markDirty();
                return false;
            }
            iInventory.setInventorySlotContents(n, itemStack2);
        }
        return true;
    }

    public static boolean captureItem(IInventory iInventory, ItemEntity itemEntity) {
        boolean bl = false;
        ItemStack itemStack = itemEntity.getItem().copy();
        ItemStack itemStack2 = HopperTileEntity.putStackInInventoryAllSlots(null, iInventory, itemStack, null);
        if (itemStack2.isEmpty()) {
            bl = true;
            itemEntity.remove();
        } else {
            itemEntity.setItem(itemStack2);
        }
        return bl;
    }

    public static ItemStack putStackInInventoryAllSlots(@Nullable IInventory iInventory, IInventory iInventory2, ItemStack itemStack, @Nullable Direction direction) {
        if (iInventory2 instanceof ISidedInventory && direction != null) {
            ISidedInventory iSidedInventory = (ISidedInventory)iInventory2;
            int[] nArray = iSidedInventory.getSlotsForFace(direction);
            for (int i = 0; i < nArray.length && !itemStack.isEmpty(); ++i) {
                itemStack = HopperTileEntity.insertStack(iInventory, iInventory2, itemStack, nArray[i], direction);
            }
        } else {
            int n = iInventory2.getSizeInventory();
            for (int i = 0; i < n && !itemStack.isEmpty(); ++i) {
                itemStack = HopperTileEntity.insertStack(iInventory, iInventory2, itemStack, i, direction);
            }
        }
        return itemStack;
    }

    private static boolean canInsertItemInSlot(IInventory iInventory, ItemStack itemStack, int n, @Nullable Direction direction) {
        if (!iInventory.isItemValidForSlot(n, itemStack)) {
            return true;
        }
        return !(iInventory instanceof ISidedInventory) || ((ISidedInventory)iInventory).canInsertItem(n, itemStack, direction);
    }

    private static boolean canExtractItemFromSlot(IInventory iInventory, ItemStack itemStack, int n, Direction direction) {
        return !(iInventory instanceof ISidedInventory) || ((ISidedInventory)iInventory).canExtractItem(n, itemStack, direction);
    }

    private static ItemStack insertStack(@Nullable IInventory iInventory, IInventory iInventory2, ItemStack itemStack, int n, @Nullable Direction direction) {
        ItemStack itemStack2 = iInventory2.getStackInSlot(n);
        if (HopperTileEntity.canInsertItemInSlot(iInventory2, itemStack, n, direction)) {
            int n2;
            boolean bl = false;
            boolean bl2 = iInventory2.isEmpty();
            if (itemStack2.isEmpty()) {
                iInventory2.setInventorySlotContents(n, itemStack);
                itemStack = ItemStack.EMPTY;
                bl = true;
            } else if (HopperTileEntity.canCombine(itemStack2, itemStack)) {
                int n3 = itemStack.getMaxStackSize() - itemStack2.getCount();
                n2 = Math.min(itemStack.getCount(), n3);
                itemStack.shrink(n2);
                itemStack2.grow(n2);
                boolean bl3 = bl = n2 > 0;
            }
            if (bl) {
                HopperTileEntity hopperTileEntity;
                if (bl2 && iInventory2 instanceof HopperTileEntity && !(hopperTileEntity = (HopperTileEntity)iInventory2).mayTransfer()) {
                    n2 = 0;
                    if (iInventory instanceof HopperTileEntity) {
                        HopperTileEntity hopperTileEntity2 = (HopperTileEntity)iInventory;
                        if (hopperTileEntity.tickedGameTime >= hopperTileEntity2.tickedGameTime) {
                            n2 = 1;
                        }
                    }
                    hopperTileEntity.setTransferCooldown(8 - n2);
                }
                iInventory2.markDirty();
            }
        }
        return itemStack;
    }

    @Nullable
    private IInventory getInventoryForHopperTransfer() {
        Direction direction = this.getBlockState().get(HopperBlock.FACING);
        return HopperTileEntity.getInventoryAtPosition(this.getWorld(), this.pos.offset(direction));
    }

    @Nullable
    public static IInventory getSourceInventory(IHopper iHopper) {
        return HopperTileEntity.getInventoryAtPosition(iHopper.getWorld(), iHopper.getXPos(), iHopper.getYPos() + 1.0, iHopper.getZPos());
    }

    public static List<ItemEntity> getCaptureItems(IHopper iHopper) {
        return iHopper.getCollectionArea().toBoundingBoxList().stream().flatMap(arg_0 -> HopperTileEntity.lambda$getCaptureItems$4(iHopper, arg_0)).collect(Collectors.toList());
    }

    @Nullable
    public static IInventory getInventoryAtPosition(World world, BlockPos blockPos) {
        return HopperTileEntity.getInventoryAtPosition(world, (double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5);
    }

    @Nullable
    public static IInventory getInventoryAtPosition(World world, double d, double d2, double d3) {
        Object object;
        IInventory iInventory = null;
        BlockPos blockPos = new BlockPos(d, d2, d3);
        BlockState blockState = world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        if (block instanceof ISidedInventoryProvider) {
            iInventory = ((ISidedInventoryProvider)((Object)block)).createInventory(blockState, world, blockPos);
        } else if (block.isTileEntityProvider() && (object = world.getTileEntity(blockPos)) instanceof IInventory && (iInventory = (IInventory)object) instanceof ChestTileEntity && block instanceof ChestBlock) {
            iInventory = ChestBlock.getChestInventory((ChestBlock)block, blockState, world, blockPos, true);
        }
        if (iInventory == null && !(object = world.getEntitiesInAABBexcluding(null, new AxisAlignedBB(d - 0.5, d2 - 0.5, d3 - 0.5, d + 0.5, d2 + 0.5, d3 + 0.5), EntityPredicates.HAS_INVENTORY)).isEmpty()) {
            iInventory = (IInventory)object.get(world.rand.nextInt(object.size()));
        }
        return iInventory;
    }

    private static boolean canCombine(ItemStack itemStack, ItemStack itemStack2) {
        if (itemStack.getItem() != itemStack2.getItem()) {
            return true;
        }
        if (itemStack.getDamage() != itemStack2.getDamage()) {
            return true;
        }
        if (itemStack.getCount() > itemStack.getMaxStackSize()) {
            return true;
        }
        return ItemStack.areItemStackTagsEqual(itemStack, itemStack2);
    }

    @Override
    public double getXPos() {
        return (double)this.pos.getX() + 0.5;
    }

    @Override
    public double getYPos() {
        return (double)this.pos.getY() + 0.5;
    }

    @Override
    public double getZPos() {
        return (double)this.pos.getZ() + 0.5;
    }

    private void setTransferCooldown(int n) {
        this.transferCooldown = n;
    }

    private boolean isOnTransferCooldown() {
        return this.transferCooldown > 0;
    }

    private boolean mayTransfer() {
        return this.transferCooldown > 8;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        this.inventory = nonNullList;
    }

    public void onEntityCollision(Entity entity2) {
        if (entity2 instanceof ItemEntity) {
            BlockPos blockPos = this.getPos();
            if (VoxelShapes.compare(VoxelShapes.create(entity2.getBoundingBox().offset(-blockPos.getX(), -blockPos.getY(), -blockPos.getZ())), this.getCollectionArea(), IBooleanFunction.AND)) {
                this.updateHopper(() -> this.lambda$onEntityCollision$5(entity2));
            }
        }
    }

    @Override
    protected Container createMenu(int n, PlayerInventory playerInventory) {
        return new HopperContainer(n, playerInventory, this);
    }

    private Boolean lambda$onEntityCollision$5(Entity entity2) {
        return HopperTileEntity.captureItem(this, (ItemEntity)entity2);
    }

    private static Stream lambda$getCaptureItems$4(IHopper iHopper, AxisAlignedBB axisAlignedBB) {
        return iHopper.getWorld().getEntitiesWithinAABB(ItemEntity.class, axisAlignedBB.offset(iHopper.getXPos() - 0.5, iHopper.getYPos() - 0.5, iHopper.getZPos() - 0.5), EntityPredicates.IS_ALIVE).stream();
    }

    private static boolean lambda$pullItems$3(IHopper iHopper, IInventory iInventory, Direction direction, int n) {
        return HopperTileEntity.pullItemFromSlot(iHopper, iInventory, n, direction);
    }

    private static boolean lambda$isInventoryEmpty$2(IInventory iInventory, int n) {
        return iInventory.getStackInSlot(n).isEmpty();
    }

    private static boolean lambda$isInventoryFull$1(IInventory iInventory, int n) {
        ItemStack itemStack = iInventory.getStackInSlot(n);
        return itemStack.getCount() >= itemStack.getMaxStackSize();
    }

    private Boolean lambda$tick$0() {
        return HopperTileEntity.pullItems(this);
    }
}

