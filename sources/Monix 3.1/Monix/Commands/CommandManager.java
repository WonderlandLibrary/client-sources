/*
 * Decompiled with CFR 0_122.
 */
package Monix.Commands;

import Monix.Commands.Command;
import Monix.Commands.commands.Bind;
import Monix.Commands.commands.Help;
import Monix.Monix;
import java.util.ArrayList;

public class CommandManager {
    private ArrayList<Command> Commands = new ArrayList();

    public CommandManager() {
        this.addCommand(new Bind());
        this.addCommand(new Help());
    }

    public void addCommand(Command c) {
        this.Commands.add(c);
    }

    public ArrayList<Command> getCommands() {
        return this.Commands;
    }

    public void callCommand(String input) {
        String[] split = input.split(" ");
        String Command2 = split[0];
        String args = input.substring(Command2.length()).trim();
        for (Command c : this.getCommands()) {
            if (!c.getAlias().equalsIgnoreCase(Command2)) continue;
            try {
                c.onCommand(args, args.split(" "));
            }
            catch (Exception e) {
                Monix.addChatMessage(c.getSyntax());
            }
            return;
        }
        Monix.addChatMessage("\u00a77Command not found! use -help");
    }
}

