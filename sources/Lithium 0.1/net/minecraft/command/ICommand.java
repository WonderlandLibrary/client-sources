package net.minecraft.command;

import net.minecraft.util.BlockPosition;

import java.util.List;

public interface ICommand extends Comparable<ICommand>
{
    /**
     * Gets the name of the command
     */
    String getCommandName();

    /**
     * Gets the usage string for the command.
     */
    String getCommandUsage(ICommandSender sender);

    List<String> getCommandAliases();

    /**
     * Callback when the command is invoked
     */
    void processCommand(ICommandSender sender, String[] args) throws CommandException;

    /**
     * Returns true if the given command sender is allowed to use this command.
     */
    boolean canCommandSenderUseCommand(ICommandSender sender);

    List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPosition pos);

    /**
     * Return whether the specified command parameter index is a username parameter.
     */
    boolean isUsernameIndex(String[] args, int index);
}
