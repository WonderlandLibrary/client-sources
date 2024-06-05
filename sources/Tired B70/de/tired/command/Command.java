package de.tired.command;

import de.tired.interfaces.IHook;
import de.tired.api.annotations.CommandAnnotation;

public class Command implements IHook {

    public final String name = getClass().getAnnotation(CommandAnnotation.class).name();

    public final String help = getClass().getAnnotation(CommandAnnotation.class).help();

    public final String[] alias = getClass().getAnnotation(CommandAnnotation.class).alias();

    public void doCommand(String[] args) {

    }

    public String getHelp() {
        return help;
    }

    public String getName() {
        return name;
    }

    public String[] getAlias() {
        return alias;
    }
}
