/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.controller;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.util.math.MathHelper;

public class FlyingMovementController
extends MovementController {
    private final int field_226323_i_;
    private final boolean field_226324_j_;

    public FlyingMovementController(MobEntity mobEntity, int n, boolean bl) {
        super(mobEntity);
        this.field_226323_i_ = n;
        this.field_226324_j_ = bl;
    }

    @Override
    public void tick() {
        if (this.action == MovementController.Action.MOVE_TO) {
            this.action = MovementController.Action.WAIT;
            this.mob.setNoGravity(false);
            double d = this.posX - this.mob.getPosX();
            double d2 = this.posY - this.mob.getPosY();
            double d3 = this.posZ - this.mob.getPosZ();
            double d4 = d * d + d2 * d2 + d3 * d3;
            if (d4 < 2.500000277905201E-7) {
                this.mob.setMoveVertical(0.0f);
                this.mob.setMoveForward(0.0f);
                return;
            }
            float f = (float)(MathHelper.atan2(d3, d) * 57.2957763671875) - 90.0f;
            this.mob.rotationYaw = this.limitAngle(this.mob.rotationYaw, f, 90.0f);
            float f2 = this.mob.isOnGround() ? (float)(this.speed * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)) : (float)(this.speed * this.mob.getAttributeValue(Attributes.FLYING_SPEED));
            this.mob.setAIMoveSpeed(f2);
            double d5 = MathHelper.sqrt(d * d + d3 * d3);
            float f3 = (float)(-(MathHelper.atan2(d2, d5) * 57.2957763671875));
            this.mob.rotationPitch = this.limitAngle(this.mob.rotationPitch, f3, this.field_226323_i_);
            this.mob.setMoveVertical(d2 > 0.0 ? f2 : -f2);
        } else {
            if (!this.field_226324_j_) {
                this.mob.setNoGravity(true);
            }
            this.mob.setMoveVertical(0.0f);
            this.mob.setMoveForward(0.0f);
        }
    }
}

