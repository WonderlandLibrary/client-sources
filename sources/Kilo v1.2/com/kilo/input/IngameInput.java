package com.kilo.input;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Keyboard;

import com.kilo.Kilo;
import com.kilo.manager.MacroManager;
import com.kilo.mod.Module;
import com.kilo.mod.ModuleManager;
import com.kilo.ui.GuiGrabber;
import com.kilo.ui.UIHacks;
import com.kilo.ui.UIHandler;
import com.kilo.ui.UIMusic;
import com.kilo.ui.inter.slotlist.part.Macro;
import com.kilo.util.Util;

public class IngameInput {
	
	private static final Minecraft mc = Minecraft.getMinecraft();
	
	public static void keyboardPress(int key) {
		if (key == Keyboard.KEY_NONE) { return; }
		if (key == Keyboard.KEY_RSHIFT && Kilo.kilo().canHack) {
			mc.displayGuiScreen(new GuiGrabber());
			UIHandler.changeUI(new UIHacks());
			return;
		}
		if (key == Keyboard.KEY_M) {
			mc.displayGuiScreen(new GuiGrabber());
			UIHandler.changeUI(new UIMusic());
			return;
		}
		if (Kilo.kilo().canHack) {
			for(Module m : ModuleManager.list()) {
				if (Util.makeInteger(m.getOptionValue("keybind")) == key) {
					m.toggle();
				}
			}
		
			for(Macro m : MacroManager.getList()) {
				if (m.keybind == key) {
					mc.thePlayer.sendChatMessage(m.command);
				}
			}
		}
	}
	
	public static void keyboardRelease(int key) {
	}
	
}
