package me.swezedcode.client.gui.tabGui.newTab;

import java.util.ArrayList;

import me.swezedcode.client.Tea;
import me.swezedcode.client.gui.ClientOverlay;
import me.swezedcode.client.manager.Manager;
import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.render.ColorUtils;
import me.swezedcode.client.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class TabGui {
	public String colorNormal = "";
	public int guiWidth = 88;
	public int guiHeight = 0;
	public int tabHeight = 12;
	public static int selectedTab = 0;
	public static int selectedItem = 0;
	public static boolean mainMenu = true;
	public static ArrayList tabsList;
	private Minecraft mc;

	public static String currentMode = "blue";

	public TabGui(Minecraft minecraft) {
		this.mc = minecraft;
		tabsList = new ArrayList();
		Tab tabPlayer = new Tab(this, "Combat");
		for (Module module : Manager.getManager().getModuleManager().getModules()) {
			if (module.getModcategory() == ModCategory.Fight) {
				tabPlayer.hacks.add(module);
			}
		}
		tabsList.add(tabPlayer);

		Tab tabMove = new Tab(this, "World");
		for (Module module : Manager.getManager().getModuleManager().getModules()) {
			if (module.getModcategory() == ModCategory.World) {
				tabMove.hacks.add(module);
			}
		}
		tabsList.add(tabMove);

		Tab tabMovement = new Tab(this, "Player");
		for (Module module : Manager.getManager().getModuleManager().getModules()) {
			if (module.getModcategory() == ModCategory.Player) {
				tabMovement.hacks.add(module);
			}
		}
		tabsList.add(tabMovement);

		Tab tabMovement2 = new Tab(this, "Render");
		for (Module module : Manager.getManager().getModuleManager().getModules()) {
			if (module.getModcategory() == ModCategory.Visual) {
				tabMovement2.hacks.add(module);
			}
		}
		tabsList.add(tabMovement2);

		Tab tabWorld = new Tab(this, "Movement");
		for (Module module : Manager.getManager().getModuleManager().getModules()) {
			if (module.getModcategory() == ModCategory.Motion) {
				tabWorld.hacks.add(module);
			}
		}
		tabsList.add(tabWorld);

		Tab tabRender = new Tab(this, "Exploits");
		for (Module module : Manager.getManager().getModuleManager().getModules()) {
			if (module.getModcategory() == ModCategory.Exploit) {
				tabRender.hacks.add(module);
			}
		}
		tabsList.add(tabRender);

		this.guiHeight = (this.tabHeight + tabsList.size() * this.tabHeight);
	}

	public int RGBtoHEX(int r, int g, int b, int a) {
		return (a << 24) + (r << 16) + (g << 8) + b;
	}

	public void drawGui(int posY, int posX, int width) {
		int x = posY;
		int y = posX;
		this.guiWidth = width;
		RenderUtils.drawRect(posY - +5, posX, posY + this.guiWidth + 20, posX - 1 + this.guiHeight - 12, 0x99000000);
		RenderUtils.drawRect(posY - +5, posX, posY - 59 + this.guiWidth, posX + this.guiHeight - 13, 0xFF38CF3D);
		// UtilRender.drawRect(posY - 2, posX - 1, posY + this.guiWidth + 1,
		// posX, -16777216);
		// UtilRender.drawRect(posY - 2, posX - 1, posY - 1, posX +
		// this.guiHeight - 12, -16777216);
		// UtilRender.drawRect(posY - 2, posX + this.guiHeight - 13, posY +
		// this.guiWidth + 1, posX + this.guiHeight - 12, -16777216);
		// UtilRender.drawRect(posY + this.guiWidth, posX - 1, posY +
		// this.guiWidth + 1, posX + this.guiHeight - 12, -16777216);
		int yOff = posX + 3;
		int[] var10000 = { -739999111, -6723841, -10040065, -43691 };
		for (int i = 0; i < tabsList.size(); i++) {
			if (currentMode.equalsIgnoreCase("blue")) {
				RenderUtils.drawRect(x - 1, yOff - 1, x + this.guiWidth + 19, y + this.tabHeight * i + 11,
						i == selectedTab ? 0xFF38CF3D : 0);
			} else if (currentMode.equalsIgnoreCase("green")) {
				RenderUtils.drawRect(x - 1, yOff - 1, x + this.guiWidth, y + this.tabHeight * i + 11,
						i == selectedTab ? 0xFF38CF3D : 0);
			} else if (currentMode.equalsIgnoreCase("orange")) {
				RenderUtils.drawRect(x - 1, yOff - 1, x + this.guiWidth, y + this.tabHeight * i + 11,
						i == selectedTab ? 0xFFFF7029 : 0);
			} else if (currentMode.equalsIgnoreCase("red")) {
				RenderUtils.drawRect(x - 1, yOff - 1, x + this.guiWidth, y + this.tabHeight * i + 11,
						i == selectedTab ? 0xFFF52C2C : 0);
			} else if (currentMode.equalsIgnoreCase("pink")) {
				RenderUtils.drawRect(x - 1, yOff - 1, x + this.guiWidth, y + this.tabHeight * i + 11,
						i == selectedTab ? 0xFFF227B5 : 0);
			} else if (currentMode.equalsIgnoreCase("purple")) {
				RenderUtils.drawRect(x - 1, yOff - 1, x + this.guiWidth, y + this.tabHeight * i + 11,
						i == selectedTab ? 0xFFA82D83 : 0);
			} else if (currentMode.equalsIgnoreCase("yellow")) {
				RenderUtils.drawRect(x - 1, yOff - 1, x + this.guiWidth, y + this.tabHeight * i + 11,
						i == selectedTab ? 0xFFE8D035 : 0);
			} else if (currentMode.equalsIgnoreCase("rainbow")) {
				RenderUtils.drawRect(x - 1, yOff - 1, x + this.guiWidth, y + this.tabHeight * i + 11,
						i == selectedTab ? ColorUtils.getRainbow(0, 1).getRGB() : 0);
			}
			if (i == selectedTab) {
				Tea.fontRenderer.drawStringWithShadow(" " + ((Tab) tabsList.get(i)).tabName, x + 2, yOff + 1, -3);
			} else {
				Tea.fontRenderer.drawStringWithShadow("" + ((Tab) tabsList.get(i)).tabName, x + 4, yOff + 1, -3);
			}
			if ((i == selectedTab) && (!mainMenu)) {
				((Tab) tabsList.get(i)).drawTabMenu(x + this.guiWidth + 2, yOff - 2);
			}
			yOff += this.tabHeight;
		}
	}

	public static void up() {
		if (mainMenu) {
			selectedTab -= 1;
			if (selectedTab < 0) {
				selectedTab = tabsList.size() - 1;
			}
		} else {
			selectedItem -= 1;
			if (selectedItem < 0) {
				selectedItem = ((Tab) tabsList.get(selectedTab)).hacks.size() - 1;
			}
		}
	}

	public static void down() {
		if (mainMenu) {
			selectedTab += 1;
			if (selectedTab > tabsList.size() - 1) {
				selectedTab = 0;
			}
		} else {
			selectedItem += 1;
			if (selectedItem > ((Tab) tabsList.get(selectedTab)).hacks.size() - 1) {
				selectedItem = 0;
			}
		}
	}

	public static void left() {
		if (!mainMenu) {
			mainMenu = true;
		}
	}

	public static void right() {
		if (mainMenu) {
			mainMenu = false;
			selectedItem = 0;
		}
	}

	public static void enter() {
		if (!mainMenu) {
			int sel = selectedItem;
			((Module) ((Tab) tabsList.get(selectedTab)).hacks.get(sel)).toggle();
		}
	}
}
