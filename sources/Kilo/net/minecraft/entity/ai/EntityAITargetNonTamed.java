package net.minecraft.entity.ai;

import net.minecraft.entity.passive.EntityTameable;

import com.google.common.base.Predicate;

public class EntityAITargetNonTamed extends EntityAINearestAttackableTarget
{
    private EntityTameable theTameable;

    public EntityAITargetNonTamed(EntityTameable p_i45876_1_, Class p_i45876_2_, boolean p_i45876_3_, Predicate p_i45876_4_)
    {
        super(p_i45876_1_, p_i45876_2_, 10, p_i45876_3_, false, p_i45876_4_);
        this.theTameable = p_i45876_1_;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
	public boolean shouldExecute()
    {
        return !this.theTameable.isTamed() && super.shouldExecute();
    }
}
