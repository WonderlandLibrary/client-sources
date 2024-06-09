package me.protocol_client.modules;

import java.util.Objects;

import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import me.protocol_client.ui.click.Protocol.GuiClick;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPreMotionUpdates;

public class InvMove extends Module {
	public InvMove() {
		super("InventoryMove", "inventorymove", 0, Category.MOVEMENT, new String[] { "dsdfsdfsdfsdghgh" });
		setShowing(false);
	}

	public void onEnable() {
		EventManager.register(this);
	}

	public void onDisable() {
		EventManager.unregister(this);
	}

	@EventTarget
	public void onEvent(EventPreMotionUpdates event) {
		KeyBinding[] keys = { Wrapper.mc().gameSettings.keyBindRight, Wrapper.mc().gameSettings.keyBindLeft, Wrapper.mc().gameSettings.keyBindBack, Wrapper.mc().gameSettings.keyBindForward, Wrapper.mc().gameSettings.keyBindJump, Wrapper.mc().gameSettings.keyBindSprint };
		KeyBinding[] arrayOfKeyBinding1;
		int nignog;
		int hereInMyGarage;
		if ((Wrapper.mc().currentScreen instanceof GuiContainer) || Wrapper.mc().currentScreen instanceof GuiClick || Wrapper.mc().currentScreen != null && !(Wrapper.mc().currentScreen instanceof GuiChat)) {
			nignog = (arrayOfKeyBinding1 = keys).length;
			if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
				for(int i = 0; i < 3; i++){
				Wrapper.getPlayer().rotationYaw++;
			}
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
				for(int i = 0; i < 3; i++){
				Wrapper.getPlayer().rotationYaw--;
			}
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
				for(int i = 0; i < 3; i++){
				Wrapper.getPlayer().rotationPitch--;
			}
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
				for(int i = 0; i < 3; i++){
				Wrapper.getPlayer().rotationPitch++;
			}
			}
			for (hereInMyGarage = 0; hereInMyGarage < nignog; hereInMyGarage++) {
				KeyBinding key = arrayOfKeyBinding1[hereInMyGarage];
				key.pressed = Keyboard.isKeyDown(key.getKeyCode());
			}
		} else if (Objects.isNull(Wrapper.mc().currentScreen)) {
			nignog = (arrayOfKeyBinding1 = keys).length;
			for (hereInMyGarage = 0; hereInMyGarage < nignog; hereInMyGarage++) {
				KeyBinding bind = arrayOfKeyBinding1[hereInMyGarage];
				if (!Keyboard.isKeyDown(bind.getKeyCode())) {
					KeyBinding.setKeyBindState(bind.getKeyCode(), false);
				}
			}
		}
	}
}
