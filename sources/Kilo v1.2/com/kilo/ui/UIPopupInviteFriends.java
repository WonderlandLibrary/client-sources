package com.kilo.ui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.network.NetworkPlayerInfo;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.kilo.Kilo;
import com.kilo.manager.ActivityManager;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.inter.Button;
import com.kilo.ui.inter.IconButton;
import com.kilo.ui.inter.Inter;
import com.kilo.ui.inter.TextBox;
import com.kilo.ui.inter.TextBoxAlt;
import com.kilo.users.Player;
import com.kilo.users.UserHandler;
import com.kilo.util.Align;
import com.kilo.util.Resources;
import com.kilo.util.ServerUtil;
import com.kilo.util.Timer;
import com.kilo.util.Util;

public class UIPopupInviteFriends extends UI {

	private float formOffset;
	private boolean invalid, deleting;
	
	private String invalidMessage = "";
	
	private Timer invalidTimer = new Timer();
	
	private boolean checking;
	
	private float fX, fY, fW, fH;
	private int acSelect;
	
	public UIPopupInviteFriends(UI parent) {
		super(parent);
	}
	
	@Override
	public void init() {
		title = "Invite Friend";

		formOffset = 0;
		invalid = false;
		checking = false;

		fX = Display.getWidth()/2;
		fY = (Display.getHeight()/2);
		fW = 550;
		fH = 250;

		inters.clear();
		inters.add(new IconButton(this, fX+(fW/2)-40, fY-(fH/2)+12, 24, 24, 0x00000000, Resources.iconClose[2]));
		inters.add(new Button(this, "Invite", fX+(fW/2)-136, fY+(fH/2)-48, 120, 32, Fonts.ttfRoundedBold14, Colors.GREEN.c, Resources.iconSubmit[1], 16, Align.L, Align.C));
		inters.add(new TextBoxAlt(this, "Enter ingame name...", fX-(fW/2)+48+fH-80, fY-(fH/2)+64, fW-(48+fH-80)-32, 32, Fonts.ttfRoundedBold20, -1, Align.L, Align.C));
		inters.add(new TextBoxAlt(this, "Invite message...", fX-(fW/2)+48+fH-80, fY-(fH/2)+64+32+16, fW-(48+fH-80)-32, 32, Fonts.ttfRoundedBold20, -1, Align.L, Align.C));
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
		
		((Button)inters.get(1)).text = checking?(String)null:"Invite";
	}
	
	@Override
	public void interact(Inter i) {
		switch(inters.indexOf(i)) {
		case 0:
			((UIInGameMenu)parent).changePopup(null);
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
			((UIInGameMenu)parent).changePopup(null);
			break;
		}
	}
	
	public void keyTyped(int key, char keyChar) {
		super.keyTyped(key, keyChar);
	}
	
	public void submit() {
		final String name = ((TextBox)inters.get(2)).text;
		final String msg = ((TextBox)inters.get(3)).text;
		
		if (name != null && name.length() > 0) {
			checking = true;
			new Thread() {
				@Override
				public void run() {
					if (ServerUtil.sendServerInvite(Kilo.kilo().client.clientID, name, mc.getCurrentServerData().serverIP, msg)) {
						((UIInGameMenu)parent).changePopup(null);
					} else {
						checking = false;
						invalidMessage = "There was a problem sending this invite";
						invalid = true;
					}
				}
			}.start();
		} else {
			invalidMessage = "Please enter an ingame name";
			invalid = true;
		}
		if (formOffset < (-Fonts.ttfStandard14.getHeight()*1.5f)) {
			invalid = false;
		}
	}
	
	public void render(float opacity) {
		Draw.rect(0, 0, Display.getWidth(), Display.getHeight(), Util.reAlpha(Colors.BLACK.c, 0.7f*opacity));
		
		Draw.rect(fX-(fW/2), fY-(fH/2)+48, fX+(fW/2), fY+(fH/2), Util.reAlpha(0xFF202020, 1f*opacity));
		
		Draw.rect(fX-(fW/2), fY-(fH/2), fX+(fW/2), fY-(fH/2)+48, Util.reAlpha(Colors.GREEN.c, 1f*opacity));
		
		Draw.string(Fonts.ttfRoundedBold25, fX, fY-(fH/2)+24, title, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.C, Align.C);
		
		if (Kilo.kilo().client != null && Kilo.kilo().client.kiloHead != null) {
			Draw.rectTexture(fX-(fW/2)+16, fY-(fH/2)+64, fH-80, fH-80, Kilo.kilo().client.kiloHead.getTexture(), Util.reAlpha(Colors.WHITE.c, 1f*opacity));
		}
		
		Draw.string(Fonts.ttfRoundedBold12, fX-(fW/2)+48+fH-80, fY+(fH/2)-80, "They will join \u00a73"+mc.getCurrentServerData().serverIP, Util.reAlpha(Colors.WHITE.c, 1f*opacity));
		
		super.render(opacity);
		
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
		
		//Message
		Draw.startClip(fX-(Fonts.ttfStandard14.getWidth(invalidMessage)/2), fY+(fH/2)+(Fonts.ttfStandard14.getHeight()/2), fX+(Fonts.ttfStandard14.getWidth(invalidMessage)/2), fY+(fH/2)+(Fonts.ttfStandard14.getHeight()*1.5f));
		Draw.string(Fonts.ttfStandard14, fX, fY+(fH/2)-(Fonts.ttfStandard14.getHeight(invalidMessage))-formOffset, invalidMessage, Util.reAlpha(0xFFFF5555, 1f*opacity), Align.C, Align.C);
		Draw.endClip();
	}
}
