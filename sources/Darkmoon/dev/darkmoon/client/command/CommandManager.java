package dev.darkmoon.client.command;

import com.darkmagician6.eventapi.EventManager;
import dev.darkmoon.client.command.impl.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    private final ArrayList<CommandAbstract> commands = new ArrayList<>();

    @Getter
    @Setter
    private static String prefix = ".";

    public CommandManager() {
        EventManager.register(new CommandHandler(this));
        commands.add(new PrefixCommand());
        commands.add(new ConfigCommand());
        commands.add(new FriendCommand());
        commands.add(new WayCommand());
        commands.add(new ClipCommand());
        commands.add(new StaffCommand());
        commands.add(new BindCommand());
        commands.add(new HClipCommand());
        commands.add(new MacroCommand());
        commands.add(new ParseCommand());
        commands.add(new HelpCommand());
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
