/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.managers;

import drunkclient.beta.API.EventBus;
import drunkclient.beta.API.commands.Command;
import drunkclient.beta.API.commands.impl.Bind;
import drunkclient.beta.API.commands.impl.Client;
import drunkclient.beta.API.commands.impl.Config;
import drunkclient.beta.IMPL.managers.Manager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandManager
implements Manager {
    private static List<Command> commands;

    @Override
    public void init() {
        commands = new ArrayList<Command>();
        commands.add(new Bind());
        commands.add(new Client());
        commands.add(new Config());
        EventBus.getInstance().register(this);
    }

    public List<Command> getCommands() {
        return commands;
    }

    public static Optional<Command> getCommandByName(String name) {
        return commands.stream().filter(c2 -> {
            boolean isAlias = false;
            for (String str : c2.getAlias()) {
                if (!str.equalsIgnoreCase(name)) continue;
                isAlias = true;
                break;
            }
            if (c2.getName().equalsIgnoreCase(name)) return true;
            if (isAlias) return true;
            return false;
        }).findFirst();
    }

    public void add(Command command) {
        commands.add(command);
    }
}

