/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.nemphis.command;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import pw.vertexcode.nemphis.command.Command;
import pw.vertexcode.nemphis.commands.BindCommand;
import pw.vertexcode.nemphis.commands.ExecCommand;

public class CommandManager {
    public List<Command> commands = new ArrayList<Command>();

    public CommandManager() {
        this.commands.clear();
        this.registerCommand(new BindCommand());
        this.registerCommand(new ExecCommand());
    }

    public void registerCommand(Command cmd) {
        if (!this.commands.contains(cmd)) {
            this.commands.add(cmd);
            System.out.println("> Registering Command: " + cmd.getName());
        }
    }
}

