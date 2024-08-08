package me.xatzdevelopments.clickgui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import me.xatzdevelopments.Xatz;
import me.xatzdevelopments.clickgui.elements.ModuleButton;
import me.xatzdevelopments.util.ColorUtil;
import me.xatzdevelopments.util.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;


/**
 *  Made by HeroCode
 *  it's free to use
 *  but you have to credit me
 *
 *  @author HeroCode
 */
public class Panel {
	public String title;
	public double x;
	public double y;
	private double x2;
	private double y2;
	public double width;
	public double height;
	public boolean dragging;
	public boolean extended;
	public boolean visible;
	public ArrayList<ModuleButton> Elements = new ArrayList<>();
	public ClickGUI clickgui;

	/*
	 * Konstrukor
	 */
	public Panel(String ititle, double ix, double iy, double iwidth, double iheight, boolean iextended, ClickGUI parent) {
		this.title = ititle;
		this.x = ix;
		this.y = iy;
		this.width = iwidth;
		this.height = iheight;
		this.extended = iextended;
		this.dragging = false;
		this.visible = true;
		this.clickgui = parent;
		setup();
	}

	/*
	 * Wird in ClickGUI �berschrieben, sodass auch ModuleButtons hinzugef�gt werden k�nnen :3
	 */
	public void setup() {}

	/*
	 * Rendern des Elements.
	 */
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		if (!this.visible)
			return;
		//System.out.println(title + x);
		//System.out.println(title + y);
		if (this.dragging) {
			x = x2 + mouseX;
			y = y2 + mouseY;
		}
		
		Color temp = ColorUtil.getClickGUIColor().darker();
		int outlineColor = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 170).getRGB();
		
		Gui.drawRect(x, y, x + width, y + height, 0xff121212);
		if(Xatz.getModuleByName("ClickGui").getModeSetting("Design").getMode().equals("New")){
			Gui.drawRect(x - 2, y, x, y + height, outlineColor);
			Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(title, (float) (x + 2), y + height / 2 - Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT/2, 0xffefefef);
		}else if(Xatz.getModuleByName("ClickGui").getModeSetting("Design").getMode().equals("Jelly")){
			Gui.drawRect(x + 4,			y + 2, x + 4.3, 		y + height - 2, 0xffaaaaaa);
			Gui.drawRect(x - 4 + width, y + 2, x - 4.3 + width, y + height - 2, 0xffaaaaaa);
			FontUtil.drawTotalCenteredStringWithShadow(title, x + width / 2, y + height / 2, 0xffefefef);
		}
		
		if (this.extended && !Elements.isEmpty()) {
			double startY = y + height;
			int epanelcolor = 0;
			if(Xatz.getModuleByName("ClickGui").getModeSetting("Design").getMode().equals("New")) {
				epanelcolor = 0xff232323;
			}else if(Xatz.getModuleByName("ClickGui").getModeSetting("Design").getMode().equals("Jelly")) {
				epanelcolor = 0xbb151515;
			}
			
			for (ModuleButton et : Elements) {
				if(Xatz.getModuleByName("ClickGui").getModeSetting("Design").getMode().equals("New")){
					Gui.drawRect(x - 2, startY, x + width, startY + et.height + 1, outlineColor);
				}
				Gui.drawRect(x, 	startY, x + width, startY + et.height + 1, epanelcolor);
				et.x = x + 2;
				et.y = startY;
				et.width = width - 4;
				et.drawScreen(mouseX, mouseY, partialTicks);
				startY += et.height + 1;
			}
			Gui.drawRect(x, startY + 1, x + width, startY + 1, epanelcolor);
		
		}
	}

	/*
	 * Zum Bewegen und Extenden des Panels
	 * usw.
	 */
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (!this.visible) {
			return false;
		}
		if (mouseButton == 0 && isHovered(mouseX, mouseY)) {
			x2 = this.x - mouseX;
			y2 = this.y - mouseY;
			dragging = true;
			return true;
		} else if (mouseButton == 1 && isHovered(mouseX, mouseY)) {
			extended = !extended;
			return true;
		} else if (extended) {
			for (ModuleButton et : Elements) {
				if (et.mouseClicked(mouseX, mouseY, mouseButton)) {
					return true;
				}
			}
		}
		return false;
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
