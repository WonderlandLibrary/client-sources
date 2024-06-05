package net.minecraft.src;

public interface ISidedInventory extends IInventory
{
    int[] getAccessibleSlotsFromSide(final int p0);
    
    boolean canInsertItem(final int p0, final ItemStack p1, final int p2);
    
    boolean canExtractItem(final int p0, final ItemStack p1, final int p2);
}
