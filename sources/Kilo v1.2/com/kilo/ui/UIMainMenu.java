package com.kilo.ui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.realms.RealmsBridge;

import org.lwjgl.opengl.Display;

import com.kilo.Kilo;
import com.kilo.alt.GuiAccountList;
import com.kilo.manager.DatabaseManager;
import com.kilo.manager.FriendManager;
import com.kilo.mod.ModuleManager;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.inter.IconButton;
import com.kilo.ui.inter.Inter;
import com.kilo.ui.inter.Link;
import com.kilo.ui.inter.slotlist.part.Friend;
import com.kilo.util.Align;
import com.kilo.util.Resources;
import com.kilo.util.ServerUtil;
import com.kilo.util.Util;

public class UIMainMenu extends UI {

	public UIMainMenu(UI parent) {
		super(parent);
	}
	
	@Override
	public void init() {
		ModuleManager.canHack(true);
		inters.clear();
		int i = 0;
		inters.add(new IconButton(this, Display.getWidth()-80-(120*(i++)), 32, 48, 48, Colors.WHITE.c, Resources.iconExit[4]));
		inters.add(new IconButton(this, Display.getWidth()-80-(120*(i++)), 32, 48, 48, Colors.WHITE.c, Resources.iconSettings[4]));
		inters.add(new IconButton(this, Display.getWidth()-80-(120*(i++)), 32, 48, 48, Colors.WHITE.c, Resources.iconMultiplayer[4]));
		inters.add(new IconButton(this, Display.getWidth()-80-(120*(i++)), 32, 48, 48, Colors.WHITE.c, Resources.iconSingleplayer[4]));
		inters.add(new Link(this, "Alts", (Display.getWidth()/2)-(Fonts.ttfRoundedBold14.getWidth(" - Language - ")/2), Display.getHeight()-30, Fonts.ttfRoundedBold14, Colors.WHITE.c, Align.R, Align.B));
		inters.add(new Link(this, "Language", Display.getWidth()/2, Display.getHeight()-30, Fonts.ttfRoundedBold14, Colors.WHITE.c, Align.C, Align.B));
		inters.add(new Link(this, "Forums", (Display.getWidth()/2)+(Fonts.ttfRoundedBold14.getWidth(" - Language - ")/2), Display.getHeight()-30, Fonts.ttfRoundedBold14, Colors.WHITE.c, Align.L, Align.B));
		inters.add(new Link(this, "Minetime Server", 110+Fonts.ttfRoundedBold12.getWidth("Go to "), Display.getHeight()-94+(32)+(Fonts.ttfRoundedBold12.getHeight()/2)+5, Fonts.ttfRoundedBold12, Colors.GREY.c, Align.L, Align.T));
	}
	
	public void update(int mx, int my) {
		super.update(mx, my);
	}
	
	@Override
	public void interact(Inter i) {
		switch (inters.indexOf(i)) {
		case 0:
			mc.shutdown();
			break;
		case 1:
            mc.displayGuiScreen(new GuiOptions(mc.currentScreen, mc.gameSettings));
            break;
		case 2:
            mc.displayGuiScreen(new GuiMultiplayer(mc.currentScreen));
            break;
		case 3:
            mc.displayGuiScreen(new GuiSelectWorld(mc.currentScreen));
            break;
		case 4:
	        mc.displayGuiScreen(new GuiAccountList(mc.currentScreen));
			break;
		case 5:
            mc.displayGuiScreen(new GuiLanguage(mc.currentScreen, mc.gameSettings, mc.getLanguageManager()));
			break;
		case 6:
			Util.openWeb("https://www.minetime.com/members/jawed.25514/");
			break;
		case 7:
			mc.displayGuiScreen(new GuiConnecting(mc.currentScreen, mc, new ServerData("", "minetime.com"+":"+"25565")));
			break;
		}
	}
	
