// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.module.modules;

import org.lwjgl.input.Keyboard;

import me.kaktuswasser.client.event.Event;
import me.kaktuswasser.client.event.events.EatMyAssYouFuckingDecompiler;
import me.kaktuswasser.client.event.events.PreMotion;
import me.kaktuswasser.client.module.Module;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.gui.GuiChat;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class InventoryMove extends Module
{
    public InventoryMove() {
        super("InventoryMove", Category.MOVEMENT);
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof PreMotion && InventoryMove.mc.currentScreen != null && !(InventoryMove.mc.currentScreen instanceof GuiChat)) {
            final KeyBinding[] moveKeys = { InventoryMove.mc.gameSettings.keyBindForward, InventoryMove.mc.gameSettings.keyBindBack, InventoryMove.mc.gameSettings.keyBindLeft, InventoryMove.mc.gameSettings.keyBindRight, InventoryMove.mc.gameSettings.keyBindJump };
            KeyBinding[] array;
            for (int length = (array = moveKeys).length, i = 0; i < length; ++i) {
                final KeyBinding bind = array[i];
                KeyBinding.setKeyBindState(bind.getKeyCode(), Keyboard.isKeyDown(bind.getKeyCode()));
            }
        }
    }
}
