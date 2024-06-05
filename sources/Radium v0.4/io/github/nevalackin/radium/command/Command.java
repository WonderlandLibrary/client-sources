package io.github.nevalackin.radium.command;

public abstract class Command {

    public abstract String[] getAliases();

    public abstract void execute(String[] arguments) throws CommandExecutionException;

    public abstract String getUsage();

}
