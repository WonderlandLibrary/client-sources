/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;

public class PanicGoal
extends Goal {
    protected final CreatureEntity creature;
    protected final double speed;
    protected double randPosX;
    protected double randPosY;
    protected double randPosZ;
    protected boolean running;

    public PanicGoal(CreatureEntity creatureEntity, double d) {
        this.creature = creatureEntity;
        this.speed = d;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean shouldExecute() {
        BlockPos blockPos;
        if (this.creature.getRevengeTarget() == null && !this.creature.isBurning()) {
            return true;
        }
        if (this.creature.isBurning() && (blockPos = this.getRandPos(this.creature.world, this.creature, 5, 4)) != null) {
            this.randPosX = blockPos.getX();
            this.randPosY = blockPos.getY();
            this.randPosZ = blockPos.getZ();
            return false;
        }
        return this.findRandomPosition();
    }

    protected boolean findRandomPosition() {
        Vector3d vector3d = RandomPositionGenerator.findRandomTarget(this.creature, 5, 4);
        if (vector3d == null) {
            return true;
        }
        this.randPosX = vector3d.x;
        this.randPosY = vector3d.y;
        this.randPosZ = vector3d.z;
        return false;
    }

    public boolean isRunning() {
        return this.running;
    }

    @Override
    public void startExecuting() {
        this.creature.getNavigator().tryMoveToXYZ(this.randPosX, this.randPosY, this.randPosZ, this.speed);
        this.running = true;
    }

    @Override
    public void resetTask() {
        this.running = false;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return !this.creature.getNavigator().noPath();
    }

    @Nullable
    protected BlockPos getRandPos(IBlockReader iBlockReader, Entity entity2, int n, int n2) {
        BlockPos blockPos = entity2.getPosition();
        int n3 = blockPos.getX();
        int n4 = blockPos.getY();
        int n5 = blockPos.getZ();
        float f = n * n * n2 * 2;
        BlockPos blockPos2 = null;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = n3 - n; i <= n3 + n; ++i) {
            for (int j = n4 - n2; j <= n4 + n2; ++j) {
                for (int k = n5 - n; k <= n5 + n; ++k) {
                    float f2;
                    mutable.setPos(i, j, k);
                    if (!iBlockReader.getFluidState(mutable).isTagged(FluidTags.WATER) || !((f2 = (float)((i - n3) * (i - n3) + (j - n4) * (j - n4) + (k - n5) * (k - n5))) < f)) continue;
                    f = f2;
                    blockPos2 = new BlockPos(mutable);
                }
            }
        }
        return blockPos2;
    }
}

