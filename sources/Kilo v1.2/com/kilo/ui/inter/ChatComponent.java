package com.kilo.ui.inter;

import java.awt.Color;

import net.minecraft.util.IChatComponent;

import org.newdawn.slick.TrueTypeFont;

import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.ui.InteractableParent;
import com.kilo.ui.UIChat;
import com.kilo.ui.UIHandler;
import com.kilo.util.Align;
import com.kilo.util.ChatUtil;
import com.kilo.util.Util;


public class ChatComponent extends Inter {
	
	public IChatComponent component;
	public float anim;
	public final float animSpeed = 3f;
	
	public ChatComponent(InteractableParent p, IChatComponent icc, float x, float y, TrueTypeFont f, int fc, Align fh, Align fv) {
		super(p, Inter.TYPE.chatcomponent, x-(fh==Align.C?f.getWidth(icc.getFormattedText())/2:(fh==Align.R?f.getWidth(icc.getFormattedText()):0)), y-(fv==Align.C?f.getHeight()/2:(fv==Align.B?f.getHeight():0)), 0, 0, f, fc, fh, fv);
		component = icc;
	}
	
	public void update(int mx, int my) {
		super.update(mx, my);
		if (font != null) {
			width = font.getWidth(component.getFormattedText());
			height = font.getHeight(component.getFormattedText());
		}
		
		if (component.getChatStyle().getChatClickEvent() == null && component.getChatStyle().getChatHoverEvent() == null && component.getChatStyle().getInsertion() == null) {
			hover = false;
		}
		anim+= ((hover?1f:0f)-anim)/animSpeed;
		
		if (hover) {
			if (UIHandler.currentUI != null && UIHandler.currentUI instanceof UIChat) {
				((UIChat)UIHandler.currentUI).hoverComponent = component;
			}
		}
	}
	
	public void render(float opacity) {
		int color = Util.blendColor(Colors.GREY.c, Colors.WHITE.c, 0.25f);
		if (component != null && component.getChatStyle() != null && component.getChatStyle().getColor() != null) {
			org.newdawn.slick.Color c = ChatUtil.getColorFromChar(component.getChatStyle().getColor().toString().toCharArray()[1]);
			Color rc = new Color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
			color = rc.getRGB();
		}
		if (component.getChatStyle().getChatClickEvent() != null) {
			Draw.rect(x, y+height+2, x+((width+2)*anim), y+height+4, Util.reAlpha(color, anim*opacity));
		}
		
		Draw.stringShadow(font, x+(width/2), y+2, component.getFormattedText(), Util.reAlpha(fontColor, 1f*opacity), Align.C, Align.T);
	}
}
