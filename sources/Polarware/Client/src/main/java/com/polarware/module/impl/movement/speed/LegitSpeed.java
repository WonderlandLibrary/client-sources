package com.polarware.module.impl.movement.speed;

import com.polarware.component.impl.player.RotationComponent;
import com.polarware.component.impl.player.rotationcomponent.MovementFix;
import com.polarware.module.impl.movement.SpeedModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.Priority;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreUpdateEvent;
import com.polarware.event.impl.motion.StrafeEvent;
import com.polarware.util.player.MoveUtil;
import com.polarware.util.vector.Vector2f;
import com.polarware.value.Mode;
import com.polarware.value.impl.BooleanValue;
import com.polarware.value.impl.ModeValue;
import com.polarware.value.impl.SubMode;

/**
 * @author Alan
 * @since 18/11/2022
 */

public class LegitSpeed extends Mode<SpeedModule> {

    private ModeValue rotationExploit = new ModeValue("Rotation Exploit Mode", this)
            .add(new SubMode("Off"))
            .add(new SubMode("Rotate (Fully Legit)"))
            .add(new SubMode("Speed Equivalent (Almost legit, Very hard to flag)"))
            .setDefault("Speed Equivalent (Almost legit, Very hard to flag)");
    private BooleanValue cpuSpeedUpExploit = new BooleanValue("CPU SpeedUp Exploit", this, true);
    private BooleanValue noJumpDelay = new BooleanValue("No Jump Delay", this, true);

    public LegitSpeed(String name, SpeedModule parent) {
        super(name, parent);
    }


    @EventLink(value = Priority.VERY_HIGH)
    public final Listener<PreUpdateEvent> preUpdate = event -> {
        switch (rotationExploit.getValue().getName()) {
            case "Rotate (Fully Legit)":
                if (!mc.thePlayer.onGround)
                    RotationComponent.setRotations(new Vector2f(mc.thePlayer.rotationYaw + 45, mc.thePlayer.rotationPitch), 10, MovementFix.NORMAL);
                break;

            case "Speed Equivalent (Almost legit, Very hard to flag)":
                MoveUtil.useDiagonalSpeed();
                break;
        }

        if (noJumpDelay.getValue()) {
            mc.thePlayer.jumpTicks = 0;
        }

        if (cpuSpeedUpExploit.getValue()) {
            mc.timer.timerSpeed = 1.004f;
        }
    };

    @EventLink(value = Priority.VERY_HIGH)
    public final Listener<StrafeEvent> strafe = event -> {
        if (mc.thePlayer.onGround) {
            mc.thePlayer.jump();
        }
    };
}
