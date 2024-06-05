package io.github.nevalackin.radium.module.impl.movement;

import io.github.nevalackin.radium.event.impl.player.MoveEntityEvent;
import io.github.nevalackin.radium.event.impl.player.UpdatePositionEvent;
import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import io.github.nevalackin.radium.property.impl.EnumProperty;
import io.github.nevalackin.radium.utils.MovementUtils;
import io.github.nevalackin.radium.utils.Wrapper;
import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.client.entity.EntityPlayerSP;
import org.lwjgl.input.Keyboard;

@ModuleInfo(label = "Long Jump", category = ModuleCategory.MOVEMENT, key = Keyboard.KEY_Z)
public final class LongJump extends Module {

    private final EnumProperty<LongJumpMode> longJumpModeProperty = new EnumProperty<>("Mode", LongJumpMode.WATCHDOG);

    private double lastDist;
    private double moveSpeed;

    private int stage;

    private int groundTicks;

    public LongJump() {
        setSuffixListener(longJumpModeProperty);
    }

    @Override
    public void onEnable() {
        Step.cancelStep = true;
        stage = 0;
        groundTicks = 0;
    }

    @Override
    public void onDisable() {
        Step.cancelStep = false;
    }

    @Listener
    private void onUpdatePositionEvent(UpdatePositionEvent e) {
        if (e.isPre()) {
            final EntityPlayerSP player = Wrapper.getPlayer();
            double xDist = player.posX - player.lastTickPosX;
            double zDist = player.posZ - player.lastTickPosZ;

            lastDist = Math.sqrt(xDist * xDist + zDist * zDist);

            if (MovementUtils.isOnGround() &&
                    !Wrapper.getPlayer().isOnLadder() &&
                    ++groundTicks >= 1)
                toggle();

            if (player.fallDistance < 1.0F) {
                if (isWatchdog() && player.motionY < 0.0D)
                    player.motionY *= 0.75D;

                player.motionY += 0.005D;
            }
        }
    }

    private boolean isWatchdog() {
        return longJumpModeProperty.isSelected(LongJumpMode.WATCHDOG);
    }

    @Listener
    private void onMoveEntityEvent(MoveEntityEvent e) {
        if (MovementUtils.isMoving()) {
            double baseMoveSpeed = MovementUtils.getBaseMoveSpeed();
            switch (stage) {
                case 0:
                    if (MovementUtils.isOnGround()) {
                        moveSpeed = baseMoveSpeed * MovementUtils.MAX_DIST;
                        e.setY(Wrapper.getPlayer().motionY = MovementUtils.getJumpHeight(MovementUtils.VANILLA_JUMP_HEIGHT));
                    }
                    break;
                case 1:
                    moveSpeed *= 2.0D;
                case 2:
                    double difference = (((isWatchdog() ?
                            MovementUtils.WATCHDOG_BUNNY_SLOPE :
                            MovementUtils.BUNNY_SLOPE) * 0.7D) + MovementUtils.getJumpBoostModifier() * 0.2D)
                            * (moveSpeed - baseMoveSpeed);
                    moveSpeed = moveSpeed - difference;
                    break;
                default:
                    moveSpeed = MovementUtils.calculateFriction(moveSpeed, lastDist, baseMoveSpeed);
                    break;
            }

            MovementUtils.setSpeed(e, Math.max(moveSpeed, baseMoveSpeed));
            stage++;
        }
    }

    private enum LongJumpMode {
        NCP, WATCHDOG
    }

}
