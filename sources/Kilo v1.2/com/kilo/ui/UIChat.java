package com.kilo.ui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer.EnumChatVisibility;
import net.minecraft.util.IChatComponent;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.TrueTypeFont;

import com.kilo.input.Input;
import com.kilo.manager.ChatManager;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.inter.Inter;
import com.kilo.ui.inter.TextBox;
import com.kilo.ui.inter.slotlist.ChatList;
import com.kilo.ui.inter.slotlist.part.ChatLine;
import com.kilo.ui.inter.slotlist.slot.ChatSlot;
import com.kilo.ui.inter.slotlist.slot.Slot;
import com.kilo.util.Align;
import com.kilo.util.ChatUtil;
import com.kilo.util.Util;

public class UIChat extends UI {

	public static final TrueTypeFont font = Fonts.ttfRounded14;
	
	private int selectedIndex;
	private static float fX, fY, fW, fH, dY, dYTo;
	private static final float pad = 4f;
	
	private int selectedHistory = -1;
	
	public static ChatList sl = new ChatList(3f);
	
	public IChatComponent hoverComponent;
	
	public UIChat(UI parent) {
		super(parent);
	}
	
	@Override
	public void init() {
		fX = 4+(ChatManager.chatWidth/2);
		fW = ChatManager.chatWidth;
		fH = ChatManager.chatHeight;

		inters.clear();
		inters.add(new TextBox(this, "Enter message...", -100, 0, 10, 24, font, Colors.WHITE.c, Align.L, Align.C));
		inters.get(0).active = true;
		
		((TextBox)inters.get(0)).text = ((GuiChat)mc.currentScreen).defaultInputFieldText;
		((TextBox)inters.get(0)).cursorPos = ((TextBox)inters.get(0)).text.length();
		((TextBox)inters.get(0)).startSelect = ((TextBox)inters.get(0)).cursorPos;
	}
	
	public void update(int mx, int my) {
		inters.get(0).active = true;
		if (((TextBox)inters.get(0)).text.length() > 100) {
			((TextBox)inters.get(0)).text = ((TextBox)inters.get(0)).text.substring(0, 100);
		}
		
		hoverComponent = null;
		
		super.update(mx, my);
		
		inters.get(0).x = fX-(fW/2)+(pad);
		inters.get(0).y = fY+(fH/2)+(pad)+2+dY;
		inters.get(0).width = fW-(pad*2);
	}
	
	public static void updateHistory(int mx, int my) {
		int scale = Math.max(1, Minecraft.getMinecraft().gameSettings.guiScale);
		ChatManager.chatWidth = (40+(Minecraft.getMinecraft().gameSettings.chatWidth*(320-40)))*scale;
		
		if (!(UIHandler.currentUI instanceof UIChat) && !(UIHandler.uiTo instanceof UIChat)) {
			dY+= (0-dY)/2f;
			sl.scrollTo = 0;
			float chatSize = 0;
			for(Slot slot : sl.slots) {
				chatSize+= slot.height;
			}
			ChatManager.chatHeight = Math.min(chatSize, (20+(Minecraft.getMinecraft().gameSettings.chatHeightUnfocused*(180-20)))*scale);
		} else {
			ChatManager.chatHeight = (20+(Minecraft.getMinecraft().gameSettings.chatHeightFocused*(180-20)))*scale;
			if (UIHandler.currentUI instanceof UIChat) {
				dY+= ((-pad-2-(((UIChat)UIHandler.currentUI).inters.get(0)).height)-dY)/2f;
			} else {
				dY+= ((-pad-2-(((UIChat)UIHandler.uiTo).inters.get(0)).height)-dY)/2f;
			}
		}

		fX = 4+(ChatManager.chatWidth/2);
		fW = ChatManager.chatWidth;
		fH = ChatManager.chatHeight;
		fY = Display.getHeight()-(fH/2);
		
		float oldFY = fY;
		fY+= dY;
		
		if (sl.slots.size() != ChatManager.getSize()) {
			List<Slot> temp = new ArrayList<Slot>();
			int i = 0;
			for(ChatLine s : ChatManager.getList()) {
				ChatSlot tcs;
				temp.add(tcs = new ChatSlot(sl, ChatManager.getIndex(s), sl.x, sl.y, sl.w, 22, 0, i*22));
				tcs.update(mx, my);
				i++;
			}
			sl.slots = temp;
		}
		
		sl.x = fX-(fW/2)+pad;
		sl.y = fY-(fH/2)+pad-sl.oy;
		sl.w = fW-(pad*2)-sl.sbw;
		sl.h = fH-pad;
		
		sl.update(mx, my);
		
		if (ChatManager.getSize() > 100) {
			ChatManager.removeChatLine(ChatManager.getSize()-1);
		}
		
		fY = oldFY;
	}
	
