package com.kilo.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.minecraft.client.network.NetworkPlayerInfo;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.kilo.Kilo;
import com.kilo.manager.ActivityManager;
import com.kilo.manager.DatabaseManager;
import com.kilo.manager.UpdateManager;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.render.TextureImage;
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

public class UIPopupAcceptFriend extends UI {

	private float formOffset;
	private boolean invalid;
	
	private String invalidMessage = "";
	
	private Timer invalidTimer = new Timer();
	
	private boolean checking;

	private float fX, fY, fW, fH;

	private int activityIndex;
	private String minecraftName, message;
	private TextureImage head;
	private List<String> lines;
	
	public UIPopupAcceptFriend(UI parent, int activityIndex, String minecraftName, String message) {
		super(parent);
		this.activityIndex = activityIndex; 
		this.minecraftName = minecraftName;
		this.message = message;
		
		head = Resources.downloadTexture(String.format(DatabaseManager.head, minecraftName, "128"));
		
		lines = new CopyOnWriteArrayList<String>();
		if (message == null) {
			return;
		}
		int i = 0;
		int j = 0;
		for(i = 0; i <= message.length(); i++) {
			String part = "";
			part = message.substring(j, i);
			
			if (Fonts.ttfRoundedBold20.getWidth(part) > 384) {
				int b = -1;
				for(int a = i-1; a > j; a--) {
					if (message.charAt(a) == ' ') {
						b = a+1;
						break;
					}
				}
				if (b >= j) {
					i = b;
				}
				lines.add(message.substring(j, i));
				j = i;
			}
		}
		if (i >= message.length()) {
			lines.add(message.substring(j, message.length()));
		}
	}
	
	@Override
	public void init() {
		title = "Accept Friend Request";

		formOffset = 0;
		invalid = false;
		checking = false;

		fX = Display.getWidth()/2;
		fY = (Display.getHeight()/2);
		fW = 550;
		fH = 250;

		inters.clear();
		inters.add(new IconButton(this, fX+(fW/2)-40, fY-(fH/2)+12, 24, 24, 0x00000000, Resources.iconClose[2]));
		inters.add(new Button(this, "Accept", fX+(fW/2)-136, fY+(fH/2)-48, 120, 32, Fonts.ttfRoundedBold14, Colors.GREEN.c, Resources.iconAccept[1], 16, Align.L, Align.C));
	}
	
	public void update(int mx, int my) {
		super.update(mx, my);
		fH = 170+Math.max(((lines.size())*Fonts.ttfRoundedBold20.getHeight()), 0);
		
		inters.get(0).y = fY-(fH/2)+12;
		inters.get(1).y = fY+(fH/2)-48;
		
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

		((Button)inters.get(1)).text = checking?(String)null:"Accept";
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
		case Keyboard.KEY_ESCAPE:
			((UIInGameMenu)parent).changePopup(null);
			break;
		}
	}
	
	public void keyTyped(int key, char keyChar) {
		super.keyTyped(key, keyChar);
	}
	
	public void submit() {
		checking = true;
		new Thread() {
			@Override
			public void run() {
				if (ServerUtil.friendAccept(Kilo.kilo().client.clientID, minecraftName)) {
					ServerUtil.removeActivity(Kilo.kilo().client.clientID, ActivityManager.getActivity(activityIndex).id);
					ActivityManager.removeActivity(ActivityManager.getActivity(activityIndex));
					UpdateManager.updateFriendsList();
					((UIInGameMenu)parent).changePopup(null);
				} else {
					checking = false;
					invalidMessage = "There was a problem accepting this request";
					invalid = true;
				}
			}
		}.start();
		if (formOffset < (-Fonts.ttfStandard14.getHeight()*1.5f)) {
			invalid = false;
		}
	}
	
	public void render(float opacity) {
		Draw.rect(0, 0, Display.getWidth(), Display.getHeight(), Util.reAlpha(Colors.BLACK.c, 0.7f*opacity));
		
		Draw.rect(fX-(fW/2), fY-(fH/2)+48, fX+(fW/2), fY+(fH/2), Util.reAlpha(0xFF202020, 1f*opacity));
		
		Draw.rect(fX-(fW/2), fY-(fH/2), fX+(fW/2), fY-(fH/2)+48, Util.reAlpha(Colors.GREEN.c, 1f*opacity));
		
		Draw.string(Fonts.ttfRoundedBold25, fX, fY-(fH/2)+24, title, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.C, Align.C);
		
		if (head != null && head.getTexture() != null) {
			Draw.rectTexture(fX-(fW/2)+16, fY-(fH/2)+48+16, 48, 48, head.getTexture(), Util.reAlpha(Colors.WHITE.c, 1f*opacity));
		}
		
		float maxWidth = 400f;
		float maxHeight = 32+Math.max(((lines.size()-1)*Fonts.ttfRoundedBold20.getHeight()), 0);
		float rad = (maxHeight/2)-1;
		Draw.arcEllipse(fX-(fW/2)+104, fY-(fH/2)+72+(maxHeight/2), 90, 270, 15, rad, Util.reAlpha(Colors.GREEN.c, 1f*opacity));
		Draw.arcEllipse(fX-(fW/2)+104+maxWidth, fY-(fH/2)+72+(maxHeight/2), -90, 90, 15, rad, Util.reAlpha(Colors.GREEN.c, 1f*opacity));
		Draw.rect(fX-(fW/2)+104, fY-(fH/2)+72, fX-(fW/2)+104+maxWidth, fY-(fH/2)+72+maxHeight, Util.reAlpha(Colors.GREEN.c, 1f*opacity));
		for(String s : lines) {
			Draw.string(Fonts.ttfRoundedBold20, fX-(fW/2)+112, fY-(fH/2)+88+(lines.indexOf(s)*Fonts.ttfRoundedBold20.getHeight()), s, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.L, Align.C);
		}
		
		super.render(opacity);
		
		//Message
		Draw.startClip(fX-(Fonts.ttfStandard14.getWidth(invalidMessage)/2), fY+(fH/2)+(Fonts.ttfStandard14.getHeight()/2), fX+(Fonts.ttfStandard14.getWidth(invalidMessage)/2), fY+(fH/2)+(Fonts.ttfStandard14.getHeight()*1.5f));
		Draw.string(Fonts.ttfStandard14, fX, fY+(fH/2)-(Fonts.ttfStandard14.getHeight(invalidMessage))-formOffset, invalidMessage, Util.reAlpha(0xFFFF5555, 1f*opacity), Align.C, Align.C);
		Draw.endClip();
	}
}
