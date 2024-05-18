// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.impl.commands;

import java.util.Iterator;

import net.minecraft.client.triton.management.command.Com;
import net.minecraft.client.triton.management.command.Command;
import net.minecraft.client.triton.management.command.CommandManager;
import net.minecraft.client.triton.utils.ClientUtils;

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
