package net.minecraft.client.triton.ui.ui.virtue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.ModuleManager;
import net.minecraft.client.triton.utils.ClientUtils;
import net.minecraft.client.triton.utils.RenderUtils;
import net.minecraft.client.triton.utils.RenderingUtils;

public class TabGui {
	private static final int NO_COLOR = 0;
	private static final int INSIDE_COLOR = -1610612736;
	private static final int BORDER_COLOR = 2013265920;
	private static final int COMPONENT_HEIGHT = 18;
	private static final double BUFFER_HEIGHT = 1.0;
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
		Module.Category[] values;
		for (int length = (values = Module.Category.values()).length, i = 0; i < length; ++i) {
			final Module.Category category = values[i];
			final String name = capitalize(category.name().toLowerCase());
			final int stringWidth = ClientUtils.clientFont().getStringWidth(name);
			highestWidth = Math.max(stringWidth, highestWidth);
		}
		TabGui.baseCategoryWidth = highestWidth + 5;
		TabGui.baseCategoryHeight = Module.Category.values().length * 18 + 2;
	}

	public static void render() {
		final int baseYPosition = 14;
		updateBars();
		double yPosition = baseYPosition;
		for (int i = 0; i < Module.Category.values().length; ++i) {
			RenderUtils.rectangleBordered(2.0, yPosition, 2 + TabGui.baseCategoryWidth, yPosition + 18.0,
					0.6000000238418579, (TabGui.categoryTab == i) ? -14935012 : -13421773, -16777216);
			yPosition += 19.0;
		}
		double yPos = 15.0;
		double yPosBottom = 33.0;
		for (int j = 0; j < Module.Category.values().length; ++j) {
			final Module.Category category = Module.Category.values()[j];
			final String name = capitalize(category.name().toLowerCase());
			ClientUtils.clientFont().drawStringWithShadow(name,
					TabGui.baseCategoryWidth / 2 - ClientUtils.clientFont().getStringWidth(name) / 2 + 2, yPos + 5.0,
					(TabGui.categoryTab == j) ? -1 : -6052957);
			yPos += 19.0;
			yPosBottom += 19.0;
		}
		if (TabGui.section == Section.MODS) {
			yPosition = baseYPosition;
			for (int i = 0; i < getModsInCategory(Module.Category.values()[TabGui.categoryTab]).size(); ++i) {
				RenderUtils.rectangleBordered(TabGui.baseCategoryWidth + 4, yPosition,
						2 + TabGui.baseCategoryWidth + TabGui.baseModWidth + 2, yPosition + 18.0, 0.6000000238418579,
						(TabGui.modTab == i) ? -14935012 : -13421773, -16777216);
				yPosition += 19.0;
			}
			yPos = baseYPosition;
			yPosBottom = baseYPosition + 18;
			for (int j = 0; j < getModsInCategory(Module.Category.values()[TabGui.categoryTab]).size(); ++j) {
				final Module mod = getModsInCategory(Module.Category.values()[TabGui.categoryTab]).get(j);
				final String name = mod.getDisplayName();
				ClientUtils.clientFont().drawStringWithShadow(name,
						TabGui.baseCategoryWidth + TabGui.baseModWidth / 2
								- ClientUtils.clientFont().getStringWidth(name) / 2 + 4,
						yPos + 6.0, (TabGui.modTab == j) ? (mod.isEnabled() ? -1 : -3026479)
								: (mod.isEnabled() ? -2171170 : -6052957));
				yPos += 19.0;
				yPosBottom += 19.0;
			}
		}
	}

	public static void keyPress(final int key) {
		if (TabGui.section == Section.CATEGORY) {
			switch (key) {
			case 205: {
				int highestWidth = 0;
				for (final Module module : getModsInCategory(Module.Category.values()[TabGui.categoryTab])) {
					final String name = capitalize(module.getDisplayName().toLowerCase());
					final int stringWidth = ClientUtils.clientFont().getStringWidth(name);
					highestWidth = Math.max(stringWidth, highestWidth);
				}
				TabGui.baseModWidth = highestWidth + 6;
				TabGui.baseModHeight = getModsInCategory(Module.Category.values()[TabGui.categoryTab]).size() * 18 + 2;
				TabGui.modTargetPosition = (TabGui.modPosition = TabGui.categoryTargetPosition);
				TabGui.modTab = 0;
				TabGui.section = Section.MODS;
				break;
			}
			case 208: {
				++TabGui.categoryTab;
				TabGui.categoryTargetPosition += 18;
				if (TabGui.categoryTab > Module.Category.values().length - 1) {
					TabGui.transitionQuickly = true;
					TabGui.categoryTargetPosition = 15;
					TabGui.categoryTab = 0;
					break;
				}
				break;
			}
			case 200: {
				--TabGui.categoryTab;
				TabGui.categoryTargetPosition -= 18;
				if (TabGui.categoryTab < 0) {
					TabGui.transitionQuickly = true;
					TabGui.categoryTargetPosition = 15 + (Module.Category.values().length - 1) * 18;
					TabGui.categoryTab = Module.Category.values().length - 1;
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
				final Module mod = getModsInCategory(Module.Category.values()[TabGui.categoryTab]).get(TabGui.modTab);
				mod.toggle();
				break;
			}
			case 208: {
				++TabGui.modTab;
				TabGui.modTargetPosition += 18;
				if (TabGui.modTab > getModsInCategory(Module.Category.values()[TabGui.categoryTab]).size() - 1) {
					TabGui.transitionQuickly = true;
					TabGui.modTargetPosition = TabGui.categoryTargetPosition;
					TabGui.modTab = 0;
					break;
				}
				break;
			}
			case 200: {
				--TabGui.modTab;
				TabGui.modTargetPosition -= 18;
				if (TabGui.modTab < 0) {
					TabGui.transitionQuickly = true;
					TabGui.modTargetPosition = TabGui.categoryTargetPosition
							+ (getModsInCategory(Module.Category.values()[TabGui.categoryTab]).size() - 1) * 18;
					TabGui.modTab = getModsInCategory(Module.Category.values()[TabGui.categoryTab]).size() - 1;
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

	private static List<Module> getModsInCategory(final Module.Category category) {
		final List<Module> modList = new ArrayList<Module>();
		for (final Module mod : ModuleManager.getModules()) {
			if (mod.getCategory() == category) {
				modList.add(mod);
			}
		}
		modList.sort(new Comparator<Module>() {
			@Override
			public int compare(final Module m1, final Module m2) {
				final String s1 = m1.getDisplayName();
				final String s2 = m2.getDisplayName();
				return s1.compareTo(s2);
			}
		});
		return modList;
	}

	public static String capitalize(String line) {
		return Character.toUpperCase(line.charAt(0)) + line.substring(1);
	}

	private enum Section {
		CATEGORY("CATEGORY", 0), MODS("MODS", 1);

		private Section(final String s, final int n) {
		}
	}
}
