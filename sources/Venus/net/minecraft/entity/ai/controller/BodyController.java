/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.controller;

import net.minecraft.entity.MobEntity;
import net.minecraft.util.math.MathHelper;

public class BodyController {
    private final MobEntity mob;
    private int rotationTickCounter;
    private float prevRenderYawHead;

    public BodyController(MobEntity mobEntity) {
        this.mob = mobEntity;
    }

    public void updateRenderAngles() {
        if (this.func_220662_f()) {
            this.mob.renderYawOffset = this.mob.rotationYaw;
            this.func_220664_c();
            this.prevRenderYawHead = this.mob.rotationYawHead;
            this.rotationTickCounter = 0;
        } else if (this.func_220661_e()) {
            if (Math.abs(this.mob.rotationYawHead - this.prevRenderYawHead) > 15.0f) {
                this.rotationTickCounter = 0;
                this.prevRenderYawHead = this.mob.rotationYawHead;
                this.func_220663_b();
            } else {
                ++this.rotationTickCounter;
                if (this.rotationTickCounter > 10) {
                    this.func_220665_d();
                }
            }
        }
    }

    private void func_220663_b() {
        this.mob.renderYawOffset = MathHelper.func_219800_b(this.mob.renderYawOffset, this.mob.rotationYawHead, this.mob.getHorizontalFaceSpeed());
    }

    private void func_220664_c() {
        this.mob.rotationYawHead = MathHelper.func_219800_b(this.mob.rotationYawHead, this.mob.renderYawOffset, this.mob.getHorizontalFaceSpeed());
    }

    private void func_220665_d() {
        int n = this.rotationTickCounter - 10;
        float f = MathHelper.clamp((float)n / 10.0f, 0.0f, 1.0f);
        float f2 = (float)this.mob.getHorizontalFaceSpeed() * (1.0f - f);
        this.mob.renderYawOffset = MathHelper.func_219800_b(this.mob.renderYawOffset, this.mob.rotationYawHead, f2);
    }

    private boolean func_220661_e() {
        return this.mob.getPassengers().isEmpty() || !(this.mob.getPassengers().get(0) instanceof MobEntity);
    }

    private boolean func_220662_f() {
        double d;
        double d2 = this.mob.getPosX() - this.mob.prevPosX;
        return d2 * d2 + (d = this.mob.getPosZ() - this.mob.prevPosZ) * d > 2.500000277905201E-7;
    }
}

