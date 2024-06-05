/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package drunkclient.beta.IMPL.Module.impl.move;

import drunkclient.beta.API.EventHandler;
import drunkclient.beta.API.events.world.EventPreUpdate;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class InvMove
extends Module {
    public InvMove() {
        super("InvMovement", new String[0], Type.MOVE, "Move with your inventory open.");
    }

    @EventHandler
    public void onUpdate(EventPreUpdate event) {
        KeyBinding[] key;
        if (InvMove.mc.currentScreen == null) return;
        if (InvMove.mc.currentScreen instanceof GuiChat) return;
        KeyBinding[] keyBindingArray = new KeyBinding[6];
        keyBindingArray[0] = InvMove.mc.gameSettings.keyBindForward;
        keyBindingArray[1] = InvMove.mc.gameSettings.keyBindBack;
        keyBindingArray[2] = InvMove.mc.gameSettings.keyBindLeft;
        keyBindingArray[3] = InvMove.mc.gameSettings.keyBindRight;
        keyBindingArray[4] = InvMove.mc.gameSettings.keyBindSprint;
        keyBindingArray[5] = InvMove.mc.gameSettings.keyBindJump;
        KeyBinding[] array = key = keyBindingArray;
        int length = key.length;
        int i = 0;
        while (i < length) {
            KeyBinding b = array[i];
            KeyBinding.setKeyBindState(b.getKeyCode(), Keyboard.isKeyDown((int)b.getKeyCode()));
            ++i;
        }
    }
}

