package net.minecraft.inventory;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class ContainerDispenser extends Container
{
    private IInventory dispenserInventory;
    
    @Override
    public boolean canInteractWith(final EntityPlayer entityPlayer) {
        return this.dispenserInventory.isUseableByPlayer(entityPlayer);
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
            if (1 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ContainerDispenser(final IInventory inventory, final IInventory dispenserInventory) {
        this.dispenserInventory = dispenserInventory;
        int i = "".length();
        "".length();
        if (3 == 2) {
            throw null;
        }
        while (i < "   ".length()) {
            int j = "".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
            while (j < "   ".length()) {
                this.addSlotToContainer(new Slot(dispenserInventory, j + i * "   ".length(), (0x1A ^ 0x24) + j * (0x80 ^ 0x92), (0x98 ^ 0x89) + i * (0x78 ^ 0x6A)));
                ++j;
            }
            ++i;
        }
        int k = "".length();
        "".length();
        if (2 == 3) {
            throw null;
        }
        while (k < "   ".length()) {
            int l = "".length();
            "".length();
            if (0 == -1) {
                throw null;
            }
            while (l < (0xAB ^ 0xA2)) {
                this.addSlotToContainer(new Slot(inventory, l + k * (0x7A ^ 0x73) + (0xB2 ^ 0xBB), (0x37 ^ 0x3F) + l * (0x19 ^ 0xB), (0x58 ^ 0xC) + k * (0xB2 ^ 0xA0)));
                ++l;
            }
            ++k;
        }
        int length = "".length();
        "".length();
        if (2 == 0) {
            throw null;
        }
        while (length < (0x95 ^ 0x9C)) {
            this.addSlotToContainer(new Slot(inventory, length, (0xA6 ^ 0xAE) + length * (0x8E ^ 0x9C), 112 + 72 - 58 + 16));
            ++length;
        }
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int n) {
        ItemStack copy = null;
        final Slot slot = this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            final ItemStack stack = slot.getStack();
            copy = stack.copy();
            if (n < (0x89 ^ 0x80)) {
                if (!this.mergeItemStack(stack, 0x90 ^ 0x99, 0xB8 ^ 0x95, " ".length() != 0)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(stack, "".length(), 0x9A ^ 0x93, "".length() != 0)) {
                return null;
            }
            if (stack.stackSize == 0) {
                slot.putStack(null);
                "".length();
                if (-1 >= 0) {
                    throw null;
                }
            }
            else {
                slot.onSlotChanged();
            }
            if (stack.stackSize == copy.stackSize) {
                return null;
            }
            slot.onPickupFromSlot(entityPlayer, stack);
        }
        return copy;
    }
}
