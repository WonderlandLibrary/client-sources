package client.metaware.api.command.implementations;

import client.metaware.Metaware;
import client.metaware.api.command.Command;
import client.metaware.api.command.CommandInfo;
import client.metaware.api.command.argument.Argument;
import client.metaware.client.Logger;
import client.metaware.impl.utils.render.StringUtils;

import java.util.List;

@CommandInfo(name = "Help", description = "Shows this help menu.")
public class HelpCommand extends Command {

    @Override
    public boolean execute(String[] args, String label) {
        Logger.printWithoutPrefix("&8&m----------------------------");
        Logger.printWithoutPrefix("&9 Commands");
        Logger.printWithoutPrefix("&8&m----------------------------");
        for(Command command : Metaware.INSTANCE.getCommandManager().commands()) {
            Logger.printWithoutPrefix("&9 " + command.name() + " &7|&f " + StringUtils.stripFormatting(command.printableUsage(true, args)) + "&7- " + command.description());
        }
        Logger.printWithoutPrefix("&8&m----------------------------");
        return true;
    }

    @Override
    public List<Argument> arguments(String[] args) {
        return null;
    }
}
