package net.minecraft.inventory;

import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;

public class InventoryCrafting implements IInventory
{
    private final int inventoryHeight;
    private static final String[] I;
    private final ItemStack[] stackList;
    private final Container eventHandler;
    private final int inventoryWidth;
    
    @Override
    public void markDirty() {
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 0x6B ^ 0x2B;
    }
    
    @Override
    public ItemStack getStackInSlot(final int n) {
        ItemStack itemStack;
        if (n >= this.getSizeInventory()) {
            itemStack = null;
            "".length();
            if (1 < 1) {
                throw null;
            }
        }
        else {
            itemStack = this.stackList[n];
        }
        return itemStack;
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer entityPlayer) {
        return " ".length() != 0;
    }
    
    @Override
    public boolean hasCustomName() {
        return "".length() != 0;
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
            if (-1 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ItemStack getStackInRowAndColumn(final int n, final int n2) {
        ItemStack stackInSlot;
        if (n >= 0 && n < this.inventoryWidth && n2 >= 0 && n2 <= this.inventoryHeight) {
            stackInSlot = this.getStackInSlot(n + n2 * this.inventoryWidth);
            "".length();
            if (4 == 1) {
                throw null;
            }
        }
        else {
            stackInSlot = null;
        }
        return stackInSlot;
    }
    
    public InventoryCrafting(final Container eventHandler, final int inventoryWidth, final int inventoryHeight) {
        this.stackList = new ItemStack[inventoryWidth * inventoryHeight];
        this.eventHandler = eventHandler;
        this.inventoryWidth = inventoryWidth;
        this.inventoryHeight = inventoryHeight;
    }
    
    @Override
    public ItemStack decrStackSize(final int n, final int n2) {
        if (this.stackList[n] == null) {
            return null;
        }
        if (this.stackList[n].stackSize <= n2) {
            final ItemStack itemStack = this.stackList[n];
            this.stackList[n] = null;
            this.eventHandler.onCraftMatrixChanged(this);
            return itemStack;
        }
        final ItemStack splitStack = this.stackList[n].splitStack(n2);
        if (this.stackList[n].stackSize == 0) {
            this.stackList[n] = null;
        }
        this.eventHandler.onCraftMatrixChanged(this);
        return splitStack;
    }
    
    @Override
    public IChatComponent getDisplayName() {
        ChatComponentStyle chatComponentStyle;
        if (this.hasCustomName()) {
            chatComponentStyle = new ChatComponentText(this.getName());
            "".length();
            if (3 < 3) {
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
        if (1 <= -1) {
            throw null;
        }
        while (i < this.stackList.length) {
            this.stackList[i] = null;
            ++i;
        }
    }
    
    @Override
    public String getName() {
        return InventoryCrafting.I["".length()];
    }
    
    @Override
    public void closeInventory(final EntityPlayer entityPlayer) {
    }
    
    @Override
    public int getFieldCount() {
        return "".length();
    }
    
    @Override
    public int getSizeInventory() {
        return this.stackList.length;
    }
    
    public int getWidth() {
        return this.inventoryWidth;
    }
    
    static {
        I();
    }
    
    @Override
    public void setField(final int n, final int n2) {
    }
    
    @Override
    public boolean isItemValidForSlot(final int n, final ItemStack itemStack) {
        return " ".length() != 0;
    }
    
    public int getHeight() {
        return this.inventoryHeight;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0011\u001d\u0005\u0012,\u001b\u001c\u000e\u0014c\u0011\u0000\n\u00009\u001b\u001c\f", "rrkfM");
    }
    
    @Override
    public void setInventorySlotContents(final int n, final ItemStack itemStack) {
        this.stackList[n] = itemStack;
        this.eventHandler.onCraftMatrixChanged(this);
    }
    
    @Override
    public ItemStack removeStackFromSlot(final int n) {
        if (this.stackList[n] != null) {
            final ItemStack itemStack = this.stackList[n];
            this.stackList[n] = null;
            return itemStack;
        }
        return null;
    }
    
    @Override
    public int getField(final int n) {
        return "".length();
    }
    
    @Override
    public void openInventory(final EntityPlayer entityPlayer) {
    }
}
