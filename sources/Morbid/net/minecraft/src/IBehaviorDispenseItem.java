package net.minecraft.src;

public interface IBehaviorDispenseItem
{
    public static final IBehaviorDispenseItem itemDispenseBehaviorProvider = new BehaviorDispenseItemProvider();
    
    ItemStack dispense(final IBlockSource p0, final ItemStack p1);
}
