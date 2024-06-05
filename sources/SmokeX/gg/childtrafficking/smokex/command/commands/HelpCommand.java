// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.command.commands;

import java.util.Iterator;
import gg.childtrafficking.smokex.SmokeXClient;
import gg.childtrafficking.smokex.utils.player.ChatUtils;
import gg.childtrafficking.smokex.command.CommandInfo;
import gg.childtrafficking.smokex.command.Command;

@CommandInfo(name = "Help", usage = ".help", description = "Shows a list of commands and their descriptions.", aliases = {})
public final class HelpCommand extends Command
{
    @Override
    public void execute(final String[] args) {
        ChatUtils.addChatMessage("§7Command List:");
        for (final Command command : SmokeXClient.getInstance().getCommandManager().getCommands()) {
            ChatUtils.addChatMessage("§b" + command.getName() + " - " + command.getDescription());
        }
    }
}
