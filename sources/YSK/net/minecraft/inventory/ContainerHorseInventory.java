package net.minecraft.inventory;

import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.init.*;

public class ContainerHorseInventory extends Container
{
    private EntityHorse theHorse;
    private IInventory horseInventory;
    
    @Override
    public boolean canInteractWith(final EntityPlayer entityPlayer) {
        if (this.horseInventory.isUseableByPlayer(entityPlayer) && this.theHorse.isEntityAlive() && this.theHorse.getDistanceToEntity(entityPlayer) < 8.0f) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void onContainerClosed(final EntityPlayer entityPlayer) {
        super.onContainerClosed(entityPlayer);
        this.horseInventory.closeInventory(entityPlayer);
    }
    
    public ContainerHorseInventory(final IInventory inventory, final IInventory horseInventory, final EntityHorse theHorse, final EntityPlayer entityPlayer) {
        this.horseInventory = horseInventory;
        this.theHorse = theHorse;
        final int length = "   ".length();
        horseInventory.openInventory(entityPlayer);
        final int n = (length - (0x2E ^ 0x2A)) * (0xAD ^ 0xBF);
        this.addSlotToContainer(new Slot(this, horseInventory, "".length(), 0x6F ^ 0x67, 0x14 ^ 0x6) {
            final ContainerHorseInventory this$0;
            
            @Override
            public boolean isItemValid(final ItemStack itemStack) {
                if (super.isItemValid(itemStack) && itemStack.getItem() == Items.saddle && !this.getHasStack()) {
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
                    if (1 <= -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        });
        this.addSlotToContainer(new Slot(this, horseInventory, " ".length(), 0x22 ^ 0x2A, 0x85 ^ 0xA1, theHorse) {
            private final EntityHorse val$horse;
            final ContainerHorseInventory this$0;
            
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
                    if (4 < -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public boolean canBeHovered() {
                return this.val$horse.canWearArmor();
            }
            
            @Override
            public boolean isItemValid(final ItemStack itemStack) {
                if (super.isItemValid(itemStack) && this.val$horse.canWearArmor() && EntityHorse.isArmorItem(itemStack.getItem())) {
                    return " ".length() != 0;
                }
                return "".length() != 0;
            }
        });
        if (theHorse.isChested()) {
            int i = "".length();
            "".length();
            if (4 <= -1) {
                throw null;
            }
            while (i < length) {
                int j = "".length();
                "".length();
                if (0 >= 4) {
                    throw null;
                }
                while (j < (0x8F ^ 0x8A)) {
                    this.addSlotToContainer(new Slot(horseInventory, "  ".length() + j + i * (0x8F ^ 0x8A), (0xE7 ^ 0xB7) + j * (0x51 ^ 0x43), (0x6F ^ 0x7D) + i * (0x6E ^ 0x7C)));
                    ++j;
                }
                ++i;
            }
        }
        int k = "".length();
        "".length();
        if (-1 >= 3) {
            throw null;
        }
        while (k < "   ".length()) {
            int l = "".length();
            "".length();
            if (4 == 1) {
                throw null;
            }
            while (l < (0x17 ^ 0x1E)) {
                this.addSlotToContainer(new Slot(inventory, l + k * (0x9D ^ 0x94) + (0x24 ^ 0x2D), (0xA4 ^ 0xAC) + l * (0x18 ^ 0xA), (0x12 ^ 0x74) + k * (0x4F ^ 0x5D) + n));
                ++l;
            }
            ++k;
        }
        int length2 = "".length();
        "".length();
        if (3 < 0) {
            throw null;
        }
        while (length2 < (0x78 ^ 0x71)) {
            this.addSlotToContainer(new Slot(inventory, length2, (0xBE ^ 0xB6) + length2 * (0xB ^ 0x19), 152 + 101 - 223 + 130 + n));
            ++length2;
        }
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
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int n) {
        ItemStack copy = null;
        final Slot slot = this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            final ItemStack stack = slot.getStack();
            copy = stack.copy();
            if (n < this.horseInventory.getSizeInventory()) {
                if (!this.mergeItemStack(stack, this.horseInventory.getSizeInventory(), this.inventorySlots.size(), " ".length() != 0)) {
                    return null;
                }
            }
            else if (this.getSlot(" ".length()).isItemValid(stack) && !this.getSlot(" ".length()).getHasStack()) {
                if (!this.mergeItemStack(stack, " ".length(), "  ".length(), "".length() != 0)) {
                    return null;
                }
            }
            else if (this.getSlot("".length()).isItemValid(stack)) {
                if (!this.mergeItemStack(stack, "".length(), " ".length(), "".length() != 0)) {
                    return null;
                }
            }
            else if (this.horseInventory.getSizeInventory() <= "  ".length() || !this.mergeItemStack(stack, "  ".length(), this.horseInventory.getSizeInventory(), "".length() != 0)) {
                return null;
            }
            if (stack.stackSize == 0) {
                slot.putStack(null);
                "".length();
                if (4 <= 0) {
                    throw null;
                }
            }
            else {
                slot.onSlotChanged();
            }
        }
        return copy;
    }
}
