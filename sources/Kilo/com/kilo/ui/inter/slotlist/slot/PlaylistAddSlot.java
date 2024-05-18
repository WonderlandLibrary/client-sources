package com.kilo.ui.inter.slotlist.slot;

import net.minecraft.client.Minecraft;

import com.kilo.Kilo;
import com.kilo.music.MusicHandler;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.UIHandler;
import com.kilo.ui.UIMusic;
import com.kilo.ui.UIPopupPlaylistDelete;
import com.kilo.ui.UIPopupPlaylistEdit;
import com.kilo.ui.UIPopupPlaylistSelect;
import com.kilo.ui.inter.IconButton;
import com.kilo.ui.inter.Inter;
import com.kilo.ui.inter.slotlist.SlotList;
import com.kilo.util.Align;
import com.kilo.util.Resources;
import com.kilo.util.Util;

public class PlaylistAddSlot extends Slot {

	private final Minecraft mc = Minecraft.getMinecraft();
	
	public int index;
	
	public PlaylistAddSlot(SlotList p, int i, float x, float y, float w, float h, float ox, float oy) {
		super(p, x, y, w, h, ox, oy);
		index = i;
		
		inters.clear();
	}
	
	public void update(int mx, int my) {
		super.update(mx, my);
	}
	
	public void mouseClick(int mx, int my, int b) {
		if (hover) {
			if (((UIMusic)UIHandler.currentUI).popup instanceof UIPopupPlaylistSelect) {
				((UIPopupPlaylistSelect)((UIMusic)UIHandler.currentUI).popup).add(Kilo.kilo().client.playlists.get(index));
			}
		}
	}
	
	@Override
	public void interact(final Inter i) {
	}
	
	public void render(float opacity) {
		Draw.rect(x, y, x+width, y+height, Util.reAlpha(0xFF2A2A2A, hoverOpacity*opacity));
		Draw.rect(x, y, x+width, y+height, Util.reAlpha(0xFF2A2A2A, activeOpacity*opacity));
		String name = Kilo.kilo().client.playlists.get(index).name;
		for(int i = 0; i < name.length(); i++) {
			if (Fonts.ttfRoundedBold14.getWidth(name.substring(0, i)) > parent.w-60-Fonts.ttfRoundedBold14.getWidth("...")) {
				name = name.substring(0, i)+"...";
				break;
			}
		}
		
		Draw.string(Fonts.ttfRoundedBold14, x+16, y+24, name, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.L, Align.C);
		super.render(opacity);
	}
	
}
