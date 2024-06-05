package Ascii4UwUWareClient.Module.Modules.Move;

import org.lwjgl.input.Keyboard;

import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.World.EventPreUpdate;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;

public class InvMove extends Module {

	public InvMove() {
		super("InvMove", new String[] {}, ModuleType.Move);
	}

	@EventHandler
	public void onUpdate(EventPreUpdate event) {
		if (this.mc.currentScreen != null && !(this.mc.currentScreen instanceof GuiChat)) {
			KeyBinding[] key = { this.mc.gameSettings.keyBindForward, this.mc.gameSettings.keyBindBack,
					this.mc.gameSettings.keyBindLeft, this.mc.gameSettings.keyBindRight,
					this.mc.gameSettings.keyBindSprint, this.mc.gameSettings.keyBindJump };
			KeyBinding[] array;
			for (int length = (array = key).length, i = 0; i < length; ++i) {
				KeyBinding b = array[i];
				KeyBinding.setKeyBindState(b.getKeyCode(), Keyboard.isKeyDown(b.getKeyCode()));
			}
		}
	}
}
