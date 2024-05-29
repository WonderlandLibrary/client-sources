package dev.client.tenacity.module.impl.movement;

import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.client.tenacity.module.impl.combat.TargetStrafe;
import dev.client.tenacity.utils.player.MovementUtils;
import dev.event.EventListener;
import dev.event.impl.player.MotionEvent;
import dev.event.impl.player.MoveEvent;
import dev.settings.impl.BooleanSetting;
import dev.settings.impl.ModeSetting;
import dev.settings.impl.NumberSetting;
import dev.utils.misc.MathUtils;
import dev.utils.time.TimerUtil;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Speed extends Module {
    private final ModeSetting mode = new ModeSetting("Mode", "Watchdog", "Watchdog", "Matrix");
    private final ModeSetting watchdogMode = new ModeSetting("Watchdog Mode", "Hop", "Hop", "Low Hop", "Ground");
    private final BooleanSetting fastfall = new BooleanSetting("Fast Fall", false);
    private final NumberSetting timer = new NumberSetting("Timer", 1, 5, 1, 0.1);
    private final TimerUtil timerUtil = new TimerUtil();
    private float speed;
    private int stage;

    public Speed() {
        super("Speed", Category.MOVEMENT, "Makes you go faster");
        fastfall.addParent(watchdogMode, modeSetting -> modeSetting.is("Hop"));
        this.addSettings(mode, watchdogMode, fastfall, timer);
    }

    private final EventListener<MotionEvent> onMotion = e -> {
        mc.timer.timerSpeed = timer.getValue().floatValue();
        switch (mode.getMode()) {
            case "Watchdog":
                switch (watchdogMode.getMode()) {
                    case "Hop":
                        if (e.isPre()) {
                            if (mc.thePlayer.onGround) {
                                if (MovementUtils.isMoving()) {
                                    mc.thePlayer.jump();
                                    stage = 0;
                                    speed = 1.10f;
                                }
                            } else {
                                if (fastfall.isEnabled()) {
                                    stage++;
                                    if (stage == 3) {
                                        mc.thePlayer.motionY -= 0.05;
                                    }
                                    if (stage == 5) {
                                        mc.thePlayer.motionY -= 0.184;
                                    }
                                }
                                speed -= 0.004;
                                MovementUtils.setSpeed(MovementUtils.getBaseMoveSpeed() * speed);
                            }
                        }
                        break;
                    case "Low Hop":
                        if (e.isPre()) {
                            float moveYaw = MovementUtils.getMoveYaw(e.getYaw()) % 360.0F;
                            e.setYaw(moveYaw + (MathUtils.getRandomInRange(-1, 1)));
                            if (mc.thePlayer.onGround) {
                                speed = 1.1f;
                                MovementUtils.setSpeed(MovementUtils.getBaseMoveSpeed() * 1.6);
                            } else {
                                speed -= 0.004;
                                MovementUtils.setSpeed(MovementUtils.getBaseMoveSpeed() * speed);
                            }
                        }
                        break;
                    case "Ground":
                        if (e.isPre()) {
                            if (mc.thePlayer.onGround && MovementUtils.isMoving()) {
                                float moveYaw = MovementUtils.getMoveYaw(e.getYaw()) % 360.0F;
                                double yaw = Math.toRadians(moveYaw);
                                double x = e.getX() + (-Math.sin(yaw) * 0.1);
                                double z = e.getZ() + (Math.cos(yaw) * 0.1);
                                mc.thePlayer.setPosition(x, mc.thePlayer.posY, z);
                                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(x, e.getY(), z, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, true));
                            }
                        }
                        break;
                }
                break;
            case "Matrix":
                if (MovementUtils.isMoving()) {
                    if (mc.thePlayer.onGround && mc.thePlayer.motionY < 0.003) {
                        mc.thePlayer.jump();
                        mc.timer.timerSpeed = 1.0f;
                    }
                    if (mc.thePlayer.motionY > 0.003) {
                        mc.thePlayer.motionX *= speed;
                        mc.thePlayer.motionZ *= speed;
                        mc.timer.timerSpeed = 1.05f;
                    }
                    speed = 1.0012f;
                }
                break;
        }
    };

    private final EventListener<MoveEvent> onMovePlayer = e -> {
        switch (watchdogMode.getMode()) {
            case "Low Hop":
                if (MovementUtils.isMoving()) {
                    double base = MathUtils.round(mc.thePlayer.posY - (int) mc.thePlayer.posY, 3);
                    if (!mc.thePlayer.isCollidedHorizontally) {
                        if (base == MathUtils.round(0.4, 3)) {
                            e.setY(mc.thePlayer.motionY = 0.31);
                        } else if (base == MathUtils.round(0.71, 3)) {
                            e.setY(mc.thePlayer.motionY = 0.04);
                        } else if (base == MathUtils.round(0.75, 3)) {
                            e.setY(mc.thePlayer.motionY = -0.2);
                        } else if (base == MathUtils.round(0.55, 3)) {
                            e.setY(mc.thePlayer.motionY = -0.19);
                        } else if (base == MathUtils.round(0.41, 3)) {
                            e.setY(mc.thePlayer.motionY = -0.2);
                        }
                    }
                    if (mc.thePlayer.onGround) {
                        e.setY(mc.thePlayer.motionY = 0.4);
                    }
                }
                break;
        }
        TargetStrafe.strafe(e);
    };

    @Override
    public void onEnable() {
        speed = 1.1f;
        timerUtil.reset();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        mc.thePlayer.motionX = mc.thePlayer.motionZ = 0;
        mc.timer.timerSpeed = 1;
        super.onDisable();
    }

}
