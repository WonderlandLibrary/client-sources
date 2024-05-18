package net.minecraft.inventory;

import com.google.common.collect.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.tileentity.*;

public abstract class Container
{
    private static final String[] I;
    public List<ItemStack> inventoryItemStacks;
    public int windowId;
    private short transactionID;
    private Set<EntityPlayer> playerList;
    private int dragEvent;
    private int dragMode;
    protected List<ICrafting> crafters;
    private final Set<Slot> dragSlots;
    public List<Slot> inventorySlots;
    
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
            if (false) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected void resetDrag() {
        this.dragEvent = "".length();
        this.dragSlots.clear();
    }
    
    public void putStackInSlot(final int n, final ItemStack itemStack) {
        this.getSlot(n).putStack(itemStack);
    }
    
    protected void retrySlotClick(final int n, final int n2, final boolean b, final EntityPlayer entityPlayer) {
        this.slotClick(n, n2, " ".length(), entityPlayer);
    }
    
    public void onCraftMatrixChanged(final IInventory inventory) {
        this.detectAndSendChanges();
    }
    
    public boolean canDragIntoSlot(final Slot slot) {
        return " ".length() != 0;
    }
    
    public static int extractDragMode(final int n) {
        return n >> "  ".length() & "   ".length();
    }
    
    public Container() {
        this.inventoryItemStacks = (List<ItemStack>)Lists.newArrayList();
        this.inventorySlots = (List<Slot>)Lists.newArrayList();
        this.dragMode = -" ".length();
        this.dragSlots = (Set<Slot>)Sets.newHashSet();
        this.crafters = (List<ICrafting>)Lists.newArrayList();
        this.playerList = (Set<EntityPlayer>)Sets.newHashSet();
    }
    
