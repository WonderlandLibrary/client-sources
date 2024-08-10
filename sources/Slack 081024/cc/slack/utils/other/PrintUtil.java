package cc.slack.utils.other;

import cc.slack.utils.client.IMinecraft;
import net.minecraft.util.ChatComponentText;

public final class PrintUtil implements IMinecraft {

    public static void print(String message) {
        System.out.println("[Slack] " + message);
    }

    public static void debugMessage(String message) {
        mc.thePlayer.addChatMessage(new ChatComponentText("§f[§cDEBUG§f] §e" + message));
    }

    public static void message(String message) {
        mc.thePlayer.addChatMessage(new ChatComponentText("§cSlack » §f" + message));
    }

    public static void msgNoPrefix(String message) {
        mc.thePlayer.addChatMessage(new ChatComponentText("§f" + message));
    }
}
