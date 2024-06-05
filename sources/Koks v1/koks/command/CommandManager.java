package koks.command;

import koks.command.impl.*;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class CommandManager {

    public ArrayList<Command> commands = new ArrayList<>();

    public CommandManager() {
        addCommand(new Bind());
        addCommand(new config());
        addCommand(new Toggle());
        addCommand(new VClip());
        addCommand(new Friend());
        addCommand(new Reload());
        addCommand(new InGameName());
        addCommand(new Register());
        addCommand(new Login());
    }


    public void addCommand(Command cmd) {
        commands.add(cmd);
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }
}
