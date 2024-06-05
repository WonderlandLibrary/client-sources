package net.shoreline.client.impl.command;

import net.shoreline.client.Shoreline;
import net.shoreline.client.api.command.Command;
import net.shoreline.client.api.command.arg.Argument;
import net.shoreline.client.api.command.arg.arguments.StringArgument;

import java.util.List;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class ConfigCommand extends Command
{
    //
    Argument<String> actionArgument = new StringArgument("Action", "Whether " +
            "to save or load a preset", List.of("save", "load"));
    Argument<String> nameArgument = new StringArgument("ConfigName", "The " +
            "name for the config preset");

    /**
     *
     */
    public ConfigCommand()
    {
        super("Config", "Creates a new configuration preset");
    }

    /**
     * Runs when the command is inputted in chat
     */
    @Override
    public void onCommandInput()
    {
        String action = actionArgument.getValue();
        String name = nameArgument.getValue();
        if (action == null || name == null)
        {
            return;
        }
        if (action.equalsIgnoreCase("save"))
        {
            Shoreline.CONFIG.setConfigPreset(name);

        }
        else if (action.equalsIgnoreCase("load"))
        {

        }
    }
}
