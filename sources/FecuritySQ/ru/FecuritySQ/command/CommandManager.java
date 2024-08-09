package ru.FecuritySQ.command;


import ru.FecuritySQ.command.imp.*;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {

    private final ArrayList<CommandAbstract> commands = new ArrayList<>();

    public CommandManager() {
        commands.add(new HelpCommand());
        commands.add(new MacroCommand());
        commands.add(new BindCommand());
        commands.add(new FriendCommand());
        commands.add(new GPSCommand());
        commands.add(new PanicCommand());
        commands.add(new ConfigCommand());
    }

    public List<CommandAbstract> getCommands() {
        return this.commands;
    }

    public boolean execute(String args) {
        for (CommandAbstract command : commands) {
            String[] split = args.substring(1).split(" ");
            if (split[0].equalsIgnoreCase(command.name)) {
                try {
                    command.execute(split);
                } catch (Exception e) {
                    command.error();
                }
                return true;
            }
        }
        return false;
    }
}