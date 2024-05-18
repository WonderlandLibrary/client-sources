package net.minecraft.src;

import java.util.concurrent.*;

class CallableItemName implements Callable
{
    final ItemStack theItemStack;
    final InventoryPlayer playerInventory;
    
    CallableItemName(final InventoryPlayer par1InventoryPlayer, final ItemStack par2ItemStack) {
        this.playerInventory = par1InventoryPlayer;
        this.theItemStack = par2ItemStack;
    }
    
    public String callItemDisplayName() {
        return this.theItemStack.getDisplayName();
    }
    
    @Override
    public Object call() {
        return this.callItemDisplayName();
    }
}
