/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.command.impl;

import cc.diablo.Main;
import cc.diablo.command.Command;
import cc.diablo.event.impl.ChatEvent;
import cc.diablo.helpers.render.ChatColor;
import cc.diablo.helpers.render.ChatHelper;
import com.google.common.eventbus.Subscribe;

public class ClientNameCommand
extends Command {
    public ClientNameCommand() {
        super("ClientName", "Change client name of client");
    }

    @Subscribe
    public void onCommand(ChatEvent e) {
        String[] message = e.message.split(" ");
        if (message[0].equals("clientname") || message[0].equals("name")) {
            Main.customName = message[1];
            ChatHelper.addChat("Client name set to " + (Object)((Object)ChatColor.GREEN) + message[1] + (Object)((Object)ChatColor.RESET) + "!");
        }
    }
}

