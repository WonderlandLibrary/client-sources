/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.command.commands;

import java.util.List;
import me.thekirkayt.client.command.Com;
import me.thekirkayt.client.command.Command;
import me.thekirkayt.client.command.CommandManager;
import me.thekirkayt.client.command.commands.OptionCommand;
import me.thekirkayt.utils.ClientUtils;

@Com(names={"help"})
public class Help
extends Command {
    @Override
    public void runCommand(String[] args) {
        for (Command command : CommandManager.commandList) {
            if (command instanceof OptionCommand || command.getHelp() == null) continue;
            ClientUtils.sendMessage(command.getHelp());
        }
        ClientUtils.sendMessage(OptionCommand.getHelpString());
    }

    @Override
    public String getHelp() {
        return "\".Help\" To see the list of commands.";
    }
}

