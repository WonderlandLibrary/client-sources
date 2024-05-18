package com.kilo.ui;

import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.multiplayer.GuiConnecting;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

import com.kilo.Kilo;
import com.kilo.manager.ServerManager;
import com.kilo.manager.UpdateManager;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.inter.Button;
import com.kilo.ui.inter.IconButton;
import com.kilo.ui.inter.Inter;
import com.kilo.ui.inter.Link;
import com.kilo.ui.inter.slotlist.SlotList;
import com.kilo.ui.inter.slotlist.part.Server;
import com.kilo.ui.inter.slotlist.slot.ServerSlot;
import com.kilo.ui.inter.slotlist.slot.Slot;
import com.kilo.util.Align;
import com.kilo.util.Resources;
import com.kilo.util.ServerUtil;
import com.kilo.util.Timer;
import com.kilo.util.Util;

public class UIMultiplayer extends UI {

	private float formOffset;
	private boolean invalid, deleting;
	
	private String invalidMessage = "";
	
	private Timer invalidTimer = new Timer();

	private int selectedIndex;
	private float fX, fY, fW, fH;
	
	public SlotList ssl;
	
	public UIMultiplayer(UI parent) {
		super(parent);
	}
	
	@Override
	public void init() {
		title = "Multiplayer";

		formOffset = 0;
		invalid = false;

		UpdateManager.updateMultiplayerServerList();
		ssl = new SlotList(7f);
		
		fX = Display.getWidth()/2;
		fY = (Display.getHeight()/2)+44;
		fW = Display.getWidth()-40;
		fH = Display.getHeight()-124;

		inters.clear();
		int i = 0;
		inters.add(new IconButton(this, 32+(64*(i++)), 32, 32, 32, Colors.WHITE.c, Resources.iconReturn[3]));
		inters.add(new IconButton(this, 32+(64*(i++)), 32, 32, 32, Colors.WHITE.c, Resources.iconRefresh[3]));
		inters.add(new Button(this, "Direct", fX+(fW/2)-270-8-128-136, fY+(fH/2)-48+8, 128, 32, Fonts.ttfStandard12, Colors.DARKBLUE.c, Resources.iconSubmit[1], 16));
		inters.add(new Button(this, "Add Server", fX+(fW/2)-270-8-128, fY+(fH/2)-48+8, 128, 32, Fonts.ttfStandard12, Colors.GREEN.c, Resources.iconAdd[1], 16));
		inters.add(new Button(this, "Join Server", fX+(fW/2)-270+8, fY+(fH/2)-48+8-40, 270-16, 32, Fonts.ttfStandard12, Colors.DARKBLUE.c, null, 0));
		inters.add(new Button(this, "Move to Top", fX+(fW/2)-270+8, fY+(fH/2)-48+8, ((270-16)/2)-4, 32, Fonts.ttfStandard12, Colors.DARKGREY.c, null, 0));
		inters.add(new Button(this, "Delete", fX+(fW/2)-270+12+((270-16)/2), fY+(fH/2)-48+8, ((270-16)/2)-4, 32, Fonts.ttfStandard12, Colors.DARKRED.c, null, 0));
		
		inters.add(new Link(this, "", fX+(fW/2)-10, fY-(fH/2)+173+(Fonts.ttfRoundedBold12.getHeight()*3f)+(Fonts.ttfRoundedBold14.getHeight()*4), Fonts.ttfRoundedBold12, Colors.BLUE.c, Align.R, Align.T));
		inters.add(new Link(this, "", fX+(fW/2)-10, fY-(fH/2)+173+(Fonts.ttfRoundedBold12.getHeight()*3.5f)+(Fonts.ttfRoundedBold14.getHeight()*5), Fonts.ttfRoundedBold12, Colors.BLUE.c, Align.R, Align.T));
	}
	
