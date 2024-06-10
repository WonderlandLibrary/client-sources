package me.dinozoid.strife.command;

import me.dinozoid.strife.command.implementations.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandRepository {

    private final List<Command> COMMANDS = new ArrayList<>();

    public void init() {
        COMMANDS.addAll(Arrays.asList(new HelpCommand(),
                                    new ToggleCommand(),
                                    new BindCommand(),
                                    new FriendCommand(),
                                    new TargetCommand(),
                                    new ConfigCommand(),
                                    new FontCommand()));
    }

    public List<Command> commands() {
        return COMMANDS;
    }
}
