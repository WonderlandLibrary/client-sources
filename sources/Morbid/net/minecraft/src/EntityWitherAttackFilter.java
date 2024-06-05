package net.minecraft.src;

final class EntityWitherAttackFilter implements IEntitySelector
{
    @Override
    public boolean isEntityApplicable(final Entity par1Entity) {
        return par1Entity instanceof EntityLiving && ((EntityLiving)par1Entity).getCreatureAttribute() != EnumCreatureAttribute.UNDEAD;
    }
}
