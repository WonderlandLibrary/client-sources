package dev.luvbeeq.baritone.command.defaults;

import dev.luvbeeq.baritone.api.BaritoneAPI;
import dev.luvbeeq.baritone.api.IBaritone;
import dev.luvbeeq.baritone.api.command.Command;
import dev.luvbeeq.baritone.api.command.argument.IArgConsumer;
import dev.luvbeeq.baritone.api.command.exception.CommandException;
import dev.luvbeeq.baritone.api.process.ICustomGoalProcess;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class PathCommand extends Command {

    public PathCommand(IBaritone baritone) {
        super(baritone, "path");
    }

    @Override
    public void execute(String label, IArgConsumer args) throws CommandException {
        ICustomGoalProcess customGoalProcess = baritone.getCustomGoalProcess();
        args.requireMax(0);
        BaritoneAPI.getProvider().getWorldScanner().repack(ctx);
        customGoalProcess.path();
        logDirect("Now pathing");
    }

    @Override
    public Stream<String> tabComplete(String label, IArgConsumer args) throws CommandException {
        return Stream.empty();
    }

    @Override
    public String getShortDesc() {
        return "Start heading towards the goal";
    }

    @Override
    public List<String> getLongDesc() {
        return Arrays.asList(
                "The path command tells Baritone to head towards the current goal.",
                "",
                "Usage:",
                "> path - Start the pathing."
        );
    }
}
