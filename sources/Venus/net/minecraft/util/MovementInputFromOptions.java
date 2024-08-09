/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import mpp.venusfr.events.EventInput;
import mpp.venusfr.venusfr;
import net.minecraft.client.GameSettings;
import net.minecraft.util.MovementInput;

public class MovementInputFromOptions
extends MovementInput {
    private final GameSettings gameSettings;
    final EventInput moveInputEvent = new EventInput(0.0f, 0.0f, false, false, 0.0);

    public MovementInputFromOptions(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
    }

    @Override
    public void tickMovement(boolean bl) {
        this.forwardKeyDown = this.gameSettings.keyBindForward.isKeyDown();
        this.backKeyDown = this.gameSettings.keyBindBack.isKeyDown();
        this.leftKeyDown = this.gameSettings.keyBindLeft.isKeyDown();
        this.rightKeyDown = this.gameSettings.keyBindRight.isKeyDown();
        this.moveForward = this.forwardKeyDown == this.backKeyDown ? 0.0f : (this.forwardKeyDown ? 1.0f : -1.0f);
        moveStrafe = this.leftKeyDown == this.rightKeyDown ? 0.0f : (this.leftKeyDown ? 1.0f : -1.0f);
        this.jump = this.gameSettings.keyBindJump.isKeyDown();
        this.sneaking = this.gameSettings.keyBindSneak.isKeyDown();
        EventInput eventInput = new EventInput(this.moveForward, moveStrafe, this.jump, this.sneaking, 0.3);
        venusfr.getInstance().getEventBus().post(eventInput);
        double d = eventInput.getSneakSlowDownMultiplier();
        this.moveForward = eventInput.getForward();
        moveStrafe = eventInput.getStrafe();
        this.jump = eventInput.isJump();
        this.sneaking = eventInput.isSneak();
        if (bl) {
            moveStrafe = (float)((double)moveStrafe * d);
            this.moveForward = (float)((double)this.moveForward * d);
        }
    }
}

