/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;

public class LookRandomlyGoal
extends Goal {
    private final MobEntity idleEntity;
    private double lookX;
    private double lookZ;
    private int idleTime;

    public LookRandomlyGoal(MobEntity mobEntity) {
        this.idleEntity = mobEntity;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean shouldExecute() {
        return this.idleEntity.getRNG().nextFloat() < 0.02f;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.idleTime >= 0;
    }

    @Override
    public void startExecuting() {
        double d = Math.PI * 2 * this.idleEntity.getRNG().nextDouble();
        this.lookX = Math.cos(d);
        this.lookZ = Math.sin(d);
        this.idleTime = 20 + this.idleEntity.getRNG().nextInt(20);
    }

    @Override
    public void tick() {
        --this.idleTime;
        this.idleEntity.getLookController().setLookPosition(this.idleEntity.getPosX() + this.lookX, this.idleEntity.getPosYEye(), this.idleEntity.getPosZ() + this.lookZ);
    }
}