	public void update(int mx, int my) {
		super.update(mx, my);
		if (invalid) {
			formOffset+= ((-Fonts.ttfStandard14.getHeight()*2)-formOffset)/5f;
			if (invalidTimer.isTime(2f)) {
				invalid = false;
			}
		} else {
			invalidTimer.reset();
		}
		if (!invalid) {
			formOffset+= (0-formOffset)/5f;
		}

		Server activeServer = null;
		for(Slot s : ssl.slots) {
			if (s.active) {
				activeServer = ServerManager.getServer(((ServerSlot)s).index);
				break;
			}
		}
		((Button)inters.get(4)).enabled = activeServer != null;
		((Button)inters.get(5)).enabled = activeServer != null;
		((Button)inters.get(6)).enabled = activeServer != null;
		((Button)inters.get(6)).text = deleting?(String)null:"Delete";

		ssl.x = fX-(fW/2)+32;
		ssl.y = fY-(fH/2)+32-ssl.oy;
		ssl.w = fW-334;
		ssl.h = fH-112;
		
		if (ssl.slots.size() != ServerManager.getSize()) {
			ssl.slots.clear();
			int i = 0;
			for(Server s : ServerManager.getList()) {
				ssl.slots.add(new ServerSlot(ssl, ServerManager.getIndex(s), ssl.x, ssl.y, ssl.w, 80, 0, i*80));
				i++;
			}
		}
		
		ssl.update(mx, my);

		((Link)inters.get(7)).text = (activeServer != null && activeServer.website != null)?activeServer.website:"";
		((Link)inters.get(8)).text = (activeServer != null && activeServer.webstore != null)?activeServer.webstore:"";
		inters.get(7).x = fX+(fW/2)-10-(inters.get(7).fontAlignH==Align.C?inters.get(7).font.getWidth(((Link)inters.get(7)).text)/2:(inters.get(7).fontAlignH==Align.R?inters.get(7).font.getWidth(((Link)inters.get(7)).text):0));
		inters.get(8).x = fX+(fW/2)-10-(inters.get(8).fontAlignH==Align.C?inters.get(8).font.getWidth(((Link)inters.get(8)).text)/2:(inters.get(8).fontAlignH==Align.R?inters.get(8).font.getWidth(((Link)inters.get(8)).text):0));
	}
	
	@Override
	public void interact(Inter i) {
		Server activeServer = null;
		int index = -1;
		
		int a = 0;
		for(Slot s : ssl.slots) {
			if (s.active) {
				index = a;
				activeServer = ServerManager.getServer(((ServerSlot)s).index);
				break;
			}
			a++;
		}
		
		switch(inters.indexOf(i)) {
		case 0:
			mc.displayGuiScreen(new GuiMainMenu());
			//UIHandler.changeUI(new UIMainMenu(null));
			break;
		case 1:
			ServerManager.loadServers();
			break;
		case 2:
			UIHandler.changeUI(new UIDirectConnect(this));
			break;
		case 3:
			UIHandler.changeUI(new UIAddServer(this));
			break;
		case 4:
			if (activeServer != null) {
				mc.displayGuiScreen(new GuiConnecting(mc.currentScreen, mc, activeServer.serverData));
			}
			break;
		case 5:
			if (ServerManager.getIndex(activeServer) != 0) {
				Server temp = activeServer;
				ServerManager.removeServer(activeServer);
				ServerManager.addServer(0, temp);
				moveToTop(temp);
			}
			break;
		case 6:
			if (activeServer != null) {
				removeServer(activeServer);
			}
			break;
		case 7:
			Util.openWeb("http://"+((Link)i).text);
			break;
		case 8:
			Util.openWeb("http://"+((Link)i).text);
			break;
		}
	}
	
	public void mouseClick(int mx, int my, int b) {
		super.mouseClick(mx, my, b);
		ssl.mouseClick(mx, my, b);
	}
	
	public void mouseRelease(int mx, int my, int b) {
		super.mouseRelease(mx, my, b);
		ssl.mouseRelease(mx, my, b);
	}
	
	public void mouseScroll(int s) {
		super.mouseScroll(s);
		ssl.mouseScroll(s);
	}
	
