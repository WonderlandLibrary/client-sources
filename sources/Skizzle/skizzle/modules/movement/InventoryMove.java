/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package skizzle.modules.movement;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import skizzle.events.Event;
import skizzle.modules.Module;

public class InventoryMove
extends Module {
    public InventoryMove() {
        super(Qprot0.0("\ud128\u71c5\uea63\ua7c9\uf8d9\u54ad\u8c2a"), 0, Module.Category.MOVEMENT);
        InventoryMove Nigga;
    }

    @Override
    public void onEvent(Event Nigga) {
        InventoryMove Nigga2;
        if (Nigga.isPre() && Nigga2.mc.currentScreen != null && !(Nigga2.mc.currentScreen instanceof GuiChat)) {
            KeyBinding Nigga3 = Nigga2.mc.gameSettings.keyBindForward;
            KeyBinding.setKeyBindState(Nigga3.getKeyCode(), Keyboard.isKeyDown((int)Nigga3.getKeyCode()));
            KeyBinding Nigga4 = Nigga2.mc.gameSettings.keyBindLeft;
            KeyBinding.setKeyBindState(Nigga4.getKeyCode(), Keyboard.isKeyDown((int)Nigga4.getKeyCode()));
            KeyBinding Nigga5 = Nigga2.mc.gameSettings.keyBindRight;
            KeyBinding.setKeyBindState(Nigga5.getKeyCode(), Keyboard.isKeyDown((int)Nigga5.getKeyCode()));
            KeyBinding Nigga6 = Nigga2.mc.gameSettings.keyBindBack;
            KeyBinding.setKeyBindState(Nigga6.getKeyCode(), Keyboard.isKeyDown((int)Nigga6.getKeyCode()));
            KeyBinding Nigga7 = Nigga2.mc.gameSettings.keyBindJump;
            KeyBinding.setKeyBindState(Nigga7.getKeyCode(), Keyboard.isKeyDown((int)Nigga7.getKeyCode()));
            KeyBinding Nigga8 = Nigga2.mc.gameSettings.keyBindSprint;
            KeyBinding.setKeyBindState(Nigga8.getKeyCode(), Keyboard.isKeyDown((int)Nigga8.getKeyCode()));
        }
    }

    public static {
        throw throwable;
    }
}

