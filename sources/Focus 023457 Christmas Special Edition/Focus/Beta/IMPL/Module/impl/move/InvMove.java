package Focus.Beta.IMPL.Module.impl.move;

import org.lwjgl.input.Keyboard;

import Focus.Beta.API.EventHandler;
import Focus.Beta.API.events.world.EventPreUpdate;
import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.IMPL.Module.Type;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;

public class InvMove extends Module {

	public InvMove() {
		super("InvMovement", new String[] {}, Type.MOVE, "Move with your inventory open.");
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