	public void keyboardPress(int key) {
		Server activeServer = null;
		int index = -1;
		
		int a = 0;
		for(Slot s : ssl.slots) {
			if (s.active) {
				index = a;
				activeServer = ServerManager.getServer(((ServerSlot)s).index);
				break;
			}
			a++;
		}
		super.keyboardPress(key);
		switch (key) {
		case Keyboard.KEY_RETURN:
			if (activeServer != null) {
				mc.displayGuiScreen(new GuiConnecting(mc.currentScreen, mc, activeServer.serverData));
			}
			break;
		case Keyboard.KEY_DELETE:
			if (activeServer != null) {
				removeServer(activeServer);
			}
			break;
		case Keyboard.KEY_UP:
			if (ServerManager.getSize() > 0) {
				if (index > 0) {
					moveServer(activeServer, "up");
				}
			}
			break;
		case Keyboard.KEY_DOWN:
			if (ServerManager.getSize() > 0) {
				if (index < ServerManager.getSize()-1) {
					moveServer(activeServer, "down");
				}
			}
			break;
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
					for(Slot ser : ssl.slots) {
						ser.active = false;
						if (ServerManager.getServer(((ServerSlot)ser).index).ip == temp.ip) {
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
	
	public void moveToTop(final Server s) {
		new Thread() {
			@Override
			public void run() {
				ServerUtil.moveServer(s.ip, "top");
			}
		}.start();
	}
	
	public void removeServer(final Server s) {
		if (!deleting) {
			deleting = true;
			new Thread() {
				@Override
				public void run() {
					String connect = ServerUtil.delServer(s.ip);
					if (connect != null) {
						ServerManager.removeServer(s);
					} else {
						invalidMessage = "Failed to remove server from database";
						invalid = true;
					}
					deleting = false;
				}
			}.start();
		}
		if (formOffset < (-Fonts.ttfStandard14.getHeight()*1.5f)) {
			invalid = false;
		}
	}
	
	public void render(float opacity) {
		drawDarkerBackground(false, opacity);
		
		Draw.string(Fonts.ttfRoundedBold40, Display.getWidth()/2, fY-(fH/2)-(Fonts.ttfRoundedBold40.getHeight(title)/2)-10, title, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.C, Align.C);
		
		Draw.rect(fX-(fW/2), fY-(fH/2), fX+(fW/2)-270, fY+(fH/2), Util.reAlpha(0xFF111111, 1f*opacity));
		Draw.rect(fX+(fW/2)-270, fY-(fH/2), fX+(fW/2), fY+(fH/2), Util.reAlpha(0xFF0A0A0A, 0.75f*opacity));
		
		Draw.rect(fX-(fW/2), fY+(fH/2)-48, fX+(fW/2)-270, fY+(fH/2), Util.reAlpha(0xFF1A1A1A, 1f*opacity));


			//Texture tex = Kilo.kilo().client.minecraftPlayer.head.getTexture();
			//Draw.rectTexture(Display.getWidth()-20-64, 20, 64, 64, tex, 1f*opacity);
			
			Draw.string(Fonts.ttfRoundedBold12, Display.getWidth()-20-64-16, 20+32-(Fonts.ttfRoundedBold20.getHeight()/2), "Logged in as", Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.R, Align.B);
		//	Draw.string(Fonts.ttfRoundedBold20, Display.getWidth()-20-64-16, 20+32, Kilo.kilo().client.minecraftPlayer.gameProfile.getName(), Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.R, Align.C);

		Draw.startClip(fX-(fW/2)+32, fY-(fH/2)+32, fX+(fW/2)-302+ssl.sbw, fY+(fH/2)-80);
		ssl.render(opacity);
		Draw.endClip();
		
		Server ss = null;
		
		for(Slot s : ssl.slots) {
			if (s.active) {
				ss = ServerManager.getServer(((ServerSlot)s).index);
				break;
			}
		}
		
		if (ss != null) {
			Texture t = ss.image.getTexture();
			if (t != null) {
				float w = t.getImageWidth()/(t.getImageWidth()/250f);
				float h = t.getImageHeight()/(t.getImageWidth()/(w));
				Draw.rectTexture(fX+(fW/2)-270+10, fY-(fH/2)+10, w, h, t, 1f*opacity);
			}
			
			float fls = fX+(fW/2)-260;
			float frs = fX+(fW/2)-10;
			float fiy = fY-(fH/2)+170;
			
			Draw.string(Fonts.ttfRoundedBold14, fls, fiy, ss.name != null?ss.name:"Minecraft Server", Util.reAlpha(Colors.GREEN.c, 1f*opacity));
			fiy+= Fonts.ttfRoundedBold14.getHeight()*2;
			
			Draw.string(Fonts.ttfRoundedBold12, fls, fiy, ss.ip, Util.reAlpha(Colors.WHITE.c, 1f*opacity));
			boolean noPing = ss.serverData.serverMOTD.equalsIgnoreCase("can\'t connect to server") || ss.serverData.serverMOTD.equalsIgnoreCase("pinging...") || ss.serverData.serverMOTD.equalsIgnoreCase("can\'t resolve hostname");
			Draw.string(Fonts.ttfRoundedBold12, fls, fiy+Fonts.ttfRoundedBold12.getHeight(), noPing?"":ss.serverData.pingToServer+"ms Ping", Util.reAlpha(Colors.GREY.c, 1f*opacity));
			
			if (ss.serverData.populationInfo.contains("/")) {
				Draw.line(frs-Fonts.ttfRoundedBold12.getWidth(ss.serverData.populationInfo.split("/")[1])+8, fiy+(Fonts.ttfRoundedBold12.getHeight()*2.5f)+(Fonts.ttfRoundedBold14.getHeight()/3), frs-Fonts.ttfRoundedBold12.getWidth(ss.serverData.populationInfo.split("/")[1])-16, fiy+(Fonts.ttfRoundedBold12.getHeight()*3.5f)+(Fonts.ttfRoundedBold14.getHeight()/1.5f), Util.reAlpha(Colors.WHITE.c, 0.5f*opacity), 2f);
				Draw.string(Fonts.ttfRoundedBold14, fls, fiy+(Fonts.ttfRoundedBold12.getHeight()*3), "Online Players:", Util.reAlpha(Colors.WHITE.c, 1f*opacity));
				Draw.string(Fonts.ttfRoundedBold12, frs-Fonts.ttfRoundedBold12.getWidth(ss.serverData.populationInfo.split("/")[1])-8, fiy+(Fonts.ttfRoundedBold12.getHeight()*2.5f)+(Fonts.ttfRoundedBold14.getHeight()/2), ss.serverData.populationInfo.split("/")[0], Util.reAlpha(Colors.GREEN.c, 1f*opacity), Align.R, Align.C);
				Draw.string(Fonts.ttfRoundedBold12, frs, fiy+(Fonts.ttfRoundedBold12.getHeight()*3.5f)+(Fonts.ttfRoundedBold14.getHeight()/2), ss.serverData.populationInfo.split("/")[1], Util.reAlpha(Colors.GREY.c, 1f*opacity), Align.R, Align.C);
			}

			if (ss.website != null && ss.website.length() > 0) {
				Draw.string(Fonts.ttfRoundedBold14, fls, fiy+(Fonts.ttfRoundedBold12.getHeight()*3f)+(Fonts.ttfRoundedBold14.getHeight()*2), "Server Website:", Util.reAlpha(Colors.WHITE.c, 1f*opacity));
			}

			if (ss.webstore != null && ss.webstore.length() > 0) {
				Draw.string(Fonts.ttfRoundedBold14, fls, fiy+(Fonts.ttfRoundedBold12.getHeight()*3.5f)+(Fonts.ttfRoundedBold14.getHeight()*3), "Donation Store:", Util.reAlpha(Colors.WHITE.c, 1f*opacity));
			}
		}
		
		//Message
		Draw.startClip(fX-(Fonts.ttfStandard14.getWidth(invalidMessage)/2), fY+(fH/2)-40+(Fonts.ttfStandard14.getHeight()/2), fX+(Fonts.ttfStandard14.getWidth(invalidMessage)/2), fY+(fH/2)-40+(Fonts.ttfStandard14.getHeight()*1.5f));
		Draw.string(Fonts.ttfStandard14, fX, fY+(fH/2)-40-(Fonts.ttfStandard14.getHeight(invalidMessage))-formOffset, invalidMessage, Util.reAlpha(0xFFFF5555, 1f*opacity), Align.C, Align.C);
		Draw.endClip();
		
		super.render(opacity);
	}
}
