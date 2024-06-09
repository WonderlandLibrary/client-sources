package com.wikihacks.module;

public abstract class Module {

    private final String name;

    private final String[] aliases;

    private final String description;

    private final Category category;

    private boolean enabled;

    private boolean visible;

    protected Module(String name, String[] aliases, String description, Category category) {
        this.name = name;
        this.aliases = aliases;
        this.description = description;
        this.category = category;
        this.visible = true;
        this.enabled = false;
    }

    public abstract void onEnable();

    public abstract void onDisable();

    public abstract void onUpdate();

    public String getName() {
        return name;
    }

    public String[] getAliases() {
        return aliases;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void toggle() {
        this.enabled = !this.enabled;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}