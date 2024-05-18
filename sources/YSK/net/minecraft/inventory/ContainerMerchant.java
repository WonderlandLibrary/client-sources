package net.minecraft.inventory;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;

public class ContainerMerchant extends Container
{
    private InventoryMerchant merchantInventory;
    private IMerchant theMerchant;
    private final World theWorld;
    
    @Override
    public void onCraftGuiOpened(final ICrafting crafting) {
        super.onCraftGuiOpened(crafting);
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int n) {
        ItemStack copy = null;
        final Slot slot = this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            final ItemStack stack = slot.getStack();
            copy = stack.copy();
            if (n == "  ".length()) {
                if (!this.mergeItemStack(stack, "   ".length(), 0x7B ^ 0x5C, " ".length() != 0)) {
                    return null;
                }
                slot.onSlotChange(stack, copy);
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else if (n != 0 && n != " ".length()) {
                if (n >= "   ".length() && n < (0x8F ^ 0x91)) {
                    if (!this.mergeItemStack(stack, 0x8B ^ 0x95, 0x6D ^ 0x4A, "".length() != 0)) {
                        return null;
                    }
                }
                else if (n >= (0x32 ^ 0x2C) && n < (0x2E ^ 0x9) && !this.mergeItemStack(stack, "   ".length(), 0x8E ^ 0x90, "".length() != 0)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(stack, "   ".length(), 0x30 ^ 0x17, "".length() != 0)) {
                return null;
            }
            if (stack.stackSize == 0) {
                slot.putStack(null);
                "".length();
                if (2 < 2) {
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
    
    public InventoryMerchant getMerchantInventory() {
        return this.merchantInventory;
    }
    
    @Override
    public void onCraftMatrixChanged(final IInventory inventory) {
        this.merchantInventory.resetRecipeAndSlots();
        super.onCraftMatrixChanged(inventory);
    }
    
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
    }
    
    @Override
    public void updateProgressBar(final int n, final int n2) {
    }
    
    public void setCurrentRecipeIndex(final int currentRecipeIndex) {
        this.merchantInventory.setCurrentRecipeIndex(currentRecipeIndex);
    }
    
    public ContainerMerchant(final InventoryPlayer inventoryPlayer, final IMerchant theMerchant, final World theWorld) {
        this.theMerchant = theMerchant;
        this.theWorld = theWorld;
        this.merchantInventory = new InventoryMerchant(inventoryPlayer.player, theMerchant);
        this.addSlotToContainer(new Slot(this.merchantInventory, "".length(), 0x4B ^ 0x6F, 0xA0 ^ 0x95));
        this.addSlotToContainer(new Slot(this.merchantInventory, " ".length(), 0x88 ^ 0xB6, 0x7A ^ 0x4F));
        this.addSlotToContainer(new SlotMerchantResult(inventoryPlayer.player, theMerchant, this.merchantInventory, "  ".length(), 0x2 ^ 0x7A, 0xBD ^ 0x88));
        int i = "".length();
        "".length();
        if (1 <= -1) {
            throw null;
        }
        while (i < "   ".length()) {
            int j = "".length();
            "".length();
            if (1 >= 3) {
                throw null;
            }
            while (j < (0x5E ^ 0x57)) {
                this.addSlotToContainer(new Slot(inventoryPlayer, j + i * (0x11 ^ 0x18) + (0x57 ^ 0x5E), (0x24 ^ 0x2C) + j * (0xA3 ^ 0xB1), (0x16 ^ 0x42) + i * (0x95 ^ 0x87)));
                ++j;
            }
            ++i;
        }
        int k = "".length();
        "".length();
        if (0 == 2) {
            throw null;
        }
        while (k < (0x10 ^ 0x19)) {
            this.addSlotToContainer(new Slot(inventoryPlayer, k, (0x3D ^ 0x35) + k * (0x7C ^ 0x6E), 114 + 90 - 180 + 118));
            ++k;
        }
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer entityPlayer) {
        if (this.theMerchant.getCustomer() == entityPlayer) {
            return " ".length() != 0;
        }
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
            if (3 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void onContainerClosed(final EntityPlayer entityPlayer) {
        super.onContainerClosed(entityPlayer);
        this.theMerchant.setCustomer(null);
        super.onContainerClosed(entityPlayer);
        if (!this.theWorld.isRemote) {
            final ItemStack removeStackFromSlot = this.merchantInventory.removeStackFromSlot("".length());
            if (removeStackFromSlot != null) {
                entityPlayer.dropPlayerItemWithRandomChoice(removeStackFromSlot, "".length() != 0);
            }
            final ItemStack removeStackFromSlot2 = this.merchantInventory.removeStackFromSlot(" ".length());
            if (removeStackFromSlot2 != null) {
                entityPlayer.dropPlayerItemWithRandomChoice(removeStackFromSlot2, "".length() != 0);
            }
        }
    }
}
