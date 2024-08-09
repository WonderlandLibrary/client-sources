package dev.luvbeeq.baritone.utils;

import dev.luvbeeq.baritone.api.pathing.goals.Goal;
import dev.luvbeeq.baritone.api.process.PathingCommand;
import dev.luvbeeq.baritone.api.process.PathingCommandType;
import dev.luvbeeq.baritone.pathing.movement.CalculationContext;

public class PathingCommandContext extends PathingCommand {

    public final CalculationContext desiredCalcContext;

    public PathingCommandContext(Goal goal, PathingCommandType commandType, CalculationContext context) {
        super(goal, commandType);
        this.desiredCalcContext = context;
    }
}
