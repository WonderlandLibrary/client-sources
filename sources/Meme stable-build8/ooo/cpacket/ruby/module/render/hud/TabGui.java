package ooo.cpacket.ruby.module.render.hud;

import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glTranslated;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import ooo.cpacket.lemongui.settings.Setting;
import ooo.cpacket.ruby.Ruby;
import ooo.cpacket.ruby.module.Module;

public class TabGui {
	private List<Tab> tabs = new ArrayList<>();
	static final int OFFSET = 3;
	public static final TabGui INSTANCE = new TabGui();

	static Color BACKGROUND = new Color(0, 0, 0, 175);
	static Color BORDER = new Color(0, 0, 0, 255);
	static Color SELECTED = new Color(38, 164, 78, 200);
	static Color FOREGROUND = Color.white;

	private int selectedTab = 0;
	private int selectedSubTab = -1;

	public TabGui() {
		HashMap<Module.Category, List<Module>> moduleCategoryMap = new HashMap<>();

		for (Module module : Ruby.getRuby.getModuleManager().modules) {
			if (!moduleCategoryMap.containsKey(module.getCategory())) {
				moduleCategoryMap.put(module.getCategory(), new ArrayList<>());
			}

			moduleCategoryMap.get(module.getCategory()).add(module);
		}

		for (Map.Entry<Module.Category, List<Module>> moduleCategoryListEntry : moduleCategoryMap.entrySet()) {
			Tab tab = new Tab(moduleCategoryListEntry.getKey().name());

			for (Module module : moduleCategoryListEntry.getValue()) {
				SubTab rolf2 = new SubTab(module.getName(), subTab -> subTab.getObject().toggle(), module);
				tab.addSubTab(rolf2);
			}
			this.addTab(tab);
		}
	}

	public void addTab(Tab tab) {
		tabs.add(tab);
	}

	public void render(int x, int y) {
		y = 25;
		glTranslated(x, y, 0);

		FontRenderer font = Minecraft.getMinecraft().fontRendererObj;

		int height = (font.FONT_HEIGHT + OFFSET) * tabs.size();

		int width = 0;

		for (Tab tab : tabs) {
			if (font.getStringWidth(tab.getText()) > width) {
				width = font.getStringWidth(tab.getText());
			}
		}

		width += 24 + 2;

		drawRect(GL_QUADS, 0, 0, width, height, BACKGROUND.getRGB());

		glLineWidth(1.0f);
		drawRect(GL_LINE_LOOP, 0, 0, width, height, BORDER.getRGB());

		int offset = 2;

		int i = 0;

		for (Tab tab : tabs) {
			if (selectedTab == i) {
				drawRect(GL_QUADS, -1, offset - 2, width, offset + font.FONT_HEIGHT + OFFSET - 1, SELECTED.getRGB());

				if (selectedSubTab != -1) {
					tab.renderSubTabs(width, offset - 2, selectedSubTab);
				}
			}

			font.drawString(tab.getText(), 2, offset, FOREGROUND.getRGB());
			offset += font.FONT_HEIGHT + OFFSET;
			i++;
		}

		glTranslated(-x, -y, 0);
	}

	public void handleKey(int keycode) {
		if (keycode == Keyboard.KEY_DOWN) {
			if (selectedSubTab == -1) {
				selectedTab++;

				if (selectedTab >= tabs.size()) {
					selectedTab = 0;
				}
			} else {
				selectedSubTab++;

				if (selectedSubTab >= tabs.get(selectedTab).getSubTabs().size()) {
					selectedSubTab = 0;
				}
			}
		} else if (keycode == Keyboard.KEY_UP) {
			if (selectedSubTab == -1) {
				selectedTab--;

				if (selectedTab < 0) {
					selectedTab = tabs.size() - 1;
				}
			} else {
				selectedSubTab--;

				if (selectedSubTab < 0) {
					selectedSubTab = tabs.get(selectedTab).getSubTabs().size() - 1;
				}
			}
		} else if (keycode == Keyboard.KEY_LEFT) {
			selectedSubTab = -1;
		} else if (selectedSubTab == -1 && (keycode == Keyboard.KEY_RETURN || keycode == Keyboard.KEY_RIGHT)) {
			selectedSubTab = 0;
		} else if (keycode == Keyboard.KEY_RIGHT) {
			tabs.get(selectedTab).getSubTabs().get(selectedSubTab).press();
		}
		else if (keycode == Keyboard.KEY_RETURN) {
			
		}
	}

	public static void drawRect(int glFlag, int left, int top, int right, int bottom, int color) {
		if (left < right) {
			int i = left;
			left = right;
			right = i;
		}

		if (top < bottom) {
			int j = top;
			top = bottom;
			bottom = j;
		}

		float f3 = (float) (color >> 24 & 255) / 255.0F;
		float f = (float) (color >> 16 & 255) / 255.0F;
		float f1 = (float) (color >> 8 & 255) / 255.0F;
		float f2 = (float) (color & 255) / 255.0F;
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.color(f, f1, f2, f3);
		worldrenderer.startDrawing(glFlag);
		worldrenderer.addVertex((double) left, (double) bottom, 0.0D);
		worldrenderer.addVertex((double) right, (double) bottom, 0.0D);
		worldrenderer.addVertex((double) right, (double) top, 0.0D);
		worldrenderer.addVertex((double) left, (double) top, 0.0D);
		tessellator.draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}

}
