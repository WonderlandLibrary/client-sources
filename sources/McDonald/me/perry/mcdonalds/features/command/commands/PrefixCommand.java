// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.command.commands;

import me.perry.mcdonalds.McDonalds;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.perry.mcdonalds.features.command.Command;

public class PrefixCommand extends Command
{
    public PrefixCommand() {
        super("prefix", new String[] { "<char>" });
    }
    
    @Override
    public void execute(final String[] commands) {
        if (commands.length == 1) {
            Command.sendMessage(ChatFormatting.GREEN + "Current prefix is " + McDonalds.commandManager.getPrefix());
            return;
        }
        McDonalds.commandManager.setPrefix(commands[0]);
        Command.sendMessage("Prefix changed to " + ChatFormatting.GRAY + commands[0]);
    }
}
