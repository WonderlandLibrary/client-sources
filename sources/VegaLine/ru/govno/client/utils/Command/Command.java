/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils.Command;

public abstract class Command {
    private String name;
    private String description;
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

    public abstract void onCommand(String[] var1);

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getAliases() {
        return this.aliases;
    }

    public void setAliases(String[] aliases) {
        this.aliases = aliases;
    }
}

