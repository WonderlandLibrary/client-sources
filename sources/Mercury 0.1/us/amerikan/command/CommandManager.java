/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import us.amerikan.amerikan;
import us.amerikan.command.Command;
import us.amerikan.command.commands.AutoSettings;
import us.amerikan.command.commands.Bind;
import us.amerikan.command.commands.Friend;
import us.amerikan.command.commands.Help;
import us.amerikan.command.commands.Toggle;
import us.amerikan.utils.Logger;

public class CommandManager {
    private List<Command> commands = new ArrayList<Command>();
    public String Chat_Prefix = ".";

    public CommandManager() {
        this.addCommand(new Bind());
        this.addCommand(new Toggle());
        this.addCommand(new Help());
        this.addCommand(new AutoSettings());
        this.addCommand(new Friend());
    }

    public void addCommand(Command cmd) {
        this.commands.add(cmd);
        amerikan.instance.logger.Loading("Commands: " + cmd.getName());
    }

    public boolean execute(String text) {
        if (!text.startsWith(this.Chat_Prefix)) {
            return false;
        }
        text = text.substring(1);
        String[] arguments = text.split(" ");
        for (Command cmd : this.commands) {
            if (!cmd.getName().equalsIgnoreCase(arguments[0])) continue;
            String[] args = Arrays.copyOfRange(arguments, 1, arguments.length);
            cmd.execute(args);
            return true;
        }
        return false;
    }
}

