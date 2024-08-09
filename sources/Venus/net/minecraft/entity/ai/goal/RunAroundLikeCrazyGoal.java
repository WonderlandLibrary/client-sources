/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;

public class RunAroundLikeCrazyGoal
extends Goal {
    private final AbstractHorseEntity horseHost;
    private final double speed;
    private double targetX;
    private double targetY;
    private double targetZ;

    public RunAroundLikeCrazyGoal(AbstractHorseEntity abstractHorseEntity, double d) {
        this.horseHost = abstractHorseEntity;
        this.speed = d;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean shouldExecute() {
        if (!this.horseHost.isTame() && this.horseHost.isBeingRidden()) {
            Vector3d vector3d = RandomPositionGenerator.findRandomTarget(this.horseHost, 5, 4);
            if (vector3d == null) {
                return true;
            }
            this.targetX = vector3d.x;
            this.targetY = vector3d.y;
            this.targetZ = vector3d.z;
            return false;
        }
        return true;
    }

    @Override
    public void startExecuting() {
        this.horseHost.getNavigator().tryMoveToXYZ(this.targetX, this.targetY, this.targetZ, this.speed);
    }

    @Override
    public boolean shouldContinueExecuting() {
        return !this.horseHost.isTame() && !this.horseHost.getNavigator().noPath() && this.horseHost.isBeingRidden();
    }

    @Override
    public void tick() {
        if (!this.horseHost.isTame() && this.horseHost.getRNG().nextInt(50) == 0) {
            Entity entity2 = this.horseHost.getPassengers().get(0);
            if (entity2 == null) {
                return;
            }
            if (entity2 instanceof PlayerEntity) {
                int n = this.horseHost.getTemper();
                int n2 = this.horseHost.getMaxTemper();
                if (n2 > 0 && this.horseHost.getRNG().nextInt(n2) < n) {
                    this.horseHost.setTamedBy((PlayerEntity)entity2);
                    return;
                }
                this.horseHost.increaseTemper(5);
            }
            this.horseHost.removePassengers();
            this.horseHost.makeMad();
            this.horseHost.world.setEntityState(this.horseHost, (byte)6);
        }
    }
}

