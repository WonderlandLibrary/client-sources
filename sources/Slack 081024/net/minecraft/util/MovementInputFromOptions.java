package net.minecraft.util;

import cc.slack.events.impl.input.onMoveInputEvent;
import net.minecraft.client.settings.GameSettings;

public class MovementInputFromOptions extends MovementInput
{
    private final GameSettings gameSettings;

    public MovementInputFromOptions(GameSettings gameSettingsIn)
    {
        this.gameSettings = gameSettingsIn;
    }

    public void updatePlayerMoveState()
    {
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

        onMoveInputEvent onMoveInputEvent = new onMoveInputEvent(moveForward, moveStrafe, jump, sneak, 0.3D);
        onMoveInputEvent.call();


        final double sneakMultiplier = onMoveInputEvent.sneakSlowDownMultiplier;
        this.moveForward = onMoveInputEvent.forward;
        this.moveStrafe = onMoveInputEvent.strafe;
        this.jump = onMoveInputEvent.jump;
        this.sneak = onMoveInputEvent.sneak;

        if (this.sneak) {
            this.moveStrafe = (float) ((double) this.moveStrafe * sneakMultiplier);
            this.moveForward = (float) ((double) this.moveForward * sneakMultiplier);
        }
    }
}
