package de.Hero.clickgui;

import de.Hero.clickgui.elements.ModuleButton;
import de.Hero.clickgui.util.ColorUtil;
import de.Hero.clickgui.util.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Mouse;
import zamorozka.gui.GuiIngameHook;
import zamorozka.main.Zamorozka;
import zamorozka.ui.Colors;
import zamorozka.ui.RenderingUtils;

import java.awt.*;
import java.util.ArrayList;

/**
 * Made by HeroCode it's free to use but you have to credit me
 *
 * @author HeroCode
 */
public class Panel {
	public static double width;
	public static double height;
	public String title;
	public double x;
	public double y;
	public boolean dragging;
	public boolean extended;
	public boolean visible;
	public ArrayList<Integer> eside = new ArrayList<>();
	public ArrayList<ModuleButton> Elements = new ArrayList<>();
	public ClickGUI clickgui;
	int delay;
	float deltaY = 0;
	private double x2;
	private double y2;

	/*
	 * Konstrukor
	 */
	public Panel(String ititle, double ix, double iy, double iwidth, double iheight, boolean iextended,
			ClickGUI parent) {
		this.title = ititle;
		this.x = ix;
		this.y = iy;
		width = iwidth;
		height = iheight;
		this.extended = iextended;
		this.dragging = false;
		this.visible = true;
		this.clickgui = parent;

		setup();
	}

	/*
	 * Rendern des Elements.
	 */

	public static Color setRainbow(double d, float fade) {

		float hue = (float) (System.nanoTime() * -5L + d) / 1.0E10F % 1.0F;
		long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1.0F, 1.0F)).intValue()),
				16);
		Color c = new Color((int) color);
		return new Color(c.getRed() / 255.0F * fade, c.getGreen() / 255.0F * fade, c.getBlue() / 255.0F * fade);

	}

	/*
	 * Wird in ClickGUI berschrieben, sodass auch ModuleButtons hinzugefgt werden
	 * knnen :3
	 */
	public void setup() {
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {

		if (!this.visible)
			return;

		if (this.dragging) {
			x = x2 + mouseX;
			y = y2 + mouseY;

		}

		Color temp = ColorUtil.getClickGUIColor().darker();
		int outlineColor = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 170).getRGB();

		// Gui.drawRect(x, y, x + width, y + height, 0x5050506);

		double startY = y2 + height * 8;

		if (this.extended && !Elements.isEmpty()) {

			/**
			 * Gui.drawRect(Panel.width *4.2, Panel.height*12, Panel.width*4.3,
			 * Panel.height*3.45, 0xFFBA925F);
			 * if(mouseY<Panel.height*12&&mouseY>Panel.height*3.45) {
			 * Gui.drawRect(Panel.width *4.2, mouseY, Panel.width*4.3, Panel.height*4.7,
			 * 0xFF000008); }else { Gui.drawRect(Panel.width *4.5, Panel.height*12,
			 * Panel.width*4.3, Panel.height*4.55, 0xFF000008); }
			 */
			int dw = Mouse.getDWheel();
			float amountScrolled = (float) (dw * -.1);
			deltaY += amountScrolled;
			y2 -= deltaY * Minecraft.frameTime / 10;
			deltaY = (float) (deltaY * Math.pow(.97, Minecraft.frameTime));

			if (!Double.isFinite(deltaY))
				deltaY = 0;
			if (deltaY > 100)
				deltaY = 100;
			if (deltaY < -100)
				deltaY = -100;

			Elements.sort(new ElementSorter());
			for (ModuleButton et : Elements) {

				et.x = width * 1.86;
				et.y = startY - 161;
				et.width = width * 3 / 1.62;
				if (y2 > 0) {
					y2 = 0;
				}
//					if(y2 < -50 - ClickGUI.maxOffset) {
//						y2 = -50 - ClickGUI.maxOffset;
//					}

				startY += et.height;
				et.drawScreen(width / 6F, height / 6F, 1);
			}

//				Gui.drawRect(x-1, y+22, x + width-3, y + height, 0xffffffff);
			RenderingUtils.drawRoundedRect(x - 1, y + 23, x + width - 3, y + height, Zamorozka.getClientColor(),
					Zamorozka.getClientColor());
		}

		/**
		 * int dw = Mouse.getEventDWheel(); if(dw != 0) { if (dw > 0) { dw = -1; } else
		 * { dw = 1; } float amountScrolled = (float) (dw * 10); if (et.y +
		 * amountScrolled > 0) et.y += amountScrolled; else et.y = 0; }
		 **/

		// RenderUtils.drawPic(width /4.9, height/5.7, width/15.9, height/15.9, new
		// ResourceLocation("sword.png"));
		// RenderUtils.drawPic(width /4.9, height/5.9, width/15.9, height/15.9, new
		// ResourceLocation("sword.png"));

		Zamorozka.FONT_MANAGER.arraylist4.drawStringWithShadow(title, x + 5,
				y + height / 2 - FontUtil.getFontHeight() / 2, 0xffefefef);

		// Gui.drawRect(width *11.5, height*2.4, width*2.26, height*3.4, 0xFF043792);

	}
	/*
	 * Zum Bewegen und Extenden des Panels usw.
	 */

	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
		ArrayList<Integer> e = new ArrayList<>();
		if (!this.visible) {
			return false;
		}
		if ((mouseButton == 0 || mouseButton == 1) && isHovered(mouseX, mouseY)) {
			extended = !extended;
			return false;
		} else if (extended) {
			for (ModuleButton et : Elements) {
				if (et.mouseClicked(mouseX, mouseY, mouseButton)) {
					return true;
				}

			}
		}
		if (isExtended()) {
			extended = false;
		}

		return false;
	}

	public int getX() {
		return (int) this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public String getName() {
		return this.title;
	}

	public int getY() {
		return (int) this.y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isExtended() {
		return this.extended;
	}

	public void setExtended(boolean extended) {
		this.extended = extended;
	}

	/*
	 * Damit das Panel auch losgelassen werden kann
	 */
	public void mouseReleased(int mouseX, int mouseY, int state) {
		if (!this.visible) {
			return;
		}
		if (state == 0) {
			this.dragging = false;
		}
	}

	/*
	 * HoverCheck
	 */
	public boolean isHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
	}
}
