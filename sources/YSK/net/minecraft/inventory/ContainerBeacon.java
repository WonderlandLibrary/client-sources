package net.minecraft.inventory;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.init.*;

public class ContainerBeacon extends Container
{
    private IInventory tileBeacon;
    private final BeaconSlot beaconSlot;
    
    @Override
    public boolean canInteractWith(final EntityPlayer entityPlayer) {
        return this.tileBeacon.isUseableByPlayer(entityPlayer);
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
    
    public IInventory func_180611_e() {
        return this.tileBeacon;
    }
    
    public ContainerBeacon(final IInventory inventory, final IInventory tileBeacon) {
        this.tileBeacon = tileBeacon;
        this.addSlotToContainer(this.beaconSlot = new BeaconSlot(tileBeacon, "".length(), 3 + 134 - 121 + 120, 0x79 ^ 0x17));
        final int n = 0x8D ^ 0xA9;
        final int n2 = 32 + 60 - 33 + 78;
        int i = "".length();
        "".length();
        if (false) {
            throw null;
        }
        while (i < "   ".length()) {
            int j = "".length();
            "".length();
            if (1 <= 0) {
                throw null;
            }
            while (j < (0x73 ^ 0x7A)) {
                this.addSlotToContainer(new Slot(inventory, j + i * (0x34 ^ 0x3D) + (0x76 ^ 0x7F), n + j * (0x87 ^ 0x95), n2 + i * (0x0 ^ 0x12)));
                ++j;
            }
            ++i;
        }
        int k = "".length();
        "".length();
        if (3 < 3) {
            throw null;
        }
        while (k < (0xB5 ^ 0xBC)) {
            this.addSlotToContainer(new Slot(inventory, k, n + k * (0xBD ^ 0xAF), (0x42 ^ 0x78) + n2));
            ++k;
        }
    }
    
    @Override
    public void onContainerClosed(final EntityPlayer entityPlayer) {
        super.onContainerClosed(entityPlayer);
        if (entityPlayer != null && !entityPlayer.worldObj.isRemote) {
            final ItemStack decrStackSize = this.beaconSlot.decrStackSize(this.beaconSlot.getSlotStackLimit());
            if (decrStackSize != null) {
                entityPlayer.dropPlayerItemWithRandomChoice(decrStackSize, "".length() != 0);
            }
        }
    }
    
    @Override
    public void onCraftGuiOpened(final ICrafting crafting) {
        super.onCraftGuiOpened(crafting);
        crafting.func_175173_a(this, this.tileBeacon);
    }
    
    @Override
    public void updateProgressBar(final int n, final int n2) {
        this.tileBeacon.setField(n, n2);
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int n) {
        ItemStack copy = null;
        final Slot slot = this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            final ItemStack stack = slot.getStack();
            copy = stack.copy();
            if (n == 0) {
                if (!this.mergeItemStack(stack, " ".length(), 0xA8 ^ 0x8D, " ".length() != 0)) {
                    return null;
                }
                slot.onSlotChange(stack, copy);
                "".length();
                if (4 < 1) {
                    throw null;
                }
            }
            else if (!this.beaconSlot.getHasStack() && this.beaconSlot.isItemValid(stack) && stack.stackSize == " ".length()) {
                if (!this.mergeItemStack(stack, "".length(), " ".length(), "".length() != 0)) {
                    return null;
                }
            }
            else if (n >= " ".length() && n < (0x6C ^ 0x70)) {
                if (!this.mergeItemStack(stack, 0x6A ^ 0x76, 0x93 ^ 0xB6, "".length() != 0)) {
                    return null;
                }
            }
            else if (n >= (0x15 ^ 0x9) && n < (0x14 ^ 0x31)) {
                if (!this.mergeItemStack(stack, " ".length(), 0x5 ^ 0x19, "".length() != 0)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(stack, " ".length(), 0x1B ^ 0x3E, "".length() != 0)) {
                return null;
            }
            if (stack.stackSize == 0) {
                slot.putStack(null);
                "".length();
                if (3 != 3) {
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
    
    class BeaconSlot extends Slot
    {
        final ContainerBeacon this$0;
        
        public BeaconSlot(final ContainerBeacon this$0, final IInventory inventory, final int n, final int n2, final int n3) {
            this.this$0 = this$0;
            super(inventory, n, n2, n3);
        }
        
        @Override
        public int getSlotStackLimit() {
            return " ".length();
        }
        
        @Override
        public boolean isItemValid(final ItemStack itemStack) {
            int n;
            if (itemStack == null) {
                n = "".length();
                "".length();
                if (1 <= 0) {
                    throw null;
                }
            }
            else if (itemStack.getItem() != Items.emerald && itemStack.getItem() != Items.diamond && itemStack.getItem() != Items.gold_ingot && itemStack.getItem() != Items.iron_ingot) {
                n = "".length();
                "".length();
                if (3 < 2) {
                    throw null;
                }
            }
            else {
                n = " ".length();
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
                if (3 <= -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
