package com.kilo.ui.inter.slotlist.slot;

import net.minecraft.client.Minecraft;
import net.minecraft.util.IChatComponent;

import org.newdawn.slick.TrueTypeFont;

import com.kilo.manager.ChatManager;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.UIChat;
import com.kilo.ui.UIHandler;
import com.kilo.ui.inter.ChatComponent;
import com.kilo.ui.inter.Inter;
import com.kilo.ui.inter.slotlist.SlotList;
import com.kilo.util.Align;
import com.kilo.util.Util;

public class ChatSlot extends Slot {

	private final Minecraft mc = Minecraft.getMinecraft();
	
	public int index;
	
	public ChatSlot(SlotList p, int i, float x, float y, float w, float h, float ox, float oy) {
		super(p, x, y, w, h, ox, oy);
		index = i;
		
		inters.clear();
		
		IChatComponent message = ChatManager.getChatLine(index).formatted;

		float xx = 0;
		for(int j = 0; j < message.getSiblings().size(); j++) {
			IChatComponent icc = (IChatComponent)message.getSiblings().get(j);
			if (j > 0) {
				IChatComponent iccPre = (IChatComponent)message.getSiblings().get(j-1);
				xx+= UIChat.font.getWidth(iccPre.getFormattedText());
			}
			inters.add(new ChatComponent(this, icc, xx, y, UIChat.font, Colors.WHITE.c, Align.L, Align.C));
		}
	}
	
	public void update(int mx, int my) {
		if (ChatManager.getChatLine(index) == null) {
			return;
		}
		
		IChatComponent message = ChatManager.getChatLine(index).formatted;

		float xx = x+4;
		float yy = y;
		int k = 1;
		for(int j = 0; j < message.getSiblings().size(); j++) {
			IChatComponent icc = (IChatComponent)message.getSiblings().get(j);
			
			if (j > 0) {
				IChatComponent iccPre = (IChatComponent)message.getSiblings().get(j-1);
				xx+= UIChat.font.getWidth(iccPre.getFormattedText());
				if (xx+UIChat.font.getWidth(icc.getFormattedText()) > ChatManager.chatWidth-12) {
					xx = x+4;
					yy+= UIChat.font.getHeight();
					k++;
				}
			}
			
			if (j <= inters.size()-1) {
				inters.get(j).x = xx;
				inters.get(j).y = yy;
			}
			height = (UIChat.font.getHeight()*k)+5;
		}

		super.update(mx, my);
		
		hover = mouseOver(mx, my) && enabled && UIHandler.currentUI instanceof UIChat;
		
		activeOpacity+= ((active?1f:0f)-activeOpacity)/2f;
		activeOpacity = Math.min(Math.max(0, activeOpacity), 1f);
		
		hoverOpacity+= ((hover?1f:0f)-hoverOpacity)/2f;
		hoverOpacity = Math.min(Math.max(0, hoverOpacity), 1f);
	}
	
	public void mouseClick(int mx, int my, int b) {
		super.mouseClick(mx, my, b);
	}
	
	@Override
	public void interact(Inter i) {
		switch (i.type) {
		default:
			break;
		case chatcomponent:
			if (ChatManager.getChatLine(index) != null) {
				ChatManager.handleComponentClick(((ChatComponent)i).component);
			}
			break;
		}
	}
	
	public void render(float opacity) {
		Draw.rect(x, y, x+width, y+height, Util.reAlpha(0xFF010101, 0.5f*hoverOpacity*opacity));
		float py = parent.y+parent.oy;
		float pad = 20;
		if (y+height >= py-pad && y <= py+parent.h+pad) {
			super.render(opacity);
		}
	}
	
}
