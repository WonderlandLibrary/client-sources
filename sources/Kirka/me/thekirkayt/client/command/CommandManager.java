/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.command;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import me.thekirkayt.client.command.Com;
import me.thekirkayt.client.command.Command;
import me.thekirkayt.client.command.commands.OptionCommand;
import me.thekirkayt.client.command.commands.UnknownCommand;
import org.reflections.Reflections;
import org.reflections.scanners.Scanner;

public class CommandManager {
    public static List<Command> commandList = new ArrayList<Command>();
    public static OptionCommand optionCommand = new OptionCommand();
    public static final UnknownCommand COMMAND_UNKNOWN = new UnknownCommand();

    public static void start() {
        try {
            Reflections reflections = new Reflections("me.thekirkayt.client.command.commands", new Scanner[0]);
            Set<Class<Command>> classes = reflections.getSubTypesOf(Command.class);
            for (Class<Command> clazz : classes) {
                Command loadedCommand = clazz.newInstance();
                if (!clazz.isAnnotationPresent(Com.class)) continue;
                Com comAnnotation = clazz.getAnnotation(Com.class);
                loadedCommand.setNames(comAnnotation.names());
                commandList.add(loadedCommand);
            }
            commandList.add(optionCommand);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Command getCommandFromMessage(String message) {
        for (Command command : commandList) {
            if (command.getNames() == null) {
                return new UnknownCommand();
            }
            for (String name : command.getNames()) {
                if (!message.split(" ")[0].equalsIgnoreCase(name)) continue;
                return command;
            }
        }
        return COMMAND_UNKNOWN;
    }
}

