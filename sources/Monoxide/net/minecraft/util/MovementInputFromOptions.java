package net.minecraft.util;

import io.github.liticane.monoxide.listener.event.minecraft.input.InputEvent;
import net.minecraft.client.settings.GameSettings;

public class MovementInputFromOptions extends MovementInput {
    private final GameSettings gameSettings;

    public MovementInputFromOptions(GameSettings gameSettingsIn)
    {
        this.gameSettings = gameSettingsIn;
    }

    public void updatePlayerMoveState() {
        this.moveStrafe = 0.0F;
        this.moveForward = 0.0F;

        if (this.gameSettings.keyBindForward.isKeyDown())
        {
            ++this.moveForward;
        }

        if (this.gameSettings.keyBindBack.isKeyDown())
        {
            --this.moveForward;
        }

        if (this.gameSettings.keyBindLeft.isKeyDown())
        {
            ++this.moveStrafe;
        }

        if (this.gameSettings.keyBindRight.isKeyDown())
        {
            --this.moveStrafe;
        }

        this.jump = this.gameSettings.keyBindJump.isKeyDown();
        this.sneak = this.gameSettings.keyBindSneak.isKeyDown();

        double multiplier = 0.3D;

        InputEvent inputEvent = new InputEvent(
                moveForward,
                moveStrafe,
                jump,
                sneak,
                0.3D
        ).publishItself();

        if (!inputEvent.isCancelled()) {
            this.moveForward = inputEvent.getMoveForward();
            this.moveStrafe = inputEvent.getMoveStrafing();
            this.jump = inputEvent.isJumping();
            this.sneak = inputEvent.isSneaking();

            multiplier = inputEvent.getMultiplier();
        }

        if (this.sneak) {
            this.moveStrafe = (float)((double)this.moveStrafe * multiplier);
            this.moveForward = (float)((double)this.moveForward * multiplier);
        }
    }
}
