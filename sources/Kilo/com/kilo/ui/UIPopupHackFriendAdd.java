package com.kilo.ui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.network.NetworkPlayerInfo;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.kilo.manager.HackFriendManager;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.inter.Button;
import com.kilo.ui.inter.IconButton;
import com.kilo.ui.inter.Inter;
import com.kilo.ui.inter.TextBox;
import com.kilo.ui.inter.TextBoxAlt;
import com.kilo.ui.inter.slotlist.part.HackFriend;
import com.kilo.util.Align;
import com.kilo.util.Config;
import com.kilo.util.Resources;
import com.kilo.util.Util;

public class UIPopupHackFriendAdd extends UI {

	private float formOffset;
	
	private float fX, fY, fW, fH;
	private int acSelect;
	
	public UIPopupHackFriendAdd(UI parent) {
		super(parent);
	}
	
	@Override
	public void init() {
		title = "Add Friend";

		formOffset = 0;

		fX = Display.getWidth()/2;
		fY = (Display.getHeight()/2);
		fW = 350;
		fH = 250;

		inters.clear();
		inters.add(new IconButton(this, fX+(fW/2)-40, fY-(fH/2)+12, 24, 24, 0x00000000, Resources.iconClose[2]));
		inters.add(new Button(this, "Add", fX+(fW/2)-136, fY+(fH/2)-48, 120, 32, Fonts.ttfRoundedBold14, Colors.GREEN.c, Resources.iconSubmit[1], 16, Align.L, Align.C));
		inters.add(new TextBoxAlt(this, "Enter name...", fX-(fW/2)+24, fY-(fH/2)+80, fW-48, 32, Fonts.ttfRoundedBold20, -1, Align.L, Align.C));
		inters.add(new TextBoxAlt(this, "Enter nickname...", fX-(fW/2)+24, fY-(fH/2)+80+48, fW-48, 32, Fonts.ttfRoundedBold20, -1, Align.L, Align.C));
	}
	
	public void update(int mx, int my) {
		super.update(mx, my);
		
		String name = ((TextBox)inters.get(2)).text;
		
		inters.get(1).enabled = name.length() != 0;
	}
	
	@Override
	public void interact(Inter i) {
		switch(inters.indexOf(i)) {
		case 0:
			((UIFriends)parent).changePopup(null);
			break;
		case 1:
			submit();
			break;
		}
	}
	
	public void mouseClick(int mx, int my, int b) {
		super.mouseClick(mx, my, b);
	}
	
	public void mouseRelease(int mx, int my, int b) {
		super.mouseRelease(mx, my, b);
	}
	
	public void mouseScroll(int s) {
		super.mouseScroll(s);
	}
	
	public void keyboardPress(int key) {
		super.keyboardPress(key);
		switch (key) {
		case Keyboard.KEY_DOWN:
			acSelect++;
			break;
		case Keyboard.KEY_UP:
			acSelect--;
			break;
		case Keyboard.KEY_RETURN:
			if (acSelect != -1) {
				if (((TextBox)inters.get(2)).getText().length() > 0 && (inters.get(2).active)) {
					String ac = ((TextBox)inters.get(2)).getText();
					List<String> acnames = new ArrayList<String>();
					for(Object o : mc.thePlayer.sendQueue.func_175106_d()) {
						NetworkPlayerInfo npi = (NetworkPlayerInfo)o;
						String n = npi.getGameProfile().getName();
						if (n.toLowerCase().startsWith(ac.toLowerCase()) && !n.equalsIgnoreCase(ac)) {
							acnames.add(n);
						}
					}
					String name = acnames.get(acSelect);
					name = name.substring(((TextBox)inters.get(2)).text.length(), name.length());
					
					((TextBox)inters.get(2)).setText(((TextBox)inters.get(2)).text+name);
					((TextBox)inters.get(2)).cursorPos+= name.length();
					((TextBox)inters.get(2)).startSelect = ((TextBox)inters.get(2)).cursorPos;
					((TextBox)inters.get(2)).endSelect = ((TextBox)inters.get(2)).cursorPos;
				}
			} else {
				submit();
			}
			break;
		case Keyboard.KEY_ESCAPE:
			((UIFriends)parent).changePopup(null);
			break;
		}
	}
	
	public void keyTyped(int key, char keyChar) {
		super.keyTyped(key, keyChar);
	}
	
	public void submit() {
		String name = ((TextBox)inters.get(2)).text;
		String nickname = ((TextBox)inters.get(3)).text;
		
		HackFriendManager.addHackFriend(new HackFriend(name, nickname));
		((UIFriends)parent).changePopup(null);
		
		Config.saveHackFriends();
	}
	
	public void render(float opacity) {
		Draw.rect(0, 0, Display.getWidth(), Display.getHeight(), Util.reAlpha(Colors.BLACK.c, 0.7f*opacity));
		
		Draw.rect(fX-(fW/2), fY-(fH/2)+48, fX+(fW/2), fY+(fH/2), Util.reAlpha(0xFF202020, 1f*opacity));
		
		Draw.rect(fX-(fW/2), fY-(fH/2), fX+(fW/2), fY-(fH/2)+48, Util.reAlpha(Colors.GREEN.c, 1f*opacity));
		
		Draw.string(Fonts.ttfRoundedBold25, fX, fY-(fH/2)+24, title, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.C, Align.C);
		
		if (((TextBox)inters.get(2)).getText().length() > 0 && (inters.get(2).active)) {
			String ac = ((TextBox)inters.get(2)).getText();
			List<String> acnames = new ArrayList<String>();
			for(Object o : mc.thePlayer.sendQueue.func_175106_d()) {
				NetworkPlayerInfo npi = (NetworkPlayerInfo)o;
				String n = npi.getGameProfile().getName();
				if (n.toLowerCase().startsWith(ac.toLowerCase()) && !n.equalsIgnoreCase(ac)) {
					acnames.add(n);
				}
			}
			
			if (!acnames.isEmpty()) {
				acSelect = Math.min(Math.max(0, acSelect), acnames.size()-1);
				
				float xx = ((TextBox)inters.get(2)).x;
				float yy = ((TextBox)inters.get(2)).y;
				float ww = ((TextBox)inters.get(2)).width;
				float hh = ((TextBox)inters.get(2)).height;
				
				Draw.rect(xx-4, yy+hh, xx+ww+4, yy+hh+((acnames.size())*Fonts.ttfRoundedBold14.getHeight()), Util.reAlpha(Colors.BLACK.c, 0.8f*opacity));
				for(int i = 0; i < acnames.size(); i++) {
					Draw.string(Fonts.ttfRoundedBold14, xx+2, yy+hh+((i)*Fonts.ttfRoundedBold14.getHeight()), acnames.get(i), Util.reAlpha(acSelect == i?Colors.GREEN.c:Colors.WHITE.c, 1f*opacity));
				}
			} else {
				acSelect = -1;
			}
		} else {
			acSelect = -1;
		}
		
		super.render(opacity);
	}
}
