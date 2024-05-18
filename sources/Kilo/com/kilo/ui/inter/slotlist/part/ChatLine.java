package com.kilo.ui.inter.slotlist.part;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;

public class ChatLine {

	public IChatComponent formatted;
	public String unformatted;
	
	public ChatLine(IChatComponent message) {
		formatted = message.createCopy();
		formatted.getSiblings().clear();
		
		List list = GuiUtilRenderComponents.func_178908_a(message, 1000, Minecraft.getMinecraft().fontRendererObj, false, false);
		
		if (list.size() > 0) {
			message = (IChatComponent)list.get(0);
		}
		
		if (message.getSiblings().size() > 0) {
			for(Object o : message.getSiblings()) {
				IChatComponent icc = (IChatComponent)o;
				ChatStyle cs = icc.getChatStyle();
				String s = icc.getUnformattedText();
				
				List<IChatComponent> newList = new ArrayList<IChatComponent>();
				
				if (cs.getChatHoverEvent() != null || cs.getChatClickEvent() != null) {
					formatted.appendSibling(new ChatComponentText(s).setChatStyle(cs));
					continue;
				}
				
				String[] parts = s.split(" ");
				if (parts.length > 1) {
					for(String ss : parts) {
						newList.add(new ChatComponentText(ss+" ").setChatStyle(cs));
					}
				} else {
					newList.add(new ChatComponentText(s).setChatStyle(cs));
				}
				
				for(IChatComponent newICC : newList) {
					formatted.appendSibling(newICC);
				}
			}
		} else {
			IChatComponent icc = message;
			ChatStyle cs = icc.getChatStyle();
			
			List<IChatComponent> newList = new ArrayList<IChatComponent>();
			
			String s = icc.getUnformattedText();
			
			String[] parts = s.split(" ");
			if (parts.length > 1) {
				for(String ss : parts) {
					newList.add(new ChatComponentText(ss+" ").setChatStyle(cs));
				}
			} else {
				newList.add(new ChatComponentText(s).setChatStyle(cs));
			}
			
			for(IChatComponent newICC : newList) {
				formatted.appendSibling(newICC);
			}
		}
		
		this.unformatted = message.getUnformattedText();
	}
}
