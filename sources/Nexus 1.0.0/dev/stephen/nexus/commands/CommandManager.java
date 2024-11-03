package dev.stephen.nexus.commands;

import dev.stephen.nexus.commands.impl.*;
import dev.stephen.nexus.utils.mc.ChatUtils;
import lombok.Getter;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Getter
public class CommandManager {
    private final List<Command> commands = new ArrayList<>();
    private final String prefix = ".";

    public CommandManager() {
        commands.add(new BindCommand());
        commands.add(new HelpCommand());
        commands.add(new ConfigCommand());
        commands.add(new BindListCommand());
        commands.add(new BindClearCommand());
        commands.add(new CloudConfigCommand());
    }

    public static String[] removeElement(String[] input, int indexToDelete) {
        LinkedList<String> result = new LinkedList<>();
        for (int i = 0; i < input.length; ++i) {
            if (i == indexToDelete) continue;
            result.add(input[i]);
        }
        return result.toArray(input);
    }

    private static String strip(String str, String key) {
        if (str.startsWith(key) && str.endsWith(key)) {
            return str.substring(key.length(), str.length() - key.length());
        }
        return str;
    }

    public void executeCommand(String command) {
        String[] parts = command.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        String name = parts[0].substring(1);
        String[] args = CommandManager.removeElement(parts, 0);
        for (int i = 0; i < args.length; ++i) {
            if (args[i] == null) continue;
            args[i] = CommandManager.strip(args[i], "\"");
        }
        for (Command c : this.commands) {
            if (!c.getName().equalsIgnoreCase(name)) continue;
            c.execute(parts);
            return;
        }
        ChatUtils.addMessageToChat(Formatting.GRAY + "Command not found, type 'help' for the commands list.");
    }

    public Command getCommandByName(String name) {
        for (Command command : this.commands) {
            if (!command.getName().equals(name)) continue;
            return command;
        }
        return null;
    }
}