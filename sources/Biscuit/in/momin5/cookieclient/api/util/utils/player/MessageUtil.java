package in.momin5.cookieclient.api.util.utils.player;

import com.mojang.realmsclient.gui.ChatFormatting;
import in.momin5.cookieclient.CookieClient;
import in.momin5.cookieclient.api.module.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.util.text.TextComponentString;

public class MessageUtil {

    public static String watermark = ChatFormatting.GRAY + "[" + ChatFormatting.GOLD + CookieClient.MOD_NAME + ChatFormatting.GRAY + "] " + ChatFormatting.RESET;
    public static ChatFormatting messageFormatting = ChatFormatting.WHITE;
    private static Minecraft mc = Minecraft.getMinecraft();


    public static void sendClientPrefixMessage(String message) {
        TextComponentString string = new TextComponentString(watermark + messageFormatting + message);

        mc.player.sendMessage(string);
    }
    public static void sendServerMessage(String message) {
        mc.player.connection.sendPacket(new CPacketChatMessage(message));
    }

    public static void sendNotifMessage(String message){

        if(ModuleManager.nullCheck())
            return;
        
        TextComponentString string = new TextComponentString(watermark + message);

        mc.player.sendMessage(string);
    }
}
