/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory.container;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public abstract class Container {
    private final NonNullList<ItemStack> inventoryItemStacks = NonNullList.create();
    public final List<Slot> inventorySlots = Lists.newArrayList();
    private final List<IntReferenceHolder> trackedIntReferences = Lists.newArrayList();
    @Nullable
    private final ContainerType<?> containerType;
    public final int windowId;
    private short transactionID;
    private int dragMode = -1;
    private int dragEvent;
    private final Set<Slot> dragSlots = Sets.newHashSet();
    private final List<IContainerListener> listeners = Lists.newArrayList();
    private final Set<PlayerEntity> playerList = Sets.newHashSet();

    protected Container(@Nullable ContainerType<?> containerType, int n) {
        this.containerType = containerType;
        this.windowId = n;
    }

    protected static boolean isWithinUsableDistance(IWorldPosCallable iWorldPosCallable, PlayerEntity playerEntity, Block block) {
        return iWorldPosCallable.applyOrElse((arg_0, arg_1) -> Container.lambda$isWithinUsableDistance$0(block, playerEntity, arg_0, arg_1), true);
    }

    public ContainerType<?> getType() {
        if (this.containerType == null) {
            throw new UnsupportedOperationException("Unable to construct this menu by type");
        }
        return this.containerType;
    }

    protected static void assertInventorySize(IInventory iInventory, int n) {
        int n2 = iInventory.getSizeInventory();
        if (n2 < n) {
            throw new IllegalArgumentException("Container size " + n2 + " is smaller than expected " + n);
        }
    }

    protected static void assertIntArraySize(IIntArray iIntArray, int n) {
        int n2 = iIntArray.size();
        if (n2 < n) {
            throw new IllegalArgumentException("Container data count " + n2 + " is smaller than expected " + n);
        }
    }

    protected Slot addSlot(Slot slot) {
        slot.slotNumber = this.inventorySlots.size();
        this.inventorySlots.add(slot);
        this.inventoryItemStacks.add(ItemStack.EMPTY);
        return slot;
    }

    protected IntReferenceHolder trackInt(IntReferenceHolder intReferenceHolder) {
        this.trackedIntReferences.add(intReferenceHolder);
        return intReferenceHolder;
    }

    protected void trackIntArray(IIntArray iIntArray) {
        for (int i = 0; i < iIntArray.size(); ++i) {
            this.trackInt(IntReferenceHolder.create(iIntArray, i));
        }
    }

    public void addListener(IContainerListener iContainerListener) {
        if (!this.listeners.contains(iContainerListener)) {
            this.listeners.add(iContainerListener);
            iContainerListener.sendAllContents(this, this.getInventory());
            this.detectAndSendChanges();
        }
    }

    public void removeListener(IContainerListener iContainerListener) {
        this.listeners.remove(iContainerListener);
    }

    public NonNullList<ItemStack> getInventory() {
        NonNullList<ItemStack> nonNullList = NonNullList.create();
        for (int i = 0; i < this.inventorySlots.size(); ++i) {
            nonNullList.add(this.inventorySlots.get(i).getStack());
        }
        return nonNullList;
    }

    public void detectAndSendChanges() {
        Object object;
        int n;
        for (n = 0; n < this.inventorySlots.size(); ++n) {
            object = this.inventorySlots.get(n).getStack();
            ItemStack itemStack = this.inventoryItemStacks.get(n);
            if (ItemStack.areItemStacksEqual(itemStack, (ItemStack)object)) continue;
            ItemStack object2 = ((ItemStack)object).copy();
            this.inventoryItemStacks.set(n, object2);
            for (IContainerListener iContainerListener : this.listeners) {
                iContainerListener.sendSlotContents(this, n, object2);
            }
        }
        for (n = 0; n < this.trackedIntReferences.size(); ++n) {
            object = this.trackedIntReferences.get(n);
            if (!((IntReferenceHolder)object).isDirty()) continue;
            for (IContainerListener iContainerListener : this.listeners) {
                iContainerListener.sendWindowProperty(this, n, ((IntReferenceHolder)object).get());
            }
        }
    }

    public boolean enchantItem(PlayerEntity playerEntity, int n) {
        return true;
    }

    public Slot getSlot(int n) {
        return this.inventorySlots.get(n);
    }

    public ItemStack transferStackInSlot(PlayerEntity playerEntity, int n) {
        Slot slot = this.inventorySlots.get(n);
        return slot != null ? slot.getStack() : ItemStack.EMPTY;
    }

    public ItemStack slotClick(int n, int n2, ClickType clickType, PlayerEntity playerEntity) {
        try {
            return this.func_241440_b_(n, n2, clickType, playerEntity);
        } catch (Exception exception) {
            CrashReport crashReport = CrashReport.makeCrashReport(exception, "Container click");
            CrashReportCategory crashReportCategory = crashReport.makeCategory("Click info");
            crashReportCategory.addDetail("Menu Type", this::lambda$slotClick$1);
            crashReportCategory.addDetail("Menu Class", this::lambda$slotClick$2);
            crashReportCategory.addDetail("Slot Count", this.inventorySlots.size());
            crashReportCategory.addDetail("Slot", n);
            crashReportCategory.addDetail("Button", n2);
            crashReportCategory.addDetail("Type", (Object)clickType);
            throw new ReportedException(crashReport);
        }
    }

    private ItemStack func_241440_b_(int n, int n2, ClickType clickType, PlayerEntity playerEntity) {
        ItemStack itemStack = ItemStack.EMPTY;
        PlayerInventory playerInventory = playerEntity.inventory;
        if (clickType == ClickType.QUICK_CRAFT) {
            int n3 = this.dragEvent;
            this.dragEvent = Container.getDragEvent(n2);
            if ((n3 != 1 || this.dragEvent != 2) && n3 != this.dragEvent) {
                this.resetDrag();
            } else if (playerInventory.getItemStack().isEmpty()) {
                this.resetDrag();
            } else if (this.dragEvent == 0) {
                this.dragMode = Container.extractDragMode(n2);
                if (Container.isValidDragMode(this.dragMode, playerEntity)) {
                    this.dragEvent = 1;
                    this.dragSlots.clear();
                } else {
                    this.resetDrag();
                }
            } else if (this.dragEvent == 1) {
                Slot slot = this.inventorySlots.get(n);
                ItemStack itemStack2 = playerInventory.getItemStack();
                if (slot != null && Container.canAddItemToSlot(slot, itemStack2, true) && slot.isItemValid(itemStack2) && (this.dragMode == 2 || itemStack2.getCount() > this.dragSlots.size()) && this.canDragIntoSlot(slot)) {
                    this.dragSlots.add(slot);
                }
            } else if (this.dragEvent == 2) {
                if (!this.dragSlots.isEmpty()) {
                    ItemStack itemStack3 = playerInventory.getItemStack().copy();
                    int n4 = playerInventory.getItemStack().getCount();
                    for (Slot slot : this.dragSlots) {
                        ItemStack itemStack4 = playerInventory.getItemStack();
                        if (slot == null || !Container.canAddItemToSlot(slot, itemStack4, true) || !slot.isItemValid(itemStack4) || this.dragMode != 2 && itemStack4.getCount() < this.dragSlots.size() || !this.canDragIntoSlot(slot)) continue;
                        ItemStack itemStack5 = itemStack3.copy();
                        int n5 = slot.getHasStack() ? slot.getStack().getCount() : 0;
                        Container.computeStackSize(this.dragSlots, this.dragMode, itemStack5, n5);
                        int n6 = Math.min(itemStack5.getMaxStackSize(), slot.getItemStackLimit(itemStack5));
                        if (itemStack5.getCount() > n6) {
                            itemStack5.setCount(n6);
                        }
                        n4 -= itemStack5.getCount() - n5;
                        slot.putStack(itemStack5);
                    }
                    itemStack3.setCount(n4);
                    playerInventory.setItemStack(itemStack3);
                }
                this.resetDrag();
            } else {
                this.resetDrag();
            }
        } else if (this.dragEvent != 0) {
            this.resetDrag();
        } else if (!(clickType != ClickType.PICKUP && clickType != ClickType.QUICK_MOVE || n2 != 0 && n2 != 1)) {
            if (n == -999) {
                if (!playerInventory.getItemStack().isEmpty()) {
                    if (n2 == 0) {
                        playerEntity.dropItem(playerInventory.getItemStack(), false);
                        playerInventory.setItemStack(ItemStack.EMPTY);
                    }
                    if (n2 == 1) {
                        playerEntity.dropItem(playerInventory.getItemStack().split(1), false);
                    }
                }
            } else if (clickType == ClickType.QUICK_MOVE) {
                if (n < 0) {
                    return ItemStack.EMPTY;
                }
                Slot slot = this.inventorySlots.get(n);
                if (slot == null || !slot.canTakeStack(playerEntity)) {
                    return ItemStack.EMPTY;
                }
                ItemStack itemStack6 = this.transferStackInSlot(playerEntity, n);
                while (!itemStack6.isEmpty() && ItemStack.areItemsEqual(slot.getStack(), itemStack6)) {
                    itemStack = itemStack6.copy();
                    itemStack6 = this.transferStackInSlot(playerEntity, n);
                }
            } else {
                if (n < 0) {
                    return ItemStack.EMPTY;
                }
                Slot slot = this.inventorySlots.get(n);
                if (slot != null) {
                    ItemStack itemStack7 = slot.getStack();
                    ItemStack itemStack8 = playerInventory.getItemStack();
                    if (!itemStack7.isEmpty()) {
                        itemStack = itemStack7.copy();
                    }
                    if (itemStack7.isEmpty()) {
                        if (!itemStack8.isEmpty() && slot.isItemValid(itemStack8)) {
                            int n7;
                            int n8 = n7 = n2 == 0 ? itemStack8.getCount() : 1;
                            if (n7 > slot.getItemStackLimit(itemStack8)) {
                                n7 = slot.getItemStackLimit(itemStack8);
                            }
                            slot.putStack(itemStack8.split(n7));
                        }
                    } else if (slot.canTakeStack(playerEntity)) {
                        int n9;
                        if (itemStack8.isEmpty()) {
                            if (itemStack7.isEmpty()) {
                                slot.putStack(ItemStack.EMPTY);
                                playerInventory.setItemStack(ItemStack.EMPTY);
                            } else {
                                int n10 = n2 == 0 ? itemStack7.getCount() : (itemStack7.getCount() + 1) / 2;
                                playerInventory.setItemStack(slot.decrStackSize(n10));
                                if (itemStack7.isEmpty()) {
                                    slot.putStack(ItemStack.EMPTY);
                                }
                                slot.onTake(playerEntity, playerInventory.getItemStack());
                            }
                        } else if (slot.isItemValid(itemStack8)) {
                            if (Container.areItemsAndTagsEqual(itemStack7, itemStack8)) {
                                int n11;
                                int n12 = n11 = n2 == 0 ? itemStack8.getCount() : 1;
                                if (n11 > slot.getItemStackLimit(itemStack8) - itemStack7.getCount()) {
                                    n11 = slot.getItemStackLimit(itemStack8) - itemStack7.getCount();
                                }
                                if (n11 > itemStack8.getMaxStackSize() - itemStack7.getCount()) {
                                    n11 = itemStack8.getMaxStackSize() - itemStack7.getCount();
                                }
                                itemStack8.shrink(n11);
                                itemStack7.grow(n11);
                            } else if (itemStack8.getCount() <= slot.getItemStackLimit(itemStack8)) {
                                slot.putStack(itemStack8);
                                playerInventory.setItemStack(itemStack7);
                            }
                        } else if (itemStack8.getMaxStackSize() > 1 && Container.areItemsAndTagsEqual(itemStack7, itemStack8) && !itemStack7.isEmpty() && (n9 = itemStack7.getCount()) + itemStack8.getCount() <= itemStack8.getMaxStackSize()) {
                            itemStack8.grow(n9);
                            itemStack7 = slot.decrStackSize(n9);
                            if (itemStack7.isEmpty()) {
                                slot.putStack(ItemStack.EMPTY);
                            }
                            slot.onTake(playerEntity, playerInventory.getItemStack());
                        }
                    }
                    slot.onSlotChanged();
                }
            }
        } else if (clickType == ClickType.SWAP) {
            Slot slot = this.inventorySlots.get(n);
            ItemStack itemStack9 = playerInventory.getStackInSlot(n2);
            ItemStack itemStack10 = slot.getStack();
            if (!itemStack9.isEmpty() || !itemStack10.isEmpty()) {
                if (itemStack9.isEmpty()) {
                    if (slot.canTakeStack(playerEntity)) {
                        playerInventory.setInventorySlotContents(n2, itemStack10);
                        slot.onSwapCraft(itemStack10.getCount());
                        slot.putStack(ItemStack.EMPTY);
                        slot.onTake(playerEntity, itemStack10);
                    }
                } else if (itemStack10.isEmpty()) {
                    if (slot.isItemValid(itemStack9)) {
                        int n13 = slot.getItemStackLimit(itemStack9);
                        if (itemStack9.getCount() > n13) {
                            slot.putStack(itemStack9.split(n13));
                        } else {
                            slot.putStack(itemStack9);
                            playerInventory.setInventorySlotContents(n2, ItemStack.EMPTY);
                        }
                    }
                } else if (slot.canTakeStack(playerEntity) && slot.isItemValid(itemStack9)) {
                    int n14 = slot.getItemStackLimit(itemStack9);
                    if (itemStack9.getCount() > n14) {
                        slot.putStack(itemStack9.split(n14));
                        slot.onTake(playerEntity, itemStack10);
                        if (!playerInventory.addItemStackToInventory(itemStack10)) {
                            playerEntity.dropItem(itemStack10, false);
                        }
                    } else {
                        slot.putStack(itemStack9);
                        playerInventory.setInventorySlotContents(n2, itemStack10);
                        slot.onTake(playerEntity, itemStack10);
                    }
                }
            }
        } else if (clickType == ClickType.CLONE && playerEntity.abilities.isCreativeMode && playerInventory.getItemStack().isEmpty() && n >= 0) {
            Slot slot = this.inventorySlots.get(n);
            if (slot != null && slot.getHasStack()) {
                ItemStack itemStack11 = slot.getStack().copy();
                itemStack11.setCount(itemStack11.getMaxStackSize());
                playerInventory.setItemStack(itemStack11);
            }
        } else if (clickType == ClickType.THROW && playerInventory.getItemStack().isEmpty() && n >= 0) {
            Slot slot = this.inventorySlots.get(n);
            if (slot != null && slot.getHasStack() && slot.canTakeStack(playerEntity)) {
                ItemStack itemStack12 = slot.decrStackSize(n2 == 0 ? 1 : slot.getStack().getCount());
                slot.onTake(playerEntity, itemStack12);
                playerEntity.dropItem(itemStack12, false);
            }
        } else if (clickType == ClickType.PICKUP_ALL && n >= 0) {
            Slot slot = this.inventorySlots.get(n);
            ItemStack itemStack13 = playerInventory.getItemStack();
            if (!(itemStack13.isEmpty() || slot != null && slot.getHasStack() && slot.canTakeStack(playerEntity))) {
                int n15 = n2 == 0 ? 0 : this.inventorySlots.size() - 1;
                int n16 = n2 == 0 ? 1 : -1;
                for (int i = 0; i < 2; ++i) {
                    for (int j = n15; j >= 0 && j < this.inventorySlots.size() && itemStack13.getCount() < itemStack13.getMaxStackSize(); j += n16) {
                        Slot slot2 = this.inventorySlots.get(j);
                        if (!slot2.getHasStack() || !Container.canAddItemToSlot(slot2, itemStack13, true) || !slot2.canTakeStack(playerEntity) || !this.canMergeSlot(itemStack13, slot2)) continue;
                        ItemStack itemStack14 = slot2.getStack();
                        if (i == 0 && itemStack14.getCount() == itemStack14.getMaxStackSize()) continue;
                        int n17 = Math.min(itemStack13.getMaxStackSize() - itemStack13.getCount(), itemStack14.getCount());
                        ItemStack itemStack15 = slot2.decrStackSize(n17);
                        itemStack13.grow(n17);
                        if (itemStack15.isEmpty()) {
                            slot2.putStack(ItemStack.EMPTY);
                        }
                        slot2.onTake(playerEntity, itemStack15);
                    }
                }
            }
            this.detectAndSendChanges();
        }
        return itemStack;
    }

    public static boolean areItemsAndTagsEqual(ItemStack itemStack, ItemStack itemStack2) {
        return itemStack.getItem() == itemStack2.getItem() && ItemStack.areItemStackTagsEqual(itemStack, itemStack2);
    }

    public boolean canMergeSlot(ItemStack itemStack, Slot slot) {
        return false;
    }

    public void onContainerClosed(PlayerEntity playerEntity) {
        PlayerInventory playerInventory = playerEntity.inventory;
        if (!playerInventory.getItemStack().isEmpty()) {
            playerEntity.dropItem(playerInventory.getItemStack(), true);
            playerInventory.setItemStack(ItemStack.EMPTY);
        }
    }

    protected void clearContainer(PlayerEntity playerEntity, World world, IInventory iInventory) {
        if (!playerEntity.isAlive() || playerEntity instanceof ServerPlayerEntity && ((ServerPlayerEntity)playerEntity).hasDisconnected()) {
            for (int i = 0; i < iInventory.getSizeInventory(); ++i) {
                playerEntity.dropItem(iInventory.removeStackFromSlot(i), true);
            }
        } else {
            for (int i = 0; i < iInventory.getSizeInventory(); ++i) {
                playerEntity.inventory.placeItemBackInInventory(world, iInventory.removeStackFromSlot(i));
            }
        }
    }

    public void onCraftMatrixChanged(IInventory iInventory) {
        this.detectAndSendChanges();
    }

    public void putStackInSlot(int n, ItemStack itemStack) {
        this.getSlot(n).putStack(itemStack);
    }

    public void setAll(List<ItemStack> list) {
        for (int i = 0; i < list.size(); ++i) {
            this.getSlot(i).putStack(list.get(i));
        }
    }

    public void updateProgressBar(int n, int n2) {
        this.trackedIntReferences.get(n).set(n2);
    }

    public short getNextTransactionID(PlayerInventory playerInventory) {
        this.transactionID = (short)(this.transactionID + 1);
        return this.transactionID;
    }

    public boolean getCanCraft(PlayerEntity playerEntity) {
        return !this.playerList.contains(playerEntity);
    }

    public void setCanCraft(PlayerEntity playerEntity, boolean bl) {
        if (bl) {
            this.playerList.remove(playerEntity);
        } else {
            this.playerList.add(playerEntity);
        }
    }

    public abstract boolean canInteractWith(PlayerEntity var1);

    protected boolean mergeItemStack(ItemStack itemStack, int n, int n2, boolean bl) {
        ItemStack itemStack2;
        Slot slot;
        boolean bl2 = false;
        int n3 = n;
        if (bl) {
            n3 = n2 - 1;
        }
        if (itemStack.isStackable()) {
            while (!itemStack.isEmpty() && !(!bl ? n3 >= n2 : n3 < n)) {
                slot = this.inventorySlots.get(n3);
                itemStack2 = slot.getStack();
                if (!itemStack2.isEmpty() && Container.areItemsAndTagsEqual(itemStack, itemStack2)) {
                    int n4 = itemStack2.getCount() + itemStack.getCount();
                    if (n4 <= itemStack.getMaxStackSize()) {
                        itemStack.setCount(0);
                        itemStack2.setCount(n4);
                        slot.onSlotChanged();
                        bl2 = true;
                    } else if (itemStack2.getCount() < itemStack.getMaxStackSize()) {
                        itemStack.shrink(itemStack.getMaxStackSize() - itemStack2.getCount());
                        itemStack2.setCount(itemStack.getMaxStackSize());
                        slot.onSlotChanged();
                        bl2 = true;
                    }
                }
                if (bl) {
                    --n3;
                    continue;
                }
                ++n3;
            }
        }
        if (!itemStack.isEmpty()) {
            n3 = bl ? n2 - 1 : n;
            while (!(!bl ? n3 >= n2 : n3 < n)) {
                slot = this.inventorySlots.get(n3);
                itemStack2 = slot.getStack();
                if (itemStack2.isEmpty() && slot.isItemValid(itemStack)) {
                    if (itemStack.getCount() > slot.getSlotStackLimit()) {
                        slot.putStack(itemStack.split(slot.getSlotStackLimit()));
                    } else {
                        slot.putStack(itemStack.split(itemStack.getCount()));
                    }
                    slot.onSlotChanged();
                    bl2 = true;
                    break;
                }
                if (bl) {
                    --n3;
                    continue;
                }
                ++n3;
            }
        }
        return bl2;
    }

    public static int extractDragMode(int n) {
        return n >> 2 & 3;
    }

    public static int getDragEvent(int n) {
        return n & 3;
    }

    public static int getQuickcraftMask(int n, int n2) {
        return n & 3 | (n2 & 3) << 2;
    }

    public static boolean isValidDragMode(int n, PlayerEntity playerEntity) {
        if (n == 0) {
            return false;
        }
        if (n == 1) {
            return false;
        }
        return n == 2 && playerEntity.abilities.isCreativeMode;
    }

    protected void resetDrag() {
        this.dragEvent = 0;
        this.dragSlots.clear();
    }

    public static boolean canAddItemToSlot(@Nullable Slot slot, ItemStack itemStack, boolean bl) {
        boolean bl2;
        boolean bl3 = bl2 = slot == null || !slot.getHasStack();
        if (!bl2 && itemStack.isItemEqual(slot.getStack()) && ItemStack.areItemStackTagsEqual(slot.getStack(), itemStack)) {
            return slot.getStack().getCount() + (bl ? 0 : itemStack.getCount()) <= itemStack.getMaxStackSize();
        }
        return bl2;
    }

    public static void computeStackSize(Set<Slot> set, int n, ItemStack itemStack, int n2) {
        switch (n) {
            case 0: {
                itemStack.setCount(MathHelper.floor((float)itemStack.getCount() / (float)set.size()));
                break;
            }
            case 1: {
                itemStack.setCount(1);
                break;
            }
            case 2: {
                itemStack.setCount(itemStack.getItem().getMaxStackSize());
            }
        }
        itemStack.grow(n2);
    }

    public boolean canDragIntoSlot(Slot slot) {
        return false;
    }

    public static int calcRedstone(@Nullable TileEntity tileEntity) {
        return tileEntity instanceof IInventory ? Container.calcRedstoneFromInventory((IInventory)((Object)tileEntity)) : 0;
    }

    public static int calcRedstoneFromInventory(@Nullable IInventory iInventory) {
        if (iInventory == null) {
            return 1;
        }
        int n = 0;
        float f = 0.0f;
        for (int i = 0; i < iInventory.getSizeInventory(); ++i) {
            ItemStack itemStack = iInventory.getStackInSlot(i);
            if (itemStack.isEmpty()) continue;
            f += (float)itemStack.getCount() / (float)Math.min(iInventory.getInventoryStackLimit(), itemStack.getMaxStackSize());
            ++n;
        }
        return MathHelper.floor((f /= (float)iInventory.getSizeInventory()) * 14.0f) + (n > 0 ? 1 : 0);
    }

    private String lambda$slotClick$2() throws Exception {
        return this.getClass().getCanonicalName();
    }

    private String lambda$slotClick$1() throws Exception {
        return this.containerType != null ? Registry.MENU.getKey(this.containerType).toString() : "<no type>";
    }

    private static Boolean lambda$isWithinUsableDistance$0(Block block, PlayerEntity playerEntity, World world, BlockPos blockPos) {
        return !world.getBlockState(blockPos).isIn(block) ? false : playerEntity.getDistanceSq((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5) <= 64.0;
    }
}

