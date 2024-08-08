package me.xatzdevelopments.util;

import me.xatzdevelopments.Wrapper;
import net.minecraft.util.ChatComponentText;

public class Logger {
    public static String prefixText = "Fang";
    public static String prefixColor = "9";
    public static String textColor = "f";

    public static void log(String msg) {
        if (Wrapper.mc.thePlayer != null && Wrapper.mc.theWorld != null) {
            prefixColor = "9";
            prefixText = "Fang" + "\2479";
            textColor = "f";
            StringBuilder tempMsg = new StringBuilder();

            for (String line : msg.split("\n")) {
                tempMsg.append(line).append("\2477");
            }

            Wrapper.mc.thePlayer.addChatMessage(new ChatComponentText("\247" + prefixColor + "[" + prefixText + "]\247" + textColor + " " + tempMsg.toString()));
            System.out.println(ConsoleColor.BLUE + "[" + prefixText + "]" + ConsoleColor.RESET + " " + msg + ConsoleColor.RESET);
        }
    }

    public static void ingameInfo(String msg) {
        if (Wrapper.mc.thePlayer != null && Wrapper.mc.theWorld != null) {
            prefixColor = "9";
            textColor = "6";
            prefixText = "Fang" + " - " + "\247" + textColor + "Info" + "\2479";
            StringBuilder tempMsg = new StringBuilder();

            for (String line : msg.split("\n")) {
                tempMsg.append(line).append("\2477");
            }

            Wrapper.mc.thePlayer.addChatMessage(new ChatComponentText("\247" + prefixColor + "[" + prefixText + "]\247" + textColor + " " + tempMsg.toString()));
        }
    }

    public static void ingameWarn(String msg) {
        if (Wrapper.mc.thePlayer != null && Wrapper.mc.theWorld != null) {
            textColor = "e";
            prefixText = "Fang" + " - " + "\247" + textColor + "Warning" + "\2479";
            StringBuilder tempMsg = new StringBuilder();

            for (String line : msg.split("\n")) {
                tempMsg.append(line).append("\2477");
            }

            Wrapper.mc.thePlayer.addChatMessage(new ChatComponentText("\247" + prefixColor + "[" + prefixText + "]\247" + textColor + " " + tempMsg.toString()));
        }
    }

    public static void ingameError(String msg) {
        if (Wrapper.mc.thePlayer != null && Wrapper.mc.theWorld != null) {
            textColor = "c";
            prefixText = "Fang" + " - " + "\247" + textColor + "Error" + "\2479";
            StringBuilder tempMsg = new StringBuilder();

            for (String line : msg.split("\n")) {
                tempMsg.append(line).append("\2477");
            }

            Wrapper.mc.thePlayer.addChatMessage(new ChatComponentText("\247" + prefixColor + "[" + prefixText + "]\247" + textColor + " " + tempMsg.toString()));
        }
    }

    public static void consoleLogInfo(String msg) {
        prefixText = "Fang" + " - Info";
        System.out.println(ConsoleColor.BLUE + "[" + prefixText + "]" + ConsoleColor.RESET + " " + msg + ConsoleColor.RESET);
    }

    public static void consoleLogWarn(String msg) {
        prefixText = "Fang" + " - Warn";
        System.out.println(ConsoleColor.YELLOW + "[" + prefixText + "]" + ConsoleColor.RESET + " " + msg + ConsoleColor.RESET);
    }

    public static void consoleLogError(String msg) {
        prefixText = "Fang" + " - Error";
        System.out.println(ConsoleColor.RED + "[" + prefixText + "]" + ConsoleColor.RESET + " " + msg + ConsoleColor.RESET);
    }
}
