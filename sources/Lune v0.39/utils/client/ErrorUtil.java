package me.superskidder.lune.utils.client;

import me.superskidder.lune.utils.player.PlayerUtil;
import net.minecraft.util.EnumChatFormatting;

/**
 * @author: QianXia
 * @description: 错误处理类
 * @create: 2021/01/02-19:22
 */
public class ErrorUtil {
    public static void printException(Exception e){
        for(int i = 0; i < 5; i++) {
            PlayerUtil.sendMessageClean("");
        }

        PlayerUtil.sendMessage(EnumChatFormatting.DARK_RED + "An error has occurred, please report the error to the developer!");
        PlayerUtil.sendMessage(EnumChatFormatting.DARK_RED + e.toString());

        Throwable[] suppressed = e.getSuppressed();
        StackTraceElement[] stackTrace = e.getStackTrace();

        for(Throwable shit : suppressed){
            PlayerUtil.sendMessage(EnumChatFormatting.DARK_RED + shit.getMessage());
        }

        for (StackTraceElement stackTraceElement : stackTrace) {
            PlayerUtil.sendMessage(EnumChatFormatting.DARK_RED + stackTraceElement.toString());
        }
    }
}
