package me.swezedcode.client.module.modules.Player;

import java.util.Objects;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.gui.clickGui.ClickGui;
import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.events.EventPreMotionUpdates;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.KeyBinding;

public class InvMove extends Module {

	public InvMove() {
		super("InvMove", Keyboard.KEY_NONE, 0xFF33DEDE, ModCategory.Player);
	}
	
	@EventListener
	public void onPre(EventPreMotionUpdates e) {
		KeyBinding[] moveKeys = { mc.gameSettings.keyBindRight, mc.gameSettings.keyBindLeft,
				mc.gameSettings.keyBindBack, mc.gameSettings.keyBindForward, mc.gameSettings.keyBindJump,
				mc.gameSettings.keyBindSprint };
		if ((mc.currentScreen instanceof GuiContainer) || (mc.currentScreen instanceof ClickGui)) {
			KeyBinding[] array;
			int length = (array = moveKeys).length;
			for (int i = 0; i < length; i++) {
				KeyBinding key = array[i];
				key.pressed = Keyboard.isKeyDown(key.getKeyCode());
			}
		} else if (Objects.isNull(mc.currentScreen)) {
			KeyBinding[] array2;
			int length2 = (array2 = moveKeys).length;
			for (int j = 0; j < length2; j++) {
				KeyBinding bind = array2[j];
				if (!Keyboard.isKeyDown(bind.getKeyCode())) {
					KeyBinding.setKeyBindState(bind.getKeyCode(), false);
				}
			}
		}
	}
	
}