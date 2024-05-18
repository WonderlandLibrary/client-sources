package net.minecraft.inventory;

import net.minecraft.item.*;
import net.minecraft.entity.player.*;

public class ContainerHopper extends Container
{
    private final IInventory hopperInventory;
    
    @Override
    public boolean canInteractWith(final EntityPlayer entityPlayer) {
        return this.hopperInventory.isUseableByPlayer(entityPlayer);
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int n) {
        ItemStack copy = null;
        final Slot slot = this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            final ItemStack stack = slot.getStack();
            copy = stack.copy();
            if (n < this.hopperInventory.getSizeInventory()) {
                if (!this.mergeItemStack(stack, this.hopperInventory.getSizeInventory(), this.inventorySlots.size(), " ".length() != 0)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(stack, "".length(), this.hopperInventory.getSizeInventory(), "".length() != 0)) {
                return null;
            }
            if (stack.stackSize == 0) {
                slot.putStack(null);
                "".length();
                if (-1 >= 1) {
                    throw null;
                }
            }
            else {
                slot.onSlotChanged();
            }
        }
        return copy;
    }
    
    @Override
    public void onContainerClosed(final EntityPlayer entityPlayer) {
        super.onContainerClosed(entityPlayer);
        this.hopperInventory.closeInventory(entityPlayer);
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
            if (2 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ContainerHopper(final InventoryPlayer inventoryPlayer, final IInventory hopperInventory, final EntityPlayer entityPlayer) {
        (this.hopperInventory = hopperInventory).openInventory(entityPlayer);
        final int n = 0x23 ^ 0x10;
        int i = "".length();
        "".length();
        if (1 >= 2) {
            throw null;
        }
        while (i < hopperInventory.getSizeInventory()) {
            this.addSlotToContainer(new Slot(hopperInventory, i, (0x93 ^ 0xBF) + i * (0x55 ^ 0x47), 0x88 ^ 0x9C));
            ++i;
        }
        int j = "".length();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (j < "   ".length()) {
            int k = "".length();
            "".length();
            if (3 == 1) {
                throw null;
            }
            while (k < (0x1F ^ 0x16)) {
                this.addSlotToContainer(new Slot(inventoryPlayer, k + j * (0xA4 ^ 0xAD) + (0xA2 ^ 0xAB), (0x96 ^ 0x9E) + k * (0x30 ^ 0x22), j * (0x6C ^ 0x7E) + n));
                ++k;
            }
            ++j;
        }
        int l = "".length();
        "".length();
        if (3 <= -1) {
            throw null;
        }
        while (l < (0x73 ^ 0x7A)) {
            this.addSlotToContainer(new Slot(inventoryPlayer, l, (0x59 ^ 0x51) + l * (0x1D ^ 0xF), (0x92 ^ 0xA8) + n));
            ++l;
        }
    }
}
