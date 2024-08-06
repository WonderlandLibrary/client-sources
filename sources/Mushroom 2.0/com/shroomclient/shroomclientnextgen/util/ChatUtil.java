package com.shroomclient.shroomclientnextgen.util;

import net.minecraft.text.Text;

public class ChatUtil {

    public static void chat(Object message) {
        if (C.p() != null && C.isInGame() && C.w() != null) C.p()
            .sendMessage(Text.of(String.valueOf(message)));
    }

    public static void sendPrefixMessage(Object message) {
        if (C.p() != null) {
            C.p()
                .sendMessage(
                    Text.of("§4[§c§lMushroom §f§lClient§4]§f " + message)
                );
        }
    }
}
