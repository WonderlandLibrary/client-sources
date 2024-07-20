/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MovementInput;
import ru.govno.client.event.events.EventSlowSneak;

public class MovementInputFromOptions
extends MovementInput {
    private final GameSettings gameSettings;

    public MovementInputFromOptions(GameSettings gameSettingsIn) {
        this.gameSettings = gameSettingsIn;
    }

    @Override
    public void updatePlayerMoveState() {
        moveStrafe = 0.0f;
        field_192832_b = 0.0f;
        if (this.gameSettings.keyBindForward.isKeyDown()) {
            field_192832_b += 1.0f;
            this.forwardKeyDown = true;
        } else {
            this.forwardKeyDown = false;
        }
        if (this.gameSettings.keyBindBack.isKeyDown()) {
            field_192832_b -= 1.0f;
            this.backKeyDown = true;
        } else {
            this.backKeyDown = false;
        }
        if (GameSettings.keyBindLeft.isKeyDown()) {
            moveStrafe += 1.0f;
            this.leftKeyDown = true;
        } else {
            this.leftKeyDown = false;
        }
        if (GameSettings.keyBindRight.isKeyDown()) {
            moveStrafe -= 1.0f;
            this.rightKeyDown = true;
        } else {
            this.rightKeyDown = false;
        }
        this.jump = this.gameSettings.keyBindJump.isKeyDown();
        this.sneak = this.gameSettings.keyBindSneak.isKeyDown() || Minecraft.player.hasNewVersionMoves && Minecraft.player.isNewSneak;
        EventSlowSneak sneakEvent = new EventSlowSneak(0.3);
        sneakEvent.call();
        if (this.sneak && !sneakEvent.isCancelled()) {
            moveStrafe = (float)((double)moveStrafe * sneakEvent.getSlowFactor());
            field_192832_b = (float)((double)field_192832_b * sneakEvent.getSlowFactor());
        }
    }
}

