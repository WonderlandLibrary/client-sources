// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.command.commands;

import java.util.Iterator;
import me.chrest.utils.ClientUtils;
import me.chrest.client.command.CommandManager;
import me.chrest.client.command.Com;
import me.chrest.client.command.Command;

@Com(names = { "help" })
public class Help extends Command
{
    @Override
    public void runCommand(final String[] args) {
        for (final Command command : CommandManager.commandList) {
            if (command instanceof OptionCommand) {
                continue;
            }
            if (command.getHelp() == null) {
                continue;
            }
            ClientUtils.sendMessage(command.getHelp());
        }
        ClientUtils.sendMessage(OptionCommand.getHelpString());
    }
    
    @Override
    public String getHelp() {
        return "Help - help - Returns a list of commands.";
    }
}
