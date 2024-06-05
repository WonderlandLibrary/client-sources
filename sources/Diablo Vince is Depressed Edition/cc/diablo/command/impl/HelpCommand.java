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

public class HelpCommand
extends Command {
    public HelpCommand() {
        super("Help", "Provides infromation to the user about the client");
    }

    @Subscribe
    public void onCommand(ChatEvent e) {
        String[] message = e.message.split(" ");
        if (message[0].equals("help")) {
            ChatHelper.addChat("Diablo " + (Object)((Object)ChatColor.DARK_PURPLE) + Main.version + (Object)((Object)ChatColor.WHITE) + " [" + (Object)((Object)ChatColor.GREEN) + Main.buildType + (Object)((Object)ChatColor.WHITE) + "]");
            ChatHelper.addChat("Developed by " + Main.authors);
            ChatHelper.addChat("To list all modules do .modules");
            ChatHelper.addChat("To list all commands do .commands");
        }
    }
}

