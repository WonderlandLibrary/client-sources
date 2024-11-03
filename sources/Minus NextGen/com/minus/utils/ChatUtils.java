package com.minus.utils;

import net.minecraft.text.Text;

public class ChatUtils implements MinecraftInterface {
    public static void addMessage(String message){
        mc.inGameHud.getChatHud().addMessage(Text.of("[§9Minus§r] " + message));
    }
}
