package com.polarware.module.impl.movement.longjump;

import com.polarware.module.impl.movement.LongJumpModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.StrafeEvent;
import com.polarware.event.impl.other.TeleportEvent;
import com.polarware.util.player.MoveUtil;
import com.polarware.value.Mode;
import com.polarware.value.impl.NumberValue;

/**
 * @author Auth
 * @since 18/11/2021
 */

public class NCPLongJump extends Mode<LongJumpModule> {

    private final NumberValue bunnyFriction = new NumberValue("Bunny Friction", this, 159, 59, 259, 1);
    private final NumberValue groundSpeed = new NumberValue("Ground Speed", this, 0.4, 0.1, 3, 0.1);
    private final NumberValue jumpSpeed = new NumberValue("Jump Speed", this, 1.4, 0, 3, 0.1);
    private final NumberValue glide = new NumberValue("Glide", this, 0, 0, 3, 0.5);
    private final NumberValue timer = new NumberValue("Timer", this, 1, 0.1, 10, 0.1);

    private boolean reset;
    private double speed;

    public NCPLongJump(String name, LongJumpModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<StrafeEvent> onStrafe = event -> {

        final double base = MoveUtil.getAllowedHorizontalDistance();

        if (MoveUtil.isMoving()) {
            switch (mc.thePlayer.offGroundTicks) {
                case 0:
                    mc.thePlayer.motionY = MoveUtil.jumpMotion();
                    speed = groundSpeed.getValue().doubleValue();
                    break;

                case 1:
                    speed = jumpSpeed.getValue().doubleValue();
                    break;

                default:
                    speed -= speed / MoveUtil.BUNNY_FRICTION;
                    break;
            }

            mc.timer.timerSpeed = timer.getValue().floatValue();
            reset = false;
        } else if (!reset) {
            speed = MoveUtil.getAllowedHorizontalDistance();
            mc.timer.timerSpeed = 1;
            reset = true;
        }

        if (mc.thePlayer.fallDistance > 0) {
            mc.thePlayer.motionY += glide.getValue().floatValue() / 100;
        }

        if (mc.thePlayer.isCollidedHorizontally) {
            speed = MoveUtil.getAllowedHorizontalDistance();
        }

        event.setSpeed(Math.max(speed, base), Math.random() / 2000);
    };

    @EventLink()
    public final Listener<TeleportEvent> onTeleport = event -> {
        speed = 0;
    };

    @Override
    public void onDisable() {
        MoveUtil.stop();
        speed = 0;
    }
}