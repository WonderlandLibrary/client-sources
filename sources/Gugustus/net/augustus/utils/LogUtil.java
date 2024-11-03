package net.augustus.utils;

import net.augustus.Augustus;
import net.augustus.modules.Module;
import net.augustus.utils.interfaces.MC;
import net.minecraft.util.ChatComponentText;


public class LogUtil implements MC {

    private static final String prefix = "[" + Augustus.getInstance().getName() + "]";

    public static void print(Object message) {
        System.out.println(prefix + " " + message);
    }

    public static void addChatMessage(String message) {
        if (mc.thePlayer != null && mc.theWorld != null) {
            mc.thePlayer.addChatMessage(new ChatComponentText(message));
        }
    }
    public static void addDebugChatMessage(Module module , String message) {
        if (mc.thePlayer != null && mc.theWorld != null) {
            mc.thePlayer.addChatMessage(new ChatComponentText("§F[§6"+module.getName()+"§F]§F[§5Debug§F] "+message));
        }
    }
    public static void addAntiLIQChatMessage(Module module , String message) {
        if (mc.thePlayer != null && mc.theWorld != null) {
            mc.thePlayer.addChatMessage(new ChatComponentText("§F[§6"+module.getName()+"§F]§F[§5Anti§4LIQ§F] "+message));
        }
    }


}
