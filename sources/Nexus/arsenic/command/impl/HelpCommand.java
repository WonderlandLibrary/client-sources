package arsenic.command.impl;

import java.util.Arrays;
import java.util.List;

import arsenic.command.Command;
import arsenic.command.CommandInfo;
import arsenic.main.Nexus;
import arsenic.utils.minecraft.PlayerUtils;

@CommandInfo(name = "help", args = { "command" }, help = "shows help for commands", minArgs = 1)
public class HelpCommand extends Command {

    @Override
    public void execute(String[] args) {
        if (args.length == 0) {
            execute(new String[] { "help" });
            return;
        }

        Command command = Nexus.getNexus().getCommandManager().getCommandByName(args[0]);
        if (command != null) {
            PlayerUtils.addMessageToChat("---------------");
            PlayerUtils.addWaterMarkedMessageToChat(command.getName() + "'s info:");
            PlayerUtils.addWaterMarkedMessageToChat("Description: " + command.getHelp());
            PlayerUtils.addWaterMarkedMessageToChat("Aliases: " + Arrays.toString(command.getAliases()));
            PlayerUtils.addWaterMarkedMessageToChat("Usage: " + command.getUsage());
            PlayerUtils.addMessageToChat("---------------");
        }
    }

    @Override
    protected List<String> getAutoComplete(String str, int arg, List<String> list) {
        return arg == 0 ? Nexus.getNexus().getCommandManager().getClosestCommandName(str) : list;
    }
}
