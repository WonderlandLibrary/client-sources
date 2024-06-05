package me.vaziak.sensation.client.api.command;

public abstract class Command {
    private final String name, description, usage;
    private final String[] aliases;

    public Command(String name, String description, String usage, String[] aliases) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.aliases = aliases;
    }

    public abstract boolean onCommand(String[] args);

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }

    public String[] getAliases() {
        return aliases;
    }
}
