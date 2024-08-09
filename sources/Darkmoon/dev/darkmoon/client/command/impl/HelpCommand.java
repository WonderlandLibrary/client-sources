package dev.darkmoon.client.command.impl;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.command.Command;
import dev.darkmoon.client.command.CommandAbstract;
import dev.darkmoon.client.command.CommandManager;

@Command(name = "help", description = "help")
public class HelpCommand extends CommandAbstract {
    @Override
    public void execute(String[] args) throws Exception {
        sendMessage(ChatFormatting.GRAY + "Список команд: ");
        for (CommandAbstract command : DarkMoon.getInstance().getCommandManager().getCommands()) {
            if (!(command instanceof HelpCommand)) {
                sendMessage(ChatFormatting.WHITE + CommandManager.getPrefix() + command.name + ChatFormatting.GRAY + " - "
                        + command.description);
            }
        }
    }

    @Override
    public void error() {
        sendMessage(ChatFormatting.RED + "Error");
    }
}