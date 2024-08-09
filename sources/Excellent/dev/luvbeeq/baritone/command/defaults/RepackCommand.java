package dev.luvbeeq.baritone.command.defaults;

import dev.luvbeeq.baritone.api.BaritoneAPI;
import dev.luvbeeq.baritone.api.IBaritone;
import dev.luvbeeq.baritone.api.command.Command;
import dev.luvbeeq.baritone.api.command.argument.IArgConsumer;
import dev.luvbeeq.baritone.api.command.exception.CommandException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class RepackCommand extends Command {

    public RepackCommand(IBaritone baritone) {
        super(baritone, "repack", "rescan");
    }

    @Override
    public void execute(String label, IArgConsumer args) throws CommandException {
        args.requireMax(0);
        logDirect(String.format("Queued %d chunks for repacking", BaritoneAPI.getProvider().getWorldScanner().repack(ctx)));
    }

    @Override
    public Stream<String> tabComplete(String label, IArgConsumer args) {
        return Stream.empty();
    }

    @Override
    public String getShortDesc() {
        return "Re-cache chunks";
    }

    @Override
    public List<String> getLongDesc() {
        return Arrays.asList(
                "Repack chunks around you. This basically re-caches them.",
                "",
                "Usage:",
                "> repack - Repack chunks."
        );
    }
}
