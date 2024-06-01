package com.polarware.module.impl.ghost;

import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.HitSlowDownEvent;
import com.polarware.value.impl.BooleanValue;
import com.polarware.value.impl.NumberValue;

/**
 * @author Alan
 * @since 27/4/2022
 */
@ModuleInfo(name = "module.ghost.keepsprint.name", description = "module.ghost.keepsprint.description", category = Category.GHOST)
public class KeepSprintModule extends Module {

    private final NumberValue slowDownVelocity = new NumberValue("Hit Slow Down During Velocity", this, 0.6, 0, 1, 0.05);
    private final NumberValue slowDownNormal = new NumberValue("Hit Slow Down Normal", this, 0.6, 0, 1, 0.05);
    private final NumberValue bufferDecrease = new NumberValue("Buffer Decrease", this, 1, 0.1, 10, 0.1, () -> !this.bufferAbuse.getValue());
    private final NumberValue maxBuffer = new NumberValue("Max Buffer", this, 5, 1, 10, 1, () -> !this.bufferAbuse.getValue());
    private final BooleanValue sprintSlowDownVelocity = new BooleanValue("Velocity Hit Sprint", this, false);
    private final BooleanValue sprintSlowDownNormal = new BooleanValue("Normal Hit Sprint", this, false);
    private final BooleanValue bufferAbuse = new BooleanValue("Buffer Abuse", this, false);
    private final BooleanValue onlyInAir = new BooleanValue("Only In Air", this, false);

    private boolean resetting;
    private double combo;

    @EventLink()
    public final Listener<HitSlowDownEvent> onHitSlowDown = event -> {
        if (mc.thePlayer.onGround && this.onlyInAir.getValue()) {
            return;
        }

        if (this.bufferAbuse.getValue()) {
            if (this.combo < this.maxBuffer.getValue().intValue() && !this.resetting) {
                this.combo++;
            } else {
                if (this.combo > 0) {
                    this.combo = Math.max(0, this.combo - this.bufferDecrease.getValue().doubleValue());
                    this.resetting = true;
                    return;
                } else {
                    this.resetting = false;
                }
            }
        } else {
            this.combo = 0;
        }

        if (mc.thePlayer.hurtTime > 0) {
            event.setSlowDown(this.slowDownVelocity.getValue().doubleValue());
            event.setSprint(this.sprintSlowDownVelocity.getValue());
        } else {
            event.setSlowDown(this.slowDownNormal.getValue().doubleValue());
            event.setSprint(this.sprintSlowDownNormal.getValue());
        }
    };
}