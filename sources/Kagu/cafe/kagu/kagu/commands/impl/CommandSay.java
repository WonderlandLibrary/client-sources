/**
 * 
 */
package cafe.kagu.kagu.commands.impl;

import cafe.kagu.kagu.commands.Command;
import cafe.kagu.kagu.commands.CommandAction;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C01PacketChatMessage;

/**
 * @author lavaflowglow
 *
 */
public class CommandSay extends Command {
	
	private static ActionRequirement say = new ActionRequirement((CommandAction)args -> {
		String message = "";
		for (String str : args) {
			message += (message.length() == 0 ? "" : " ") + str;
		}
		Minecraft.getMinecraft().getNetHandler().getNetworkManager().sendPacketNoEvent(new C01PacketChatMessage(message));
		return true;
	});
	
	public CommandSay() {
		super("say", "", say);
	}
	
}
