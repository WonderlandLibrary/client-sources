package com.kilo.ui;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.SaveFormatComparator;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.kilo.Kilo;
import com.kilo.manager.DatabaseManager;
import com.kilo.manager.WorldManager;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.render.TextureImage;
import com.kilo.ui.inter.IconButton;
import com.kilo.ui.inter.Inter;
import com.kilo.ui.inter.TextBox;
import com.kilo.ui.inter.TextBoxAlt;
import com.kilo.util.Align;
import com.kilo.util.Resources;
import com.kilo.util.ServerUtil;
import com.kilo.util.Timer;
import com.kilo.util.Util;

public class UIPopupWorldRename extends UI {

	private float formOffset;
	private boolean invalid, deleting;
	
	private String invalidMessage = "";
	
	private Timer invalidTimer = new Timer();
	
	private float fX, fY, fW, fH;
	
	private SaveFormatComparator world;

	public UIPopupWorldRename(UI parent, SaveFormatComparator world) {
		super(parent);
		this.world = world;
	}

	@Override
	public void init() {
		title = "Rename";

		formOffset = 0;
		invalid = false;
		
		fX = Display.getWidth()/2;
		fY = (Display.getHeight()/2);
		fW = 450;
		fH = 175;

		inters.clear();
		inters.add(new IconButton(this, fX+(fW/2)-40, fY-(fH/2)+12, 24, 24, 0x00000000, Resources.iconClose[3]));
		inters.add(new IconButton(this, fX+(fW/2)-16-24, fY+(fH/2)-48, 24, 24, Colors.WHITE.c, Resources.iconSubmit[2]));
		inters.add(new TextBoxAlt(this, "Enter new world name...", fX-(fW/2)+24, fY-(fH/2)+48+24, fW-64, 32, Fonts.ttfRoundedBold20, -1, Align.L, Align.C));
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
	}
	
	@Override
	public void interact(Inter i) {
		switch(inters.indexOf(i)) {
		case 0:
			((UISingleplayer)parent).changePopup(null);
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
			((UISingleplayer)parent).changePopup(null);
			break;
		}
	}
	
	public void keyTyped(int key, char keyChar) {
		super.keyTyped(key, keyChar);
	}
	
	public void submit() {
		String newName = ((TextBox)inters.get(2)).text;
		
		if (newName != null && newName.length() > 0) {
            ISaveFormat var2 = this.mc.getSaveLoader();
            var2.renameWorld(world.getFileName(), newName);
            WorldManager.loadWorlds();
			((UISingleplayer)parent).changePopup(null);
		} else {
			invalidMessage = "Please enter a new world name";
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

		super.render(opacity);
		
		//Message
		Draw.startClip(fX-(Fonts.ttfStandard14.getWidth(invalidMessage)/2), fY+(fH/2)+(Fonts.ttfStandard14.getHeight()/2), fX+(Fonts.ttfStandard14.getWidth(invalidMessage)/2), fY+(fH/2)+(Fonts.ttfStandard14.getHeight()*1.5f));
		Draw.string(Fonts.ttfStandard14, fX, fY+(fH/2)-(Fonts.ttfStandard14.getHeight(invalidMessage))-formOffset, invalidMessage, Util.reAlpha(0xFFFF5555, 1f*opacity), Align.C, Align.C);
		Draw.endClip();
	}
}
