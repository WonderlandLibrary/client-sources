/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.List;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.goal.BoatGoals;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class FollowBoatGoal
extends Goal {
    private int field_205143_a;
    private final CreatureEntity swimmer;
    private PlayerEntity player;
    private BoatGoals field_205146_d;

    public FollowBoatGoal(CreatureEntity creatureEntity) {
        this.swimmer = creatureEntity;
    }

    @Override
    public boolean shouldExecute() {
        List<BoatEntity> list = this.swimmer.world.getEntitiesWithinAABB(BoatEntity.class, this.swimmer.getBoundingBox().grow(5.0));
        boolean bl = false;
        for (BoatEntity boatEntity : list) {
            Entity entity2 = boatEntity.getControllingPassenger();
            if (!(entity2 instanceof PlayerEntity) || !(MathHelper.abs(((PlayerEntity)entity2).moveStrafing) > 0.0f) && !(MathHelper.abs(((PlayerEntity)entity2).moveForward) > 0.0f)) continue;
            bl = true;
            break;
        }
        return this.player != null && (MathHelper.abs(this.player.moveStrafing) > 0.0f || MathHelper.abs(this.player.moveForward) > 0.0f) || bl;
    }

    @Override
    public boolean isPreemptible() {
        return false;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.player != null && this.player.isPassenger() && (MathHelper.abs(this.player.moveStrafing) > 0.0f || MathHelper.abs(this.player.moveForward) > 0.0f);
    }

    @Override
    public void startExecuting() {
        for (BoatEntity boatEntity : this.swimmer.world.getEntitiesWithinAABB(BoatEntity.class, this.swimmer.getBoundingBox().grow(5.0))) {
            if (boatEntity.getControllingPassenger() == null || !(boatEntity.getControllingPassenger() instanceof PlayerEntity)) continue;
            this.player = (PlayerEntity)boatEntity.getControllingPassenger();
            break;
        }
        this.field_205143_a = 0;
        this.field_205146_d = BoatGoals.GO_TO_BOAT;
    }

    @Override
    public void resetTask() {
        this.player = null;
    }

    @Override
    public void tick() {
        boolean bl;
        boolean bl2 = bl = MathHelper.abs(this.player.moveStrafing) > 0.0f || MathHelper.abs(this.player.moveForward) > 0.0f;
        float f = this.field_205146_d == BoatGoals.GO_IN_BOAT_DIRECTION ? (bl ? 0.01f : 0.0f) : 0.015f;
        this.swimmer.moveRelative(f, new Vector3d(this.swimmer.moveStrafing, this.swimmer.moveVertical, this.swimmer.moveForward));
        this.swimmer.move(MoverType.SELF, this.swimmer.getMotion());
        if (--this.field_205143_a <= 0) {
            this.field_205143_a = 10;
            if (this.field_205146_d == BoatGoals.GO_TO_BOAT) {
                BlockPos blockPos = this.player.getPosition().offset(this.player.getHorizontalFacing().getOpposite());
                blockPos = blockPos.add(0, -1, 0);
                this.swimmer.getNavigator().tryMoveToXYZ(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1.0);
                if (this.swimmer.getDistance(this.player) < 4.0f) {
                    this.field_205143_a = 0;
                    this.field_205146_d = BoatGoals.GO_IN_BOAT_DIRECTION;
                }
            } else if (this.field_205146_d == BoatGoals.GO_IN_BOAT_DIRECTION) {
                Direction direction = this.player.getAdjustedHorizontalFacing();
                BlockPos blockPos = this.player.getPosition().offset(direction, 10);
                this.swimmer.getNavigator().tryMoveToXYZ(blockPos.getX(), blockPos.getY() - 1, blockPos.getZ(), 1.0);
                if (this.swimmer.getDistance(this.player) > 12.0f) {
                    this.field_205143_a = 0;
                    this.field_205146_d = BoatGoals.GO_TO_BOAT;
                }
            }
        }
    }
}

