package dev.luvbeeq.baritone.command.defaults;

import dev.luvbeeq.baritone.api.IBaritone;
import dev.luvbeeq.baritone.api.command.Command;
import dev.luvbeeq.baritone.api.command.argument.IArgConsumer;
import dev.luvbeeq.baritone.api.command.exception.CommandException;
import dev.luvbeeq.baritone.api.command.exception.CommandInvalidStateException;
import dev.luvbeeq.baritone.api.pathing.calc.IPathingControlManager;
import dev.luvbeeq.baritone.api.process.IBaritoneProcess;
import dev.luvbeeq.baritone.api.process.PathingCommand;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ProcCommand extends Command {

    public ProcCommand(IBaritone baritone) {
        super(baritone, "proc");
    }

    @Override
    public void execute(String label, IArgConsumer args) throws CommandException {
        args.requireMax(0);
        IPathingControlManager pathingControlManager = baritone.getPathingControlManager();
        IBaritoneProcess process = pathingControlManager.mostRecentInControl().orElse(null);
        if (process == null) {
            throw new CommandInvalidStateException("No process in control");
        }
        logDirect(String.format(
                """
                        Class: %s
                        Priority: %f
                        Temporary: %b
                        Display name: %s
                        Last command: %s""",
                process.getClass().getTypeName(),
                process.priority(),
                process.isTemporary(),
                process.displayName(),
                pathingControlManager
                        .mostRecentCommand()
                        .map(PathingCommand::toString)
                        .orElse("None")
        ));
    }

    @Override
    public Stream<String> tabComplete(String label, IArgConsumer args) {
        return Stream.empty();
    }

    @Override
    public String getShortDesc() {
        return "View process state information";
    }

    @Override
    public List<String> getLongDesc() {
        return Arrays.asList(
                "The proc command provides miscellaneous information about the process currently controlling Baritone.",
                "",
                "You are not expected to understand this if you aren't familiar with how Baritone works.",
                "",
                "Usage:",
                "> proc - View process information, if present"
        );
    }
}
