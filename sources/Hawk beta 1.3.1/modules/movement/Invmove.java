package eze.modules.movement;

import eze.modules.*;
import eze.events.*;
import eze.events.listeners.*;
import org.lwjgl.input.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.settings.*;
import net.minecraft.client.entity.*;

public class Invmove extends Module
{
    public Invmove() {
        super("Invmove", 0, Category.MOVEMENT);
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventUpdate) {
            if (this.mc.currentScreen instanceof GuiScreen) {
                if (Keyboard.isKeyDown(205) && !(this.mc.currentScreen instanceof GuiChat)) {
                    final EntityPlayerSP thePlayer = this.mc.thePlayer;
                    thePlayer.rotationYaw += 8.0f;
                }
                if (Keyboard.isKeyDown(203) && !(this.mc.currentScreen instanceof GuiChat)) {
                    final EntityPlayerSP thePlayer2 = this.mc.thePlayer;
                    thePlayer2.rotationYaw -= 8.0f;
                }
                if (Keyboard.isKeyDown(200) && !(this.mc.currentScreen instanceof GuiChat)) {
                    final EntityPlayerSP thePlayer3 = this.mc.thePlayer;
                    thePlayer3.rotationPitch -= 8.0f;
                }
                if (Keyboard.isKeyDown(208) && !(this.mc.currentScreen instanceof GuiChat)) {
                    final EntityPlayerSP thePlayer4 = this.mc.thePlayer;
                    thePlayer4.rotationPitch += 8.0f;
                }
            }
            final KeyBinding[] moveKeys = { this.mc.gameSettings.keyBindRight, this.mc.gameSettings.keyBindLeft, this.mc.gameSettings.keyBindBack, this.mc.gameSettings.keyBindForward, this.mc.gameSettings.keyBindJump, this.mc.gameSettings.keyBindSprint };
            if (this.mc.currentScreen instanceof GuiScreen && !(this.mc.currentScreen instanceof GuiChat)) {
                KeyBinding[] array;
                for (int length = (array = moveKeys).length, i = 0; i < length; ++i) {
                    final KeyBinding key = array[i];
                    key.pressed = Keyboard.isKeyDown(key.getKeyCode());
                }
            }
            else {
                KeyBinding[] array2;
                for (int length2 = (array2 = moveKeys).length, j = 0; j < length2; ++j) {
                    final KeyBinding bind = array2[j];
                    if (!Keyboard.isKeyDown(bind.getKeyCode())) {
                        KeyBinding.setKeyBindState(bind.getKeyCode(), false);
                    }
                }
            }
        }
    }
}
