package com.kilo.ui.inter.slotlist.slot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import net.minecraft.client.Minecraft;

import com.kilo.Kilo;
import com.kilo.music.MusicHandler;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.UIHandler;
import com.kilo.ui.UIMusic;
import com.kilo.ui.UIPopupPlaylistSelect;
import com.kilo.ui.inter.IconButton;
import com.kilo.ui.inter.Inter;
import com.kilo.ui.inter.slotlist.SlotList;
import com.kilo.ui.inter.slotlist.part.Playlist;
import com.kilo.ui.inter.slotlist.part.Song;
import com.kilo.util.Align;
import com.kilo.util.Resources;
import com.kilo.util.ServerUtil;
import com.kilo.util.Util;

public class SongSlot extends Slot {

	private final Minecraft mc = Minecraft.getMinecraft();
	
	public Song song;
	
	public SongSlot(SlotList p, Song s, float x, float y, float w, float h, float ox, float oy) {
		super(p, x, y, w, h, ox, oy);
		song = s;

		inters.clear();
		inters.add(new IconButton(this, x+height-10, y+height-24, 16, 16, Colors.WHITE.c, Resources.iconPlay[1]));
		inters.add(new IconButton(this, x+height-4, y+8, 8, 8, Colors.GREY.c, Resources.iconStar[0]));
		
		boolean isInPlaylist = false;
		for(Playlist pl : Kilo.kilo().client.playlists) {
			if (pl.songs.contains(song)) {
				inters.add(new IconButton(this, x+height-4, y+20, 8, 8, Colors.GREY.c, Resources.iconDelete[0]));
				isInPlaylist = true;
				break;
			}
		}
		if (!isInPlaylist) {
			inters.add(new IconButton(this, x+height-4, y+20, 8, 8, Colors.GREY.c, Resources.iconAdd[0]));
		}
	}
	
	public void update(int mx, int my) {
		super.update(mx, my);
		
		inters.get(0).x = x+height-10;
		inters.get(0).y = y+height-24;

		inters.get(1).x = x+height-4;
		inters.get(1).y = y+8;
		((IconButton)inters.get(1)).buttonColor = song.starred?Colors.YELLOW.c:Colors.GREY.c;
		
		if (inters.size() >= 3) {
			inters.get(2).x = x+height-4;
			inters.get(2).y = y+20;
		}
	}
	
	public void mouseClick(int mx, int my, int b) {
		super.mouseClick(mx, my, b);
		active = hover;
	}

	@Override
	public void interact(final Inter i) {
		switch(inters.indexOf(i)) {
		case 0:
			play();
			break;
		case 1:
			if (!song.starred) {
				ServerUtil.songAddStar(Kilo.kilo().client.clientID, String.valueOf(song.id));
			} else {
				ServerUtil.songRemoveStar(Kilo.kilo().client.clientID, String.valueOf(song.id));
			}
			song.starred = !song.starred;
			break;
		case 2:
			Playlist pl = null;
			for(Playlist pll : Kilo.kilo().client.playlists) {
				if (pll.songs.contains(song)) {
					pl = pll;
					break;
				}
			}
			if (pl != null) {
				ServerUtil.playlistRemoveSong(Kilo.kilo().client.clientID, pl.id, String.valueOf(song.id));
				pl.songs.remove(song);
			} else {
				((UIMusic)UIHandler.currentUI).changePopup(new UIPopupPlaylistSelect(UIHandler.currentUI, song));
			}
			break;
		}
	}
	
	public void play() {
		List<Song> list = new ArrayList<Song>();
		for(Slot s : parent.slots) {
			list.add(((SongSlot)s).song);
		}
		MusicHandler.play(list, song);
	}
	
	public void render(float opacity) {
		Draw.rect(x, y, x+width, y+height, Util.reAlpha(0xFF2A2A2A, activeOpacity*opacity));
		Draw.rect(x, y, x+width, y+height, Util.reAlpha(0xFF1A1A1A, hoverOpacity*opacity));
		Draw.rect(x, y-1, x+width, y, Util.reAlpha(Colors.WHITE.c, 0.1f*opacity));
		if (song != null) {
			try {
				if (song.image != null && song.image.getTexture() != null) {
					Draw.rectTexture(x+8, y+8, height-16, height-16, song.image.getTexture(), 1f*opacity);
				}
				String title = song.title;
				for(int i = 0; i < title.length(); i++) {
					if (Fonts.ttfRounded14.getWidth(title.substring(0, i)) > (parent.w-height-24-32-Fonts.ttfRounded12.getWidth("00:00:00"))-Fonts.ttfRounded14.getWidth("...")) {
						title = title.substring(0, i)+"...";
						break;
					}
				}
				Draw.string(Fonts.ttfRounded14, x+height+24, y+(height/2), title, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.L, Align.C);

				int millis = song.duration;
				String time = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
					    TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
					    TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
				Draw.string(Fonts.ttfRounded12, x+width-16, y+(height/2), time, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.R, Align.C);
			} catch (Exception e) {
				
			}
		} else {
			parent.slots.remove(this);
		}
		super.render(opacity);
	}
	
}
