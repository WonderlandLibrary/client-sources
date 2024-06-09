package com.wikihacks.command.commands;

import com.wikihacks.command.Command;

public class ExampleCommand extends Command {
    @Override
    public boolean onCommand(String command, String[] args) {
        return false;
    }

    @Override
    public String getDescription() {
        return "example";
    }

    @Override
    public String getUsage() {
        return "example";
    }
}
