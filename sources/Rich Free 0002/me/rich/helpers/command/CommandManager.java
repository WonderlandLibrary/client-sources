package me.rich.helpers.command;

import java.util.ArrayList;
import me.rich.helpers.command.impl.*;

public class CommandManager {

    public final ArrayList<Command> commands = new ArrayList<>();

    public CommandManager() {
        commands.add(new FeatureToggle());
        commands.add(new Bind());
        commands.add(new Clip());

    }

    public ArrayList<Command> getCommands() {
        return commands;
    }
}