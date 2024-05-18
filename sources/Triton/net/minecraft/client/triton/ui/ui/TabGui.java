package net.minecraft.client.triton.ui.ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import io.netty.util.internal.StringUtil;
import net.minecraft.client.triton.impl.modules.render.Hud;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.ModuleManager;
import net.minecraft.client.triton.management.module.Module.Category;
import net.minecraft.client.triton.utils.ClientUtils;
import net.minecraft.client.triton.utils.RenderingUtils;

public class TabGui {
	private static int baseCategoryWidth;
	private static int baseCategoryHeight;
	private static int baseModWidth;
	private static int baseModHeight;
	private static Section section;
	private static int categoryTab;
	private static int modTab;
	private static int categoryPosition;
	private static int categoryTargetPosition;
	private static int modPosition;
	private static int modTargetPosition;
	private static boolean transitionQuickly;
	private static long lastUpdateTime;

	static {
		TabGui.section = Section.CATEGORY;
		TabGui.categoryTab = 0;
		TabGui.modTab = 0;
		TabGui.categoryPosition = 15;
		TabGui.categoryTargetPosition = 15;
		TabGui.modPosition = 15;
		TabGui.modTargetPosition = 15;
	}

	public static void init() {
		int highestWidth = 0;
		Category[] values;
		for (int length = (values = Category.values()).length, i = 0; i < length; ++i) {
			final Category category = values[i];
			final String name = capitalize(category.name().toLowerCase());
			final int stringWidth = ClientUtils.clientFont.getStringWidth(name);
			highestWidth = Math.max(stringWidth, highestWidth);
		}
		TabGui.baseCategoryWidth = highestWidth;
		TabGui.baseCategoryHeight = Category.values().length * 14 + 2;
	}

	public static double mainhue = 0;

	public static void render() {
		updateBars();
		RenderingUtils.drawRect(2, 14, TabGui.baseCategoryWidth + 5, TabGui.baseCategoryHeight + 2, 0xa6000000);
		RenderingUtils.drawRect(2, TabGui.categoryPosition - 1, TabGui.baseCategoryWidth + 5,
				TabGui.categoryPosition + 11, Hud.currentColor);
		int yPos = 13;
		int yPosBottom = 29;
		for (int i = 0; i < Category.values().length; ++i) {
			final Category category = Category.values()[i];
			final String name = capitalize(category.name().toLowerCase());
			ClientUtils.clientFont.drawStringWithShadow(name,
					TabGui.baseCategoryWidth / ClientUtils.clientFont.getStringWidth(name) / 2 + 4, yPos + 3, -1);
			yPos += 12;
			yPosBottom += 12;
		}
		if (TabGui.section == Section.MODS) {
			RenderingUtils
					.drawRect(TabGui.baseCategoryWidth + 6, TabGui.categoryPosition - 1,
							TabGui.baseCategoryWidth + 2 + TabGui.baseModWidth,
							TabGui.categoryPosition
									+ (getModsInCategory(Category.values()[TabGui.categoryTab]).size() * 12) - 1,
									0xa6000000);
			RenderingUtils.drawRect(TabGui.baseCategoryWidth + 6, TabGui.modPosition - 1, TabGui.baseCategoryWidth + TabGui.baseModWidth + 2, TabGui.modPosition + 11, Hud.currentColor);
			yPos = TabGui.categoryPosition;
			yPosBottom = TabGui.categoryPosition + 12;
			for (int i = 0; i < getModsInCategory(Category.values()[TabGui.categoryTab]).size(); ++i) {
				final Module mod = getModsInCategory(Category.values()[TabGui.categoryTab]).get(i);
				final String name = mod.getDisplayName();
				ClientUtils.clientFont.drawStringWithShadow(
						name, TabGui.baseCategoryWidth
								+ TabGui.baseModWidth / ClientUtils.clientFont.getStringWidth(name) / 2 + 8,
						yPos + 1, mod.isEnabled() ? -1 : -4210753);
				yPos += 12;
				yPosBottom += 12;
			}
		}
	}