	public void render(float opacity) {
		
		float w = 358;
		float h = 203;
		float x = Math.round((Display.getWidth()/2)-(w/2));
		float y = Math.round((Display.getHeight()/2)-(h/2));
		Draw.rectTexture(x, y, w, h, Resources.branding, Util.reAlpha(0x00FFFFFF, 1f*opacity));
		Draw.string(Fonts.ttfRoundedBold12, x+w+8, y, Kilo.kilo().VERSION_NAME, Colors.WHITE.c);
		
		Draw.rect((Display.getWidth()/2)-110, Display.getHeight()-56, (Display.getWidth()/2)+110, Display.getHeight()-20, Util.reAlpha(0xFF333333, 0.5f));

		Draw.string(Fonts.ttfRoundedBold14, (Display.getWidth()/2)-(Fonts.ttfRoundedBold14.getWidth("- Language -")/2), Display.getHeight()-30-Fonts.ttfRoundedBold14.getHeight(), "-", Util.reAlpha(Colors.WHITE.c, 1f*opacity));
		Draw.string(Fonts.ttfRoundedBold14, (Display.getWidth()/2)+(Fonts.ttfRoundedBold14.getWidth("Language -")/2)-1, Display.getHeight()-30-Fonts.ttfRoundedBold14.getHeight(), "-", Util.reAlpha(Colors.WHITE.c, 1f*opacity));
		
		Draw.string(Fonts.ttfRoundedBold12, Display.getWidth()-20, Display.getHeight()-20-Fonts.ttfRoundedBold12.getHeight(), "Fixed by Jawed - Copyright 2015 KiLO. All Rights Reserved", Util.reAlpha(Colors.GREY.c, 1f*opacity), Align.R, Align.B);
		Draw.string(Fonts.ttfRoundedBold12, Display.getWidth()-20, Display.getHeight()-20, "Minecraft - Copyright Mojang AB. Do not distribute! - Jawed", Util.reAlpha(Colors.GREY.c, 1f*opacity), Align.R, Align.B);
		
		String name = "Kilo 2.0 Fixed by Jawed";
		String nametag = "Welcome to";
		boolean premium = true;
		List<Friend> friends = null;
		List<Friend> friendsOnline = null;

		
		String[] accountInfo = new String[] {nametag+" "+name, " Friends 0 Online)", "Go to Minetime Server"};
		float maxW = 0;
		for(String s : accountInfo) {
			float curW = Fonts.ttfRoundedBold12.getWidth(s);
			if (s.equalsIgnoreCase(accountInfo[0])) {
				curW = Fonts.ttfRoundedBold14.getWidth(s);
			}
			if (curW > maxW) {
				maxW = curW;
			}
		}

		float pad = 10f;
		w = 64;
		h = 64;
		x = 30;
		y = Display.getHeight()-30-h;
		Draw.rect(x-pad, y-pad, x+w+32+maxW, y+h+pad, Util.reAlpha(0xFF333333, 0.5f*opacity));
	
		int a = (int)(255*opacity);
		Draw.string(Fonts.ttfRoundedBold14, x+w+16, y+(h/2)-(Fonts.ttfRoundedBold12.getHeight()/2)-5, nametag+" \u00a7r"+name, Util.reAlpha(0xFFFFFFFF, 1f*opacity), Align.L, Align.B);
	//	Draw.string(Fonts.ttfRoundedBold12, x+w+16, y+(h/2), "0"+" \u00a77Friends"+(" \u00a72("+friendsOnline.size()+" Online)"):""), Util.reAlpha(0xFFCCCCCC, 1f*opacity), Align.L, Align.C);
		Draw.string(Fonts.ttfRoundedBold12, x+w+16, y+(h/2)+(Fonts.ttfRoundedBold12.getHeight()/2)+5, "Go to", Util.reAlpha(0xFFCCCCCC, 1f*opacity), Align.L, Align.T);
		
		if (premium) {
			Draw.rect(x+w+32+maxW, y-10, x+w+32+maxW+w+(pad*2), y+h+pad, Util.reAlpha(Colors.DARKGREEN.c, 1f*opacity));
			Draw.rectTexture(x+w+32+maxW+((w+(pad*2))/2)-24, y-10+((h+(pad*2))/2)-24, 48, 48, Resources.iconShield[4], Util.reAlpha(Colors.YELLOW.c, 1f*opacity));
		}
		
		super.render(opacity);
	}
}
