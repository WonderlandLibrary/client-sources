/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.modules.impl.Movement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import us.amerikan.modules.Category;
import us.amerikan.modules.Module;

public class InvWalk
extends Module {
    private int bindJumpKeyCode;
    private int bindSneakKeyCode;
    private int bindSprintKeyCode;
    private int bindForwardKeyCode;
    private int bindBackKeyCode;
    private int bindLeftKeyCode;
    private int bindRightKeyCode;
    private int rotationSpeed = 5;

    public InvWalk() {
        super("InvWalk", "InvWalk", 0, Category.MOVEMENT);
    }

    @Override
    public void onUpdate() {
        this.bindJumpKeyCode = InvWalk.mc.gameSettings.keyBindJump.getKeyCode();
        this.bindSneakKeyCode = InvWalk.mc.gameSettings.keyBindSneak.getKeyCode();
        this.bindSprintKeyCode = InvWalk.mc.gameSettings.keyBindSprint.getKeyCode();
        this.bindForwardKeyCode = InvWalk.mc.gameSettings.keyBindForward.getKeyCode();
        this.bindBackKeyCode = InvWalk.mc.gameSettings.keyBindBack.getKeyCode();
        this.bindLeftKeyCode = InvWalk.mc.gameSettings.keyBindLeft.getKeyCode();
        this.bindRightKeyCode = InvWalk.mc.gameSettings.keyBindRight.getKeyCode();
    }

    public void runUpdate() {
        if (InvWalk.mc.currentScreen != null && !(InvWalk.mc.currentScreen instanceof GuiChat)) {
            KeyBinding.setKeyBindState(this.bindJumpKeyCode, Keyboard.isKeyDown(this.bindJumpKeyCode));
            KeyBinding.setKeyBindState(this.bindSneakKeyCode, Keyboard.isKeyDown(this.bindSneakKeyCode));
            KeyBinding.setKeyBindState(this.bindSprintKeyCode, Keyboard.isKeyDown(this.bindSprintKeyCode));
            KeyBinding.setKeyBindState(this.bindForwardKeyCode, Keyboard.isKeyDown(this.bindForwardKeyCode));
            KeyBinding.setKeyBindState(this.bindBackKeyCode, Keyboard.isKeyDown(this.bindBackKeyCode));
            KeyBinding.setKeyBindState(this.bindLeftKeyCode, Keyboard.isKeyDown(this.bindLeftKeyCode));
            KeyBinding.setKeyBindState(this.bindRightKeyCode, Keyboard.isKeyDown(this.bindRightKeyCode));
            if (Keyboard.isKeyDown(200)) {
                if (Minecraft.thePlayer.rotationPitch > -90.0f) {
                    Minecraft.thePlayer.rotationPitch -= (float)this.rotationSpeed;
                }
            }
            if (Keyboard.isKeyDown(208)) {
                if (Minecraft.thePlayer.rotationPitch < 90.0f) {
                    Minecraft.thePlayer.rotationPitch += (float)this.rotationSpeed;
                }
            }
            if (Keyboard.isKeyDown(203)) {
                Minecraft.thePlayer.rotationYaw -= (float)this.rotationSpeed;
            }
            if (Keyboard.isKeyDown(205)) {
                Minecraft.thePlayer.rotationYaw += (float)this.rotationSpeed;
            }
        }
    }
}

