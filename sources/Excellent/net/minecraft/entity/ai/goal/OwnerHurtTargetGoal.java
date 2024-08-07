package net.minecraft.entity.ai.goal;

import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;

import java.util.EnumSet;

public class OwnerHurtTargetGoal extends TargetGoal
{
    private final TameableEntity tameable;
    private LivingEntity attacker;
    private int timestamp;

    public OwnerHurtTargetGoal(TameableEntity theEntityTameableIn)
    {
        super(theEntityTameableIn, false);
        this.tameable = theEntityTameableIn;
        this.setMutexFlags(EnumSet.of(Goal.Flag.TARGET));
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean shouldExecute()
    {
        if (this.tameable.isTamed() && !this.tameable.isSitting())
        {
            LivingEntity livingentity = this.tameable.getOwner();

            if (livingentity == null)
            {
                return false;
            }
            else
            {
                this.attacker = livingentity.getLastAttackedEntity();
                int i = livingentity.getLastAttackedEntityTime();
                return i != this.timestamp && this.isSuitableTarget(this.attacker, EntityPredicate.DEFAULT) && this.tameable.shouldAttackEntity(this.attacker, livingentity);
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.goalOwner.setAttackTarget(this.attacker);
        LivingEntity livingentity = this.tameable.getOwner();

        if (livingentity != null)
        {
            this.timestamp = livingentity.getLastAttackedEntityTime();
        }

        super.startExecuting();
    }
}
