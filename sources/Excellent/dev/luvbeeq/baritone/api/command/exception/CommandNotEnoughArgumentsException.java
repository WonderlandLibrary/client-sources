package dev.luvbeeq.baritone.api.command.exception;

public class CommandNotEnoughArgumentsException extends CommandErrorMessageException {

    public CommandNotEnoughArgumentsException(int minArgs) {
        super(String.format("Not enough arguments (expected at least %d)", minArgs));
    }
}
