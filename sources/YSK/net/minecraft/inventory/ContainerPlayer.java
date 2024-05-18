package net.minecraft.inventory;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.item.crafting.*;

public class ContainerPlayer extends Container
{
    private final EntityPlayer thePlayer;
    public InventoryCrafting craftMatrix;
    public boolean isLocalWorld;
    public IInventory craftResult;
    
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
            if (4 < 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ContainerPlayer(final InventoryPlayer inventoryPlayer, final boolean isLocalWorld, final EntityPlayer thePlayer) {
        this.craftMatrix = new InventoryCrafting(this, "  ".length(), "  ".length());
        this.craftResult = new InventoryCraftResult();
        this.isLocalWorld = isLocalWorld;
        this.thePlayer = thePlayer;
        this.addSlotToContainer(new SlotCrafting(inventoryPlayer.player, this.craftMatrix, this.craftResult, "".length(), 104 + 4 - 7 + 43, 0x96 ^ 0xB2));
        int i = "".length();
        "".length();
        if (0 >= 4) {
            throw null;
        }
        while (i < "  ".length()) {
            int j = "".length();
            "".length();
            if (0 >= 4) {
                throw null;
            }
            while (j < "  ".length()) {
                this.addSlotToContainer(new Slot(this.craftMatrix, j + i * "  ".length(), (0xD1 ^ 0x89) + j * (0x35 ^ 0x27), (0x27 ^ 0x3D) + i * (0x66 ^ 0x74)));
                ++j;
            }
            ++i;
        }
        int k = "".length();
        "".length();
        if (-1 < -1) {
            throw null;
        }
        while (k < (0x5A ^ 0x5E)) {
            this.addSlotToContainer(new Slot(this, inventoryPlayer, inventoryPlayer.getSizeInventory() - " ".length() - k, 0x6F ^ 0x67, (0xA5 ^ 0xAD) + k * (0x54 ^ 0x46), k) {
                private final int val$k_f;
                final ContainerPlayer this$0;
                
                @Override
                public boolean isItemValid(final ItemStack itemStack) {
                    int n;
                    if (itemStack == null) {
                        n = "".length();
                        "".length();
                        if (-1 >= 2) {
                            throw null;
                        }
                    }
                    else if (itemStack.getItem() instanceof ItemArmor) {
                        if (((ItemArmor)itemStack.getItem()).armorType == this.val$k_f) {
                            n = " ".length();
                            "".length();
                            if (4 == 1) {
                                throw null;
                            }
                        }
                        else {
                            n = "".length();
                            "".length();
                            if (0 <= -1) {
                                throw null;
                            }
                        }
                    }
                    else if (itemStack.getItem() != Item.getItemFromBlock(Blocks.pumpkin) && itemStack.getItem() != Items.skull) {
                        n = "".length();
                        "".length();
                        if (3 >= 4) {
                            throw null;
                        }
                    }
                    else if (this.val$k_f == 0) {
                        n = " ".length();
                        "".length();
                        if (0 >= 2) {
                            throw null;
                        }
                    }
                    else {
                        n = "".length();
                    }
                    return n != 0;
                }
                
                @Override
                public int getSlotStackLimit() {
                    return " ".length();
                }
                
                @Override
                public String getSlotTexture() {
                    return ItemArmor.EMPTY_SLOT_NAMES[this.val$k_f];
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
                        if (0 <= -1) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
            });
            ++k;
        }
        int l = "".length();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (l < "   ".length()) {
            int length = "".length();
            "".length();
            if (1 >= 4) {
                throw null;
            }
            while (length < (0xBF ^ 0xB6)) {
                this.addSlotToContainer(new Slot(inventoryPlayer, length + (l + " ".length()) * (0xB3 ^ 0xBA), (0xAD ^ 0xA5) + length * (0xC ^ 0x1E), (0xE9 ^ 0xBD) + l * (0x2C ^ 0x3E)));
                ++length;
            }
            ++l;
        }
        int length2 = "".length();
        "".length();
        if (2 < 1) {
            throw null;
        }
        while (length2 < (0x30 ^ 0x39)) {
            this.addSlotToContainer(new Slot(inventoryPlayer, length2, (0x3 ^ 0xB) + length2 * (0x37 ^ 0x25), 71 + 46 - 26 + 51));
            ++length2;
        }
        this.onCraftMatrixChanged(this.craftMatrix);
    }
    
    @Override
    public void onCraftMatrixChanged(final IInventory inventory) {
        this.craftResult.setInventorySlotContents("".length(), CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.thePlayer.worldObj));
    }
    
    @Override
    public boolean canMergeSlot(final ItemStack itemStack, final Slot slot) {
        if (slot.inventory != this.craftResult && super.canMergeSlot(itemStack, slot)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void onContainerClosed(final EntityPlayer entityPlayer) {
        super.onContainerClosed(entityPlayer);
        int i = "".length();
        "".length();
        if (3 == 4) {
            throw null;
        }
        while (i < (0xAE ^ 0xAA)) {
            final ItemStack removeStackFromSlot = this.craftMatrix.removeStackFromSlot(i);
            if (removeStackFromSlot != null) {
                entityPlayer.dropPlayerItemWithRandomChoice(removeStackFromSlot, "".length() != 0);
            }
            ++i;
        }
        this.craftResult.setInventorySlotContents("".length(), null);
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer entityPlayer) {
        return " ".length() != 0;
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int n) {
        ItemStack copy = null;
        final Slot slot = this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            final ItemStack stack = slot.getStack();
            copy = stack.copy();
            if (n == 0) {
                if (!this.mergeItemStack(stack, 0x96 ^ 0x9F, 0x3 ^ 0x2E, " ".length() != 0)) {
                    return null;
                }
                slot.onSlotChange(stack, copy);
                "".length();
                if (-1 >= 0) {
                    throw null;
                }
            }
            else if (n >= " ".length() && n < (0x3F ^ 0x3A)) {
                if (!this.mergeItemStack(stack, 0x66 ^ 0x6F, 0x7A ^ 0x57, "".length() != 0)) {
                    return null;
                }
            }
            else if (n >= (0xC5 ^ 0xC0) && n < (0x26 ^ 0x2F)) {
                if (!this.mergeItemStack(stack, 0x72 ^ 0x7B, 0x4F ^ 0x62, "".length() != 0)) {
                    return null;
                }
            }
            else if (copy.getItem() instanceof ItemArmor && !this.inventorySlots.get((0xA9 ^ 0xAC) + ((ItemArmor)copy.getItem()).armorType).getHasStack()) {
                final int n2 = (0x73 ^ 0x76) + ((ItemArmor)copy.getItem()).armorType;
                if (!this.mergeItemStack(stack, n2, n2 + " ".length(), "".length() != 0)) {
                    return null;
                }
            }
            else if (n >= (0x90 ^ 0x99) && n < (0xB2 ^ 0x96)) {
                if (!this.mergeItemStack(stack, 0x50 ^ 0x74, 0x89 ^ 0xA4, "".length() != 0)) {
                    return null;
                }
            }
            else if (n >= (0xB4 ^ 0x90) && n < (0x96 ^ 0xBB)) {
                if (!this.mergeItemStack(stack, 0x6C ^ 0x65, 0x3 ^ 0x27, "".length() != 0)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(stack, 0x1 ^ 0x8, 0x50 ^ 0x7D, "".length() != 0)) {
                return null;
            }
            if (stack.stackSize == 0) {
                slot.putStack(null);
                "".length();
                if (0 <= -1) {
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
}
