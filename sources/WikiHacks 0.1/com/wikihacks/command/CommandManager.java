package com.wikihacks.command;

import java.util.LinkedHashMap;

public class CommandManager {
    private static LinkedHashMap<String, Command> registered_commands = new LinkedHashMap<>();
    private static String prefix = ".";

    public static void init() {
    }

    public static void register(String id, Command command) {
        registered_commands.put(id, command);
    }

    public static LinkedHashMap<String, Command> getRegistered() {
        return registered_commands;
    }

    public static String getPrefix() {
        return prefix;
    }

    public static void setPrefix(String prefix) {
        CommandManager.prefix = prefix;
    }
}
