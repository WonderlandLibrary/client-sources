/*
 * Decompiled with CFR 0_122.
 */
package winter.console.cmds;

import java.util.ArrayList;
import winter.console.Console;
import winter.console.cmds.Command;

public class help
extends Command {
    public help() {
        super("Help");
        this.desc("Lists all commands.");
        this.use("help");
    }

    @Override
    public void run(String cmd2) {
        String[] args = cmd2.split(" ");
        if (args[0].equalsIgnoreCase("help")) {
            for (Command command : Console.cmds) {
                this.printChat(String.valueOf(command.name) + ": " + command.desc + " | " + command.use);
            }
        }
    }
}

