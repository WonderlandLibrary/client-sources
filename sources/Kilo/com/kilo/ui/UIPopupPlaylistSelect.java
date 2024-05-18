package com.kilo.ui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.kilo.Kilo;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.inter.IconButton;
import com.kilo.ui.inter.Inter;
import com.kilo.ui.inter.slotlist.SlotList;
import com.kilo.ui.inter.slotlist.part.Playlist;
import com.kilo.ui.inter.slotlist.part.Song;
import com.kilo.ui.inter.slotlist.slot.HistorySlot;
import com.kilo.ui.inter.slotlist.slot.PlaylistAddSlot;
import com.kilo.users.UserHandler;
import com.kilo.util.Align;
import com.kilo.util.Resources;
import com.kilo.util.ServerUtil;
import com.kilo.util.Util;

public class UIPopupPlaylistSelect extends UI {

	private float fX, fY, fW, fH;

	public SlotList sl;
	private Song song;
	
	public UIPopupPlaylistSelect(UI parent, Song song) {
		super(parent);
		this.song = song;
	}
	
	@Override
	public void init() {
		title = "Select a Playlist";
		
		fX = Display.getWidth()/2;
		fY = (Display.getHeight()/2);
		fW = 400;
		fH = 48+48+(48*Math.min(8, Kilo.kilo().client.playlists.size()));

		sl = new SlotList(7f);
		
		inters.clear();
		inters.add(new IconButton(this, fX+(fW/2)-40, fY-(fH/2)+12, 24, 24, 0x00000000, Resources.iconClose[2]));
	}
	
	public void update(int mx, int my) {
		super.update(mx, my);
		
		if (fH != 48+48+(48*Math.min(8, Kilo.kilo().client.playlists.size()))) {
			init();
		}

		sl.x = fX-(fW/2)+24;
		sl.y = fY-(fH/2)+72-sl.oy;
		sl.w = fW-48;
		sl.h = fH-96;
		
		if (sl.slots.size() != Kilo.kilo().client.playlists.size()) {
			sl.slots.clear();
			int i = 0;
			for(Playlist f : Kilo.kilo().client.playlists) {
				sl.slots.add(new PlaylistAddSlot(sl, Kilo.kilo().client.playlists.indexOf(f), sl.x, sl.y, sl.w, 48, 0, i*48));
				i++;
			}
		}

		sl.update(mx, my);
	}
	
	@Override
	public void interact(final Inter i) {
		switch(inters.indexOf(i)) {
		case 0:
			((UIMusic)parent).changePopup(null);
			break;
		}
	}
	
	public void mouseClick(int mx, int my, int b) {
		super.mouseClick(mx, my, b);
		sl.mouseClick(mx, my, b);
	}
	
	public void mouseRelease(int mx, int my, int b) {
		super.mouseRelease(mx, my, b);
		sl.mouseRelease(mx, my, b);
	}
	
	public void mouseScroll(int s) {
		super.mouseScroll(s);
		sl.mouseScroll(s);
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
	
	public void add(Playlist playlist) {
		if (song != null && playlist != null) {
			if (ServerUtil.playlistAddSong(Kilo.kilo().client.clientID, playlist.id, String.valueOf(song.id))) {
				playlist.songs = ServerUtil.getPlaylistSongs(Kilo.kilo().client.clientID, playlist.id);
				((UIMusic)parent).changePopup(null);
			}
		}
	}
	
	public void render(float opacity) {
		Draw.rect(0, 0, Display.getWidth(), Display.getHeight(), Util.reAlpha(Colors.BLACK.c, 0.7f*opacity));
		
		Draw.rect(fX-(fW/2), fY-(fH/2)+48, fX+(fW/2), fY+(fH/2), Util.reAlpha(0xFF202020, 1f*opacity));

		Draw.rect(fX-(fW/2), fY-(fH/2), fX+(fW/2), fY-(fH/2)+48, Util.reAlpha(Colors.GREEN.c, 1f*opacity));
		
		Draw.string(Fonts.ttfRoundedBold25, fX, fY-(fH/2)+24, title, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.C, Align.C);
		
		Draw.startClip(fX-(fW/2)+24, fY-(fH/2)+72, fX+(fW/2)-24+sl.sbw, fY+(fH/2)-24);
		sl.render(opacity);
		Draw.endClip();

		super.render(opacity);
	}
}
