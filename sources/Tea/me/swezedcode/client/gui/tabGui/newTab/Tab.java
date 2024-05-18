package me.swezedcode.client.gui.tabGui.newTab;

import java.util.ArrayList;

import me.swezedcode.client.Tea;
import me.swezedcode.client.gui.ClientOverlay;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.render.ColorUtils;
import me.swezedcode.client.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class Tab {
	private int color = 0xFF87969C;
	private TabGui gui;
	public ArrayList hacks;
	public String tabName;
	public int selectedItem = 0;
	public int menuHeight = 0;
	public int menuWidth = 0;
	private int colour;

	public Tab(TabGui GUI, String TabName) {
		this.tabName = TabName;
		this.gui = GUI;
		this.hacks = new ArrayList();
	}

	public void countMenuSize() {
		int maxWidth = 0;
		for (int i = 0; i < this.hacks.size(); i++) {
			if (Minecraft.getMinecraft().fontRendererObj
					.getStringWidth(((Module) this.hacks.get(i)).getName() + 4) > maxWidth) {
				maxWidth = (int) (Minecraft.getMinecraft().fontRendererObj
						.getStringWidth(((Module) this.hacks.get(i)).getName()) + 7.5F);
			}
		}
		this.menuWidth = maxWidth;
		this.menuHeight = (this.hacks.size() * this.gui.tabHeight - 1);
	}

	public int RGBtoHEX(int r, int g, int b, int a) {
		return (a << 24) + (r << 16) + (g << 8) + b;
	}

	public void drawTabMenu(int x, int y) {
		countMenuSize();
		x += 2;
		y += 2;
		RenderUtils.drawRect(x + 19, y - 1, x + this.menuWidth + 20, y + this.menuHeight - 1, 0x99000000);
		// UtilRender.drawRect(x - 2, y - 2, x - 1, y + this.menuHeight,
		// -16777216);
		// UtilRender.drawRect(x - 2, y + this.menuHeight - 1, x +
		// this.menuWidth - 1, y + this.menuHeight, -16777216);
		// UtilRender.drawRect(x - 2, y - 2, x + this.menuWidth - 1, y - 1,
		// -16777216);
		// UtilRender.drawRect(x + this.menuWidth - 2, y - 2, x + this.menuWidth
		// - 1, y + this.menuHeight, -16777216);
		for (int i = 0; i < this.hacks.size(); i++) {
			this.colour = -1;
			if (TabGui.currentMode.equalsIgnoreCase("blue")) {
				RenderUtils.drawRect(x + 20, y + this.gui.tabHeight * i - (float) 0.1, x + this.menuWidth + 19,
						y + this.gui.tabHeight * i + 9, i == TabGui.selectedItem ? 0xFF38CF3D : 0);
			} else if (TabGui.currentMode.equalsIgnoreCase("green")) {
				RenderUtils.drawRect(x - 1, y + this.gui.tabHeight * i - 1, x + this.menuWidth - 2,
						y + this.gui.tabHeight * i + 10, i == TabGui.selectedItem ? 0xFF38CF3D : 0);
			} else if (TabGui.currentMode.equalsIgnoreCase("orange")) {
				RenderUtils.drawRect(x - 1, y + this.gui.tabHeight * i - 1, x + this.menuWidth - 2,
						y + this.gui.tabHeight * i + 10, i == TabGui.selectedItem ? 0xFFFF7029 : 0);
			} else if (TabGui.currentMode.equalsIgnoreCase("red")) {
				RenderUtils.drawRect(x - 1, y + this.gui.tabHeight * i - 1, x + this.menuWidth - 2,
						y + this.gui.tabHeight * i + 10, i == TabGui.selectedItem ? 0xFFF52C2C : 0);
			} else if (TabGui.currentMode.equalsIgnoreCase("pink")) {
				RenderUtils.drawRect(x - 1, y + this.gui.tabHeight * i - 1, x + this.menuWidth - 2,
						y + this.gui.tabHeight * i + 10, i == TabGui.selectedItem ? 0xFFF227B5 : 0);
			} else if (TabGui.currentMode.equalsIgnoreCase("purple")) {
				RenderUtils.drawRect(x - 1, y + this.gui.tabHeight * i - 1, x + this.menuWidth - 2,
						y + this.gui.tabHeight * i + 10, i == TabGui.selectedItem ? 0xFFA82D83 : 0);
			} else if (TabGui.currentMode.equalsIgnoreCase("yellow")) {
				RenderUtils.drawRect(x - 1, y + this.gui.tabHeight * i - 1, x + this.menuWidth - 2,
						y + this.gui.tabHeight * i + 10, i == TabGui.selectedItem ? 0xFFE8D035 : 0);
			} else if (TabGui.currentMode.equalsIgnoreCase("rainbow")) {
				RenderUtils.drawRect(x - 1, y + this.gui.tabHeight * i - 1, x + this.menuWidth - 2,
						y + this.gui.tabHeight * i + 10, i == TabGui.selectedItem ? ColorUtils.getRainbow(0, 1).getRGB() : 0);
			}
			Tea.fontRenderer.drawStringWithShadow((((Module) this.hacks.get(i)).getName()), x + 22, y + this.gui.tabHeight * i,
					((Module) this.hacks.get(i)).isToggled() ? 0xFFFFFFFF : 0xFF81888A);
		}
	}
}