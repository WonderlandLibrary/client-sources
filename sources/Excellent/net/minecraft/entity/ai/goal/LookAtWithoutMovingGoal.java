package net.minecraft.entity.ai.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;

import java.util.EnumSet;

public class LookAtWithoutMovingGoal extends LookAtGoal
{
    public LookAtWithoutMovingGoal(MobEntity entitylivingIn, Class <? extends LivingEntity > watchTargetClass, float maxDistance, float chanceIn)
    {
        super(entitylivingIn, watchTargetClass, maxDistance, chanceIn);
        this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK, Goal.Flag.MOVE));
    }
}
