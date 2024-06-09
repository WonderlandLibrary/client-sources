/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import winter.console.Console;
import winter.event.EventListener;
import winter.event.events.UpdateEvent;
import winter.module.Module;

public class Inventory
extends Module {
    public Inventory() {
        super("Inventory", Module.Category.Movement, -3239783);
        this.setBind(23);
    }

    @EventListener
    public void onUpdate(UpdateEvent event) {
        if (this.mc.currentScreen != null && !(this.mc.currentScreen instanceof GuiChat) && !(this.mc.currentScreen instanceof Console)) {
            if (Keyboard.isKeyDown(200)) {
                EntityPlayerSP thePlayer = this.mc.thePlayer;
                thePlayer.rotationPitch -= 2.0f;
            }
            if (Keyboard.isKeyDown(208)) {
                EntityPlayerSP thePlayer2 = this.mc.thePlayer;
                thePlayer2.rotationPitch += 2.0f;
            }
            if (Keyboard.isKeyDown(203)) {
                EntityPlayerSP thePlayer3 = this.mc.thePlayer;
                thePlayer3.rotationYaw -= 3.0f;
            }
            if (Keyboard.isKeyDown(205)) {
                EntityPlayerSP thePlayer4 = this.mc.thePlayer;
                thePlayer4.rotationYaw += 3.0f;
            }
            this.mc.gameSettings.keyBindForward.pressed = Keyboard.isKeyDown(this.mc.gameSettings.keyBindForward.getKeyCode());
            this.mc.gameSettings.keyBindBack.pressed = Keyboard.isKeyDown(this.mc.gameSettings.keyBindBack.getKeyCode());
            this.mc.gameSettings.keyBindLeft.pressed = Keyboard.isKeyDown(this.mc.gameSettings.keyBindLeft.getKeyCode());
            this.mc.gameSettings.keyBindRight.pressed = Keyboard.isKeyDown(this.mc.gameSettings.keyBindRight.getKeyCode());
            this.mc.gameSettings.keyBindJump.pressed = Keyboard.isKeyDown(this.mc.gameSettings.keyBindJump.getKeyCode());
        }
    }
}

