package dev.luvbeeq.baritone.api.command.datatypes;

import dev.luvbeeq.baritone.api.command.exception.CommandException;
import dev.luvbeeq.baritone.api.command.helpers.TabCompleteHelper;
import net.minecraft.util.Direction;

import java.util.Locale;
import java.util.stream.Stream;

public enum ForAxis implements IDatatypeFor<Direction.Axis> {
    INSTANCE;

    @Override
    public Direction.Axis get(IDatatypeContext ctx) throws CommandException {
        return Direction.Axis.valueOf(ctx.getConsumer().getString().toUpperCase(Locale.US));
    }

    @Override
    public Stream<String> tabComplete(IDatatypeContext ctx) throws CommandException {
        return new TabCompleteHelper()
                .append(Stream.of(Direction.Axis.values())
                        .map(Direction.Axis::getString).map(String::toLowerCase))
                .filterPrefix(ctx.getConsumer().getString())
                .stream();
    }
}
