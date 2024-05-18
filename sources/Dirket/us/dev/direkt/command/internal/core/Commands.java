package us.dev.direkt.command.internal.core;

import us.dev.direkt.Direkt;
import us.dev.direkt.command.Command;
import us.dev.direkt.command.handler.annotations.Executes;
import us.dev.direkt.module.Module;

import java.util.stream.Collectors;

/**
 * @author Foundry
 */
public class Commands extends Command {
    public Commands() {
        super(Direkt.getInstance().getCommandManager(), "commands", "cmds", "commandlist", "cmdlist", "c");
    }

    @Executes
    public String run() {
        final int[] count = new int[1];
        final String resultString = Direkt.getInstance().getCommandManager().getCommands().stream()
                .filter(command -> !(command instanceof Module.ModuleCommand))
                .distinct()
                .peek(command -> count[0]++)
                .map(Command::getLabel)
                .sorted()
                .map(label -> "\u00A76" + label + "\u00A78")
                .collect(Collectors.joining(", ", "\u00A78[\u00A7r", "\u00A78]"));
        return "\u00A7lCommands \u00A7r\u00A77(\u00A7f" + count[0] + "\u00A77):" + System.lineSeparator() + resultString;
    }

}
