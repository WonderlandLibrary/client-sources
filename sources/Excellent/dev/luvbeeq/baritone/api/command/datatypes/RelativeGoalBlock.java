package dev.luvbeeq.baritone.api.command.datatypes;

import dev.luvbeeq.baritone.api.command.argument.IArgConsumer;
import dev.luvbeeq.baritone.api.command.exception.CommandException;
import dev.luvbeeq.baritone.api.pathing.goals.GoalBlock;
import dev.luvbeeq.baritone.api.utils.BetterBlockPos;
import net.minecraft.util.math.MathHelper;

import java.util.stream.Stream;

public enum RelativeGoalBlock implements IDatatypePost<GoalBlock, BetterBlockPos> {
    INSTANCE;

    @Override
    public GoalBlock apply(IDatatypeContext ctx, BetterBlockPos origin) throws CommandException {
        if (origin == null) {
            origin = BetterBlockPos.ORIGIN;
        }

        final IArgConsumer consumer = ctx.getConsumer();
        return new GoalBlock(
                MathHelper.floor(consumer.getDatatypePost(RelativeCoordinate.INSTANCE, (double) origin.x)),
                MathHelper.floor(consumer.getDatatypePost(RelativeCoordinate.INSTANCE, (double) origin.y)),
                MathHelper.floor(consumer.getDatatypePost(RelativeCoordinate.INSTANCE, (double) origin.z))
        );
    }

    @Override
    public Stream<String> tabComplete(IDatatypeContext ctx) {
        final IArgConsumer consumer = ctx.getConsumer();
        if (consumer.hasAtMost(3)) {
            return consumer.tabCompleteDatatype(RelativeCoordinate.INSTANCE);
        }
        return Stream.empty();
    }
}
