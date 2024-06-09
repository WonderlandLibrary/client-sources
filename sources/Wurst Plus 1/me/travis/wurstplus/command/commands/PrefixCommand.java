package me.travis.wurstplus.command.commands;

import me.travis.wurstplus.command.Command;
import me.travis.wurstplus.command.syntax.ChunkBuilder;

public class PrefixCommand extends Command {

    public PrefixCommand() {
        super("prefix", new ChunkBuilder().append("character").build());
    }

    @Override
    public void call(String[] args) {
        if (args.length == 0) {
            Command.sendChatMessage("State ur prefix cuz");
            return;
        }

        Command.commandPrefix.setValue(args[0]);
        Command.sendChatMessage("Prefix set to &b" + Command.commandPrefix.getValue());
    }

}
