package net.shoreline.client.api.command.arg.arguments;

import net.shoreline.client.api.command.Command;
import net.shoreline.client.api.command.arg.Argument;
import net.shoreline.client.init.Managers;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 *
 * @author linus
 * @since 1.0
 *
 * @see Command
 */
public class CommandArgument extends Argument<Command>
{
    //
    private final Collection<String> commandIds = new ArrayList<>();

    /**
     *
     *
     * @param name
     * @param desc
     */
    public CommandArgument(String name, String desc)
    {
        super(name, desc);
    }

    /**
     * @see Command#onCommandInput()
     */
    @Override
    public Command getValue()
    {
        for (Command command : Managers.COMMAND.getCommands())
        {
            if (command.getName().equalsIgnoreCase(getLiteral()))
            {
                return command;
            }
        }
        // ChatUtil.error("Failed to parse Command argument! Literal: " + getLiteral());
        return null;
    }

    /**
     *
     * @return
     */
    @Override
    public Collection<String> getSuggestions()
    {
        if (!commandIds.isEmpty())
        {
            return commandIds;
        }
        for (Command command : Managers.COMMAND.getCommands())
        {
            commandIds.add(command.getName());
        }
        return commandIds;
    }
}
