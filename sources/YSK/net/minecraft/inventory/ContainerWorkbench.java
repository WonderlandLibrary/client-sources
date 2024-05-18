package net.minecraft.inventory;

import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.crafting.*;

public class ContainerWorkbench extends Container
{
    public InventoryCrafting craftMatrix;
    private BlockPos pos;
    public IInventory craftResult;
    private World worldObj;
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int n) {
        ItemStack copy = null;
        final Slot slot = this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            final ItemStack stack = slot.getStack();
            copy = stack.copy();
            if (n == 0) {
                if (!this.mergeItemStack(stack, 0x20 ^ 0x2A, 0x2E ^ 0x0, " ".length() != 0)) {
                    return null;
                }
                slot.onSlotChange(stack, copy);
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else if (n >= (0x11 ^ 0x1B) && n < (0x15 ^ 0x30)) {
                if (!this.mergeItemStack(stack, 0xAB ^ 0x8E, 0x16 ^ 0x38, "".length() != 0)) {
                    return null;
                }
            }
            else if (n >= (0x78 ^ 0x5D) && n < (0x79 ^ 0x57)) {
                if (!this.mergeItemStack(stack, 0x3E ^ 0x34, 0x72 ^ 0x57, "".length() != 0)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(stack, 0x38 ^ 0x32, 0x6B ^ 0x45, "".length() != 0)) {
                return null;
            }
            if (stack.stackSize == 0) {
                slot.putStack(null);
                "".length();
                if (-1 == 3) {
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
    
    @Override
    public void onContainerClosed(final EntityPlayer entityPlayer) {
        super.onContainerClosed(entityPlayer);
        if (!this.worldObj.isRemote) {
            int i = "".length();
            "".length();
            if (3 == 4) {
                throw null;
            }
            while (i < (0x4E ^ 0x47)) {
                final ItemStack removeStackFromSlot = this.craftMatrix.removeStackFromSlot(i);
                if (removeStackFromSlot != null) {
                    entityPlayer.dropPlayerItemWithRandomChoice(removeStackFromSlot, "".length() != 0);
                }
                ++i;
            }
        }
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer entityPlayer) {
        int n;
        if (this.worldObj.getBlockState(this.pos).getBlock() != Blocks.crafting_table) {
            n = "".length();
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else if (entityPlayer.getDistanceSq(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64.0) {
            n = " ".length();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public ContainerWorkbench(final InventoryPlayer inventoryPlayer, final World worldObj, final BlockPos pos) {
        this.craftMatrix = new InventoryCrafting(this, "   ".length(), "   ".length());
        this.craftResult = new InventoryCraftResult();
        this.worldObj = worldObj;
        this.pos = pos;
        this.addSlotToContainer(new SlotCrafting(inventoryPlayer.player, this.craftMatrix, this.craftResult, "".length(), 0x7E ^ 0x2, 0x52 ^ 0x71));
        int i = "".length();
        "".length();
        if (1 <= 0) {
            throw null;
        }
        while (i < "   ".length()) {
            int j = "".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
            while (j < "   ".length()) {
                this.addSlotToContainer(new Slot(this.craftMatrix, j + i * "   ".length(), (0x4E ^ 0x50) + j * (0x2 ^ 0x10), (0x51 ^ 0x40) + i * (0x7D ^ 0x6F)));
                ++j;
            }
            ++i;
        }
        int k = "".length();
        "".length();
        if (0 == 3) {
            throw null;
        }
        while (k < "   ".length()) {
            int l = "".length();
            "".length();
            if (3 < 3) {
                throw null;
            }
            while (l < (0xBF ^ 0xB6)) {
                this.addSlotToContainer(new Slot(inventoryPlayer, l + k * (0x85 ^ 0x8C) + (0x95 ^ 0x9C), (0x9F ^ 0x97) + l * (0x75 ^ 0x67), (0x15 ^ 0x41) + k * (0xA0 ^ 0xB2)));
                ++l;
            }
            ++k;
        }
        int length = "".length();
        "".length();
        if (3 == -1) {
            throw null;
        }
        while (length < (0x8A ^ 0x83)) {
            this.addSlotToContainer(new Slot(inventoryPlayer, length, (0x5 ^ 0xD) + length * (0x4F ^ 0x5D), 14 + 48 - 14 + 94));
            ++length;
        }
        this.onCraftMatrixChanged(this.craftMatrix);
    }
    
    @Override
    public boolean canMergeSlot(final ItemStack itemStack, final Slot slot) {
        if (slot.inventory != this.craftResult && super.canMergeSlot(itemStack, slot)) {
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
            if (-1 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void onCraftMatrixChanged(final IInventory inventory) {
        this.craftResult.setInventorySlotContents("".length(), CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.worldObj));
    }
}
