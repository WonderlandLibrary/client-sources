package me.travis.wurstplus.util;

import com.mojang.realmsclient.gui.ChatFormatting;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatUtils {

    public static String getChatTimestamp() {
        String timestamp = (Object) ChatFormatting.GRAY + "\u00A76" + "<" + new SimpleDateFormat("k:mm a").format(new Date())  + ">" + (Object) ChatFormatting.RESET + " ";
    return timestamp;
    }

}
