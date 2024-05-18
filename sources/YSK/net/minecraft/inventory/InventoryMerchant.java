package net.minecraft.inventory;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.village.*;
import net.minecraft.util.*;

public class InventoryMerchant implements IInventory
{
    private final IMerchant theMerchant;
    private MerchantRecipe currentRecipe;
    private static final String[] I;
    private final EntityPlayer thePlayer;
    private ItemStack[] theInventory;
    private int currentRecipeIndex;
    
    @Override
    public void markDirty() {
        this.resetRecipeAndSlots();
    }
    
    public InventoryMerchant(final EntityPlayer thePlayer, final IMerchant theMerchant) {
        this.theInventory = new ItemStack["   ".length()];
        this.thePlayer = thePlayer;
        this.theMerchant = theMerchant;
    }
    
    @Override
    public String getName() {
        return InventoryMerchant.I["".length()];
    }
    
    public void resetRecipeAndSlots() {
        this.currentRecipe = null;
        ItemStack itemStack = this.theInventory["".length()];
        ItemStack itemStack2 = this.theInventory[" ".length()];
        if (itemStack == null) {
            itemStack = itemStack2;
            itemStack2 = null;
        }
        if (itemStack == null) {
            this.setInventorySlotContents("  ".length(), null);
            "".length();
            if (-1 == 2) {
                throw null;
            }
        }
        else {
            final MerchantRecipeList recipes = this.theMerchant.getRecipes(this.thePlayer);
            if (recipes != null) {
                final MerchantRecipe canRecipeBeUsed = recipes.canRecipeBeUsed(itemStack, itemStack2, this.currentRecipeIndex);
                if (canRecipeBeUsed != null && !canRecipeBeUsed.isRecipeDisabled()) {
                    this.currentRecipe = canRecipeBeUsed;
                    this.setInventorySlotContents("  ".length(), canRecipeBeUsed.getItemToSell().copy());
                    "".length();
                    if (-1 == 4) {
                        throw null;
                    }
                }
                else if (itemStack2 != null) {
                    final MerchantRecipe canRecipeBeUsed2 = recipes.canRecipeBeUsed(itemStack2, itemStack, this.currentRecipeIndex);
                    if (canRecipeBeUsed2 != null && !canRecipeBeUsed2.isRecipeDisabled()) {
                        this.currentRecipe = canRecipeBeUsed2;
                        this.setInventorySlotContents("  ".length(), canRecipeBeUsed2.getItemToSell().copy());
                        "".length();
                        if (3 != 3) {
                            throw null;
                        }
                    }
                    else {
                        this.setInventorySlotContents("  ".length(), null);
                        "".length();
                        if (3 != 3) {
                            throw null;
                        }
                    }
                }
                else {
                    this.setInventorySlotContents("  ".length(), null);
                }
            }
        }
        this.theMerchant.verifySellingItem(this.getStackInSlot("  ".length()));
    }
    
    @Override
    public boolean hasCustomName() {
        return "".length() != 0;
    }
    
    @Override
    public int getSizeInventory() {
        return this.theInventory.length;
    }
    
    @Override
    public void closeInventory(final EntityPlayer entityPlayer) {
    }
    
    @Override
    public int getFieldCount() {
        return "".length();
    }
    
    @Override
    public IChatComponent getDisplayName() {
        ChatComponentStyle chatComponentStyle;
        if (this.hasCustomName()) {
            chatComponentStyle = new ChatComponentText(this.getName());
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            chatComponentStyle = new ChatComponentTranslation(this.getName(), new Object["".length()]);
        }
        return chatComponentStyle;
    }
    
    @Override
    public ItemStack decrStackSize(final int n, final int n2) {
        if (this.theInventory[n] == null) {
            return null;
        }
        if (n == "  ".length()) {
            final ItemStack itemStack = this.theInventory[n];
            this.theInventory[n] = null;
            return itemStack;
        }
        if (this.theInventory[n].stackSize <= n2) {
            final ItemStack itemStack2 = this.theInventory[n];
            this.theInventory[n] = null;
            if (this.inventoryResetNeededOnSlotChange(n)) {
                this.resetRecipeAndSlots();
            }
            return itemStack2;
        }
        final ItemStack splitStack = this.theInventory[n].splitStack(n2);
        if (this.theInventory[n].stackSize == 0) {
            this.theInventory[n] = null;
        }
        if (this.inventoryResetNeededOnSlotChange(n)) {
            this.resetRecipeAndSlots();
        }
        return splitStack;
    }
    
    private boolean inventoryResetNeededOnSlotChange(final int n) {
        if (n != 0 && n != " ".length()) {
            return "".length() != 0;
        }
        return " ".length() != 0;
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
            if (3 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void setInventorySlotContents(final int n, final ItemStack itemStack) {
        this.theInventory[n] = itemStack;
        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
        if (this.inventoryResetNeededOnSlotChange(n)) {
            this.resetRecipeAndSlots();
        }
    }
    
    @Override
    public int getField(final int n) {
        return "".length();
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 0xC ^ 0x4C;
    }
    
    @Override
    public boolean isItemValidForSlot(final int n, final ItemStack itemStack) {
        return " ".length() != 0;
    }
    
    @Override
    public void clear() {
        int i = "".length();
        "".length();
        if (4 < 4) {
            throw null;
        }
        while (i < this.theInventory.length) {
            this.theInventory[i] = null;
            ++i;
        }
    }
    
    public MerchantRecipe getCurrentRecipe() {
        return this.currentRecipe;
    }
    
    @Override
    public ItemStack removeStackFromSlot(final int n) {
        if (this.theInventory[n] != null) {
            final ItemStack itemStack = this.theInventory[n];
            this.theInventory[n] = null;
            return itemStack;
        }
        return null;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0007\u0007\u001aY\u0003\u0003\u0004\u0014\u0016\u0012\u000f\u001a", "jhxwu");
    }
    
    @Override
    public void openInventory(final EntityPlayer entityPlayer) {
    }
    
    @Override
    public void setField(final int n, final int n2) {
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer entityPlayer) {
        if (this.theMerchant.getCustomer() == entityPlayer) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    static {
        I();
    }
    
    public void setCurrentRecipeIndex(final int currentRecipeIndex) {
        this.currentRecipeIndex = currentRecipeIndex;
        this.resetRecipeAndSlots();
    }
    
    @Override
    public ItemStack getStackInSlot(final int n) {
        return this.theInventory[n];
    }
}
