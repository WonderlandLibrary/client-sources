package me.rich.helpers.command;

public abstract class Command {
    private String name, description;
    private String[] aliases;

    public Command(String name, String description, String[] aliases) {
        this.name = name;
        this.description = description;
        this.aliases = aliases;
    }

    public Command(String name, String[] aliases) {
        this.name = name;
        this.aliases = aliases;
        this.description = null;
    }

    public abstract void onCommand(String args[]);

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getAliases() {
        return aliases;
    }

    public void setAliases(String[] aliases) {
        this.aliases = aliases;
    }
}