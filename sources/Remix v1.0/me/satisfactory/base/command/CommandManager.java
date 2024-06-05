package me.satisfactory.base.command;

import java.util.*;
import me.mees.remix.commands.*;
import xyz.vladymyr.commons.*;

public class CommandManager
{
    public final Map<String, Command> commands;
    
    public CommandManager() {
        Maps.putAll(this.commands = new HashMap<String, Command>(), Command::getName, new CommandBind(), new CommandConfig(), new CommandFriend(), new CommandHelp(), new CommandMode(), new CommandIRC(), new CommandToggle());
    }
    
    public Command getCommand(final String name) {
        return this.commands.get(name);
    }
}
