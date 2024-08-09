package ru.FecuritySQ.command.imp;

import ru.FecuritySQ.FecuritySQ;
import ru.FecuritySQ.command.Command;
import ru.FecuritySQ.command.CommandAbstract;

@Command(name = "help", description = "WTF")
public class HelpCommand extends CommandAbstract {

    @Override
    public void execute(String[] args) throws Exception {
        for (CommandAbstract command : FecuritySQ.get().getCommandManager().getCommands()) {
            if (!(command instanceof HelpCommand)) {
                sendMessage("§7." +  command.name + "§7" + " ("
                        + "§f" + command.description + "§7" + ")");
            }
        }
    }

    @Override
    public void error() {
        sendMessage("§c" + "WTF");
    }
}
