package client.metaware.client;

import client.metaware.Metaware;
import client.metaware.api.utils.MinecraftUtil;
import client.metaware.impl.utils.render.StringUtils;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class Logger implements MinecraftUtil {

    public static void print(final String msg) {
        mc.thePlayer.addChatMessage((IChatComponent)new ChatComponentText(Metaware.INSTANCE.getClientInfo().getClientNamePrefix() + StringUtils.translateAlternateColorCodes('&', msg)));
    }

    public static void printWithoutPrefix(final String msg){
        mc.thePlayer.addChatMessage(new ChatComponentText(StringUtils.translateAlternateColorCodes('&', msg)));
    }

    public static void printOutput(final String msg){
        System.out.println(msg);
    }

}
