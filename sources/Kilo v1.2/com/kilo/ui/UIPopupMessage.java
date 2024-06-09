package com.kilo.ui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.network.NetworkPlayerInfo;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.kilo.Kilo;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.inter.IconButton;
import com.kilo.ui.inter.Inter;
import com.kilo.ui.inter.TextBox;
import com.kilo.ui.inter.TextBoxAlt;
import com.kilo.users.Player;
import com.kilo.users.UserHandler;
import com.kilo.util.Align;
import com.kilo.util.Resources;
import com.kilo.util.Util;

public class UIPopupMessage extends UI {

	private float fX, fY, fW, fH;
	
	private String message;
	
	public UIPopupMessage(UI parent, String title, String message) {
		super(parent);
		this.title = title;
		this.message = message;
	}
	
	@Override
	public void init() {
		fX = Display.getWidth()/2;
		fY = (Display.getHeight()/2);
		fW = 550;
		fH = 176;

		inters.clear();
		inters.add(new IconButton(this, fX+(fW/2)-40, fY-(fH/2)+12, 24, 24, 0x00000000, Resources.iconClose[3]));
	}
	
	public void update(int mx, int my) {
		super.update(mx, my);
	}
	
	@Override
	public void interact(Inter i) {
		switch(inters.indexOf(i)) {
		case 0:
			((UIInGameMenu)parent).changePopup(null);
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
	
	public void render(float opacity) {
		Draw.rect(0, 0, Display.getWidth(), Display.getHeight(), Util.reAlpha(Colors.BLACK.c, 0.7f*opacity));
		
		Draw.rect(fX-(fW/2), fY-(fH/2)+48, fX+(fW/2), fY+(fH/2), Util.reAlpha(0xFF202020, 1f*opacity));
		
		Draw.rect(fX-(fW/2), fY-(fH/2), fX+(fW/2), fY-(fH/2)+48, Util.reAlpha(Colors.GREEN.c, 1f*opacity));
		
		Draw.string(Fonts.ttfRoundedBold25, fX, fY-(fH/2)+24, title, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.C, Align.C);
		
		Draw.string(Fonts.ttfRoundedBold20, fX, fY+24, message, Util.reAlpha(Colors.GREY.c, 1f*opacity), Align.C, Align.C);
		
		super.render(opacity);
	}
}
