package com.kilo.mod.toolbar.dropdown;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.Timer;

import com.kilo.mod.toolbar.Positionable;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.UIHacks;
import com.kilo.ui.UIHandler;
import com.kilo.users.Player;
import com.kilo.users.UserHandler;
import com.kilo.util.Align;
import com.kilo.util.Util;

public class Window extends Positionable {

	public String title;
	public float toX, toY, toWidth, toHeight, maxWidth, maxHeight, boxFade, interactablesFade, arrowX;
	public boolean enabled = true;
	protected boolean showInteractables;
	protected int contentParent, newContentParent;
	protected Window parent;
	public Timer exist = new Timer();
	public float existFor = 0.1f;
	
	protected float padding = 4f;
	
	public List<Interactable> interactables = new ArrayList<Interactable>();
	
	public Window(Window parent) {
		this.parent = parent;
		this.contentParent = -1;
		this.newContentParent = this.contentParent;
	}
	public void keyboardPress(int key) {}
	public void keyboardRelease(int key) {}
	public void updateInteractables() {}
	public void action(Interactable i, int mouseX, int mouseY, int button) {}
	
	public void update(int mouseX, int mouseY) {
		y = 80;
		toY = 80;
		if (!enabled || !active) {
			exist.reset();
			return;
		}
		exist.tick();
		if (exist.getTime() > existFor) {
			exist.set(existFor);
		}
	}
	
	public void translate(float x, float y) {
		if (!enabled) { return; }

		toX = x;
		toY = y;

		if (toX < 16+(toWidth/2)) {
			toX = 16+(toWidth/2);
		}
		if (toX > Display.getWidth()-(toWidth/2)-16) {
			toX = Display.getWidth()-(toWidth/2)-16;
		}
		
		if ((toX < 16 && toX > Display.getWidth()-(toWidth/2)-16) || toWidth > Display.getWidth()-32) {
			toX = Display.getWidth()/2;
		}
	}
	
	public void resize(float width, float height) {
		if (!enabled) { return; }
		
		this.toWidth = width;
		this.toHeight = height;
	}
	
	public void mouseClick(int mouseX, int mouseY, int button) {
		if (!enabled || exist.getTime() < existFor) { return; }
		
		for(Interactable i : interactables) {
			i.mouseClicked(mouseX, mouseY, button);
		}
	}
	public void mouseRelease(int mouseX, int mouseY, int button) {
		if (!enabled || exist.getTime() < existFor) {return; }
		
		for(Interactable i : interactables) {
			i.mouseReleased(mouseX, mouseY, button);
		}
	}
	
	public boolean mouseOver(int mouseX, int mouseY) {
		return enabled && active && mouseX > (x-(width/2)-padding) && mouseX < x+(width/2)+padding && mouseY > y && mouseY < y+height+padding;
	}
	
	public void updateContentParent(int index) {
		this.newContentParent = index;
		updateInteractables();
	}
	
	public void updateContent() {
		this.x+= (toX-x)/UIHacks.transitionSpeed;
		this.y+= (toY-y)/UIHacks.transitionSpeed;
		this.width+= (toWidth-width)/UIHacks.transitionSpeed;
		this.height+= (toHeight-height)/UIHacks.transitionSpeed;
		
		if (contentParent != newContentParent) {
			updateInteractables();
		}
	}
	
	public void render(float transparency) {
		if (!enabled) { return; }
		
		float xx = arrowX;
		if (this instanceof WindowModule) {
			xx = (Display.getWidth()/2)-((((UIHacks)UIHandler.currentUI).toolbarComponents.size()-2)*32)+(contentParent*64)+32;
			arrowX+= (xx-arrowX)/UIHacks.transitionSpeed;
		} else {
			xx = parent.x;
			arrowX = -100;
		}
		
		Draw.triangle(arrowX-12, y, arrowX, y-12, arrowX+12, y, Util.reAlpha(Colors.BLACK.c, boxFade*transparency));
		Draw.rect(x-(width/2)-padding, y, x+(width/2)+padding, y+height+padding, Util.reAlpha(Colors.BLACK.c, boxFade*transparency));

		Draw.startClip(x-(width/2)-padding, y, x+(width/2)+padding, y+height+padding);
		
		if (interactablesFade > 0.01f) {
			Draw.string(Fonts.ttfRoundedBold14, x, y+16, title.substring(0, 1).toUpperCase()+title.substring(1, title.length()).toLowerCase(), Util.reAlpha(Colors.BLUE.c, interactablesFade*transparency), Align.C, Align.C);
		}

		Draw.startClip(x-(width/2), y+24, x+(width/2), y+height);
		for(Interactable i : interactables) {
			i.render(interactablesFade*transparency);
			if (i instanceof TextBox) {
				switch (((TextBox)i).rel) {
				case FOLLOW:
					if (i.active) { 
						String text = ((TextBox)i).text;
						if (text.length() > 0) {
							List<String> players = new ArrayList<String>();
							
							for(Object o : Minecraft.getMinecraft().thePlayer.sendQueue.func_175106_d()) {
								NetworkPlayerInfo npi = (NetworkPlayerInfo)o;
								String n = npi.getGameProfile().getName();
								if (n.toLowerCase().startsWith(text.toLowerCase()) && !n.equalsIgnoreCase(text)) {
									players.add(n);
								}
							}
							
							if (players.size() > 0) {
								Draw.rect(i.x-4, i.y+i.height, i.x+i.width+4, i.y+i.height+(players.size()*Fonts.ttfRoundedBold12.getHeight())+8, Util.reAlpha(Colors.BLACK.c, boxFade*transparency));
								for(String p : players) {
									Draw.string(Fonts.ttfRoundedBold12, i.x+2, i.y+i.height+4+(players.indexOf(p)*Fonts.ttfRoundedBold12.getHeight()), p, Util.reAlpha(Colors.WHITE.c, boxFade*transparency));
								}
							}
						}
					}
					break;
				}
			}
		}
		Draw.endClip();
		
		Draw.endClip();
	}
}
