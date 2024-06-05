package me.mees.remix.commands;

import me.satisfactory.base.command.*;
import java.util.*;
import me.satisfactory.base.*;
import java.util.function.*;
import java.util.stream.*;
import me.satisfactory.base.utils.*;

public class CommandHelp extends Command
{
    public CommandHelp() {
        super("Help", "help");
    }
    
    @Override
    public void execute(final String[] args) {
        final Set<String> commandNames = Base.INSTANCE.getCommandManager().commands.values().stream().map((Function<? super Command, ?>)Command::getName).collect((Collector<? super Object, ?, Set<String>>)Collectors.toSet());
        final String joinedCommands = String.join(", ", commandNames);
        MiscellaneousUtil.sendInfo(joinedCommands);
    }
}
