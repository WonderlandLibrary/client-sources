package de.tired.module.impl.list.visual;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.api.guis.clickgui.setting.impl.ColorPickerSetting;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;
import de.tired.module.ModuleManager;
import de.tired.tired.Tired;

import java.awt.*;

@ModuleAnnotation(name = "ClickGUI", category = ModuleCategory.RENDER, clickG = "ClickGui idk lol")
public class ClickGUI extends Module {

	public ColorPickerSetting colorPickerSetting = new ColorPickerSetting("ColorClickGUI", this, true, new Color(0, 0, 0, 255), (new Color(0, 0, 0, 255)).getRGB(), null);

	public static ClickGUI getInstance() {
		return ModuleManager.getInstance(ClickGUI.class);
	}

	@Override
	public void onState() {
		MC.displayGuiScreen(Tired.INSTANCE.clickGui);
		unableModule();

	}

	@Override
	public void onUndo() {

	}
}
