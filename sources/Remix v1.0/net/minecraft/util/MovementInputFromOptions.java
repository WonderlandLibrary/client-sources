package net.minecraft.util;

import net.minecraft.client.settings.*;
import me.satisfactory.base.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import org.lwjgl.input.*;
import net.minecraft.client.entity.*;

public class MovementInputFromOptions extends MovementInput
{
    private final GameSettings gameSettings;
    
    public MovementInputFromOptions(final GameSettings p_i1237_1_) {
        this.gameSettings = p_i1237_1_;
    }
    
    @Override
    public void updatePlayerMoveState() {
        if (Base.INSTANCE.getModuleManager().getModByName("InvMove").isEnabled() && !(Minecraft.getMinecraft().currentScreen instanceof GuiChat)) {
            MovementInputFromOptions.moveStrafe = 0.0f;
            MovementInputFromOptions.moveForward = 0.0f;
            if (Keyboard.isKeyDown(this.gameSettings.keyBindForward.getKeyCode())) {
                ++MovementInputFromOptions.moveForward;
            }
            if (Keyboard.isKeyDown(this.gameSettings.keyBindBack.getKeyCode())) {
                --MovementInputFromOptions.moveForward;
            }
            if (Keyboard.isKeyDown(this.gameSettings.keyBindLeft.getKeyCode())) {
                ++MovementInputFromOptions.moveStrafe;
            }
            if (Keyboard.isKeyDown(this.gameSettings.keyBindRight.getKeyCode())) {
                --MovementInputFromOptions.moveStrafe;
            }
            this.jump = Keyboard.isKeyDown(this.gameSettings.keyBindJump.getKeyCode());
            this.sneak = this.gameSettings.keyBindSneak.pressed;
            if (this.sneak) {
                MovementInputFromOptions.moveStrafe *= (float)0.3;
                MovementInputFromOptions.moveForward *= (float)0.3;
            }
            final Minecraft mc2 = Minecraft.getMinecraft();
            if (mc2.currentScreen != null) {
                if (Keyboard.isKeyDown(200) && mc2.thePlayer.rotationPitch > -90.0f) {
                    final EntityPlayerSP thePlayer = mc2.thePlayer;
                    thePlayer.rotationPitch -= 5.0f;
                }
                else if (Keyboard.isKeyDown(208) && mc2.thePlayer.rotationPitch < 90.0f) {
                    final EntityPlayerSP thePlayer2 = mc2.thePlayer;
                    thePlayer2.rotationPitch += 5.0f;
                }
                if (Keyboard.isKeyDown(203)) {
                    final EntityPlayerSP thePlayer3 = mc2.thePlayer;
                    thePlayer3.rotationYaw -= 5.0f;
                }
                else if (Keyboard.isKeyDown(205)) {
                    final EntityPlayerSP thePlayer4 = mc2.thePlayer;
                    thePlayer4.rotationYaw += 5.0f;
                }
            }
        }
        else {
            MovementInputFromOptions.moveStrafe = 0.0f;
            MovementInputFromOptions.moveForward = 0.0f;
            if (this.gameSettings.keyBindForward.getIsKeyPressed()) {
                ++MovementInputFromOptions.moveForward;
            }
            if (this.gameSettings.keyBindBack.getIsKeyPressed()) {
                --MovementInputFromOptions.moveForward;
            }
            if (this.gameSettings.keyBindLeft.getIsKeyPressed()) {
                ++MovementInputFromOptions.moveStrafe;
            }
            if (this.gameSettings.keyBindRight.getIsKeyPressed()) {
                --MovementInputFromOptions.moveStrafe;
            }
            this.jump = this.gameSettings.keyBindJump.getIsKeyPressed();
            this.sneak = this.gameSettings.keyBindSneak.getIsKeyPressed();
            if (this.sneak) {
                MovementInputFromOptions.moveStrafe *= (float)0.3;
                MovementInputFromOptions.moveForward *= (float)0.3;
            }
        }
    }
}
