package net.shoreline.client.impl.command;

import baritone.api.command.ICommand;
import net.shoreline.client.api.command.Command;

/**
 * @author Shoreline
 * @since 1.0
 */
public class BaritoneCommand extends Command {
    private final ICommand command;

    /**
     * @param command
     */
    public BaritoneCommand(ICommand command) {
        super(command.getNames().get(0), command.getShortDesc());
        this.command = command;
    }

    @Override
    public void onCommandInput() {
        // command.execute();
    }
}
