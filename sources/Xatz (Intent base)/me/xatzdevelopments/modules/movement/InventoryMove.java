package me.xatzdevelopments.modules.movement;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventMotion;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.modules.Module.Category;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

public class InventoryMove extends Module
{
    private KeyBinding[] affectedBindings;
    
    public InventoryMove() {
		super("InvMove", Keyboard.KEY_NONE, Category.MOVEMENT, "Lets you move while in inventory GUI.");
        this.affectedBindings = new KeyBinding[] { this.mc.gameSettings.keyBindForward, this.mc.gameSettings.keyBindBack, this.mc.gameSettings.keyBindRight, this.mc.gameSettings.keyBindLeft, this.mc.gameSettings.keyBindJump, this.mc.gameSettings.keyBindSprint };
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
        this.mc.thePlayer.setSprinting(this.mc.gameSettings.keyBindSprint.getIsKeyPressed());
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventMotion && e.isPre() && !(this.mc.currentScreen instanceof GuiChat)) {
            KeyBinding[] affectedBindings;
            for (int length = (affectedBindings = this.affectedBindings).length, i = 0; i < length; ++i) {
                final KeyBinding keyBinding;
                final KeyBinding a = keyBinding = affectedBindings[i];
                final GameSettings gameSettings = this.mc.gameSettings;
                keyBinding.pressed = GameSettings.isKeyDown(a);
            }
        }
    }
}
