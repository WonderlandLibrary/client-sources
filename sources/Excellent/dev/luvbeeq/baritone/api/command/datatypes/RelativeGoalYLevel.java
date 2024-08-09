package dev.luvbeeq.baritone.api.command.datatypes;

import dev.luvbeeq.baritone.api.command.argument.IArgConsumer;
import dev.luvbeeq.baritone.api.command.exception.CommandException;
import dev.luvbeeq.baritone.api.pathing.goals.GoalYLevel;
import dev.luvbeeq.baritone.api.utils.BetterBlockPos;
import net.minecraft.util.math.MathHelper;

import java.util.stream.Stream;

public enum RelativeGoalYLevel implements IDatatypePost<GoalYLevel, BetterBlockPos> {
    INSTANCE;

    @Override
    public GoalYLevel apply(IDatatypeContext ctx, BetterBlockPos origin) throws CommandException {
        if (origin == null) {
            origin = BetterBlockPos.ORIGIN;
        }

        return new GoalYLevel(
                MathHelper.floor(ctx.getConsumer().getDatatypePost(RelativeCoordinate.INSTANCE, (double) origin.y))
        );
    }

    @Override
    public Stream<String> tabComplete(IDatatypeContext ctx) {
        final IArgConsumer consumer = ctx.getConsumer();
        if (consumer.hasAtMost(1)) {
            return consumer.tabCompleteDatatype(RelativeCoordinate.INSTANCE);
        }
        return Stream.empty();
    }
}
