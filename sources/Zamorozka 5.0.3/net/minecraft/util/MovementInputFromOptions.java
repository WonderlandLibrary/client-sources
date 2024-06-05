package net.minecraft.util;

import net.minecraft.client.settings.GameSettings;

public class MovementInputFromOptions extends MovementInput {
    public final GameSettings gameSettings;

    public MovementInputFromOptions(GameSettings gameSettingsIn) {
        this.gameSettings = gameSettingsIn;
    }

    public void updatePlayerMoveState() {
        moveStrafe = 0.0F;
        moveForward = 0.0F;

        if (this.gameSettings.keyBindForward.isKeyDown()) {
            ++moveForward;
            forwardKeyDown = true;
        } else {
            forwardKeyDown = false;
        }

        if (this.gameSettings.keyBindBack.isKeyDown()) {
            --moveForward;
            backKeyDown = true;
        } else {
            backKeyDown = false;
        }

        if (this.gameSettings.keyBindLeft.isKeyDown()) {
            ++moveStrafe;
            leftKeyDown = true;
        } else {
            leftKeyDown = false;
        }

        if (this.gameSettings.keyBindRight.isKeyDown()) {
            --moveStrafe;
            rightKeyDown = true;
        } else {
            rightKeyDown = false;
        }

        jump = this.gameSettings.keyBindJump.isKeyDown();
        sneak = this.gameSettings.keyBindSneak.isKeyDown();

        if (sneak) {
            moveStrafe = (float) ((double) moveStrafe * 0.3D);
            moveForward = (float) ((double) moveForward * 0.3D);
        }
    }
}
