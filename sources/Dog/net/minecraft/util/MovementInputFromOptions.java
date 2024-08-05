package net.minecraft.util;

import net.minecraft.client.settings.GameSettings;
import fr.dog.Dog;
import fr.dog.event.impl.player.move.MoveInputEvent;

public class MovementInputFromOptions extends MovementInput {
    private final GameSettings gameSettings;

    public MovementInputFromOptions(GameSettings gameSettingsIn) {
        this.gameSettings = gameSettingsIn;
    }

    public void updatePlayerMoveState() {
        this.moveStrafe = 0.0F;
        this.moveForward = 0.0F;

        if (this.gameSettings.keyBindForward.isKeyDown()) {
            ++this.moveForward;
        }

        if (this.gameSettings.keyBindBack.isKeyDown()) {
            --this.moveForward;
        }

        if (this.gameSettings.keyBindLeft.isKeyDown()) {
            ++this.moveStrafe;
        }

        if (this.gameSettings.keyBindRight.isKeyDown()) {
            --this.moveStrafe;
        }

        this.jump = this.gameSettings.keyBindJump.isKeyDown();
        this.sneak = this.gameSettings.keyBindSneak.isKeyDown();

        final MoveInputEvent moveInputEvent = new MoveInputEvent(moveForward, moveStrafe, jump, sneak, 0.3D);
        Dog.getInstance().getEventBus().post(moveInputEvent);

        final double sneakMultiplier = moveInputEvent.getSneakMultiplier();
        this.moveForward = moveInputEvent.getForward();
        this.moveStrafe = moveInputEvent.getStrafe();
        this.jump = moveInputEvent.isJumping();
        this.sneak = moveInputEvent.isSneaking();

        if (this.sneak) {
            this.moveStrafe = (float) ((double) this.moveStrafe * sneakMultiplier);
            this.moveForward = (float) ((double) this.moveForward * sneakMultiplier);
        }
    }
}
