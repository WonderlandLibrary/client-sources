package dev.star.module.impl.movement;

import dev.star.event.impl.network.PacketReceiveEvent;
import dev.star.event.impl.player.MotionEvent;
import dev.star.event.impl.player.MoveEvent;
import dev.star.event.impl.player.PlayerMoveUpdateEvent;
import dev.star.module.Category;
import dev.star.module.Module;
import dev.star.module.settings.impl.BooleanSetting;
import dev.star.module.settings.impl.ModeSetting;
import dev.star.module.settings.impl.NumberSetting;
import dev.star.gui.notifications.NotificationManager;
import dev.star.gui.notifications.NotificationType;
import dev.star.utils.player.MovementUtils;
import dev.star.utils.time.TimerUtil;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;

import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("unused")
public final class Speed extends Module {
    private final ModeSetting mode = new ModeSetting("Mode", "Watchdog", "Watchdog", "Verus", "Vulcan");
    private final ModeSetting WatchdogMode = new ModeSetting("Watchdog Mode", "Ground Strafe", "Ground Strafe", "Fast Fall", "Low Hop");
    private final ModeSetting verusMode = new ModeSetting("Verus Mode", "Normal", "Low", "Normal");
    private final BooleanSetting autoDisable = new BooleanSetting("Auto Disable", false);
    private final NumberSetting timer = new NumberSetting("Timer", 1, 5, 1, 0.1);
    private final float r = ThreadLocalRandom.current().nextFloat();
    private final TimerUtil timerUtil = new TimerUtil();
    private boolean strafe, wasOnGround;
    private boolean setTimer = true;
    private double speed, lastDist;
    private double moveSpeed;
    private int inAirTicks;
    private int stage;

    public Speed() {
        super("Speed", Category.MOVEMENT, "Makes you go faster");
        verusMode.addParent(mode, modeSetting -> modeSetting.is("Verus"));
        this.addSettings(mode, WatchdogMode,  verusMode, autoDisable,  timer);
        WatchdogMode.addParent(mode, modeSetting -> modeSetting.is("Watchdog"));
    }

    @Override
    public void onMoveEvent(MoveEvent e) {
        if (this.isEnabled())
        {
        if (mode.is("Watchdog")) {
            switch (WatchdogMode.getMode()) {
                case "Ground Strafe":
                    break;
                case "Fast Fall":
                    if (mc.thePlayer.onGround) {
                        MovementUtils.jump(e);
                        e.setY(e.getY() + 1E-4);
                        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                            MovementUtils.strafe(e, 0.60 - Math.random() * 0.001 + MovementUtils.getSpeedAmplifier() * 0.08);
                        } else {
                            MovementUtils.strafe(e, 0.61 - Math.random() * 0.001);
                        }
                        inAirTicks = 0;
                    } else {
                        inAirTicks++;
                        if (inAirTicks == 9)
                            e.setY(mc.thePlayer.motionY = -0.225);
                    }
                    break;
                case "Low Hop":
                    if (mc.thePlayer.onGround) {
                        MovementUtils.jump(e);
                      //  e.setY(e.getY() + 1E-4);
                        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                            MovementUtils.strafe(e, 0.60 - Math.random() * 0.001 + MovementUtils.getSpeedAmplifier() * 0.08);
                        } else {
                            MovementUtils.strafe(e, 0.61 - Math.random() * 0.001);
                        }
                        inAirTicks = 0;
                    } else {
                        inAirTicks++;
                        if (inAirTicks == 6)
                            e.setY(mc.thePlayer.motionY = -0.225);
                    }

                    break;
            }
        }
        }
    }

    @Override
    public void onMotionEvent(MotionEvent e) {
        if (this.isEnabled()) {
        this.setSuffix(mode.getMode());
        if (setTimer) {
            mc.timer.timerSpeed = timer.getValue().floatValue();
        }

        double distX = e.getX() - mc.thePlayer.prevPosX, distZ = e.getZ() - mc.thePlayer.prevPosZ;
        lastDist = Math.hypot(distX, distZ);

        switch (mode.getMode()) {
            case "Watchdog":
                switch (WatchdogMode.getMode()) {
                    case "Ground Strafe":
                        if (e.isPre()) {
                            if (mc.thePlayer.onGround) {
                                if (MovementUtils.isMoving()) {
                                    mc.thePlayer.jump();
                                    MovementUtils.setSpeed(MovementUtils.getBaseMoveSpeed() * 1.6);
                                    inAirTicks = 0;
                                }
                            } else {
                                inAirTicks++;
                                if (inAirTicks == 1)
                                    MovementUtils.setSpeed(MovementUtils.getBaseMoveSpeed() * 1.15);
                            }
                        }
                        break;
                }
                break;
            case "Vulcan":
                if (e.isPre()) {
                    if (mc.thePlayer.onGround) {
                        if (MovementUtils.isMoving()) {
                            mc.thePlayer.jump();
                            MovementUtils.setSpeed(MovementUtils.getBaseMoveSpeed() * 1.6);
                            inAirTicks = 0;
                        }
                    } else {
                        inAirTicks++;

                        if (inAirTicks == 1)
                        {
                            MovementUtils.setSpeed(MovementUtils.getBaseMoveSpeed() * 1.16);
                        }
                               //MovementUtils.setSpeed(MovementUtils.getBaseMoveSpeed() * 1.16);
                    }
                }
                break;
            case "Verus":
                switch (verusMode.getMode()) {
                    case "Low":
                        if (e.isPre()) {
                            if (MovementUtils.isMoving()) {
                                if (mc.thePlayer.onGround) {
                                    mc.thePlayer.jump();
                                    wasOnGround = true;
                                } else if (wasOnGround) {
                                    if (!mc.thePlayer.isCollidedHorizontally) {
                                        mc.thePlayer.motionY = -0.0784000015258789;
                                    }
                                    wasOnGround = false;
                                }
                                MovementUtils.setSpeed(0.33);
                            } else {
                                mc.thePlayer.motionX = mc.thePlayer.motionZ = 0;
                            }
                        }
                        break;
                    case "Normal":
                        if (e.isPre()) {
                            if (MovementUtils.isMoving()) {
                                if (mc.thePlayer.onGround) {
                                    mc.thePlayer.jump();
                                    MovementUtils.setSpeed(0.48);
                                } else {
                                    MovementUtils.setSpeed(MovementUtils.getSpeed());
                                }
                            } else {
                                MovementUtils.setSpeed(0);
                            }
                        }
                        break;
                }
                break;
        }
        }

    }


    @Override
    public void onPacketReceiveEvent(PacketReceiveEvent e) {
        if (e.getPacket() instanceof S08PacketPlayerPosLook && autoDisable.isEnabled()) {
            NotificationManager.post(NotificationType.WARNING, "Flag Detector",
                    "Speed disabled due to " +
                            (mc.thePlayer == null || mc.thePlayer.ticksExisted < 5
                                    ? "world change"
                                    : "lagback"), 1.5F);
            this.toggleSilent();
        }
    }

    @Override
    public void onEnable() {
        speed = 1.5f;
        timerUtil.reset();
        if (mc.thePlayer != null) {
            wasOnGround = mc.thePlayer.onGround;
        }
        inAirTicks = 0;
        moveSpeed = 0;
        stage = 0;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1;
        mc.thePlayer.motionX = 0;
        mc.thePlayer.motionZ = 0;
        super.onDisable();
    }

}
