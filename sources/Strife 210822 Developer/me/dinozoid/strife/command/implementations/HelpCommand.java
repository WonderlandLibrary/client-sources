package me.dinozoid.strife.command.implementations;

import me.dinozoid.strife.StrifeClient;
import me.dinozoid.strife.command.argument.Argument;
import me.dinozoid.strife.command.Command;
import me.dinozoid.strife.command.CommandInfo;
import me.dinozoid.strife.util.player.ChatFormatting;
import me.dinozoid.strife.util.player.PlayerUtil;

import java.util.List;

@CommandInfo(name = "Help", description = "Shows this help menu.")
public class HelpCommand extends Command {

    @Override
    public boolean execute(String[] args, String label) {
        PlayerUtil.sendMessage("&8&m----------------------------");
        PlayerUtil.sendMessage("&c Commands");
        PlayerUtil.sendMessage("&8&m----------------------------");
        for(Command command : StrifeClient.INSTANCE.commandRepository().commands()) {
            PlayerUtil.sendMessage("&c " + command.name() + " &7⎜&f " + ChatFormatting.stripFormatting(command.printableUsage(true, args)) + "&7- " + command.description());
        }
        PlayerUtil.sendMessage("&8&m----------------------------");
        return true;
    }

    @Override
    public List<Argument> arguments(String[] args) {
        return null;
    }
}
