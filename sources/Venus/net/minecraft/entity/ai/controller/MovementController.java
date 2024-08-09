/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.controller;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;

public class MovementController {
    protected final MobEntity mob;
    protected double posX;
    protected double posY;
    protected double posZ;
    protected double speed;
    protected float moveForward;
    protected float moveStrafe;
    protected Action action = Action.WAIT;

    public MovementController(MobEntity mobEntity) {
        this.mob = mobEntity;
    }

    public boolean isUpdating() {
        return this.action == Action.MOVE_TO;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setMoveTo(double d, double d2, double d3, double d4) {
        this.posX = d;
        this.posY = d2;
        this.posZ = d3;
        this.speed = d4;
        if (this.action != Action.JUMPING) {
            this.action = Action.MOVE_TO;
        }
    }

    public void strafe(float f, float f2) {
        this.action = Action.STRAFE;
        this.moveForward = f;
        this.moveStrafe = f2;
        this.speed = 0.25;
    }

    public void tick() {
        if (this.action == Action.STRAFE) {
            float f;
            float f2 = (float)this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED);
            float f3 = (float)this.speed * f2;
            float f4 = this.moveForward;
            float f5 = this.moveStrafe;
            float f6 = MathHelper.sqrt(f4 * f4 + f5 * f5);
            if (f6 < 1.0f) {
                f6 = 1.0f;
            }
            f6 = f3 / f6;
            float f7 = MathHelper.sin(this.mob.rotationYaw * ((float)Math.PI / 180));
            float f8 = MathHelper.cos(this.mob.rotationYaw * ((float)Math.PI / 180));
            float f9 = (f4 *= f6) * f8 - (f5 *= f6) * f7;
            if (!this.func_234024_b_(f9, f = f5 * f8 + f4 * f7)) {
                this.moveForward = 1.0f;
                this.moveStrafe = 0.0f;
            }
            this.mob.setAIMoveSpeed(f3);
            this.mob.setMoveForward(this.moveForward);
            this.mob.setMoveStrafing(this.moveStrafe);
            this.action = Action.WAIT;
        } else if (this.action == Action.MOVE_TO) {
            this.action = Action.WAIT;
            double d = this.posX - this.mob.getPosX();
            double d2 = this.posZ - this.mob.getPosZ();
            double d3 = this.posY - this.mob.getPosY();
            double d4 = d * d + d3 * d3 + d2 * d2;
            if (d4 < 2.500000277905201E-7) {
                this.mob.setMoveForward(0.0f);
                return;
            }
            float f = (float)(MathHelper.atan2(d2, d) * 57.2957763671875) - 90.0f;
            this.mob.rotationYaw = this.limitAngle(this.mob.rotationYaw, f, 90.0f);
            this.mob.setAIMoveSpeed((float)(this.speed * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
            BlockPos blockPos = this.mob.getPosition();
            BlockState blockState = this.mob.world.getBlockState(blockPos);
            Block block = blockState.getBlock();
            VoxelShape voxelShape = blockState.getCollisionShape(this.mob.world, blockPos);
            if (d3 > (double)this.mob.stepHeight && d * d + d2 * d2 < (double)Math.max(1.0f, this.mob.getWidth()) || !voxelShape.isEmpty() && this.mob.getPosY() < voxelShape.getEnd(Direction.Axis.Y) + (double)blockPos.getY() && !block.isIn(BlockTags.DOORS) && !block.isIn(BlockTags.FENCES)) {
                this.mob.getJumpController().setJumping();
                this.action = Action.JUMPING;
            }
        } else if (this.action == Action.JUMPING) {
            this.mob.setAIMoveSpeed((float)(this.speed * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
            if (this.mob.isOnGround()) {
                this.action = Action.WAIT;
            }
        } else {
            this.mob.setMoveForward(0.0f);
        }
    }

    private boolean func_234024_b_(float f, float f2) {
        NodeProcessor nodeProcessor;
        PathNavigator pathNavigator = this.mob.getNavigator();
        return pathNavigator != null && (nodeProcessor = pathNavigator.getNodeProcessor()) != null && nodeProcessor.getPathNodeType(this.mob.world, MathHelper.floor(this.mob.getPosX() + (double)f), MathHelper.floor(this.mob.getPosY()), MathHelper.floor(this.mob.getPosZ() + (double)f2)) != PathNodeType.WALKABLE;
    }

    protected float limitAngle(float f, float f2, float f3) {
        float f4;
        float f5 = MathHelper.wrapDegrees(f2 - f);
        if (f5 > f3) {
            f5 = f3;
        }
        if (f5 < -f3) {
            f5 = -f3;
        }
        if ((f4 = f + f5) < 0.0f) {
            f4 += 360.0f;
        } else if (f4 > 360.0f) {
            f4 -= 360.0f;
        }
        return f4;
    }

    public double getX() {
        return this.posX;
    }

    public double getY() {
        return this.posY;
    }

    public double getZ() {
        return this.posZ;
    }

    public static enum Action {
        WAIT,
        MOVE_TO,
        STRAFE,
        JUMPING;

    }
}

