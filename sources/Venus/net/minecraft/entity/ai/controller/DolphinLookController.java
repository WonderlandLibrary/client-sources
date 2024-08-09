/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.controller;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.controller.LookController;
import net.minecraft.util.math.MathHelper;

public class DolphinLookController
extends LookController {
    private final int field_205139_h;

    public DolphinLookController(MobEntity mobEntity, int n) {
        super(mobEntity);
        this.field_205139_h = n;
    }

    @Override
    public void tick() {
        if (this.isLooking) {
            this.isLooking = false;
            this.mob.rotationYawHead = this.clampedRotate(this.mob.rotationYawHead, this.getTargetYaw() + 20.0f, this.deltaLookYaw);
            this.mob.rotationPitch = this.clampedRotate(this.mob.rotationPitch, this.getTargetPitch() + 10.0f, this.deltaLookPitch);
        } else {
            if (this.mob.getNavigator().noPath()) {
                this.mob.rotationPitch = this.clampedRotate(this.mob.rotationPitch, 0.0f, 5.0f);
            }
            this.mob.rotationYawHead = this.clampedRotate(this.mob.rotationYawHead, this.mob.renderYawOffset, this.deltaLookYaw);
        }
        float f = MathHelper.wrapDegrees(this.mob.rotationYawHead - this.mob.renderYawOffset);
        if (f < (float)(-this.field_205139_h)) {
            this.mob.renderYawOffset -= 4.0f;
        } else if (f > (float)this.field_205139_h) {
            this.mob.renderYawOffset += 4.0f;
        }
    }
}

