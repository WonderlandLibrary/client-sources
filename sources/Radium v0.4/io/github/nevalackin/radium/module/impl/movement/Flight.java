package io.github.nevalackin.radium.module.impl.movement;

import io.github.nevalackin.radium.event.impl.player.MoveEntityEvent;
import io.github.nevalackin.radium.event.impl.player.StepEvent;
import io.github.nevalackin.radium.event.impl.player.UpdatePositionEvent;
import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import io.github.nevalackin.radium.property.Property;
import io.github.nevalackin.radium.property.impl.DoubleProperty;
import io.github.nevalackin.radium.property.impl.EnumProperty;
import io.github.nevalackin.radium.property.impl.Representation;
import io.github.nevalackin.radium.utils.MovementUtils;
import io.github.nevalackin.radium.utils.ServerUtils;
import io.github.nevalackin.radium.utils.TimerUtil;
import io.github.nevalackin.radium.utils.Wrapper;
import me.zane.basicbus.api.annotations.Listener;
import me.zane.basicbus.api.annotations.Priority;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C03PacketPlayer;

@ModuleInfo(label = "Flight", category = ModuleCategory.MOVEMENT)
public final class Flight extends Module {

    private final EnumProperty<FlightMode> flightModeProperty = new EnumProperty<>("Mode", FlightMode.WATCHDOG);
    private final Property<Boolean> viewBobbingProperty = new Property<>("View Bobbing", true);
    private final Property<Boolean> damageProperty = new Property<>("Damage", true,
            this::isWatchdog);
    private final Property<Boolean> timerProperty = new Property<>("Timer", true,
            this::isWatchdog);
    private final DoubleProperty timerSpeedProperty = new DoubleProperty("Timer Speed", 1.7,
            () -> isWatchdog() && timerProperty.getValue(),
            1.0, 3.0, 0.1);
    private final DoubleProperty timerDurationProperty = new DoubleProperty("Timer Duration", 600L,
            () -> isWatchdog() && timerProperty.getValue(),
            0, 1000, 10, Representation.MILLISECONDS);
    private final DoubleProperty motionSpeedProperty = new DoubleProperty("Speed", 1.0,
            () -> flightModeProperty.getValue() == FlightMode.MOTION,
            0.1, 5.0, 0.1);
    private final DoubleProperty initialWatchdogSpeedMultiplier = new DoubleProperty("Initial Speed Multiplier", 1.0,
            this::isWatchdog,
            0.0, 1.0, 0.01);
    private final DoubleProperty watchdogSpeedProperty = new DoubleProperty("Speed", 1.0,
            this::isWatchdog,
            0.1, 10.0, 0.1);
    private final DoubleProperty watchdogSpeedReductionFactorProperty = new DoubleProperty("Reduction Factor", 0.4,
            this::isWatchdog,
            0.0, 1.0, 0.01);
    private final TimerUtil timer = new TimerUtil();
    private double lastDist;
    private double moveSpeed;
    private int stage;

    public Flight() {
        setSuffixListener(flightModeProperty);
    }

    private boolean isWatchdog() {
        return flightModeProperty.isSelected(FlightMode.WATCHDOG);
    }

    @Override
    public void onEnable() {
        Step.cancelStep = true;
        stage = 0;
        timer.reset();
    }

    @Override
    public void onDisable() {
        Step.cancelStep = false;
        Wrapper.getTimer().timerSpeed = 1.0F;
        if (flightModeProperty.getValue() == FlightMode.WATCHDOG) {
            Wrapper.getPlayer().motionX = 0.0D;
            Wrapper.getPlayer().motionZ = 0.0D;
        }
    }

    @Listener
    public void onStepEvent(StepEvent e) {
        e.setStepHeight(0.0F);
    }

