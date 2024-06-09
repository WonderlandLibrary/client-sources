package com.kilo.ui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

import com.kilo.Kilo;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.inter.Button;
import com.kilo.ui.inter.CheckBox;
import com.kilo.ui.inter.IconButton;
import com.kilo.ui.inter.Inter;
import com.kilo.ui.inter.TextBox;
import com.kilo.users.UserControl;
import com.kilo.util.Align;
import com.kilo.util.ChatUtil;
import com.kilo.util.Config;
import com.kilo.util.Resources;
import com.kilo.util.ServerUtil;
import com.kilo.util.Timer;
import com.kilo.util.Util;

public class UIAddServer extends UI {

	private float formOffset;
	private boolean invalid, checking;
	
	private String invalidMessage = "";
	
	private Timer invalidTimer = new Timer();
	
	private float fX, fY, fW, fH;
	
	public UIAddServer(UI parent) {
		super(parent);
	}
	
	@Override
	public void init() {
		title = "Add Server";

		formOffset = 0;
		invalid = false;

		fX = Display.getWidth()/2;
		fY = (Display.getHeight()/2)+44;
		fW = Display.getWidth()-40;
		fH = Display.getHeight()-124;

		inters.clear();
		int i = 0;
		inters.add(new IconButton(this, 32+(64*(i++)), 32, 32, 32, Colors.WHITE.c, Resources.iconReturn[3]));
		inters.add(new Button(this, "Add Server", fX+(fW/2)-8-128, fY+(fH/2)-48+8, 128, 32, Fonts.ttfStandard12, Colors.GREEN.c, Resources.iconAdd[1], 16));
		inters.add(new TextBox(this, "Example: play.hivemc.com", fX-250-20, fY, 400, 40, Fonts.ttfRoundedBold25, Colors.GREY.c, Align.L, Align.C));
		inters.add(new TextBox(this, "Port", fX-250-20+400+40, fY, 100, 40, Fonts.ttfRoundedBold25, Colors.WHITE.c, Align.L, Align.C));
		
		((TextBox)inters.get(3)).setText("25565");
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
		
		((Button)inters.get(1)).enabled = ((TextBox)inters.get(2)).text.length() > 0 && ((TextBox)inters.get(3)).text.length() > 0;
		((Button)inters.get(1)).text = checking?(String)null:"Add Server";
	}
	
	@Override
	public void interact(Inter i) {
		switch(inters.indexOf(i)) {
		case 0:
			UIHandler.changeUI(parent);
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
	
	public void keyboardPress(int key) {
		super.keyboardPress(key);
		switch (key) {
		case Keyboard.KEY_RETURN:
			submit();
			break;
		}
	}
	
	private void submit() {
		if (!invalid && validate() == "" && !checking) {
			checking = true;
			new Thread() {
				@Override
				public void run() {
					String ip= ((TextBox)inters.get(2)).getText();
					String port = ((TextBox)inters.get(3)).getText();
					
					String connect = ServerUtil.addServer(ip, port);
					if (connect != null) {
						UIHandler.changeUI(parent);
					} else {
						 ServerUtil.addServer(ip, port);
						 UIHandler.changeUI(parent);
						//invalidMessage = "Failed to add server to database";
						//invalid = true;
					}
				}
			}.start();
		} else {
			if (invalid == false) {
				invalidMessage = validate();
				invalid = true;
			}
		}
		if (formOffset < (-Fonts.ttfStandard14.getHeight()*1.5f)) {
			invalid = false;
		}
	}
	
	private String validate() {
		String[] input = new String[] {((TextBox)inters.get(2)).getText(), ((TextBox)inters.get(3)).getText()};
		boolean[] hasText = new boolean[] {input[0].length() > 0, input[1].length() > 0};
		boolean[] isValid = new boolean[] {!input[0].contains(" "), ChatUtil.isNumber(input[1])};
		String [] hasTextError = new String[] {"Please enter a server IP", "Please enter a server port (e.g. 25565)"};
		String [] isValidError = new String[] {"That is not a valid server IP", "The server port can only be a number"};
		
		String message = "";
		for(int i = input.length-1; i >= 0; i--) {
			if (hasText[i]) {
				if (isValid[i]) {
				} else {
					message = isValidError[i];
				}
			} else {
				message = hasTextError[i];
			}
		}
		return message;
	}
	
	public void render(float opacity) {
		drawDarkerBackground(false, opacity);
		
		Draw.string(Fonts.ttfRoundedBold40, Display.getWidth()/2, fY-(fH/2)-(Fonts.ttfRoundedBold40.getHeight(title)/2)-10, title, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.C, Align.C);
		
		Draw.rect(fX-(fW/2), fY-(fH/2), fX+(fW/2), fY+(fH/2)-48, Util.reAlpha(0xFF111111, 0.75f*opacity));
		
		Draw.rect(fX-(fW/2), fY+(fH/2)-48, fX+(fW/2), fY+(fH/2), Util.reAlpha(0xFF0A0A0A, 0.75f*opacity));

		Draw.string(Fonts.ttfRoundedBold40, fX, fY-80, "Enter Server IP:", Util.reAlpha(Colors.DARKBLUE.c, 1f*opacity), Align.C, Align.C);
		Draw.string(Fonts.ttfRoundedBold25, fX-250-20+400+20, fY, ":", Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.C, Align.T);
		Draw.rect(fX-250-30, fY+(Fonts.ttfRoundedBold25.getHeight())+15, fX+250+30, fY+(Fonts.ttfRoundedBold25.getHeight())+20, Util.reAlpha(Colors.DARKBLUE.c, 1f*opacity));
		
		//Message
		Draw.startClip(fX-(Fonts.ttfStandard14.getWidth(invalidMessage)/2), fY+(fH/2)-40+(Fonts.ttfStandard14.getHeight()/2), fX+(Fonts.ttfStandard14.getWidth(invalidMessage)/2), fY+(fH/2)-40+(Fonts.ttfStandard14.getHeight()*1.5f));
		Draw.string(Fonts.ttfStandard14, fX, fY+(fH/2)-40-(Fonts.ttfStandard14.getHeight(invalidMessage))-formOffset, invalidMessage, Util.reAlpha(0xFFFF5555, 1f*opacity), Align.C, Align.C);
		Draw.endClip();

		super.render(opacity);
	}
}
