package net.minecraft.inventory;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.stats.*;

public class ContainerBrewingStand extends Container
{
    private int brewTime;
    private IInventory tileBrewingStand;
    private final Slot theSlot;
    
    @Override
    public boolean canInteractWith(final EntityPlayer entityPlayer) {
        return this.tileBrewingStand.isUseableByPlayer(entityPlayer);
    }
    
    public ContainerBrewingStand(final InventoryPlayer inventoryPlayer, final IInventory tileBrewingStand) {
        this.tileBrewingStand = tileBrewingStand;
        this.addSlotToContainer(new Potion(inventoryPlayer.player, tileBrewingStand, "".length(), 0x0 ^ 0x38, 0x1E ^ 0x30));
        this.addSlotToContainer(new Potion(inventoryPlayer.player, tileBrewingStand, " ".length(), 0x5A ^ 0x15, 0x46 ^ 0x73));
        this.addSlotToContainer(new Potion(inventoryPlayer.player, tileBrewingStand, "  ".length(), 0x7 ^ 0x61, 0x5E ^ 0x70));
        this.theSlot = this.addSlotToContainer(new Ingredient(tileBrewingStand, "   ".length(), 0x1B ^ 0x54, 0x91 ^ 0x80));
        int i = "".length();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (i < "   ".length()) {
            int j = "".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
            while (j < (0x71 ^ 0x78)) {
                this.addSlotToContainer(new Slot(inventoryPlayer, j + i * (0x79 ^ 0x70) + (0x1C ^ 0x15), (0x1B ^ 0x13) + j * (0xB1 ^ 0xA3), (0xED ^ 0xB9) + i * (0x89 ^ 0x9B)));
                ++j;
            }
            ++i;
        }
        int k = "".length();
        "".length();
        if (4 <= 0) {
            throw null;
        }
        while (k < (0x6C ^ 0x65)) {
            this.addSlotToContainer(new Slot(inventoryPlayer, k, (0x14 ^ 0x1C) + k * (0x17 ^ 0x5), 9 + 92 - 90 + 131));
            ++k;
        }
    }
    
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        int i = "".length();
        "".length();
        if (-1 >= 4) {
            throw null;
        }
        while (i < this.crafters.size()) {
            final ICrafting crafting = this.crafters.get(i);
            if (this.brewTime != this.tileBrewingStand.getField("".length())) {
                crafting.sendProgressBarUpdate(this, "".length(), this.tileBrewingStand.getField("".length()));
            }
            ++i;
        }
        this.brewTime = this.tileBrewingStand.getField("".length());
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int n) {
        ItemStack copy = null;
        final Slot slot = this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            final ItemStack stack = slot.getStack();
            copy = stack.copy();
            if ((n < 0 || n > "  ".length()) && n != "   ".length()) {
                if (!this.theSlot.getHasStack() && this.theSlot.isItemValid(stack)) {
                    if (!this.mergeItemStack(stack, "   ".length(), 0x0 ^ 0x4, "".length() != 0)) {
                        return null;
                    }
                }
                else if (Potion.canHoldPotion(copy)) {
                    if (!this.mergeItemStack(stack, "".length(), "   ".length(), "".length() != 0)) {
                        return null;
                    }
                }
                else if (n >= (0x9E ^ 0x9A) && n < (0x4B ^ 0x54)) {
                    if (!this.mergeItemStack(stack, 0x6B ^ 0x74, 0x32 ^ 0x1A, "".length() != 0)) {
                        return null;
                    }
                }
                else if (n >= (0x9A ^ 0x85) && n < (0x4B ^ 0x63)) {
                    if (!this.mergeItemStack(stack, 0x6 ^ 0x2, 0x7F ^ 0x60, "".length() != 0)) {
                        return null;
                    }
                }
                else if (!this.mergeItemStack(stack, 0x7D ^ 0x79, 0x13 ^ 0x3B, "".length() != 0)) {
                    return null;
                }
            }
            else {
                if (!this.mergeItemStack(stack, 0x11 ^ 0x15, 0x60 ^ 0x48, " ".length() != 0)) {
                    return null;
                }
                slot.onSlotChange(stack, copy);
            }
            if (stack.stackSize == 0) {
                slot.putStack(null);
                "".length();
                if (2 < 1) {
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
            if (0 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void updateProgressBar(final int n, final int n2) {
        this.tileBrewingStand.setField(n, n2);
    }
    
    @Override
    public void onCraftGuiOpened(final ICrafting crafting) {
        super.onCraftGuiOpened(crafting);
        crafting.func_175173_a(this, this.tileBrewingStand);
    }
    
    static class Potion extends Slot
    {
        private EntityPlayer player;
        
        public static boolean canHoldPotion(final ItemStack itemStack) {
            if (itemStack != null && (itemStack.getItem() == Items.potionitem || itemStack.getItem() == Items.glass_bottle)) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        
        @Override
        public void onPickupFromSlot(final EntityPlayer entityPlayer, final ItemStack itemStack) {
            if (itemStack.getItem() == Items.potionitem && itemStack.getMetadata() > 0) {
                this.player.triggerAchievement(AchievementList.potion);
            }
            super.onPickupFromSlot(entityPlayer, itemStack);
        }
        
        @Override
        public int getSlotStackLimit() {
            return " ".length();
        }
        
        @Override
        public boolean isItemValid(final ItemStack itemStack) {
            return canHoldPotion(itemStack);
        }
        
        public Potion(final EntityPlayer player, final IInventory inventory, final int n, final int n2, final int n3) {
            super(inventory, n, n2, n3);
            this.player = player;
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
    }
    
    class Ingredient extends Slot
    {
        final ContainerBrewingStand this$0;
        
        @Override
        public int getSlotStackLimit() {
            return 0x56 ^ 0x16;
        }
        
        @Override
        public boolean isItemValid(final ItemStack itemStack) {
            int n;
            if (itemStack != null) {
                n = (itemStack.getItem().isPotionIngredient(itemStack) ? 1 : 0);
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            else {
                n = "".length();
            }
            return n != 0;
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
                if (4 < 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public Ingredient(final ContainerBrewingStand this$0, final IInventory inventory, final int n, final int n2, final int n3) {
            this.this$0 = this$0;
            super(inventory, n, n2, n3);
        }
    }
}
