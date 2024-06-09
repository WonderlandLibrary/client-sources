package us.loki.freaky.command;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import us.loki.legit.Client;
import us.loki.legit.modules.Module;
import us.loki.legit.modules.ModuleManager;

public abstract class Command {
	
	public static Minecraft mc = Minecraft.getMinecraft();
	public abstract String getAlias();
	public abstract String getDescription();
	public abstract String getSyntax();
	public abstract void onCommand(String command, String[] args) throws Exception;
	
	public static void messageWithoutPrefix(String msg) {
		Object chat = new ChatComponentText(msg);
		if(msg != null){
			Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage((IChatComponent)chat);
		}
	}
	public static void messageWithPrefix(String msg){
		messageWithoutPrefix(Client.instance.Client_Prefix + msg);
	}
	public void printChat(String print) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\u00a77[\u00a76NUMB\u00a77]: " + (Object)((Object)EnumChatFormatting.RESET) + print));
    }

}