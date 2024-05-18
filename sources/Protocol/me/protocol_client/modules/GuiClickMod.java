package me.protocol_client.modules;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import me.protocol_client.ui.click.Protocol.GuiClick;

import org.lwjgl.input.Keyboard;

public class GuiClickMod extends Module {
	public GuiClickMod() {
		super("Click Gui", "clickgui", Keyboard.KEY_RSHIFT, Category.RENDER, new String[] { "dsdfsdfsdfsdghgh" });
	}

	public void onEnable() {
		if (!(Wrapper.mc().currentScreen instanceof GuiClick)) {
			mc.displayGuiScreen(Protocol.getGuiClick());
		}
		this.setToggled(false);
	}
}
