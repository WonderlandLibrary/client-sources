// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod;

import org.lwjgl.input.Keyboard;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.KeyBinding;
import com.klintos.twelve.mod.events.EventPreUpdate;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import com.klintos.twelve.mod.events.EventPacketSend;

public class InvMove extends Mod
{
    public InvMove() {
        super("InvMove", 0, ModCategory.MISC);
    }
    
    @EventTarget
    public void onPacketSend(final EventPacketSend event) {
        if (event.getPacket() instanceof C0DPacketCloseWindow) {
            event.setCancelled(true);
        }
    }
    
    @EventTarget
    public void onPreUpdate(final EventPreUpdate event) {
        final KeyBinding[] keys = { InvMove.mc.gameSettings.keyBindForward, InvMove.mc.gameSettings.keyBindBack, InvMove.mc.gameSettings.keyBindLeft, InvMove.mc.gameSettings.keyBindRight, InvMove.mc.gameSettings.keyBindJump };
        if (InvMove.mc.currentScreen instanceof GuiContainer) {
            KeyBinding[] array;
            for (int length = (array = keys).length, i = 0; i < length; ++i) {
                final KeyBinding bind = array[i];
                bind.pressed = Keyboard.isKeyDown(bind.getKeyCode());
            }
        }
    }
}