    @Listener(Priority.HIGH)
    private void onUpdatePositionEvent(UpdatePositionEvent e) {
        if (e.isPre()) {
            final EntityPlayerSP player = Wrapper.getPlayer();
            if (viewBobbingProperty.getValue())
                player.cameraYaw = 0.105F;
            switch (flightModeProperty.getValue()) {
                case WATCHDOG:
                    if (timerProperty.getValue()) {
                        if (timer.hasElapsed(timerDurationProperty.getValue().longValue()))
                            Wrapper.getTimer().timerSpeed = 1.0F;
                        else
                            Wrapper.getTimer().timerSpeed = timerSpeedProperty.getValue().floatValue();
                    }

                    e.setOnGround(true);

                    player.motionY = 0.0D;

                    if (ServerUtils.isOnHypixel())
                        player.setPosition(
                                player.posX,
                                player.posY + (player.ticksExisted % 2 == 0 ? -0.0003F : 0.0003F),
                                player.posZ);

                    double xDist = player.posX - player.lastTickPosX;
                    double zDist = player.posZ - player.lastTickPosZ;

                    lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
                    break;
                case MOTION:
                    if (Wrapper.getGameSettings().keyBindJump.isKeyDown()) {
                        player.motionY = 1.0F;
                    } else if (Wrapper.getGameSettings().keyBindSneak.isKeyDown()) {
                        player.motionY = -1.0F;
                    } else {
                        Wrapper.getPlayer().motionY = 0.0D;
                    }
                    break;
                case REDESKY:
                    player.motionY = 0.0D;

                    Wrapper.getTimer().timerSpeed = 0.6F;
                    break;
            }
        }
    }

    @Listener(Priority.HIGH)
    private void onMoveEntityEvent(MoveEntityEvent e) {
        switch (flightModeProperty.getValue()) {
            case WATCHDOG:
                if (MovementUtils.isMoving()) {
                    double baseMoveSpeed = MovementUtils.getBaseMoveSpeed();
                    switch (stage) {
                        case 0:
                            moveSpeed = baseMoveSpeed;
                            if (MovementUtils.isOnGround()) {
                                e.setY(Wrapper.getPlayer().motionY = MovementUtils.getJumpHeight(MovementUtils.VANILLA_JUMP_HEIGHT));
                                if (damageProperty.getValue() && MovementUtils.fallDistDamage() || Wrapper.getPlayer().fallDistance >= 3.0F)
                                    moveSpeed = baseMoveSpeed * (MovementUtils.MAX_DIST * initialWatchdogSpeedMultiplier.getValue());
                            }
                            break;
                        case 1:
                            moveSpeed *= watchdogSpeedProperty.getValue();
                            double difference = watchdogSpeedReductionFactorProperty.getValue() * (moveSpeed - baseMoveSpeed);
                            moveSpeed = moveSpeed - difference;
                            break;
                        case 2:
                            double lastDif = watchdogSpeedReductionFactorProperty.getValue() * (lastDist - baseMoveSpeed);
                            moveSpeed = lastDist - lastDif;
                            break;
                        default:
                            moveSpeed = lastDist - lastDist / MovementUtils.BUNNY_DIV_FRICTION;
                            break;
                    }
                    MovementUtils.setSpeed(e, Math.max(baseMoveSpeed, moveSpeed));
                    stage++;
                }
                break;
            case MOTION:
                if (MovementUtils.isMoving())
                    MovementUtils.setSpeed(e, motionSpeedProperty.getValue());
                break;
            case REDESKY:
                if (MovementUtils.isMoving()) {
                    MovementUtils.setSpeed(e, 1.0);
                    if (Wrapper.getPlayer().ticksExisted % 2 == 0) {
                        Wrapper.sendPacketDirect(new C03PacketPlayer.C04PacketPlayerPosition(
                                Wrapper.getPlayer().posX + e.getX(), Wrapper.getPlayer().posY,
                                Wrapper.getPlayer().posZ + e.getZ(), true));
                    }
                }
                break;
        }
    }

    private enum FlightMode {
        WATCHDOG, MOTION, REDESKY
    }
}
