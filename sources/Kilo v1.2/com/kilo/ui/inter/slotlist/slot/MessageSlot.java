package com.kilo.ui.inter.slotlist.slot;

import net.minecraft.client.Minecraft;

import com.kilo.manager.ActivityManager;
import com.kilo.manager.MessageManager;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.UIHandler;
import com.kilo.ui.UIInGameMenu;
import com.kilo.ui.UIPopupMessageReply;
import com.kilo.ui.inter.IconButton;
import com.kilo.ui.inter.Inter;
import com.kilo.ui.inter.slotlist.SlotList;
import com.kilo.ui.inter.slotlist.part.Message;
import com.kilo.util.Align;
import com.kilo.util.Resources;
import com.kilo.util.Timer;
import com.kilo.util.Util;

public class MessageSlot extends Slot {

	private final Minecraft mc = Minecraft.getMinecraft();
	
	public int index;
	
	public int clicks = 0;
	public Timer clickTimer = new Timer();
	
	public MessageSlot(SlotList p, int i, float x, float y, float w, float h, float ox, float oy) {
		super(p, x, y, w, h, ox, oy);
		index = i;
		
		clickTimer.reset();
		
		inters.clear();

		inters.add(new IconButton(this, x+10+40, y+20, 8, 8, Colors.WHITE.c, Resources.iconBack[0]));
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

		inters.get(0).x = x+10+40;
		inters.get(0).y = y+20;
	}
	
	public void doubleClick(int mx, int my) {
		((UIInGameMenu)UIHandler.currentUI).changePopup(new UIPopupMessageReply(UIHandler.currentUI, MessageManager.getMessage(index).minecraftName, MessageManager.getMessage(index).message));
	}
	
	public void mouseClick(int mx, int my, int b) {
		if (hover) {
			if (clicks == 0) {
				clickTimer.reset();
			}
			clicks++;
		}
		super.mouseClick(mx, my, b);
	}
	
	public void interact(Inter i) {
		switch (i.type) {
		default:
			break;
		case button:
			switch(inters.indexOf(i)) {
			case 0:
				doubleClick(0, 0);
				break;
			}
			break;
		case slider:
			break;
		case checkbox:
			break;
		case textbox:
			break;
		case link:
			break;
		}
	}
	
	public void render(float opacity) {
		Draw.rect(x, y, x+width, y+height, Util.reAlpha(0xFF2A2A2A, hoverOpacity*opacity));
		if (MessageManager.getMessage(index) != null) {
			Message message = MessageManager.getMessage(index);
			boolean noIcon = true;
			if (message.icon != null) {
				if (message.icon.getTexture() != null) {
					Draw.rectTexture(x+4, y+4, 40, 40, message.icon.getTexture(), opacity);
					noIcon = false;
				}
			}
			if (noIcon) {
				Draw.loader(x+24, y+24, 8, Util.reAlpha(Colors.WHITE.c, 1f*opacity));
			}
			
			if (message.minecraftName != null && message.message != null) {
				String[] lines = new String[] {
						message.minecraftName,
						"\u00a77"+message.message
					};
				int k = 0;
				for(String l : lines) {
					for(int i = 0; i < l.length(); i++) {
						if (Fonts.ttfRoundedBold10.getWidth(l.substring(0, i)) > parent.w-76-Fonts.ttfRoundedBold10.getWidth("...")) {
							l = l.substring(0, i)+"...";
							break;
						}
					}
					Draw.string(Fonts.ttfRoundedBold10, x+64, y+24-((Fonts.ttfRoundedBold10.getHeight()*(lines.length-1))/2)+(Fonts.ttfRoundedBold10.getHeight()*k), l, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.L, Align.C);
					k++;
				}
			}
		}
		super.render(opacity);
	}
	
}
