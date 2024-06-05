/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.command;

import cc.diablo.command.Command;
import cc.diablo.command.impl.BindCommand;
import cc.diablo.command.impl.ClearChatCommand;
import cc.diablo.command.impl.ClientNameCommand;
import cc.diablo.command.impl.CommandsCommand;
import cc.diablo.command.impl.ConfigCommand;
import cc.diablo.command.impl.FriendCommand;
import cc.diablo.command.impl.HelpCommand;
import cc.diablo.command.impl.ModulesCommand;
import cc.diablo.command.impl.OpenFolderCommand;
import cc.diablo.command.impl.PanicCommand;
import cc.diablo.command.impl.QuitCommand;
import cc.diablo.command.impl.ToggleCommand;
import cc.diablo.command.impl.VClipCommand;
import java.util.ArrayList;
import java.util.Arrays;

public class CommandManager {
    public static ArrayList<Command> commands = new ArrayList();

    public CommandManager() {
        System.out.println("Loading commands...");
        this.addModules(new ToggleCommand(), new ModulesCommand(), new HelpCommand(), new CommandsCommand(), new VClipCommand(), new ClearChatCommand(), new BindCommand(), new FriendCommand(), new OpenFolderCommand(), new PanicCommand(), new ConfigCommand(), new ClientNameCommand(), new QuitCommand());
        for (Command c : commands) {
            System.out.println(c.getDisplayName() + " | " + c.getDescription());
        }
        System.out.println("Loaded " + commands.size() + " commands!");
    }

    public void addModules(Command ... modulesAsList) {
        commands.addAll(Arrays.asList(modulesAsList));
    }

    public static ArrayList<Command> getModules() {
        return commands;
    }

    public static <T extends Command> Command getCommand(Class<T> clas) {
        return CommandManager.getModules().stream().filter(command -> command.getClass() == clas).findFirst().orElse(null);
    }
}

