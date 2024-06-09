/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package net.minecraft.util;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MovementInput;
import org.lwjgl.input.Keyboard;
import pw.vertexcode.nemphis.Nemphis;
import pw.vertexcode.nemphis.module.ModuleManager;

public class MovementInputFromOptions
extends MovementInput {
    private final GameSettings gameSettings;
    private static final String __OBFID = "CL_00000937";

    public MovementInputFromOptions(GameSettings p_i1237_1_) {
        this.gameSettings = p_i1237_1_;
    }

    @Override
    public void updatePlayerMoveState() {
        this.moveStrafe = 0.0f;
        this.moveForward = 0.0f;
        if (this.gameSettings.keyBindForward.getIsKeyPressed() || this.getKeyIsPressed(this.gameSettings.keyBindForward.getKeyCode())) {
            this.moveForward += 1.0f;
        }
        if (this.gameSettings.keyBindBack.getIsKeyPressed() || this.getKeyIsPressed(this.gameSettings.keyBindBack.getKeyCode())) {
            this.moveForward -= 1.0f;
        }
        if (this.gameSettings.keyBindLeft.getIsKeyPressed() || this.getKeyIsPressed(this.gameSettings.keyBindLeft.getKeyCode())) {
            this.moveStrafe += 1.0f;
        }
        if (this.gameSettings.keyBindRight.getIsKeyPressed() || this.getKeyIsPressed(this.gameSettings.keyBindRight.getKeyCode())) {
            this.moveStrafe -= 1.0f;
        }
        this.jump = this.gameSettings.keyBindJump.getIsKeyPressed() || this.getKeyIsPressed(this.gameSettings.keyBindJump.getKeyCode());
        boolean bl = this.sneak = this.gameSettings.keyBindSneak.getIsKeyPressed() || this.getKeyIsPressed(this.gameSettings.keyBindSneak.getKeyCode());
        if (this.sneak) {
            this.moveStrafe = (float)((double)this.moveStrafe * 0.3);
            this.moveForward = (float)((double)this.moveForward * 0.3);
        }
    }

    public boolean getKeyIsPressed(int key) {
        if (Keyboard.isKeyDown((int)key) && Nemphis.instance.modulemanager.getModuleIsEnabled("InventoryMove")) {
            return true;
        }
        return false;
    }
}