	public static void keyPress(final int key) {
		if (TabGui.section == Section.CATEGORY) {
			switch (key) {
			case 205: {
				int highestWidth = 0;
				for (final Module module : getModsInCategory(Category.values()[TabGui.categoryTab])) {
					final String name = capitalize(module.getDisplayName().toLowerCase());
					final int stringWidth = ClientUtils.clientFont.getStringWidth(name);
					highestWidth = Math.max(stringWidth, highestWidth);
				}
				TabGui.baseModWidth = highestWidth + 6;
				TabGui.baseModHeight = getModsInCategory(Category.values()[TabGui.categoryTab]).size() * 14 + 2;
				TabGui.modTargetPosition = (TabGui.modPosition = TabGui.categoryTargetPosition);
				TabGui.modTab = 0;
				TabGui.section = Section.MODS;
				break;
			}
			case 208: {
				++TabGui.categoryTab;
				TabGui.categoryTargetPosition += 12;
				if (TabGui.categoryTab > Category.values().length - 1) {
					TabGui.transitionQuickly = true;
					TabGui.categoryTargetPosition = 15;
					TabGui.categoryTab = 0;
					break;
				}
				break;
			}
			case 200: {
				--TabGui.categoryTab;
				TabGui.categoryTargetPosition -= 12;
				if (TabGui.categoryTab < 0) {
					TabGui.transitionQuickly = true;
					TabGui.categoryTargetPosition = 15 + (Category.values().length - 1) * 12;
					TabGui.categoryTab = Category.values().length - 1;
					break;
				}
				break;
			}
			}
		} else if (TabGui.section == Section.MODS) {
			switch (key) {
			case 203: {
				TabGui.section = Section.CATEGORY;
				break;
			}
			case 205: {
				final Module mod = getModsInCategory(Category.values()[TabGui.categoryTab]).get(TabGui.modTab);
				mod.toggle();
				break;
			}
			case 208: {
				++TabGui.modTab;
				TabGui.modTargetPosition += 12;
				if (TabGui.modTab > getModsInCategory(Category.values()[TabGui.categoryTab]).size() - 1) {
					TabGui.transitionQuickly = true;
					TabGui.modTargetPosition = TabGui.categoryTargetPosition;
					TabGui.modTab = 0;
					break;
				}
				break;
			}
			case 200: {
				--TabGui.modTab;
				TabGui.modTargetPosition -= 12;
				if (TabGui.modTab < 0) {
					TabGui.transitionQuickly = true;
					TabGui.modTargetPosition = TabGui.categoryTargetPosition
							+ (getModsInCategory(Category.values()[TabGui.categoryTab]).size() - 1) * 12;
					TabGui.modTab = getModsInCategory(Category.values()[TabGui.categoryTab]).size() - 1;
					break;
				}
				break;
			}
			}
		}
	}

	private static void updateBars() {
		final long timeDifference = System.currentTimeMillis() - TabGui.lastUpdateTime;
		TabGui.lastUpdateTime = System.currentTimeMillis();
		int increment = TabGui.transitionQuickly ? 100 : 20;
		increment = Math.max(1, Math.round(increment * timeDifference / 100L));
		if (TabGui.categoryPosition < TabGui.categoryTargetPosition) {
			TabGui.categoryPosition += increment;
			if (TabGui.categoryPosition >= TabGui.categoryTargetPosition) {
				TabGui.categoryPosition = TabGui.categoryTargetPosition;
				TabGui.transitionQuickly = false;
			}
		} else if (TabGui.categoryPosition > TabGui.categoryTargetPosition) {
			TabGui.categoryPosition -= increment;
			if (TabGui.categoryPosition <= TabGui.categoryTargetPosition) {
				TabGui.categoryPosition = TabGui.categoryTargetPosition;
				TabGui.transitionQuickly = false;
			}
		}
		if (TabGui.modPosition < TabGui.modTargetPosition) {
			TabGui.modPosition += increment;
			if (TabGui.modPosition >= TabGui.modTargetPosition) {
				TabGui.modPosition = TabGui.modTargetPosition;
				TabGui.transitionQuickly = false;
			}
		} else if (TabGui.modPosition > TabGui.modTargetPosition) {
			TabGui.modPosition -= increment;
			if (TabGui.modPosition <= TabGui.modTargetPosition) {
				TabGui.modPosition = TabGui.modTargetPosition;
				TabGui.transitionQuickly = false;
			}
		}
	}

	private static List<Module> getModsInCategory(final Category category) {
		final List<Module> modList = new ArrayList<Module>();
		for (final Module mod : ModuleManager.moduleList) {
			if (mod.getCategory() == category) {
				modList.add(mod);
			}
		}
		return modList;
	}

	private enum Section {
		CATEGORY("CATEGORY", 0), MODS("MODS", 1);

		private Section(final String s, final int n) {
		}
	}

	public static String capitalize(String line) {
		return Character.toUpperCase(line.charAt(0)) + line.substring(1);
	}
}
