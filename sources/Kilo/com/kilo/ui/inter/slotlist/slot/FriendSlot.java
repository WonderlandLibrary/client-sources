package com.kilo.ui.inter.slotlist.slot;

import net.minecraft.client.Minecraft;

import com.kilo.manager.DatabaseManager;
import com.kilo.manager.FriendManager;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.inter.slotlist.SlotList;
import com.kilo.util.Align;
import com.kilo.util.Timer;
import com.kilo.util.Util;

public class FriendSlot extends Slot {

	private final Minecraft mc = Minecraft.getMinecraft();
	
	public int index;
	
	public int clicks = 0;
	public Timer clickTimer = new Timer();
	
	public FriendSlot(SlotList p, int i, float x, float y, float w, float h, float ox, float oy) {
		super(p, x, y, w, h, ox, oy);
		index = i;
		
		clickTimer.reset();
		
		inters.clear();
	}
	
	public void update(int mx, int my) {
		super.update(mx, my);
		if (clicks == 2) {
			doubleClick(mx, my);
			clicks = 0;
		}
		
		if (clickTimer.isTime(Util.doubleClickTimer)) {
			clickTimer.reset();
			clicks = 0;
		}
	}
	
	public void doubleClick(int mx, int my) {
		Util.openWeb(String.format(DatabaseManager.profile, FriendManager.getFriend(index).mcname));
	}
	
	public void mouseClick(int mx, int my, int b) {
		if (hover) {
			if (clicks == 0) {
				clickTimer.reset();
			}
			clicks++;
		}
	}
	
	public void render(float opacity) {
		Draw.rect(x, y, x+width, y+height, Util.reAlpha(0xFF2A2A2A, hoverOpacity*opacity));
		if (FriendManager.getFriend(index) != null) {
			boolean noHead = true;
			if (FriendManager.getFriend(index).head != null) {
				if (FriendManager.getFriend(index).head.getTexture() != null) {
					Draw.rectTexture(x+4, y+4, 40, 40, FriendManager.getFriend(index).head.getTexture(), opacity);
					noHead = false;
				}
			}
			if (noHead) {
				Draw.loader(x+24, y+24, 8, Util.reAlpha(Colors.WHITE.c, 1f*opacity));
			}
			
			try {
				if (FriendManager.getFriend(index) != null && FriendManager.getFriend(index).kiloname != null) {
					String[] lines = new String[] {
						FriendManager.getFriend(index).kiloname+" \u00a77("+FriendManager.getFriend(index).mcname+")",
						FriendManager.getFriend(index).ip.length() > 0?"\u00a7a"+FriendManager.getFriend(index).ip:
							FriendManager.getFriend(index).status == "Offline"?"\u00a7c"+FriendManager.getFriend(index).status:
								FriendManager.getFriend(index).status == "Online"?"\u00a7b"+FriendManager.getFriend(index).status:
									FriendManager.getFriend(index).status == "Singleplayer"?"\u00a7b"+FriendManager.getFriend(index).status:
										FriendManager.getFriend(index).status == "Multiplayer"?"\u00a7a"+FriendManager.getFriend(index).status:"\u00a7cOffline"
						};
					int k = 0;
					for(String l : lines) {
						for(int i = 0; i < l.length(); i++) {
							if (Fonts.ttfRoundedBold10.getWidth(l.substring(0, i)) > parent.w-60-Fonts.ttfRoundedBold10.getWidth("...")) {
								l = l.substring(0, i)+"...";
								break;
							}
						}
						Draw.string(Fonts.ttfRoundedBold10, x+48, y+24-((Fonts.ttfRoundedBold10.getHeight()*(lines.length-1))/2)+(Fonts.ttfRoundedBold10.getHeight()*k), l, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.L, Align.C);
						k++;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		super.render(opacity);
	}
	
}
