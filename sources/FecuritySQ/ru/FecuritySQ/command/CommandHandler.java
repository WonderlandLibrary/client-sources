package ru.FecuritySQ.command;

import ru.FecuritySQ.FecuritySQ;
import ru.FecuritySQ.command.imp.HelpCommand;
import ru.FecuritySQ.utils.RenderUtil;

public class CommandHandler {

    public CommandManager commandManager;

    public CommandHandler(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public boolean execute(String msg) {
        if (msg.length() > 0 && msg.startsWith(".")) {
            if (this.commandManager.execute(msg)) {
                return true;
            }else {
                for (CommandAbstract command : FecuritySQ.get().getCommandManager().getCommands()) {
                    if (!(command instanceof HelpCommand)) {
                        RenderUtil.addChatMessage("§7" + "." +  command.name + "§7" + " ("
                                + "§f" + command.description + "§7" + ")");
                    }
                }
                return true;
            }
        }
        return false;
    }
}
