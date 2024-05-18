package net.minecraft.inventory;

import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;

public class InventoryLargeChest implements ILockableContainer
{
    private String name;
    private ILockableContainer lowerChest;
    private ILockableContainer upperChest;
    
    @Override
    public boolean hasCustomName() {
        if (!this.upperChest.hasCustomName() && !this.lowerChest.hasCustomName()) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    @Override
    public String getGuiID() {
        return this.upperChest.getGuiID();
    }
    
    @Override
    public void setInventorySlotContents(final int n, final ItemStack itemStack) {
        if (n >= this.upperChest.getSizeInventory()) {
            this.lowerChest.setInventorySlotContents(n - this.upperChest.getSizeInventory(), itemStack);
            "".length();
            if (4 == 1) {
                throw null;
            }
        }
        else {
            this.upperChest.setInventorySlotContents(n, itemStack);
        }
    }
    
    @Override
    public void markDirty() {
        this.upperChest.markDirty();
        this.lowerChest.markDirty();
    }
    
    @Override
    public void clear() {
        this.upperChest.clear();
        this.lowerChest.clear();
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
            if (1 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public ItemStack removeStackFromSlot(final int n) {
        ItemStack itemStack;
        if (n >= this.upperChest.getSizeInventory()) {
            itemStack = this.lowerChest.removeStackFromSlot(n - this.upperChest.getSizeInventory());
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            itemStack = this.upperChest.removeStackFromSlot(n);
        }
        return itemStack;
    }
    
    @Override
    public void openInventory(final EntityPlayer entityPlayer) {
        this.upperChest.openInventory(entityPlayer);
        this.lowerChest.openInventory(entityPlayer);
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer entityPlayer) {
        if (this.upperChest.isUseableByPlayer(entityPlayer) && this.lowerChest.isUseableByPlayer(entityPlayer)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public boolean isItemValidForSlot(final int n, final ItemStack itemStack) {
        return " ".length() != 0;
    }
    
    @Override
    public void setLockCode(final LockCode lockCode) {
        this.upperChest.setLockCode(lockCode);
        this.lowerChest.setLockCode(lockCode);
    }
    
    @Override
    public int getFieldCount() {
        return "".length();
    }
    
    @Override
    public void closeInventory(final EntityPlayer entityPlayer) {
        this.upperChest.closeInventory(entityPlayer);
        this.lowerChest.closeInventory(entityPlayer);
    }
    
    @Override
    public int getSizeInventory() {
        return this.upperChest.getSizeInventory() + this.lowerChest.getSizeInventory();
    }
    
    @Override
    public boolean isLocked() {
        if (!this.upperChest.isLocked() && !this.lowerChest.isLocked()) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    @Override
    public void setField(final int n, final int n2) {
    }
    
    @Override
    public int getInventoryStackLimit() {
        return this.upperChest.getInventoryStackLimit();
    }
    
    @Override
    public String getName() {
        String s;
        if (this.upperChest.hasCustomName()) {
            s = this.upperChest.getName();
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        else if (this.lowerChest.hasCustomName()) {
            s = this.lowerChest.getName();
            "".length();
            if (-1 == 2) {
                throw null;
            }
        }
        else {
            s = this.name;
        }
        return s;
    }
    
    @Override
    public ItemStack decrStackSize(final int n, final int n2) {
        ItemStack itemStack;
        if (n >= this.upperChest.getSizeInventory()) {
            itemStack = this.lowerChest.decrStackSize(n - this.upperChest.getSizeInventory(), n2);
            "".length();
            if (1 == 2) {
                throw null;
            }
        }
        else {
            itemStack = this.upperChest.decrStackSize(n, n2);
        }
        return itemStack;
    }
    
    @Override
    public LockCode getLockCode() {
        return this.upperChest.getLockCode();
    }
    
    @Override
    public ItemStack getStackInSlot(final int n) {
        ItemStack itemStack;
        if (n >= this.upperChest.getSizeInventory()) {
            itemStack = this.lowerChest.getStackInSlot(n - this.upperChest.getSizeInventory());
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        else {
            itemStack = this.upperChest.getStackInSlot(n);
        }
        return itemStack;
    }
    
    public InventoryLargeChest(final String name, ILockableContainer upperChest, ILockableContainer lowerChest) {
        this.name = name;
        if (upperChest == null) {
            upperChest = lowerChest;
        }
        if (lowerChest == null) {
            lowerChest = upperChest;
        }
        this.upperChest = upperChest;
        this.lowerChest = lowerChest;
        if (upperChest.isLocked()) {
            lowerChest.setLockCode(upperChest.getLockCode());
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        else if (lowerChest.isLocked()) {
            upperChest.setLockCode(lowerChest.getLockCode());
        }
    }
    
    @Override
    public int getField(final int n) {
        return "".length();
    }
    
    @Override
    public Container createContainer(final InventoryPlayer inventoryPlayer, final EntityPlayer entityPlayer) {
        return new ContainerChest(inventoryPlayer, this, entityPlayer);
    }
    
    public boolean isPartOfLargeChest(final IInventory inventory) {
        if (this.upperChest != inventory && this.lowerChest != inventory) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    @Override
    public IChatComponent getDisplayName() {
        ChatComponentStyle chatComponentStyle;
        if (this.hasCustomName()) {
            chatComponentStyle = new ChatComponentText(this.getName());
            "".length();
            if (4 < 1) {
                throw null;
            }
        }
        else {
            chatComponentStyle = new ChatComponentTranslation(this.getName(), new Object["".length()]);
        }
        return chatComponentStyle;
    }
}
