package io.github.nevalackin.radium.module.impl.movement;

import io.github.nevalackin.radium.event.impl.player.MoveEntityEvent;
import io.github.nevalackin.radium.event.impl.player.UpdatePositionEvent;
import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import io.github.nevalackin.radium.module.ModuleManager;
import io.github.nevalackin.radium.property.Property;
import io.github.nevalackin.radium.property.impl.DoubleProperty;
import io.github.nevalackin.radium.property.impl.EnumProperty;
import io.github.nevalackin.radium.utils.MovementUtils;
import io.github.nevalackin.radium.utils.ServerUtils;
import io.github.nevalackin.radium.utils.Wrapper;
import me.zane.basicbus.api.annotations.Listener;
import me.zane.basicbus.api.annotations.Priority;
import org.lwjgl.input.Keyboard;

@ModuleInfo(label = "Speed", key = Keyboard.KEY_V, category = ModuleCategory.MOVEMENT)
public final class Speed extends Module {

    private final EnumProperty<SpeedMode> speedModeProperty = new EnumProperty<>("Mode", SpeedMode.WATCHDOG);
    private final Property<Boolean> watchdogTimerProperty = new Property<>("Timer", false, this::isWatchdog);
    private final DoubleProperty customSpeedProperty = new DoubleProperty("Custom Speed", 0.5, this::isCustomSpeed, 0.0, 10.0, 0.1);
    private final Property<Boolean> customJumpProperty = new Property<>("Jump On Ground", false, this::isCustomSpeed);
    private double lastDist;
    private double moveSpeed;
    private int ticksSinceJump;
    private boolean wasOnGround;

    public Speed() {
        setSuffixListener(speedModeProperty);
    }

    public static boolean isSpeeding() {
        return ModuleManager.getInstance(Speed.class).isEnabled();
    }

    private boolean isWatchdog() {
        return speedModeProperty.isSelected(SpeedMode.WATCHDOG);
    }

    private boolean isCustomSpeed() {
        return speedModeProperty.isSelected(SpeedMode.CUSTOM);
    }

    @Override
    public void onEnable() {
        ticksSinceJump = 3;
    }

    @Override
    public void onDisable() {
        Wrapper.getTimer().timerSpeed = 1.0f;
    }

    @Listener(Priority.LOW)
    private void onUpdatePositionEvent(UpdatePositionEvent e) {
        if (e.isPre()) {
            switch (speedModeProperty.getValue()) {
                case WATCHDOG:
                    if (e.isOnGround() && ServerUtils.isOnHypixel())
                        e.setPosY(e.getPosY() + (0.0003F + (Math.random() * 0.0002F)));

                    if (Wrapper.getPlayer().posY > Wrapper.getPlayer().lastTickPosY)
                        Wrapper.getPlayer().motionY -= 0.005D;
                case NCP:
                    double xDist = Wrapper.getPlayer().posX - Wrapper.getPlayer().lastTickPosX;
                    double zDist = Wrapper.getPlayer().posZ - Wrapper.getPlayer().lastTickPosZ;

                    lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
                    break;
            }
        }
    }

    @Listener(Priority.LOW)
    private void onMoveEntityEvent(MoveEntityEvent e) {
        switch (speedModeProperty.getValue()) {
            case NCP:
            case WATCHDOG:
                if (MovementUtils.isMoving()) {
                    double baseMoveSpeed = MovementUtils.getBaseMoveSpeed();
                    if (MovementUtils.isInLiquid()) {
                        moveSpeed = baseMoveSpeed;
                        e.setY(Wrapper.getPlayer().motionY = MovementUtils.getJumpHeight(
                                MovementUtils.VANILLA_JUMP_HEIGHT));
                        ticksSinceJump = 0;
                    } else if (MovementUtils.isOnGround()) {
                        if (watchdogTimerProperty.getValue())
                            Wrapper.getTimer().timerSpeed = 1.3F;
                        wasOnGround = true;
                        moveSpeed = baseMoveSpeed * (ticksSinceJump > 1 ?
                                (isWatchdog() && !ModuleManager.getInstance(Scaffold.class).isEnabled() ?
                                        MovementUtils.MAX_DIST : 2.0D) : 1.55D);
                        if (MovementUtils.isOnIce())
                            moveSpeed *= MovementUtils.ICE_MOD;
                        e.setY(Wrapper.getPlayer().motionY = MovementUtils.getJumpHeight(
                                MovementUtils.VANILLA_JUMP_HEIGHT));
                        ticksSinceJump = 0;
                    } else if (wasOnGround) {
                        if (isWatchdog() && watchdogTimerProperty.getValue())
                            Wrapper.getTimer().timerSpeed = 1.185F;
                        double difference = MovementUtils.getBunnySlope(isWatchdog()) * (lastDist - baseMoveSpeed);
                        moveSpeed = lastDist - difference;
                        wasOnGround = false;
                    } else {
                        ticksSinceJump++;
                        moveSpeed = MovementUtils.calculateFriction(moveSpeed,
                                lastDist, baseMoveSpeed);
                    }

                    MovementUtils.setSpeed(e, Math.max(moveSpeed, baseMoveSpeed));
                }
                break;
            case CUSTOM:
                if (MovementUtils.isMoving()) {
                    if (customJumpProperty.getValue() && MovementUtils.isOnGround()) {
                        e.setY(Wrapper.getPlayer().motionY = 0.42f);
                    }

                    MovementUtils.setSpeed(e, customSpeedProperty.getValue());
                }
                break;
        }
    }

    private enum SpeedMode {
        WATCHDOG, CUSTOM, NCP
    }
}
