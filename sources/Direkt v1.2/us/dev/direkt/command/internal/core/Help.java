package us.dev.direkt.command.internal.core;

import us.dev.direkt.Direkt;
import us.dev.direkt.command.Command;
import us.dev.direkt.command.handler.annotations.Executes;

import java.util.function.Supplier;

/**
 * @author Foundry
 */
public class Help extends Command {
    public Help() {
        super(Direkt.getInstance().getCommandManager(), "help", "h", "?");
    }

    @Executes
    public String run(String commandName) {
        return Direkt.getInstance().getCommandManager().find(commandName)
                .<Supplier<String>>map(command -> () -> command.getFormattedUsage(true))
                .orElseGet(() -> () -> String.format("'%s' is not a valid command", commandName))
                .get();
    }

}
