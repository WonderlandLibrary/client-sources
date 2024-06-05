package Focus.Beta.UTILS.helper;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class Helper {
	public static Minecraft mc = Minecraft.getMinecraft();

	public static void sendMessage(String message) {
		new ChatUtils.ChatMessageBuilder(true, true).appendText(message).setColor(EnumChatFormatting.GRAY).build()
				.displayClientSided();
	}

	public static boolean onServer(String server) {
		if (!mc.isSingleplayer() && Helper.mc.getCurrentServerData().serverIP.toLowerCase().contains(server)) {
			return true;
		}
		return false;
	}

	public static void sendMessageWithoutPrefix(String string) {
		Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(string));
	}
}
