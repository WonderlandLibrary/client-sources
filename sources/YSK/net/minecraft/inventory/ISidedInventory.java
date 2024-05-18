package net.minecraft.inventory;

import net.minecraft.item.*;
import net.minecraft.util.*;

public interface ISidedInventory extends IInventory
{
    boolean canExtractItem(final int p0, final ItemStack p1, final EnumFacing p2);
    
    int[] getSlotsForFace(final EnumFacing p0);
    
    boolean canInsertItem(final int p0, final ItemStack p1, final EnumFacing p2);
}
