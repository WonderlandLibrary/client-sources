package io.github.nevalackin.radium.command.impl;

import io.github.nevalackin.radium.command.Command;
import io.github.nevalackin.radium.command.CommandExecutionException;
import io.github.nevalackin.radium.module.impl.render.hud.Hud;

public final class ClientNameCommand extends Command {
    @Override
    public String[] getAliases() {
        return new String[]{"clientname", "rename"};
    }

    @Override
    public void execute(String[] arguments) throws CommandExecutionException {
        if (arguments.length >= 2) {
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < arguments.length; i++)
                sb.append(arguments[i]).append(' ');
            Hud.watermarkText.setValue(sb.toString());
        } else
            throw new CommandExecutionException(getUsage());
    }

    @Override
    public String getUsage() {
        return "clientname <name>";
    }
}
