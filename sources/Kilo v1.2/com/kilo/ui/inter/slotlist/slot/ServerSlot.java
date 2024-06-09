package com.kilo.ui.inter.slotlist.slot;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.GuiConnecting;

import com.kilo.manager.ServerManager;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.inter.slotlist.SlotList;
import com.kilo.ui.inter.slotlist.part.Server;
import com.kilo.util.Align;
import com.kilo.util.ChatUtil;
import com.kilo.util.Resources;
import com.kilo.util.ServerUtil;
import com.kilo.util.Timer;
import com.kilo.util.Util;

public class ServerSlot extends Slot {

	private final Minecraft mc = Minecraft.getMinecraft();
	
	public float moveUpFade, moveDownFade;
	public boolean moveUp, moveDown;
	
	public int index;
	
	public int clicks = 0;
	public Timer clickTimer = new Timer();
	
	public ServerSlot(SlotList p, int i, float x, float y, float w, float h, float ox, float oy) {
		super(p, x, y, w, h, ox, oy);
		index = i;
		
		clickTimer.reset();
		
		inters.clear();
	}
	
	public void update(int mx, int my) {
		super.update(mx, my);
		hover = mouseOver(mx, my) && parent.mouseOver(mx, my);

		float pad = 4;
		moveUp = (mx >= x+4 && my > y && mx < x+20 && my <= y+(height/2)) && index > 0;
		moveDown = (mx >= x+4 && my > y+(height/2) && mx < x+20 && my <= y+height) && index < ServerManager.getSize()-1;
		
		moveUpFade+= ((moveUp?1:0)-moveUpFade)/2;
		moveDownFade+= ((moveDown?1:0)-moveDownFade)/2;
		
		if (clicks == 2) {
			if (ServerManager.getSize() > 0) {
				if (ServerManager.getServer(index) != null) {
					mc.displayGuiScreen(new GuiConnecting(mc.currentScreen, mc, ServerManager.getServer(index).serverData));
				}
			}
			clicks = 0;
		}
		
		if (clickTimer.isTime(Util.doubleClickTimer)) {
			clickTimer.reset();
			clicks = 0;
		}
	}
	
	public void mouseClick(int mx, int my, int b) {
		if (parent.mouseOver(mx, my)) {
			active = hover;
		}
		
		if (hover) {
			if (clicks == 0) {
				clickTimer.reset();
			}
			clicks++;
		}
		
		if (moveUp) {
			clicks = 0;
			moveServer(ServerManager.getServer(index), "up");
		}
		if (moveDown) {
			clicks = 0;
			moveServer(ServerManager.getServer(index), "down");
		}
	}
	
	public void moveServer(final Server s, final String method) {
		new Thread() {
			@Override
			public void run() {
				int index = ServerManager.getIndex(s);
				try {
					Server temp = s;
					ServerManager.removeServer(s);
					ServerManager.addServer(index+(method.equalsIgnoreCase("up")?-1:1), temp);
					for(Slot s : parent.slots) {
						ServerSlot ser = (ServerSlot)s;
						ser.active = false;
						if (ServerManager.getServer(ser.index).ip == temp.ip) {
							ser.active = true;
						}
					}
					ServerUtil.moveServer(s.ip, method);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	public void render(float opacity) {
		Draw.rect(x, y, x+width, y+height, Util.reAlpha(0xFF1A1A1A, activeOpacity*opacity));
		Draw.rect(x, y, x+width, y+height, Util.reAlpha(0xFF0A0A0A, hoverOpacity*opacity));
		if (ServerManager.getServer(index) != null) {
			boolean noIcon = true;

			if (noIcon) {
				Draw.loader(x+56, y+40, 8, Util.reAlpha(Colors.WHITE.c, 1f*opacity));
			}
			
			if (ServerManager.getServer(index).serverData != null && ServerManager.getServer(index).serverData.serverMOTD != null) {
				if (ServerManager.getServer(index).serverData.serverMOTD.equalsIgnoreCase("can\'t resolve hostname") || ServerManager.getServer(index).serverData.serverMOTD.equalsIgnoreCase("Pinging...") || ServerManager.getServer(index).serverData.serverMOTD.equalsIgnoreCase("can\'t connect to server")) {
					Draw.string(Fonts.ttfRoundedBold14, x+104+((width-104)/2), y+40, "\u00a78"+ServerManager.getServer(index).serverData.serverMOTD.replace(".","")+": \u00a77"+(ServerManager.getServer(index).ip.contains(":25565")?ServerManager.getServer(index).ip.replace(":25565", ""):ServerManager.getServer(index).ip), Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.C, Align.C);
				} else {
					if (ServerManager.getServer(index).serverData.serverMOTD.length() == 0) {
						//Draw.string(Fonts.ttfComBold14, x+104+((width-104)/2), y+40, "\u00a78Loading server details", Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.C, Align.C);
						Draw.loader(x+104+((width-104)/2), y+40, 8, Util.reAlpha(Colors.WHITE.c, 1f*opacity));
					} else {
						String[] motdLines = ServerManager.getServer(index).serverData.serverMOTD.split("\n");
						int k = 0;
						for(String motd : motdLines) {
							Draw.string(Fonts.ttfRoundedBold14, x+104, y+40-((Fonts.ttfRoundedBold14.getHeight()*motdLines.length)/2)+(Fonts.ttfRoundedBold14.getHeight()*k), motd, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.L, Align.T);
							k++;
						}
					}
				}
			}
			
			if (ServerManager.getServer(index).serverData.populationInfo != null) {
				ServerManager.getServer(index).serverData.populationInfo = ChatUtil.clearFormat(ServerManager.getServer(index).serverData.populationInfo);
				String[] parts = ServerManager.getServer(index).serverData.populationInfo.split("/");
				if (parts.length == 2) {
					Draw.string(Fonts.ttfRoundedBold12, x+width-32, y+(height/2), parts[0]+"\u00a78/"+parts[1], Util.reAlpha(Colors.BLUE.c, 1f*opacity), Align.R, Align.C);
					Draw.rectTexture(x+width-24, y+(height/2)-4, 8, 8, Resources.iconUser[0], Util.reAlpha(Colors.BLUE.c, 1f*opacity));
				}
			}
			if (index > 0) {
				Draw.rect(x+4, y, x+20, y+(height/2), Util.reAlpha(Colors.DARKBLUE.c, (moveUpFade)*opacity));
				Draw.rectTexture(x+8, y+36-12, 8, 8, Resources.iconArrowUp[0]);
			}
			
			if (index < ServerManager.getSize()-1) {
				Draw.rect(x+4, y+(height/2), x+20, y+height, Util.reAlpha(Colors.DARKBLUE.c, (moveDownFade)*opacity));
				Draw.rectTexture(x+8, y+36+12, 8, 8, Resources.iconArrowDown[0]);
			}
		}
		super.render(opacity);
	}
	
}
