package net.minecraft.inventory;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.*;
import net.minecraft.tileentity.*;

public class ContainerFurnace extends Container
{
    private int field_178154_h;
    private final IInventory tileFurnace;
    private int field_178152_f;
    private int field_178153_g;
    private int field_178155_i;
    
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        int i = "".length();
        "".length();
        if (2 < 2) {
            throw null;
        }
        while (i < this.crafters.size()) {
            final ICrafting crafting = this.crafters.get(i);
            if (this.field_178152_f != this.tileFurnace.getField("  ".length())) {
                crafting.sendProgressBarUpdate(this, "  ".length(), this.tileFurnace.getField("  ".length()));
            }
            if (this.field_178154_h != this.tileFurnace.getField("".length())) {
                crafting.sendProgressBarUpdate(this, "".length(), this.tileFurnace.getField("".length()));
            }
            if (this.field_178155_i != this.tileFurnace.getField(" ".length())) {
                crafting.sendProgressBarUpdate(this, " ".length(), this.tileFurnace.getField(" ".length()));
            }
            if (this.field_178153_g != this.tileFurnace.getField("   ".length())) {
                crafting.sendProgressBarUpdate(this, "   ".length(), this.tileFurnace.getField("   ".length()));
            }
            ++i;
        }
        this.field_178152_f = this.tileFurnace.getField("  ".length());
        this.field_178154_h = this.tileFurnace.getField("".length());
        this.field_178155_i = this.tileFurnace.getField(" ".length());
        this.field_178153_g = this.tileFurnace.getField("   ".length());
    }
    
    public ContainerFurnace(final InventoryPlayer inventoryPlayer, final IInventory tileFurnace) {
        this.tileFurnace = tileFurnace;
        this.addSlotToContainer(new Slot(tileFurnace, "".length(), 0x7E ^ 0x46, 0xA1 ^ 0xB0));
        this.addSlotToContainer(new SlotFurnaceFuel(tileFurnace, " ".length(), 0x95 ^ 0xAD, 0x84 ^ 0xB1));
        this.addSlotToContainer(new SlotFurnaceOutput(inventoryPlayer.player, tileFurnace, "  ".length(), 0xB ^ 0x7F, 0x98 ^ 0xBB));
        int i = "".length();
        "".length();
        if (3 < -1) {
            throw null;
        }
        while (i < "   ".length()) {
            int j = "".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
            while (j < (0x84 ^ 0x8D)) {
                this.addSlotToContainer(new Slot(inventoryPlayer, j + i * (0x21 ^ 0x28) + (0x95 ^ 0x9C), (0xAC ^ 0xA4) + j * (0xD3 ^ 0xC1), (0x74 ^ 0x20) + i * (0xB ^ 0x19)));
                ++j;
            }
            ++i;
        }
        int k = "".length();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (k < (0x88 ^ 0x81)) {
            this.addSlotToContainer(new Slot(inventoryPlayer, k, (0x97 ^ 0x9F) + k * (0x80 ^ 0x92), 13 + 8 + 77 + 44));
            ++k;
        }
    }
    
    @Override
    public void updateProgressBar(final int n, final int n2) {
        this.tileFurnace.setField(n, n2);
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int n) {
        ItemStack copy = null;
        final Slot slot = this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            final ItemStack stack = slot.getStack();
            copy = stack.copy();
            if (n == "  ".length()) {
                if (!this.mergeItemStack(stack, "   ".length(), 0x99 ^ 0xBE, " ".length() != 0)) {
                    return null;
                }
                slot.onSlotChange(stack, copy);
                "".length();
                if (2 == 3) {
                    throw null;
                }
            }
            else if (n != " ".length() && n != 0) {
                if (FurnaceRecipes.instance().getSmeltingResult(stack) != null) {
                    if (!this.mergeItemStack(stack, "".length(), " ".length(), "".length() != 0)) {
                        return null;
                    }
                }
                else if (TileEntityFurnace.isItemFuel(stack)) {
                    if (!this.mergeItemStack(stack, " ".length(), "  ".length(), "".length() != 0)) {
                        return null;
                    }
                }
                else if (n >= "   ".length() && n < (0xB9 ^ 0xA7)) {
                    if (!this.mergeItemStack(stack, 0x21 ^ 0x3F, 0x55 ^ 0x72, "".length() != 0)) {
                        return null;
                    }
                }
                else if (n >= (0x31 ^ 0x2F) && n < (0x4F ^ 0x68) && !this.mergeItemStack(stack, "   ".length(), 0xB1 ^ 0xAF, "".length() != 0)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(stack, "   ".length(), 0x19 ^ 0x3E, "".length() != 0)) {
                return null;
            }
            if (stack.stackSize == 0) {
                slot.putStack(null);
                "".length();
                if (false) {
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
    public void onCraftGuiOpened(final ICrafting crafting) {
        super.onCraftGuiOpened(crafting);
        crafting.func_175173_a(this, this.tileFurnace);
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer entityPlayer) {
        return this.tileFurnace.isUseableByPlayer(entityPlayer);
    }
}
