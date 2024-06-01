package net.minecraft.util;

import best.actinium.Actinium;
import best.actinium.event.impl.move.MoveInputEvent;
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

        MoveInputEvent moveInput = new MoveInputEvent(
                moveForward,
                moveStrafe,
                jump,
                sneak,
                0.3D
        );

        Actinium.INSTANCE.getEventManager().publish(moveInput);

        this.moveForward = moveInput.getForward();
        this.moveStrafe = moveInput.getStrafe();
        this.jump = moveInput.isJump();
        this.sneak = moveInput.isSneak();

        if (this.sneak) {
            this.moveStrafe = (float)((double)this.moveStrafe * moveInput.getSlowdown());
            this.moveForward = (float)((double)this.moveForward * moveInput.getSlowdown());
        }
    }
}
