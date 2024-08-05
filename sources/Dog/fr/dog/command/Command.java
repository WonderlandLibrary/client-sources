package fr.dog.command;

import fr.dog.util.InstanceAccess;
import lombok.Getter;

@Getter
public abstract class Command implements InstanceAccess {
    private final String[] aliases;

    public Command(final String... aliases) {
        this.aliases = aliases;
    }

    public abstract void execute(final String[] args, final String message);
}