package none.event.events;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import none.Client;
import none.event.Event;

public class EventChat extends Event{
	private String message;

	public void fire(String message) {
		this.message = message;
		super.fire();
	}
	
	public static void addchatmessage(String message) {
		if (Client.Starting == false) return;
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ChatFormatting.AQUA + "["+ ChatFormatting.DARK_AQUA + "None" + ChatFormatting.AQUA + "]" + ChatFormatting.WHITE + " " + message));
	}
	
	public static boolean onSendChatMessage(String s){//EntityPlayerSP
		if(s.startsWith(".")){
			Client.instance.commandManager.callCommand(s.substring(1));
			return false;
		}
		return true;
	}
}