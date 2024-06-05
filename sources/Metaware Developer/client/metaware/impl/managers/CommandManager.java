package client.metaware.impl.managers;

import client.metaware.api.command.Command;
import client.metaware.api.command.implementations.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager {

    private final List<Command> COMMANDS = new ArrayList<>();

    public void init() {
        COMMANDS.addAll(Arrays.asList(new HelpCommand(),
                new ToggleCommand(),
                new BindCommand(),
                new ConfigCommand())
        );
    }

    public List<Command> commands() {
        return COMMANDS;
    }
}