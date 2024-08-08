/*
 * Decompiled with CFR 0.136.
 */
package me.napoleon.napoline.modules.movement;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

import me.napoleon.napoline.events.EventUpdate;
import me.napoleon.napoline.manager.event.EventTarget;
import me.napoleon.napoline.modules.Mod;
import me.napoleon.napoline.modules.ModCategory;

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
