// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.util;

import org.lwjgl.input.Keyboard;
import me.chrest.utils.ClientUtils;
import net.minecraft.client.gui.GuiChat;
import me.chrest.client.module.modules.misc.ScreenWalk;
import net.minecraft.client.settings.GameSettings;

public class MovementInputFromOptions extends MovementInput
{
    private final GameSettings gameSettings;
    private static final String __OBFID = "CL_00000937";
    
    public MovementInputFromOptions(final GameSettings p_i1237_1_) {
        this.gameSettings = p_i1237_1_;
    }
    
    @Override
    public void updatePlayerMoveState() {
        if (new ScreenWalk().getInstance().isEnabled() && !(ClientUtils.mc().currentScreen instanceof GuiChat)) {
            this.moveStrafe = 0.0f;
            this.moveForward = 0.0f;
            if (Keyboard.isKeyDown(this.gameSettings.keyBindForward.getKeyCode())) {
                ++this.moveForward;
            }
            if (Keyboard.isKeyDown(this.gameSettings.keyBindBack.getKeyCode())) {
                --this.moveForward;
            }
            if (Keyboard.isKeyDown(this.gameSettings.keyBindLeft.getKeyCode())) {
                ++this.moveStrafe;
            }
            if (Keyboard.isKeyDown(this.gameSettings.keyBindRight.getKeyCode())) {
                --this.moveStrafe;
            }
            this.jump = Keyboard.isKeyDown(this.gameSettings.keyBindJump.getKeyCode());
            this.sneak = this.gameSettings.keyBindSneak.isKeyDown();
            if (this.sneak) {
                this.moveStrafe *= 0.3;
                this.moveForward *= 0.3;
            }
        }
        else {
            this.moveStrafe = 0.0f;
            this.moveForward = 0.0f;
            if (this.gameSettings.keyBindForward.isKeyDown()) {
                ++this.moveForward;
            }
            if (this.gameSettings.keyBindBack.isKeyDown()) {
                --this.moveForward;
            }
            if (this.gameSettings.keyBindLeft.isKeyDown()) {
                ++this.moveStrafe;
            }
            if (this.gameSettings.keyBindRight.isKeyDown()) {
                --this.moveStrafe;
            }
            this.jump = this.gameSettings.keyBindJump.isKeyDown();
            this.sneak = this.gameSettings.keyBindSneak.isKeyDown();
            if (this.sneak) {
                this.moveStrafe *= 0.3;
                this.moveForward *= 0.3;
            }
        }
    }
}
