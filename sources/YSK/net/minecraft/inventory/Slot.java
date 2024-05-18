package net.minecraft.inventory;

import net.minecraft.item.*;
import net.minecraft.entity.player.*;

public class Slot
{
    private final int slotIndex;
    public int yDisplayPosition;
    public int slotNumber;
    public final IInventory inventory;
    public int xDisplayPosition;
    
    public String getSlotTexture() {
        return null;
    }
    
    public boolean isItemValid(final ItemStack itemStack) {
        return " ".length() != 0;
    }
    
    public boolean canBeHovered() {
        return " ".length() != 0;
    }
    
    protected void onCrafting(final ItemStack itemStack) {
    }
    
    public boolean getHasStack() {
        if (this.getStack() != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public boolean canTakeStack(final EntityPlayer entityPlayer) {
        return " ".length() != 0;
    }
    
    public ItemStack decrStackSize(final int n) {
        return this.inventory.decrStackSize(this.slotIndex, n);
    }
    
    protected void onCrafting(final ItemStack itemStack, final int n) {
    }
    
    public void onSlotChanged() {
        this.inventory.markDirty();
    }
    
    public boolean isHere(final IInventory inventory, final int n) {
        if (inventory == this.inventory && n == this.slotIndex) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public int getSlotStackLimit() {
        return this.inventory.getInventoryStackLimit();
    }
    
    public ItemStack getStack() {
        return this.inventory.getStackInSlot(this.slotIndex);
    }
    
    public void onPickupFromSlot(final EntityPlayer entityPlayer, final ItemStack itemStack) {
        this.onSlotChanged();
    }
    
    public void onSlotChange(final ItemStack itemStack, final ItemStack itemStack2) {
        if (itemStack != null && itemStack2 != null && itemStack.getItem() == itemStack2.getItem()) {
            final int n = itemStack2.stackSize - itemStack.stackSize;
            if (n > 0) {
                this.onCrafting(itemStack, n);
            }
        }
    }
    
    public void putStack(final ItemStack itemStack) {
        this.inventory.setInventorySlotContents(this.slotIndex, itemStack);
        this.onSlotChanged();
    }
    
    public int getItemStackLimit(final ItemStack itemStack) {
        return this.getSlotStackLimit();
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
            if (1 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public Slot(final IInventory inventory, final int slotIndex, final int xDisplayPosition, final int yDisplayPosition) {
        this.inventory = inventory;
        this.slotIndex = slotIndex;
        this.xDisplayPosition = xDisplayPosition;
        this.yDisplayPosition = yDisplayPosition;
    }
}
