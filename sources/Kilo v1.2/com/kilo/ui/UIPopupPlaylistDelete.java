package com.kilo.ui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.kilo.Kilo;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.inter.Button;
import com.kilo.ui.inter.IconButton;
import com.kilo.ui.inter.Inter;
import com.kilo.ui.inter.slotlist.part.Playlist;
import com.kilo.util.Align;
import com.kilo.util.Resources;
import com.kilo.util.ServerUtil;
import com.kilo.util.Timer;
import com.kilo.util.Util;

public class UIPopupPlaylistDelete extends UI {

	private float formOffset;
	private boolean invalid;
	
	private String invalidMessage = "";
	
	private Timer invalidTimer = new Timer();
	
	private float fX, fY, fW, fH;
	
	private Playlist playlist;
	private boolean deleting;
	
	public UIPopupPlaylistDelete(UI parent, Playlist playlist) {
		super(parent);
		this.playlist = playlist;
	}
	
	@Override
	public void init() {
		title = "Delete Playlist?";

		formOffset = 0;
		invalid = false;
		
		fX = Display.getWidth()/2;
		fY = (Display.getHeight()/2);
		fW = 400;
		fH = 96+96;

		inters.clear();
		inters.add(new IconButton(this, fX+(fW/2)-40, fY-(fH/2)+12, 24, 24, 0x00000000, Resources.iconClose[3]));
		inters.add(new Button(this, "Yes", fX-(fW/2)+24, fY+(fH/2)-24-48, (fW/2)-24-12, 48, Fonts.ttfRoundedBold20, Colors.GREEN.c, null, 0));
		inters.add(new Button(this, "No", fX+(fW/2)-24-((fW/2)-24-12), fY+(fH/2)-24-48, (fW/2)-24-12, 48, Fonts.ttfRoundedBold20, Colors.RED.c, null, 0));
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
			formOffset+= (0-formOffset)/5f;
		}
		
		inters.get(1).shown = !deleting;
		inters.get(1).enabled = !deleting;
		inters.get(2).shown = !deleting;
		inters.get(2).enabled = !deleting;
	}
	
	@Override
	public void interact(Inter i) {
		switch(inters.indexOf(i)) {
		case 0:
			((UIMusic)parent).changePopup(null);
			break;
		case 1:
			if (!deleting) {
				deleting = true;
				new Thread() {
					@Override
					public void run() {
						if (ServerUtil.playlistRemove(Kilo.kilo().client.clientID, playlist.id)) {
							((UIMusic)parent).changePopup(null);
							Kilo.kilo().client.playlists.remove(playlist);
						} else {
							invalid = true;
							invalidMessage = "There was a problem removing this playlist";
							deleting = false;
						}
					}
				}.start();
			}
			break;
		case 2:
			((UIMusic)parent).changePopup(null);
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
			((UIMusic)parent).changePopup(null);
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
		
		Draw.string(Fonts.ttfRounded16, fX, fY-(fH/2)+48+24, "Are you sure you want to delete the playlist:", Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.C, Align.C);
		Draw.string(Fonts.ttfRounded16, fX, fY-(fH/2)+48+24+(Fonts.ttfRounded16.getHeight()), playlist.name, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.C, Align.C);
		
		if (deleting) {
			Draw.loader(fX, fY+48, 16, Util.reAlpha(Colors.WHITE.c, 1f*opacity));
		}
		
		super.render(opacity);
		
		Draw.startClip((Display.getWidth()/2)-(Fonts.ttfStandard14.getWidth(invalidMessage)/2), fY+(fH/2)+(Fonts.ttfStandard14.getHeight()/2), (Display.getWidth()/2)+(Fonts.ttfStandard14.getWidth(invalidMessage)/2), fY+(fH/2)+(Fonts.ttfStandard14.getHeight()*1.5f));
		Draw.string(Fonts.ttfStandard14, Display.getWidth()/2, fY+(fH/2)-(Fonts.ttfStandard14.getHeight(invalidMessage))-formOffset, invalidMessage, Util.reAlpha(0xFFFF5555, 1f*opacity), Align.C, Align.C);
		Draw.endClip();
	}
}