    public static boolean isValidDragMode(final int n, final EntityPlayer entityPlayer) {
        int n2;
        if (n == 0) {
            n2 = " ".length();
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        else if (n == " ".length()) {
            n2 = " ".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else if (n == "  ".length() && entityPlayer.capabilities.isCreativeMode) {
            n2 = " ".length();
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        return n2 != 0;
    }
    
    public Slot getSlot(final int n) {
        return this.inventorySlots.get(n);
    }
    
    public void onContainerClosed(final EntityPlayer entityPlayer) {
        final InventoryPlayer inventory = entityPlayer.inventory;
        if (inventory.getItemStack() != null) {
            entityPlayer.dropPlayerItemWithRandomChoice(inventory.getItemStack(), "".length() != 0);
            inventory.setItemStack(null);
        }
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("$%\u00049\u0007\u0006)\u0005m\u0003\u0004>\u0012,\u0006\u0011l\u001b$\u0011\u001c)\u0019$\f\u000f", "hLwMb");
    }
    
    public ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int n) {
        final Slot slot = this.inventorySlots.get(n);
        ItemStack stack;
        if (slot != null) {
            stack = slot.getStack();
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        else {
            stack = null;
        }
        return stack;
    }
    
    public void putStacksInSlots(final ItemStack[] array) {
        int i = "".length();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (i < array.length) {
            this.getSlot(i).putStack(array[i]);
            ++i;
        }
    }
    
    public boolean canMergeSlot(final ItemStack itemStack, final Slot slot) {
        return " ".length() != 0;
    }
    
    public boolean getCanCraft(final EntityPlayer entityPlayer) {
        int n;
        if (this.playerList.contains(entityPlayer)) {
            n = "".length();
            "".length();
            if (2 <= 0) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    public short getNextTransactionID(final InventoryPlayer inventoryPlayer) {
        return this.transactionID += (short)" ".length();
    }
    
    public void removeCraftingFromCrafters(final ICrafting crafting) {
        this.crafters.remove(crafting);
    }
    
    public void setCanCraft(final EntityPlayer entityPlayer, final boolean b) {
        if (b) {
            this.playerList.remove(entityPlayer);
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else {
            this.playerList.add(entityPlayer);
        }
    }
    
    static {
        I();
    }
    
    public boolean enchantItem(final EntityPlayer entityPlayer, final int n) {
        return "".length() != 0;
    }
    
    public void detectAndSendChanges() {
        int i = "".length();
        "".length();
        if (3 >= 4) {
            throw null;
        }
        while (i < this.inventorySlots.size()) {
            final ItemStack stack = this.inventorySlots.get(i).getStack();
            if (!ItemStack.areItemStacksEqual(this.inventoryItemStacks.get(i), stack)) {
                ItemStack copy;
                if (stack == null) {
                    copy = null;
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
                else {
                    copy = stack.copy();
                }
                final ItemStack itemStack = copy;
                this.inventoryItemStacks.set(i, itemStack);
                int j = "".length();
                "".length();
                if (-1 == 4) {
                    throw null;
                }
                while (j < this.crafters.size()) {
                    this.crafters.get(j).sendSlotContents(this, i, itemStack);
                    ++j;
                }
            }
            ++i;
        }
    }
    
    public void updateProgressBar(final int n, final int n2) {
    }
    
    public static void computeStackSize(final Set<Slot> set, final int n, final ItemStack itemStack, final int n2) {
        switch (n) {
            case 0: {
                itemStack.stackSize = MathHelper.floor_float(itemStack.stackSize / set.size());
                "".length();
                if (3 == 4) {
                    throw null;
                }
                break;
            }
            case 1: {
                itemStack.stackSize = " ".length();
                "".length();
                if (2 == 3) {
                    throw null;
                }
                break;
            }
            case 2: {
                itemStack.stackSize = itemStack.getItem().getItemStackLimit();
                break;
            }
        }
        itemStack.stackSize += n2;
    }
    
    protected boolean mergeItemStack(final ItemStack itemStack, final int n, final int n2, final boolean b) {
        int n3 = "".length();
        int n4 = n;
        if (b) {
            n4 = n2 - " ".length();
        }
        if (itemStack.isStackable()) {
            "".length();
            if (3 <= 2) {
                throw null;
            }
            while (itemStack.stackSize > 0 && ((!b && n4 < n2) || (b && n4 >= n))) {
                final Slot slot = this.inventorySlots.get(n4);
                final ItemStack stack = slot.getStack();
                if (stack != null && stack.getItem() == itemStack.getItem() && (!itemStack.getHasSubtypes() || itemStack.getMetadata() == stack.getMetadata()) && ItemStack.areItemStackTagsEqual(itemStack, stack)) {
                    final int stackSize = stack.stackSize + itemStack.stackSize;
                    if (stackSize <= itemStack.getMaxStackSize()) {
                        itemStack.stackSize = "".length();
                        stack.stackSize = stackSize;
                        slot.onSlotChanged();
                        n3 = " ".length();
                        "".length();
                        if (4 < 1) {
                            throw null;
                        }
                    }
                    else if (stack.stackSize < itemStack.getMaxStackSize()) {
                        itemStack.stackSize -= itemStack.getMaxStackSize() - stack.stackSize;
                        stack.stackSize = itemStack.getMaxStackSize();
                        slot.onSlotChanged();
                        n3 = " ".length();
                    }
                }
                if (b) {
                    --n4;
                    "".length();
                    if (0 >= 4) {
                        throw null;
                    }
                    continue;
                }
                else {
                    ++n4;
                }
            }
        }
        if (itemStack.stackSize > 0) {
            int n5;
            if (b) {
                n5 = n2 - " ".length();
                "".length();
                if (3 >= 4) {
                    throw null;
                }
            }
            else {
                n5 = n;
                "".length();
                if (4 <= -1) {
                    throw null;
                }
            }
            while ((!b && n5 < n2) || (b && n5 >= n)) {
                final Slot slot2 = this.inventorySlots.get(n5);
                if (slot2.getStack() == null) {
                    slot2.putStack(itemStack.copy());
                    slot2.onSlotChanged();
                    itemStack.stackSize = "".length();
                    n3 = " ".length();
                    "".length();
                    if (1 <= -1) {
                        throw null;
                    }
                    break;
                }
                else if (b) {
                    --n5;
                    "".length();
                    if (1 < -1) {
                        throw null;
                    }
                    continue;
                }
                else {
                    ++n5;
                }
            }
        }
        return n3 != 0;
    }
    
    public static int func_94534_d(final int n, final int n2) {
        return (n & "   ".length()) | (n2 & "   ".length()) << "  ".length();
    }
    
    public ItemStack slotClick(final int n, final int n2, final int n3, final EntityPlayer entityPlayer) {
        ItemStack itemStack = null;
        final InventoryPlayer inventory = entityPlayer.inventory;
        if (n3 == (0x37 ^ 0x32)) {
            final int dragEvent = this.dragEvent;
            this.dragEvent = getDragEvent(n2);
            if ((dragEvent != " ".length() || this.dragEvent != "  ".length()) && dragEvent != this.dragEvent) {
                this.resetDrag();
                "".length();
                if (3 <= 2) {
                    throw null;
                }
            }
            else if (inventory.getItemStack() == null) {
                this.resetDrag();
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
            }
            else if (this.dragEvent == 0) {
                this.dragMode = extractDragMode(n2);
                if (isValidDragMode(this.dragMode, entityPlayer)) {
                    this.dragEvent = " ".length();
                    this.dragSlots.clear();
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                }
                else {
                    this.resetDrag();
                    "".length();
                    if (1 <= -1) {
                        throw null;
                    }
                }
            }
            else if (this.dragEvent == " ".length()) {
                final Slot slot = this.inventorySlots.get(n);
                if (slot != null && canAddItemToSlot(slot, inventory.getItemStack(), " ".length() != 0) && slot.isItemValid(inventory.getItemStack()) && inventory.getItemStack().stackSize > this.dragSlots.size() && this.canDragIntoSlot(slot)) {
                    this.dragSlots.add(slot);
                    "".length();
                    if (3 <= 1) {
                        throw null;
                    }
                }
            }
            else if (this.dragEvent == "  ".length()) {
                if (!this.dragSlots.isEmpty()) {
                    ItemStack copy = inventory.getItemStack().copy();
                    int stackSize = inventory.getItemStack().stackSize;
                    final Iterator<Slot> iterator = this.dragSlots.iterator();
                    "".length();
                    if (0 >= 1) {
                        throw null;
                    }
                    while (iterator.hasNext()) {
                        final Slot slot2 = iterator.next();
                        if (slot2 != null && canAddItemToSlot(slot2, inventory.getItemStack(), " ".length() != 0) && slot2.isItemValid(inventory.getItemStack()) && inventory.getItemStack().stackSize >= this.dragSlots.size() && this.canDragIntoSlot(slot2)) {
                            final ItemStack copy2 = copy.copy();
                            int n4;
                            if (slot2.getHasStack()) {
                                n4 = slot2.getStack().stackSize;
                                "".length();
                                if (2 == -1) {
                                    throw null;
                                }
                            }
                            else {
                                n4 = "".length();
                            }
                            final int n5 = n4;
                            computeStackSize(this.dragSlots, this.dragMode, copy2, n5);
                            if (copy2.stackSize > copy2.getMaxStackSize()) {
                                copy2.stackSize = copy2.getMaxStackSize();
                            }
                            if (copy2.stackSize > slot2.getItemStackLimit(copy2)) {
                                copy2.stackSize = slot2.getItemStackLimit(copy2);
                            }
                            stackSize -= copy2.stackSize - n5;
                            slot2.putStack(copy2);
                        }
                    }
                    copy.stackSize = stackSize;
                    if (copy.stackSize <= 0) {
                        copy = null;
                    }
                    inventory.setItemStack(copy);
                }
                this.resetDrag();
                "".length();
                if (false) {
                    throw null;
                }
            }
            else {
                this.resetDrag();
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
        }
        else if (this.dragEvent != 0) {
            this.resetDrag();
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else if ((n3 == 0 || n3 == " ".length()) && (n2 == 0 || n2 == " ".length())) {
            if (n == -(486 + 359 - 127 + 281)) {
                if (inventory.getItemStack() != null) {
                    if (n2 == 0) {
                        entityPlayer.dropPlayerItemWithRandomChoice(inventory.getItemStack(), " ".length() != 0);
                        inventory.setItemStack(null);
                    }
                    if (n2 == " ".length()) {
                        entityPlayer.dropPlayerItemWithRandomChoice(inventory.getItemStack().splitStack(" ".length()), " ".length() != 0);
                        if (inventory.getItemStack().stackSize == 0) {
                            inventory.setItemStack(null);
                            "".length();
                            if (0 <= -1) {
                                throw null;
                            }
                        }
                    }
                }
            }
            else if (n3 == " ".length()) {
                if (n < 0) {
                    return null;
                }
                final Slot slot3 = this.inventorySlots.get(n);
                if (slot3 != null && slot3.canTakeStack(entityPlayer)) {
                    final ItemStack transferStackInSlot = this.transferStackInSlot(entityPlayer, n);
                    if (transferStackInSlot != null) {
                        final Item item = transferStackInSlot.getItem();
                        itemStack = transferStackInSlot.copy();
                        if (slot3.getStack() != null && slot3.getStack().getItem() == item) {
                            this.retrySlotClick(n, n2, " ".length() != 0, entityPlayer);
                            "".length();
                            if (2 <= -1) {
                                throw null;
                            }
                        }
                    }
                }
            }
            else {
                if (n < 0) {
                    return null;
                }
                final Slot slot4 = this.inventorySlots.get(n);
                if (slot4 != null) {
                    final ItemStack stack = slot4.getStack();
                    final ItemStack itemStack2 = inventory.getItemStack();
                    if (stack != null) {
                        itemStack = stack.copy();
                    }
                    if (stack == null) {
                        if (itemStack2 != null && slot4.isItemValid(itemStack2)) {
                            int n6;
                            if (n2 == 0) {
                                n6 = itemStack2.stackSize;
                                "".length();
                                if (-1 != -1) {
                                    throw null;
                                }
                            }
                            else {
                                n6 = " ".length();
                            }
                            int itemStackLimit = n6;
                            if (itemStackLimit > slot4.getItemStackLimit(itemStack2)) {
                                itemStackLimit = slot4.getItemStackLimit(itemStack2);
                            }
                            if (itemStack2.stackSize >= itemStackLimit) {
                                slot4.putStack(itemStack2.splitStack(itemStackLimit));
                            }
                            if (itemStack2.stackSize == 0) {
                                inventory.setItemStack(null);
                                "".length();
                                if (2 == 4) {
                                    throw null;
                                }
                            }
                        }
                    }
                    else if (slot4.canTakeStack(entityPlayer)) {
                        if (itemStack2 == null) {
                            int stackSize2;
                            if (n2 == 0) {
                                stackSize2 = stack.stackSize;
                                "".length();
                                if (true != true) {
                                    throw null;
                                }
                            }
                            else {
                                stackSize2 = (stack.stackSize + " ".length()) / "  ".length();
                            }
                            inventory.setItemStack(slot4.decrStackSize(stackSize2));
                            if (stack.stackSize == 0) {
                                slot4.putStack(null);
                            }
                            slot4.onPickupFromSlot(entityPlayer, inventory.getItemStack());
                            "".length();
                            if (3 <= 0) {
                                throw null;
                            }
                        }
                        else if (slot4.isItemValid(itemStack2)) {
                            if (stack.getItem() == itemStack2.getItem() && stack.getMetadata() == itemStack2.getMetadata() && ItemStack.areItemStackTagsEqual(stack, itemStack2)) {
                                int n7;
                                if (n2 == 0) {
                                    n7 = itemStack2.stackSize;
                                    "".length();
                                    if (-1 >= 3) {
                                        throw null;
                                    }
                                }
                                else {
                                    n7 = " ".length();
                                }
                                int n8 = n7;
                                if (n8 > slot4.getItemStackLimit(itemStack2) - stack.stackSize) {
                                    n8 = slot4.getItemStackLimit(itemStack2) - stack.stackSize;
                                }
                                if (n8 > itemStack2.getMaxStackSize() - stack.stackSize) {
                                    n8 = itemStack2.getMaxStackSize() - stack.stackSize;
                                }
                                itemStack2.splitStack(n8);
                                if (itemStack2.stackSize == 0) {
                                    inventory.setItemStack(null);
                                }
                                final ItemStack itemStack3 = stack;
                                itemStack3.stackSize += n8;
                                "".length();
                                if (-1 >= 4) {
                                    throw null;
                                }
                            }
                            else if (itemStack2.stackSize <= slot4.getItemStackLimit(itemStack2)) {
                                slot4.putStack(itemStack2);
                                inventory.setItemStack(stack);
                                "".length();
                                if (3 <= -1) {
                                    throw null;
                                }
                            }
                        }
                        else if (stack.getItem() == itemStack2.getItem() && itemStack2.getMaxStackSize() > " ".length() && (!stack.getHasSubtypes() || stack.getMetadata() == itemStack2.getMetadata()) && ItemStack.areItemStackTagsEqual(stack, itemStack2)) {
                            final int stackSize3 = stack.stackSize;
                            if (stackSize3 > 0 && stackSize3 + itemStack2.stackSize <= itemStack2.getMaxStackSize()) {
                                final ItemStack itemStack4 = itemStack2;
                                itemStack4.stackSize += stackSize3;
                                if (slot4.decrStackSize(stackSize3).stackSize == 0) {
                                    slot4.putStack(null);
                                }
                                slot4.onPickupFromSlot(entityPlayer, inventory.getItemStack());
                            }
                        }
                    }
                    slot4.onSlotChanged();
                    "".length();
                    if (3 < -1) {
                        throw null;
                    }
                }
            }
        }
        else if (n3 == "  ".length() && n2 >= 0 && n2 < (0xC9 ^ 0xC0)) {
            final Slot slot5 = this.inventorySlots.get(n);
            if (slot5.canTakeStack(entityPlayer)) {
                final ItemStack stackInSlot = inventory.getStackInSlot(n2);
                int n9;
                if (stackInSlot != null && (slot5.inventory != inventory || !slot5.isItemValid(stackInSlot))) {
                    n9 = "".length();
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
                else {
                    n9 = " ".length();
                }
                int n10 = n9;
                int firstEmptyStack = -" ".length();
                if (n10 == 0) {
                    firstEmptyStack = inventory.getFirstEmptyStack();
                    final int n11 = n10;
                    int n12;
                    if (firstEmptyStack > -" ".length()) {
                        n12 = " ".length();
                        "".length();
                        if (2 != 2) {
                            throw null;
                        }
                    }
                    else {
                        n12 = "".length();
                    }
                    n10 = (n11 | n12);
                }
                if (slot5.getHasStack() && n10 != 0) {
                    final ItemStack stack2 = slot5.getStack();
                    inventory.setInventorySlotContents(n2, stack2.copy());
                    if ((slot5.inventory != inventory || !slot5.isItemValid(stackInSlot)) && stackInSlot != null) {
                        if (firstEmptyStack > -" ".length()) {
                            inventory.addItemStackToInventory(stackInSlot);
                            slot5.decrStackSize(stack2.stackSize);
                            slot5.putStack(null);
                            slot5.onPickupFromSlot(entityPlayer, stack2);
                            "".length();
                            if (4 == 2) {
                                throw null;
                            }
                        }
                    }
                    else {
                        slot5.decrStackSize(stack2.stackSize);
                        slot5.putStack(stackInSlot);
                        slot5.onPickupFromSlot(entityPlayer, stack2);
                        "".length();
                        if (4 != 4) {
                            throw null;
                        }
                    }
                }
                else if (!slot5.getHasStack() && stackInSlot != null && slot5.isItemValid(stackInSlot)) {
                    inventory.setInventorySlotContents(n2, null);
                    slot5.putStack(stackInSlot);
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                }
            }
        }
        else if (n3 == "   ".length() && entityPlayer.capabilities.isCreativeMode && inventory.getItemStack() == null && n >= 0) {
            final Slot slot6 = this.inventorySlots.get(n);
            if (slot6 != null && slot6.getHasStack()) {
                final ItemStack copy3 = slot6.getStack().copy();
                copy3.stackSize = copy3.getMaxStackSize();
                inventory.setItemStack(copy3);
                "".length();
                if (2 < 0) {
                    throw null;
                }
            }
        }
        else if (n3 == (0x1C ^ 0x18) && inventory.getItemStack() == null && n >= 0) {
            final Slot slot7 = this.inventorySlots.get(n);
            if (slot7 != null && slot7.getHasStack() && slot7.canTakeStack(entityPlayer)) {
                final Slot slot8 = slot7;
                int n13;
                if (n2 == 0) {
                    n13 = " ".length();
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                }
                else {
                    n13 = slot7.getStack().stackSize;
                }
                final ItemStack decrStackSize = slot8.decrStackSize(n13);
                slot7.onPickupFromSlot(entityPlayer, decrStackSize);
                entityPlayer.dropPlayerItemWithRandomChoice(decrStackSize, " ".length() != 0);
                "".length();
                if (3 <= 0) {
                    throw null;
                }
            }
        }
        else if (n3 == (0x19 ^ 0x1F) && n >= 0) {
            final Slot slot9 = this.inventorySlots.get(n);
            final ItemStack itemStack5 = inventory.getItemStack();
            if (itemStack5 != null && (slot9 == null || !slot9.getHasStack() || !slot9.canTakeStack(entityPlayer))) {
                int length;
                if (n2 == 0) {
                    length = "".length();
                    "".length();
                    if (1 <= 0) {
                        throw null;
                    }
                }
                else {
                    length = this.inventorySlots.size() - " ".length();
                }
                final int n14 = length;
                int length2;
                if (n2 == 0) {
                    length2 = " ".length();
                    "".length();
                    if (4 < 0) {
                        throw null;
                    }
                }
                else {
                    length2 = -" ".length();
                }
                final int n15 = length2;
                int i = "".length();
                "".length();
                if (0 < -1) {
                    throw null;
                }
                while (i < "  ".length()) {
                    int n16 = n14;
                    "".length();
                    if (4 < 0) {
                        throw null;
                    }
                    while (n16 >= 0 && n16 < this.inventorySlots.size() && itemStack5.stackSize < itemStack5.getMaxStackSize()) {
                        final Slot slot10 = this.inventorySlots.get(n16);
                        if (slot10.getHasStack() && canAddItemToSlot(slot10, itemStack5, " ".length() != 0) && slot10.canTakeStack(entityPlayer) && this.canMergeSlot(itemStack5, slot10) && (i != 0 || slot10.getStack().stackSize != slot10.getStack().getMaxStackSize())) {
                            final int min = Math.min(itemStack5.getMaxStackSize() - itemStack5.stackSize, slot10.getStack().stackSize);
                            final ItemStack decrStackSize2 = slot10.decrStackSize(min);
                            final ItemStack itemStack6 = itemStack5;
                            itemStack6.stackSize += min;
                            if (decrStackSize2.stackSize <= 0) {
                                slot10.putStack(null);
                            }
                            slot10.onPickupFromSlot(entityPlayer, decrStackSize2);
                        }
                        n16 += n15;
                    }
                    ++i;
                }
            }
            this.detectAndSendChanges();
        }
        return itemStack;
    }
    
    public abstract boolean canInteractWith(final EntityPlayer p0);
    
    public Slot getSlotFromInventory(final IInventory inventory, final int n) {
        int i = "".length();
        "".length();
        if (2 <= -1) {
            throw null;
        }
        while (i < this.inventorySlots.size()) {
            final Slot slot = this.inventorySlots.get(i);
            if (slot.isHere(inventory, n)) {
                return slot;
            }
            ++i;
        }
        return null;
    }
    
    public void onCraftGuiOpened(final ICrafting crafting) {
        if (this.crafters.contains(crafting)) {
            throw new IllegalArgumentException(Container.I["".length()]);
        }
        this.crafters.add(crafting);
        crafting.updateCraftingInventory(this, this.getInventory());
        this.detectAndSendChanges();
    }
    
    public static boolean canAddItemToSlot(final Slot slot, final ItemStack itemStack, final boolean b) {
        int n;
        if (slot != null && slot.getHasStack()) {
            n = "".length();
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        int n2 = n;
        if (slot != null && slot.getHasStack() && itemStack != null && itemStack.isItemEqual(slot.getStack()) && ItemStack.areItemStackTagsEqual(slot.getStack(), itemStack)) {
            final int n3 = n2;
            final int stackSize = slot.getStack().stackSize;
            int n4;
            if (b) {
                n4 = "".length();
                "".length();
                if (1 >= 2) {
                    throw null;
                }
            }
            else {
                n4 = itemStack.stackSize;
            }
            int n5;
            if (stackSize + n4 <= itemStack.getMaxStackSize()) {
                n5 = " ".length();
                "".length();
                if (1 < 1) {
                    throw null;
                }
            }
            else {
                n5 = "".length();
            }
            n2 = (n3 | n5);
        }
        return n2 != 0;
    }
    
    public static int getDragEvent(final int n) {
        return n & "   ".length();
    }
    
    protected Slot addSlotToContainer(final Slot slot) {
        slot.slotNumber = this.inventorySlots.size();
        this.inventorySlots.add(slot);
        this.inventoryItemStacks.add(null);
        return slot;
    }
    
    public static int calcRedstoneFromInventory(final IInventory inventory) {
        if (inventory == null) {
            return "".length();
        }
        int length = "".length();
        float n = 0.0f;
        int i = "".length();
        "".length();
        if (3 <= -1) {
            throw null;
        }
        while (i < inventory.getSizeInventory()) {
            final ItemStack stackInSlot = inventory.getStackInSlot(i);
            if (stackInSlot != null) {
                n += stackInSlot.stackSize / Math.min(inventory.getInventoryStackLimit(), stackInSlot.getMaxStackSize());
                ++length;
            }
            ++i;
        }
        final int floor_float = MathHelper.floor_float(n / inventory.getSizeInventory() * 14.0f);
        int n2;
        if (length > 0) {
            n2 = " ".length();
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        return floor_float + n2;
    }
    
    public List<ItemStack> getInventory() {
        final ArrayList arrayList = Lists.newArrayList();
        int i = "".length();
        "".length();
        if (-1 >= 0) {
            throw null;
        }
        while (i < this.inventorySlots.size()) {
            arrayList.add(this.inventorySlots.get(i).getStack());
            ++i;
        }
        return (List<ItemStack>)arrayList;
    }
    
    public static int calcRedstone(final TileEntity tileEntity) {
        int n;
        if (tileEntity instanceof IInventory) {
            n = calcRedstoneFromInventory((IInventory)tileEntity);
            "".length();
            if (-1 < -1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n;
    }
}
