package net.minecraft.util;

import dev.excellent.Excellent;
import dev.excellent.api.event.impl.player.MoveInputEvent;
import net.minecraft.client.GameSettings;

public class MovementInputFromOptions extends MovementInput {
    private final GameSettings gameSettings;

    public MovementInputFromOptions(GameSettings gameSettingsIn) {
        this.gameSettings = gameSettingsIn;
    }

    public void tickMovement(boolean isSneak) {
        this.forwardKeyDown = this.gameSettings.keyBindForward.isKeyDown();
        this.backKeyDown = this.gameSettings.keyBindBack.isKeyDown();
        this.leftKeyDown = this.gameSettings.keyBindLeft.isKeyDown();
        this.rightKeyDown = this.gameSettings.keyBindRight.isKeyDown();
        this.moveForward = this.forwardKeyDown == this.backKeyDown ? 0.0F : (this.forwardKeyDown ? 1.0F : -1.0F);
        this.moveStrafe = this.leftKeyDown == this.rightKeyDown ? 0.0F : (this.leftKeyDown ? 1.0F : -1.0F);
        this.jump = this.gameSettings.keyBindJump.isKeyDown();
        this.sneaking = this.gameSettings.keyBindSneak.isKeyDown();

        MoveInputEvent event = new MoveInputEvent(moveForward, moveStrafe, jump, sneaking, 0.3D);
        Excellent.getInst().getEventBus().handle(event);

        if (event.isCancelled()) return;

        final double sneakMultiplier = event.getSneakSlowDownMultiplier();
        this.moveForward = event.getForward();
        this.moveStrafe = event.getStrafe();
        this.jump = event.isJump();
        this.sneaking = event.isSneaking();

        if (isSneak) {
            this.moveStrafe = (float) (this.moveStrafe * sneakMultiplier);
            this.moveForward = (float) (this.moveForward * sneakMultiplier);
        }
    }
}
