package club.strifeclient.module.implementations.movement.speed;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import club.strifeclient.event.implementations.player.MotionEvent;
import club.strifeclient.event.implementations.player.MoveEvent;
import club.strifeclient.event.implementations.player.StrafeEvent;
import club.strifeclient.module.implementations.movement.Speed;
import club.strifeclient.setting.Mode;
import club.strifeclient.setting.implementations.BooleanSetting;
import club.strifeclient.util.math.MathUtil;
import club.strifeclient.util.player.MovementUtil;

import java.util.Random;

public final class WatchdogSpeed extends Mode<Speed.SpeedMode> {

    private int stage;
    private double moveSpeed;
    private double speedMultiplier;

    private BooleanSetting strafeSetting = new BooleanSetting("Strafe", true);

    @Override
    public Speed.SpeedMode getRepresentation() {
        return Speed.SpeedMode.WATCHDOG;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        speedMultiplier = 0;
        stage = 0;
        moveSpeed = 0;
        mc.timer.timerSpeed = 1.0f;
        mc.thePlayer.motionX = mc.thePlayer.motionZ = 0;
    }

    @EventHandler
    private final Listener<StrafeEvent> strafeEventListener = e -> {
        if (strafeSetting.getValue()) {
            final double baseSpeed = MovementUtil.getBaseMovementSpeed();
            if (MovementUtil.isMovingOnGround()) {
                mc.thePlayer.motionY = MovementUtil.getJumpHeight(0.42F);
                stage = 0;
            }
            switch (stage) {
                case 0: {
                    moveSpeed = baseSpeed * 2.13;
                    break;
                }
                case 1: {
                    moveSpeed *= 0.58;
                    break;
                }
                case 2: {
                    moveSpeed = baseSpeed * 1.2;
                    break;
                }
                default: {
                    moveSpeed = moveSpeed / 100 * 98.5f;
                    break;
                }
            }
            if (MovementUtil.isMoving()) {
                double speed = Math.max(baseSpeed, moveSpeed);
                if (mc.thePlayer.ticksExisted % 2 == 0)
                    speed += 1E-7;
                else speed -= 9E-16;
                try { MovementUtil.strafe(e, speed, 0.235F * new Random(System.currentTimeMillis()).nextFloat()); } catch (Exception ex) { ex.printStackTrace(); }
            } else {
//                e.setMotionPartialStrafe(0, 0);
//                mc.thePlayer.motionX = mc.thePlayer.motionZ = 0;
            }
            stage++;
        }
    };

    @EventHandler
    private final Listener<MoveEvent> moveEventListener = e -> {
        if (!strafeSetting.getValue()) {
            final double baseSpeed = MovementUtil.getBaseMovementSpeed();
            if (MovementUtil.isMovingOnGround()) {
                e.y = mc.thePlayer.motionY = MovementUtil.getJumpHeight(0.42F);
                stage = 0;
            }
            switch (stage) {
                case 0: {
                    moveSpeed = baseSpeed * 2.13;
                    break;
                }
                case 1: {
                    moveSpeed *= 0.58;
                    break;
                }
                case 2: {
                    moveSpeed = baseSpeed * 1.2;
                    break;
                }
                default: {
                    moveSpeed = moveSpeed / 100 * 98.5f;
                    break;
                }
            }
            if (MovementUtil.isMoving()) {
                double speed = Math.max(baseSpeed, moveSpeed);
                if (mc.thePlayer.ticksExisted % MathUtil.randomInt(1, 3) == 0)
                    speed += 1E-7;
//                final double value = (float)(mc.thePlayer.ticksExisted % 10) * Math.pow(10, -7);
                MovementUtil.strafeMove(e, speed);
            }
            stage++;
        }
    };

    @EventHandler
    private final Listener<MotionEvent> motionEventListener = e -> {
    };
}
