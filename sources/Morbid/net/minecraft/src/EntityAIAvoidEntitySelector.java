package net.minecraft.src;

class EntityAIAvoidEntitySelector implements IEntitySelector
{
    final EntityAIAvoidEntity entityAvoiderAI;
    
    EntityAIAvoidEntitySelector(final EntityAIAvoidEntity par1EntityAIAvoidEntity) {
        this.entityAvoiderAI = par1EntityAIAvoidEntity;
    }
    
    @Override
    public boolean isEntityApplicable(final Entity par1Entity) {
        return par1Entity.isEntityAlive() && EntityAIAvoidEntity.func_98217_a(this.entityAvoiderAI).getEntitySenses().canSee(par1Entity);
    }
}
