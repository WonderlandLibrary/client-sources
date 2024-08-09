/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.JumpGoal;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class DolphinJumpGoal
extends JumpGoal {
    private static final int[] JUMP_DISTANCES = new int[]{0, 1, 4, 5, 6, 7};
    private final DolphinEntity dolphin;
    private final int field_220712_c;
    private boolean inWater;

    public DolphinJumpGoal(DolphinEntity dolphinEntity, int n) {
        this.dolphin = dolphinEntity;
        this.field_220712_c = n;
    }

    @Override
    public boolean shouldExecute() {
        if (this.dolphin.getRNG().nextInt(this.field_220712_c) != 0) {
            return true;
        }
        Direction direction = this.dolphin.getAdjustedHorizontalFacing();
        int n = direction.getXOffset();
        int n2 = direction.getZOffset();
        BlockPos blockPos = this.dolphin.getPosition();
        for (int n3 : JUMP_DISTANCES) {
            if (this.canJumpTo(blockPos, n, n2, n3) && this.isAirAbove(blockPos, n, n2, n3)) continue;
            return true;
        }
        return false;
    }

    private boolean canJumpTo(BlockPos blockPos, int n, int n2, int n3) {
        BlockPos blockPos2 = blockPos.add(n * n3, 0, n2 * n3);
        return this.dolphin.world.getFluidState(blockPos2).isTagged(FluidTags.WATER) && !this.dolphin.world.getBlockState(blockPos2).getMaterial().blocksMovement();
    }

    private boolean isAirAbove(BlockPos blockPos, int n, int n2, int n3) {
        return this.dolphin.world.getBlockState(blockPos.add(n * n3, 1, n2 * n3)).isAir() && this.dolphin.world.getBlockState(blockPos.add(n * n3, 2, n2 * n3)).isAir();
    }

    @Override
    public boolean shouldContinueExecuting() {
        double d = this.dolphin.getMotion().y;
        return !(d * d < (double)0.03f && this.dolphin.rotationPitch != 0.0f && Math.abs(this.dolphin.rotationPitch) < 10.0f && this.dolphin.isInWater() || this.dolphin.isOnGround());
    }

    @Override
    public boolean isPreemptible() {
        return true;
    }

    @Override
    public void startExecuting() {
        Direction direction = this.dolphin.getAdjustedHorizontalFacing();
        this.dolphin.setMotion(this.dolphin.getMotion().add((double)direction.getXOffset() * 0.6, 0.7, (double)direction.getZOffset() * 0.6));
        this.dolphin.getNavigator().clearPath();
    }

    @Override
    public void resetTask() {
        this.dolphin.rotationPitch = 0.0f;
    }

    @Override
    public void tick() {
        Object object;
        boolean bl = this.inWater;
        if (!bl) {
            object = this.dolphin.world.getFluidState(this.dolphin.getPosition());
            this.inWater = ((FluidState)object).isTagged(FluidTags.WATER);
        }
        if (this.inWater && !bl) {
            this.dolphin.playSound(SoundEvents.ENTITY_DOLPHIN_JUMP, 1.0f, 1.0f);
        }
        object = this.dolphin.getMotion();
        if (((Vector3d)object).y * ((Vector3d)object).y < (double)0.03f && this.dolphin.rotationPitch != 0.0f) {
            this.dolphin.rotationPitch = MathHelper.rotLerp(this.dolphin.rotationPitch, 0.0f, 0.2f);
        } else {
            double d = Math.sqrt(Entity.horizontalMag((Vector3d)object));
            double d2 = Math.signum(-((Vector3d)object).y) * Math.acos(d / ((Vector3d)object).length()) * 57.2957763671875;
            this.dolphin.rotationPitch = (float)d2;
        }
    }
}

