package me.swezedcode.client.gui.tabGui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import me.swezedcode.client.Tea;
import me.swezedcode.client.gui.ClientOverlay;
import me.swezedcode.client.gui.other.ColorPickerGui;
import me.swezedcode.client.manager.Manager;
import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.module.modules.Options.Rainbow;
import me.swezedcode.client.module.modules.Visual.ClickGui;
import me.swezedcode.client.utils.render.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class TabGui {

	private static int selected, moduleIsSelected;

	private static boolean isOpen;

	private static final Minecraft mc = Minecraft.getMinecraft();

	public static int y;

	public static void setY(int y) {
		TabGui.y = y;
	}

	public static int getY() {
		return y;
	}

	public static void drawTabGui(int y) {
		TabGui.y = y;
		for (int i = 0; i < ModCategory.values().length; i++) {
			int color = 0xFF000000;
			ModCategory cat = ModCategory.values()[i];

			if (cat == ModCategory.Gui)
				continue;
			if (cat == ModCategory.NONE)
				continue;

			if (cat == ModCategory.Options)
				continue;

			String name = cat.name().substring(0, 1) + cat.name().substring(1).toLowerCase();

			Gui.drawRect(1, y + i * 10, 75, y + 10 + i * 10, 0x99000000);

			if (selected == i) {
				if (Rainbow.enabled) {
					Gui.drawRect(1, y + i * 10, 75, y + 10 + i * 10, ColorUtils.getRainbow(1L, 1F).getRGB());
				} else {
					Gui.drawRect(1, y + i * 10, 75, y + 10 + i * 10, ColorPickerGui.color);
				}
			}

			if (selected == i) {
				ClientOverlay.fontRenderer.drawStringWithShadow("  " + name, 1, y + i * 10 + 1,
						selected == i ? 0xFFFFFFFF : 0xFFFFFFFF);
			} else {
				ClientOverlay.fontRenderer.drawStringWithShadow(" " + name, 1, y + i * 10 + 1,
						selected == i ? 0xFFFFFFFF : 0xFFFFFFFF);
			}

			if (isOpen && i == selected) {
				for (int j = 0; j < getModsForCategory(cat).size(); j++) {
					Module mod = getModsForCategory(cat).get(j);
					if (j == moduleIsSelected) {
						if (Rainbow.enabled) {
							Gui.drawRect(80, y + i * 10 + j * 10, 167, y + i * 10 + j * 10 + 10,
									moduleIsSelected == j ? ColorUtils.getRainbow(1L, 1F).getRGB() : ColorPickerGui.color);
						} else {
							Gui.drawRect(80, y + i * 10 + j * 10, 167, y + i * 10 + j * 10 + 10,
									moduleIsSelected == j ? ColorPickerGui.color : 0x99000000);
						}
						if (mod.isToggled()) {
							ClientOverlay.fontRenderer.drawStringWithShadow(mod.getName(), 84, y + i * 10 + j * 10 + 1,
									0xFFFFFFFF);
						}else{
							ClientOverlay.fontRenderer.drawStringWithShadow(mod.getName(), 84, y + i * 10 + j * 10 + 1,
									0xFFCCCCCC);
						}
					} else {
						if (Rainbow.enabled) {
							Gui.drawRect(80, y + i * 10 + j * 10, 167, y + i * 10 + j * 10 + 10,
									moduleIsSelected == j ? ColorUtils.getRainbow(1L, 1F).getRGB() : 0x99000000);
						} else {
							Gui.drawRect(80, y + i * 10 + j * 10, 167, y + i * 10 + j * 10 + 10,
									moduleIsSelected == j ? ColorPickerGui.color : 0x99000000);
						}
						if (mod.isToggled()) {
							ClientOverlay.fontRenderer.drawStringWithShadow(mod.getName(), 82, y + i * 10 + j * 10 + 1,
									0xFFFFFFFF);
						}else{
							ClientOverlay.fontRenderer.drawStringWithShadow(mod.getName(), 82, y + i * 10 + j * 10 + 1,
									0xFFCCCCCC);
						}
					}
				}
			}

		}
	}

	public static void onKey(int key) {
		if (key == Keyboard.KEY_UP) {
			if (!isOpen) {
				selected--;
				if (selected <= -1) {
					selected = 5;
				}
			} else {
				moduleIsSelected--;
				if (moduleIsSelected <= 0) {
					moduleIsSelected = 0;
				}
			}
		}

		if (key == Keyboard.KEY_DOWN) {
			if (!isOpen) {
				selected++;
				if (selected >= 6) {
					selected = 0;
				}
			} else {
				moduleIsSelected++;
				if (moduleIsSelected >= getModsForCategory(ModCategory.values()[selected]).size() - 1) {
					moduleIsSelected = getModsForCategory(ModCategory.values()[selected]).size() - 1;
				}
			}
		}

		if (key == Keyboard.KEY_RIGHT) {
			isOpen = true;
		}

		if (key == Keyboard.KEY_LEFT) {
			isOpen = false;
			moduleIsSelected = 0;
		}

		if (key == Keyboard.KEY_RETURN) {
			getModsForCategory(ModCategory.values()[selected]).get(moduleIsSelected).toggle();
		}

	}

	private static List<Module> getModsForCategory(ModCategory category) {
		List<Module> mods = new ArrayList<Module>();

		for (Module mod : Manager.getManager().getModuleManager().getModules()) {
			if (mod.getModcategory() == category) {
				mods.add(mod);
			}
		}
		return mods;
	}

}
