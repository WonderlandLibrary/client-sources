package net.minecraft.entity.ai;

import net.minecraft.entity.*;

public class EntityAIWatchClosest2 extends EntityAIWatchClosest
{
    public EntityAIWatchClosest2(final EntityLiving p_i1629_1_, final Class p_i1629_2_, final float p_i1629_3_, final float p_i1629_4_) {
        super(p_i1629_1_, p_i1629_2_, p_i1629_3_, p_i1629_4_);
        this.setMutexBits(3);
    }
}
