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

public class QuitCommand
extends Command {
    public QuitCommand() {
        super("Quit", "Quits your game safely");
    }

    @Subscribe
    public void onCommand(ChatEvent e) {
        String[] message = e.message.split(" ");
        if (message[0].equals("quit")) {
            mc.shutdown();
        }
    }
}

