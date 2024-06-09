package com.wikihacks.command;

public abstract class Command {
        public abstract boolean onCommand(String command, String[] args);

        public String getDescription() {
            return "There is no description.";
        }

        public abstract String getUsage();
}

