/*
 * Decompiled with CFR 0.136.
 */
package me.superskidder.lune.modules.movement;

import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.events.EventUpdate;
import me.superskidder.lune.manager.event.EventTarget;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class InventoryMove extends Mod {
	public InventoryMove() {
		super("InventoryMove", ModCategory.Movement ,"You can move when guis open");
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		if (mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat)) {
			KeyBinding[] key = { mc.gameSettings.keyBindForward, mc.gameSettings.keyBindBack,
					mc.gameSettings.keyBindLeft, mc.gameSettings.keyBindRight,
					mc.gameSettings.keyBindSprint, mc.gameSettings.keyBindJump };
			KeyBinding[] array;
			for (int length = (array = key).length, i = 0; i < length; ++i) {
				KeyBinding b = array[i];
				KeyBinding.setKeyBindState(b.getKeyCode(), Keyboard.isKeyDown(b.getKeyCode()));
			}
		}
	}
}
