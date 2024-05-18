package net.minecraft.client.triton.impl.commands;

import net.minecraft.client.triton.management.command.Com;
import net.minecraft.client.triton.management.command.Command;
import net.minecraft.client.triton.management.command.CommandManager;
import net.minecraft.client.triton.utils.ClientUtils;

@Com(names = { "credits", "cred" })
public class Credits extends Command
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
            ClientUtils.sendMessage("Credits to the following people:");
            ClientUtils.sendMessage("N3xus for his event hook API.");
            ClientUtils.sendMessage("Aristhena for his utils and management. Also a few modules.");
            ClientUtils.sendMessage("Arab for his notebot.");
        }
    }
}