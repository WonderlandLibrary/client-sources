package dev.luvbeeq.baritone.command.defaults;

import dev.luvbeeq.baritone.api.IBaritone;
import dev.luvbeeq.baritone.api.command.Command;
import dev.luvbeeq.baritone.api.command.argument.IArgConsumer;
import dev.luvbeeq.baritone.api.command.exception.CommandException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class GcCommand extends Command {

    public GcCommand(IBaritone baritone) {
        super(baritone, "gc");
    }

    @Override
    public void execute(String label, IArgConsumer args) throws CommandException {
        args.requireMax(0);
        System.gc();
        logDirect("ok called System.gc()");
    }

    @Override
    public Stream<String> tabComplete(String label, IArgConsumer args) {
        return Stream.empty();
    }

    @Override
    public String getShortDesc() {
        return "Call System.gc()";
    }

    @Override
    public List<String> getLongDesc() {
        return Arrays.asList(
                "Calls System.gc().",
                "",
                "Usage:",
                "> gc"
        );
    }
}
