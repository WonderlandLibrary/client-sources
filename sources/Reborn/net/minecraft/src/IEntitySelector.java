package net.minecraft.src;

public interface IEntitySelector
{
    public static final IEntitySelector selectAnything = new EntitySelectorAlive();
    public static final IEntitySelector selectInventories = new EntitySelectorInventory();
    
    boolean isEntityApplicable(final Entity p0);
}
