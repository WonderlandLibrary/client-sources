package com.wikihacks.listener.chat;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientChatRecieved {
    @SubscribeEvent
    public static void onChatRecieved(ClientChatReceivedEvent e) {
        System.out.print(e.getMessage().getUnformattedText());
    }
}
