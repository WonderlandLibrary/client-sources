package com.polarware.module.impl.movement.speed;

import com.polarware.module.impl.movement.SpeedModule;
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

public class NCPSpeed extends Mode<SpeedModule> {

    private final NumberValue jumpMotion = new NumberValue("Jump Motion", this, 0.4, 0.4, 0.42F, 0.01);
    private final NumberValue groundSpeed = new NumberValue("Ground Speed", this, 1.75, 0.1, 2.5, 0.05);
    private final NumberValue bunnySlope = new NumberValue("Bunny Slope", this, 0.66, 0, 1, 0.01);
    private final NumberValue timer = new NumberValue("Timer", this, 1, 0.1, 10, 0.05);

    private boolean reset;
    private double speed;

    public NCPSpeed(String name, SpeedModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<StrafeEvent> onStrafe = event -> {

        final double base = MoveUtil.getAllowedHorizontalDistance();

        if (MoveUtil.isMoving()) {
            switch (mc.thePlayer.offGroundTicks) {
                case 0:
                    float jumpMotion = this.jumpMotion.getValue().floatValue();

                    float motion = mc.thePlayer.isCollidedHorizontally ? 0.42F : jumpMotion == 0.4f ? jumpMotion : 0.42f;
                    mc.thePlayer.motionY = MoveUtil.jumpBoostMotion(motion);
                    speed = base * groundSpeed.getValue().doubleValue();
                    break;

                case 1:
                    speed -= (bunnySlope.getValue().doubleValue() * (speed - base));
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
        speed = 0;
    }
}