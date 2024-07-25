package club.bluezenith.events.impl;

import club.bluezenith.events.Event;

public class SentMessageEvent extends Event {
    public String message;

    public boolean sendToChat;

    public SentMessageEvent(String message, boolean sendToChat) {
        this.message = message;
        this.sendToChat = sendToChat;
    }
}