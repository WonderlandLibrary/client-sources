package net.minecraft.inventory;

import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;

public class InventoryCraftResult implements IInventory
{
    private static final String[] I;
    private ItemStack[] stackResult;
    
    @Override
    public int getFieldCount() {
        return "".length();
    }
    
    @Override
    public ItemStack getStackInSlot(final int n) {
        return this.stackResult["".length()];
    }
    
    @Override
    public void setInventorySlotContents(final int n, final ItemStack itemStack) {
        this.stackResult["".length()] = itemStack;
    }
    
    @Override
    public void closeInventory(final EntityPlayer entityPlayer) {
    }
    
    static {
        I();
    }
    
    @Override
    public void setField(final int n, final int n2) {
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
            if (2 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getSizeInventory() {
        return " ".length();
    }
    
    @Override
    public String getName() {
        return InventoryCraftResult.I["".length()];
    }
    
    @Override
    public boolean hasCustomName() {
        return "".length() != 0;
    }
    
    @Override
    public int getField(final int n) {
        return "".length();
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 0xE0 ^ 0xA0;
    }
    
    @Override
    public IChatComponent getDisplayName() {
        ChatComponentStyle chatComponentStyle;
        if (this.hasCustomName()) {
            chatComponentStyle = new ChatComponentText(this.getName());
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        else {
            chatComponentStyle = new ChatComponentTranslation(this.getName(), new Object["".length()]);
        }
        return chatComponentStyle;
    }
    
    @Override
    public void clear() {
        int i = "".length();
        "".length();
        if (2 == 4) {
            throw null;
        }
        while (i < this.stackResult.length) {
            this.stackResult[i] = null;
            ++i;
        }
    }
    
    @Override
    public ItemStack removeStackFromSlot(final int n) {
        if (this.stackResult["".length()] != null) {
            final ItemStack itemStack = this.stackResult["".length()];
            this.stackResult["".length()] = null;
            return itemStack;
        }
        return null;
    }
    
    @Override
    public boolean isItemValidForSlot(final int n, final ItemStack itemStack) {
        return " ".length() != 0;
    }
    
    @Override
    public void openInventory(final EntityPlayer entityPlayer) {
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0011\b\u001f\u0007 7", "CmlrL");
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer entityPlayer) {
        return " ".length() != 0;
    }
    
    @Override
    public void markDirty() {
    }
    
    public InventoryCraftResult() {
        this.stackResult = new ItemStack[" ".length()];
    }
    
    @Override
    public ItemStack decrStackSize(final int n, final int n2) {
        if (this.stackResult["".length()] != null) {
            final ItemStack itemStack = this.stackResult["".length()];
            this.stackResult["".length()] = null;
            return itemStack;
        }
        return null;
    }
}
