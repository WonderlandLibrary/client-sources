package net.silentclient.client.mods.render;

import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.silentclient.client.Client;
import net.silentclient.client.event.EventTarget;
import net.silentclient.client.event.impl.EventReceivePacket;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;

public class ChatMod extends Mod {
	public ChatMod() {
		super("Chat", ModCategory.MODS, "silentclient/icons/mods/chat.png", true);
	}
	
	private String lastMessage = "";
    private int line, amount;
	
	@Override
	public void setup() {
		this.addBooleanSetting("Font Shadow", this, true);
		this.addBooleanSetting("Bar Animation", this, false);
		this.addBooleanSetting("Smooth", this, false);
		this.addSliderSetting("Smooth Speed", this, 4, 1, 10, false);
		this.addBooleanSetting("Disable Background", this, true);
		this.addBooleanSetting("Copy Message Button", this, false);
		this.addBooleanSetting("Anti-Spam", this, false);
	}
	
	@EventTarget
	public void chat(EventReceivePacket event) {
		if(event.getPacket() instanceof S02PacketChat && !event.isCancelable()) {
			final S02PacketChat chatPacket = (S02PacketChat)event.getPacket();
			if(chatPacket.getType() == 2) return;

			if (Client.getInstance().getSettingsManager().getSettingByName(this, "Copy Message Button").getValBoolean()) {
				String unformattedText = StringUtils.stripControlCodes(chatPacket.getChatComponent().getUnformattedText());
				if (!unformattedText.replace(" ", "").isEmpty()) {
					ChatComponentText copyText = new ChatComponentText(EnumChatFormatting.AQUA.toString() + EnumChatFormatting.BOLD + "[COPY]");
					ChatStyle style = new ChatStyle()
							.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(EnumChatFormatting.GRAY + "Copy message")))
							.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/slc$copy " + unformattedText));
					copyText.setChatStyle(style);

					chatPacket.getChatComponent().appendSibling(new ChatComponentText(EnumChatFormatting.RESET + " "));
					chatPacket.getChatComponent().appendSibling(copyText);
				}
			}

			if (Client.getInstance().getSettingsManager().getSettingByName(this, "Anti-Spam").getValBoolean()) {
				GuiNewChat guiNewChat = mc.ingameGUI.getChatGUI();
		    	if (lastMessage.equals(chatPacket.getChatComponent().getUnformattedText())) {
		    		guiNewChat.deleteChatLine(line);
		    		amount++;
		    		lastMessage = chatPacket.getChatComponent().getUnformattedText();
		    		chatPacket.getChatComponent().appendText(EnumChatFormatting.GRAY + " (" + amount + ")");
		    	} else {
		    		amount = 1;
		    		lastMessage = chatPacket.getChatComponent().getUnformattedText();
		    	}

		    	line++;
		    	if (!event.isCancelable()) {
		    		guiNewChat.printChatMessageWithOptionalDeletion(chatPacket.getChatComponent(), line);
		    	}

		    	if (line > 256) {
		        	line = 0;
		        }

		        event.setCancelled(true);
		 	}
		}
	}
}
