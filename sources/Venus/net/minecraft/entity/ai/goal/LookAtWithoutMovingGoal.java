/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;

public class LookAtWithoutMovingGoal
extends LookAtGoal {
    public LookAtWithoutMovingGoal(MobEntity mobEntity, Class<? extends LivingEntity> clazz, float f, float f2) {
        super(mobEntity, clazz, f, f2);
        this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK, Goal.Flag.MOVE));
    }
}