	@Override
	public void interact(Inter i) {
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
		super.mouseScroll(s*3);
		sl.mouseScroll(s*3);
	}
	
	public void keyboardPress(int key) {
		super.keyboardPress(key);
		
		boolean setText = false;
		TextBox tb = (TextBox)inters.get(0);
		
		switch(key) {
		case Keyboard.KEY_TAB:
			String part = "";
			for(int i = Math.min(tb.cursorPos-1, tb.getText().length()-1); i >= 0; i--) {
				if (tb.getText().charAt(i) == ' ' || i == 0) {
					part = tb.getText().substring(i+(i == 0?0:1), Math.min(tb.cursorPos, tb.getText().length()));
					break;
				}
			}
			String name = "";
			for(Object o : mc.thePlayer.sendQueue.func_175106_d()) {
				NetworkPlayerInfo npi = (NetworkPlayerInfo)o;
				String n = npi.getGameProfile().getName();
				if (n.toLowerCase().startsWith(part.toLowerCase())) {
					name = n.substring(part.length(), n.length());
					break;
				}
			}
			tb.setText(ChatUtil.insertAt(tb.getText(), name, tb.cursorPos));
			tb.cursorPos+= name.length();
			tb.startSelect = tb.cursorPos;
			tb.endSelect = tb.cursorPos;
			break;
		case Keyboard.KEY_UP:
			selectedHistory++;
			setText = true;
			break;
		case Keyboard.KEY_DOWN:
			selectedHistory--;
			setText = true;
			break;
		case Keyboard.KEY_RETURN:
			if (inters.get(0).active) {
				if (tb.text.length() > 0) {
					mc.thePlayer.sendChatMessage(tb.text);
					ChatManager.userHistory.add(tb.text);
				}
				tb.text = "";
				mc.displayGuiScreen(null);
			}
			break;
		case Keyboard.KEY_ESCAPE:
			mc.displayGuiScreen((GuiScreen)null);
			break;
		}
		
		selectedHistory = Math.min(Math.max(-1, selectedHistory), ChatManager.userHistory.size()-1);
		
		if (setText) {
			if (selectedHistory != -1) {
				tb.text = ChatManager.userHistory.get(ChatManager.userHistory.size()-1-selectedHistory);
				tb.cursorPos = tb.text.length();
				tb.startSelect = tb.cursorPos;
			} else {
				tb.text = "";
			}
		}
	}
	
	public void render(float opacity) {
		if (hoverComponent != null) {
			ChatManager.handleComponentHover(hoverComponent, Input.mouse[0], Input.mouse[1]);
		}
		super.render(opacity);
	}
	
	public static void renderHistory(float opacity) {
		opacity *= Minecraft.getMinecraft().gameSettings.chatOpacity;
		if (Minecraft.getMinecraft().gameSettings.chatVisibility != EnumChatVisibility.HIDDEN) {
			float oldFY = fY;
			fY+= dY;
			
			Draw.rect(fX-(fW/2), fY-(fH/2), fX+(fW/2), fY+(fH/2)+(pad), Util.reAlpha(0xFF111111, 0.7f*opacity));
			Draw.rect(fX-(fW/2), fY+(fH/2)+(pad), fX+(fW/2), fY+(fH/2)+(pad)+2, Util.reAlpha(Colors.GREEN.c, 1f*opacity));
			Draw.rect(fX-(fW/2), fY+(fH/2)+(pad)+2, fX+(fW/2), fY+(fH/2)+(pad)+2+100, Util.reAlpha(0xFF111111, 0.7f*opacity));
			
			Draw.startClip(fX-(fW/2)+4, fY-(fH/2)+4, fX+(fW/2)-4, fY+(fH/2));
			sl.render(opacity);
			Draw.endClip();
			
			fY = oldFY;
		}
	}
}
