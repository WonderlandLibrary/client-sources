/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package net.minecraft.util;

import cc.diablo.manager.module.ModuleManager;
import cc.diablo.module.impl.player.InventoryMove;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MovementInput;
import org.lwjgl.input.Keyboard;

public class MovementInputFromOptions
extends MovementInput {
    private final GameSettings gameSettings;

    public MovementInputFromOptions(GameSettings gameSettingsIn) {
        this.gameSettings = gameSettingsIn;
    }

    @Override
    public void updatePlayerMoveState() {
        InventoryMove inventorymove = (InventoryMove)ModuleManager.getModule(InventoryMove.class);
        if (inventorymove.isToggled() && !(Minecraft.getMinecraft().currentScreen instanceof GuiChat)) {
            moveStrafe = 0.0f;
            moveForward = 0.0f;
            if (Keyboard.isKeyDown((int)this.gameSettings.keyBindForward.getKeyCode())) {
                moveForward -= 1.0f;
            }
            if (Keyboard.isKeyDown((int)this.gameSettings.keyBindBack.getKeyCode())) {
                moveForward -= 1.0f;
            }
            if (Keyboard.isKeyDown((int)this.gameSettings.keyBindLeft.getKeyCode())) {
                moveStrafe += 1.0f;
            }
            if (Keyboard.isKeyDown((int)this.gameSettings.keyBindRight.getKeyCode())) {
                moveStrafe -= 1.0f;
            }
            jump = Keyboard.isKeyDown((int)this.gameSettings.keyBindJump.getKeyCode());
            sneak = this.gameSettings.keyBindSneak.pressed;
            if (sneak) {
                moveStrafe = (float)((double)moveStrafe * 0.3);
                moveForward = (float)((double)moveForward * 0.3);
            }
        } else {
            moveStrafe = 0.0f;
        }
        moveForward = 0.0f;
        if (this.gameSettings.keyBindForward.isKeyDown()) {
            moveForward += 1.0f;
        }
        if (this.gameSettings.keyBindBack.isKeyDown()) {
            moveForward -= 1.0f;
        }
        if (this.gameSettings.keyBindLeft.isKeyDown()) {
            moveStrafe += 1.0f;
        }
        if (this.gameSettings.keyBindRight.isKeyDown()) {
            moveStrafe -= 1.0f;
        }
        jump = this.gameSettings.keyBindJump.isKeyDown();
        sneak = this.gameSettings.keyBindSneak.isKeyDown();
        if (sneak) {
            moveStrafe = (float)((double)moveStrafe * 0.3);
            moveForward = (float)((double)moveForward * 0.3);
        }
    }
}

