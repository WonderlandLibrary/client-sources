// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.command.commands;

import java.util.Iterator;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.perry.mcdonalds.McDonalds;
import me.perry.mcdonalds.features.command.Command;

public class HelpCommand extends Command
{
    public HelpCommand() {
        super("help");
    }
    
    @Override
    public void execute(final String[] commands) {
        Command.sendMessage("Commands: ");
        for (final Command command : McDonalds.commandManager.getCommands()) {
            Command.sendMessage(ChatFormatting.GRAY + McDonalds.commandManager.getPrefix() + command.getName());
        }
    }
}
