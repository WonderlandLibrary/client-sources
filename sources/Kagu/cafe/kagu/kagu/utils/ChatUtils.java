/**
 * 
 */
package cafe.kagu.kagu.utils;

import cafe.kagu.kagu.Kagu;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

/**
 * @author lavaflowglow
 *
 */
public class ChatUtils {
	
	/**
	 * Adds a message to the chat client side, it turns the object array into a long
	 * string with each object seperated by a space. <code>.toString()</code> is run
	 * on every object before adding it to the message
	 * 
	 * @param objects An array of objects, used to create the message
	 */
	public static void addChatMessage(Object... objects) {
		String message = "";
		for (Object obj : objects) {
			message += (message.length() == 0 ? "" : " ") + obj.toString();
		}
		message = "[ " + Kagu.getName() + " ] " + message;
		ChatComponentText chatComponentText = new ChatComponentText(message);
		addChatMessage(chatComponentText);
	}
	
	/**
	 * Adds a chat component to the chat client side
	 * @param chatComponent The chat component
	 */
	public static void addChatMessage(IChatComponent chatComponent) {
		if (Minecraft.getMinecraft().thePlayer == null)
			return;
		Minecraft.getMinecraft().thePlayer.addChatComponentMessage(chatComponent);
	}
	
}
