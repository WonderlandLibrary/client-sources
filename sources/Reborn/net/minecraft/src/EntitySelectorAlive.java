package net.minecraft.src;

final class EntitySelectorAlive implements IEntitySelector
{
    @Override
    public boolean isEntityApplicable(final Entity par1Entity) {
        return par1Entity.isEntityAlive();
    }
}
