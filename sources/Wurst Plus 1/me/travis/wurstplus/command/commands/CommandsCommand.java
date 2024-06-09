package me.travis.wurstplus.command.commands;

import me.travis.wurstplus.wurstplusMod;
import me.travis.wurstplus.command.Command;
import me.travis.wurstplus.command.syntax.SyntaxChunk;

import java.util.Comparator;

public class CommandsCommand extends Command {

    public CommandsCommand() {
        super("commands", SyntaxChunk.EMPTY);
    }

    @Override
    public void call(String[] args) {
        wurstplusMod.getInstance().getCommandManager().getCommands().stream().sorted(Comparator.comparing(command -> command.getLabel())).forEach(command ->
            Command.sendChatMessage("&7" + Command.getCommandPrefix() + command.getLabel() + "&r ~ &8" + command.getDescription())
        );
    }
}
