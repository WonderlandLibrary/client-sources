package net.minecraft.inventory;

import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.stats.*;
import net.minecraft.village.*;

public class SlotMerchantResult extends Slot
{
    private final IMerchant theMerchant;
    private EntityPlayer thePlayer;
    private final InventoryMerchant theMerchantInventory;
    private int field_75231_g;
    
    @Override
    protected void onCrafting(final ItemStack itemStack, final int n) {
        this.field_75231_g += n;
        this.onCrafting(itemStack);
    }
    
    @Override
    protected void onCrafting(final ItemStack itemStack) {
        itemStack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.field_75231_g);
        this.field_75231_g = "".length();
    }
    
    @Override
    public ItemStack decrStackSize(final int n) {
        if (this.getHasStack()) {
            this.field_75231_g += Math.min(n, this.getStack().stackSize);
        }
        return super.decrStackSize(n);
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
            if (0 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void onPickupFromSlot(final EntityPlayer entityPlayer, final ItemStack itemStack) {
        this.onCrafting(itemStack);
        final MerchantRecipe currentRecipe = this.theMerchantInventory.getCurrentRecipe();
        if (currentRecipe != null) {
            ItemStack stackInSlot = this.theMerchantInventory.getStackInSlot("".length());
            ItemStack stackInSlot2 = this.theMerchantInventory.getStackInSlot(" ".length());
            if (this.doTrade(currentRecipe, stackInSlot, stackInSlot2) || this.doTrade(currentRecipe, stackInSlot2, stackInSlot)) {
                this.theMerchant.useRecipe(currentRecipe);
                entityPlayer.triggerAchievement(StatList.timesTradedWithVillagerStat);
                if (stackInSlot != null && stackInSlot.stackSize <= 0) {
                    stackInSlot = null;
                }
                if (stackInSlot2 != null && stackInSlot2.stackSize <= 0) {
                    stackInSlot2 = null;
                }
                this.theMerchantInventory.setInventorySlotContents("".length(), stackInSlot);
                this.theMerchantInventory.setInventorySlotContents(" ".length(), stackInSlot2);
            }
        }
    }
    
    public SlotMerchantResult(final EntityPlayer thePlayer, final IMerchant theMerchant, final InventoryMerchant theMerchantInventory, final int n, final int n2, final int n3) {
        super(theMerchantInventory, n, n2, n3);
        this.thePlayer = thePlayer;
        this.theMerchant = theMerchant;
        this.theMerchantInventory = theMerchantInventory;
    }
    
    private boolean doTrade(final MerchantRecipe merchantRecipe, final ItemStack itemStack, final ItemStack itemStack2) {
        final ItemStack itemToBuy = merchantRecipe.getItemToBuy();
        final ItemStack secondItemToBuy = merchantRecipe.getSecondItemToBuy();
        if (itemStack != null && itemStack.getItem() == itemToBuy.getItem()) {
            if (secondItemToBuy != null && itemStack2 != null && secondItemToBuy.getItem() == itemStack2.getItem()) {
                itemStack.stackSize -= itemToBuy.stackSize;
                itemStack2.stackSize -= secondItemToBuy.stackSize;
                return " ".length() != 0;
            }
            if (secondItemToBuy == null && itemStack2 == null) {
                itemStack.stackSize -= itemToBuy.stackSize;
                return " ".length() != 0;
            }
        }
        return "".length() != 0;
    }
    
    @Override
    public boolean isItemValid(final ItemStack itemStack) {
        return "".length() != 0;
    }
}
