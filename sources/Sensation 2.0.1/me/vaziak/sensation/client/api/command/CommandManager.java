package me.vaziak.sensation.client.api.command;

import java.util.ArrayList;
import java.util.List;

import me.vaziak.sensation.client.api.command.impl.*;

public class CommandManager {
    private List<Command> commandList = new ArrayList<>();

    public CommandManager() {
        commandList.add(new BindCommand());
        commandList.add(new HelpCommand());
        commandList.add(new FriendCommand());
        commandList.add(new ToggleCommand());
        commandList.add(new VClipCommand());

        new CommandHandler(); // call to start
    }

    public List<Command> getCommandList() {
        return commandList;
    }
}
