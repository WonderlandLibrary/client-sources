/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package net.minecraft.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MovementInput;
import org.lwjgl.input.Keyboard;
import digital.rbq.core.Autumn;
import digital.rbq.module.impl.player.InventoryWalkMod;

public class MovementInputFromOptions
extends MovementInput {
    private static final Minecraft mc = Minecraft.getMinecraft();
    private final GameSettings gameSettings;

    public MovementInputFromOptions(GameSettings gameSettingsIn) {
        this.gameSettings = gameSettingsIn;
    }

    @Override
    public void updatePlayerMoveState() {
        boolean safeGui;
        InventoryWalkMod inventoryWalk = Autumn.MANAGER_REGISTRY.moduleManager.getModuleOrNull(InventoryWalkMod.class);
        boolean bl = safeGui = !(MovementInputFromOptions.mc.currentScreen instanceof GuiChat);
        if (inventoryWalk.isEnabled() && safeGui) {
            this.moveStrafe = 0.0f;
            this.moveForward = 0.0f;
            if (Keyboard.isKeyDown((int)this.gameSettings.keyBindForward.getKeyCode())) {
                this.moveForward += 1.0f;
            }
            if (Keyboard.isKeyDown((int)this.gameSettings.keyBindBack.getKeyCode())) {
                this.moveForward -= 1.0f;
            }
            if (Keyboard.isKeyDown((int)this.gameSettings.keyBindLeft.getKeyCode())) {
                this.moveStrafe += 1.0f;
            }
            if (Keyboard.isKeyDown((int)this.gameSettings.keyBindRight.getKeyCode())) {
                this.moveStrafe -= 1.0f;
            }
            this.jump = Keyboard.isKeyDown((int)this.gameSettings.keyBindJump.getKeyCode());
            this.sneak = Keyboard.isKeyDown((int)this.gameSettings.keyBindSneak.getKeyCode());
            if (this.sneak) {
                this.moveStrafe = (float)((double)this.moveStrafe * 0.3);
                this.moveForward = (float)((double)this.moveForward * 0.3);
            }
        } else {
            this.moveStrafe = 0.0f;
            this.moveForward = 0.0f;
            if (this.gameSettings.keyBindForward.isKeyDown()) {
                this.moveForward += 1.0f;
            }
            if (this.gameSettings.keyBindBack.isKeyDown()) {
                this.moveForward -= 1.0f;
            }
            if (this.gameSettings.keyBindLeft.isKeyDown()) {
                this.moveStrafe += 1.0f;
            }
            if (this.gameSettings.keyBindRight.isKeyDown()) {
                this.moveStrafe -= 1.0f;
            }
            this.jump = this.gameSettings.keyBindJump.isKeyDown();
            this.sneak = this.gameSettings.keyBindSneak.isKeyDown();
            if (this.sneak) {
                this.moveStrafe = (float)((double)this.moveStrafe * 0.3);
                this.moveForward = (float)((double)this.moveForward * 0.3);
            }
        }
    }
}

