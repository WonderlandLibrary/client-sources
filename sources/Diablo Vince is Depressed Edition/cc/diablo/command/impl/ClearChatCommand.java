/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.command.impl;

import cc.diablo.command.Command;
import cc.diablo.event.impl.ChatEvent;
import com.google.common.eventbus.Subscribe;

public class ClearChatCommand
extends Command {
    public ClearChatCommand() {
        super("ClearChat", "Clears all messages in chat");
    }

    @Subscribe
    public void onCommand(ChatEvent e) {
        String[] message = e.message.split(" ");
        if (message[0].equals("clearchat")) {
            ClearChatCommand.mc.ingameGUI.getChatGUI().clearChatMessages();
        }
    }
}

