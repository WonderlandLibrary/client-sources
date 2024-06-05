package club.mega.command.impl;

import club.mega.Mega;
import club.mega.command.Command;
import club.mega.util.ChatUtil;

import java.util.Arrays;

@Command.Info(name = "Help", description = "Gives you information about commands", usage = ".help [command]", aliases = {"help", "h"})
public final class HelpCommand extends Command {

    @Override
    public void execute(final String[] args) {
        final Command command = Mega.INSTANCE.getCommandRegistry().commandBy(args[0]);
        if(command == null) {
            ChatUtil.sendMessage("--------- Help ---------");
            Mega.INSTANCE.getCommandRegistry().getCommands().forEach(commande -> {
                ChatUtil.sendMessage(commande.name() + ": " + commande.getDescription());
            });
            ChatUtil.sendMessage("-------------------------");
        } else {
            ChatUtil.sendMessage("--------- " + command.name() + " ---------");
            ChatUtil.sendMessage("usage: " + command.getUsage());
            ChatUtil.sendMessage("description: " + command.getDescription());
            ChatUtil.sendMessage("aliases: " + Arrays.toString(command.getAliases()));
            ChatUtil.sendMessage("------------------------------");
        }

    }

}
