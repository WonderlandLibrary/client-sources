package net.minecraft.inventory;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class ContainerChest extends Container
{
    private IInventory lowerChestInventory;
    private int numRows;
    
    public ContainerChest(final IInventory inventory, final IInventory lowerChestInventory, final EntityPlayer entityPlayer) {
        this.lowerChestInventory = lowerChestInventory;
        this.numRows = lowerChestInventory.getSizeInventory() / (0xAF ^ 0xA6);
        lowerChestInventory.openInventory(entityPlayer);
        final int n = (this.numRows - (0x45 ^ 0x41)) * (0x3F ^ 0x2D);
        int i = "".length();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (i < this.numRows) {
            int j = "".length();
            "".length();
            if (4 <= 3) {
                throw null;
            }
            while (j < (0xB5 ^ 0xBC)) {
                this.addSlotToContainer(new Slot(lowerChestInventory, j + i * (0x84 ^ 0x8D), (0x18 ^ 0x10) + j * (0x88 ^ 0x9A), (0x10 ^ 0x2) + i * (0xB0 ^ 0xA2)));
                ++j;
            }
            ++i;
        }
        int k = "".length();
        "".length();
        if (0 >= 4) {
            throw null;
        }
        while (k < "   ".length()) {
            int l = "".length();
            "".length();
            if (-1 >= 3) {
                throw null;
            }
            while (l < (0x1E ^ 0x17)) {
                this.addSlotToContainer(new Slot(inventory, l + k * (0xAC ^ 0xA5) + (0x83 ^ 0x8A), (0x6F ^ 0x67) + l * (0xD6 ^ 0xC4), (0xA5 ^ 0xC2) + k * (0x43 ^ 0x51) + n));
                ++l;
            }
            ++k;
        }
        int length = "".length();
        "".length();
        if (4 <= 3) {
            throw null;
        }
        while (length < (0x37 ^ 0x3E)) {
            this.addSlotToContainer(new Slot(inventory, length, (0x26 ^ 0x2E) + length * (0x6E ^ 0x7C), 35 + 145 - 142 + 123 + n));
            ++length;
        }
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
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer entityPlayer) {
        return this.lowerChestInventory.isUseableByPlayer(entityPlayer);
    }
    
    @Override
    public void onContainerClosed(final EntityPlayer entityPlayer) {
        super.onContainerClosed(entityPlayer);
        this.lowerChestInventory.closeInventory(entityPlayer);
    }
    
    public IInventory getLowerChestInventory() {
        return this.lowerChestInventory;
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int n) {
        ItemStack copy = null;
        final Slot slot = this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            final ItemStack stack = slot.getStack();
            copy = stack.copy();
            if (n < this.numRows * (0x2 ^ 0xB)) {
                if (!this.mergeItemStack(stack, this.numRows * (0x3E ^ 0x37), this.inventorySlots.size(), " ".length() != 0)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(stack, "".length(), this.numRows * (0x47 ^ 0x4E), "".length() != 0)) {
                return null;
            }
            if (stack.stackSize == 0) {
                slot.putStack(null);
                "".length();
                if (3 <= 0) {
                    throw null;
                }
            }
            else {
                slot.onSlotChanged();
            }
        }
        return copy;
    }
}
