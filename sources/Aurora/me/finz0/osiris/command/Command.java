package me.finz0.osiris.command;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.finz0.osiris.module.modules.gui.NotificationsHud;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

import java.awt.*;

public abstract class Command {
    static Minecraft mc = Minecraft.getMinecraft();
    public static String prefix = "-";
    public abstract String[] getAlias();
    public abstract String getSyntax();
    public abstract void onCommand(String command, String[] args) throws Exception;

    public static boolean MsgWaterMark = true;
    public static ChatFormatting cf = ChatFormatting.GRAY;

    public static void sendClientMessage(String message){
            NotificationsHud.addMessage(new TextComponentString(cf + message));
        if(MsgWaterMark)
            mc.player.sendMessage(new TextComponentString("[\u00A7aAurora\u00A7f] "+ cf + message));
        else
            mc.player.sendMessage(new TextComponentString(cf + message));
    }

    public static Color getColorFromChatFormatting(ChatFormatting cf){
        if(cf == ChatFormatting.BLACK) return Color.BLACK;
        if(cf == ChatFormatting.GREEN) return  Color.GREEN;
        if(cf == ChatFormatting.AQUA) return Color.CYAN;
        if(cf == ChatFormatting.BLUE || cf == ChatFormatting.DARK_BLUE || cf == ChatFormatting.DARK_AQUA) return Color.BLUE;
        if(cf == ChatFormatting.BLUE) return Color.BLUE;
        if(cf == ChatFormatting.DARK_GREEN || cf == ChatFormatting.GREEN) return Color.GREEN;
        if(cf == ChatFormatting.DARK_PURPLE) return Color.MAGENTA;
        if(cf == ChatFormatting.RED || cf == ChatFormatting.DARK_RED) return Color.RED;
        if(cf == ChatFormatting.LIGHT_PURPLE) return Color.PINK;
        if(cf == ChatFormatting.YELLOW) return Color.YELLOW;
        if(cf == ChatFormatting.GOLD) return Color.ORANGE;
        return Color.WHITE;
    }
    public static char SECTIONSIGN() {
        return '\u00a7';
    }

    public static void sendRawMessage(String message){
        mc.player.sendMessage(new TextComponentString(message));
    }

    public static String getPrefix(){
        return prefix;
    }

    public static void setPrefix(String p){
        prefix = p;
    }

}
