 package de.Hero.clickgui;

 import java.util.ArrayList;

import org.newdawn.slick.Color;

import de.Hero.clickgui.elements.ModuleButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

 public class Panel {
 	public String title;
 	public float x;
 	public float y;
 	private float x2;
 	private float y2;
 	public float width;
 	public float height;
 	public boolean dragging;
 	public boolean extended;
 	public boolean visible;
 	public ArrayList<ModuleButton> Elements = new ArrayList<ModuleButton>();

 	/*
 	 * Konstrukor
 	 */
 	public Panel(String ititle, float ix, float iy, float iwidth, float iheight, boolean iextended) {
 		this.title = ititle;
 		this.x = ix;
 		this.y = iy;
 		this.width = iwidth;
 		this.height = iheight;
 		this.extended = iextended;
 		this.dragging = false;
 		this.visible = true;
 		setup();
 	}

 	/*
 	 * Wird in ClickGUI berschrieben, sodass auch ModuleButtons hinzugefgt werden knnen :3
 	 */
 	public void setup() {}
 	//Farbe Ambien,0xff8A2BE2
 	//Farbe rot 0xffff0000


 	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
 		if (!this.visible)
 			return;

 		if (this.dragging) {
 			x = x2 + mouseX;
 			y = y2 + mouseY;
 		}
 	
 		
 		Gui.drawRect(x  - 2, y, x + width  + 2, y + height, 0xff8A2BE2);
 		Gui.drawRect(x - 2, y, x + width - 80, y + height, 0xffffff);
 		Gui.drawRect(x + 80, y, x + width + 2, y + height, 0xffffff);
 		Gui.drawRect(x - 2 , y + 13, x + width + 2, y + height, 0xffffff);
 		Gui.drawRect(x - 2 , y , x + width + 2, y + height - 13, 0xffffff);
 		double stuffx = 4.0;
 		double stuffxp = 4.3;
 		double stuffy = 2;
 					FontUtils.drawTitleString(title, x + width / 2, y + height / 2 - 3.1F, 0xffffffff);
 			
 		
 		
 		if (this.extended) {
 			float startY = y + height;
 			int epanelcolor = 0xbb151515;
 			for (ModuleButton et : Elements) {
 				
 		 		
 				Gui.drawRect(x, startY, x + width, startY + et.height + 1, 0xff000000);
 				Gui.drawRect(x, startY, x + width - 77, startY + et.height + 1, 0xff000000);
 				Gui.drawRect(x + 77, startY, x + width , startY + et.height + 1, 0xffffff);
 				et.x = x + 2;
 				et.y = startY;
 				et.width = width - 4;
 				et.drawScreen(mouseX, mouseY, partialTicks);
 				startY += et.height + 1;
 	
 			}
 			Gui.drawRect(x, startY, x + width , startY  + 3,0xff8A2BE2);

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
