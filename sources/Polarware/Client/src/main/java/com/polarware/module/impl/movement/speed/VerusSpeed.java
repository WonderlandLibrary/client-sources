package com.polarware.module.impl.movement.speed;

import com.polarware.component.impl.render.SmoothCameraComponent;
import com.polarware.module.impl.movement.SpeedModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.input.MoveInputEvent;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.other.MoveEvent;
import com.polarware.util.player.MoveUtil;
import com.polarware.value.Mode;
import com.polarware.value.impl.ModeValue;
import com.polarware.value.impl.SubMode;

/**
 * @author Nicklas
 * @since 19.08.2022
 */
// -0.0784000015258789
// -0.09800000190734864
public class VerusSpeed extends Mode<SpeedModule> {

    private final ModeValue mode = new ModeValue("Sub-Mode", this)
            .add(new SubMode("Hop"))
            .add(new SubMode("Fast"))
            .add(new SubMode("yPort"))
            .add(new SubMode("Trooll"))
            .setDefault("Hop");

    private int ticks;
    private boolean bool, lastStopped;

    public VerusSpeed(String name, SpeedModule parent) {
        super(name, parent);
    }

    @Override
    public void onEnable() {
        bool = lastStopped = false;
        ticks = 0;
    }

    @Override
    public void onDisable() {

    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
      //  SmoothCameraComponent.setY();
        if (!MoveUtil.isMoving()) return;

        switch (mode.getValue().getName()) {
            case "Hop": {
                if (mc.thePlayer.onGround) {
                    mc.thePlayer.jump();
                    MoveUtil.strafe(0.55F + MoveUtil.speedPotionAmp(0.09));
                } else {
                    MoveUtil.strafe(0.33F + MoveUtil.speedPotionAmp(0.084));
                }

                if (!(mc.thePlayer.moveForward > 0)) {
                    MoveUtil.strafe(0.3 + MoveUtil.speedPotionAmp(0.065));
                }

                break;
            }

            case "Fast": {
                if (!(mc.thePlayer.moveForward > 0)) {
                    lastStopped = true;
                    return;
                }

                if (mc.thePlayer.onGround) {
                    if (MoveUtil.speed() > 0.3) lastStopped = false;

                    event.setOnGround(true);
                    MoveUtil.strafe(0.41);
                    mc.thePlayer.motionY = 0.42F;
                    mc.timer.timerSpeed = 2.1F;
                    ticks = 0;
                } else {
                    if (ticks >= 10) {
                        bool = true;
                        MoveUtil.strafe(0.35F);
                        return;
                    }

                    if (bool) {
                        if (lastStopped) {
                            MoveUtil.strafe(0.2);
                        }
                        else if (ticks <= 1) {
                            MoveUtil.strafe(0.35F);
                        }
                        else {
                            MoveUtil.strafe(0.69F - (ticks - 2F) * 0.019F);
                        }
                    }

                    mc.thePlayer.motionY = 0F;
                    mc.timer.timerSpeed = 0.9F;

                    event.setOnGround(true);
                    mc.thePlayer.onGround = true;
                }

                break;
            }
        }
        ticks++;
    };

    @EventLink()
    public final Listener<MoveEvent> onMove = event -> {

        if (!MoveUtil.isMoving()) return;

        if (mode.getValue().getName().equals("yPort")) {
            if (mc.thePlayer.onGround) {
                event.setPosY(0.42F);
                MoveUtil.strafe(0.69F + MoveUtil.speedPotionAmp(0.1));
                mc.thePlayer.motionY = 0F;
            } else {
                MoveUtil.strafe(0.41F + MoveUtil.speedPotionAmp(0.055));
            }

            mc.thePlayer.setSprinting(true);
            mc.thePlayer.omniSprint = true;
        }
    };

    @EventLink()
    public final Listener<MoveInputEvent> onMoveInput = event -> {
        event.setJump(false);
        event.setSneak(false);
    };
}