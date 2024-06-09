/*
 * Decompiled with CFR 0.143.
 */
package net.minecraft.util;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.player.InventoryMove;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MovementInput;
import org.lwjgl.input.Keyboard;

public class MovementInputFromOptions
extends MovementInput {
    private final GameSettings gameSettings;
    private static final String __OBFID = "CL_00000937";

    public MovementInputFromOptions(GameSettings p_i1237_1_) {
        this.gameSettings = p_i1237_1_;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void updatePlayerMoveState() {
        if (new InventoryMove().getInstance().isEnabled()) {
            ClientUtils.mc();
            if (!(Minecraft.currentScreen instanceof GuiChat)) {
                moveStrafe = 0.0f;
                moveForward = 0.0f;
                if (Keyboard.isKeyDown((int)this.gameSettings.keyBindForward.getKeyCode())) {
                    moveForward += 1.0f;
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
                this.jump = Keyboard.isKeyDown((int)this.gameSettings.keyBindJump.getKeyCode());
                this.sneak = this.gameSettings.keyBindSneak.isKeyDown();
                if (!this.sneak) return;
                moveStrafe = (float)((double)moveStrafe * 0.3);
                moveForward = (float)((double)moveForward * 0.3);
                return;
            }
        }
        moveStrafe = 0.0f;
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
        this.jump = this.gameSettings.keyBindJump.isKeyDown();
        this.sneak = this.gameSettings.keyBindSneak.isKeyDown();
        if (!this.sneak) return;
        moveStrafe = (float)((double)moveStrafe * 0.3);
        moveForward = (float)((double)moveForward * 0.3);
    }
}

