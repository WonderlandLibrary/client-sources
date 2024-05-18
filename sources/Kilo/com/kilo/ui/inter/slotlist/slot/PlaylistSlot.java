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
import com.kilo.ui.inter.IconButton;
import com.kilo.ui.inter.Inter;
import com.kilo.ui.inter.slotlist.SlotList;
import com.kilo.util.Align;
import com.kilo.util.Resources;
import com.kilo.util.Util;

public class PlaylistSlot extends Slot {

	private final Minecraft mc = Minecraft.getMinecraft();
	
	public int index;
	
	public PlaylistSlot(SlotList p, int i, float x, float y, float w, float h, float ox, float oy) {
		super(p, x, y, w, h, ox, oy);
		index = i;
		
		inters.clear();
		inters.add(new IconButton(this, x+width-12, y+(height/2)-4, 8, 8, Colors.GREEN.c, Resources.iconPlay[0]));
		inters.add(new IconButton(this, x+width-24, y+(height/2)-4, 8, 8, Colors.GREY.c, Resources.iconEdit[0]));
		inters.add(new IconButton(this, x+width-36, y+(height/2)-4, 8, 8, Colors.RED.c, Resources.iconDelete[0]));
	}
	
	public void update(int mx, int my) {
		super.update(mx, my);
		
		inters.get(0).x = x+width-12;
		inters.get(0).y = y+(height/2)-4;

		inters.get(1).x = x+width-24;
		inters.get(1).y = y+(height/2)-4;
		
		inters.get(2).x = x+width-36;
		inters.get(2).y = y+(height/2)-4;
	}
	
	public void mouseClick(int mx, int my, int b) {
		super.mouseClick(mx, my, b);
		active = hover;
	}
	
	@Override
	public void interact(final Inter i) {
		switch(inters.indexOf(i)) {
		case 0:
			if (!Kilo.kilo().client.playlists.get(index).songs.isEmpty()) {
				MusicHandler.play(Kilo.kilo().client.playlists.get(index).songs, Kilo.kilo().client.playlists.get(index).songs.get(0));
			}
			break;
		case 1:
			if (UIHandler.currentUI instanceof UIMusic) {
				((UIMusic)UIHandler.currentUI).changePopup(new UIPopupPlaylistEdit(UIHandler.currentUI, Kilo.kilo().client.playlists.get(index)));
			}
			break;
		case 2:
			if (UIHandler.currentUI instanceof UIMusic) {
				((UIMusic)UIHandler.currentUI).changePopup(new UIPopupPlaylistDelete(UIHandler.currentUI, Kilo.kilo().client.playlists.get(index)));
			}
			break;
		}
	}
	
	public void render(float opacity) {
		if (Kilo.kilo().client.getPlaylist(index) != null) {
			try {
				if (Kilo.kilo().client.getPlaylist(index) != null && Kilo.kilo().client.getPlaylist(index).name != null) {
					Draw.rectTexture(x+8, y+(height/2)-4, 8, 8, Resources.iconMusic[0], Util.reAlpha(Colors.WHITE.c, (Math.max((hoverOpacity/2f), (activeOpacity/2))+0.5f)*opacity));
					
					String title = Kilo.kilo().client.getPlaylist(index).name;
					for(int i = 0; i < title.length(); i++) {
						if (Fonts.ttfRounded14.getWidth(title.substring(0, i)) > (parent.w-64)-Fonts.ttfRounded14.getWidth("...")) {
							title = title.substring(0, i)+"...";
							break;
						}
					}
					
					Draw.string(Fonts.ttfRoundedBold12, x+24, y+(height/2), title, Util.reAlpha(active?Colors.GREEN.c:Colors.WHITE.c, !active?((hoverOpacity/2)+0.5f):1f*opacity), Align.L, Align.C);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		super.render(opacity);
	}
	
}
