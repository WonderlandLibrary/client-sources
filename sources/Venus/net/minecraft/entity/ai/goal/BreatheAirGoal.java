/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.IWorldReader;

public class BreatheAirGoal
extends Goal {
    private final CreatureEntity field_205142_a;

    public BreatheAirGoal(CreatureEntity creatureEntity) {
        this.field_205142_a = creatureEntity;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean shouldExecute() {
        return this.field_205142_a.getAir() < 140;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.shouldExecute();
    }

    @Override
    public boolean isPreemptible() {
        return true;
    }

    @Override
    public void startExecuting() {
        this.navigate();
    }

    private void navigate() {
        Iterable<BlockPos> iterable = BlockPos.getAllInBoxMutable(MathHelper.floor(this.field_205142_a.getPosX() - 1.0), MathHelper.floor(this.field_205142_a.getPosY()), MathHelper.floor(this.field_205142_a.getPosZ() - 1.0), MathHelper.floor(this.field_205142_a.getPosX() + 1.0), MathHelper.floor(this.field_205142_a.getPosY() + 8.0), MathHelper.floor(this.field_205142_a.getPosZ() + 1.0));
        Vector3i vector3i = null;
        for (BlockPos blockPos : iterable) {
            if (!this.canBreatheAt(this.field_205142_a.world, blockPos)) continue;
            vector3i = blockPos;
            break;
        }
        if (vector3i == null) {
            vector3i = new BlockPos(this.field_205142_a.getPosX(), this.field_205142_a.getPosY() + 8.0, this.field_205142_a.getPosZ());
        }
        this.field_205142_a.getNavigator().tryMoveToXYZ(vector3i.getX(), vector3i.getY() + 1, vector3i.getZ(), 1.0);
    }

    @Override
    public void tick() {
        this.navigate();
        this.field_205142_a.moveRelative(0.02f, new Vector3d(this.field_205142_a.moveStrafing, this.field_205142_a.moveVertical, this.field_205142_a.moveForward));
        this.field_205142_a.move(MoverType.SELF, this.field_205142_a.getMotion());
    }

    private boolean canBreatheAt(IWorldReader iWorldReader, BlockPos blockPos) {
        BlockState blockState = iWorldReader.getBlockState(blockPos);
        return (iWorldReader.getFluidState(blockPos).isEmpty() || blockState.isIn(Blocks.BUBBLE_COLUMN)) && blockState.allowsMovement(iWorldReader, blockPos, PathType.LAND);
    }
}

