package com.kilo.ui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.network.NetworkPlayerInfo;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.kilo.manager.ChatSpamManager;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.inter.Button;
import com.kilo.ui.inter.IconButton;
import com.kilo.ui.inter.Inter;
import com.kilo.ui.inter.TextBox;
import com.kilo.ui.inter.TextBoxAlt;
import com.kilo.util.Align;
import com.kilo.util.Config;
import com.kilo.util.Resources;
import com.kilo.util.Util;

public class UIPopupChatSpamAdd extends UI {

	private float formOffset;
	
	private float fX, fY, fW, fH;
	
	public UIPopupChatSpamAdd(UI parent) {
		super(parent);
	}
	
	@Override
	public void init() {
		title = "Add Message";

		formOffset = 0;

		fX = Display.getWidth()/2;
		fY = (Display.getHeight()/2);
		fW = 350;
		fH = 200;

		inters.clear();
		inters.add(new IconButton(this, fX+(fW/2)-40, fY-(fH/2)+12, 24, 24, 0x00000000, Resources.iconClose[2]));
		inters.add(new Button(this, "Add", fX+(fW/2)-136, fY+(fH/2)-48, 120, 32, Fonts.ttfRoundedBold14, Colors.GREEN.c, Resources.iconAdd[1], 16, Align.L, Align.C));
		inters.add(new TextBoxAlt(this, "Enter message...", fX-(fW/2)+24, fY-(fH/2)+80, fW-48, 32, Fonts.ttfRoundedBold20, -1, Align.L, Align.C));
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
			((UIChatSpam)parent).changePopup(null);
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
		case Keyboard.KEY_RETURN:
			submit();
			break;
		case Keyboard.KEY_ESCAPE:
			((UIChatSpam)parent).changePopup(null);
			break;
		}
	}
	
	public void keyTyped(int key, char keyChar) {
		super.keyTyped(key, keyChar);
	}
	
	public void submit() {
		String name = ((TextBox)inters.get(2)).text;
		
		ChatSpamManager.addMessage(name);
		((UIChatSpam)parent).changePopup(null);
		
		Config.saveChatSpam();
	}
	
	public void render(float opacity) {
		Draw.rect(0, 0, Display.getWidth(), Display.getHeight(), Util.reAlpha(Colors.BLACK.c, 0.7f*opacity));
		
		Draw.rect(fX-(fW/2), fY-(fH/2)+48, fX+(fW/2), fY+(fH/2), Util.reAlpha(0xFF202020, 1f*opacity));
		
		Draw.rect(fX-(fW/2), fY-(fH/2), fX+(fW/2), fY-(fH/2)+48, Util.reAlpha(Colors.GREEN.c, 1f*opacity));
		
		Draw.string(Fonts.ttfRoundedBold25, fX, fY-(fH/2)+24, title, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.C, Align.C);
		
		super.render(opacity);
	}
}
