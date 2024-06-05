package io.github.nevalackin.radium.command.impl;

import io.github.nevalackin.radium.RadiumClient;
import io.github.nevalackin.radium.command.Command;
import io.github.nevalackin.radium.command.CommandExecutionException;
import io.github.nevalackin.radium.utils.Wrapper;

import java.util.Arrays;

public final class HelpCommand extends Command {
    @Override
    public String[] getAliases() {
        return new String[]{"help", "h"};
    }

    @Override
    public void execute(String[] arguments) throws CommandExecutionException {
        Wrapper.addChatMessage("Available Commands:");
        for (Command command : RadiumClient.getInstance().getCommandHandler().getElements()) {
            Wrapper.addChatMessage(Arrays.toString(command.getAliases()) + ": " + command.getUsage());
        }
    }

    @Override
    public String getUsage() {
        return "help/h";
    }
}
