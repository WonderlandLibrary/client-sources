package net.minecraft.inventory;

import net.minecraft.item.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import com.google.common.collect.*;

public class InventoryBasic implements IInventory
{
    private String inventoryTitle;
    private boolean hasCustomName;
    private int slotsCount;
    private ItemStack[] inventoryContents;
    private List<IInvBasic> field_70480_d;
    
    @Override
    public void closeInventory(final EntityPlayer entityPlayer) {
    }
    
    @Override
    public IChatComponent getDisplayName() {
        ChatComponentStyle chatComponentStyle;
        if (this.hasCustomName()) {
            chatComponentStyle = new ChatComponentText(this.getName());
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else {
            chatComponentStyle = new ChatComponentTranslation(this.getName(), new Object["".length()]);
        }
        return chatComponentStyle;
    }
    
    @Override
    public int getFieldCount() {
        return "".length();
    }
    
    @Override
    public void setInventorySlotContents(final int n, final ItemStack itemStack) {
        this.inventoryContents[n] = itemStack;
        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
        this.markDirty();
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 0x4D ^ 0xD;
    }
    
    public void func_110134_a(final IInvBasic invBasic) {
        if (this.field_70480_d == null) {
            this.field_70480_d = (List<IInvBasic>)Lists.newArrayList();
        }
        this.field_70480_d.add(invBasic);
    }
    
    public void setCustomName(final String inventoryTitle) {
        this.hasCustomName = (" ".length() != 0);
        this.inventoryTitle = inventoryTitle;
    }
    
    @Override
    public boolean hasCustomName() {
        return this.hasCustomName;
    }
    
    public ItemStack func_174894_a(final ItemStack itemStack) {
        final ItemStack copy = itemStack.copy();
        int i = "".length();
        "".length();
        if (4 == -1) {
            throw null;
        }
        while (i < this.slotsCount) {
            final ItemStack stackInSlot = this.getStackInSlot(i);
            if (stackInSlot == null) {
                this.setInventorySlotContents(i, copy);
                this.markDirty();
                return null;
            }
            if (ItemStack.areItemsEqual(stackInSlot, copy)) {
                final int min = Math.min(copy.stackSize, Math.min(this.getInventoryStackLimit(), stackInSlot.getMaxStackSize()) - stackInSlot.stackSize);
                if (min > 0) {
                    final ItemStack itemStack2 = stackInSlot;
                    itemStack2.stackSize += min;
                    final ItemStack itemStack3 = copy;
                    itemStack3.stackSize -= min;
                    if (copy.stackSize <= 0) {
                        this.markDirty();
                        return null;
                    }
                }
            }
            ++i;
        }
        if (copy.stackSize != itemStack.stackSize) {
            this.markDirty();
        }
        return copy;
    }
    
    @Override
    public void openInventory(final EntityPlayer entityPlayer) {
    }
    
    @Override
    public ItemStack decrStackSize(final int n, final int n2) {
        if (this.inventoryContents[n] == null) {
            return null;
        }
        if (this.inventoryContents[n].stackSize <= n2) {
            final ItemStack itemStack = this.inventoryContents[n];
            this.inventoryContents[n] = null;
            this.markDirty();
            return itemStack;
        }
        final ItemStack splitStack = this.inventoryContents[n].splitStack(n2);
        if (this.inventoryContents[n].stackSize == 0) {
            this.inventoryContents[n] = null;
        }
        this.markDirty();
        return splitStack;
    }
    
    @Override
    public String getName() {
        return this.inventoryTitle;
    }
    
    public InventoryBasic(final IChatComponent chatComponent, final int n) {
        this(chatComponent.getUnformattedText(), " ".length() != 0, n);
    }
    
    @Override
    public void clear() {
        int i = "".length();
        "".length();
        if (1 == -1) {
            throw null;
        }
        while (i < this.inventoryContents.length) {
            this.inventoryContents[i] = null;
            ++i;
        }
    }
    
    @Override
    public ItemStack getStackInSlot(final int n) {
        ItemStack itemStack;
        if (n >= 0 && n < this.inventoryContents.length) {
            itemStack = this.inventoryContents[n];
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else {
            itemStack = null;
        }
        return itemStack;
    }
    
    public void func_110132_b(final IInvBasic invBasic) {
        this.field_70480_d.remove(invBasic);
    }
    
    @Override
    public void setField(final int n, final int n2) {
    }
    
    @Override
    public boolean isItemValidForSlot(final int n, final ItemStack itemStack) {
        return " ".length() != 0;
    }
    
    @Override
    public int getSizeInventory() {
        return this.slotsCount;
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
            if (2 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void markDirty() {
        if (this.field_70480_d != null) {
            int i = "".length();
            "".length();
            if (!true) {
                throw null;
            }
            while (i < this.field_70480_d.size()) {
                this.field_70480_d.get(i).onInventoryChanged(this);
                ++i;
            }
        }
    }
    
    @Override
    public int getField(final int n) {
        return "".length();
    }
    
    @Override
    public ItemStack removeStackFromSlot(final int n) {
        if (this.inventoryContents[n] != null) {
            final ItemStack itemStack = this.inventoryContents[n];
            this.inventoryContents[n] = null;
            return itemStack;
        }
        return null;
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer entityPlayer) {
        return " ".length() != 0;
    }
    
    public InventoryBasic(final String inventoryTitle, final boolean hasCustomName, final int slotsCount) {
        this.inventoryTitle = inventoryTitle;
        this.hasCustomName = hasCustomName;
        this.slotsCount = slotsCount;
        this.inventoryContents = new ItemStack[slotsCount];
    }
}
