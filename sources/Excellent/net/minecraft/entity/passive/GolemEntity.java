package net.minecraft.entity.passive;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class GolemEntity extends CreatureEntity
{
    protected GolemEntity(EntityType <? extends GolemEntity > type, World worldIn)
    {
        super(type, worldIn);
    }

    public boolean onLivingFall(float distance, float damageMultiplier)
    {
        return false;
    }

    @Nullable
    protected SoundEvent getAmbientSound()
    {
        return null;
    }

    @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return null;
    }

    @Nullable
    protected SoundEvent getDeathSound()
    {
        return null;
    }

    /**
     * Get number of ticks, at least during which the living entity will be silent.
     */
    public int getTalkInterval()
    {
        return 120;
    }

    public boolean canDespawn(double distanceToClosestPlayer)
    {
        return false;
    }
}
