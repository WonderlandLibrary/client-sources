package com.kilo.ui;

import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.util.ChatComponentText;

import org.lwjgl.opengl.Display;

import com.kilo.Kilo;
import com.kilo.manager.ChatManager;
import com.kilo.mod.ModuleManager;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.inter.Button;
import com.kilo.ui.inter.Inter;
import com.kilo.util.Align;
import com.kilo.util.ServerUtil;
import com.kilo.util.Util;

public class UIConnecting extends UI {

	private String ip;
	
	public UIConnecting(UI parent, String ip) {
		super(parent);
		this.ip = ip;
	}
	
	@Override
	public void init() {
		title = "Connecting to";
    	ChatManager.chatLines.clear();
		
		if (ip != null && ip.length() > 0) {
			String i = ip;
			new Thread() {
				@Override
				public void run() {
					ModuleManager.canHack(ServerUtil.allowedToHack(Kilo.kilo().client.clientID, ip));
				}
			}.start();
		}
		
		inters.clear();
		inters.add(new Button(this, "Cancel", Display.getWidth()/2-200, Display.getHeight()-128, 400, 64, Fonts.ttfRoundedBold25, Colors.RED.c, null, 0));
	}
	
	@Override
	public void interact(Inter i) {
		switch(inters.indexOf(i)) {
		case 0:
			if (((GuiConnecting)mc.currentScreen).networkManager != null) {
				((GuiConnecting)mc.currentScreen).networkManager.closeChannel(new ChatComponentText("Aborted"));
            }

            mc.displayGuiScreen(((GuiConnecting)mc.currentScreen).previousGuiScreen);
			break;
		}
	}
	
	public void render(float opacity) {
		
		Draw.string(Fonts.ttfRoundedBold20, Display.getWidth()/2, Display.getHeight()-192, title, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.C, Align.B);
		Draw.string(Fonts.ttfRoundedBold20, Display.getWidth()/2, Display.getHeight()-192, ip, Util.reAlpha(Colors.DARKBLUE.c, 1f*opacity), Align.C, Align.T);
		
		Draw.loader(Display.getWidth()/2, Display.getHeight()/2, 32, Util.reAlpha(Colors.WHITE.c, 1f*opacity));
		
		super.render(opacity);
	}
}
