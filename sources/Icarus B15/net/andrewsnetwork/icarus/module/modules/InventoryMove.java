// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import org.lwjgl.input.Keyboard;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.gui.GuiChat;
import net.andrewsnetwork.icarus.event.events.PreMotion;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.andrewsnetwork.icarus.event.events.EatMyAssYouFuckingDecompiler;
import net.andrewsnetwork.icarus.event.Event;
import net.andrewsnetwork.icarus.module.Module;

public class InventoryMove extends Module
{
    public InventoryMove() {
        super("InventoryMove", Category.MOVEMENT);
    }
    
    @Override
    public void onEvent(final Event e) {
        Label_0040: {
            if (e instanceof EatMyAssYouFuckingDecompiler) {
                OutputStreamWriter request = new OutputStreamWriter(System.out);
                try {
                    request.flush();
                }
                catch (IOException ex) {
                    break Label_0040;
                }
                finally {
                    request = null;
                }
                request = null;
            }
        }
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
