package net.minecraft.client.main.neptune.Mod.Collection.Render;

import net.minecraft.client.main.neptune.Neptune;
import net.minecraft.client.main.neptune.Mod.Category;
import net.minecraft.client.main.neptune.Mod.Mod;

import org.lwjgl.input.Keyboard;

public class Gui extends Mod {

	public Gui() {
		super("GUI", Category.HACKS);
		this.setBind(Keyboard.KEY_LBRACKET);
	}

	public void onEnable() {
		this.mc.displayGuiScreen(Neptune.getWinter().getGui());
		this.setEnabled(false);
	}

}
